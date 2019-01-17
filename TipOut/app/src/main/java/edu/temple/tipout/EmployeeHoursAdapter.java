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

public class EmployeeHoursAdapter extends ArrayAdapter<Employee> {

    private Context myContext;
    private int employee_hours;

    public EmployeeHoursAdapter(@NonNull Context context, int resource, ArrayList<Employee> objects){
        super(context, resource, objects);
        this.myContext = context;
        this.employee_hours = resource;
    }//end Constructor

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String Employee = myContext.getResources().getString(R.string.employee_label) + " " + getItem(position).getEmployeeNumber();
        String hours = Double.toString(getItem(position).getHours());

        LayoutInflater inflater = LayoutInflater.from(myContext);
        convertView = inflater.inflate(employee_hours,parent,false);

        //get views
        TextView EmployeeNumberView = (TextView) convertView.findViewById(R.id.EmployeeNumber);
        TextView EmployeeHoursView = (TextView) convertView.findViewById(R.id.EmployeeHours);

        EmployeeNumberView.setText(Employee);
        EmployeeHoursView.setText(hours);

        return convertView;

    }//end getView

    @Nullable
    @Override
    public Employee getItem(int position) {
        return super.getItem(position);
    }//end getItem

}//end EmployeeHoursAdapter
