package com.zilvis.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CellData {
    private final SimpleIntegerProperty index;
    private final SimpleIntegerProperty x;
    private final SimpleIntegerProperty y;
    private final SimpleStringProperty key;
    private final SimpleStringProperty color;

    public CellData(int index, int x, int y, String color, String key) {
        this.index = new SimpleIntegerProperty(index);
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.key = new SimpleStringProperty(key);
        this.color = new SimpleStringProperty(color);
    }

    public int getIndex() {
        return index.get();
    }

    public int getX() {
        return x.get();
    }

    public int getY() {
        return y.get();
    }

    public String getColor() {
        return color.get();
    }

    public String getKey() {
        return key.get();
    }

    public void setIndex(int index) {
        this.index.set(index);
    }

    public void setX(int x) {
        this.x.set(x);
    }

    public void setY(int y) {
        this.y.set(y);
    }

    public void setKey(String key) {
        this.key.set(key);
    }

    public void setColor(String color) {
        this.color.set(color);
    }
}
