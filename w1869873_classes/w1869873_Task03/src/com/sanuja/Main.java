package com.sanuja;
import java.io.*;
import java.util.*;

public class Main {

    static Scanner input;
    static Passenger[] customerQueue = new Passenger[5];
    static int queuePostion = 0;

    static Cabin[] hotel = new Cabin[12];


    public static void main(String[] args) {
//  logic for main menu options
        String optionMenu = "";
        boolean menuLoop = true;

        initialise(hotel);
        while (menuLoop) {
            optionMenu = printMenu();
            if (optionMenu.equals("E")) {
                displayEmptyCabins();
            }

            else if (optionMenu.equals("A")) {
                addCustomerToCabin();
            }

            else if (optionMenu.equals("V")) {
                viewAllCabins();
            }

            else if (optionMenu.equals("D")) {
                deleteCustomer();
                viewAllCabins();
            }


            else if (optionMenu.equals("Q")) {
                menuLoop = false;
            }

            else if (optionMenu.equals("F")) {
                findCabinFromName();
            }

            else if (optionMenu.equals("S")) {
                storeProgramToFile();
            }
            else if (optionMenu.equals("L")){
                loadProgramFromFile();
            }
            else if(optionMenu.equals("O")){
                viewAlphabetically();
            }
            else if(optionMenu.equals("T")){
                getExpenses();
            }

        }
    }

    //========================= methods ==========================================

    // to check whether the inputs are valid
    private static boolean sanitizeInput(String s) {
        String[] list = {"A", "V", "E", "D", "S", "O", "Q", "F", "L", "T"};
        boolean match = false;
        for (String option : list) {
            if (option.equals(s)) match = true;
        }
        return match;
    }

    // to print the main menu if the inputs are valid according to sanitizeInput()
    private static String printMenu() {
        String menu;
        do {
            input = new Scanner(System.in);
            System.out.println("\nMain Menu" +
                    "\nchoose from the below options :" +
                    "\nA  : Add Customer to Cabin" +
                    "\nV  : View All Cabins" +
                    "\nE  : Display Empty Cabins" +
                    "\nD  : Delete Customer from Cabin" +
                    "\nF  : Find Cabin from customer Name" +
                    "\nS  : Store program data into file" +
                    "\nL  : Load program data from file" +
                    "\nO  : View passengers ordered alphabetically by name" +
                    "\nT  : View expenses" +
                    "\nQ  : Quit");
            menu = input.nextLine().toUpperCase();
        } while (!sanitizeInput(menu));
        return menu;
    }


    private static void initialise(Cabin[] hotelRef) {
        for (int x = 0; x < 12; x++) hotelRef[x] = null;
        System.out.println("initialise ");

    }

    // to add customers to the cabin
    // this will check whether the room is available and add the customer to queue
    private static void addCustomerToCabin() {
        input = new Scanner(System.in);
        boolean isRoomAvailable = false;
        for(int j = 0; j<hotel.length; j++ ){
            if(hotel[j] == null) {isRoomAvailable = true; break;}
        }
        if(isRoomAvailable) {
            int roomNum = roomNumValid();
            Passenger[] passengers = new Passenger[3];
            for (int i = 0; i < 3; i++) {
                passengers[i] = addCustomer(String.valueOf(roomNum));

                // ask to continue taking passengers for room (roomNum);
                System.out.println("If you wish to add more passengers to " + roomNum + " insert 'Y' :");
                char cont = input.nextLine().toUpperCase().charAt(0);
                if (cont != 'Y')
                    break;


            }
            hotel[roomNum - 1] = new Cabin(roomNum, passengers);
        } else {
            //
            if(queuePostion < 5)
                customerQueue[queuePostion++] = addCustomer("|Queue|");
            else
                System.out.println("Queue full");
        }
    }
// add customers to the cabin
    private static Passenger addCustomer(String roomNum){
        System.out.println("Enter First name for room " +
                (roomNum) + " :");
        String firstName = input.nextLine();
        System.out.println("Enter Last name of " +
                (firstName) + " :");
        String lastName = input.nextLine();
        int expenses = expensesValid();
        Passenger passenger = new Passenger(firstName, lastName, expenses);
        return passenger;
    }

    // to check whether the room number is valid
    private static int roomNumValid(){
        boolean isValid =false;
        int roomNum = 0;
        do {
            System.out.println("\nEnter room number (1-12) :");
            try {
                roomNum = (Integer.parseInt(input.nextLine()));
                if(roomNum < 13 && roomNum >0){
                    isValid = true;
                }
            } catch (Exception e){
                System.out.println("Please enter a valid room ID between '1 to 12' : ");
            }
         }while (!isValid);
        return roomNum;
    }

    // to check whether the expenses are integers
    private static int expensesValid(){
        boolean isValid;
        int expenses = 0;
        do {
            try {
                System.out.println("Enter Expenses for the passenger ");
                expenses = Integer.parseInt(input.nextLine());
                isValid = false;

            } catch (Exception e){
                System.out.println("Please enter a valid amount (integer) : ");
                isValid = true;
            }
        }while (isValid);
        return expenses;
    }

