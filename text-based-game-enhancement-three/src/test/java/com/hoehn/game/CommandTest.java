package com.hoehn.game;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandTest {


    @ParameterizedTest
    @CsvSource({
            "1, src/main/resources/space-theme.json",
            "2, src/main/resources/medieval-theme.json",
            "3, src/main/resources/cyberpunk-theme.json"
    })
    void testCases(String input, String expectedFilepath) {
        Scanner scanner = new Scanner(input);

        String filepath = Command.chooseTheme(scanner);

        assertEquals(expectedFilepath, filepath);
    }
}
