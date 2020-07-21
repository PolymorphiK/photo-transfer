package views;

import components.NavigationRail;

import javax.swing.*;
import java.awt.*;

public class GUIView extends JPanel {
    private NavigationRail navigationRail = null;

    public GUIView() {
        this.setLayout(new BorderLayout());

        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.navigationRail = new NavigationRail();

        this.add(this.navigationRail, BorderLayout.WEST);
        this.add(new Main(), BorderLayout.CENTER);
    }

    public static class Main extends JPanel {
        public Main() {
            JPanel splitView = new JPanel();
            splitView.setSize(new Dimension(500, 500));
            splitView.setBackground(Color.white);
            splitView.setLayout(new BoxLayout(splitView, BoxLayout.X_AXIS));

            JList<String> list = new JList<>();
            list.setVisibleRowCount(10);
            list.setSize(new Dimension(500, 500));

            splitView.add(list);

            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            this.add(splitView);

            this.setBackground(Color.black);
        }
    }
}
