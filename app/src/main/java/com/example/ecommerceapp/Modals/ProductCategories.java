package com.example.ecommerceapp.Modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductCategories {

    @SerializedName("ID")
    @Expose
    private String id;
    @SerializedName("UID")
    @Expose
    private String uid;
    @SerializedName("PNAME")
    @Expose
    private String pname;
    @SerializedName("PDISC")
    @Expose
    private String pdisc;
    @SerializedName("PPRICE")
    @Expose
    private String pprice;
    @SerializedName("PIMAGE")
    @Expose
    private String pimage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPdisc() {
        return pdisc;
    }

    public void setPdisc(String pdisc) {
        this.pdisc = pdisc;
    }

    public String getPprice() {
        return pprice;
    }

    public void setPprice(String pprice) {
        this.pprice = pprice;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }


}
