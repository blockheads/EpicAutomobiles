package Application;


import Application.User.*;

import java.sql.Connection;
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
        System.out.println("Please Enter in a command... ( Type Help for a list of commands, quit to leave )");
        System.out.println("Syntax 'CommandName Arg1 Arg2 ... Argn'");
        String input = scanner.next();
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
                if(handleErrorCases("salesOfBrand",commandList, 1)) {
                    Commands.salesOfBrand(con, commandList[1]);
                }
                break;
            case "customerlookup":
                if(handleErrorCases("customerLookup",commandList, 2)) {
                    Commands.customerLookup(con, commandList[1], commandList[2]);
                }
                break;
            case "salesofmodel":
                if(handleErrorCases("salesOfModel",commandList, 2)) {
                    Commands.salesOfModel(con, commandList[1], commandList[2]);
                }
                break;
            case "salesofbrands":
                if(handleErrorCases("salesOfBrands",commandList, 0)) {
                    Commands.salesOfBrands(con);
                }
                break;
            case "vehiclelookupdealers":
                if(handleErrorCases("vehicleLookupDealers",commandList, 2)) {
                    Commands.vehicleLookupDealers(con, commandList[1], commandList[2]);
                }
            case "salesofcustomer":
                if(handleErrorCases("salesOfCustomer",commandList, 1)) {
                    Commands.salesOfCustomer(con, commandList[1]);
                }
                break;
            case "registerCustomer":
                if(handleErrorCases("registerCustomer",commandList, 3)) {
                    Commands.registerCustomer(con, commandList[1], commandList[2], commandList[3]);
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

        String input = scanner.next();
        if(input.equals("quit")){
            logedOut();
            running = false;
        }

        switch (input){

            case "1":
                user = new Admin();
                logedIn();
                break;
            case "2":
                user = new VehicleLocatorService();
                logedIn();
                break;
            case "3":
                user = new Customer();
                logedIn();
                break;
            case "4":
                user = new MarketingDepartment();
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

        if (commandList.length < args + 1){
            System.out.println("Invalid length of command, command requires at-least 2 arguments.");
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
                System.out.println("salesOfBrand arg1: model arg2: year");
                System.out.println("Gets the sales of a particular model and year, given listed as a count.");
                break;
            case "customerlookup":
                System.out.println("customerLookup arg1: first name arg2: last name");
                System.out.println("Displays sales made to a customer with a specified first name last name.");
                break;
            case "salesofmodel":
                System.out.println("salesOfModel arg1: model arg2: year");
                System.out.println("Gets the sales of a particular model and year, given listed as a count.");
                break;
            case "salesofbrands":
                System.out.println("salesOfBrands");
                System.out.println("Groups by brands and returns the sales of each brand.");
                break;
            case "vehiclelookupdealers":
                System.out.println("vehicleLookupDealers arg1: model arg2: year");
                System.out.println("Selects dealers with the given vehicle model and year.");
                break;
            case "salesofcustomer":
                System.out.println("salesOfCustomer arg1: ssn");
                System.out.println("Displays sale of a given customer.");
                break;
            case "registerCustomer":
                System.out.println("registerCustomer arg1: ssn arg2: firstName arg3: lastName");
                System.out.println("Registers the current customer so they can purchase vehicles.");
                break;
        }

    }
}
