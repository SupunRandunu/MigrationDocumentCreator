package org.wso2.migration.docs;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.File;
import java.io.IOException;

public class GitManagementUtil {
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
    public String getLastRemoteCommitID(String organization, String directory, String token) throws IOException {
        GitHub github = GitHub.connectUsingOAuth("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");
        GHRepository repo = github.getRepository("wso2-enterprise" + "/" + "migration-docs");

        GHCommit latestCommit = repo.listCommits().iterator().next();
        String latestCommitId = latestCommit.getSHA1();
        return latestCommitId;
    }
    public String getLastLocalCommitID(String directory) throws IOException {

        try (Repository repository = Git.open(new File("./"+directory)).getRepository()) {
            Ref head = repository.exactRef("HEAD");
            ObjectId lastCommitId = head.getObjectId();

            try (RevWalk revWalk = new RevWalk(repository)) {
                RevCommit lastCommit = revWalk.parseCommit(lastCommitId);
                String lastCommitIdStr = lastCommit.getId().getName();
                return lastCommitIdStr;
            }
        } catch (IOException e) {
            System.out.println("An error occurred while fetching commit information: " + e.getMessage());
        }
        return null;
    }
}
