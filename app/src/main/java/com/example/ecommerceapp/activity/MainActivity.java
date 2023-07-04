package com.example.ecommerceapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.Fragment.AddProductFragment;
import com.example.ecommerceapp.Fragment.ShowAllProductFragment;
import com.example.ecommerceapp.Modals.addProductData;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.Retro_Instance_Class;
import com.google.android.material.navigation.NavigationView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    DrawerLayout drawer;
    NavigationView navigation;
    Toolbar toolbar;
    TextView drawer_name;
    EditText pname,pdecs,pprice;
    Button add_product_btn;
    ImageView imageView,headerImg;
    String image,imgPath;
    String imgName;
    int cnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer = findViewById(R.id.drawer);
        navigation = findViewById(R.id.navigation);
        toolbar = findViewById(R.id.toolbar);

        preferences = getSharedPreferences("Login_pref", MODE_PRIVATE);
        editor = preferences.edit();

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this, drawer, toolbar, R.string.open, R.string.close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        addfragment(new AddProductFragment());
        
        String name = preferences.getString("name", "User");
        View headerView = navigation.getHeaderView(0);
        drawer_name = (TextView) headerView.findViewById(R.id.drawer_name);
        drawer_name.setText("" + name);
        int uid;
        uid= preferences.getInt("uid",0);
        headerImg=headerView.findViewById(R.id.HeaderImage);

         headerImg.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 CropImage.activity()
                         .setGuidelines(CropImageView.Guidelines.ON)
                         .start(MainActivity.this);
                 cnt=1;
             }
         });
        imgName=imgName+new Random().nextInt(10000)+".jpg";
        Bitmap bitmap= ((BitmapDrawable)headerImg.getDrawable()).getBitmap();
        imgPath=saveToInternalStorage(bitmap);
        imgPath=imgPath+"/"+imgName;
        loadImageFromStorage(imgPath);

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.nav_add_product)
                {
                    Dialog dialog=new Dialog(MainActivity.this);
                    dialog.setContentView(R.layout.dailog_main);
                    add_product_btn=dialog.findViewById(R.id.add_product_btn);
                    pname=dialog.findViewById(R.id.pro_name_edt);
                    pdecs=dialog.findViewById(R.id.pro_dec_edt);
                    pprice=dialog.findViewById(R.id.pro_price_edt);
                    imageView=dialog.findViewById(R.id.add_product_img);

                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            CropImage.activity()
                                    .setGuidelines(CropImageView.Guidelines.ON)
                                    .start(MainActivity.this);
                            cnt=2;
                        }
                    });
                    add_product_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            String name,descri,price;
                            name=pname.getText().toString();
                            descri=pdecs.getText().toString();
                            price=pprice.getText().toString();
                            int r=new Random().nextInt(100);
                            Bitmap bitmap= ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                            ByteArrayOutputStream bos=new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG,30,bos);
                            byte[] byteArray = bos.toByteArray();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                image= Base64.getEncoder().encodeToString(byteArray);
                            }
                            Retro_Instance_Class.CallApi().PRODUCT_USER_CALL(uid,name,price,descri,image).enqueue(new Callback<addProductData>() {
                                @Override
                                public void onResponse(Call<addProductData> call, Response<addProductData> response) {
                                    Log.d("ttt", "onResponse: "+response.body().toString());
                                    if (response.body().getConnection() == 1)
                                    {
                                        if (response.body().getProductaddd() == 1)
                                        {
                                            Toast.makeText(MainActivity.this, "Product Add", Toast.LENGTH_LONG).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(MainActivity.this, "Product Not Add", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    else {
                                        Toast.makeText(MainActivity.this, "Somthing Went Wrong", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<addProductData> call, Throwable t) {

                                }
                            });
                            dialog.dismiss();

                        }
                    });
                    dialog.show();

                }if (item.getItemId()==R.id.nav_show_product)
                {
                    addfragment(new ShowAllProductFragment());
                    drawer.closeDrawer(Gravity.LEFT);

                }if (item.getItemId()==R.id.nav_show_all_product)
                {
                    addfragment(new ShowAllProductFragment());
                    drawer.closeDrawer(Gravity.LEFT);
                }if (item.getItemId()==R.id.menu_logout)
                {
                    editor.putBoolean("logged_in",false);
                    editor.commit();
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    finish();
                }
                return true;
            }
        });
    }
    private void addfragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                Uri resultUri = result.getUri();

                if(cnt==1)
                {
                    headerImg.setImageURI(resultUri);
                }
                else if(cnt==2)
                {
                    imageView.setImageURI(resultUri);
                }

            }

            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void loadImageFromStorage(String path) {

        try {
            File f=new File(path);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));

            headerImg.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage) {
        //ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory=getApplicationContext().getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir

        File mypath=new File(directory,imgName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();

    }

}