package javaswingdev.sm3d;

import java.awt.Color;
import javaswingdev.FontAwesome;

public class ModelItem {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public FontAwesome getIcon() {
        return icon;
    }

    public void setIcon(FontAwesome icon) {
        this.icon = icon;
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

    public ModelItem(String name, String url, FontAwesome icon, Color color, Color colorShadow1, Color colorShadow2) {
        this.name = name;
        this.url = url;
        this.icon = icon;
        this.color = color;
        this.colorShadow1 = colorShadow1;
        this.colorShadow2 = colorShadow2;
    }

    public ModelItem() {
    }

    private String name;
    private String url;
    private FontAwesome icon;
    private Color color;
    private Color colorShadow1;
    private Color colorShadow2;
}
