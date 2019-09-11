package com.lilin.fx.utils;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class CheckBoxListItem {
    private SimpleStringProperty name = new SimpleStringProperty();
    private BooleanProperty selected = new SimpleBooleanProperty(false);

    public CheckBoxListItem(String name) {
        this.name.set(name);
    }

    public CheckBoxListItem(String name, boolean selected) {
        this.name.set(name);
        this.selected.set(selected);
    }

    public String getName() {
        return name.get();
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }
}
