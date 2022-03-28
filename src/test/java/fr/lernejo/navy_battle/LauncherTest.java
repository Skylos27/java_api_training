package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Assertions;

class LauncherTest {
    @org.junit.jupiter.api.Test
    void launcherArgsFail() {

        String[] args = {};
        Assertions.assertDoesNotThrow(() -> Launcher.main(args));

        String[] args2 = {"toto"};
        Assertions.assertDoesNotThrow(() -> Launcher.main(args2));

        String[] args3 = {"525", "toto"};
        Assertions.assertDoesNotThrow(() -> Launcher.main(args3));

    }
}