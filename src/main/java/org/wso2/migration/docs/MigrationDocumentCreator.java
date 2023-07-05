package org.wso2.migration.docs;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.wso2.migration.docs.config.YAMLConfig;
import org.wso2.migration.docs.config.YAMLConfigReader;
import org.wso2.migration.docs.json.models.DependencyProduct;
import org.wso2.migration.docs.json.models.MigrationDocuments;
import org.wso2.migration.docs.json.models.MigrationResource;
import org.wso2.migration.docs.json.models.Product;
import org.wso2.migration.docs.util.Constants;

public class MigrationDocumentCreator {
    private static final Logger logger = LogManager.getLogger(MigrationDocumentCreator.class);

    public static void main(String[] args) throws IOException {

        logger.info("........................................................");
        logger.info("........................................................");
        logger.info("...........Starting Migration Document Creator..........");
        logger.info("........................................................");

        logger.info("........................................................");

        YAMLConfig yamlConfig =new YAMLConfigReader().readYAMLConfig();

        logger.info("Creating migration document artifact from "
                +yamlConfig.getProduct()+" "+yamlConfig.getSource()+" to "+ yamlConfig.getTarget());


        GitManagementUtil gitManagementUtil = new GitManagementUtil();
        DirectoryManagementUtil directoryManagementUtil = new DirectoryManagementUtil();

        if(new File(String.valueOf("./"+yamlConfig.getDirectory())).exists()){
            if(!gitManagementUtil.getLastLocalCommitID(yamlConfig.getDirectory())
                    .equals(gitManagementUtil.
                            getLastRemoteCommitID(
                                    yamlConfig.getOrganization(),
                                    yamlConfig.getDirectory(),
                                    yamlConfig.getToken()))){
                directoryManagementUtil.deleteDirectory("./"+yamlConfig.getDirectory());
                gitManagementUtil
                        .downloadMigrationRepo(yamlConfig.getRepository(),yamlConfig.getDirectory(),yamlConfig.getToken());
            }
        } else {
            gitManagementUtil
                    .downloadMigrationRepo(yamlConfig.getRepository(),yamlConfig.getDirectory(),yamlConfig.getToken());
        }


        try {
            JSONParser jsonParser = new JSONParser();
            String migrationArtifact=jsonParser.parse(new FileReader(Constants.migrationArtifactsJson)).toString();
            JSONObject jsonObject = new JSONObject(migrationArtifact);
            ObjectMapper objectMapper = new ObjectMapper();
            MigrationDocuments migrationDocuments= objectMapper.readValue( jsonObject.toString(), MigrationDocuments.class);


            String basePath = yamlConfig.getProduct()+"-"+yamlConfig.getSource()+"-"+yamlConfig.getTarget()+"-"+"Migration";

            for (Product product : migrationDocuments.getProduct()){

                if(product.getProduct().equals(yamlConfig.getProduct())
                        && product.getSource().equals(yamlConfig.getSource())
                        && product.getTarget().equals(yamlConfig.getTarget())){
                    for(MigrationResource migrationResource: product.getMigrationResources()){
                        new DirectoryManagementUtil().createDirectories(basePath,migrationResource.getTargetFolder());
                        new FileManagementUtil().copyFile(basePath,migrationResource.getSourcePath(),migrationResource.getTargetFolder());
                    }
                    for(DependencyProduct dependencyProduct: product.getDependencyProducts()){
                        if(!dependencyProduct.getProduct().equals("")){
                            for (MigrationResource migrationResource:dependencyProduct.getMigrationResources()){
                                new DirectoryManagementUtil().createDirectories(basePath,migrationResource.getTargetFolder());
                                new FileManagementUtil().copyFile(basePath,migrationResource.getSourcePath(),migrationResource.getTargetFolder());
                            }
                        }
                    }
                    directoryManagementUtil.zipDirectory(basePath);
                    directoryManagementUtil.deleteDirectory(basePath);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("........................................................");
        logger.info("........................................................");
        logger.info(".........Completing Migration Document Creator..........");
        logger.info("........................................................");
        logger.info("........................................................");
    }
}
