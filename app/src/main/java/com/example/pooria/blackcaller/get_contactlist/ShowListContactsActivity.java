package com.example.pooria.blackcaller.get_contactlist;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.pooria.blackcaller.R;
import com.example.pooria.blackcaller.broadcastReciever.CheckCallPermissionActivity;
import com.example.pooria.blackcaller.pojo.Contacts;
import com.example.pooria.blackcaller.pojo.Information;
import com.example.pooria.blackcaller.pojo.ReadContactRecycler;
import com.example.pooria.blackcaller.send.WebServicesSendDate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowListContactsActivity extends AppCompatActivity {

    private List<ReadContactRecycler> list = new ArrayList<>() ;
    private RecyclerView recyclerView;
    private CustomAdapterReadContactRecycler adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private EditText text;
    String phoneNumber;
    //ListView lv;
    //ArrayList <String> aa= new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_contacts);
       // lv= (ListView) findViewById(R.id.listtt);
        text=findViewById(R.id.edt_searchContacts);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL,false));
        getNumber(this.getContentResolver());

        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

    }

    public void getNumber(final ContentResolver cr)
    {
        Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (phones.moveToNext())
        {
            final String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Log.d("phones :", "Name : " +name.toString());
            Log.d("phones :", " Phone Number :"+phoneNumber.toString());

            //aa.add("Name : "+ name+"  "+"Phone : "+phoneNumber);
            // aa.add( phoneNumber);
            ReadContactRecycler recycler = new ReadContactRecycler();
            recycler.setName(name);
            recycler.setPhone(phoneNumber);
            adapter = new CustomAdapterReadContactRecycler(list,ShowListContactsActivity.this);
            list.add(recycler);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);


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
            Contacts contacts = new Contacts();
            contacts.setName(name.toString());
            contacts.setPhone(phoneNumber);
            Log.d("log", contacts.toString());
            Call<Information> call = date.createComment(contacts.getName(), contacts.getPhone());
            call.enqueue(new Callback<Information>() {
                @Override
                public void onResponse(Call<Information> call, Response<Information> response) {
                    Information body = response.body();
                    Log.d("log", body.toString());
                    Intent intent = new Intent(ShowListContactsActivity.this, CheckCallPermissionActivity.class);
                    startActivity(intent);
                    //Toast.makeText(ShowListContactsActivity.this, "Ook", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Information> call, Throwable t) {
                    //Toast.makeText(ShowListContactsActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        phones.close();
            /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,aa);
            lv.setAdapter(adapter);*/

    }

    private void filter(String text) {

        ArrayList<ReadContactRecycler> filteredList = new ArrayList<>();

        for (ReadContactRecycler item : list) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
            if (item.getPhone().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter.filterList(filteredList);
    }
}