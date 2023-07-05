package org.wso2.migration.docs;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileOperations {


    static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
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
    }
    public void copyAPIMFiles(Package aPackage,String directoryName) throws IOException, IllegalAccessException {


//        File apimResource = new File( aPackage.getApim().getApimAttachments().getApimResource());
//        File isResource = new File( aPackage.getApim().getApimAttachments().getIsResource());
//        File isJar = new File( aPackage.getApim().getApimAttachments().getIsJar());
//        System.out.println(directoryName);
//
//        File resourcesTarget = new File(directoryName+"/attachments");
//
//        FileUtils.copyFileToDirectory(isResource, resourcesTarget);
//        FileUtils.copyFileToDirectory(isJar, resourcesTarget);
//        FileUtils.copyFileToDirectory(apimResource, resourcesTarget);
//
//        File upgradingGuidelines = new File( aPackage.getApim().getApimMigrationDocuments().getUpgradingGuidelines());
//        File whatHasChanged = new File( aPackage.getApim().getApimMigrationDocuments().getWhatHasChanged());
//        File upgradingDoc = new File( aPackage.getApim().getApimMigrationDocuments().getUpgradingDoc());
//
//        File upgradingDocTarget = new File(directoryName+"/migration-documents/api-manager");
//
//        FileUtils.copyFileToDirectory(upgradingGuidelines, upgradingDocTarget);
//        FileUtils.copyFileToDirectory(whatHasChanged, upgradingDocTarget);
//        FileUtils.copyFileToDirectory(upgradingDoc, upgradingDocTarget);
//
//        File dbScript = new File( aPackage.getApim().getDbscript());
//
//        File scriptTarget = new File(directoryName+"/dbscripts");
//
//        FileUtils.copyDirectory(dbScript, scriptTarget);
//
//
//



       // Read more: https://javarevisited.blogspot.com/2022/12/how-to-iterate-over-jsonobject-in-java.html#ixzz80QXoZAMs
        //Read more: https://javarevisited.blogspot.com/2022/12/how-to-iterate-over-jsonobject-in-java.html#ixzz80QWncyvW



    }

    public void copyISasKMFiles(Package aPackage, String directoryName) throws IOException, IllegalAccessException {

//
//        File apimResource = new File( aPackage.getApim().getApimAttachments().getApimResource());
//
//
//        File resourcesTarget = new File(directoryName+"/attachments");
//
//
//        FileUtils.copyFileToDirectory(apimResource, resourcesTarget);
//
//        File upgradingGuidelines = new File( aPackage.getApim().getApimMigrationDocuments().getUpgradingGuidelines());
//        File whatHasChanged = new File( aPackage.getApim().getApimMigrationDocuments().getWhatHasChanged());
//        File upgradingDoc = new File( aPackage.getApim().getApimMigrationDocuments().getUpgradingDoc());
//
//        File upgradingDocTarget = new File(directoryName+"/migration-documents/api-manager");
//
//        FileUtils.copyFileToDirectory(upgradingGuidelines, upgradingDocTarget);
//        FileUtils.copyFileToDirectory(whatHasChanged, upgradingDocTarget);
//        FileUtils.copyFileToDirectory(upgradingDoc, upgradingDocTarget);
//
//        File dbScript = new File( aPackage.getApim().getDbscript());
//
//        File scriptTarget = new File(directoryName+"/dbscripts");
//
//        FileUtils.copyDirectory(dbScript, scriptTarget);
//
//        File isConnector = new File( aPackage.getIsaskm().getIsaskmAttachments().getIsConnector());
//        File isResource = new File( aPackage.getApim().getApimAttachments().getIsResource());
//        File isJar = new File( aPackage.getApim().getApimAttachments().getIsJar());
//
//        File isResourceTarget= new File(directoryName+"/attachments/is-as-km");
//
//        FileUtils.copyFileToDirectory(isResource, isResourceTarget);
//        FileUtils.copyFileToDirectory(isJar, isResourceTarget);
//        FileUtils.copyFileToDirectory(isConnector, isResourceTarget);
//
//        File isAsKmDoc = new File( aPackage.getIsaskm().getIsaskmMigrationDocuments().getUpgradeIsAsKm());
//        File upgradingGuideline = new File( aPackage.getIsaskm().getIsaskmMigrationDocuments().getUpgradingGuidelines());
//
//        File isAsKMTarget= new File(directoryName+"/migration-documents/is-as-km");
//
//        FileUtils.copyFileToDirectory(isAsKmDoc, isAsKMTarget);
//        FileUtils.copyFileToDirectory(upgradingGuideline, isAsKMTarget);
//
//        File preparingForMigration = new File( aPackage.getIsaskm().getIsaskmMigrationDocuments().getPreparingForMigration());
//        File isMigration = new File( aPackage.getIsaskm().getIsaskmMigrationDocuments().getMigratingIs());
//        File userStoreMigration = new File( aPackage.getIsaskm().getIsaskmMigrationDocuments().getMigratingUserstore());
//        File whatHasChange = new File( aPackage.getIsaskm().getIsaskmMigrationDocuments().getWhatHasChange());
//        File migrationProcess = new File( aPackage.getIsaskm().getIsaskmMigrationDocuments().getMigrationProcess());
//
//        File isTarget= new File(directoryName+"/migration-documents/is-as-km/identity-server");
//
//        FileUtils.copyFileToDirectory(preparingForMigration, isTarget);
//        FileUtils.copyFileToDirectory(isMigration, isTarget);
//        FileUtils.copyFileToDirectory(userStoreMigration, isTarget);
//        FileUtils.copyFileToDirectory(whatHasChange, isTarget);
//        FileUtils.copyFileToDirectory(migrationProcess, isTarget);
//
//
    }
}
