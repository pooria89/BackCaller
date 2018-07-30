package com.example.pooria.blackcaller.recieve;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pooria.blackcaller.R;
import com.example.pooria.blackcaller.pojo.Gett;
import com.example.pooria.blackcaller.pojo.Item;
import com.example.pooria.blackcaller.send.WebServicesSendDate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchDate extends AppCompatActivity {
    private EditText searh ;
    private TextView show;
    private Button search_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_date);
        BindDate();

        search_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                OkHttpClient client = new OkHttpClient();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.1.10/Search.php/")
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                WebServicesSendDate date = API.getClient().create(WebServicesSendDate.class);
                Call<List<Item>> call = date.recieveDate(searh.getText().toString());
                call.enqueue(new Callback<List<Item>>() {

                    @Override
                    public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                        List<Item> body = response.body();
                        for (int i =0;i<body.size();i++){
                            Log.d("log", body.get(i).getName().toString());
                            Log.d("log", body.get(i).getFamily().toString());
                            Log.d("log", body.get(i).getPhone().toString());

                            Toast.makeText(SearchDate.this, "Name: "+body.get(i).getName().toString() + "  "+
                                    "Family: "+ body.get(i).getFamily().toString()+"  "+"Phone: "+body.get(i).getPhone().toString(), Toast.LENGTH_SHORT).show();


                        }
                    }

                    @Override
                    public void onFailure(Call<List<Item>> call, Throwable t) {
                        Log.d("log", t.toString());
                       // Toast.makeText(SearchDate.this, t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    void BindDate() {
        searh = findViewById(R.id.txtSearch);
        show = findViewById(R.id.textviewshow);
        search_date = findViewById(R.id.btnSearch);
    }

}
