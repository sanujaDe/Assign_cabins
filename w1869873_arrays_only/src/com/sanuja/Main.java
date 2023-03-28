package com.sanuja;

import java.io.*;
import java.util.*;

public class Main {

    static Scanner input;
    static int roomNum = 1;
    static String roomName;
    static String[] hotel = new String[12];


    public static void main(String[] args) {

        String optionMenu = "";
        boolean menuLoop = true;

        initialise(hotel);
        while (menuLoop) {
            optionMenu = printMenu();
            if (optionMenu.equals("E")) {
                displayEmptyCabins();
            }

            if (optionMenu.equals("A")) {
                addCustomerToCabin();
            }

            if (optionMenu.equals("V")) {
                viewAllCabins();
            }

            if (optionMenu.equals("D")) {
                deleteCustomer();
                viewAllCabins();
            }


            if (optionMenu.equals("Q")) {
                menuLoop = false;
            }

            if (optionMenu.equals("F")) {
                findCabinFromName();
            }

            if (optionMenu.equals("S")) {
                storeProgramToFile();
            }
            if (optionMenu.equals("L")){
                loadProgramFromFile();
            }
            else if(optionMenu.equals("O")){
                viewAlphabetically();
            }

        }
    }

    //========================= methods ==========================================
    // to check whether the inputs are valid
    private static boolean sanitizeInput(String s) {
        String[] list = {"A", "V", "E", "D", "S", "O", "Q", "F", "L"};
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
                    "\nQ  : Quit");
            menu = input.nextLine().toUpperCase(Locale.ROOT);
        } while (!sanitizeInput(menu));
        return menu;
    }


    private static void initialise(String[] hotelRef) {
        for (int x = 0; x < 12; x++) hotelRef[x] = null;
        System.out.println("initialise ");

    }

    // to add customers to the cabin
    private static void addCustomerToCabin() {
        input = new Scanner(System.in);
        System.out.println("\nEnter room number (1-12) :");
        roomNum = Integer.parseInt(input.nextLine());
        System.out.println("Enter name for room " +
                (roomNum) + " :");
        roomName = input.nextLine();
        hotel[roomNum - 1] = roomName;

    }

    // to display all cabins
    private static void viewAllCabins() {
        for (int x = 0; x < 12; x++) {
            if (hotel[x] == null) System.out.println("room " + (x + 1) + " is not occupied");
            else
                System.out.println("room " + (x + 1) + " occupied by " + hotel[x]);

        }
    }


    private static void displayEmptyCabins() {
        System.out.println("\nAvailable Rooms\n");
        for (int x = 0; x < 12; x++) {
            if (hotel[x] == null)
                System.out.println("room " + (x + 1) + " is empty");
        }
    }

    private static void deleteCustomer() {
        System.out.println("Enter the cabin number to delete the customer name : ");
        int delete = input.nextInt();
        hotel[delete - 1] = null;


    }

    private static void findCabinFromName() {
        input = new Scanner(System.in);
        System.out.println("Enter the name : ");
        String search = input.nextLine();
        for (int i = 0; i < hotel.length; i++) {
            if (hotel[i] != null && hotel[i].contains(search)) { // && = if first condition true, check for second condition
                System.out.println("Found room number : " + (i + 1));
            }

        }
    }

    private static void storeProgramToFile() {
        try {
            FileWriter wr = new FileWriter("db.txt", false);
            for (int i = 0; i < hotel.length; i++) {
                if (hotel[i] == null)
                    wr.write("empty\n");
                else
                    wr.write(hotel[i] + "\n");

            }
            wr.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error cannot write to file");

        }
    }

    private static String[] sortMethod(String[] hotel){
            for(int i = 0; i < hotel.length - 1; i++)
            {
                for(int j = i+1; j < hotel.length; j++)
                {
                    if(hotel[i].compareTo(hotel[j]) > 0)//words[i] is greater than words[j]
                    {
                        String temp = hotel[i];
                        hotel[i] = hotel[j];
                        hotel[j] = temp;
                    }
                }
            }
            return hotel;
    }

    private static void loadProgramFromFile() {
        File file=new File("db.txt");
        Scanner sc= null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int x = 0;
        while(sc.hasNextLine()){
//            hotel[x++] = sc.nextLine();
            hotel[x] = sc.nextLine();
            if(hotel[x].equals("empty")) hotel[x] = null;
            System.out.println(hotel[x]);
            x++;
        }
    }

    private static void viewAlphabetically() {
        String[] dtoHotel = new String[12];
        for (int i = 0; i < hotel.length; i++) {
            dtoHotel[i] = hotel[i];
            if(hotel[i] == null) dtoHotel[i] = "";
        }
        String[] sortHotel = sortMethod(dtoHotel);
        for (int i = 0; i < sortHotel.length ; i++) {
            if(sortHotel[i].isEmpty()) continue;
            System.out.println(sortHotel[i]);
        }
    }

}
