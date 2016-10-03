package io.github.evilofsoul.openwol.core;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Yevhenii on 02.08.2016.
 */
public class Machine implements Parcelable {
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

    public Machine(Parcel parcel){
        id = parcel.readInt();
        mac = MacAddress.create(parcel.readString());
        name = parcel.readString();
        ip = parcel.readString();
        port = parcel.readInt();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(mac.toString());
        parcel.writeString(name);
        parcel.writeString(ip);
        parcel.writeInt(port);
    }

    /** Static field used to regenerate object, individually or as arrays */
    public static final Parcelable.Creator<Machine> CREATOR = new Parcelable.Creator<Machine>() {
        public Machine createFromParcel(Parcel machine) {
            return new Machine(machine);
        }
        public Machine[] newArray(int size) {
            return new Machine[size];
        }
    };
}
