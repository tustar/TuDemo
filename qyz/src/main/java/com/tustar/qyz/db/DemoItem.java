package com.tustar.qyz.db;

/**
 * Created by tustar on 17-3-10.
 */

public class DemoItem {

    private int color;
    private String colorName;

    public DemoItem() {

    }

    public DemoItem(int color, String colorName) {
        this.color = color;
        this.colorName = colorName;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }
}
