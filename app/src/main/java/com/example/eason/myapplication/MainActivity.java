package com.example.eason.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.start)
    Button start;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.stop)
    Button stop;

    private Handler handler;
    private HandlerThread mThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    int i = 0;
    private Runnable r1 = new Runnable() {
        @Override
        public void run() {
            if (i < 10) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(i + "");
                    }
                });
                i++;
            } else {
                i = 0;
            }
            handler.postDelayed(this,1000);
        }
    };

    @OnClick({R.id.start, R.id.textView, R.id.stop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.start:
                mThread = new HandlerThread("name");
                mThread.start();
                handler = new Handler(mThread.getLooper());
                handler.post(r1);
                break;
            case R.id.stop:
                textView.setText("stop");
                handler.removeCallbacks(r1);
                mThread.quit();
                break;
        }
    }
}
