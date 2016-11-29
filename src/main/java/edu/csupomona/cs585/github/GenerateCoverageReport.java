package edu.csupomona.cs585.github;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;

public class GenerateCoverageReport {
	public static final String DEST = "target/Test_Coverage_Report.pdf";
	Font tableContentFont;

	public static void main(String[] args) throws Exception {
		File file = new File(DEST);
		file.getParentFile().mkdirs();
		new GenerateCoverageReport().createPdf(DEST);
	}

	public void createPdf(String dest) throws Exception {
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(dest));
		document.open();

		Font TitleFont = FontFactory.getFont(FontFactory.TIMES, 20);
		Font H1Font = FontFactory.getFont(FontFactory.TIMES, 18);
		Font H2Font = FontFactory.getFont(FontFactory.TIMES, 16);
		Font H3Font = FontFactory.getFont(FontFactory.TIMES, 12);
		Font tableHeaderFont = FontFactory.getFont(FontFactory.TIMES, 10, Font.BOLD);
		tableContentFont = FontFactory.getFont(FontFactory.TIMES, 10);

		ListRepository.getRepository("Nadas");

		for(Path repoPath : ListRepository.clonedRepos){
			Paragraph title = new Paragraph("Test Coverage For: " + repoPath.getFileName(), TitleFont); 
			title.setAlignment(Element.ALIGN_CENTER);
			document.add(title);

			//not here, Nada!! change later.
			ListClasses.files = new ArrayList<>();
			ListClasses.testFiles = new ArrayList<>();
			ListClasses.getClasses(repoPath);

			//Class Coverage
			document.add(new Paragraph("Class Coverage: ", H1Font));
			document.add(new Paragraph(" "));

			PdfPTable classCoverageTable = new PdfPTable(new float[] { 40, 15, 15, 20 });
			classCoverageTable.setWidthPercentage(100);
			classCoverageTable.addCell(new Phrase("Repo Name", tableHeaderFont));
			classCoverageTable.addCell(new Phrase("# of Classes",tableHeaderFont));
			classCoverageTable.addCell(new Phrase("# of Test Classes", tableHeaderFont));
			classCoverageTable.addCell(new Phrase("%", tableHeaderFont));

			classCoverageTable.addCell(new Phrase(""+repoPath.getFileName(), tableContentFont));
			classCoverageTable.addCell(new Phrase(""+ListClasses.getClassCount(), tableContentFont));
			classCoverageTable.addCell(new Phrase(""+ListClasses.getTestClassCount(), tableContentFont));
			classCoverageTable.addCell(percentageTable(ListClasses.getTestClassCount(), ListClasses.getClassCount()));
			document.add(classCoverageTable);


			//Classes Details
			document.add(new Paragraph("Details: ", H2Font));
			PdfPTable classNamesTable = new PdfPTable(2);
			classNamesTable.addCell(new Phrase("Main Classes", tableHeaderFont));
			classNamesTable.addCell(new Phrase("Test Classes", tableHeaderFont));
			classNamesTable(classNamesTable);
			document.add(classNamesTable);

			Paragraph line = new Paragraph("-------------------------------------------------------------------"); 
			line.setAlignment(Element.ALIGN_CENTER);
			document.add(line);

			//Method Coverage
			document.add(new Paragraph("Method Coverage:", H1Font));
			document.add(new Paragraph(" "));

			PdfPTable methodCoverageTable = new PdfPTable(new float[] { 40, 15, 15, 20 });
			methodCoverageTable.setWidthPercentage(100);
			methodCoverageTable.addCell(new Phrase("Class Name", tableHeaderFont));
			methodCoverageTable.addCell(new Phrase("# of Test Methods", tableHeaderFont));
			methodCoverageTable.addCell(new Phrase("# of Main Methods", tableHeaderFont));
			methodCoverageTable.addCell(new Phrase("%", tableHeaderFont));


			for(Path classPaths: ListClasses.files){
				ListClasses.loadClass(classPaths);
			}

			for(Path testClassPath: ListClasses.testFiles){	
				ListMethods.methods = new ArrayList<>();
				ListMethods.getTestMethods(testClassPath); //get methods of test class
				methodCoverageTable.addCell(new Phrase(""+testClassPath.getFileName(), tableContentFont));
				methodCoverageTable.addCell(new Phrase("" + ListMethods.methodsCount, tableContentFont));

				ListMethods.methods2 = new ArrayList<>();
				ListMethods.getMainMethods(repoPath, testClassPath); //get methods of main class

				methodCoverageTable.addCell(new Phrase("" + ListMethods.methods2.size(), tableContentFont));
				methodCoverageTable.addCell(percentageTable(ListMethods.methodsCount, ListMethods.methods2.size()));
			}
			document.add(methodCoverageTable);

			//Method Details
			document.add(new Paragraph("Details: ", H2Font));
			for(Path testClassPath: ListClasses.testFiles){
				document.add(new Paragraph("    "+testClassPath.getFileName(), H3Font));

				PdfPTable methodNamesTable = new PdfPTable(2);
				methodNamesTable.addCell(new Phrase("Test Class' Methods", tableHeaderFont));
				methodNamesTable.addCell(new Phrase("Main Class' Methods", tableHeaderFont));

				ListMethods.getTestMethods(testClassPath); //get methods of test class
				ListMethods.getMainMethods(repoPath, testClassPath); //get methods of main class

				methodNamesTable(methodNamesTable);
				document.add(methodNamesTable);
			}

			document.newPage();
		}     
		document.close();
	}

	private PdfPTable percentageTable(int a, int b){
		PdfPTable bigPercentage = new PdfPTable(new float[] { 1, 3 });
		int size = b;
		if (b==0){
			size = 1;
		}
		PdfPTable percentage = new PdfPTable(size);
		PdfPCell cell= new PdfPCell();  

		//cell.setFixedHeight(30);

		float percent = ((float) a / (float) b) * 100;
		String newValue = Integer.toString((int)percent);

		//String newValue = Double.toString(Math.floor(percent));
		//int percent1 = (a/b) *100;

		cell = new PdfPCell(new Phrase(newValue+"%", tableContentFont));
		cell.setBorder(Rectangle.NO_BORDER);
		bigPercentage.addCell(cell);


		for(int aw = 0; aw < a; aw++){
			cell = new PdfPCell(new Phrase(""));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setBackgroundColor(BaseColor.GREEN);
			percentage.addCell(cell);
		}

		for(int aw = 0; aw < b-a; aw++){
			cell = new PdfPCell(new Phrase(""));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setBackgroundColor(BaseColor.RED);
			percentage.addCell(cell);
		}
		cell = new PdfPCell(percentage);
		cell.setBorder(Rectangle.NO_BORDER);
		bigPercentage.addCell(cell);

		return bigPercentage;
	}

	private void methodNamesTable(PdfPTable outerTable) {
		PdfPTable mainMathodsTable = new PdfPTable(1);
		PdfPTable testMethodsTable = new PdfPTable(1);
		PdfPCell cell;

		//System.out.println(listClasses.getClassCount());
		for(int aw = 0; aw < ListMethods.methodsCount; aw++){
			cell = new PdfPCell(new Phrase(""+ListMethods.methods.get(aw), tableContentFont));
			cell.setBorder(Rectangle.NO_BORDER);
			mainMathodsTable.addCell(cell);
		}
		outerTable.addCell(mainMathodsTable);

		for(int aw = 0; aw < ListMethods.methods2.size(); aw++){
			cell = new PdfPCell(new Phrase(""+ListMethods.methods2.get(aw), tableContentFont));
			cell.setBorder(Rectangle.NO_BORDER);
			testMethodsTable.addCell(cell);
		}
		outerTable.addCell(testMethodsTable);
	}

	private void classNamesTable(PdfPTable outerTable) {
		PdfPTable mainClassesTable = new PdfPTable(1);
		PdfPTable testClassesTable = new PdfPTable(1);
		PdfPCell cell;

		//System.out.println(listClasses.getClassCount());
		for(int aw = 0; aw < ListClasses.getClassCount(); aw++){
			cell = new PdfPCell(new Phrase(""+ListClasses.files.get(aw).getFileName(), tableContentFont));
			cell.setBorder(Rectangle.NO_BORDER);
			mainClassesTable.addCell(cell);
		}
		outerTable.addCell(mainClassesTable);

		for(int aw = 0; aw < ListClasses.getTestClassCount(); aw++){
			cell = new PdfPCell(new Phrase(""+ListClasses.testFiles.get(aw).getFileName(), tableContentFont));
			cell.setBorder(Rectangle.NO_BORDER);
			testClassesTable.addCell(cell);
		}
		outerTable.addCell(testClassesTable);
	}
}