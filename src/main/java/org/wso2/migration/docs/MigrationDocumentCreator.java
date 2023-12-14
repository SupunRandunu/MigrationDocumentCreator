package org.wso2.migration.docs;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
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
        if ( yamlConfig.getEnable()==true) {
            if (new File(String.valueOf("./" + yamlConfig.getDirectory())).exists()) {
                if (!gitManagementUtil.getLastLocalCommitID(yamlConfig.getDirectory())
                        .equals(gitManagementUtil.
                                getLastRemoteCommitID(
                                        yamlConfig.getOrganization(),
                                        yamlConfig.getDirectory(),
                                        yamlConfig.getToken()))) {
                    directoryManagementUtil.deleteDirectory("./" + yamlConfig.getDirectory());
                    gitManagementUtil
                            .downloadMigrationRepo(yamlConfig.getRepository(), yamlConfig.getDirectory(), yamlConfig.getToken());
                }
            } else {
                gitManagementUtil
                        .downloadMigrationRepo(yamlConfig.getRepository(), yamlConfig.getDirectory(), yamlConfig.getToken());
            }
        }

        try (FileReader fileReader = new FileReader(Constants.migrationArtifactsJson)) {
            JSONParser jsonParser = new JSONParser();
            String migrationArtifact = jsonParser.parse(fileReader).toString();
            JSONObject jsonObject = new JSONObject(migrationArtifact);
            ObjectMapper objectMapper = new ObjectMapper();
            MigrationDocuments migrationDocuments= objectMapper.readValue( jsonObject.toString(), MigrationDocuments.class);


            StringBuilder basePathBuilder = new StringBuilder();
            basePathBuilder.append(yamlConfig.getProduct())
                    .append("-").append(yamlConfig.getSource())
                    .append("-").append(yamlConfig.getTarget())
                    .append("-").append("Migration");
            String basePath = basePathBuilder.toString();
            boolean isProductFound= false;
            for (Product product : migrationDocuments.getProducts()){

                if(product.getProduct().equals(yamlConfig.getProduct())
                        && product.getSource().equals(yamlConfig.getSource())
                        && product.getTarget().equals(yamlConfig.getTarget())){
                    //logger.info(product.toString());

                    if(yamlConfig.getDependency()==null){
                        for(MigrationResource migrationResource: product.getMigrationResources()){
                            new DirectoryManagementUtil().createDirectories(basePath,migrationResource.getTargetFolder());
                            new FileManagementUtil().copyFile(basePath,migrationResource.getSourcePath(),migrationResource.getTargetFolder());
                        }
                    }else {
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
                    }
                    isProductFound= true;
                    directoryManagementUtil.zipDirectory(basePath);
                    directoryManagementUtil.deleteDirectory(basePath);
                }
            }
            if(!isProductFound){
                logger.info("Unable to find the provided product versions");
            }
        } catch (Exception e) {
            logger.error("Error occurred while creating Migration Document " + e);
        }
        logger.info("........................................................");
        logger.info("........................................................");
        logger.info(".........Completing Migration Document Creator..........");
        logger.info("........................................................");
        logger.info("........................................................");
    }
}
