package Application.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MarketingDepartment implements BaseUser{
    private static final Set<String> commands = new HashSet<>(Arrays.asList(new String[]{
            "salesOfBrand",
            "customerLookup",
            "salesOfModel",
            "brandsAnalytics",
            "vehicleLookupDealers",
            "salesOfCustomer",
            "registerCustomer",
            "salesOfBrandsTimerange",
            "salesOfBrandsDollarAmount"
    }));


    @Override
    public Set<String> getCommands() {
        return commands;
    }
}
