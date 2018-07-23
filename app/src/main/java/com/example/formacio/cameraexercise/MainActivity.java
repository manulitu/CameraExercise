package com.example.formacio.cameraexercise;

import android.Manifest;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.Permission;

public class MainActivity extends AppCompatActivity {


    Button takePicture;
    Button selectFile;
    EditText phoneNumberContainer;
    Button phoneCaller;
    String number;
    EditText webAddress;
    Button webSurfer;
    String url;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        takePicture = (Button)findViewById(R.id.takePicture);
        selectFile = (Button)findViewById(R.id.selectFile);
        phoneNumberContainer = (EditText) findViewById(R.id.phoneNumberContainer);
        webAddress = (EditText)findViewById(R.id.webAddress);
        webSurfer = (Button)findViewById(R.id.webSurfer);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            takePicture.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeAPicture();//TODO
            }
        });

        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectAFile();//TODO
            }
        });

        phoneCaller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number = phoneNumberContainer.getText().toString();
                if(number.length() > 0){
                    callNumber(number);//TODO
                }else{
                    Toast.makeText(this, "No number to call", Toast.LENGTH_SHORT).show();
                }
            }
        });

        webSurfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url = webAddress.getText().toString();
                if(url.contains("http:")) {
                    surfInternet(url);
                }else{
                    Toast.makeText(this, "Invalid address", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    protected void takeAPicture(){//TODO

    }

    protected void selectAFile(){//TODO

    }

    protected void callNumber(String number){
        Uri uriNumber = Uri.parse(number);
        Intent callIntent = new Intent(Intent.ACTION_DIAL, uriNumber);
        startActivity(callIntent);
    }

    protected void surfInternet(String url){
        //TODO
        Uri address = Uri.parse(url);
        Intent internetIntent = new Intent(Intent.ACTION_VIEW, address);
        startActivity(internetIntent);


    }
}
