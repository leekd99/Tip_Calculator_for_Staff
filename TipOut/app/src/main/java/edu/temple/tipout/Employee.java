package edu.temple.tipout;

import android.os.Parcel;
import android.os.Parcelable;

public class Employee implements Parcelable{

    public static final Parcelable.Creator CREATOR = new Creator() {
        @Override
        public Employee createFromParcel(Parcel source) {
            return new Employee(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new Employee[size];
        }
    };//end Parcelable.Creator CREATOR

    private int employeeNumber;
    private double hours;
    private int payout;

    public Employee(int employeeNumber, double hours){

        this.employeeNumber = employeeNumber;
        this.hours = hours;

    }//end constructor

    public void setPayout(int pool){

        this.payout = pool;

    }//end setPayout

    public int getPayout(){

        return this.payout;

    }//end getPayout

    public int getEmployeeNumber(){
        return this.employeeNumber;
    }//end getEmployeeNumber

    public double getHours(){
        return this.hours;
    }//end get hours

    public Employee(Parcel source){

        this.employeeNumber = source.readInt();
        this.hours = source.readDouble();
        this.payout = source.readInt();

    }//end Employee

    @Override
    public int describeContents() {
        return 0;
    }//end describeContents

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(employeeNumber);
        dest.writeDouble(hours);
        dest.writeInt(payout);

    }//end describeContents

    public String toString(){

        return "\nEmployee: " + employeeNumber + "\nHours: " + hours + "\nPayout: " + payout + "\n";

    }//end toString

}//end
