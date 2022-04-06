package lexica_math;

import java.util.regex.Pattern;

public enum TypeToken {
    END("\\$"),
    POW("\\*\\*"),
    PLUS("\\+"),
    MINUS("-"),
    MUL("\\*"),
    DIV("/"),
    SIN("sin"),
    COS("cos"),
    NUM("[0-9]+"),
    OPEN("\\("),
    CLOSE("\\)"),
    OPENBIN("\\["),
    CLOSEBIN("\\]"),
    COMMA(",");
    private final Pattern pattern;

    TypeToken (String regexp) {
        this.pattern = Pattern.compile(regexp);
    }

    public boolean match(String text) {
        return pattern.matcher(text).matches();
    }
}
