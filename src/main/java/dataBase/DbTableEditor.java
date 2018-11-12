package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DbTableEditor {
    public static void main(String[] args) {
        Connection c = null;
        Statement stmt = null;
        String sql = "";

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","root");
            stmt = c.createStatement();

            sql = "ALTER TABLE resource_circle.regular_event " +
                    "DROP COLUMN FINISH_DATE ";
            stmt.executeUpdate(sql);
            sql = "ALTER TABLE resource_circle.regular_event " +
                    "ADD FINISH_DATE DATE ";
            stmt.executeUpdate(sql);

            stmt.close();
            c.close();

        }catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+ e.getMessage());
            System.exit(0);
        }
        System.out.println("Tables are created successfully");
    }
}
