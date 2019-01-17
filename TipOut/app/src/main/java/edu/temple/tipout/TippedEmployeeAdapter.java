package edu.temple.tipout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TippedEmployeeAdapter extends ArrayAdapter<Employee> {

    private Context myContext;
    private int payout_view;

    TippedEmployeeAdapter(@NonNull Context context, int resource, ArrayList<Employee> objects){
        super(context, resource, objects);
        this.myContext = context;
        this.payout_view = resource;
    }//end Constructor

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String Employee = myContext.getResources().getString(R.string.employee_label) + " " + getItem(position).getEmployeeNumber();
        String hours = Double.toString(getItem(position).getHours()) + " " + getContext().getResources().getString(R.string.hours_worked);
        String payout = "$" + Integer.toString(getItem(position).getPayout());

        LayoutInflater inflater = LayoutInflater.from(myContext);
        convertView = inflater.inflate(payout_view,parent,false);

        //get views
        TextView EmployeeNumberView = (TextView) convertView.findViewById(R.id.EmployeeNumber);
        TextView EmployeeHoursView = (TextView) convertView.findViewById(R.id.EmployeeHours);
        TextView EmployeePayoutView = (TextView) convertView.findViewById(R.id.EmployeePayout);

        EmployeeNumberView.setText(Employee);
        EmployeeHoursView.setText(hours);
        EmployeePayoutView.setText(payout);

        return convertView;

    }//end getView

    @Nullable
    @Override
    public Employee getItem(int position) {
        return super.getItem(position);
    }//end getItem

}//end TippedEmployeeAdatper
