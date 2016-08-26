package io.github.evilofsoul.openwol.core;

import java.io.Serializable;

/**
 * Created by Yevhenii on 02.08.2016.
 */
public class Machine implements Serializable {
    private int id;
    private MacAddress mac;
    private String name;
    private String ip;
    private int port;

    public Machine(int id, String name, MacAddress mac, String ip, int port) {
        this.id = id;
        this.mac = mac;
        this.name = name;
        this.port = port;
        this.ip = ip;
    }

    public Machine() {
        this.id = -1;
    }

    public int getId() {
        return id;
    }

    public MacAddress getMac() {
        return mac;
    }

    public void setMac(MacAddress mac) {
        this.mac = mac;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
