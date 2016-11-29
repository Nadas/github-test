package edu.csupomona.cs585.github;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.jgit.api.Git;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;

public class Main {

	public static void main(String[] args) throws IOException  {
		GitHub github;
		GHUser user;

		String userName = "csupomona-cs585";
		github = GitHub.connectAnonymously();
		try {
			user = github.getUser(userName);
			for (GHRepository repository : user.listRepositories()) {
				String serverRepPath = "/Users/nada/Desktop/CS585Project/"+userName+"/"+repository.getName();


				if(repository.getLanguage().equals("Java")){
					//STEP 2: Clone Repos
					System.out.println("1| CLONING   " + repository.getFullName());


				}

			}
		} catch (NullPointerException e) { 
			// TODO Auto-generated catch block
			//System.out.println("ERROR: "+e);
		}

	}
}
