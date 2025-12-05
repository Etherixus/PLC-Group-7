package testers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import projectFiles.Jott;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Stream;



public class JottExecutionTester {

    @ParameterizedTest
    @MethodSource("testCases")
    public void testExecutionOutput(String inputFile, String expectedOutput) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            Jott.main(new String[]{"phase3testcases/"+inputFile});
            String actualOutput = normalizeOutput(output.toString().trim());
            expectedOutput = normalizeOutput(expectedOutput.trim());

            assertEquals(expectedOutput, actualOutput);
        } finally {
            System.setOut(oldOut);
        }

    }

    private static Stream<Arguments> testCases() {
        return Stream.of(
                Arguments.of("test.jott", "Hello World\n5"),
                Arguments.of("providedExample1.jott", "5\nfoo bar"),
                Arguments.of("largerValid.jott", "5\n" +
                        "Hello World\n" +
                        "foo\n" +
                        "4\n" +
                        "Hello World\n" +
                        "foo\n" +
                        "3\n" +
                        "Hello World\n" +
                        "foo\n" +
                        "2\n" +
                        "Hello World\n" +
                        "foo\n" +
                        "1\n" +
                        "bar\n" +
                        "Hello World"),
                Arguments.of("reallyLong.jott", "5\n" +
                        "4\n" +
                        "3\n" +
                        "2\n" +
                        "1\n" +
                        "a1a1\n" +
                        "3\n" +
                        "4\n" +
                        "ran foo\n" +
                        "ran foo\n" +
                        "ran foo"),
                Arguments.of("validLoop.jott", "1\n2\n3\n4\n5\n6\n7\n8\n9\n10"),
                Arguments.of("ifStmtReturns.jott", "5"),
                Arguments.of("multiParam.jott", "10\n10\n10\n10\n10\n10\n10\n10\n10\n10")
        );
    }

    public static String normalizeOutput(String output) {
        if (output == null) {
            return null;
        }
        return output.replace("\r\n", "\n").replace("\r", "\n").trim();
    }

}