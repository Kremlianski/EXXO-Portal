package EXXOlib;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.image.BufferedImage;
//import java.awt.image.ImageObserver;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
//ImgInserter, ImgUpdater

public class IMGESLIB {

    static String r = "";

    public static String getType(String newLine) {
        String format;
        if (newLine.contains("PNG")) {
            format = "png";
        } else if (newLine.contains("GIF89")) {
            format = "gif";
        } else if (newLine.contains("BM")) {
            format = "bmp";
        } else if (newLine.contains("JFIF")) {
            format = "jpeg";
        } else {
            format = "wbmp";
        }
        return format;
    }

    public static String getFormat(byte[] bytes, String type) {

        byte[] header = java.util.Arrays.copyOfRange(bytes, 0, 10);
        bytes = null;
        String h = new String(header).trim();
        String format;
        if (type.endsWith("png")) {
            format = "png";
        } else if (type.endsWith("gif")) {
            format = "gif";
        } else if (type.endsWith("jpeg")) {
            format = "jpeg";
        } else if (type.endsWith("bmp")) {
            format = "bmp";
        } else if (h.contains("PNG")) {
            format = "png";
        } else if (h.contains("GIF89")) {
            format = "gif";
        } else if (h.contains("BM")) {
            format = "bmp";
        } else if (h.contains("JFIF")) {
            format = "jpeg";
        } else {
            format = "noresult";
        }

        return format;
    }

    public static BufferedImage toBufferedImage(Image image, int type, int trans) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        image = new ImageIcon(image).getImage();

        BufferedImage bimage = null;

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {

            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(
                    image.getWidth(null), image.getHeight(null), trans);
        } catch (HeadlessException e) {

        }

        if (bimage == null) {
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }

        Graphics g = bimage.createGraphics();

        g.drawImage(image, 0, 0, null);
        /*  while (1==1){
         try {
                Thread.sleep(300);
            } catch (InterruptedException e) {

            }
        if(bimage.getWidth()!=image.getWidth(null)||bimage.getHeight()!=image.getHeight(null)) continue;
        else break;
       
    }
  * 
         */
        g.dispose();

