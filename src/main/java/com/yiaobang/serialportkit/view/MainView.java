package com.yiaobang.serialportkit.view;

import com.yiaobang.mvvm.BaseView;
import com.yiaobang.serialportkit.model.MainModel;
import com.yiaobang.serialportkit.model.NavItem;
import com.yiaobang.serialportkit.viewmodel.MainViewModel;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainView extends BaseView<MainViewModel> {

    @FXML
    private TreeView<NavItem> navTreeView;
    @FXML
    private StackPane contentPane;

    @FXML
    private Label alwaysOnTop;
    @FXML
    private Label settings;


    public MainView(MainViewModel viewModel) {
        super(viewModel);
    }

    @FXML
    void min() {
        getStage().setIconified(true);
    }

    @FXML
    void close() {
        getStage().close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // 置顶点击事件
        alwaysOnTop.setOnMouseClicked(_ -> viewModel.toggleTop(getStage(), alwaysOnTop));

        // 设置点击事件
        settings.setOnMouseClicked(_ -> {
            viewModel.toggleTheme();
            viewModel.setRightFill(alwaysOnTop);
        });


        // 初始化样式
        //alwaysOnTop.setTextFill(viewModel.getModel().isTop() ? Color.LIMEGREEN : Color.BLACK);
        Application.setUserAgentStylesheet(getViewModel().getModel().isDarkTheme() ? MainModel.dark : MainModel.light);

    }
}