    // to display all cabins
    private static void viewAllCabins() {
        for (int x = 0; x < hotel.length ; x++) {
            int cabinNumber = x + 1;
            if(hotel[x] == null){
                System.out.println("room " + cabinNumber + " is not occupied");
            } else {
                for (int i = 0; i < hotel[x].getPassengerNames().length; i++) {
                    String empty = hotel[x].getPassengerNames()[i];
                    if (hotel[x].getPassengerNames()[i] == null){
                        break;
                    }
                    else{
                        System.out.println("room " + cabinNumber + " occupied by " + empty);}
                }
            }
        }
    }

// to display empty cabins
    private static void displayEmptyCabins() {
        System.out.println("\nAvailable Cabins\n");
        for (int x = 0; x < 12; x++) {
            if (hotel[x] == null)
                System.out.println("room " + (x + 1) + " is empty");
        }
    }
    // to delete the customer from cabin by cabin number
    private static void deleteCustomer() {
        System.out.println("Enter the cabin number to remove the customers : ");
        int delete = input.nextInt();
        hotel[delete - 1] = null;
        System.out.println("Customers removed from cabin number  : " + delete);
        if(queuePostion > 0) {
            Passenger[] passengers = new Passenger[3];
            passengers[0] = customerQueue[0];
            hotel[delete - 1] = new Cabin(delete, passengers);
            for(int i = 0; i < customerQueue.length; i++){
                if(customerQueue[i+1] == null) break;
                customerQueue[i] = customerQueue[i+1];
                queuePostion = i;
            }
        }


    }
// find cabin number by customer name
    private static void findCabinFromName() {
        input = new Scanner(System.in);
        System.out.println("Enter the name : ");
        boolean found = false;
        String search = input.nextLine();
        for (int i = 0; i < hotel.length; i++) {
            if(hotel[i] != null)
                if (hotel[i].searchName(search) != -1) {
                    System.out.println("Found "+ search + " in room number : " + hotel[i].searchName(search));
                    found = true;
                }
        }
        if(!found) System.out.println("Passenger not found!");
    }

// to store data into a file
    private static void storeProgramToFile() {
        // CSV file format = comma separated values
        try {
            FileWriter wr = new FileWriter("db.csv", false); // csv file format used
            for (int i = 0; i < hotel.length; i++) {
                if (hotel[i] == null)
                    wr.write("empty\n");

                else {
                    String writeString = hotel[i].getCabinNumber() + ",";
                    for (int x = 0; x < hotel[i].getPassengers().length; x++) {
                        if (hotel[i].getPassengers()[x] == null){
                            writeString += "n/a" + "," + "n/a" + ",";
                        }
                        else {
                            writeString += hotel[i].getPassengers()[x].getFullName() + "," + hotel[i].getPassengers()[x].getExpenses() + ",";
                        }
                    }
                    wr.write(writeString + "\n");
                }
            }
            wr.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error cannot write to file");

        }
    }

    // sort alphabetically
    private static String[] sortMethod(String[] hotel){
            for(int i = 0; i < hotel.length - 1; i++)
            {   if (hotel[i]==null) {continue;}
                else{
                for(int j = i+1; j < hotel.length; j++)
                {   if (hotel[j]==null) {continue;}
                    else{
                    if(hotel[i].compareTo(hotel[j]) > 0)//words[i] is greater than words[j]
                    {
                        String temp = hotel[i];
                        hotel[i] = hotel[j];
                        hotel[j] = temp;
                    }
                }}
            }}
            return hotel;
    }

    // load data from a file to the program
    private static void loadProgramFromFile() {
        File file=new File("db.csv");
        Scanner sc= null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int x = 0;
        while(sc.hasNextLine()){
//            hotel[x++] = sc.nextLine();hotel[x] = null;
            String[] lineObjects = sc.nextLine().split(",");
            if(lineObjects[0].equals("empty") || lineObjects[0].isEmpty()){
                hotel[x] = null;
                x++;
                continue;
            }
            int roomId = Integer.parseInt(lineObjects[0]);
            Passenger[] passengers = new Passenger[3];
            int passengerCount = 0;
            for(int i = 1; i < 7; i = i + 2){
                if(!lineObjects[i].equals("n/a")) {
                    String customerFirstName = lineObjects[i].split(" ")[0];
                    String customerLastName = lineObjects[i].split(" ")[1];
                    int customerExpenses = Integer.parseInt(lineObjects[i+1]);
                    Passenger passenger = new Passenger(customerFirstName, customerLastName, customerExpenses);
                    passengers[passengerCount++] = passenger;
                }
            }
            hotel[x] = new Cabin(roomId, passengers);
            x++;
        }
        System.out.println("\nCabins loaded from file\n");
        viewAllCabins();
    }

    // to view customers in alphabetical order
    private static void viewAlphabetically() {
        String[] dtoHotel = new String[36];
        int dtoHotelCount = 0;
        for (int i = 0; i < hotel.length; i++) {
                for (int j = 0; j < 3; j++) {
                    if(hotel[i] == null){
                        dtoHotel[dtoHotelCount] = "";
                    } else {
                        dtoHotel[dtoHotelCount] = hotel[i].getPassengerNames()[j];
                    }
                    dtoHotelCount++;
                }
        }
        String[] sortHotel = sortMethod(dtoHotel);
        System.out.println("\nCustomers in alphabetical order\n");
        for (int i = 0; i < sortHotel.length ; i++) {
            if(sortHotel[i]== null) continue;
            else if(sortHotel[i].isEmpty()) continue;

            System.out.println(sortHotel[i]);
        }
    }
    // logic to calculate and view expenses
    private static void getExpenses(){
        System.out.println("\nExpenses\n");
        int total = 0;
        for (int x = 0; x < hotel.length ; x++) {
            if(hotel[x] != null){
                for (int i = 0; i < hotel[x].getPassengerNames().length; i++) {
                    if (hotel[x].getPassengers()[i] == null){ break;}
                    total += hotel[x].getPassengers()[i].getExpenses();
                    System.out.println("Passenger => " + hotel[x].getPassengers()[i].getFullName() + "'s expenses : " + hotel[x].getPassengers()[i].getExpenses());
                }
            }
        }
        System.out.println("Total  : " + total);
    }



}
