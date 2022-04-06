package lexica_lambda;

import java.util.regex.Pattern;

public enum TypeToken {
    END("\\$"),
    POW("\\*\\*"),
    PLUS("\\+"),
    MINUS("-"),
    MUL("\\*"),
    DIV("/"),
    NUM("[0-9]+"),
    OPEN("\\("),
    CLOSE("\\)"),
    LAMBDA("lambda"),
    SEMICOLON("\\:"),
    COMMA("\\,"),
    LETTER("[a-z][a-z|A-Z|0-9|_]*");
    private final Pattern pattern;

    TypeToken (String regexp) {
        this.pattern = Pattern.compile(regexp);
    }

    public boolean match(String text) {
        return pattern.matcher(text).matches();
    }
}
