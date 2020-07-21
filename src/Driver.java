import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

public class Driver implements Runnable {
    FileFilter filter = new FileFilter() {
        private final String[] extensions = new String[] {
                "png", "jpeg", "jpg", "raw", "bpm", "gif"
        };

        @Override
        public boolean accept(File file) {
            if(file.isDirectory()) return  true;

            for (int i = 0; i < extensions.length; ++i) {
                String extension = extensions[i];

                if(file.getName().toLowerCase().endsWith(extension)) return true;
            }

            return false;
        }
    };

    private final String[] args;
    private Hashtable<String, File> photos = new Hashtable<>();
    private MessageDigest digest;

    public Driver(String[] args) {
        this.args = args;

        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }

    @Override
    public void run() {
        if(args.length == 0) {
            throw new IllegalArgumentException("You must specify the source directory!");
        }

        File output = new File(System.getProperty("user.home") + File.separator + "output");

        if(output.exists() == false) {
            output.mkdirs();
        }

        System.out.println("Prewarming...");

        Hashtable<String, File> prewarm = new Hashtable<>();

        this.locateFiles(output);

        if(photos.size() > 0) {
            prewarm = this.photos;
            System.out.println("Loaded: " + photos.size() + " photos...");

            this.photos = new Hashtable<>();
        }

        String sourceDirectory = args[0];

        File root = new File(sourceDirectory);

        System.out.println("Recursively searching for images...");
        this.locateFiles(root);

        if(this.photos.size() > 0) {
            Hashtable<String, File> temp = new Hashtable<>();

            Enumeration<String> checksums = this.photos.keys();

            while (checksums.hasMoreElements()) {
                String checksum = checksums.nextElement();

                if(prewarm.containsKey(checksum) == false) {
                    temp.put(checksum, this.photos.get(checksum));
                }
            }

            this.photos = temp;
        }

        if(this.photos.size() == 0) {
            System.out.println("No unique files have been found...");
            System.exit(0);
        }

        System.out.println("Found: " + photos.size() + " unique photos...");

        Collection<File> values = this.photos.values();

        Iterator<File> iterator = values.iterator();

        // TODO: Loop through and save all the photos...

        int i = 0;

        int max_characters = 0;

        while (iterator.hasNext()) {
            File file = iterator.next();

            String fileName = file.getName();

            String minified = fileName.substring(Math.max(fileName.length() - 50, 0));

            String current = String.format("\r%-50s ", minified);

            current += progressPercentage(i++ + 1, values.size());

            max_characters = Math.max(max_characters, current.length());

            System.out.print(current);

            try {
                byte[] data = Files.readAllBytes(file.toPath());

                String target = output.getPath() + File.separator + file.getName();

                File newFile = new File(target);

                while (newFile.exists()) {
                    digest.update(Files.readAllBytes(newFile.toPath()));

                    byte[] hash = digest.digest();

                    String checksumA = DatatypeConverter.printHexBinary(hash);

                    digest.update(data);
                    hash = digest.digest();

                    String checksumB = DatatypeConverter.printHexBinary(hash);

                    if(checksumA.equals(checksumB) == true) {
                        newFile = null;
                        break;
                    }

                    target = output.getPath() + File.separator + "_" + newFile.getName();

                    newFile = new File(target);
                }

                if(newFile == null) continue;

                newFile.createNewFile();

                FileOutputStream stream = new FileOutputStream(newFile);

                stream.write(data);

                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String done = String.format("\r%-" + max_characters +"s", "Done");

        System.out.println(done);
    }

    private void locateFiles(File root) {
        File[] list = root.listFiles(filter);

        for (int i = 0; i < list.length; ++i) {
            File file = list[i];

            if(file.isDirectory()) {
                this.locateFiles(file);

                continue;
            }

            try {
                digest.update(Files.readAllBytes(file.toPath()));
                byte[] rawHash = digest.digest();
                String checksum = DatatypeConverter.printHexBinary(rawHash);

                if(this.photos.containsKey(checksum) == true) continue;

                this.photos.put(checksum, file);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public static String progressPercentage(int remain, int total) {
        if (remain > total) {
            throw new IllegalArgumentException();
        }
        int maxBareSize = 10; // 10unit for 100%
        int remainProcent = ((100 * remain) / total) / maxBareSize;
        char defaultChar = '-';
        String icon = "*";
        String bare = new String(new char[maxBareSize]).replace('\0', defaultChar) + "]";
        StringBuilder bareDone = new StringBuilder();
        bareDone.append("[");
        for (int i = 0; i < remainProcent; i++) {
            bareDone.append(icon);
        }
        String bareRemain = bare.substring(remainProcent, bare.length());

        String result = bareDone + bareRemain + " " + remainProcent * 10 + "%";

        return result;
    }
}
