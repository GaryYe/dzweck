package sepm.ss2017.e1625772;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {

    public static void main(String[] args) {
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/boxes", "sa", "");
        } catch (Exception e) {
            return;
        }
    }
}
