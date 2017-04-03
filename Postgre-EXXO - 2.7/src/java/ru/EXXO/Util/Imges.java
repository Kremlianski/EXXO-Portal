package ru.EXXO.Util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;

public class Imges {

    public Img produceImg(byte[] img) throws IOException {
        ByteArrayInputStream bain = new ByteArrayInputStream(img);
        BufferedImage image = ImageIO.read(bain);
        int h = image.getHeight();
        int w = image.getWidth();
        Img IMG = new Img();
        int fh = ru.EXXO.LOGIN.Context.getGalsHeight();
        Scalr.Method method = ru.EXXO.LOGIN.Context.getGalsSpead();
        int fw = (int) java.lang.Math.round(fh / 0.75);
        int ratio = h / w;
        String format = "png";
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        IMG.setImg(img);
        IMG.setHeight(h);
        IMG.setWidth(w);
        if (ratio >= 0.75) {
            if (h > fh) {
                image = Scalr.resize(image, method, Scalr.Mode.FIT_TO_HEIGHT, fh);
                ImageIO.write(image, format, b);
                IMG.setImg(b.toByteArray());
                IMG.setHeight(image.getHeight());
                IMG.setWidth(image.getWidth());
                b.flush();
            }
        } else {
            if (w > fw) {
                image = Scalr.resize(image, method, Scalr.Mode.FIT_TO_WIDTH, fw);
                ImageIO.write(image, format, b);
                IMG.setImg(b.toByteArray());
                IMG.setHeight(image.getHeight());
                IMG.setWidth(image.getWidth());
                b.flush();
            }
        }
        b.close();
        ByteArrayOutputStream b1 = new ByteArrayOutputStream();
        h = image.getHeight();
        w = image.getWidth();
        IMG.setSmall(img);
        format = "png";
        if (h > 180 || w > 180) {
            if (h > w) {

                image = Scalr.resize(image, Scalr.Method.QUALITY, 180);
                ImageIO.write(image, format, b1);
                IMG.setSmall(b1.toByteArray());
                b1.flush();
            } else {

                BufferedImage im = Scalr.resize(image, Scalr.Method.QUALITY, 180);
                ImageIO.write(im, format, b1);
                IMG.setSmall(b1.toByteArray());
                b1.flush();
            }
        }
        b1.close();
        ByteArrayOutputStream b2 = new ByteArrayOutputStream();
        h = image.getHeight();
        w = image.getWidth();
        fh = 50;
        fw = 50;
        int ch;
        int cw;
        boolean album = false;
        boolean normal = false;
        format = "png";
        if (h < w) {
            album = true;
        }
        IMG.setIko(img);
        if (h > 50 && w > 50) {
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

            BufferedImage im = Scalr.resize(image, Scalr.Method.QUALITY, fh);
            ImageIO.write(im, format, b2);
            IMG.setIko(b2.toByteArray());
            b2.flush();
            b2.close();
        } else if (h > 50 || w > 50) {
            if ((int) java.lang.Math.round(w / h) == 1) {
                normal = true;
            }

            if (!normal) {
                if (w / h > 1) {
                    ch = h;
                    cw = 50;
                    image = image.getSubimage(((int) java.lang.Math.floor(w - cw) / 2), 0, cw, ch);

                } else {
                    cw = w;
                    ch = 50;
                    image = image.getSubimage(0, ((int) java.lang.Math.floor(h - ch) / 2), cw, ch);
                }
            }

            ImageIO.write(image, format, b2);
            IMG.setIko(b2.toByteArray());
            b2.flush();
            b2.close();

        }
        IMG.setType("image/png");

        return IMG;

    }

    public class Img {

        private int width;
        private int height;
        private String type;
        private byte[] img;
        private byte[] small;
        private byte[] iko;

        public Img(int width, int height, String type, byte[] img, byte[] small, byte[] iko) {
            this.width = width;
            this.height = height;
            this.type = type;
            this.img = img;
            this.small = small;
            this.iko = iko;
        }

        public Img() {
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public byte[] getImg() {
            return img;
        }

        public void setImg(byte[] img) {
            this.img = img;
        }

        public byte[] getSmall() {
            return small;
        }

        public void setSmall(byte[] small) {
            this.small = small;
        }

        public byte[] getIko() {
            return iko;
        }

        public void setIko(byte[] iko) {
            this.iko = iko;
        }

    }
}
