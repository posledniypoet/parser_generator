package parser_parentheses;

import lexica_parentheses.LexicalAnalyzer;
import lexica_parentheses.Token;
import lexica_parentheses.TypeToken;

public class Parser {
    private final LexicalAnalyzer tokens;
    private Token token;

    private void nextToken() {
        tokens.nextToken();
        token = tokens.getToken();
    }

    public Parser(LexicalAnalyzer tokens) {
        this.tokens = tokens;
        nextToken();
    }

    public E e() {
        E res = new E("e");
        switch(token.typeToken()) {
            case OPEN -> {
                if (token.typeToken() != TypeToken.OPEN) {
                    throw new ParseException("No valid token: " + token.text());
                }
                String OPEN0 = token.text();
                res.addChild(token.text());
                nextToken();
                E e1 = e();
                res.addChild(e1);
                if (token.typeToken() != TypeToken.CLOSE) {
                    throw new ParseException("No valid token: " + token.text());
                }
                String CLOSE2 = token.text();
                res.addChild(token.text());
                nextToken();
                E e3 = e();
                res.addChild(e3);
            }
            case END, CLOSE -> {
                res.addChild("eps");
                
            }
            default ->
                throw new ParseException("No valid token: " + token.text());
        }

        return res;
    }


    public static class E extends Tree {
        public E(String node) {
            super(node);
        }
    }

}
