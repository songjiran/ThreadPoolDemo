package com.sjr.threadpooldemo;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;


import com.sjr.threadpooldemo.util.ThreadPoolUtil;

import java.util.Random;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ProgressBar pb1;
    private ProgressBar pb2;
    private ProgressBar pb3;
    private ProgressBar pb4;

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;

    private static final int FLAGS1 = 1;
    private static final int FLAGS2 = 2;
    private static final int FLAGS3 = 3;
    private static final int FLAGS4 = 4;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FLAGS1:
                    pb1.setProgress(msg.arg1);
                    break;
                case FLAGS2:
                    pb2.setProgress(msg.arg1);
                    break;
                case FLAGS3:
                    pb3.setProgress(msg.arg1);
                    break;
                case FLAGS4:
                    pb4.setProgress(msg.arg1);
                    break;


                default:
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        pb1 = (ProgressBar) findViewById(R.id.pb1);
        pb2 = (ProgressBar) findViewById(R.id.pb2);
        pb3 = (ProgressBar) findViewById(R.id.pb3);
        pb4 = (ProgressBar) findViewById(R.id.pb4);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
    }

    private Runnable runnable1 = new MRunnable(FLAGS1);
    private Runnable runnable2 = new MRunnable(FLAGS2);
    private Runnable runnable3 = new MRunnable(FLAGS3);
    private Runnable runnable4 = new MRunnable(FLAGS4);

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                updateProcessBar(runnable1);
                break;
            case R.id.btn2:
                updateProcessBar(runnable2);
                break;
            case R.id.btn3:
                updateProcessBar(runnable3);
                break;
            case R.id.btn4:
                updateProcessBar(runnable4);
                break;
            default:
                break;
        }
    }


    private void updateProcessBar(Runnable runnable) {
        ThreadPoolUtil.runTaskInThread(runnable);
    }

    private class MRunnable implements Runnable {
        private int mFlags;

        public MRunnable(int flags) {
            this.mFlags = flags;
        }

        @Override
        public void run() {
            for (int i = 0; i <= 100; i++) {
                Message msg = mHandler.obtainMessage();
                //随机数，让下载更真实...
                Random rand = new Random();
                int randNum = rand.nextInt(100);
                SystemClock.sleep(randNum);

                msg.what = mFlags;
                msg.arg1 = i;
                mHandler.sendMessage(msg);
            }
        }
    }

    /**
     * 退出时清除线程
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ThreadPoolUtil.removeTask(runnable1);
        ThreadPoolUtil.removeTask(runnable2);
        ThreadPoolUtil.removeTask(runnable3);
        ThreadPoolUtil.removeTask(runnable4);
    }
}
