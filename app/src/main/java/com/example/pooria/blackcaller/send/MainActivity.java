package com.example.pooria.blackcaller.send;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.pooria.blackcaller.R;
import com.example.pooria.blackcaller.broadcastReciever.BroadCastRecieverCallActivity;
import com.example.pooria.blackcaller.pojo.Information;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private EditText name,family,phone;
    private Button register,test;
    //private WebServicesSendDate webServicesSendDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BindDate();

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BroadCastRecieverCallActivity.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpClient client = new OkHttpClient();
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.1.10")
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                WebServicesSendDate date = retrofit.create(WebServicesSendDate.class);
                Information information = new Information();
                information.setName(name.getText().toString());
                information.setFamily(family.getText().toString());
                information.setPhone(phone.getText().toString());
                name.setText("");
                family.setText("");
                phone.setText("");
                //Toast.makeText(MainActivity.this, "Date Inserted", Toast.LENGTH_SHORT).show();
                Call<Information> call = date.createComment(information.getName(),
                        information.getFamily(),
                        String.valueOf(information.getPhone()));
                call.enqueue(new Callback<Information>() {
                    @Override
                    public void onResponse(Call<Information> call, Response<Information> response) {
                        Information body = response.body();
                    }

                    @Override
                    public void onFailure(Call<Information> call, Throwable t) {
                        //Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("error", t.toString());
                    }
                });



                /*Information information = new Information(
                        name.getText().toString(),
                        family.getText().toString(),
                        Integer.parseInt(phone.getText().toString())
                );
                sendNetworkRequest(information);*/
            }
        });
    }

    void BindDate(){
        name = findViewById(R.id.txtName);
        family = findViewById(R.id.txtFamily);
        phone = findViewById(R.id.txtPhone);
        register = findViewById(R.id.btnReg);
        test = findViewById(R.id.test);
    }

    /*private void sendNetworkRequest(Information information) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://127.0.0.1/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        WebServicesSendDate date = retrofit.create(WebServicesSendDate.class);
        Call<Information>call = date.createComment(information.getName().toString(),
                information.getFamily().toString(),
                information.getPhone());
        call.enqueue(new Callback<Information>() {
            @Override
            public void onResponse(Call<Information> call, Response<Information> response) {
                //Information body = response.body();

                Toast.makeText(MainActivity.this, "ok"+response.body().getName().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Information> call, Throwable t) {
                t.toString();
                Toast.makeText(MainActivity.this, "something wrong ..", Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}
