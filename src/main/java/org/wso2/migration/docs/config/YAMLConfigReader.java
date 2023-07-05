package org.wso2.migration.docs.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.wso2.migration.docs.DirectoryManagementUtil;
import org.wso2.migration.docs.util.Constants;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public class YAMLConfigReader {
    private static final Logger logger = LogManager.getLogger(YAMLConfigReader.class);
    public YAMLConfig readYAMLConfig(){
        try {

            FileInputStream fis = new FileInputStream(Constants.configFile);

            Yaml yaml = new Yaml();

            Map<String, Object> config = yaml.load(fis);

            YAMLConfig yamlConfig = new YAMLConfig();

            Map<String, Object> git = (Map<String, Object>) config.get("git");

            yamlConfig.setToken( (String) git.get("token"));
            yamlConfig.setRepository( (String) git.get("repository"));
            yamlConfig.setDirectory( (String) git.get("directory"));
            yamlConfig.setOrganization( (String) git.get("organization"));


            Map<String, Object> wso2 = (Map<String, Object>) config.get("wso2");

            yamlConfig.setSource( (String) wso2.get("source"));
            yamlConfig.setTarget( (String) wso2.get("target"));
            yamlConfig.setProduct( (String) wso2.get("product"));
            yamlConfig.setDependency( (String) wso2.get("dependency"));

            logger.info("Read the configurations from the config.yaml successfully.");

            return yamlConfig;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
