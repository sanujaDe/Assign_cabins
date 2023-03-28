package com.sanuja;

public class Cabin {

    private int cabinNumber;
    private Passenger[] passengers;
    private int passengerCount = 0;
    private boolean status = false;

    public Cabin(int cabinNumber, Passenger[] passengers) {
        this.cabinNumber = cabinNumber;
        this.passengers = passengers;
        this.status = true;
    }

    public void addPassenger(Passenger passenger){
        this.passengers[passengerCount] = passenger;
        this.passengerCount++;
    }

    public int searchName(String name){
        for(Passenger passenger: this.getPassengers()){
            if(passenger.getFullName().contains(name)){
                return this.getCabinNumber();
            }
        }
         return -1;
    }

    public String[] getPassengerNames(){
        String[] passengerNames = new String[3];


        for (int i = 0; i < this.passengers.length; i++) {
            if (this.passengers[i] == null){
                break;
            }
            String passengerName = this.passengers[i].getFirstName() + " " + this.passengers[i].getLastName();
            passengerNames[i] = passengerName;
        }
        return passengerNames;
    }

    public int getCabinNumber() {
        return cabinNumber;
    }

    public void setCabinNumber(int cabinNumber) {
        this.cabinNumber = cabinNumber;
    }

    public Passenger[] getPassengers() {
        return passengers;
    }

    public void setPassengers(Passenger[] passengers) {
        this.passengers = passengers;
    }
}
