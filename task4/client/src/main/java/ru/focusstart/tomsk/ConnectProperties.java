package ru.focusstart.tomsk;

class ConnectProperties {
    private String nickName;
    private int portName;
    private String hostName;

    public String getNickName() {
        return nickName;
    }

    public String getHostName() {
        return hostName;
    }

    public int getPortName() {
        return portName;
    }


    ConnectProperties(String nickName, String hostName, int portName) {
        this.nickName = nickName;
        this.hostName = hostName;
        this.portName = portName;
    }
}
