package com.example.ecommerceapp.activity;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.example.ecommerceapp.GetPosition;
import com.example.ecommerceapp.Modals.AlluserProduct;
import com.example.ecommerceapp.Modals.Productdatum;
import com.example.ecommerceapp.Modals.UserProduct;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.Retro_Instance_Class;
import com.example.ecommerceapp.adapter.User_product_adapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Product_showActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    User_product_adapter adapter;
    List<Productdatum> getAllproduct=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_show);
        recyclerView = findViewById(R.id.nav_show_recycler);

        Retro_Instance_Class.CallApi().ALLUSER_PRODUCT_CALL(1).enqueue(new Callback<AlluserProduct>() {
            @Override
            public void onResponse(Call<AlluserProduct> call, Response<AlluserProduct> response) {

                for (int i=0;i<response.body().getProductdata().size();i++)
                {
                    getAllproduct.add(response.body().getProductdata().get(i));
                }

                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<AlluserProduct> call, Throwable t) {

                Log.d("GGG", "onFailure: "+t.getLocalizedMessage());

            }
        });
    }
}