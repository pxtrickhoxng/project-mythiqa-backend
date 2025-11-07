package com.mythiqa.mythiqabackend.model;

import java.util.List;
import java.util.Map;

public class RichEditor {
    private String type;
    private Map<String, Object> attrs;
    private List<RichEditor> content;
    private List<Mark> marks;
    private String text;

    public RichEditor() {}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<String, Object> attrs) {
        this.attrs = attrs;
    }

    public List<RichEditor> getContent() {
        return content;
    }

    public void setContent(List<RichEditor> content) {
        this.content = content;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static class Mark {
        private String type;
        private Map<String, Object> attrs;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Map<String, Object> getAttrs() {
            return attrs;
        }

        public void setAttrs(Map<String, Object> attrs) {
            this.attrs = attrs;
        }
    }
}
