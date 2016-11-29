package edu.csupomona.cs585.github;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ListMethods extends ListClasses {

	static int methodsCount;
	static int methodsCoverage;
	static List<String> methods = new ArrayList<>();
	static List<String> methods2 = new ArrayList<>();

	public static void getTestMethods(Path classPath) throws IOException {
		try{
			methods = new ArrayList<>();
			Class<?> clazz;
			clazz = loadClass(classPath);
			String className = getClassName(classPath);

			for (Method method : clazz.getDeclaredMethods()) {
				if(className.endsWith("Test")){
					if (method.getName().startsWith("test")){
						methods.add(method.getName());
						//System.out.println(method);
					}
				}
				else{
					methods.add(method.getName());
				}
			}
			methodsCount = methods.size();
		} catch (NoClassDefFoundError e) {
			System.out.println("ERROR: " + e);
			//http://stackoverflow.com/questions/15144601/noclassdeffounderror-when-loading-classes-with-classloader-recursively
		}

	}

	//needs refining ... extending listClass
	public static void getMainMethods(Path dir, Path testClassPath) throws IOException, ClassNotFoundException {	
		DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
		for (Path entry : stream) {
			if (Files.isDirectory(entry)) {
				getMainMethods(entry, testClassPath);
			}
			else if(entry.toString().endsWith(".class")){
				String testClassName = getClassName(testClassPath);
				String className = getClassName(entry);

				if(className.equals(testClassName.substring(0, testClassName.length() - 4)) ){
					//System.out.println("here");
					methods2 = new ArrayList<>();
					Class<?> clazz = loadClass(entry);
					for (Method method : clazz.getDeclaredMethods()) {
						methods2.add(method.getName());
					}
				}
			}
		}
	}

	public static List<String> getMethods(){
		return methods;
	}

	public static int getMethodsCount(){
		return methodsCount;
	}
}
