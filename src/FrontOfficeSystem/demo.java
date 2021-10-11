package FrontOfficeSystem;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class demo {
    private static final String ACCOUNT_REGEX = "";

    public boolean validDate (String regex) {

        Pattern pattern = Pattern.compile(ACCOUNT_REGEX);
        Matcher matcher = pattern.matcher(regex);
        if (matcher.matches()){
            return true;
        }
        return false;
    }
}
