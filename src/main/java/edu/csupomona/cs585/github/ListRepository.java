package edu.csupomona.cs585.github;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.cli.MavenCli;
import org.eclipse.jgit.api.Git;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;

public class ListRepository {

	static GitHub github;
	static GHUser user;

	public static List<Path> clonedRepos = new ArrayList<>();

	public static void getRepository(String userName) throws Exception{
		github = GitHub.connectAnonymously();
		user = github.getUser(userName);

		try {
			//STEP 1: List Repos
			for (GHRepository repository : user.listRepositories()) {
				String serverRepPath = "/Users/nada/Desktop/CS585Project/"+userName+"/"+repository.getName();


				if(repository.getLanguage().equals("Java")){
					//STEP 2: Clone Repos
					System.out.println("1| CLONING   " + repository.getFullName());
					Git.cloneRepository().setURI(repository.getSvnUrl()).setDirectory(new File(serverRepPath)).call();
					//System.out.println("end cloning " + repository.getFullName() + " -->");

					//STEP 3: Compile Repos if pom.xml exists		
					File pomFile = new File(serverRepPath+"/pom.xml");
					if(pomFile.exists()){ 
						Path clonedRepoPath = Paths.get(serverRepPath);
						clonedRepos.add(clonedRepoPath);
						System.out.println("2| COMPILING   " + clonedRepoPath.getFileName());
						compileRepo(serverRepPath);
					}
					else if(!pomFile.exists()){
						System.out.println("   No pom.xml file. Can not compile");
					}

				}
			}

		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			//System.out.println("ERROR: "+e);
		}
	}

	public static void compileRepo(String repoPath) {	
		try{
			//System.out.println("COMPILING   " + repoPath.toString());
			MavenCli cli = new MavenCli();
			cli.doMain(new String[]{"clean", "install"}, repoPath, System.out, System.out);
		}
		catch(Exception e){
			System.out.println("Compilation Error: " + e );
		}
	} 
}
