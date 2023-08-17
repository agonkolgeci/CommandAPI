package dev.jorel.commandapi.test.arguments.parseexceptions;

import dev.jorel.commandapi.arguments.parseexceptions.InitialParseExceptionContext;
import dev.jorel.commandapi.arguments.parseexceptions.InitialParseExceptionNumberArgument;
import dev.jorel.commandapi.test.TestBase;
import org.bukkit.command.CommandSender;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * A helper class for verifying {@link InitialParseExceptionContext} objects generated by
 * {@link InitialParseExceptionNumberArgument}s.
 * @param <N> The type of number returned by the Argument being tested.
 */
public class InitialParseExceptionNumberArgumentVerifier<N extends Number>
        extends InitialParseExceptionContextVerifier<N, InitialParseExceptionNumberArgument.ExceptionInformation<N>> {

    /**
     * Creates a new {@link InitialParseExceptionNumberArgumentVerifier}.
     *
     * @param testBase The {@link TestBase} object running the test.
     */
    public InitialParseExceptionNumberArgumentVerifier(TestBase testBase) {
        super(testBase);
    }

    /**
     * Asserts that the given {@code actual} context has all the given expected attributes
     *
     * @param exceptionMessage The message of the initial parse exception.
     * @param readerString     The command string being parsed.
     * @param cursorStart      The place where the StringReader started parsing the ArgumentType.
     * @param readerCursor     The place where the StringReader ended when the exception was handled.
     * @param exceptionType    The type of exception that occurred.
     * @param rawInput         The string read for this ArgumentType.
     * @param input            The number input for this ArgumentType.
     * @param minimum          The minimum bound set for this ArgumentType.
     * @param maximum          The maximum bound set for this ArgumentType.
     * @param actual           The actual {@link InitialParseExceptionContext} generated.
     */
    public void assertCorrectContext(
            String exceptionMessage, String readerString, int cursorStart, int readerCursor,
            InitialParseExceptionNumberArgument.ExceptionInformation.Exceptions exceptionType,
            String rawInput, N input, N minimum, N maximum,
            InitialParseExceptionContext<InitialParseExceptionNumberArgument.ExceptionInformation<N>> actual
    ) {
        super.assertCorrectContext(exceptionMessage, readerString, cursorStart, readerCursor, actual);

        InitialParseExceptionNumberArgument.ExceptionInformation<N> exceptionInformation = actual.exceptionInformation();
        assertEquals(exceptionType, exceptionInformation.type());
        assertEquals(rawInput, exceptionInformation.rawInput());
        assertEquals(input, exceptionInformation.input());
        assertEquals(minimum, exceptionInformation.minimum());
        assertEquals(maximum, exceptionInformation.maximum());
    }

    /**
     * Tests a case where the {@code exceptionType} is {@code EXPECTED_NUMBER}, when no characters for a number were
     * found. In this case, the StringReader's cursor should have started and ended in the same place, the {@code rawInput}
     * should be empty, and the {@code input} number should default to 0.
     *
     * @param sender           The sender for the command.
     * @param command          The command to execute.
     * @param exceptionMessage The message of the exception that should be thrown.
     * @param cursorStart      The index where the StringReader's cursor should start and end.
     * @param argument         The {@link InitialParseExceptionNumberArgument} that defines the minimum and maximum bounds.
     */
    public void testExpectedNumberCase(
            CommandSender sender, String command, String exceptionMessage,
            int cursorStart, InitialParseExceptionNumberArgument<N> argument
    ) {
        verifyGeneratedContext(
                sender, command, exceptionMessage,
                context -> assertCorrectContext(
                        exceptionMessage, command, cursorStart, cursorStart,
                        InitialParseExceptionNumberArgument.ExceptionInformation.Exceptions.EXPECTED_NUMBER,
                        "", argument.getZero(), argument.getMinimum(), argument.getMaximum(),
                        context
                )
        );
    }

    /**
     * Tests a case where the {@code exceptionType} is {@code INVALID_NUMBER}, when the given characters could not be
     * parsed as a number. In this case, the StringReader's cursor should end {@code rawInput.length()} characters after
     * where it started, and the {@code input} number should default to 0.
     *
     * @param sender           The sender for the command.
     * @param command          The command to execute.
     * @param exceptionMessage The message of the exception that should be thrown.
     * @param cursorStart      The index where the StringReader's cursor should start and end.
     * @param rawInput         The raw String input for the Argument.
     * @param argument         The {@link InitialParseExceptionNumberArgument} that defines the minimum and maximum bounds.
     */
    public void testInvalidNumberCase(
            CommandSender sender, String command, String exceptionMessage,
            int cursorStart, String rawInput, InitialParseExceptionNumberArgument<N> argument
    ) {
        verifyGeneratedContext(
                sender, command, exceptionMessage,
                context -> assertCorrectContext(
                        exceptionMessage, command, cursorStart, cursorStart + rawInput.length(),
                        InitialParseExceptionNumberArgument.ExceptionInformation.Exceptions.INVALID_NUMBER,
                        rawInput, argument.getZero(), argument.getMinimum(), argument.getMaximum(),
                        context
                )
        );
    }

    /**
     * Tests a case where the {@code exceptionType} is {@code NUMBER_TOO_LOW}, when the given number was below the defined
     * bound for the argument. In this case, the StringReader's cursor should end {@code rawInput.length()} characters after
     * where it started.
     *
     * @param sender           The sender for the command.
     * @param command          The command to execute.
     * @param exceptionMessage The message of the exception that should be thrown.
     * @param cursorStart      The index where the StringReader's cursor should start and end.
     * @param rawInput         The raw String input for the Argument.
     * @param input            The number input for the Argument.
     * @param argument         The {@link InitialParseExceptionNumberArgument} that defines the minimum and maximum bounds.
     */
    public void testNumberTooLowCase(
            CommandSender sender, String command, String exceptionMessage,
            int cursorStart, String rawInput, N input, InitialParseExceptionNumberArgument<N> argument
    ) {
        verifyGeneratedContext(
                sender, command, exceptionMessage,
                context -> assertCorrectContext(
                        exceptionMessage, command, cursorStart, cursorStart + rawInput.length(),
                        InitialParseExceptionNumberArgument.ExceptionInformation.Exceptions.NUMBER_TOO_LOW,
                        rawInput, input, argument.getMinimum(), argument.getMaximum(),
                        context
                )
        );
    }

    /**
     * Tests a case where the {@code exceptionType} is {@code NUMBER_TOO_High}, when the given number was above the defined
     * bound for the argument. In this case, the StringReader's cursor should end {@code rawInput.length()} characters after
     * where it started.
     *
     * @param sender           The sender for the command.
     * @param command          The command to execute.
     * @param exceptionMessage The message of the exception that should be thrown.
     * @param cursorStart      The index where the StringReader's cursor should start and end.
     * @param rawInput         The raw String input for the Argument.
     * @param input            The number input for the Argument.
     * @param argument         The {@link InitialParseExceptionNumberArgument} that defines the minimum and maximum bounds.
     */
    public void testNumberTooHighCase(
            CommandSender sender, String command, String exceptionMessage,
            int cursorStart, String rawInput, N input, InitialParseExceptionNumberArgument<N> argument
    ) {
        verifyGeneratedContext(
                sender, command, exceptionMessage,
                context -> assertCorrectContext(
                        exceptionMessage, command, cursorStart, cursorStart + rawInput.length(),
                        InitialParseExceptionNumberArgument.ExceptionInformation.Exceptions.NUMBER_TOO_HIGH,
                        rawInput, input, argument.getMinimum(), argument.getMaximum(),
                        context
                )
        );
    }
}
