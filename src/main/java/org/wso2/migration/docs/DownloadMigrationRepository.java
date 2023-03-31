package org.wso2.migration.docs;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.Git;

import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;

public class DownloadMigrationRepository {
    public static void downloadMigrationRepo(String repositoryLink, String directory, String token){
        try {

            Git.cloneRepository().setURI(repositoryLink)
                    .setDirectory(new File(directory))
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider(token,""))
                    .call();

            System.out.println("Successfully downloaded the repository!");
        }
        catch(GitAPIException | JGitInternalException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
