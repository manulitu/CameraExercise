package com.example.formacio.cameraexercise.View;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
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

import com.example.formacio.cameraexercise.R;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.security.Permission;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    Button takePicture;
    Button selectFile;
    Uri pictureFile;
    String filePath;
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
        setContentView(R.layout.activity_main_constraint);

        takePicture = (Button)findViewById(R.id.takePicture);
        selectFile = (Button)findViewById(R.id.selectFile);
        phoneNumberContainer = (EditText) findViewById(R.id.phoneNumberContainer);
        webAddress = (EditText)findViewById(R.id.webAddress);
        webSurfer = (Button)findViewById(R.id.webSurfer);
        image = (ImageView)findViewById(R.id.image);
        phoneCaller = (Button)findViewById(R.id.phoneCaller);

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==0){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED){
                takePicture.setEnabled(true);
            }
        }
    }
    protected void takeAPicture(){//TODO

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        pictureFile = Uri.fromFile(getOutputMediaFile());
        if(filePath.equals("")){
            filePath = pictureFile.getPath();
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureFile);

        startActivityForResult(intent, 100);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if(requestCode == 100){
            if(resultCode == Activity.RESULT_OK){
                image.setImageURI(pictureFile);
            }
        }

        if(requestCode == 11888){
            if(resultCode == Activity.RESULT_OK){
                Uri uri = null;
                if(data != null){
                    uri = data.getData();
                    Bitmap imageBitmap = null;
                    try {
                        imageBitmap = getBitmapFromUri(uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    image.setImageBitmap(imageBitmap);
                }
            }
        }
    }

    protected void selectAFile(){//TODO


        Intent selectFileIntent= new Intent(Intent.ACTION_OPEN_DOCUMENT);

        selectFileIntent.addCategory(Intent.CATEGORY_OPENABLE);
        selectFileIntent.setType("image/*");
        startActivityForResult(selectFileIntent, 11888);
    }

    private Bitmap getBitmapFromUri(Uri uri)throws IOException{
        ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
        assert parcelFileDescriptor != null;
        FileDescriptor fileDescriptor;
        fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        return BitmapFactory.decodeFileDescriptor(fileDescriptor);
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
