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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TableLayout stk;
    TextView showValue,showValue1,showValue2;
    int counter=0,counter1=0;

    DisasterNetwork network;
    CPU localCenter;
    RemoteCenter remoteCenter;

    android.os.Handler handler;
    Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        network = new DisasterNetwork ();
        localCenter = network.localCenter;
        remoteCenter = network.remoteCenter;
        localCenter.remoteCenter = remoteCenter;
        network.CreateDevices();
        network.CreateChannels();
        setContentView(R.layout.activity_main);
        LayoutInflater inflater = getLayoutInflater();
        showValue=(TextView) findViewById(R.id.LCapacityNum);
        showValue1=(TextView) findViewById(R.id.LCapacityNum2);

        updateLocalCapacity(localCenter.GetUsage());
        stk = (TableLayout) findViewById(R.id.table_main);
        init(new ArrayList<Job>());
    }

    @Override
    protected void onResume() { // Handles events that occur once every second
        network.paused = false;
        final int delay = 1000;
        handler.postDelayed( runnable = new Runnable() {
            public void run() {
                network.elapsedTime++;
                network.GenerateJobs();
                network.LoadJobs();
                network.TransferJobs();

                // Enter Network Events here //
                localCenter.TransferToRemote();
                localCenter.Compute();
                remoteCenter.Compute();

                network.IncrementTime((delay/1000));

                //System.out.println("\n Printing jobs \n");
                //network.PrintJobs();
                //System.out.println("\n Printing Channels \n");
                //network.PrintChannels();
                System.out.println("\n ComputePerJob: " + localCenter.GetComputePerJob());
                System.out.println("\n Printing Transfers \n");
                network.PrintTransferring();
                System.out.println("\n Printing Compute \n");
                network.PrintComputing();
                System.out.println("\n Printing Remote Transfer \n");
                network.PrintRemoteTransfer();
                System.out.println("\n Printing Remote Compute \n");
                network.PrintRemoteCompute();
                init(network.activeJobs);
                updateLocalCapacity(localCenter.GetComputePerJob());
                //showValue.setText(Integer.toString(network.numAvailableChannels));
                //showValue2.setText(Integer.toString(network.jobsPerSecond));
                handler.postDelayed(runnable, delay);

            }
        }, delay);
        super.onResume();
    }

    @Override // pauses the ticker when activity is not visible
    protected void onPause() {
        network.paused = true;
        handler.removeCallbacks(runnable); //stop handler when activity not visible
        super.onPause();
    }

    public void init (ArrayList<Job> activeJobs) {

        String[] columns = new String[] {" Job ID ", "  Device  ", "  Priority  ", "  STATE  ", "  NETWORK TIME  ",
                "   COMPUTE TIME  ", "  Location  ", "  PROGRESS  "};
        stk.removeAllViews();
        stk.removeAllViewsInLayout();
        TableRow tbrow0 = new TableRow(this);
        for (int i = 0; i < columns.length; i++) {
            TextView tv0 = createAndFormatTextView(tbrow0);
            tv0.setText(columns[i]);
        } stk.addView(tbrow0);

        for (int i = 0; i < Math.min(activeJobs.size(), 99); i++) {
            TableRow tbrow = new TableRow(this);
            Job j = activeJobs.get(i);
            TextView idText = createAndFormatTextView(tbrow);
            idText.setText(String.valueOf(j.id));
            TextView deviceText = createAndFormatTextView(tbrow);
            deviceText.setText(j.deviceOrigin);
            TextView priorityText = createAndFormatTextView(tbrow);
            priorityText.setText(String.valueOf(j.priority));
            TextView stateText = createAndFormatTextView(tbrow);
            stateText.setText(j.state);
            TextView networkText = createAndFormatTextView(tbrow);
            networkText.setText(String.valueOf(j.networkTime));
            TextView computeText = createAndFormatTextView(tbrow);
            computeText.setText(String.valueOf(j.computeTime));

            TextView locationText = createAndFormatTextView(tbrow);
            locationText.setText(j.location);
            TextView progressText = createAndFormatTextView(tbrow);
            progressText.setText(String.valueOf(j.GetProgress()));
            stk.addView(tbrow);
        }
    }

    public TextView createAndFormatTextView (TableRow row) {
        TextView text = new TextView(this);
        text.setTextColor(Color.BLACK);
        text.setGravity(Gravity.CENTER);
        row.addView(text);
        return text;
    }


    public void updateLocalCapacity(double toThis) {
        TextView textView = (TextView) findViewById(R.id.LCapInput);
        textView.setText((Double.toString(toThis)));
    }

    public void increaseJobsPerSecond (View view){
        network.jobsPerSecond++;
        showValue1.setText(Integer.toString(network.jobsPerSecond));
    }
    public void decreaseJobsPerSecond(View view){
        if (network.jobsPerSecond <= 0)
            return;
        network.jobsPerSecond--;
        showValue1.setText(Integer.toString(network.jobsPerSecond));
    }
    public void increaseNumChannels(View view){
        network.numAvailableChannels++;
        showValue.setText(Integer.toString(network.numAvailableChannels));
    }
    public void decreaseNumChannels(View view){
        if (network.numAvailableChannels <= 0)
            return;
        network.numAvailableChannels--;
        showValue.setText(Integer.toString(network.numAvailableChannels));
    }
}