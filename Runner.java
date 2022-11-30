// Created by Jenya Ivanov - ID: 321858136 & Tair Farangi - ID: ID: 207215278
package Exe2;
import java.util.Scanner;

public class Runner {
   static Scanner input = new Scanner(System.in);
    public static void main(String[]args ){
        int numOfCars ,numOfStaA , numOfStaB;
        double averageCars , averageWash;


        System.out.println("Welcome to Jenya & Tair washing station, please enter the following properties:\n");
        System.out.print("Amount of cars:");
        numOfCars = input.nextInt();
        System.out.print("Amount of stations in section 1:");
        numOfStaA = input.nextInt();
        System.out.print("Amount of stations in section 2:");
        numOfStaB = input.nextInt();
        System.out.print("Average time between each car (reasonable time for use:around 1.5 seconds)");
        averageCars = input.nextDouble();
        System.out.print("Average wash time (reasonable time for use:around 3 seconds)");
        averageWash = input.nextDouble();

        //start time of the program
        long startTime = System.currentTimeMillis();

        //create a carWash object
        CarWash carWash = new CarWash(startTime , numOfCars , averageCars , averageWash ,numOfStaA, numOfStaB);

        //array of threadsCar
        Car [] threadCar = new Car[numOfCars];

        //start each thread
        for (int i=0 ; i<numOfCars ; i++){
            Car car = new Car(carWash,i); //create a car thread
            threadCar[i] = car; //extend thread and not implement runnable
            threadCar[i].start(); //run the thread
        }

        //Close the CarWash when all M cars have left the CarWash
        //join the main thread to all the cars
        for (Car car:threadCar) {
            try {
                car.join();
            } catch (InterruptedException e) {
            }
        }

        //The average waiting time of cars in each station
        System.out.printf("\nThe average waiting time of the cars in station A: %.2f milli seconds.\n" , carWash.getAverageTimeA() / numOfCars);
        System.out.printf("The average waiting time of the cars in station B: %.2f milli seconds.\n" , carWash.getAverageTimeB() / numOfCars);
        System.out.printf("The average waiting time of the cars in station C: %.2f milli seconds.\n" , carWash.getAverageTimeC() / numOfCars);

        System.out.println("\nFinish.Thank you !");
    }
}