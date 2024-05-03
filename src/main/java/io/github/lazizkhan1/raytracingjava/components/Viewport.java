package io.github.lazizkhan1.raytracingjava.components;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

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

    public void exportImage() {
        File directory = new File("output");
        directory.mkdir();
        String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm").format(new Date());
        File outputImage = new File("output/" + fileName + ".png");
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        bufferedImage.setData(image.getRaster());
        try {
            outputImage.createNewFile();
            ImageIO.write(bufferedImage, "png", outputImage);
            JDialog dialog = new JDialog();
            dialog.add(new JLabel("Image saved successfully to " + outputImage.getPath()));
            // Set dialog to the center of the screen
            dialog.setSize(400, 100);
            dialog.setTitle("Info");
            dialog.setLayout(new FlowLayout());;
            JButton okButton = new JButton("OK");
            okButton.addActionListener(e -> {
                dialog.setVisible(false);
                dialog.dispose();
            });
            dialog.add(okButton);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}