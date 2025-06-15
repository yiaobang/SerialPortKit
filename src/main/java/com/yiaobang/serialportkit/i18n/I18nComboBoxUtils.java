package com.yiaobang.serialportkit.i18n;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class I18nComboBoxUtils {
    // 设置国际化 cellFactory
    public static <T> void setupI18nCellFactory(ComboBox<T> comboBox, Function<T, String> keyExtractor) {
        comboBox.setCellFactory(_ -> new ListCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : I18nManager.get(keyExtractor.apply(item)));
            }
        });
        comboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : I18nManager.get(keyExtractor.apply(item)));
            }
        });
        // 切换语言时刷新items
        I18nManager.localeProperty().addListener((_, _, _) -> {
            comboBox.setItems(FXCollections.observableArrayList(comboBox.getItems()));
        });
    }

    public static <T> void setKeys(ComboBox<T> comboBox, T[] keys, Function<T, String> keyExtractor) {
        setKeys(comboBox, Arrays.asList(keys), keyExtractor);
    }

    // 快速设置 keys（如字符串 key 列表）
    public static <T> void setKeys(ComboBox<T> comboBox, List<T> keys, Function<T, String> keyExtractor) {
        comboBox.setItems(FXCollections.observableArrayList(keys));
        if (!keys.isEmpty()) {
            comboBox.getSelectionModel().select(0);
        }
        setupI18nCellFactory(comboBox, keyExtractor);
    }
}