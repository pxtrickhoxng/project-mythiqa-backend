package com.mythiqa.mythiqabackend.model;

import java.util.List;

public class AppearanceClothing {
    private String style;
    private List<String> colors;
    private List<String> accessories;

    public AppearanceClothing() {}

    public String getStyle() { return style; }
    public void setStyle(String style) { this.style = style; }

    public List<String> getColors() { return colors; }
    public void setColors(List<String> colors) { this.colors = colors; }

    public List<String> getAccessories() { return accessories; }
    public void setAccessories(List<String> accessories) { this.accessories = accessories; }
}

