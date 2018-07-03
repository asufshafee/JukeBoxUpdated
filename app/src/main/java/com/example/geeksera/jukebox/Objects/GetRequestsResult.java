package com.example.geeksera.jukebox.Objects;

/**
 * Created by Mir Kamran on 5/7/2018.
 */

public class GetRequestsResult {

    private String Purchasedby;

    private String T_id;

    private String TableNumber;

    private History[] History;
    private RestaurantInfo RestaurantInfo;

    private String Imei;

    private String Purchasedate;

    private String Tokenstatus;

    private String Tcount;

    private String Tokentitle_id;

    public String getPurchasedby ()
    {
        return Purchasedby;
    }

    public void setPurchasedby (String Purchasedby)
    {
        this.Purchasedby = Purchasedby;
    }

    public String getT_id ()
    {
        return T_id;
    }

    public void setT_id (String T_id)
    {
        this.T_id = T_id;
    }

    public String getTableNumber ()
    {
        return TableNumber;
    }

    public void setTableNumber (String TableNumber)
    {
        this.TableNumber = TableNumber;
    }

    public History[] getHistory ()
    {
        return History;
    }

    public void setHistory (History[] History)
    {
        this.History = History;
    }

    public String getImei ()
    {
        return Imei;
    }

    public void setImei (String Imei)
    {
        this.Imei = Imei;
    }

    public String getPurchasedate ()
    {
        return Purchasedate;
    }

    public void setPurchasedate (String Purchasedate)
    {
        this.Purchasedate = Purchasedate;
    }

    public String getTokenstatus ()
    {
        return Tokenstatus;
    }

    public void setTokenstatus (String Tokenstatus)
    {
        this.Tokenstatus = Tokenstatus;
    }

    public String getTcount ()
    {
        return Tcount;
    }

    public void setTcount (String Tcount)
    {
        this.Tcount = Tcount;
    }

    public String getTokentitle_id ()
    {
        return Tokentitle_id;
    }

    public void setTokentitle_id (String Tokentitle_id)
    {
        this.Tokentitle_id = Tokentitle_id;
    }

    public com.example.geeksera.jukebox.Objects.RestaurantInfo getRestaurantInfo() {
        return RestaurantInfo;
    }

    public void setRestaurantInfo(com.example.geeksera.jukebox.Objects.RestaurantInfo restaurantInfo) {
        RestaurantInfo = restaurantInfo;
    }
}
