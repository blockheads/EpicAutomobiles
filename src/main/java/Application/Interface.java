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
    private boolean logedIn;
    private BaseUser user;
    private Connection con;

    public void start(){

        running = true;
        while(running){
            if(logedIn) {
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
        }

        String[] commandList = input.split(" ");
        String[] args = new String[commandList.length-1];

        // storing [1::n] in args array
        for(int i=1; i<commandList.length; i++){
            args[i-1] = commandList[i];
        }

        switch (commandList[0]){

            // here specify your command and link it up in the command file
            case "SampleCommand":
                break;

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
            running = false;
        }

        switch (input){

            case "1":
                user = new Admin();
                break;
            case "2":
                user = new VehicleLocatorService();
                break;
            case "3":
                user = new Customer();
                break;
            case "4":
                user = new MarketingDepartment();
                break;
        }
    }


}
