package parser_lambda;

import lexica_lambda.LexicalAnalyzer;
import lexica_lambda.Token;
import lexica_lambda.TypeToken;

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

    public A a() {
        A res = new A("a");
        switch(token.typeToken()) {
            case MINUS -> {
                if (token.typeToken() != TypeToken.MINUS) {
                    throw new ParseException("No valid token: " + token.text());
                }
                String MINUS0 = token.text();
                res.addChild(token.text());
                nextToken();
                F f1 = f();
                res.addChild(f1);
                A a2 = a();
                res.addChild(a2);
            }
            case PLUS -> {
                if (token.typeToken() != TypeToken.PLUS) {
                    throw new ParseException("No valid token: " + token.text());
                }
                String PLUS0 = token.text();
                res.addChild(token.text());
                nextToken();
                F f1 = f();
                res.addChild(f1);
                A a2 = a();
                res.addChild(a2);
            }
            case END, CLOSE -> {
                res.addChild("eps");
                
            }
            default ->
                throw new ParseException("No valid token: " + token.text());
        }

        return res;
    }

    public S s() {
        S res = new S("s");
        switch(token.typeToken()) {
            case LAMBDA -> {
                if (token.typeToken() != TypeToken.LAMBDA) {
                    throw new ParseException("No valid token: " + token.text());
                }
                String LAMBDA0 = token.text();
                res.addChild(token.text());
                nextToken();
                W w1 = w();
                res.addChild(w1);
                if (token.typeToken() != TypeToken.SEMICOLON) {
                    throw new ParseException("No valid token: " + token.text());
                }
                String SEMICOLON2 = token.text();
                res.addChild(token.text());
                nextToken();
                E e3 = e();
                res.addChild(e3);
            }
            default ->
                throw new ParseException("No valid token: " + token.text());
        }

        return res;
    }

    public E e() {
        E res = new E("e");
        switch(token.typeToken()) {
            case LETTER, NUM, OPEN -> {
                F f0 = f();
                res.addChild(f0);
                A a1 = a();
                res.addChild(a1);
            }
            default ->
                throw new ParseException("No valid token: " + token.text());
        }

        return res;
    }

    public F f() {
        F res = new F("f");
        switch(token.typeToken()) {
            case LETTER, NUM, OPEN -> {
                N n0 = n();
                res.addChild(n0);
                M m1 = m();
                res.addChild(m1);
            }
            default ->
                throw new ParseException("No valid token: " + token.text());
        }

        return res;
    }

    public V v() {
        V res = new V("v");
        switch(token.typeToken()) {
            case COMMA -> {
                if (token.typeToken() != TypeToken.COMMA) {
                    throw new ParseException("No valid token: " + token.text());
                }
                String COMMA0 = token.text();
                res.addChild(token.text());
                nextToken();
                if (token.typeToken() != TypeToken.LETTER) {
                    throw new ParseException("No valid token: " + token.text());
                }
                String LETTER1 = token.text();
                res.addChild(token.text());
                nextToken();
                V v2 = v();
                res.addChild(v2);
            }
            case SEMICOLON -> {
                res.addChild("eps");
                
            }
            default ->
                throw new ParseException("No valid token: " + token.text());
        }

        return res;
    }

    public W w() {
        W res = new W("w");
        switch(token.typeToken()) {
            case LETTER -> {
                if (token.typeToken() != TypeToken.LETTER) {
                    throw new ParseException("No valid token: " + token.text());
                }
                String LETTER0 = token.text();
                res.addChild(token.text());
                nextToken();
                V v1 = v();
                res.addChild(v1);
            }
            case SEMICOLON -> {
                res.addChild("eps");
                
            }
            default ->
                throw new ParseException("No valid token: " + token.text());
        }

        return res;
    }

    public M m() {
        M res = new M("m");
        switch(token.typeToken()) {
            case DIV -> {
                if (token.typeToken() != TypeToken.DIV) {
                    throw new ParseException("No valid token: " + token.text());
                }
                String DIV0 = token.text();
                res.addChild(token.text());
                nextToken();
                N n1 = n();
                res.addChild(n1);
                M m2 = m();
                res.addChild(m2);
            }
            case MUL -> {
                if (token.typeToken() != TypeToken.MUL) {
                    throw new ParseException("No valid token: " + token.text());
                }
                String MUL0 = token.text();
                res.addChild(token.text());
                nextToken();
                N n1 = n();
                res.addChild(n1);
                M m2 = m();
                res.addChild(m2);
            }
            case END, CLOSE, MINUS, PLUS -> {
                res.addChild("eps");
                
            }
            default ->
                throw new ParseException("No valid token: " + token.text());
        }

        return res;
    }

    public N n() {
        N res = new N("n");
        switch(token.typeToken()) {
            case LETTER -> {
                if (token.typeToken() != TypeToken.LETTER) {
                    throw new ParseException("No valid token: " + token.text());
                }
                String LETTER0 = token.text();
                res.addChild(token.text());
                nextToken();
            }
            case NUM -> {
                if (token.typeToken() != TypeToken.NUM) {
                    throw new ParseException("No valid token: " + token.text());
                }
                String NUM0 = token.text();
                res.addChild(token.text());
                nextToken();
            }
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
            }
            default ->
                throw new ParseException("No valid token: " + token.text());
        }

        return res;
    }


    public static class A extends Tree {
        public A(String node) {
            super(node);
        }
    }

    public static class S extends Tree {
        public S(String node) {
            super(node);
        }
    }

    public static class E extends Tree {
        public E(String node) {
            super(node);
        }
    }

    public static class F extends Tree {
        public F(String node) {
            super(node);
        }
    }

    public static class V extends Tree {
        public V(String node) {
            super(node);
        }
    }

    public static class W extends Tree {
        public W(String node) {
            super(node);
        }
    }

    public static class M extends Tree {
        public M(String node) {
            super(node);
        }
    }

    public static class N extends Tree {
        public N(String node) {
            super(node);
        }
    }

}
