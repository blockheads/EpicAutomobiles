package Application;


import java.util.Scanner;

/**
 * Simple Command line interface...
 */
public class Interface {

    private Scanner scanner;
    private boolean running;

    public void start(){

        running = true;
        while(running){
            command();
        }
    }

    public Interface(){
        scanner = new Scanner(System.in);
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
                Commands.SampleCommand(args);

        }

    }


}
