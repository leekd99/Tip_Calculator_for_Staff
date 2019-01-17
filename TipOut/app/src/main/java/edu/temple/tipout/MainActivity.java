package edu.temple.tipout;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView employeeHourList;
    private EditText tipPool;
    private EditText employeeHours;
    private Button addButton;
    private Button removeButton;
    private FloatingActionButton calculate;

    private EmployeeHoursAdapter hoursAdapter;
    private double tip_pool;
    private double payRate;
    private double remainder;

    private Intent showTipOut;

    //keys to send extras via intent
    private String tip_pool_extra;// = getResources().getString(R.string.tip_pool_extra);
    private String tip_remainder_extra;// = getResources().getString(R.string.tip_remainder_extra);
    private String employee_list_extra;// = getResources().getString(R.string.employee_list_extra);

    ArrayList<Employee> shiftEmployees = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find resources
        employeeHourList = findViewById(R.id.employeeList);
        tipPool = findViewById(R.id.tip_pool_input);
        employeeHours = findViewById(R.id.employee_hours_input);
        addButton = findViewById(R.id.addEmployee);
        removeButton = findViewById(R.id.removeEmployee);
        calculate = findViewById(R.id.calculateTips);

        //find string resources
        //keys to send extras via intent
        tip_pool_extra = getResources().getString(R.string.tip_pool_extra);
        tip_remainder_extra = getResources().getString(R.string.tip_remainder_extra);
        employee_list_extra = getResources().getString(R.string.employee_list_extra);

        //Create Employee hours listView
        hoursAdapter = new EmployeeHoursAdapter(this, R.layout.employee_hours_view, shiftEmployees);
        employeeHourList.setAdapter(hoursAdapter);

        //onClick implementation to add employee to ListView
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String check = employeeHours.getText().toString();

                //check to see field is not blank
                if (check.matches("")) {
                    Toast.makeText(getApplicationContext(), R.string.blank_hours_error, Toast.LENGTH_SHORT).show();
                    return;
                }//end check

                double employee_hour = Double.parseDouble(check);

                //check to see if tip Pool makes sense i.g. is above 0 and non negative
                if(employee_hour > 0) {
                    //TODO
                    //implement method to add employee to arrayList and display them in ListView
                    Employee toBeAdded = new Employee(shiftEmployees.size()+1, employee_hour);
                    addEmployee(toBeAdded);

                } else {
                    Toast.makeText(getApplicationContext(), R.string.invalid_hours_error, Toast.LENGTH_SHORT).show();
                }//end else if

                //Clear hours
                employeeHours.getText().clear();

            }//end onClick
        });//end setOnClickListener

        //onClick to remove last item in array list
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (shiftEmployees.size() != 0) {
                    removeEmployee();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.empty_list_error, Toast.LENGTH_SHORT).show();
                }//end

            }//end onClick
        });//end setOnClick

        //onClick method to calculate tips
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String check = tipPool.getText().toString();

                if(check.matches("")){
                    Toast.makeText(getApplicationContext(), R.string.empty_tip_pool_error, Toast.LENGTH_SHORT).show();
                    return;
                }//end check for empty entry

                double tip_pool = Double.parseDouble(check);

                //check to see if tip Pool makes sense i.g. is above 0 and non negative
                if(tip_pool > 0) {
                    //TODO
                    calculateTips();
                    //pass new stuff
                    //create new Intent
                    showTipOut = new Intent(MainActivity.this, Payout.class);
                    //pass info via putExtra
                    showTipOut.putExtra(tip_pool_extra, tip_pool);
                    showTipOut.putExtra(tip_remainder_extra, remainder);
                    //showTipOut.putExtra(employee_list_extra, shiftEmployees);

                    showTipOut.putParcelableArrayListExtra(employee_list_extra, shiftEmployees);

                    //Launch Activity
                    startActivity(showTipOut);

                } else {
                    Toast.makeText(getApplicationContext(), R.string.invalid_tip_pool_error, Toast.LENGTH_SHORT).show();
                }//end else if

            }//end onCLick
        });//end setOnClickListener

    }//end onCreate

    //method for adding employees to list view
    public void addEmployee(Employee employee){

        shiftEmployees.add(employee);
        hoursAdapter.notifyDataSetChanged();

    }//end addEmployee

    public void removeEmployee(){

        shiftEmployees.remove(shiftEmployees.size() - 1);
        hoursAdapter.notifyDataSetChanged();

    }//end removeEmployee

    public void calculateTips(){

        tip_pool = Double.parseDouble(tipPool.getText().toString());
        payRate = tip_pool/totalHours();

        //set payout per employee
        for(Employee currentEmployee : shiftEmployees){

            int pay = ((int)(payRate*currentEmployee.getHours()));

            Log.d("^^^^^^^^^^^^^^^^^^", "payRate: " + payRate + "\n" + "pay: " + pay);

            //calculate shares and set it to employee
            currentEmployee.setPayout(pay);

        }//end for

        //while there is enough money left over
        while(anyLeftOver() && distributable()){

            //distribute cash among employees
            distribute();

        }//end while

        remainder = tip_pool - ((double)currentTotalPay());

    }//end calculate tips

    public double totalHours(){

        double return_value = 0;

        //add up work hours
        for(Employee temp : shiftEmployees){

            return_value += temp.getHours();

        }//end

        return return_value;

    }//end totalHours

    public int currentTotalPay(){

        int return_value = 0;

        //add up currentPayout
        for(Employee temp : shiftEmployees){

            return_value += temp.getPayout();

            Log.d("&&&&&&&&&&&&&&&&&&&&", "currentTotalPay: " + return_value + "\n" + "employee: " + temp.getPayout());

        }//end

        return return_value;

    }//end currentTotalPay

    public boolean anyLeftOver(){

        //Check to see if current tipout is equal to tip pool
        if(currentTotalPay() == ((int)tip_pool)){

            Log.d("#############", "" + ((int)tip_pool) +  " | " + tip_pool + "<>" + currentTotalPay());
            return false;

        } else if (currentTotalPay() > ((int)tip_pool)){

            //check to see if current pay is greater than actual tip pool
            //if so exit as something went horribly wrong
            Toast.makeText(this,R.string.fatal_calculation_error,Toast.LENGTH_SHORT).show();
            //System.exit(1);

            Log.d("#############", "" + ((int)tip_pool) + " | " + tip_pool + "<>" + currentTotalPay());

        }//end check

        //implied that there is some tip left over
        return true;

    }//end checkLeftOvers

    public boolean distributable(){

        int leftovers = ((int)tip_pool) - currentTotalPay();

        //there are more leftover than there are shift employees
        if(leftovers >= shiftEmployees.size()){
            return true;
        }//end if

        //leftOver is too small to distribute
        return false;

    }//end distributable

    public void distribute(){

        //add dollar to tip pool
        for(Employee temp : shiftEmployees){

            temp.setPayout(temp.getPayout()+1);

        }//end for

    }//end distribute

}//end MainActivity
