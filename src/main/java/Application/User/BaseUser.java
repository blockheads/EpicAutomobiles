package Application.User;

import Application.Commands;

import java.util.Set;

public interface BaseUser {

    /**
     *
     * @return list of command names inside of a String List
     * ['Command1', 'Command2', ... ]
     */
    public Set<String> getCommands();
}
