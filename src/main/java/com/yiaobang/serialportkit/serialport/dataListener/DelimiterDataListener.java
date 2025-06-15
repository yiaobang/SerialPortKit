package com.yiaobang.serialportkit.serialport.dataListener;

import com.fazecast.jSerialComm.SerialPortMessageListener;
import com.yiaobang.serialportkit.serialport.BaseSerialPortModel;

public final class DelimiterDataListener extends BaseSerialPortDataListener implements SerialPortMessageListener{

    private final byte[] delimiterBytes;

    public DelimiterDataListener(BaseSerialPortModel serialPortBase, byte[] delimiterBytes) {
        super(serialPortBase);
        this.delimiterBytes = delimiterBytes;
    }

    @Override
    public byte[] getMessageDelimiter() {
        return this.delimiterBytes;
    }

    @Override
    public boolean delimiterIndicatesEndOfMessage() {
        return true;
    }

}
