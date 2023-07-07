package org.wso2.migration.docs;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class DirectoryManagementUtil {

    private static final Logger logger = LogManager.getLogger(DirectoryManagementUtil.class);
    public static void createDirectories(String basePath, String path) {

        File file = new File(basePath+"/"+path);
        if (!file.exists()) {
            if (file.mkdirs()) {
                logger.info("Migration directory path created: " + path);
            } else {
                logger.info("Failed to create migration path directory " + path);
            }
        } else {
            logger.info("Directory path already exists:: " + path);

        }

    }
    public void deleteDirectory(String directoryName) throws IOException {
        File file = new File(String.valueOf(directoryName));
        if (file.exists()) {
            try {
                FileUtils.deleteDirectory(file);
                logger.info("Directory deleted successfully: "+directoryName);
            } catch (Exception e) {
                logger.error("Failed to delete the directory: " +directoryName+" "+ e.getMessage());
            }
        } else {
            logger.info("Directory does not exist: "+directoryName);
        }
    }
    public void zipDirectory(String directoryName) throws IOException
    {
        FileOutputStream fos = new FileOutputStream(directoryName+".zip");
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        File fileToZip = new File(directoryName);
        new FileManagementUtil().zipFile(fileToZip, fileToZip.getName(), zipOut);
        zipOut.close();
        fos.close();
        logger.info("Directory zipped successfully: "+directoryName);
    }
}
