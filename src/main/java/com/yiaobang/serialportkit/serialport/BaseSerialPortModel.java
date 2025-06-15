package com.yiaobang.serialportkit.serialport;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.yiaobang.mvvm.BaseModel;
import com.yiaobang.mvvm.Fx;
import com.yiaobang.serialportkit.serialport.dataListener.BaseSerialPortDataListener;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

public class BaseSerialPortModel extends BaseModel implements AutoCloseable {
    //串口常数
    public static final int[] BAUD_RATE = {9600, 19200, 38400, 115200, 128000, 230400, 256000, 460800, 921600, 1382400};
    public static final int[] DATA_BITS = {8, 7, 6, 5};
    public static final int[] STOP_BITS = {SerialPort.ONE_STOP_BIT, SerialPort.ONE_POINT_FIVE_STOP_BITS, SerialPort.TWO_STOP_BITS};
    public static final int[] PARITY = {SerialPort.NO_PARITY, SerialPort.ODD_PARITY, SerialPort.EVEN_PARITY, SerialPort.MARK_PARITY, SerialPort.SPACE_PARITY};
    public static final int[] FLOW_CONTROL = {SerialPort.FLOW_CONTROL_DISABLED,
            SerialPort.FLOW_CONTROL_RTS_ENABLED | SerialPort.FLOW_CONTROL_CTS_ENABLED,
            SerialPort.FLOW_CONTROL_DSR_ENABLED | SerialPort.FLOW_CONTROL_DTR_ENABLED,
            SerialPort.FLOW_CONTROL_XONXOFF_IN_ENABLED | SerialPort.FLOW_CONTROL_XONXOFF_OUT_ENABLED};


    @Getter
    //发送的数据量
    public final SimpleLongProperty sendCount = new SimpleLongProperty(0);
    @Getter
    //接收的数据量
    public final SimpleLongProperty receiveCount = new SimpleLongProperty(0);
    @Getter
    //串口状态
    public final SimpleBooleanProperty serialPortState = new SimpleBooleanProperty(false);


    //设置串口默认参数
    protected SerialPort serialPort;
    protected String serialPortName;
    protected int baudRate = 9600;
    protected int dataBits = 8;
    protected int stopBits = 1;
    protected int parity = 0;
    protected int flowControl = 0;
    protected final SerialPortDataListener listener;

    public BaseSerialPortModel() {
        this.listener = new BaseSerialPortDataListener(this);
    }

    public BaseSerialPortModel(SerialPortDataListener listener) {
        this.listener = listener;
    }


    public void listen(byte[] bytes) {
        //接受的数据量
        Fx.run(() -> receiveCount.set(receiveCount.get() + bytes.length));
    }

    public void scanSerialPort() {
        close();
        updateSerialPorts();
        for (SerialPort serial : commPorts) {
            if (serial.getSystemPortName().equals(serialPortName)) {
                serialPort = serial;
                return;
            }
        }
        serialPort = null;
    }

    /**
     * 打开串口
     */
    public void openSerialPort() {
        scanSerialPort();
        if (serialPort == null) {
            Fx.run(() -> serialPortState.set(false));
            return;
        }
        serialPort.setComPortParameters(baudRate, dataBits, stopBits, parity);
        serialPort.setFlowControl(flowControl);
        serialPort.addDataListener(this.listener);
        boolean isOpen = serialPort.openPort();
        Fx.run(() -> serialPortState.set(isOpen));
    }

    /**
     * 写
     *
     * @param bytes 字节
     * @return int
     */
    public void write(byte[] bytes) {
        if (bytes == null || serialPort == null || !serialPort.isOpen()) return;

        int sent = serialPort.writeBytes(bytes, bytes.length);
        if (sent > 0) {
            // 更新发送的数据量
            Fx.run(() -> sendCount.set(sendCount.get() + sent));
        } else {
            close();
        }
    }

    /**
     * 设置串口名称
     *
     * @param serialPortName 串口名称
     */
    public void setSerialPortName(String serialPortName) {
        this.serialPortName = serialPortName;
        openSerialPort();
    }

    /**
     * 设置波特率
     *
     * @param baudRateIndex 波特率
     */
    public void setBaudRate(int baudRateIndex) {
        this.baudRate = BAUD_RATE[baudRateIndex];
        openSerialPort();
    }

    /**
     * 设置数据位
     *
     * @param dataBitsIndex 数据位
     */
    public void setDataBits(int dataBitsIndex) {
        this.dataBits = DATA_BITS[dataBitsIndex];
        openSerialPort();
    }

    /**
     * 设置停止位
     *
     * @param stopBitsIndex 停止位
     */
    public void setStopBits(int stopBitsIndex) {
        this.stopBits = STOP_BITS[stopBitsIndex];
        openSerialPort();
    }

    /**
     * 设置奇偶校验
     *
     * @param parityIndex 校验
     */
    public void setParity(int parityIndex) {
        this.parity = PARITY[parityIndex];
        openSerialPort();
    }


    /**
     * 设置流量控制
     *
     * @param flowControlIndex 流控制
     */
    public void setFlowControl(int flowControlIndex) {
        this.flowControl = FLOW_CONTROL[flowControlIndex];
        openSerialPort();
    }

    @Override
    public void close() {
        if (serialPort != null) {
            serialPort.closePort();
        }
        Fx.run(() -> serialPortState.set(false));
    }

    //************************************************用于维护串口列表*************************************************//
    //************************************************用于维护串口列表*************************************************//
    //************************************************用于维护串口列表*************************************************//
    public static volatile SerialPort[] commPorts = SerialPort.getCommPorts();
    public static final SimpleStringProperty serialPorts = new SimpleStringProperty(serialPortsToString());

    static {
        Thread.ofVirtual()
                .name("SerialPortMonitor", 0)
                .start(() -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        updateSerialPorts();
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            //Thread.currentThread().interrupt(); // 优雅退出
                        }
                    }
                });
    }

    private static void updateSerialPorts() {
        SerialPort[] newPorts = SerialPort.getCommPorts();
        if (hasPortsChanged(newPorts)) {
            commPorts = newPorts;
            Fx.run(() -> serialPorts.set(serialPortsToString()));
        }
    }
    private static boolean hasPortsChanged(SerialPort[] newPorts) {
        if (commPorts.length != newPorts.length) return true;
        for (int i = 0; i < newPorts.length; i++) {
            if (!commPorts[i].getSystemPortName().equals(newPorts[i].getSystemPortName())) return true;
        }
        return false;
    }

    private static String serialPortsToString() {
        return Arrays.stream(commPorts)
                .map(SerialPort::getSystemPortName)
                .collect(Collectors.joining("\n"));
    }
}
