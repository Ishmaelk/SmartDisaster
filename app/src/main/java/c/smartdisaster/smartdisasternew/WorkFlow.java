package c.smartdisaster.smartdisasternew;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class WorkFlow extends AppCompatActivity {

    DisasterNetwork network;
    CPU localCenter;
    RemoteCenter remoteCenter;

    android.os.Handler handler;
    Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        network = new DisasterNetwork ();
        localCenter = network.localCenter;
        remoteCenter = network.remoteCenter;
        setContentView(R.layout.activity_work_flow);

        //go back to infrastructure status
        Button infrastructureBtn= (Button)findViewById(R.id.infrastructureBack);
        infrastructureBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent startIntent= new Intent(getApplicationContext(),infrastructure.class);
                startActivity(startIntent);
            }
        });
        init();

    }



    @Override
    protected void onResume() { // Handles events that occur once every second
        network.paused = false;
        handler.postDelayed( runnable = new Runnable() {
            public void run() {
                network.elapsedTime += 1;
                // Enter Network Events here //
                localCenter.Compute();
                remoteCenter.Compute();
                handler.postDelayed(runnable, 1000);
            }
        }, 1000);
        super.onResume();
    }

    @Override // pauses the ticker when activity is not visible
    protected void onPause() {
        network.paused = true;
        handler.removeCallbacks(runnable); //stop handler when activity not visible
        super.onPause();
    }


    public void init() {
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        tv0.setText(" start ");
        tv0.setTextColor(Color.WHITE);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" End ");
        tv1.setTextColor(Color.WHITE);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(" channel Bandwidth/Allocation ");
        tv2.setTextColor(Color.WHITE);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText("Channel");
        tv3.setTextColor(Color.WHITE);
        tbrow0.addView(tv3);
        stk.addView(tbrow0);
        for (int i = 0; i < 25; i++) {
            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText("UE" + i+ " ");
            t1v.setTextColor(Color.WHITE);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);
            TextView t2v = new TextView(this);
            t2v.setText(" "+"End" + i);
            t2v.setTextColor(Color.WHITE);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);
            TextView t3v = new TextView(this);
            t3v.setText("CB" + i);
            t3v.setTextColor(Color.WHITE);
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);
            TextView t4v = new TextView(this);
            t4v.setText("" + i
            );
            t4v.setTextColor(Color.WHITE);
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);
            stk.addView(tbrow);
        }
    }
}
