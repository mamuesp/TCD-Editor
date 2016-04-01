package Editor;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * Part of project: TCD-Editor
 * <p>
 * Created by M. Mueller-Spaeth on 17.03.16.
 *
 * @version ${Version}
 */
public class Utils {
    private static final Logger log = Logger.getLogger("MainLogger");


    public static ImageView copyImage(Button in) {

        ImageView srcImageView = (ImageView) in.getGraphic();

        return copyImage(srcImageView);
    }

    public static ImageView copyImage(ImageView srcImageView) {

        ImageView destImageView = null;
        new ImageView();

        if (srcImageView != null) {
            destImageView = new ImageView();
            //copying sourceImage
            javafx.scene.image.Image srcImage = srcImageView.getImage();
            destImageView.setImage(SwingFXUtils.toFXImage(SwingFXUtils.fromFXImage(srcImage, null), null));
            destImageView.setId(generateId());
        }
        return destImageView;
    }

    public static String generateId() {
        java.util.Date now = new java.util.Date();
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(1000);
        long nowTS = ((long) System.currentTimeMillis() * 1000) + randomInt;

        return Long.toString(nowTS);
    }

    public static File getControlsFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    public static void setControlsFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());
        } else {
            prefs.remove("filePath");
        }
    }

    public static BufferedImage decodeToImage(String imageString) {

        BufferedImage image = null;
        byte[] imageByte;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();

            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }

    public static BufferedImage decompressAndDecode(String imageString) {

        BufferedImage image = null;
        byte[] imageByte;
        byte[] comprByte;
        try {
            comprByte = imageString.getBytes();
            BASE64Decoder decoder = new BASE64Decoder();
            comprByte = decoder.decodeBuffer(imageString);
            imageByte = decompress(comprByte);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    public static String compressAndEncode(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();
            byte[] comprBytes = compress(imageBytes);

            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(comprBytes);

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }

    public static byte[] compress(byte[] data) throws IOException {

        Deflater deflater = new Deflater();
        deflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        deflater.finish();

        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer); // returns the generated code... index
            outputStream.write(buffer, 0, count);
        }

        outputStream.close();
        byte[] output = outputStream.toByteArray();

        log.info("Original: " + data.length / 1024 + " Kb");
        log.info("Compressed: " + output.length / 1024 + " Kb");

        return output;
    }

    public static byte[] decompress(byte[] data) throws IOException, DataFormatException {

        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];

        while (!inflater.finished()) {
            int count = inflater.inflate(buffer);
            outputStream.write(buffer, 0, count);
        }

        outputStream.close();

        byte[] output = outputStream.toByteArray();
        log.info("Original: " + data.length);
        log.info("Decompressed: " + output.length);

        return output;

    }

    public static boolean checkExists(File f)  {
        try {
            byte[] buffer = new byte[4];
            InputStream is = new FileInputStream(f);
            if (is.read(buffer) != buffer.length) {
                // do something
            }
            is.close();
            return true;
        } catch (java.io.IOException ex) {
            ex.printStackTrace();
        // "/Volumes/Fast SSD/Develop/Embedded/TCD-Editor/resources/defaults/Editor.Items.TcdButton-defaults.xml"
        }
        return false;
    }
}
