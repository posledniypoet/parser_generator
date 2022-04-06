package lexica_parentheses;

import java.util.regex.Pattern;

public enum TypeToken {
    END("\\$"),
    OPEN("\\("),
    CLOSE("\\)");
    private final Pattern pattern;

    TypeToken (String regexp) {
        this.pattern = Pattern.compile(regexp);
    }

    public boolean match(String text) {
        return pattern.matcher(text).matches();
    }
}
