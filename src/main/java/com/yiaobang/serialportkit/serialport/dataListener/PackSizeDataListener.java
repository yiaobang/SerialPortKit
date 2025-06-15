package com.yiaobang.serialportkit.serialport.dataListener;

import com.fazecast.jSerialComm.SerialPortPacketListener;
import com.yiaobang.serialportkit.serialport.BaseSerialPortModel;

public final class PackSizeDataListener extends BaseSerialPortDataListener implements SerialPortPacketListener{
    private final int packetSize;

    public PackSizeDataListener(BaseSerialPortModel serialPortBase, int packetSize) {
        super(serialPortBase);
        this.packetSize = packetSize;
    }


    @Override
    public int getPacketSize() {
        return this.packetSize;
    }

}
