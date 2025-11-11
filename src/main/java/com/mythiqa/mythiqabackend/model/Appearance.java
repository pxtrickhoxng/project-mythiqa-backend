package com.mythiqa.mythiqabackend.model;

import java.util.List;
import com.mythiqa.mythiqabackend.model.AppearanceClothing;

public class Appearance {
    private String height;
    private String build;
    private String eyeColor;
    private String hairColor;
    private String hairStyle;
    private String skinTone;
    private List<String> distinguishingFeatures;
    private AppearanceClothing clothing;

    public Appearance() {}

    public String getHeight() { return height; }
    public void setHeight(String height) { this.height = height; }

    public String getBuild() { return build; }
    public void setBuild(String build) { this.build = build; }

    public String getEyeColor() { return eyeColor; }
    public void setEyeColor(String eyeColor) { this.eyeColor = eyeColor; }

    public String getHairColor() { return hairColor; }
    public void setHairColor(String hairColor) { this.hairColor = hairColor; }

    public String getHairStyle() { return hairStyle; }
    public void setHairStyle(String hairStyle) { this.hairStyle = hairStyle; }

    public String getSkinTone() { return skinTone; }
    public void setSkinTone(String skinTone) { this.skinTone = skinTone; }

    public List<String> getDistinguishingFeatures() { return distinguishingFeatures; }
    public void setDistinguishingFeatures(List<String> distinguishingFeatures) { this.distinguishingFeatures = distinguishingFeatures; }

    public AppearanceClothing getClothing() { return clothing; }
    public void setClothing(AppearanceClothing clothing) { this.clothing = clothing; }
}
