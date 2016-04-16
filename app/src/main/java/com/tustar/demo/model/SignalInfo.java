package com.tustar.demo.model;

/**
 * Created by tustar on 16-2-19.
 */
public class SignalInfo {

    private String sim;
    private String network;
    private String prop1;
    private String prop2;

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
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
                "sim='" + sim + '\'' +
                ", network='" + network + '\'' +
                ", prop1='" + prop1 + '\'' +
                ", prop2='" + prop2 + '\'' +
                '}';
    }
}
