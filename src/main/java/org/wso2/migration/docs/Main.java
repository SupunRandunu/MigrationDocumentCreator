package org.wso2.migration.docs;

import java.io.IOException;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHRef;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

public class Main {
    public static void main(String[] args) throws IOException {
        DownloadMigrationRepository.downloadMigrationRepo("https://github.com/SupunRandunu/migration-docs","doc","............token.............");
        testSomeMethod();


    }

    public static void testSomeMethod() throws IOException
    {
        GitHub github = GitHub.connectUsingPassword("SupunRandunu", "..............token.............");
        GHRepository repo = github.getRepository("SupunRandunu" + "/" + "migration-docs");
        // get current branch
        //GHRepository ghRepo = github.getRepository("REPOSITORY");
       // GHContent content = repo.getFileContent("/bower.json");


        GHRef ref = repo.getRef("heads/" + "main");
        GHCommit latestCommit = repo.getCommit(ref.getObject().getSha());
        System.out.println("Latest Commit ID"+latestCommit.getSHA1());
//        GHTreeBuilder treeBuilder = repo.createTree().baseTree(latestCommit.getTree().getSha());
//        addFilesToTree(treeBuilder, baseDirectory, baseDirectory);
    }
}
