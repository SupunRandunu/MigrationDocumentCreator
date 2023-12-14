package org.wso2.migration.docs.config;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.wso2.migration.docs.GitManagementUtil;
import org.wso2.migration.docs.util.Constants;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class YAMLConfigReader {
    private static final Logger logger = LogManager.getLogger(GitManagementUtil.class);
    public YAMLConfig readYAMLConfig(){
        try {

            FileInputStream configYAML = new FileInputStream(Constants.configFile);

            Yaml yaml = new Yaml();

            Map<String, Object> config = yaml.load(configYAML);

            YAMLConfig yamlConfig = new YAMLConfig();

            Map<String, Object> git = (Map<String, Object>) config.get("git");

            yamlConfig.setToken( (String) git.get("token"));
            yamlConfig.setRepository( (String) git.get("repository"));
            yamlConfig.setDirectory( (String) git.get("directory"));
            yamlConfig.setOrganization( (String) git.get("organization"));
            yamlConfig.setEnable((Boolean) git.get("enable"));



            Map<String, Object> doc = (Map<String, Object>) config.get("doc");

            yamlConfig.setSource( (String) doc.get("source"));
            yamlConfig.setTarget( (String) doc.get("target"));
            yamlConfig.setProduct( (String) doc.get("product"));
            yamlConfig.setDependency( (String) doc.get("dependency"));
            yamlConfig.setAttachmentPrefixes((List<String>) doc.get("attachmentPrefixes"));

            logger.info("Read the configurations from the config.yaml successfully.");
            logger.info(yamlConfig.toString());

            return yamlConfig;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
