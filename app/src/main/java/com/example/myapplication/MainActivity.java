package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button write_external;
    Button read_external;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        write_external = findViewById(R.id.button);
        read_external = findViewById(R.id.button2);


        write_external.setOnClickListener(this);
        read_external.setOnClickListener(this);



    }

    @Override
    public void onClick(View v){
        String fileName ="photo2.jpg";
        File filePath=getExternalFilesDir(null);
        switch (v.getId()){
            case R.id.button:
                downloadImage();
                break;
            case R.id.button2:
                try{
                    File file = new File(filePath,fileName);
                    FileInputStream fileInputStream = new FileInputStream(file);
                    Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
                    fileInputStream.close();
                    imageView.setImageBitmap(bitmap);
                }catch (IOException e){
                    e.printStackTrace();
                }
            default:
                break;

        }
    }
    private void downloadImage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    URL url = new URL("https://www.baidu.com/img/bd_logo1.png");
                    URLConnection connection = (URLConnection) url.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    File file = new File(getExternalFilesDir(null), "photo2.jpg");
                    FileOutputStream outputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG,100, outputStream);
                    inputStream.close();
                    outputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
