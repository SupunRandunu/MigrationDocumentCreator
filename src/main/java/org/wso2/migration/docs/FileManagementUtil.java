package org.wso2.migration.docs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileManagementUtil {

    private static final Logger logger = LogManager.getLogger(FileManagementUtil.class);
    public void copyFile(String basePath, String sourcePath, String destinationPath){
        Path source = new File(sourcePath).toPath();
        try {

            Path destination = new File("./"+basePath+"/"+destinationPath+"/"+source.getFileName()).toPath();
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);

            logger.info("File "+source.getFileName()+" copied to the path "+destination+" successfully");
        } catch (IOException e) {

            logger.error("An error occurred while copying the file: "+source.getFileName()+" " + e);
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
