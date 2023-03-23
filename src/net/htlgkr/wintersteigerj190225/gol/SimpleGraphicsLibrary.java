package net.htlgkr.wintersteigerj190225.gol;

import javax.swing.*;
import java.awt.*;

public class SimpleGraphicsLibrary extends JPanel {
    private final int width;
    private final int height;
    private final Color background;
    private int[] pixels;

    public SimpleGraphicsLibrary(int width, int height, Color background) {
        this.width = width;
        this.height = height;
        this.background = background;
        clear();

        JFrame frame = new JFrame("Simple Graphics Library");
        this.setPreferredSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.pack();
    }

    public void setPixel(int x, int y, Color color) {
        int index = y * width + x;
        pixels[index] = color.getRGB();
        repaint();
    }

    public void clear() {
        this.pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int index = y * width + x;
                pixels[index] = background.getRGB();
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int index = y * width + x;
                Color color = new Color(pixels[index]);
                g.setColor(color);
                g.drawLine(x, y, x, y);
            }
        }
    }
}