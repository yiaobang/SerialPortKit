module com.yiaobang.serialportkit {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;


    opens com.yiaobang.serialportkit to javafx.fxml;
    exports com.yiaobang.serialportkit;
}