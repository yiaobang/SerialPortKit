package com.yiaobang.serialportkit;

import com.yiaobang.mvvm.AppStart;
import com.yiaobang.mvvm.Fx;
import com.yiaobang.serialportkit.i18n.I18nManager;
import com.yiaobang.serialportkit.model.MainModel;
import com.yiaobang.serialportkit.view.MainView;
import com.yiaobang.serialportkit.viewmodel.MainViewModel;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.util.List;
import java.util.Locale;

public class SerialPortKitApplication extends AppStart {


    @Override
    public void start(Stage stage) {
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(Fx.createMVVM(new MainModel(), MainViewModel::new, MainView::new).getScene());
        stage.getIcons().add(new Image(Fx.getResourceAsStream("/com/yiaobang/serialportkit/images/appicon/app@4X.png")));
        Fx.stageDrag(stage).show();
    }

    @Override
    public void init() throws Exception {
        super.init();

        // 在任何界面初始化前，优先设置全局语言
        Locale defaultLocale = Locale.getDefault();
        List<Locale> supported = I18nManager.getSupportedLocales();

        Locale initialLocale = supported.stream()
                .filter(l -> l.equals(defaultLocale)) // 1. 找 supported 里有没有和本机完全一致的 Locale
                .findFirst()
                .orElseGet(() -> supported.stream()
                        .filter(l -> l.getLanguage().equals(defaultLocale.getLanguage())) // 2. 没有完全一样的，就找有没有语言相同的
                        .findFirst()
                        .orElse(Locale.ENGLISH) // 3. 连语言都没有一样的，最后用英文
                );
        I18nManager.switchLocale(initialLocale);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
