// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Arrays;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        //Assigning the variables
        String cashierSlotsStatus[][] = {{"X", "X"}, {"X", "X", "X"}, {"X", "X", "X", "X", "X"}};
        String cashierSlotsName[][] = {{"1A", "1B"}, {"2A", "2B", "2C"}, {"3A", "3B", "3C", "3D", "3E"}};
        String customerNameArr[][] = {{"", ""}, {"", "", ""}, {"", "", "", "", ""}};

        int burgerStock = 50;

        int mainLooping = 1;
        while (mainLooping == 1) {

            //Prompt Menu
            println("100 or VFQ: View all Queues.\n101 or VEQ: View all Empty Queues.\n" +
                    "102 or ACQ: Add customer to a Queue.\n103 or RCQ: Remove a customer from a Queue.\n" +
                    "104 or PCQ: Remove a served customer.\n105 or VCS: View Customers Sorted in alphabetical order.\n" +
                    "106 or SPD: Store Program Data into file.\n107 or LPD: Load Program Data from file.\n" +
                    "108 or STK: View Remaining burgers Stock.\n109 or AFS: Add burgers to Stock.\n999 or EXT: Exit the Program");
            Scanner scan = new Scanner(System.in);
            print("Enter a option from the above menu: ");
            String option = scan.next();
            option = option.toUpperCase();

            //To Perform task that user require
            switch (option) {
                //To View all queues' status
                case ("100"):
                case ("VFQ"):
                    slotDiagram(cashierSlotsStatus);
                    println("X - Not Occupied\nO - Occupied");
                    backToMenu();
                    break;
                //To View empty slots in queue
                case ("101"):
                case ("VEQ"):
                    viewEmptySlots(cashierSlotsStatus);
                    backToMenu();
                    break;
                //To add a customer to a queue
                case ("102"):
                case ("ACQ"):
                    addCustomer(cashierSlotsStatus, cashierSlotsName, customerNameArr);
                    break;
                //To remove a customer from specific slot
                case ("103"):
                case ("RCQ"):
                    removeCustomer(cashierSlotsStatus, cashierSlotsName, customerNameArr);
                    break;
                //To remove served customer from the slot
                case ("104"):
                case ("PCQ"):
                    burgerStock = removeServedCustomer(cashierSlotsStatus, cashierSlotsName, customerNameArr,burgerStock);
                    break;
                //To sort Customers in Alphabetical order
                case ("105"):
                case ("VCS"):
                    customerAlphabeticalOrder(customerNameArr);
                    backToMenu();
                    break;
                //To Store Data into text file
                case("106"):
                case("SPD"):
                    storeData(cashierSlotsStatus,customerNameArr,burgerStock);
                    break;
                //To retrieve data from text file and update the program with previously saved data
                case ("107"):
                case("LPD"):
                    print("Enter 1 to restore Backed up data or Enter any key to Go Back to the menu: ");
                    String execute = scan.next();
                    if (execute.equals("1")) {
                        try {
                            File readFile = new File("storingFile.txt");
                            Scanner file_reader = new Scanner(readFile);


                            int line = 0;
                            int row = 0;
                            while (row < 5) {
                                int column = 0;
                                while (column < 3) {
                                    if ((column == 0 && row > 1) || (column == 1 && row > 2)) {
                                        column++;
                                        continue;
                                    }
                                    cashierSlotsStatus[column][row] = file_reader.nextLine();
                                    line++;
                                    column++;
                                }
                                row++;
                            }
                            row = 0;
                            while (row < 5) {
                                int column = 0;
                                while (column < 3) {
                                    if ((column == 0 && row > 1) || (column == 1 && row > 2)) {
                                        column++;
                                        continue;
                                    }
                                    customerNameArr[column][row] = file_reader.nextLine();
                                    line++;
                                    column++;
                                }
                                row++;
                            }
                            burgerStock = Integer.parseInt(file_reader.nextLine());
                            println("The data from the file has been updated.");
                            file_reader.close();

                        } catch (IOException e) {
                            System.out.println("An error occurred.");
                        }
                    }
                    break;

                //To view remaining burgers in stock
                case ("108"):
                case ("STK"):
                    println(burgerStock + " burgers are remaining.");
                    backToMenu();
                    break;
                // To Add burgers to the stock
                case ("109"):
                case ("AFS"):
                    burgerStock = addBurgerStock(burgerStock);
                    break;
                //To Exit the Main Program
                case ("999"):
                case ("EXT"):
                    mainLooping = 0;
                    break;

                default:
                    println("Enter Valid Option.");
            }
        }
    }
    public static void print(Object line) {//To print objects in same line
        System.out.print(line);
    }

    public static void println(Object line) {//To print objects in next line
        System.out.println(line);
    }

    public static void slotDiagram(String array[][]) {//To print occupied and unoccupied slots in a diagram method
        println("*******************\n*     Cashiers    *\n*******************");
        println("   1     2     3");
        println("A  " + array[0][0] + "     " + array[1][0] + "     " + array[2][0]);
        println("B  " + array[0][1] + "     " + array[1][1] + "     " + array[2][1]);
        println("C        " + array[1][2] + "     " + array[2][2]);
        println("D              " + array[2][3]);
        println("E              " + array[2][4]);
    }
    public static void viewEmptySlots(String array1[][]) {//To view unoccupied slot in a diagram
        String cashierEmptySlots[][] = {{"", ""}, {"", "", ""}, {"", "", "", "", ""}};
        int row = 0;
        while (row < 5) {
            int column = 0;
            while (column < 3) {
                if ((column == 0 && row > 1) || (column == 1 && row > 2)) {
                    column++;
                    continue;
                }
                if (array1[column][row].equals("X")) {
                    cashierEmptySlots[column][row] = "X";
                } else {
                    cashierEmptySlots[column][row] = " ";
                }
                column++;
            }
            row++;
        }
        slotDiagram(cashierEmptySlots);
    }
    public static void addCustomer(String array1[][], String array2[][], String array3[][]) {//To add customer to a specific slot
        Scanner scan = new Scanner(System.in);
        int looping = 1;
        while(looping == 1) {
            print("Enter Customer's name or Get to next step to Go back to the menu: ");
            String customerName = scan.next();
            customerName = customerName.toUpperCase();
            int innerLooping = 1;
            while (innerLooping == 1) {
                println("Available slots");
                slotDiagram(array1);
                println("X - Not Occupied\nO - Occupied");
                print("Enter the slot where customer needed to be added or Enter 1 to Go back to the menu (eg: 1A, 3E): ");
                String customerSlot = scan.next();
                customerSlot = customerSlot.toUpperCase();

                int inValidEntry = 1;
                int row = 0;
                while (row < 5) {
                    int column = 0;
                    while (column < 3) {
                        if ((column == 0 && row > 1) || (column == 1 && row > 2)) {
                            column++;
                            continue;
                        }
                        if (customerSlot.equals(array2[column][row])) {
                            if(checkFront(column,row,array1)) {
                                if (array1[column][row].equals("X")) {
                                    array1[column][row] = "O";
                                    array3[column][row] = customerName;
                                    println("Added " + customerName + " to " + customerSlot + " slot.");
                                    inValidEntry = 0;
                                    innerLooping = 0;
                                    print("Enter 1 to add another Customer or Enter any character to Go back to the menu: ");
                                    char loop = scan.next().charAt(0);
                                    if (loop == '1') {
                                        looping = 1;
                                    } else {
                                        looping = 0;
                                    }

                                } else {
                                    println(customerSlot + " slot is occupied, Enter a not occupied slot.");
                                    innerLooping = 1;
                                    inValidEntry = 0;
                                }
                            }
                            else{
                                println("Empty slots available in front");
                                innerLooping = 1;
                                inValidEntry = 0;
                            }
                        }
                        else if (column == 2 && row == 4 && inValidEntry == 1) {
                            println("Invalid Slot, Enter a Valid slot.");
                        }
                        else if (customerSlot.equals("1")) {
                            looping = 0;
                            innerLooping = 0;
                            inValidEntry = 0;
                        }

                        column++;
                    }
                    row++;
                }
            }


        }
    }
    public static boolean checkFront(int column, int row, String array4[][]){//To check whether the slots in front of given slot are free
        boolean value = true;
        for (int i = 0; i < row;i++){
            if(array4[column][i].equals("X")){
                value = false;
                break;
            }
            else{
                value = true;

            }

        }
        return value;
    }

    public static void removeCustomer(String array1[][], String array2[][], String array3[][]) {//To remove customer from a slot
        int looping = 1;
        while (looping == 1) {
            Scanner scan = new Scanner(System.in);
            slotDiagram(array1);
            println("X - Not Occupied\nO - Occupied");
            print("Enter the slot where customer needed to be removed or Enter 1 to Go Back to the menu (eg :- 1A, 3E): ");
            String customerSlot = scan.next();
            customerSlot = customerSlot.toUpperCase();

            int inValidEntry = 1;
            int row = 0;
            while (row < 5) {
                int column = 0;
                while (column < 3) {
                    if ((column == 0 && row > 1) || (column == 1 && row > 2)) {
                        column++;
                        continue;
                    }
                    if (customerSlot.equals(array2[column][row])) {
                        if (array1[column][row].equals("O")) {
                            println(array3[column][row] + " is been removed from " + array2[column][row] + " slot.");
                            while (row < (array1[column].length - 1)) {
                                array1[column][row] = array1[column][row + 1];
                                array3[column][row] = array3[column][row + 1];
                                row++;
                            }
                            array1[column][array1[column].length - 1] = "X";
                            array3[column][array3[column].length - 1] = " ";
                            inValidEntry = 0;
                            looping = 0;
                        } else {
                            println(customerSlot + " slot is not occupied, Enter a occupied slot.");
                            looping = 1;
                            inValidEntry = 0;
                        }
                    } else if (row == 4 && column == 2 && inValidEntry == 1) {
                        println("Incorrect Entry, Enter Valid Option.");
                    } else if (customerSlot.equals("1")) {
                        looping = 0;
                        inValidEntry = 0;
                    }
                    column++;
                }
                row++;
            }
        }
    }
    public static int removeServedCustomer(String array1[][], String array2[][], String array3[][], int stock){//To remove served customer from Slot
        Scanner scan = new Scanner(System.in);
        int looping = 1;
        while (looping == 1) {
            slotDiagram(array1);
            println("X - Not Occupied\nO - Occupied");
            print("Enter the slot where the served customer needed to be removed or Enter 1 to Go Back to the menu (eg :-1A, 2A, 3A): ");
            String customerSlot = scan.next();
            customerSlot = customerSlot.toUpperCase();

            int inValidEntry = 1;
            int column = 0;
            int row = 0;
            while (column < 3) {
                if (customerSlot.equals(array2[column][row])) {
                    if (array1[column][row].equals("O")){
                        println(array3[column][row] + " is been served and removed from " + array2[column][row] + " slot.");
                        while (row < (array1[column].length - 1)) {
                            array1[column][row] = array1[column][row + 1];
                            array3[column][row] = array3[column][row + 1];
                            row++;
                        }
                        array1[column][array1[column].length - 1] = "X";
                        array3[column][array3[column].length - 1] = " ";
                        looping = 0;
                        inValidEntry = 0;
                        stock = stock - 5;

                    } else {
                        println(customerSlot + " slot is not occupied, Enter a occupied slot.");
                        looping = 1;
                        inValidEntry = 0;
                    }
                } else if (column == 2 && inValidEntry == 1) {
                    println("Incorrect Entry, Enter Valid Option.");
                } else if (customerSlot.equals("1")) {
                    looping = 0;
                    inValidEntry = 0;
                }

                column++;
            }
        }
        if (stock == 10){
            println("Only 10 burgers left");
        }
        if (stock == 0){
            println("No more burgers left");
        }
        return stock;
    }
    public static void customerAlphabeticalOrder(String array[][]) {//To sort customers' name in Alphabetical order
        String customerNameAlphabeticalArr[] = new String[10];
        int row = 0;
        int i = 0;
        while (row < 5) {
            int column = 0;
            while (column < 3) {
                if ((column == 0 && row > 1) || (column == 1 && row > 2 )){
                    column++;
                    continue;
                }
                customerNameAlphabeticalArr[i] = array[column][row];
                i++;


                column++;
            }
            row++;

        }
        for(int a = 0; a < customerNameAlphabeticalArr.length-1;a++){
            for (int b = a + 1; b < customerNameAlphabeticalArr.length; b++){
                String firstInOrder = compareStrings(customerNameAlphabeticalArr[a],customerNameAlphabeticalArr[b]);
                if (customerNameAlphabeticalArr[a].equals(firstInOrder)){
                    continue;
                }
                else{
                    String temp = customerNameAlphabeticalArr[a];
                    customerNameAlphabeticalArr[a] = firstInOrder;
                    customerNameAlphabeticalArr[b] = temp;
                }
            }
        }

        for(int k = 0; k < customerNameAlphabeticalArr.length; k++){
            if (!customerNameAlphabeticalArr[k].equals("")){
                println(customerNameAlphabeticalArr[k]);
            }
        }
    }

    public static String compareStrings(String str1, String str2){//To compare two strings and find the Alphabetical order of them

        String firstInOrder = "";
        int i = 0;
        try{
            while(true) {
                if (str1.charAt(i) == str2.charAt(i)) {
                    i++;
                } else if (str1.charAt(i) > str2.charAt(i)) {
                    firstInOrder = str2;
                    break;
                } else {
                    firstInOrder = str1;
                    break;
                }
            }
        }
        catch(Exception e){
            firstInOrder = str1;
        }
        return firstInOrder;
    }
    public static void storeData(String array1[][],String array2[][],int bStock){//to store current data to a text file
        File file = new File("storingFile.txt");

        while(true) {
            if (file.exists()) {
                try {
                    FileWriter myWriter = new FileWriter("storingFile.txt");
                    int row = 0;
                    while (row < 5) {
                        int column = 0;
                        while (column < 3) {
                            if ((column == 0 && row > 1) || (column == 1 && row > 2)) {
                                column++;
                                continue;
                            }
                            myWriter.write(array1[column][row]+"\n");

                            column++;
                        }
                        row++;
                    }
                    row = 0;
                    while (row < 5) {
                        int column = 0;
                        while (column < 3) {
                            if ((column == 0 && row > 1) || (column == 1 && row > 2)) {
                                column++;
                                continue;
                            }
                            myWriter.write(array2[column][row]+"\n");
                            column++;
                        }
                        row++;
                    }
                    String stock = Integer.toString(bStock);
                    myWriter.write(stock);
                    myWriter.close();
                    println("The data has been stored");
                    break;
                }
                catch (IOException e){
                    System.out.println("An error occurred.");
                }

            } else {
                try {
                    if (file.createNewFile()) {
                        System.out.println();
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                }


            }
        }
    }
    public static int addBurgerStock(int stock){
        Scanner scan = new Scanner(System.in);
        int add = 50 - stock;
        while(true) {
            println("Only " + add + " burgers can be added");

            try {
                print("Enter the number of burgers needed to be added or Enter -1 to return back to Main menu: ");
                int addedBurger = scan.nextInt();
                if (addedBurger <= add && addedBurger > 0 ) {
                    stock = stock + addedBurger;
                    println(stock + " burgers are remaining");
                    break;
                }
                if (addedBurger == -1){
                    break;
                }
                else {
                    println("Exceeding the maximum limit of Burger Stock");
                }
                backToMenu();
            }
            catch (Exception e){
                println("Invalid Entry, Enter a number.");
                scan.next();
            }
        }
        return stock;
    }
    public static void backToMenu(){//To get Back to main menu
        Scanner scan = new Scanner(System.in);
        print("Enter any Character to Go back to Main Menu: ");
        char character = scan.nextLine().charAt(0);
        println("");

    }


}

