package javaswingdev.sm3d;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import javaswingdev.FontAwesomeIcon;
import javaswingdev.GradientType;
import javaswingdev.shadow.ShadowRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class SocialMediaItem {

    public ColorAnimation getAnimator() {
        return animator;
    }

    public void setAnimator(ColorAnimation animator) {
        this.animator = animator;
    }

    public ModelItem getData() {
        return data;
    }

    public void setData(ModelItem data) {
        this.data = data;
    }

    public Color getDefaultColor() {
        return defaultColor;
    }

    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
    }

    public Color getDefaultColorShadow1() {
        return defaultColorShadow1;
    }

    public void setDefaultColorShadow1(Color defaultColorShadow1) {
        this.defaultColorShadow1 = defaultColorShadow1;
    }

    public Color getDefaultColorShadow2() {
        return defaultColorShadow2;
    }

    public void setDefaultColorShadow2(Color defaultColorShadow2) {
        this.defaultColorShadow2 = defaultColorShadow2;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColorShadow1() {
        return colorShadow1;
    }

    public void setColorShadow1(Color colorShadow1) {
        this.colorShadow1 = colorShadow1;
    }

    public Color getColorShadow2() {
        return colorShadow2;
    }

    public void setColorShadow2(Color colorShadow2) {
        this.colorShadow2 = colorShadow2;
    }

    public Color getIconColor1() {
        return iconColor1;
    }

    public void setIconColor1(Color iconColor1) {
        this.iconColor1 = iconColor1;
    }

    public Color getIconColor2() {
        return iconColor2;
    }

    public void setIconColor2(Color iconColor2) {
        this.iconColor2 = iconColor2;
    }

    private ModelItem data;
    private Color defaultColor;
    private Color defaultColorShadow1;
    private Color defaultColorShadow2;
    private Color color;
    private Color colorShadow1;
    private Color colorShadow2;
    private Color iconColor1;
    private Color iconColor2;
    private Shape shape;
    private ColorAnimation animator;

    public SocialMediaItem(Component com, ModelItem data) {
        this.data = data;
        //  Init Default color
        color = defaultColor = new Color(248, 248, 248);
        colorShadow1 = defaultColorShadow1 = new Color(201, 201, 201);
        colorShadow2 = defaultColorShadow2 = new Color(222, 222, 222);
        iconColor1 = data.getColor();
        iconColor2 = data.getColorShadow2();
        animator = new ColorAnimation(com, this);
    }

    public void render(Graphics2D g2, Rectangle rec) {
        float shadowSize = 0.25f;
        int width = rec.width;
        int height = rec.height;

        Path2D p = new Path2D.Double();
        p.moveTo(0, height * 0.45f);
        p.lineTo(width * 0.45f, height);
        p.lineTo(width, height * 0.55f);
        p.lineTo(width * 0.55f, 0);
        Path2D pLeft = new Path2D.Double();
        pLeft.moveTo(0, height * 0.45f);
        pLeft.lineTo(0, (height * (0.45f + shadowSize)));
        pLeft.lineTo(width * 0.45f, height + (height * shadowSize));
        pLeft.lineTo(width * 0.45f, height);
        Path2D pRight = new Path2D.Double();
        pRight.moveTo(width * 0.45f, height);
        pRight.lineTo(width * 0.45f, height + (height * shadowSize));
        pRight.lineTo(width, height * (0.55f + shadowSize));
        pRight.lineTo(width, height * 0.55f);

        AffineTransform tran = g2.getTransform();
        g2.translate(rec.x, rec.y);
        //  Create Shape
        Area area = new Area();
        area.add(new Area(p));
        area.add(new Area(pLeft));
        area.add(new Area(pRight));
        shape = area;
        //  Create Shadow
        createShadow(g2, shape);
        //  Fill
        g2.setColor(color);
        g2.fill(p);
        g2.setColor(colorShadow1);
        g2.fill(pLeft);
        g2.setColor(colorShadow2);
        g2.fill(pRight);

        //  Draw Icon
        Icon icon = new FontAwesomeIcon(data.getIcon(), GradientType.VERTICAL, iconColor1, iconColor2, 30).toIcon();
        int iconX = (width - icon.getIconWidth()) / 2;
        int iconY = (height - icon.getIconHeight()) / 2;
        g2.rotate(Math.toRadians(-40), width / 2, height / 2);
        g2.shear(Math.toRadians(15), Math.toRadians(15));
        g2.drawImage(((ImageIcon) icon).getImage(), iconX - 5, iconY - 15, null);
        g2.setTransform(tran);
    }

    private void createShadow(Graphics2D g2, Shape shape) {
        Rectangle rec = shape.getBounds();
        BufferedImage img = new BufferedImage(rec.width, rec.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.fill(shape);
        g.dispose();
        g2.drawImage(new ShadowRenderer(6, 0.3f, Color.GRAY).createShadow(img), -6, 0, null);
    }

    public boolean checkMouse(Point mouse, int x, int y) {
        int mouseX = mouse.x - x;
        int mouseY = mouse.y - y;
        return shape.contains(mouseX, mouseY);
    }
}
