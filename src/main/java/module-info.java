module com.yiaobang.serialportkit {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires com.fazecast.jSerialComm;
    requires com.google.gson;
    requires org.apache.commons.codec;
    requires org.apache.commons.text;


    opens com.yiaobang.serialportkit to javafx.fxml;
    exports com.yiaobang.serialportkit;
}