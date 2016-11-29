package edu.csupomona.cs585.github;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ListClassesTest {
	File f;

	@Before
	public void setUp() throws URISyntaxException{
		URL myLocation = ListMethodsTest.class.getProtectionDomain().getCodeSource().getLocation();
		f =new File(myLocation.toURI());
	}

	@Test
	public void testGetClasses() throws ClassNotFoundException, IOException {
		ListClasses.getClasses(f.toPath());
		Assert.assertEquals(0, ListClasses.getClassCount());
		assert(ListClasses.getTestClassCount()>0);
	}

	@Test
	public void testGetClassName() {
		String ff = f.toPath()+"/edu/csupomona/cs585/github/ListClassesTest.class";
		String myName = ListClasses.getClassName(Paths.get(ff));
		Assert.assertEquals("ListClassesTest", myName);
	}

	@Test
	public void testGetClassCount() throws ClassNotFoundException, IOException {
		ListClasses.getClasses(f.toPath());

		Assert.assertEquals(0, ListClasses.getClassCount());
	}

	@Test
	public void testGetTestClassCount() throws ClassNotFoundException, IOException {
		ListClasses.getClasses(f.toPath());

		assert(ListClasses.getTestClassCount()>0);
	}

	@Test
	public void testloadClass() throws URISyntaxException, IOException {
		URL myLocation = ListMethodsTest.class.getProtectionDomain().getCodeSource().getLocation();
		File f=new File(myLocation.toURI());

		String ff = f.toPath()+"/edu/csupomona/cs585/github/ListClassesTest.class";

		Class c = ListClasses.loadClass(Paths.get(ff));

		Assert.assertNotNull(c);
	}
}
