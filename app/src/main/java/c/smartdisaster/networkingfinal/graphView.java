package c.smartdisaster.networkingfinal;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

public class graphView extends AppCompatActivity {

    RelativeLayout mainLayout;
    LineChart mchart;
    //DisasterNetwork network;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_view);

        mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        mchart = new LineChart(this);
        mainLayout.addView(mchart);

        mchart.setDragEnabled(true);
        mchart.setScaleEnabled(true);
        mchart.setDrawGridBackground(false);
        mchart.setBackgroundColor(Color.LTGRAY);

        Button toMainButton =(Button) findViewById(R.id.toMainView);
        toMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startIntent);
            }
        });

        LineData data = new LineData();
        ILineDataSet xSet = data.getDataSetByIndex(0);
        ILineDataSet ySet = data.getDataSetByIndex(1);

        data.setValueTextColor(Color.BLACK);
        mchart.setData(data);

        for (int i = 0; i < MainActivity.network.dataPerSecond.size(); i++) {
            int d = MainActivity.network.dataPerSecond.get(i);
            xSet.addEntry(i+1);
            ySet.addEntry(d);
        }
    }

}
