package com.tustar.demo.ui.signal;

/**
 * Created by tustar on 11/19/16.
 */

public class SignalInfo {

    private String network;
    private String sim;
    private String prop1;
    private String prop2;

    public SignalInfo() {

    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public String getProp1() {
        return prop1;
    }

    public void setProp1(String prop1) {
        this.prop1 = prop1;
    }

    public String getProp2() {
        return prop2;
    }

    public void setProp2(String prop2) {
        this.prop2 = prop2;
    }

    @Override
    public String toString() {
        return "SignalInfo{" +
                "network='" + network + '\'' +
                ", sim='" + sim + '\'' +
                ", prop1='" + prop1 + '\'' +
                ", prop2='" + prop2 + '\'' +
                '}';
    }
}
