package components;

import javax.swing.*;
import java.awt.*;

public class ColorImage extends ImageIcon {
    public ColorImage(Image image) {
        super(image);
    }

    @Override
    public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
        super.paintIcon(c, g, x, y);
        g.setColor(Color.black);
    }
}
