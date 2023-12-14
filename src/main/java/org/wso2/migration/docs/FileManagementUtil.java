package org.wso2.migration.docs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.wso2.migration.docs.config.YAMLConfig;
import org.wso2.migration.docs.config.YAMLConfigReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileManagementUtil {

    private static final Logger logger = LogManager.getLogger(FileManagementUtil.class);

    YAMLConfig yamlConfig =new YAMLConfigReader().readYAMLConfig();

    public void copyFile(String basePath, String sourcePath, String destinationPath){
        Path source = new File(sourcePath).toPath();
        source = filterLatestAttachments(source);


        try {

            Path destination = new File("./"+basePath+"/"+destinationPath+"/"+source.getFileName()).toPath();
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);

            logger.info("File "+source.getFileName()+" copied to the path "+destination+" successfully");
        } catch (IOException e) {

            logger.error("An error occurred while copying the file: "+source.getFileName()+" " + e);
            System.exit(0);
        }
    }

    public Path filterLatestAttachments(Path path){
        String fileName = path.getFileName().toString();
        Path attachmentPath = null;
        logger.info("Start finding the attachment: " + fileName);

        if (fileName.toLowerCase().endsWith(".zip")||fileName.toLowerCase().endsWith(".jar")) {

            Path directoryPath = path.getParent();
            File directory = new File(String.valueOf(directoryPath).substring(1));
            File[] matchingFiles = new File[0];

            List<String> attachmentprefixes = yamlConfig.getAttachmentPrefixes();
            ArrayList<String> fileNameInDirectory = new ArrayList<>();
            for(String attachmentprefixe: attachmentprefixes){

                if(fileName.startsWith(attachmentprefixe)){
                    File resourcePath = new File(System.getProperty("user.dir")+ directory);
                    FilenameFilter filter = new FilenameFilter() {
                        public boolean accept(File dir, String name) {
                            if(name.contains(attachmentprefixe)){

                                fileNameInDirectory.add(name);
                                return true;
                            }
                            return false;
                        }
                    };

                    resourcePath.list(filter);
                }
            }
            logger.info("Filtered the latest migration resources: " + fileNameInDirectory.get(0));
            attachmentPath  = new File(String.valueOf(directoryPath)+"/"+fileNameInDirectory.get(0)).toPath();
            System.out.println(attachmentPath);
            return  attachmentPath;
        } else {
            System.out.println(path);
            System.out.println(path);
            return path;
        }
    }

    public void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
        logger.info("Zipped the migration document : "+fileName +" successfully");
    }
}
