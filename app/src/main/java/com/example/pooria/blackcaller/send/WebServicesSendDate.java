package com.example.pooria.blackcaller.send;

import com.example.pooria.blackcaller.broadcastReciever.Users;
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

    @GET("?phone=[]")
    Call<List<Item>> recieveDate(@Query("phone") String phone);

    @GET("?phone=[]")
    Call<Users> recievePersonsList(@Query("phone") String phone);

    @FormUrlEncoded
    @POST("/SMySql.php")
        Call<Information> createComment(@Field("name") String name ,@Field("family")String family ,@Field("phone")String phone);

    @FormUrlEncoded
    @POST("/SaveContacts.php")
    Call<Information> createComment(@Field("name") String name ,@Field("phone")String phone);

    @GET("/showContactsDb.php")
    Call<List<com.example.pooria.blackcaller.get_contactlist.Item>>getContactsList();

}
