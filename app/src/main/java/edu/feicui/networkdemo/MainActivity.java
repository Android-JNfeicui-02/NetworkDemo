package edu.feicui.networkdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button      mBtnGet;
    private TextView    mTv;
    private EditText    mEtInput;
    private InputStream mInputStream;
    private String      mStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnGet = (Button) findViewById(R.id.btn_get);
        mTv = (TextView) findViewById(R.id.tv_show);
        mEtInput = (EditText) findViewById(R.id.et_get);

        mBtnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = mEtInput.getText().toString().trim();
                doGet(url);
            }
        });
    }

    private void doGet(final String urlString) {

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    // 设置请求方式 需要写出来 大写 GET
                    connection.setRequestMethod("GET");

                    // 设置超时时间
                    connection.setConnectTimeout(3000);

                    // 获取返回的状态码
                    int responseCode = connection.getResponseCode();

                    if (responseCode == 200) {
                        // 获取的数据以流的形式 返回回来
                        mInputStream = connection.getInputStream();
                        // 解析流数据
                        mStream = NetworkUtils.readStream(mInputStream);


                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        mTv.setText(mStream);

    }
}
