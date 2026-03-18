package main.java.kindergarten;

import main.java.kindergarten.config.AppConfig;
import main.java.kindergarten.ui.ConsoleApplication;

public class Main {
    
    public static void main(String[] args) {
        AppConfig config = new AppConfig();
        ConsoleApplication app = config.createConsoleApplication();
        app.run();
    }
}
