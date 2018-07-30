package com.example.pooria.blackcaller.send;

import com.example.pooria.blackcaller.pojo.Gett;
import com.example.pooria.blackcaller.pojo.Information;
import com.example.pooria.blackcaller.pojo.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WebServicesSendDate {

    /*@Headers("Content-Type: application/json")
    @GET("/?phone={}")
    Call<List<Gett>> recieveDate(@Query("phone") String phone);*/

    @GET("?phone=[]")
    Call<List<Item>> recieveDate(@Query("phone") String phone);

/*
    @GET("?phone={}")
    Call<List<People>> getinf();*/

    /*@POST("/GetDate.php?name={}&family={}&phone={}")
    Call<Information> createComment(@Body Information information);*/
    @FormUrlEncoded
    @POST("/SMySql.php")
        Call<Information> createComment(@Field("name") String name ,@Field("family")String family ,@Field("phone")String phone);
}
