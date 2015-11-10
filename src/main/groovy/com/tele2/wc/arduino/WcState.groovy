package com.tele2.wc.arduino


enum WcState {

    FREE("opened"), BUSY("closed")

    String arduinoStatus

    WcState(String arduinoStatus) {
        this.arduinoStatus = arduinoStatus
    }

    static WcState getFromArduinoStatus(String arduinoStatus) {
        for (WcState state : values()) {
            if (state.arduinoStatus == arduinoStatus) {
                return state
            }
        }
        return null
    }
}