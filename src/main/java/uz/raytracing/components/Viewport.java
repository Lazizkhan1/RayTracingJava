package uz.raytracing.components;

import javax.swing.JPanel;
import java.awt.Graphics;

public class Viewport extends JPanel {
    private Image image;

    public void setImage(Image image) {
        this.image = image;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.clearRect(0, 0, getWidth(), getHeight());
        g.drawImage(image, 0, 0, null);
        g.dispose();
    }
}