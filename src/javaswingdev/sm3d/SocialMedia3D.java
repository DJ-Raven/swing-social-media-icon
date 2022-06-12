package javaswingdev.sm3d;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;

public class SocialMedia3D extends JComponent {

    private final List<SocialMediaItem> items = new ArrayList<>();
    private final List<SocialMediaEvent> events = new ArrayList<>();
    private int mouseHoverIndex = -1;

    public SocialMedia3D() {
        init();
    }

    private void init() {
        MouseAdapter mouseEvent = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int index = checkMouseOver(e.getPoint());
                if (index != mouseHoverIndex) {
                    mouseHoverIndex = index;
                    if (mouseHoverIndex != -1) {
                        items.get(mouseHoverIndex).getAnimator().show();
                    }
                    hide(mouseHoverIndex);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (mouseHoverIndex != -1) {
                    runEvent();
                }
            }

        };
        addMouseMotionListener(mouseEvent);
        addMouseListener(mouseEvent);
    }

    public void addItem(ModelItem item) {
        items.add(new SocialMediaItem(this, item));
    }

    public void addEvent(SocialMediaEvent event) {
        events.add(event);
    }

    private void runEvent() {
        for (SocialMediaEvent event : events) {
            event.selected(items.get(mouseHoverIndex).getData());
        }
    }

    private void hide(int exitIndex) {
        for (int i = 0; i < items.size(); i++) {
            if (i != exitIndex) {
                items.get(i).getAnimator().hide();
            }
        }
    }

    private int checkMouseOver(Point mouse) {
        int hoverIndex = -1;
        int itemWidth = 120;
        int itemHeight = 70;
        int space = 10;
        int x = (getWidth() - ((itemWidth * items.size()) + (space * (items.size() - 1)))) / 2;
        int y = (getHeight() - itemHeight) / 2;
        int index = 0;
        for (SocialMediaItem item : items) {
            if (item.checkMouse(mouse, x, y)) {
                hoverIndex = index;
                break;
            }
            x += itemWidth + space;
            index++;
        }
        return hoverIndex;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        int itemWidth = 120;
        int itemHeight = 70;
        int space = 10;
        int x = (getWidth() - ((itemWidth * items.size()) + (space * (items.size() - 1)))) / 2;
        int y = (getHeight() - itemHeight) / 2;
        for (SocialMediaItem item : items) {
            item.render(g2, new Rectangle(x, y, itemWidth, itemHeight));
            x += itemWidth + space;
        }
        g2.dispose();
        super.paintComponent(g);
    }
}
