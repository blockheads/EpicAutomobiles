package Application.User;

public class Admin implements BaseUser{

    private String[] commands = {
            "Command1"
    };

    @Override
    public String[] getCommands() {
        return commands;
    }
}
