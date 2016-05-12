package com.provarefresh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView myLocationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myLocationText = (TextView) findViewById(R.id.textView);
        runThread();

    }

    private void runThread() {

        new Thread() {
            public void run() {
                int i = 0;
                while (i++ < 1000) {
                    final int temp = i;
                    try
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                myLocationText.setText("numero aggiornamenti: "+temp);
                            }
                        });
                        Thread.sleep(1000);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }
}
