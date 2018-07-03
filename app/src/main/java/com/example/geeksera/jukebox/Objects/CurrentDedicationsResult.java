
package com.example.geeksera.jukebox.Objects;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class CurrentDedicationsResult {

    @SerializedName("D_id")
    private String mDId;
    @SerializedName("DedicatedBy")
    private String mDedicatedBy;
    @SerializedName("Dedicatedto")
    private String mDedicatedto;
    @SerializedName("DedicationType")
    private String mDedicationType;
    @SerializedName("Dedicationmessage")
    private String mDedicationmessage;
    @SerializedName("DeviceId")
    private String mDeviceId;
    @SerializedName("Q_id")
    private String mQId;

    public String getDId() {
        return mDId;
    }

    public void setDId(String DId) {
        mDId = DId;
    }

    public String getDedicatedBy() {
        return mDedicatedBy;
    }

    public void setDedicatedBy(String DedicatedBy) {
        mDedicatedBy = DedicatedBy;
    }

    public String getDedicatedto() {
        return mDedicatedto;
    }

    public void setDedicatedto(String Dedicatedto) {
        mDedicatedto = Dedicatedto;
    }

    public String getDedicationType() {
        return mDedicationType;
    }

    public void setDedicationType(String DedicationType) {
        mDedicationType = DedicationType;
    }

    public String getDedicationmessage() {
        return mDedicationmessage;
    }

    public void setDedicationmessage(String Dedicationmessage) {
        mDedicationmessage = Dedicationmessage;
    }

    public String getDeviceId() {
        return mDeviceId;
    }

    public void setDeviceId(String DeviceId) {
        mDeviceId = DeviceId;
    }

    public String getQId() {
        return mQId;
    }

    public void setQId(String QId) {
        mQId = QId;
    }

}
