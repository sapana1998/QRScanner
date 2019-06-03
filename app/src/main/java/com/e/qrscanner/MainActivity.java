package com.e.qrscanner;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

public class MainActivity extends AppCompatActivity {

    TextView barcodeResult;
    public String bhai_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        barcodeResult=findViewById(R.id.barcode_Content);
        isCameraPermissionGranted();
    }

    private boolean isCameraPermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)//M for Marshmallow
        {
            if(checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
                return true;
            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
                Toast.makeText(this, "PERMISSION RE-GRANT", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        else {
            Toast.makeText(this, "OS is not supporting", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
    public void scanBarcode(View view){
        Intent intent=new Intent(MainActivity.this,ScanBarcodeActivity.class);
        startActivityForResult(intent,0);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra("barcode"); //
                    bhai_value = barcode.displayValue;
                    barcodeResult.setText(" Barcode Value::" + bhai_value);
                    Toast.makeText(this, bhai_value, Toast.LENGTH_SHORT).show();
                } else {
                    barcodeResult.setText("No Barcode Captured");
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            //Create your Directory here
        }
    }

}
