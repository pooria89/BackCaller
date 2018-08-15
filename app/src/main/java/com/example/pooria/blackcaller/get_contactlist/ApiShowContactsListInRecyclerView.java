package com.example.pooria.blackcaller.get_contactlist;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiShowContactsListInRecyclerView {
    public static final String BASE_URL = "http://192.168.1.10/";
    private static Retrofit retrofit;
    public static Retrofit getClient(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
