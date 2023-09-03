//package uz.raytracing;
//
//import uz.raytracing.util.Timer;
//
//import javax.imageio.ImageIO;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//
//public class Main {
//    public static void main(String[] args) throws IOException {
//        int mViewportWidth = 500;
//        int mViewportHeight = 500;
//        //TODO: this haven't declare here and get from viewport every loop
//        Renderer mRenderer = new Renderer();
//        JFrame frame = new JFrame("Ray Tracing");
//        JLabel label = new JLabel();
//        JButton renderButton = new JButton("Render");
//        JPanel controlPanel = new JPanel();
//        JPanel rootPanel = new JPanel();
//        //TODO: viewport have add root panel and also control panel /not directly frame
//        JPanel viewport = new JPanel() {
//            //TODO: This have be replace by Viewport
//            @Override
//            protected void paintComponent(Graphics g) {
//                Timer timer = new Timer();
//                mRenderer.onResize(mViewportWidth, mViewportHeight);
//                g.drawImage(mRenderer.render(), 0, 0, null);
//                label.setText(timer.toString());
//            }
//        };
//
//        //TODO:Those shits also moves into other file
//        frame.setLayout(new FlowLayout(FlowLayout.LEFT));
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
//
//        renderButton.setSize(10, 5);
//        renderButton.addActionListener(e -> {
////            System.out.println(frame.getWidth() + " : " + frame.getHeight());
//            viewport.repaint();
//        });
////        viewport.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
//        label.setVerticalAlignment(SwingConstants.TOP);
//        renderButton.setVerticalAlignment(SwingConstants.TOP);
//        controlPanel.add(label);
//        controlPanel.add(renderButton);
//        frame.add(viewport);
//        frame.add(controlPanel);
//        frame.setSize(610, 550);
//        frame.setVisible(true);
//
//        new Thread(() -> {
//            while (true) {
//                viewport.repaint();
//            }
//        }).start();
//    }
//
//    public static void writeFile(BufferedImage image) {
//        File file = new File("./src/main/resources/result.png");
//        try {
//            ImageIO.write(image,"png", file);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}