package httpraider.view.components.helpers;

import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

public class MainTabbedPaneUI extends BasicTabbedPaneUI {
    private static final int ARC = 12;
    private static final int GAP = 3;
    private final Color SEL       = new Color(0x674A87BC, true);
    private final Color UNSEL     = new Color(0x73DADADA, true);
    private final Color BORDER    = new Color(0x0B0B0B0, true);

    @Override
    protected void installDefaults() {
        super.installDefaults();
        tabInsets            = new Insets(2, 12, 2, 12);
        selectedTabPadInsets = tabInsets;
        tabRunOverlay        = 0;
        tabAreaInsets        = new Insets(2, 2, 2, 2);
    }

    @Override
    protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
        return 25;
    }

    @Override
    protected void paintTabBackground(Graphics g, int tp, int i,
                                      int x, int y, int w, int h, boolean sel) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int paintWidth = w - GAP;
        g2.setColor(sel ? SEL : UNSEL);
        g2.fillRoundRect(x, y, paintWidth, h, ARC, ARC);
        g2.dispose();
    }

    @Override
    protected void paintTabBorder(Graphics g, int tp, int i,
                                  int x, int y, int w, int h, boolean sel) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int paintWidth = w - GAP;
        g2.setColor(BORDER);
        g2.drawRoundRect(x, y, paintWidth, h, ARC, ARC);
        g2.dispose();
    }

    @Override
    protected void paintFocusIndicator(Graphics g, int tp, Rectangle[] rects,
                                       int i, Rectangle iconRect, Rectangle textRect,
                                       boolean sel) {
        // no dotted focus
    }

    @Override
    protected void paintContentBorder(Graphics g, int tp, int i) {
        // none
    }
}