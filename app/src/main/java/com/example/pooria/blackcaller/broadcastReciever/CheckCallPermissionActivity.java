package com.example.pooria.blackcaller.broadcastReciever;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.pooria.blackcaller.R;

public class CheckCallPermissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_cast_reciever);

        if (ContextCompat.checkSelfPermission(CheckCallPermissionActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(CheckCallPermissionActivity.this, Manifest.permission.READ_PHONE_STATE)) {
                ActivityCompat.requestPermissions(CheckCallPermissionActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            }else {
                ActivityCompat.requestPermissions(CheckCallPermissionActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(CheckCallPermissionActivity.this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "No Permission granted", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }
}
