package com.example.pooria.blackcaller.broadcastReciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.example.pooria.blackcaller.get_contactlist.Api;
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

public class BroadCastRecieverCallActivity extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        try {
            String incoming = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            //String replaceString=incoming.replace("+98","0");
            Toast.makeText(context, " Number Of Calling : " +incoming, Toast.LENGTH_SHORT).show();
            Log.d("phone", " Number Of Calling : " + incoming);
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            OkHttpClient client = new OkHttpClient();

            WebServicesSendDate date = Api.getClient().create(WebServicesSendDate.class);
            date.recievePersonsList(incoming).enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    Users body = response.body();
                    Log.d("phone", body.getName().toString());
                    Log.d("phone", body.getPhone().toString());
                    Toast.makeText(context, body.getName().toString()+" , "+ body.getPhone().toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {
                    Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                }
            });

            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING)) {
                Log.d("phone", "Phone is Ringing");
                //Toast.makeText(context, "Ringing", Toast.LENGTH_SHORT).show();
            }
            if (state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                Log.d("phone", "Recieved");
                Toast.makeText(context, "Recieved", Toast.LENGTH_SHORT).show();
            }
            if (state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_IDLE)) {
                Log.d("phone", "Idle");
                Toast.makeText(context, "Idle", Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
