package com.yiaobang.mvvm;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.FileChooser;

import java.io.File;

public abstract class AppStart extends Application {

    public static final File HOME = new File(System.getProperty("user.dir"));
    public static final FileChooser FILE_CHOOSER = new FileChooser();

    @Override
    public void init() throws Exception {
        super.init();
        System.setProperty("prism.lcdtext", "false");
        System.setProperty("prism.allowhidpi", "false");
        //初始化文件选择器
        FILE_CHOOSER.setTitle("选择json文件");
        FILE_CHOOSER.setInitialDirectory(HOME);
        FILE_CHOOSER.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("json文件 (*.json)", "*.json")
        );
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Platform.exit();
        System.exit(0);
    }
}
