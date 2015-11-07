package com.tele2.wc.arduino

import gnu.io.*

class UsbListener implements SerialPortEventListener {

    private static final String PORT_NAME = "COM8"
    private BufferedReader input
    private static final int TIME_OUT = 2000
    private static final int DATA_RATE = 9600

    void initialize() {
        def portId = CommPortIdentifier.getPortIdentifier(PORT_NAME)
        def serialPort = portId.open(this.getClass().getName(), TIME_OUT)

        serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE)

        input = serialPort.getInputStream().newReader()

        serialPort.addEventListener(this)
        serialPort.notifyOnDataAvailable(true)
    }

    void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                println input.readLine()
            } catch (Exception e) {
            }
        }
    }

    static void main(String[] args) throws Exception {
        UsbListener main = new UsbListener()
        main.initialize()
        Thread.start { Thread.sleep(1000000) }
    }
}
