package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button write_external;
    Button write_internal;
    Button read_external;
    Button read_internal;
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
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url("https://www.hqu.edu.cn/__local/2/51/57/6C5867D37FC8F2A19085BC56EBD_91FD6E34_2A0EC.jpg")
//                .build();
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                InputStream inputStream = response.body().byteStream();
//                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                    File file = new File(getExternalFilesDir( Environment.DIRECTORY_PICTURES),"hqu.jpg");
//                    FileOutputStream outputStream = new FileOutputStream(file);
//                    bitmap.compress(Bitmap.CompressFormat.JPEG,100, outputStream);
//            }
//        });



    }

    @Override
    public void onClick(View v){
        String fileName ="photo2.jpg";
        File filePath=getExternalFilesDir(null);
        Bitmap bitmapForWriting;
        Bitmap bitmapForReading;
        switch (v.getId()){
            case R.id.button:
                downloadImage();
                break;
            case R.id.button2:
//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.dog);
////                Bitmap bitmap = Bitmap.createBitmap(R.drawable.dog);
//                imageView.setImageBitmap(bitmap);
//                imageView.setImageDrawable(getDrawable(R.drawable.dog));
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
