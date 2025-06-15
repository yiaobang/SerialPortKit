package com.yiaobang.serialportkit.serialport.dataListener;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortDataListenerWithExceptions;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.yiaobang.serialportkit.serialport.BaseSerialPortModel;

public sealed  class BaseSerialPortDataListener implements SerialPortDataListener, SerialPortDataListenerWithExceptions permits DelimiterDataListener, PackSizeDataListener {
    protected final BaseSerialPortModel serialPortBase;

    public BaseSerialPortDataListener(BaseSerialPortModel serialPortBase) {
        this.serialPortBase = serialPortBase;
    }

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }
    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        serialPortBase.listen(serialPortEvent.getReceivedData());
    }

    @Override
    public void catchException(Exception e) {

    }
}
