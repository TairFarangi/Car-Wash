// Created by Jenya Ivanov & Tair Farangi 
package Exe2;

public class Car extends Thread {

   private final CarWash carWash; //mutual resource
    public int licensePlate;

    //Constructor
    Car(CarWash carWash,int id){
        this.carWash = carWash;
        this.licensePlate = id;
        }

    @Override
    public void run(){
        carWash.wash1(this);
        carWash.wash2(this);
        carWash.finish(this);
    }

    // Class functions
    // This function converts the vehicle ID into a real license plate.
    public String licensePlate_toString(){
        final String FORMAT = "65-323-"; // <---- Format for numbers in license plate.

        // Output string will be formatted - "65-323-ID". !!- (GENERAL CASE: FORMAT-ID).
        if(this.licensePlate < 10){
            return String.format("%s0%d",FORMAT, this.licensePlate);
        } else {
            return String.format("%s%d",FORMAT, this.licensePlate);
        }
    }
}