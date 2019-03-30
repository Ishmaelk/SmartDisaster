package c.smartdisaster.networkingfinal;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // Display Variables //
    TableLayout stk;
    TextView showValue,showValue1,showValue2;
    Button pauseButton;
    Spinner filterSpinner;
    // Network Variables //
    DisasterNetwork network;
    CPU localCenter;
    RemoteCenter remoteCenter;

    // Update handler variables //
    android.os.Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // initializes variables
        super.onCreate(savedInstanceState);
        showValue = null;
        showValue2 = null;
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
        pauseButton = (Button) findViewById(R.id.pauseButton);

        updateLocalCapacity(localCenter.GetUsage());
        stk = (TableLayout) findViewById(R.id.table_main);
        filterSpinner = (Spinner) findViewById(R.id.filterSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.filters));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(adapter);
        filterSpinner.setSelection(1);
        populateTable();
    }

    @Override
    protected void onResume() { // Handles events that occur once every second
        if (network.paused)
            return;
        final int delay = 1000;
        handler.postDelayed( runnable = new Runnable() {
            public void run() {
                if (!network.paused) {
                    network.elapsedTime++;
                    network.GenerateJobs();
                    network.LoadJobs();
                    network.TransferJobs();
                    localCenter.TransferToRemote();
                    network.IncrementTime((delay / 1000));
                    populateTable();
                    localCenter.Compute();
                    remoteCenter.Compute();
                    updateLocalCapacity(localCenter.GetComputePerJob());

                    if (showValue != null)
                        showValue.setText("    " + Integer.toString(network.numAvailableChannels));
                    if (showValue2 != null)
                        showValue2.setText("   " + Integer.toString(network.jobsPerSecond));
                }
                handler.postDelayed(runnable, delay);

            }
        }, delay);
        super.onResume();
    }

    @Override // pauses the ticker when activity is not visible
    protected void onPause() {
        handler.removeCallbacks(runnable); //stop handler when activity not visible
        super.onPause();
    }

    // Populates the table with active job information | called in onResume () //
    public void populateTable () {

        String[] columns = new String[] {" Job ID ", "  Device  ", "  Priority  ", "     STATE     ",
                "    Location    ", "  NETWORK TIME  ", "   COMPUTE TIME  ", "  SIZE  ",
                "  PROGRESS  "};
        
        stk.removeAllViews();
        stk.removeAllViewsInLayout();
        TableRow tbrow0 = new TableRow(this);
        for (int i = 0; i < columns.length; i++) {
            TextView tv0 = createAndFormatTextView(tbrow0);
            tv0.setText(columns[i]);
        } stk.addView(tbrow0);

        String filter = filterSpinner.getSelectedItem().toString();
        if (filter.equals("All"))
            _populateTable(network.allJobs);
        else if (filter.equals("Active"))
            _populateTable(network.activeJobs);
        else if (filter.equals("Local"))
            _populateTable(network.FilterActiveJobsByLocation("Local Center"));
        else if (filter.equals("Remote"))
            _populateTable(network.FilterActiveJobsByLocation("Remote Center"));
        else if (filter.equals("Pool"))
            _populateTable(network.GetJobPoolAsArray());

    }

    void _populateTable(ArrayList<Job> jobs) {
        for (int i = 0; i < jobs.size(); i++) {
            TableRow tbrow = new TableRow(this);
            Job j = jobs.get(i);

            TextView idText = createAndFormatTextView(tbrow);
            idText.setText(String.valueOf(j.id));

            TextView deviceText = createAndFormatTextView(tbrow);
            deviceText.setText(j.deviceOrigin);

            TextView priorityText = createAndFormatTextView(tbrow);
            priorityText.setText(String.valueOf(j.priority));

            TextView stateText = createAndFormatTextView(tbrow);
            stateText.setText(j.state);

            TextView locationText = createAndFormatTextView(tbrow);
            locationText.setText(j.location);

            TextView networkText = createAndFormatTextView(tbrow);
            networkText.setText(String.valueOf(j.networkTime));

            TextView computeText = createAndFormatTextView(tbrow);
            computeText.setText(String.valueOf(j.computeTime));

            TextView sizeText = createAndFormatTextView(tbrow);
            sizeText.setText(String.valueOf(j.totalPayLoad));

            TextView progressText = createAndFormatTextView(tbrow);
            progressText.setText(String.valueOf(Math.round(j.GetProgress() * 100.0)/100.0));
            stk.addView(tbrow);
        }
    }

    // onClick method for pause button //
    public void pauseHandler (View view) {
        network.paused = !network.paused;
        String text = network.paused ? "Unpause" : "Pause";
        pauseButton.setText(text);
    }

    // Updates compute per job display | called in onResume () //
    public void updateLocalCapacity(double toThis) {
        TextView textView = (TextView) findViewById(R.id.LCapInput);
        textView.setText((Double.toString(Math.round(toThis * 100.0)/100.0)));
    }

    // On click method to increase jobsPerSecond //
    public void increaseJobsPerSecond (View view){
        network.jobsPerSecond++;
        showValue1.setText(Integer.toString(network.jobsPerSecond));
    }
    // On click method to decrease jobsPerSecond //
    public void decreaseJobsPerSecond(View view){
        if (network.jobsPerSecond <= 0)
            return;
        network.jobsPerSecond--;
        showValue1.setText(Integer.toString(network.jobsPerSecond));
    }
    // On click method to increase numberOfChannels //
    public void increaseNumChannels(View view){
        network.CreateChannel();
        showValue.setText(Integer.toString(network.numAvailableChannels));
    }
    // On click method to decrease numberOfChannels //
    public void decreaseNumChannels(View view){
        if (network.numAvailableChannels <= 0)
            return;
        network.DestroyChannel();
        showValue.setText(Integer.toString(network.numAvailableChannels));
    }

    public TextView createAndFormatTextView (TableRow row) {
        TextView text = new TextView(this);
        text.setTextColor(Color.BLACK);
        text.setGravity(Gravity.CENTER);
        row.addView(text);
        return text;
    }

    public void outputData () {
        System.out.println("\n Printing jobs \n");
        network.PrintJobs();
        System.out.println("\n Printing Channels \n");
        network.PrintChannels();
        System.out.println("\n ComputePerJob: " + localCenter.GetComputePerJob());
        System.out.println("\n Printing Transfers \n");
        network.PrintTransferring();
        System.out.println("\n Printing Compute \n");
        network.PrintComputing();
        System.out.println("\n Printing Remote Transfer \n");
        network.PrintRemoteTransfer();
        System.out.println("\n Printing Remote Compute \n");
        network.PrintRemoteCompute();
    }

}