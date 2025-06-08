module com.yiaobang.serialportkit {
    requires javafx.controls;
    requires javafx.fxml;



    opens com.yiaobang.serialportkit to javafx.fxml;
    exports com.yiaobang.serialportkit;
}