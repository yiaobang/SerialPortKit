package com.yiaobang.mvvm;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.function.Function;

public interface Fx {
    static void run(Runnable runnable) {
        if (Platform.isFxApplicationThread()) {
            runnable.run();
        } else {
            Platform.runLater(runnable);
        }
    }

    static InputStream getResourceAsStream(String resource) {
        return Fx.class.getResourceAsStream(resource);
    }
    /**
     * 通过Scene获取Stage
     *
     * @param scene scene
     * @return {@code Stage }
     */
     static Stage getStage(Scene scene) {
        return (Stage) scene.getWindow();
    }

    /**
     * 获取Node获取Stage
     *
     * @param node 节点
     * @return {@code Stage }
     */
     static Stage getStage(Node node) {
        return getStage(node.getScene());
    }
    /**
     * 设置窗口拖拽
     *
     * @param stage 窗口
     */
    static Stage stageDrag(Stage stage) {
        final double[] offsetX = {0};
        final double[] offsetY = {0};
        stage.getScene().setOnMousePressed(event -> {
            offsetX[0] = event.getSceneX();
            offsetY[0] = event.getSceneY();
        });

        stage.getScene().setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - offsetX[0]);
            stage.setY(event.getScreenY() - offsetY[0]);
        });
        return stage;
    }
    /**
     * 创建MVVM
     *
     * @param model    模型
     * @param vmCreator    创建VM的方法
     * @param viewCreator 创建V的方法
     * @return {@code V }
     */
    static <M extends BaseModel, VM extends BaseViewModel<M>, V extends BaseView<VM>>
    V createMVVM(M model, Function<M, VM> vmCreator, Function<VM, V> viewCreator) {
        VM vm = vmCreator.apply(model);
        return viewCreator.apply(vm);
    }
}
