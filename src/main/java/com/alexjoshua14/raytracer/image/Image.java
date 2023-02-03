package com.alexjoshua14.raytracer.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;

public class Image implements Closeable {
    private final BufferedImage image;
    private final Graphics2D graphics;

    public Image(int w, int h) {
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        graphics = image.createGraphics();
    }

    public void plotPixel(int x, int y, ImageColor color) {
        int r = (int) Math.max(0, Math.min(255, color.getR()));
        int g = (int) Math.max(0, Math.min(255, color.getG()));
        int b = (int) Math.max(0, Math.min(255, color.getB()));

        graphics.setPaint(
                new Color(r, g, b)
        );

        graphics.fillRect(x, y, 1, 1);
    }

    public void save(Path file) throws IOException {
        ImageIO.write(image, "PNG", file.toFile());
    }

    @Override
    public void close() throws IOException {
        graphics.dispose();
    }
}
