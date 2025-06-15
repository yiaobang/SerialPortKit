package com.yiaobang.mvvm;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class BaseModel {
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    protected <T> void firePropertyChange(String propertyName, T oldValue, T newValue) {
        pcs.firePropertyChange(propertyName, oldValue, newValue);
    }



    public void dispose() {
        // 默认什么都不做，子类可覆盖
    }
}
