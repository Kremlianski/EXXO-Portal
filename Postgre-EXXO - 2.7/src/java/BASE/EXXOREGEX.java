package BASE;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class EXXOREGEX {

    public static String replaceHref(String INPUT) {
        String REGEX = "href=\"\\S*galLoader";
        String REPLACE = "href=\"galLoader";
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(INPUT);
        INPUT = m.replaceAll(REPLACE);
        INPUT = INPUT.replaceAll("http://localhost:8080", "");
        return INPUT;
    }

    public static String replaceSrc(String INPUT) {
        String REGEX = "src=\"\\S*galLoader";
        String REPLACE = "src=\"galLoader";
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(INPUT);
        INPUT = m.replaceAll(REPLACE);
        INPUT = INPUT.replaceAll("http://localhost:8080", "");
        return INPUT;
    }
}
