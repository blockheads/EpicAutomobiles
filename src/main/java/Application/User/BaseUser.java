package Application.User;

import Application.Commands;

public interface BaseUser {

    /**
     *
     * @return list of command names inside of a String List
     * ['Command1', 'Command2', ... ]
     */
    public String[] getCommands();
}
