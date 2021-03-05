import java.util.zip.ZipEntry;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipInputStream;
import java.io.FileInputStream;
import java.io.File;
import java.util.List;

public class UnzipFiles {
	List<String> fileList;
    
    public void unZipIt(final String zipFile, final String outputFolder) {
        final byte[] buffer = new byte[1024];
        try {
            final File folder = new File(outputFolder);
            if (!folder.exists()) {
                folder.mkdir();
            }
            final ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
            for (ZipEntry zip = zis.getNextEntry(); zip != null; zip = zis.getNextEntry()) {
                final String fileName = zip.getName();
                File newFile = null;
                if (fileName.substring(fileName.length() - 4).equals(".txt")) {
                    newFile = new File(String.valueOf(outputFolder) + File.separator + fileName);
                    new File(newFile.getParent()).mkdirs();
                    final FileOutputStream fos = new FileOutputStream(newFile);
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                }
            }
            zis.closeEntry();
            zis.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
