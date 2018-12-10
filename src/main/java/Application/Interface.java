package Application;


import Application.User.*;

import java.sql.Connection;
import java.sql.SQLOutput;
import java.util.Scanner;

/**
 * Simple Command line interface...
 */
public class Interface {

    private Scanner scanner;
    private boolean running;
    private boolean activeUser;
    private BaseUser user;
    private Connection con;

    public void start(){

        running = true;
        while(running){
            if(activeUser) {
                command();
            }
            else{
                login();
            }
        }
    }

    public Interface(Connection con){
        scanner = new Scanner(System.in);
        this.con = con;
    }

    public void command(){
        System.out.println("Please Enter in a command... ( Type help for a list of commands, quit to leave )");
        System.out.println("Syntax 'CommandName Arg1 Arg2 ... Argn'");
        String input = scanner.nextLine();
        if(input.equals("quit")){
            running = false;
            System.out.println("Goodbye, thank you for running our service!");
        }

        String[] commandList = input.split(" ");
        String[] args = new String[commandList.length-1];

        // storing [1::n] in args array
        for(int i=1; i<commandList.length; i++){
            args[i-1] = commandList[i];
        }

        commandList[0] = commandList[0].toLowerCase();

        System.out.println(commandList[0]);

        switch (commandList[0]){
            // here specify your command and link it up in the command file
            // the cases are lower case so we allow for the command to be entered in with any upercase/lowercase...
            case "help":
                System.out.println("As your user you can execute the following commands...");
                System.out.println("Given 'ExampleCommand Arg1: ex_arg_name1 Arg2: ex_arg_name2' -> 'ExampleCommand " +
                        "ex_input1 ex_input2'");
                System.out.println("*----------------------------------------------------------------------*\n");
                for ( String command : user.getCommands()){
                    getHelp(command);
                    System.out.println("*----------------------------------------------------------------------*\n");
                }
                break;
            case "salesofbrand":
                if(handleErrorCases("salesOfBrand",args, 1)) {
                    Commands.salesOfBrand(con, args[0]);
                }
                break;
            case "customerlookup":
                if(handleErrorCases("customerLookup",args, 2)) {
                    Commands.customerLookup(con, args[0], args[1]);
                }
                break;
            case "salesofmodel":
                if(handleErrorCases("salesOfModel",args, 2)) {
                    Commands.salesOfModel(con, args[0], Integer.parseInt(args[1]));
                }
                break;
            case "salesofbrands":
                if(handleErrorCases("salesOfBrands",args, 0)) {
                    Commands.salesOfBrands(con);
                }
                break;
            case "vehiclelookupdealers":
                if(handleErrorCases("vehicleLookupDealers",args, 2)) {
                    Commands.vehicleLookupDealers(con, args[0], args[1]);
                }
            case "salesofcustomer":
                if(handleErrorCases("salesOfCustomer",args, 1)) {
                    Commands.salesOfCustomer(con, args[0]);
                }
                break;
            case "registercustomer":
                if(handleErrorCases("registerCustomer",commandList, 0)) {
                    System.out.println("Registering customer...");
                    boolean done = false;

                    System.out.print("SSN: ");
                    String ssn = scanner.nextLine();

                    System.out.print("First Name: ");
                    String firstName = scanner.nextLine();

                    System.out.print("Last Name: ");
                    String lastName = scanner.nextLine();

                    System.out.print("Phone Number: ");
                    String phoneNumber = scanner.nextLine();

                    System.out.print("Gender: ");
                    String gender = scanner.nextLine();

                    System.out.print("Annual Income: ");
                    int annualIncome = Integer.parseInt( scanner.nextLine());

                    System.out.print("Street Address: ");
                    String streetAddress = scanner.nextLine();

                    System.out.print("City: ");
                    String city = scanner.nextLine();

                    System.out.print("Zip Code: ");
                    String zipCode = scanner.nextLine();

                    System.out.print("State: ");
                    String state = scanner.nextLine();

                    Commands.registerCustomer(con, ssn, firstName, lastName, phoneNumber, gender, annualIncome,
                            streetAddress, city, zipCode, state);
                }
                break;
            case "addvehicle":
                if(handleErrorCases("addVehicle",commandList, 1)) {
                    Commands.addVehicle(con, args[0], null, null,null,null,null);
                }
                break;
            case "purchasevehicle":
                if(handleErrorCases("purchaseVehicle",commandList, 2)) {
                    Commands.purchaseVehicle(con, args[0], args[1]);
                }
                break;
            default:
                if(running) {
                    System.out.println("Invalid Command " + commandList[0] + "!");
                }

        }

    }

    public void login(){
        System.out.println("Welcome to Automobile database no.34323, please select your service, simply enter in ");
        System.out.println("the number of the desired service...\n");
        System.out.println("1) Database Administrator");
        System.out.println("2) Vehicle Locator Service");
        System.out.println("3) Online Customers");
        System.out.println("4) Marketing Department");

        String input = scanner.nextLine();
        if(input.equals("quit")){
            logedOut();
            running = false;
        }

        switch (input){

            case "1":
                user = new Admin();
                System.out.println("Logged in as admin");
                logedIn();
                break;
            case "2":
                user = new VehicleLocatorService();
                logedIn();
                break;
            case "3":
                user = new Customer();
                System.out.println("Logged in as customer");
                logedIn();
                break;
            case "4":
                user = new MarketingDepartment();
                System.out.println("Logged in as marketing dept");
                logedIn();
                break;
            default:
                System.out.println("Invalid input! Please enter in a number 1-4...");

        }
    }

    private void logedIn(){
        activeUser = true;
    }

    private void logedOut(){
        activeUser = false;
    }


    /**
     * Helper func for switch statement to handle common error cases.
     * returns false if command can not execute properly
     */
    private boolean handleErrorCases(String commandName, String[] commandList,int args){
        if(!user.getCommands().contains(commandName)){
            System.out.println("Your user group does not have access to the command " +  commandName + ".");
            return false;
        }

        if (commandList.length < args){
            System.out.println("Invalid length of command, command requires at-least " + args + " arguments.");
            return false;
        }

        // executed properly here
        return true;
    }


    private void getHelp(String command){
        command =  command.toLowerCase();
        switch (command){
            // here specify your command and link it up in the command file
            // the cases are lower case so we allow for the command to be entered in with any upercase/lowercase...
            case "help":
                System.out.println("Haha very funny...");
                break;
            case "salesofbrand":
                System.out.println("salesOfBrand arg1:model arg2:year");
                System.out.println("Gets the sales of a particular model and year, given listed as a count.");
                break;
            case "customerlookup":
                System.out.println("customerLookup arg1:firstname arg2:lastname");
                System.out.println("Displays sales made to a customer with a specified first name last name.");
                break;
            case "salesofmodel":
                System.out.println("salesOfModel arg1:model arg2:year");
                System.out.println("Gets the sales of a particular model and year, given listed as a count.");
                break;
            case "salesofbrands":
                System.out.println("salesOfBrands");
                System.out.println("Groups by brands and returns the sales of each brand.");
                break;
            case "vehiclelookupdealers":
                System.out.println("vehicleLookupDealers arg1:model arg2:year");
                System.out.println("Selects dealers with the given vehicle model and year.");
                break;
            case "salesofcustomer":
                System.out.println("salesOfCustomer arg1:ssn");
                System.out.println("Displays sale of a given customer.");
                break;
            case "registerCustomer":
                System.out.println("registerCustomer arg1:ssn arg2:firstName arg3:lastName");
                System.out.println("Registers the current customer so they can purchase vehicles.");
                break;
        }

    }
}
