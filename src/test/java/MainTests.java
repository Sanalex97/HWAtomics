import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class MainTests {

    @ParameterizedTest
    @MethodSource("sourcePalindrome")
    public void testCheckingForPalindrome(String text, boolean bool) {
        assertThat(Main.checkingForPalindrome(text), equalTo(bool));
    }

    @ParameterizedTest
    @MethodSource("sourceEquality")
    public void testCheckingForCharacterEquality(String text, boolean bool) {
        assertThat(Main.checkingForCharacterEquality(text), equalTo(bool));
    }

    @ParameterizedTest
    @MethodSource("sourceAscending")
    public void testCheckingForCharacterAscending(String text, boolean bool) {
        assertThat(Main.checkingForCharacterAscending(text), equalTo(bool));
    }

    static Stream<Arguments> sourcePalindrome() {
        return Stream.of(
                arguments("abcba", true),
                arguments("abba", true),
                arguments("abcab", false),
                arguments("aba", true)
        );
    }
    static Stream<Arguments> sourceEquality() {
        return Stream.of(
                arguments("aaaa", true),
                arguments("abbc", false),
                arguments("bbb", true)
        );
    }
    static Stream<Arguments> sourceAscending() {
        return Stream.of(
                arguments("abc", true),
                arguments("abba", false),
                arguments("aabb", true),
                arguments("bbc", true)
        );
    }
}
