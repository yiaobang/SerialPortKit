package com.yiaobang.serialportkit.i18n;

import javafx.beans.property.SimpleStringProperty;

public class I18nStringProperty extends SimpleStringProperty {
    private final String key;

    public I18nStringProperty(String key) {
        this.key = key;
        set(I18nManager.get(key));
        // 监听语言变化，自动刷新文本
        I18nManager.localeProperty().addListener((_, _, _) -> set(I18nManager.get(key)));
    }

    public String getKey() {
        return key;
    }
}