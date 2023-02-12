/*
 * Decompiled with CFR 0.150.
 */
package qwezxc.asd.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
    private List<String> filesList = new LinkedList<String>();

    public void addFiles(File node) {
        if (node.isFile()) {
            this.filesList.add(node.toString());
        }
        if (node.isDirectory()) {
            for (String subFile : node.list()) {
                this.addFiles(new File(node, subFile));
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void exportZip(String exportFileName, String[] delete) {
        if (!new File(exportFileName).exists()) {
            new File(exportFileName).getParentFile().mkdirs();
            try {
                new File(exportFileName).createNewFile();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        byte[] buffer = new byte[1024];
        FileOutputStream fileOutputStream = null;
        ZipOutputStream zipOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(exportFileName);
            zipOutputStream = new ZipOutputStream(fileOutputStream);
            FileInputStream in = null;
            Iterator<String> iterator = this.filesList.iterator();
            while (iterator.hasNext()) {
                String file;
                String fileName = file = iterator.next();
                for (String rep : delete) {
                    if (!file.startsWith(rep)) continue;
                    fileName = file.replace(rep, "");
                    break;
                }
                ZipEntry zipEntry = new ZipEntry(fileName);
                zipOutputStream.putNextEntry(zipEntry);
                try {
                    int length;
                    in = new FileInputStream(file);
                    while ((length = in.read(buffer)) > 0) {
                        zipOutputStream.write(buffer, 0, length);
                    }
                }
                finally {
                    in.close();
                }
            }
            zipOutputStream.closeEntry();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                zipOutputStream.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

