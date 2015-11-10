package com.tele2.wc.arduino

import gnu.io.CommPortIdentifier
import gnu.io.SerialPort
import gnu.io.SerialPortEvent
import gnu.io.SerialPortEventListener
import groovyx.net.http.RESTClient

class UsbListener implements SerialPortEventListener {

    static final String PORT_NAME = "COM8"
    BufferedReader input
    static final int TIME_OUT = 2000
    static final int DATA_RATE = 9600
    RESTClient restClient

    void initialize() {
        restClient = new RESTClient("http://localhost:5050/")
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
                def line = input.readLine()
                def id = line.tokenize(":").first()
                def status = WcState.getFromArduinoStatus(line.tokenize(":").last())

                switch (status) {

                    case WcState.FREE:
                        restClient.post(path : "release/$id")
                        break
                    case WcState.BUSY:
                        restClient.post(path : "occupy/$id")
                        break
                }
                println "$id:$status"
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
