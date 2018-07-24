package com.example.formacio.cameraexercise;

import android.Manifest;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;
import java.security.Permission;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    Button takePicture;
    Button selectFile;
    Uri pictureFile;
    ImageView image;
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
        image = (ImageView)findViewById(R.id.image);

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
                }/*else{
                    Toast.makeText(this, "No number to call", Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        webSurfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url = webAddress.getText().toString();
                if(url.contains("http:")) {
                    surfInternet(url);
                }/*else{
                    Toast toast = Toast.makeText(this, "Invalid address", Toast.LENGTH_SHORT);
                    toast.show();
                }*/
            }
        });




    }

    protected void takeAPicture(){//TODO

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        pictureFile = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureFile);

        startActivityForResult(intent, 100);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if(requestCode == 100){
            if(resultCode == RESULT_OK){
                image.setImageURI(pictureFile);
            }
        }

    }

    protected void selectAFile(){//TODO

        Uri images;
        images = new Uri.fromFile(getOutputMediaFile());
        InputStream imageDir = Uri.openInputStream(images);


    }

    public boolean isStorageReadable() {
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==0){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED){
                takePicture.setEnabled(true);
            }
        }
    }
    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "CameraExercise");
        if(!mediaStorageDir.exists()){
            if(!mediaStorageDir.mkdirs()){
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath()+ File.separator + "IMG_" +
        timeStamp + ".jpg");


    }
}
