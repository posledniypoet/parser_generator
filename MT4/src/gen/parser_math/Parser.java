package parser_math;

import lexica_math.LexicalAnalyzer;
import lexica_math.Token;
import lexica_math.TypeToken;

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

    public TermS termS(int acc) {
        TermS res = new TermS("termS");
        switch(token.typeToken()) {
            case MUL -> {
                if (token.typeToken() != TypeToken.MUL) {
                    throw new ParseException("No valid token: " + token.text());
                }
                String MUL0 = token.text();
                res.addChild(token.text());
                nextToken();
                Power power1 = power();
                res.addChild(power1);
                res.val = acc * power1.val;
                TermS termS2 = termS(res.val);
                res.addChild(termS2);
                res.val = termS2.val;
            }
            case DIV -> {
                if (token.typeToken() != TypeToken.DIV) {
                    throw new ParseException("No valid token: " + token.text());
                }
                String DIV0 = token.text();
                res.addChild(token.text());
                nextToken();
                Power power1 = power();
                res.addChild(power1);
                res.val = acc / power1.val;
                TermS termS2 = termS(res.val);
                res.addChild(termS2);
                res.val = termS2.val;
            }
            case COMMA, CLOSEBIN, END, CLOSE, PLUS, MINUS -> {
                res.addChild("eps");
                res.val = acc;
            }
            default ->
                throw new ParseException("No valid token: " + token.text());
        }

        return res;
    }

    public ExprS exprS(int acc) {
        ExprS res = new ExprS("exprS");
        switch(token.typeToken()) {
            case PLUS -> {
                if (token.typeToken() != TypeToken.PLUS) {
                    throw new ParseException("No valid token: " + token.text());
                }
                String PLUS0 = token.text();
                res.addChild(token.text());
                nextToken();
                Term term1 = term();
                res.addChild(term1);
                res.val = acc + term1.val;
                ExprS exprS2 = exprS(res.val);
                res.addChild(exprS2);
                res.val = exprS2.val;
            }
            case MINUS -> {
                if (token.typeToken() != TypeToken.MINUS) {
                    throw new ParseException("No valid token: " + token.text());
                }
                String MINUS0 = token.text();
                res.addChild(token.text());
                nextToken();
                Term term1 = term();
                res.addChild(term1);
                res.val = acc - term1.val;
                ExprS exprS2 = exprS(res.val);
                res.addChild(exprS2);
                res.val = exprS2.val;
            }
            case COMMA, CLOSEBIN, END, CLOSE -> {
                res.addChild("eps");
                res.val = acc;
            }
            default ->
                throw new ParseException("No valid token: " + token.text());
        }

        return res;
    }

    public Term term() {
        Term res = new Term("term");
        switch(token.typeToken()) {
            case COS, NUM, SIN, OPENBIN, OPEN, MINUS -> {
                Power power0 = power();
                res.addChild(power0);
                TermS termS1 = termS(power0.val);
                res.addChild(termS1);
                res.val = termS1.val;
            }
            default ->
                throw new ParseException("No valid token: " + token.text());
        }

        return res;
    }

    public Expr expr() {
        Expr res = new Expr("expr");
        switch(token.typeToken()) {
            case COS, NUM, SIN, OPENBIN, OPEN, MINUS -> {
                Term term0 = term();
                res.addChild(term0);
                ExprS exprS1 = exprS(term0.val);
                res.addChild(exprS1);
                res.val = exprS1.val;
            }
            default ->
                throw new ParseException("No valid token: " + token.text());
        }

        return res;
    }

    public Power power() {
        Power res = new Power("power");
        switch(token.typeToken()) {
            case COS, NUM, SIN, OPENBIN, OPEN, MINUS -> {
                Factor factor0 = factor();
                res.addChild(factor0);
                PowerS powerS1 = powerS(factor0.val);
                res.addChild(powerS1);
                res.val = powerS1.val;
            }
            default ->
                throw new ParseException("No valid token: " + token.text());
        }

        return res;
    }

    public Factor factor() {
        Factor res = new Factor("factor");
        switch(token.typeToken()) {
            case SIN -> {
                if (token.typeToken() != TypeToken.SIN) {
                    throw new ParseException("No valid token: " + token.text());
                }
                String SIN0 = token.text();
                res.addChild(token.text());
                nextToken();
                Factor factor1 = factor();
                res.addChild(factor1);
                res.val = (int) Math.sin(factor1.val);
            }
            case COS -> {
                if (token.typeToken() != TypeToken.COS) {
                    throw new ParseException("No valid token: " + token.text());
                }
                String COS0 = token.text();
                res.addChild(token.text());
                nextToken();
                Factor factor1 = factor();
                res.addChild(factor1);
                res.val = (int) Math.cos(factor1.val);
            }
            case NUM -> {
                if (token.typeToken() != TypeToken.NUM) {
                    throw new ParseException("No valid token: " + token.text());
                }
                String NUM0 = token.text();
                res.addChild(token.text());
                nextToken();
                res.val = Integer.parseInt(NUM0);
            }
            case OPEN -> {
                if (token.typeToken() != TypeToken.OPEN) {
                    throw new ParseException("No valid token: " + token.text());
                }
                String OPEN0 = token.text();
                res.addChild(token.text());
                nextToken();
                Expr expr1 = expr();
                res.addChild(expr1);
                if (token.typeToken() != TypeToken.CLOSE) {
                    throw new ParseException("No valid token: " + token.text());
                }
                String CLOSE2 = token.text();
                res.addChild(token.text());
                nextToken();
                res.val = expr1.val;
            }
            case MINUS -> {
                if (token.typeToken() != TypeToken.MINUS) {
                    throw new ParseException("No valid token: " + token.text());
                }
                String MINUS0 = token.text();
                res.addChild(token.text());
                nextToken();
                Factor factor1 = factor();
                res.addChild(factor1);
                res.val = (-1) * factor1.val;
            }
            case OPENBIN -> {
                if (token.typeToken() != TypeToken.OPENBIN) {
                    throw new ParseException("No valid token: " + token.text());
                }
                String OPENBIN0 = token.text();
                res.addChild(token.text());
                nextToken();
                Expr expr1 = expr();
                res.addChild(expr1);
                if (token.typeToken() != TypeToken.COMMA) {
                    throw new ParseException("No valid token: " + token.text());
                }
                String COMMA2 = token.text();
                res.addChild(token.text());
                nextToken();
                Expr expr3 = expr();
                res.addChild(expr3);
                if (token.typeToken() != TypeToken.CLOSEBIN) {
                    throw new ParseException("No valid token: " + token.text());
                }
                String CLOSEBIN4 = token.text();
                res.addChild(token.text());
                nextToken();
                
int val1 = expr1.val;
int val2 = expr3.val;
int n = 1;
int k = 1;
int p = 1;
for(int i = 1; i <=val1; i++)
  n *= i;

for(int i = 1; i <= val2; i++)
  k *= i;

for(int i = 1; i <= val1 - val2; i++)
  p *= i;

res.val = n/(k*p);

            }
            default ->
                throw new ParseException("No valid token: " + token.text());
        }

        return res;
    }

    public PowerS powerS(int acc) {
        PowerS res = new PowerS("powerS");
        switch(token.typeToken()) {
            case POW -> {
                if (token.typeToken() != TypeToken.POW) {
                    throw new ParseException("No valid token: " + token.text());
                }
                String POW0 = token.text();
                res.addChild(token.text());
                nextToken();
                Power power1 = power();
                res.addChild(power1);
                res.val = (int) Math.pow(acc, power1.val);
            }
            case DIV, COMMA, MUL, CLOSEBIN, END, CLOSE, PLUS, MINUS -> {
                res.addChild("eps");
                res.val = acc;
            }
            default ->
                throw new ParseException("No valid token: " + token.text());
        }

        return res;
    }


    public static class TermS extends Tree {
        public int val;
        public TermS(String node) {
            super(node);
        }
    }

    public static class ExprS extends Tree {
        public int val;
        public ExprS(String node) {
            super(node);
        }
    }

    public static class Term extends Tree {
        public int val;
        public Term(String node) {
            super(node);
        }
    }

    public static class Expr extends Tree {
        public int val;
        public Expr(String node) {
            super(node);
        }
    }

    public static class Power extends Tree {
        public int val;
        public Power(String node) {
            super(node);
        }
    }

    public static class Factor extends Tree {
        public int val;
        public Factor(String node) {
            super(node);
        }
    }

    public static class PowerS extends Tree {
        public int val;
        public PowerS(String node) {
            super(node);
        }
    }

}
