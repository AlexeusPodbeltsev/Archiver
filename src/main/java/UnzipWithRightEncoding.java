import java.io.*;

import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipWithRightEncoding{
    public static final String OS = System.getProperty("os.name");
    public static final String WORKING_DIRECTORY = System.getProperty("user.dir").concat("/");


    public static void main(String[] args) {

        try {
            unzipWithRightEncode(args[0]);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void unzipWithRightEncode(String path) throws IOException {
        if (path != null && path.matches(".+\\.zip$")) {
            try (ZipInputStream zin = new ZipInputStream(
                    new FileInputStream(WORKING_DIRECTORY.concat(path)), Charset.forName("cp866"))) {
                ZipEntry entry;
                String name;
                File file;
                while ((entry = zin.getNextEntry()) != null) {
                    name = entry.getName();

                    if (entry.isDirectory()) {
                        file = new File(WORKING_DIRECTORY.concat(name));
                        boolean created = file.mkdir();
                        if (created)
                            System.out.println("Folder has been created");
                    } else {
                        File newFile = new File(WORKING_DIRECTORY.concat(name));
                        boolean created = newFile.createNewFile();
                        if (created) {
                            System.out.println("File has been created");
                            BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(newFile));
                            for (int i = zin.read(); i != -1; i = zin.read()) {
                                fos.write(i);
                            }
                            fos.flush();
                            zin.closeEntry();
                            fos.close();
                        }
                    }
                }
            }
        } else throw new IllegalArgumentException();
    }
}
