package com.example.pooria.blackcaller.main;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pooria.blackcaller.R;

public class InsertContactsActivity extends AppCompatActivity {
    private EditText editText_name , editText_phone;
    private Button button_insert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_contacts);
        editText_name = findViewById(R.id.edt_name);
        editText_phone = findViewById(R.id.edt_phone);
        button_insert = findViewById(R.id.btn_insert);
        button_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent addContactIntent = new Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI);
                    addContactIntent.putExtra(ContactsContract.Intents.Insert.NAME,editText_name.getText().toString());
                    addContactIntent.putExtra(ContactsContract.Intents.Insert.PHONE,editText_phone.getText().toString());
                    startActivity(addContactIntent);
                    Toast.makeText(InsertContactsActivity.this, "Insert Compeleted", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
