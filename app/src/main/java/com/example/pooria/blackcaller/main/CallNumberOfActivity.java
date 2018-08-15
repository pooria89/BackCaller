package com.example.pooria.blackcaller.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pooria.blackcaller.R;
import com.example.pooria.blackcaller.get_contactlist.ShowListContactsActivity;
import com.example.pooria.blackcaller.pojo.ReadLogContacts;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CallNumberOfActivity extends AppCompatActivity {
    private static final int MISSED_CALL_TYPE = 0;
    private FloatingActionButton floatingActionButton;
    private EditText editText_callNumber, editText_gettext,editTextSearch;
    private Button button_call;
    private ImageView view_call, view_sms, view_contacts, view_insert;
    final int SEND_SMS_PERMISSION_REQUEST_CODE = 111;
    private List<ReadLogContacts> list= new ArrayList<>() ;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_number_of);
        bind();
        requestReadCallLog();
        view_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = editText_callNumber.getText().toString();
                if (!TextUtils.isEmpty(s)) {
                    String dial = "tel:" + s;
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                } else {
                    Toast.makeText(CallNumberOfActivity.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        view_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_Sms();
            }
        });

        view_contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContacts();
            }
        });

        view_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertContacts();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view_call.setVisibility(View.VISIBLE);
                view_sms.setVisibility(View.VISIBLE);
                view_contacts.setVisibility(View.VISIBLE);
                view_insert.setVisibility(View.VISIBLE);
                editText_gettext.setVisibility(View.VISIBLE);
                floatingActionButton.setVisibility(View.INVISIBLE);

            }
        });

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void send_Sms() {
        if (checkPermission(Manifest.permission.SEND_SMS)) {
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},
                    SEND_SMS_PERMISSION_REQUEST_CODE);
        }
        String number = editText_callNumber.getText().toString();
        String text = editText_gettext.getText().toString();
        if (!TextUtils.isEmpty(number) && !TextUtils.isEmpty(text)) {

            if (checkPermission(Manifest.permission.SEND_SMS)) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(number, null, text, null, null);
            } else {
                Toast.makeText(CallNumberOfActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private boolean checkPermission(String permission) {
        int checkPermission = ContextCompat.checkSelfPermission(this, permission);
        return (checkPermission == PackageManager.PERMISSION_GRANTED);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case SEND_SMS_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    if (ContextCompat.checkSelfPermission(CallNumberOfActivity.this, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "No permission", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
    private void showContacts() {
        Intent intent = new Intent(CallNumberOfActivity.this, ShowListContactsActivity.class);
        startActivity(intent);
    }
    private void insertContacts() {
        Intent intent = new Intent(CallNumberOfActivity.this, InsertContactsActivity.class);
        startActivity(intent);

    }
    private String getCallDetails() {
        StringBuffer sb = new StringBuffer();
        Cursor manageCursur = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
        int number = manageCursur.getColumnIndex(CallLog.Calls.NUMBER);
        int type = manageCursur.getColumnIndex(CallLog.Calls.TYPE);
        int date = manageCursur.getColumnIndex(CallLog.Calls.DATE);
        int duration = manageCursur.getColumnIndex(CallLog.Calls.DURATION);
        sb.append("Call Detalis :" + "\n\n");
        while (manageCursur.moveToNext()) {
            String num = manageCursur.getString(number);
            String dur = manageCursur.getString(duration);
            String typee = manageCursur.getString(type);
            String datee = manageCursur.getString(date);
            Date callDayTime = new Date(Long.valueOf(datee));
            SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yy , HH:mm");
            String dateString = formater.format(callDayTime);
            String callDuration = manageCursur.getString(duration)+" Second ";
            String dir = null;
            int dircode = Integer.parseInt(typee);
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "outgoing Call";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    dir = "incoming Call";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    dir = "Missed Type";
                    break;
            }
            sb.append("\nPhone Number:" + num + "\nCall TYpe:" + dir + "\nCall Date:" + dateString +
                    "\nCall Duration:" + callDuration);
            sb.append("\n----------------");
            Log.d("phone", num.toString());
            Log.d("phone", dateString.toString());
            Log.d("phone", callDuration.toString());

            adapter = new CustomAdapter(CallNumberOfActivity.this,list);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL,false));
                ReadLogContacts contacts = new ReadLogContacts();
                contacts.setNumber(num);
                contacts.setDate(dateString);
                contacts.setDuration(callDuration);
                list.add(contacts);
        }

        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        manageCursur.close();
        return sb.toString();
    }
    private void requestReadCallLog(){
        if (ContextCompat.checkSelfPermission(CallNumberOfActivity.this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(CallNumberOfActivity.this, Manifest.permission.READ_CALL_LOG)) {
                ActivityCompat.requestPermissions(CallNumberOfActivity.this, new String[]{Manifest.permission.READ_CALL_LOG}, 1);
            } else {
                ActivityCompat.requestPermissions(CallNumberOfActivity.this, new String[]{Manifest.permission.READ_CALL_LOG}, 1);
            }
        } else {
            Log.d("log", getCallDetails());
        }
    }

    private void bind(){
        floatingActionButton = findViewById(R.id.fab);
        editText_callNumber = findViewById(R.id.edt_getnumber);
        //button_call = findViewById(R.id.btn_call);
        view_call = findViewById(R.id.img_);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        view_contacts = findViewById(R.id.img_contacts);
        editTextSearch = findViewById(R.id.editTextSearch);
        view_insert = findViewById(R.id.img_insert);
        view_sms = findViewById(R.id.img_sms);
        editText_gettext = findViewById(R.id.edt_gettext);
    }
    private void filter(String text) {

        ArrayList<ReadLogContacts> filteredList = new ArrayList<>();

        for (ReadLogContacts item : list) {
            if (item.getNumber().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter.filterList(filteredList);
    }


}
