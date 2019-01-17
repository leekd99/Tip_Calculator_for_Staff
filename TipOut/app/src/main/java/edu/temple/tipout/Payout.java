package edu.temple.tipout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStreamReader;
import java.util.ArrayList;

public class Payout extends AppCompatActivity {

    //Views
    private TextView tipPoolView;
    private TextView remainderView;
    private ListView tippedEmployeeView;

    private TippedEmployeeAdapter tipAdapter;

    //Intent
    private Intent parent;

    //Values to display
    private double tip_pool;
    private double remainder;
    private ArrayList<Employee> tippedEmployees;

    //default value incase nothing is sent via bundle and Extra
    private double defaultValue = -1;

    //Keys to retrieve extras via intent
    private String tip_pool_extra;
    private String tip_remainder_extra;
    private String employee_list_extra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payout);

        //get Intent and bundle
        parent = getIntent();
        Bundle bundle = parent.getExtras();

        //find resources
        tipPoolView = findViewById(R.id.payout_tipPool);
        remainderView = findViewById(R.id.payout_remainder);
        tippedEmployeeView = findViewById(R.id.payoutView);

        //find string resources
        tip_pool_extra = getResources().getString(R.string.tip_pool_extra);
        tip_remainder_extra = getResources().getString(R.string.tip_remainder_extra);
        employee_list_extra = getResources().getString(R.string.employee_list_extra);

        //retrieve and assign values from
        this.tip_pool = parent.getDoubleExtra(tip_pool_extra, defaultValue);
        this.remainder = parent.getDoubleExtra(tip_remainder_extra, defaultValue);
        this.tippedEmployees = bundle.getParcelableArrayList(employee_list_extra);

        tipAdapter = new TippedEmployeeAdapter(this, R.layout.employee_payout_view, tippedEmployees);
        tippedEmployeeView.setAdapter(tipAdapter);

        //convert values to displayable values
        int intTipPool = (int)this.tip_pool;
        int intRemainder = (int)this.remainder;

        String displayTipPool = "$" + Integer.toString(intTipPool);
        String displayRemainder = "$" + Integer.toString(intRemainder);

        //set views for tip pool and remainder
        tipPoolView.setText(displayTipPool);
        remainderView.setText(displayRemainder);

        for(Employee temp : tippedEmployees){

            Log.d("()()()()()()()()()", temp.toString());

        }//end


    }//end onCreate
}//end Payout
