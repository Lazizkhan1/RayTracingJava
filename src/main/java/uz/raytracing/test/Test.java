package uz.raytracing.test;


import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;


public class Test {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Drag float");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JColorChooser colorChooser = new JColorChooser();
        colorChooser.setPreviewPanel(null);
        AbstractColorChooserPanel t = colorChooser.getChooserPanels()[1];
        for(AbstractColorChooserPanel a: colorChooser.getChooserPanels()) {
            if(a == t) continue;
            colorChooser.removeChooserPanel(a);
        }
        colorChooser.getSelectionModel().addChangeListener(e -> {
            System.out.println(colorChooser.getColor().getRGB());
        });
        JButton button = new JButton();
        button.addActionListener(e -> {
            JDialog dialog = new JDialog();
            dialog.add(colorChooser);
            dialog.setVisible(true);
            dialog.show();
        });

        frame.add(button);
        frame.setVisible(true);
    }
}

