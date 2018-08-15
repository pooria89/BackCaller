package com.example.pooria.blackcaller.get_contactlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pooria.blackcaller.R;
import com.example.pooria.blackcaller.pojo.Item;
import com.example.pooria.blackcaller.recieve.SearchDate;
import com.example.pooria.blackcaller.send.WebServicesSendDate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.nio.file.Files;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowListOfContactsRecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_contacts);
        bind();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient client = new OkHttpClient();

        WebServicesSendDate date = ApiShowContactsListInRecyclerView.getClient().create(WebServicesSendDate.class);
        date.getContactsList().enqueue(new Callback<List<com.example.pooria.blackcaller.get_contactlist.Item>>() {
            @Override
            public void onResponse(Call<List<com.example.pooria.blackcaller.get_contactlist.Item>> call, Response<List<com.example.pooria.blackcaller.get_contactlist.Item>> response) {
                List<com.example.pooria.blackcaller.get_contactlist.Item> body = response.body();
                for (int i =0;i<body.size();i++) {
                    String name = body.get(i).getName();
                    String phone = body.get(i).getPhone();
                    Log.d("date", "name " + name.toString() + " ," + "phone" + phone.toString());
                }
            }

            @Override
            public void onFailure(Call<List<com.example.pooria.blackcaller.get_contactlist.Item>> call, Throwable t) {
                Log.d("date", t.toString());
            }
        });
    }
    private void bind() {

    }
}
