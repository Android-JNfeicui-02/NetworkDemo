package edu.feicui.okhttpdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    String urlAddress = "http://cloud.bmob.cn/0906a62b462a3082/";
    String method     = "getMemberBySex";
    private static final String TAG = "MainActivity";
    OkHttpClient mClient;

    String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mClient = new OkHttpClient();


    }

    public void doGet(View view) {
        new Thread() {
            @Override
            public void run() {
                // request阶段
                Request request = new Request.Builder().url(urlAddress + method).build();

                // Response阶段
                try {
                    Response response = mClient.newCall(request).execute();

                    if (response.isSuccessful()) {
                        String message = response.message();
                        Log.d(TAG, "message: " + message);
                        json = response.body().string();
                        //Log.d(TAG, "response: " + response.body().string());
                    } else {
                        Log.d(TAG, "请求失败 ");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void doPost(View view) {
        new Thread() {
            @Override
            public void run() {
                // 需要提交一个表单格式
                FormBody formbody = new FormBody.Builder().add("sex", "boy").build();
                // Request请求
                Request request = new Request.Builder().url(urlAddress + method).post(formbody).build();

                mClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: 请求失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d(TAG, "onResponse: " + response.body().string());
                    }
                });

            }
        }.start();
    }


    public void doGson(View view) throws IOException {

        // Log.d(TAG, "得到的JSON是: " + json);
        // Gson gson = new Gson();
        // Person person = gson.fromJson(json, Person.class);
        //List<Person.ListBean> list = person.getList();
        //for (Person.ListBean listBean : list) {
        //    String name = listBean.getName();
        //    String sex = listBean.getSex();
        //    Log.d(TAG, "doGson: " + name + "---" + sex);
        //}
        //List<Person.ListBean> list = person.getList();
        //for (Person.ListBean listBean : list) {
        //    System.out.println(listBean.getName() + "---" + listBean.getSex());
        //}
        //Log.d(TAG, "doGson: " + person.toString());
        //Log.d(TAG, "doGson: " + name + "```" + sex);

        Log.d(TAG, "拿到的JSON是: " + json);
        if (json != null && !Objects.equals(json, "")) {
            Gson gson = new Gson();
            Member member = gson.fromJson(json, Member.class);

            List<Member.ListBean> list = member.getList();

            for (Member.ListBean listBean : list) {
                String sex = listBean.getSex();
                String name = listBean.getName();
                Log.d(TAG, "doGson: " + sex + "---" + name);
            }
        } else {
            Toast.makeText(MainActivity.this, "JSON有问题", Toast.LENGTH_SHORT).show();
        }


    }


}
