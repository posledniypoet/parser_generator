import lexica_lambda.LexicalAnalyzer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parser_lambda.Parser;
import parser_math.ParseException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParserLambdaMathTest {

    private static List<Arguments> correctExpression() {
        return Stream.of(
                        "-(123)*(123-123)",
                        "-3213*(73-41)+22",
                        "sin(-13)",
                        "sin 1",
                        "2 + 2 * 2",
                        "(1+2)*sin(-3*(7-4)+2)",
                        "2 / 2 + 2 * 2 - 4 * 4 + sin 1",
                        "(cos 2 + sin 2) / 41232 - 123 ",
                        "        123      ",
                        "      -228  + sin(34) ",
                        "-123 + -21312",
                        "-123/-123 + sin-123",
                        "sin cos cos 2",
                        "-------123",
                        "123 / 123 / 123 + 123"
                ).map(Arguments::of)
                .collect(Collectors.toList());
    }

    private static List<Arguments> errorExpressions() {
        return Stream.of(
                        "cos cos",
                        "sin sin",
                        "- - + 12",
                        "()",
                        "2 + + 2 * 2",
                        "//123",
                        "kek",
                        "* 213 * 123 ",
                        "        123   *   ",
                        "      -228 -  + sin(34) ",
                        "-123 + -21312 + sin cos",
                        "+-123/-123 + sin-123",
                        "( 1 + 2) sin",
                        " 123  ! 123 "
                ).map(Arguments::of)
                .collect(Collectors.toList());
    }

    private static record Entry(String expr, int val) {
    }

    private static List<Arguments> checkValueExpression() {
        return Stream.of(
                        new Entry("4 / 2 / 2", 1),
                        new Entry("64 / 2 / 2 / 2 / 2 / 2 / 2", 1),
                        new Entry("64 / 2 / 2 / 2 / 2 / 2  - 2", 0),
                        new Entry("64 / 2  * 2", 64),
                        new Entry("2 + 2 * 2", 6),
                        new Entry("-(2 + 2) * 2 - 8", -16),
                        new Entry("9 - 1 - 2 - 3 ", 3),
                        new Entry("1 + 2 + 3 * 3 - (12 + 12) * 3 + 72", 12),
                        new Entry("1 * 2 * 3 * (4 + 1 - 1) - 21", 3),
                        new Entry("- 2 - 2 - 2", -6),
                        new Entry("-----2 + 2 + 1", 1),
                        new Entry("1", 1),
                        new Entry("25 - 4 * 8 / 2 - 4 + 8 / 2", 9),
                        new Entry("12 - 2 * (5 - 4 / 2)", 6),
                        new Entry("300 - 29 * 4", 184),
                        new Entry("80 - (46 - 14)", 48),
                        new Entry("9 / 3 * 2", 6),
                        new Entry("15 - (7 + 3) / 2", 10),
                        new Entry("3 * (1 + 3) + 8", 20),
                        new Entry("[4,3]",4),
                        new Entry("[[4,3],3]",4)
                ).map(Arguments::of)
                .collect(Collectors.toList());
    }
    private static List<Arguments> correctLambdaExpression() {
        return Stream.of(
                "lambda x:x+1",
                "lambda x,y: x/y + z",
                "lambda x,y, z: 1*6 + (x *7 +3)",
                "lambda:1 +1",
                "lambda x,y,z,w: a + b + c"
                ).map(Arguments::of)
                .collect(Collectors.toList());
    }

    private static List<Arguments> errorLambdaExpressions() {
        return Stream.of(
                        "lambda",
                        "lambda lambda : 1+ 1",
                        "lambda x,y (x+y)"
                ).map(Arguments::of)
                .collect(Collectors.toList());
    }
    @ParameterizedTest(name = "Test {0}")
    @MethodSource("correctExpression")
    public void testCorrectExpression(String expr) {
        Assertions.assertDoesNotThrow(() -> new parser_math.Parser(new lexica_math.LexicalAnalyzer(expr)).expr());
    }

    @ParameterizedTest(name = "Test {0}")
    @MethodSource("errorExpressions")
    public void testErrorExpressions(String expr) {
        Assertions.assertThrows(ParseException.class,
                () -> new parser_math.Parser(new lexica_math.LexicalAnalyzer(expr)).expr());
    }

    @ParameterizedTest(name = "Test{0}")
    @MethodSource("checkValueExpression")
    public void checkValueExpressionTest(Entry entry) {
        Assertions.assertEquals(entry.val, new parser_math.Parser(new lexica_math.LexicalAnalyzer(entry.expr)).expr().val);
    }

    @ParameterizedTest(name = "Test {0}")
    @MethodSource("correctLambdaExpression")
    public void testCorrectLambdaExpression(String expr) {
        Assertions.assertDoesNotThrow(() -> new Parser(new LexicalAnalyzer(expr)).s());
    }

    @ParameterizedTest(name = "Test {0}")
    @MethodSource("errorLambdaExpressions")
    public void testErrorLambdaExpressions(String expr) {
        Assertions.assertThrows(parser_lambda.ParseException.class,
                () -> new Parser(new LexicalAnalyzer(expr)).s());
    }

}
