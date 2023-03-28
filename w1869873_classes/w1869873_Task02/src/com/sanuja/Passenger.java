package com.sanuja;

// getters and setters were generated
public class Passenger {
    private String firstName;
    private String lastName;
    private int expenses;

    public int getExpenses() {
        return expenses;
    }

    public void setExpenses(int expenses) {
        this.expenses = expenses;
    }

    public Passenger(String firstName, String lastName , int expenses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.expenses = expenses;

    }

    public Passenger(String firstName) {
        this.firstName = firstName;
    }

    public String getFullName(){
        return this.getFirstName() + " " + getLastName();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


}
