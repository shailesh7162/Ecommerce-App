package com.example.ecommerceapp.Modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product_get_model {
    @SerializedName("connection")
    @Expose
    private Integer connection;
    @SerializedName("result")
    @Expose
    private Integer result;
    @SerializedName("productdata")
    @Expose
    private List<ProductCategories> productdata;

    public Integer getConnection() {
        return connection;
    }

    public void setConnection(Integer connection) {
        this.connection = connection;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public List<ProductCategories> getProductdata() {
        return productdata;
    }

    public void setProductdata(List<ProductCategories> productdata) {
        this.productdata = productdata;
    }
}






