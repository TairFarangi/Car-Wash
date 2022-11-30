// Created by Jenya Ivanov - ID: 321858136 & Tair Farangi - ID: ID: 207215278
package Exe2;
import java.util.concurrent.Semaphore;

public class CarWash {
    long startTime; //start time of the program
    int totalCars; //num of cars in the wash car
    double averageCars;
    double averageWash;
    double averageTimeA;
    double averageTimeB;
    double averageTimeC;
    private final Semaphore semA ,semB;

    //Constructor
    CarWash(long startTime , int numOfCars , double averageCars , double averageWash, int numOfStaA, int numOfStaB){
        this.startTime=startTime;
        this.totalCars = numOfCars;
        this.averageCars = averageCars;
        this.averageWash = averageWash;
        this.semA = new Semaphore(numOfStaA ,true);
        this.semB = new Semaphore(numOfStaB , true);
    }

    //station A
    public void wash1(Car car){
        long arriveTime = System.currentTimeMillis();
        System.out.println("Vehicle "+car.licensePlate_toString()+ " has arrived to wash1. " + ((System.currentTimeMillis()-startTime)) +" milli seconds has past.");
        try {
            semA.acquire();
            Thread.sleep(nextTime(averageCars)); //Average time between the arrival of cars at the first section
            System.out.println( "Vehicle "+ car.licensePlate_toString()+ " is washing now at wash1. "+ ((System.currentTimeMillis()-startTime)) +" milli seconds has past.");
            Thread.sleep(nextTime(averageWash));//Average time for car wash
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println( "Vehicle "+ car.licensePlate_toString()+ " has left wash1. "+ ((System.currentTimeMillis()-startTime)) +" milli seconds has past.");
        //car left wash1
        synchronized (this){
            totalCars--; //decreasing the number of cars after they left section 1
            if (totalCars==0)
                notifyAll(); //notify all the thread that wait
        }
        averageTimeA+= (System.currentTimeMillis() - arriveTime);


        semA.release();
    }

    //station B
    public void wash2(Car car){
        long arriveTime = System.currentTimeMillis();
        System.out.println( "Vehicle "+ car.licensePlate_toString()+ " has arrived to wash2. " + + ((System.currentTimeMillis()-startTime)) +" milli seconds has past.");
        try {
            semB.acquire();
            System.out.println("Vehicle "+  car.licensePlate_toString()+ " is washing now at wash2. "+ ((System.currentTimeMillis()-startTime)) +" milli seconds has past.");
            Thread.sleep(nextTime(averageWash));//Average time for car wash
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println( "Vehicle "+ car.licensePlate_toString()+ " has left wash2. "+ ((System.currentTimeMillis()-startTime)) +" milli seconds has past.");
        averageTimeB += (System.currentTimeMillis()-arriveTime);
        semB.release();
    }

    //station C
    public void finish(Car car){
        long arriveTime = System.currentTimeMillis();
        System.out.println("Vehicle "+  car.licensePlate_toString()+ " has arrived to the finish wait line. " +((System.currentTimeMillis()-startTime)) +" milli seconds has past.");

        //when a car exit wash 1 the total cars number decrease by one
        // and if the total car number is 0 - the cars will start exiting the finish line
        // Guarded suspension to only allow exit when theres no vehicles being washed (at station 1).
            while (totalCars != 0){
                synchronized (this){
                    try {
                        System.out.println(car.licensePlate_toString() + " is waiting at the exit.");
                        wait(); //let the thread wait
                    } catch (InterruptedException e) {
                    }
                }
            }

             //totalCars==0
          System.out.println(  car.licensePlate_toString()+ " is exiting the wait line. " + ((System.currentTimeMillis()-startTime)) +" milli seconds has past.");
        averageTimeC+= (System.currentTimeMillis() - arriveTime);
    }

    public long nextTime (double average){
        final int MILLISECONDS = 1000;
        double u = Math.random() ; // generate random number between 0 to 1
        double result = ((-Math.log(u)) / average);
        return (long) result * MILLISECONDS;
    }

    public double getAverageTimeA(){ return averageTimeA; }
    public double getAverageTimeB(){ return averageTimeB; }
    public double getAverageTimeC(){ return averageTimeC; }

}