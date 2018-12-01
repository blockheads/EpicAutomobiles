package Application.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Customer implements BaseUser {
    private static final Set<String> commands = new HashSet<>(Arrays.asList(new String[]{
            "vehicleLookupDealers"
    }));

    @Override
    public Set<String> getCommands() {
        return commands;
    }
}
