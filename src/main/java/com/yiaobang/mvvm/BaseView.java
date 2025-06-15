package com.yiaobang.mvvm;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public abstract class BaseView<VM extends BaseViewModel<?>> implements Initializable {
    protected final VM viewModel;
    protected final Parent root;
    protected final Scene scene;

    public BaseView(VM viewModel) {
        this.viewModel = viewModel;
        String fxmlName = getClass().getSimpleName() + ".fxml";
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlName));
            loader.setController(this);
            root = loader.load();
            scene = new Scene(root);
        } catch (IOException e) {
            throw new RuntimeException("FXML 加载失败: " + fxmlName, e);
        }
    }

    protected VM getViewModel() {
        return this.viewModel;
    }

    public Scene getScene() {
        scene.setFill(Color.TRANSPARENT);
        return this.scene;
    }
    public Stage getStage() {
        return Fx.getStage(scene);
    }
    public Parent getView() {
        return this.root;
    }

    @Override
    public abstract void initialize(URL location, ResourceBundle resources);
}
