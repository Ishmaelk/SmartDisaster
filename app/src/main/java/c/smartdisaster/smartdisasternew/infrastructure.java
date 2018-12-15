package c.smartdisaster.smartdisasternew;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class infrastructure extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infrastructure);

        //Go back to workflow status
        Button workflowBtn= (Button)findViewById(R.id.worflowBack);
        workflowBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent startIntent= new Intent(getApplicationContext(),WorkFlow.class);
                startActivity(startIntent);
            }
        });
    }
}
