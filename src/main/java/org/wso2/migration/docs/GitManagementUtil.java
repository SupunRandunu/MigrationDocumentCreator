package org.wso2.migration.docs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger logger = LogManager.getLogger(GitManagementUtil.class);
    public static void downloadMigrationRepo(String repositoryLink, String directory, String token){
        try {
            logger.info("Start downloading migration repository ");
            Git.cloneRepository().setURI(repositoryLink)
                    .setDirectory(new File(directory))
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider(token,""))
                    .call();

            logger.info("Migration repository downloaded successfully");
        }
        catch(GitAPIException | JGitInternalException e) {
            logger.error("Failed tp download migration repository : "+e);
        }
    }
    public String getLastRemoteCommitID(String organization, String directory, String token) throws IOException {
        GitHub github = GitHub.connectUsingOAuth(token);
        GHRepository repo = github.getRepository(organization+ "/" + directory);

        GHCommit latestCommit = repo.listCommits().iterator().next();
        String latestCommitId = latestCommit.getSHA1();
        logger.info("Retrieved the last commit ID from github : "+latestCommitId);
        return latestCommitId;
    }
    public String getLastLocalCommitID(String directory) throws IOException {

        try (Repository repository = Git.open(new File("./"+directory)).getRepository()) {
            Ref head = repository.exactRef("HEAD");
            ObjectId lastCommitId = head.getObjectId();

            try (RevWalk revWalk = new RevWalk(repository)) {
                RevCommit lastCommit = revWalk.parseCommit(lastCommitId);
                String lastCommitIdStr = lastCommit.getId().getName();
                logger.info("Retrieved the last commit ID from local repository : "+lastCommitIdStr);
                return lastCommitIdStr;
            }
        } catch (IOException e) {
            logger.error("Error while retrieving last commit ID from local repository : "+e);
        }
        return null;
    }
}
