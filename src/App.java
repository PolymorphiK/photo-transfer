//import mdlaf.MaterialLookAndFeel;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {
    private JPanel content;

    public App() {
//        try {
//            UIManager.setLookAndFeel(new MaterialLookAndFeel());
//        } catch (UnsupportedLookAndFeelException e) {
//            e.printStackTrace();
//        }
//
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        this.setSize(new Dimension(1280, 720));
//        this.setMinimumSize(new Dimension(800, 600));
//        this.setLocationRelativeTo(null);
//
//        this.setTitle("App");
//
//        this.setVisible(true);
    }

    public JPanel content() {
        return this.content;
    }

    public void content(JPanel content) {
        if(this.content != null) this.remove(this.content);

        this.add(this.content = content);

        this.revalidate();
    }
}
