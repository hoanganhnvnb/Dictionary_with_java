package View;

import javax.swing.*;
import java.awt.*;

public class DictionaryApplication extends JFrame {

    private final int WIDTH = 480;
    private final int LENGTH = 680;

    private static void runApplication() {
        new DictionaryApplication().setVisible(true);
    }

    public DictionaryApplication() {
        setTitle("Dictionary");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(LENGTH, WIDTH));
        setSize(new Dimension(LENGTH, WIDTH));
        setLocationRelativeTo(null);

    }

    public static void main(String[] args) {
        runApplication();
    }
}
