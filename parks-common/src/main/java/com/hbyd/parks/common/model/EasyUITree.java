package com.hbyd.parks.common.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by len on 14-7-17.
 */
public class EasyUITree {
    private String id;
    private String text;
    private String iconCls;
    private HashMap<String,String> attributes;
    private List<EasyUITree> children;

    public void addChild(EasyUITree child) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.add(child);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<EasyUITree> getChildren() {
        return children;
    }

    public void setChildren(List<EasyUITree> children) {
        this.children = children;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<String, String> attributes) {
        this.attributes = attributes;
    }
}
