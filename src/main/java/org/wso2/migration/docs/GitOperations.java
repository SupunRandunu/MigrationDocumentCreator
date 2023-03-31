package org.wso2.migration.docs;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHRef;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.IOException;

public class GitOperations {
    public static String getLastCommitID(String username, String token, String repository, String branch) throws IOException {
        GitHub github = GitHub.connectUsingPassword(username, token);
        GHRepository repo = github.getRepository(username + "/" + repository);
        GHRef ref = repo.getRef("heads/" + branch);
        GHCommit latestCommit = repo.getCommit(ref.getObject().getSha());
        return latestCommit.getSHA1();
    }

}
