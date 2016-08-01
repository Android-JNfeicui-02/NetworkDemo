package edu.feicui.networktest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //  http://cloud.bmob.cn/0906a62b462a3082/getMemberBySex
    String urlAddress = "http://cloud.bmob.cn/0906a62b462a3082/";
    String method     = "getMemberBySex?";
    private Button mBtnGet;
    private Button mBtnPost;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnGet = (Button) findViewById(R.id.btn_get);
        mBtnPost = (Button) findViewById(R.id.btn_post);

        mBtnGet.setOnClickListener(this);
        mBtnPost.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get:
                doGet("boy");
                break;

            case R.id.btn_post:
                doPost("girl");
                break;
        }
    }

    private void doPost(final String value) {
        // Post 要比 get 麻烦很多

        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlAddress + method);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //connection.setDoInput(true);
                    // 需要上传 / 下载
                    //connection.setDoOutput(true);
                    connection.setRequestMethod("POST");
                    //connection.setUseCaches(false);
                    //connection.setRequestProperty("Accept-Charset","UTF-8");
                    //connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                    connection.connect();

                    String content = "sex=" + value;
                    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());

                    //Log.d(TAG, "stream: " + stream.toString());
                    outputStream.writeBytes(content);
                    //Log.d(TAG, "outputStream: " + outputStream.toString());
                    outputStream.flush();
                    outputStream.close();

                    if (connection.getResponseCode() == 200) {
                        InputStream inputStream = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder builder = new StringBuilder();
                        String readLine = "";
                        while ((readLine = reader.readLine()) != null) {
                            builder.append(readLine);
                        }

                        inputStream.close();
                        reader.close();
                        connection.disconnect();
                        Log.d(TAG, "StringBuilder: " + builder.toString());
                    } else {
                        Log.d(TAG, "联网请求失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    public void doGet(String value) {
        final String getUrl = urlAddress + method + value;
        Log.d(TAG, "getUrl: " + getUrl);
        // 进行网络请求

        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(getUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    if (connection.getResponseCode() == 200) {
                        InputStream inputStream = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder builder = new StringBuilder();
                        String readLine = "";
                        while ((readLine = reader.readLine()) != null) {
                            builder.append(readLine);
                        }

                        inputStream.close();
                        reader.close();
                        connection.disconnect();
                        Log.d(TAG, "StringBuilder: " + builder.toString());
                    } else {
                        Log.d(TAG, "联网请求失败");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