        return bimage;
    }

    public static byte[] cropResize(byte[] bytes, int width, int height, String format) throws IOException {
        ByteArrayInputStream bain = new ByteArrayInputStream(bytes);
        BufferedImage image = ImageIO.read(bain);
        int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType();
//int type=image.getType();
        int trans = image.getTransparency();
        int h = image.getHeight();
        int w = image.getWidth();
        int fh = height;
        int fw = width;
        int ch = 0;
        int cw = 0;
        Image newImage;
        boolean album = false;
        boolean normal = false;
        format = "png";
        if (h < w) {
            album = true;
        }
        if (h > 50 && w > 50) {
            if (!album) {
                if ((int) java.lang.Math.round(w * 100 / h) == 75) {
                    normal = true;
                }

                if (!normal) {
                    if ((int) java.lang.Math.round(w * 100 / h) > 75) {
                        //h<
                        ch = h;
                        cw = (int) java.lang.Math.round(ch * 3 / 4);
                        image = image.getSubimage(((int) java.lang.Math.round(w - cw) / 2), 0, cw, ch);

                    } else {
                        cw = w;
                        // h> 
                        ch = (int) java.lang.Math.round(cw * 4 / 3);
                        image = image.getSubimage(0, ((int) java.lang.Math.round(h - ch) / 2), cw, ch);
                    }
                }

            } else {
                if (w > fw) {
                    ch = h;
                    cw = (int) java.lang.Math.round(ch * 0.75);
                    image = image.getSubimage(((int) java.lang.Math.round(w - cw) / 2), 0, cw, ch);
                    //   if(h<147) mode=false;
                }
            }
        }
        newImage = Scalr.resize(image, Method.ULTRA_QUALITY, Scalr.Mode.FIT_TO_HEIGHT, fh);
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ImageIO.write(EXXOlib.IMGESLIB.toBufferedImage(newImage, type, trans), format, b);
        bytes = b.toByteArray();
        return bytes;
    }

    public static byte[] resizeLogo(byte[] bytes, String format) throws IOException {
        ByteArrayInputStream bain = new ByteArrayInputStream(bytes);
        BufferedImage image = ImageIO.read(bain);
        int type = image.getType();
        int trans = image.getTransparency();
        int fh = 90;
        int fw = -1;
        Image newImage;
        newImage = image.getScaledInstance(fw, fh, Image.SCALE_SMOOTH);

        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ImageIO.write(EXXOlib.IMGESLIB.toBufferedImage(newImage, type, trans), format, b);
        bytes = b.toByteArray();

        return bytes;
    }

    public static byte[] iconCropResize(byte[] bytes, int width, int height, String format) throws IOException {
        byte[] ba = null;
        try {
            ByteArrayInputStream bain = new ByteArrayInputStream(bytes);

            BufferedImage image = ImageIO.read(bain);
            bain.close();
            bytes = null;
            int type = image.getType();
            int trans = image.getTransparency();
            int h = image.getHeight();
            int w = image.getWidth();
            int fh = height;
            int fw = width;
            int ch = 0;
            int cw = 0;
            Image newImage;
            boolean album = false;
            boolean normal = false;
            format = "png";
            if (h < w) {
                album = true;
            }
            if (h > 50 || w > 50) {
                if (!album) {
                    if ((int) java.lang.Math.round(w / h) == 1) {
                        normal = true;
                    }

                    if (!normal) {
                        if (w / h > 1) {
                            ch = h;
                            cw = ch;
                            image = image.getSubimage(((int) java.lang.Math.round(w - cw) / 2), 0, cw, ch);

                        } else {
                            cw = w;
                            ch = cw;
                            image = image.getSubimage(0, ((int) java.lang.Math.round(h - ch) / 2), cw, ch);
                        }
                    }

                } else {
                    if (w > fw) {
                        ch = h;
                        cw = ch;
                        image = image.getSubimage(((int) java.lang.Math.round(w - cw) / 2), 0, cw, ch);

                    }
                }
            }

            BufferedImage im = Scalr.resize(image, Method.QUALITY, fh);
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ImageIO.write(im, format, b);
            ba = b.toByteArray();
            b.flush();
            b.close();
//bytes=b.toByteArray();     
            r = ba.length + "";
        } finally {
            return ba;
        }
    }

    public static byte[] galCropResize(byte[] bytes, int height, String format) throws IOException {
        ByteArrayInputStream bain = new ByteArrayInputStream(bytes);
        BufferedImage image = ImageIO.read(bain);
//int type=image.getType();
//int trans = image.getTransparency();
        int h = image.getHeight();
        int w = image.getWidth();
//int fh=height;
        int fh = ru.EXXO.LOGIN.Context.getGalsHeight();
        Method method = ru.EXXO.LOGIN.Context.getGalsSpead();
        int fw = (int) java.lang.Math.round(fh / 0.75);
        int ratio = h / w;
        format = "png";
        if (ratio >= 0.75) {
            if (h > fh) {

//Image newImage=image.getScaledInstance(-1, fh, Image.SCALE_SMOOTH);
//BufferedImage im = EXXOlib.IMGESLIB.toBufferedImage(newImage, type, trans);
                BufferedImage im = Scalr.resize(image, method, Scalr.Mode.FIT_TO_HEIGHT, fh);
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                ImageIO.write(im, format, b);
                bytes = b.toByteArray();
                b.flush();
                b.close();
//bytes=b.toByteArray();     

            }
        } else {
            if (w > fw) {
//Image newImage=image.getScaledInstance(fw, -1, Image.SCALE_SMOOTH);
//BufferedImage im = EXXOlib.IMGESLIB.toBufferedImage(newImage, type, trans);
                BufferedImage im = Scalr.resize(image, method, Scalr.Mode.FIT_TO_WIDTH, fw);
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                ImageIO.write(im, format, b);
                bytes = b.toByteArray();
                b.flush();
                b.close();
            }
        }
        return bytes;
    }

    public static byte[] simpleCropResize(byte[] bytes, int height, String format) throws IOException {
        ByteArrayInputStream bain = new ByteArrayInputStream(bytes);
        BufferedImage image = ImageIO.read(bain);
        int type = image.getType();
        int trans = image.getTransparency();
        int h = image.getHeight();
        int w = image.getWidth();
        format = "png";
        if (h > w) {

            BufferedImage im = Scalr.resize(image, Method.QUALITY, height);
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ImageIO.write(im, format, b);
            bytes = b.toByteArray();
            b.flush();
            b.close();
//bytes=b.toByteArray();     

        } else {

            BufferedImage im = Scalr.resize(image, Method.QUALITY, height);
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ImageIO.write(im, format, b);
            bytes = b.toByteArray();
            b.flush();
            b.close();
        }
        return bytes;
    }

    public static String iconTest() {
        return r;
    }

    public static byte[] preCropResize(byte[] bytes, int height, String format, int minHeight, int minWidth) throws IOException {
        ByteArrayInputStream bain = new ByteArrayInputStream(bytes);
        BufferedImage image = ImageIO.read(bain);
        int finalHeight = 0;
        int finalWidth = 0;

        int type = image.getType();
        int trans = image.getTransparency();
        int h = image.getHeight();
        int w = image.getWidth();
        format = "png";
        if (h > height) {
            int fh = height;
            int fw = (int) java.lang.Math.round(fh / 0.75);
            double ratio = w / h;
            if (ratio >= 0.75) {
                if (h > fh) {
                    BufferedImage im = Scalr.resize(image, Method.ULTRA_QUALITY, fh);
                    finalHeight = im.getHeight();
                    finalWidth = im.getWidth();
                    ByteArrayOutputStream b = new ByteArrayOutputStream();
                    ImageIO.write(im, format, b);
                    bytes = b.toByteArray();
                    b.flush();
                    b.close();
//bytes=b.toByteArray();     

                }
            } else {
                if (w > fw) {
                    BufferedImage im = Scalr.resize(image, Method.ULTRA_QUALITY, fw);
                    finalHeight = im.getHeight();
                    finalWidth = im.getWidth();
                    ByteArrayOutputStream b = new ByteArrayOutputStream();
                    ImageIO.write(im, format, b);
                    bytes = b.toByteArray();
                    b.flush();
                    b.close();
                }
            }
            if (finalHeight < minHeight || finalWidth < minWidth) {
                bytes = null;
            }

        } else if (w < minWidth || h < minHeight) {
            bytes = null;
        }
        return bytes;
    }

    public static byte[] photoCrop(byte[] bytes, int width, int height, int x, int y, String format) throws IOException {
        ByteArrayInputStream bain = new ByteArrayInputStream(bytes);
        BufferedImage image = ImageIO.read(bain);
        format = "png";
        image = image.getSubimage(x, y, width, height);
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ImageIO.write(image, format, b);
        return b.toByteArray();
    }
}
