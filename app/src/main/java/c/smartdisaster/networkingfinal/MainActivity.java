package c.smartdisaster.networkingfinal;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView showValue,showValue1,showValue2;
    int counter=0,counter1=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LayoutInflater inflater = getLayoutInflater();
        //ProgressBar bar = (ProgressBar ) inflater.inflate(R.layout.small_progress_bar, null);
        init();

        showValue=(TextView) findViewById(R.id.LCapacityNum);
        showValue1=(TextView) findViewById(R.id.LCapacityNum3);

        updateLocalCapacity(12.1);

       /* new Thread(new Runnable() {
            @Override
            public void run() {
                while (Pstatus < 100){
                    Pstatus++;
                    android.os.SystemClock.sleep(50);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            prg.setProgress(Pstatus);
                        }
                    });

                }

            }
        })*/


    }
    public void updateLocalCapacity(double toThis) {
        TextView textView = (TextView) findViewById(R.id.LCapInput);
        textView.setText((Double.toString(toThis)));
    }

    public void countIn (View view){
        //
        counter++;
        showValue.setText(Integer.toString(counter));
    }
    public void countDe(View view){
        if (counter <= 0)
            return;
        counter--;
        showValue.setText(Integer.toString(counter));
    }
    public void countIn1(View view){
        //
        counter1++;
        showValue1.setText(Integer.toString(counter1));
    }
    public void countDe1(View view){
        if (counter1 <= 0)
            return;
        counter1--;
        showValue1.setText(Integer.toString(counter1));
    }
    public void init() {

        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        tv0.setText(" JOB ID ");
        tv0.setTextColor(Color.BLACK);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText("  DEVICE  ");
        tv1.setTextColor(Color.BLACK);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText("  STATE  ");
        tv2.setTextColor(Color.BLACK);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText("  NETWORK TIME  ");
        tv3.setTextColor(Color.BLACK);
        tbrow0.addView(tv3);
        TextView tv4 = new TextView(this);
        tv4.setText(" " + "  COMPUTE TIME  ");
        tv4.setTextColor(Color.BLACK);
        tbrow0.addView(tv4);
        TextView tv5 = new TextView(this);
        tv5.setText(" " + "  TOTAL TIME  ");
        tv5.setTextColor(Color.BLACK);
        tbrow0.addView(tv5);
        TextView tv6 = new TextView(this);
        tv6.setText(" " + "  LOCATION  ");
        tv6.setTextColor(Color.BLACK);
        tbrow0.addView(tv6);
        TextView tv7 = new TextView(this);
        tv7.setText(" " + " PROGRESS  ");
        tv7.setTextColor(Color.BLACK);
        tbrow0.addView(tv7);
        stk.addView(tbrow0);
        for (int i = 0; i < 10; i++) {
            double j = 0.0;
            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText("10" + i + " ");
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);
            TextView t2v = new TextView(this);
            t2v.setText(" " + "20" + i);
            t2v.setTextColor(Color.BLACK);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);
            TextView t3v = new TextView(this);
            t3v.setText("Commputing");
            t3v.setTextColor(Color.BLACK);
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);
            TextView t4v = new TextView(this);
            t4v.setText(String.format("%.2f", j++));
            t4v.setTextColor(Color.BLACK);
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);
            TextView t5v = new TextView(this);
            t5v.setText(String.format("%.2f", j + 2));
            t5v.setTextColor(Color.BLACK);
            t5v.setGravity(Gravity.CENTER);
            tbrow.addView(t5v);
            TextView t6v = new TextView(this);
            t6v.setText(String.format("%.2f", j + 3));
            t6v.setTextColor(Color.BLACK);
            t6v.setGravity(Gravity.CENTER);
            tbrow.addView(t6v);
            TextView t7v = new TextView(this);
            t7v.setText("LC");
            t7v.setTextColor(Color.BLACK);
            t7v.setGravity(Gravity.CENTER);
            tbrow.addView(t7v);
            TextView t8v = new TextView(this);
            t8v.setText(String.format("%.2f", j + 2));
            t8v.setTextColor(Color.BLACK);
            t8v.setGravity(Gravity.CENTER);
            tbrow.addView(t8v);
            stk.addView(tbrow);
        }

    }
}