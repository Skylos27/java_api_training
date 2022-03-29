package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Assertions;

class LauncherTest {
    @org.junit.jupiter.api.Test
    void launcherArgsOk() {
        String[] args = {"555"};
        Assertions.assertDoesNotThrow(() -> Launcher.main(args));
    }

    @org.junit.jupiter.api.Test
    void launcherArgsFails() {
        String[] args1 = {};
        Assertions.assertThrows(
                NullPointerException.class,
                () -> Launcher.main(args1), "Usage: Launcher [port]"
        );
    }


    @org.junit.jupiter.api.Test
    void launcherArgsFailsLetter() {
        String[] args1 = {"toto"};

        Assertions.assertThrows(
                NumberFormatException.class,
                () -> Launcher.main(args1)
        );}
    @org.junit.jupiter.api.Test
    void launcherArgsFailsManyArg() {
        String[] args1 = {"5555","http://localhost:5555"};

        Assertions.assertDoesNotThrow(() -> Launcher.main(args1));
    }
}
