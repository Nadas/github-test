package edu.csupomona.cs585.github;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ListClasses {

	private static int classCount;
	private static int testClassCount;

	public static List<Path> files = new ArrayList<>();
	public static List<Path> testFiles = new ArrayList<>();

	public static String getClassName(Path classPath){
		String className = classPath.getFileName().toString();
		className = className.substring(0, className.length()-6);
		
		return className;
	}
	
	public static void getClasses(Path dir) throws IOException, ClassNotFoundException {
		DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
		for (Path entry : stream) {
			if (Files.isDirectory(entry)) {
				getClasses(entry);
			}
			else if(entry.toString().endsWith(".class") && !entry.toString().endsWith("Test.class")){
				files.add(entry);
			}

			else if(entry.toString().endsWith("Test.class")){
				testFiles.add(entry);

			}
		}
		classCount = files.size();
		testClassCount = testFiles.size();
	}

	public static Class<?> loadClass(Path file) throws IOException{

		String str = file.toString();
		String Path = str.substring(0, str.indexOf("classes/")+8);

		URL myurl[] = { new URL("file:///"+Path) };
		URLClassLoader x = new URLClassLoader(myurl);

		String className = str.substring(str.indexOf("classes")+8, str.length()-6);
		className = className.replace('/', '.');
		Class<?> c = null;
		try {
			c = x.loadClass(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR: " + e);
		}
		return c;
}
	
	public static int getClassCount(){
		return classCount;
	}

	public static int getTestClassCount(){
		return testClassCount;
	}

}
