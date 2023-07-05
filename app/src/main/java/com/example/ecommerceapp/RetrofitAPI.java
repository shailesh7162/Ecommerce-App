package com.example.ecommerceapp;

import com.example.ecommerceapp.Modals.AlluserProduct;
import com.example.ecommerceapp.Modals.DeleteData;
import com.example.ecommerceapp.Modals.LoginData;
import com.example.ecommerceapp.Modals.RegisterData;
import com.example.ecommerceapp.Modals.UserProduct;
import com.example.ecommerceapp.Modals.addProductData;
import com.example.ecommerceapp.Modals.viewProductData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitAPI
{
    @FormUrlEncoded
    @POST("Register.php")
    Call<RegisterData> REGISTER_USER_CALL(@Field("name") String name, @Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginData> LOGIN_USER_CALL(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("addProduct.php")
    Call<addProductData> PRODUCT_USER_CALL(@Field("userid") int usrid, @Field("pname") String pname, @Field("pprize") String pprize, @Field("pdes") String pdes, @Field("productimage") String productimage);

    @FormUrlEncoded
    @POST("allproduct.php")
    Call<AlluserProduct>ALLUSER_PRODUCT_CALL(@Field("userid") Integer id);

    @FormUrlEncoded
    @POST("userproduct.php")
    Call<UserProduct>USER_PRODUCT_CALL(@Field("userid") Integer uid);

    @FormUrlEncoded
    @POST("viewProduct.php")
    Call<viewProductData> VIEW_PRODUCT_DATA_CALL(@Field("userid") int usrid);

    @FormUrlEncoded
    @POST("deleteproduct.php")
    Call<DeleteData> DELETE_MODEL_CALL (@Field("id")int id);

    @FormUrlEncoded
    @POST("updateproduct.php")
    Call<RegisterData> call_update (@Field("id") int id,@Field("name") String name,@Field("price") String price,@Field("description")String des,@Field("imagedata") String imagedata,@Field("imagename")String imagename);
}


