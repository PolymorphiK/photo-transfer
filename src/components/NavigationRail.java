package components;

import resources.Resources;
import javax.swing.*;
import java.awt.*;

public class NavigationRail extends JPanel {
    public NavigationRail() {
        this.setLayout(new BorderLayout());

        this.setBackground(new Color(0x26, 0x32,0x38));

        JButton button = new JButton();
        button.setText("Account");
        button.setHorizontalAlignment(SwingConstants.RIGHT);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);

        //button.setPreferredSize(new Dimension(32, 32));
        this.add(button, BorderLayout.NORTH);

        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
        separator.setForeground(Color.lightGray);
        this.add(separator);
    }
}
