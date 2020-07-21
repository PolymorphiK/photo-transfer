package resources;

import javax.swing.*;
import java.awt.*;

public class Resources {
    public static Image user(int width, int height) {
        return new ImageIcon(Resources.class.getResource("icon_action_account.png"))
                .getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
}
