package sepm.ss2017.e1625772;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {

    public static void main(String[] args) {
        // final Logger logger = LoggerFactory.getLogger(Main.class);
        // logger.info("This is my {}", "test!");
        System.out.println("YEA");
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/boxes", "sa", "");
        } catch (Exception e) {
            return;
        }
    }
}
