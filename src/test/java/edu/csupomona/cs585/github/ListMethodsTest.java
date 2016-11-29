package edu.csupomona.cs585.github;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

public class ListMethodsTest {

	@Test
	public void testGetTestMethods() throws URISyntaxException, IOException, ClassNotFoundException {
		URL myLocation = ListMethodsTest.class.getProtectionDomain().getCodeSource().getLocation();
		File f=new File(myLocation.toURI());
		
		String ff = f.toPath()+"/edu/csupomona/cs585/github/ListMethodsTest.class";
		//System.out.println(ff);
		
		ListMethods.getTestMethods(Paths.get(ff));
		Assert.assertNotNull(ListMethods.methods);
	}
	
	@Test
	public void testGetMainMethods() throws ClassNotFoundException, IOException, URISyntaxException {
			URL myLocation = ListMethodsTest.class.getProtectionDomain().getCodeSource().getLocation();
			File f=new File(myLocation.toURI());
			
			String ff = f.toPath()+"/edu/csupomona/cs585/github/ListMethodsTest.class";
			//System.out.println(ff);
			
			ListMethods.getMainMethods(f.toPath(), Paths.get(ff));

			Assert.assertEquals(ListMethods.methods2.size(), 0);
	}
}
