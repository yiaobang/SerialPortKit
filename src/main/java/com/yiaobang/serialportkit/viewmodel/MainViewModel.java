package com.yiaobang.serialportkit.viewmodel;

import com.yiaobang.mvvm.BaseViewModel;
import com.yiaobang.serialportkit.model.MainModel;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class MainViewModel extends BaseViewModel<MainModel> {

    public MainViewModel(MainModel model) {
        super(model);
    }

    // 切换置顶
    public void toggleTop(Stage stage, Label label) {
        model.setTop(!model.isTop());
        stage.setAlwaysOnTop(model.isTop());
        setRightFill(label);
    }

    /**
     * 设置正确填充
     *
     * @param label 标签
     */
    public void setRightFill(Label label) {
        label.setTextFill(model.isTop() ? Color.LIMEGREEN : model.isDarkTheme() ? Color.WHITE : Color.BLACK);
    }

    // 切换主题
    public void toggleTheme() {
        boolean dark = !model.isDarkTheme();
        model.setDarkTheme(dark);
        Application.setUserAgentStylesheet(dark ? MainModel.dark : MainModel.light);
    }
}
