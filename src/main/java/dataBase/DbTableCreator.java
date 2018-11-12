package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DbTableCreator {
    public static void main(String[] args) {
        Connection c = null;
        Statement stmt = null;
        String sql = "";
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres",
                    "postgres",
                    "root");
            stmt = c.createStatement();
            sql = "CREATE SCHEMA IF NOT EXISTS  RESOURCE_CIRCLE ";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS RESOURCE_CIRCLE.RESOURCE "
                    + " ( "
                    + " ID                SERIAL PRIMARY KEY     NOT NULL, "
                    + " NAME              VARCHAR(32)            NOT NULL"
                    + " ) ";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS RESOURCE_CIRCLE.CONTRIBUTION "
                    + " ( "
                    + " ID                      SERIAL PRIMARY KEY     NOT NULL, "
                    + " RESOURCE_ID                 INT                    NOT NULL, "
                    + " NAME                        VARCHAR(120)           NOT NULL, "
                    + " FACTOR                      INT           NOT NULL  "
                    + " ) ";
            stmt.executeUpdate(sql);

            sql = "ALTER TABLE RESOURCE_CIRCLE.CONTRIBUTION "
                    + " ADD CONSTRAINT FK_RESOURCE "
                    + " FOREIGN KEY (RESOURCE_ID ) REFERENCES RESOURCE_CIRCLE.RESOURCE (ID) MATCH FULL "
                    + " ";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS RESOURCE_CIRCLE.EVENT "
                    + " ( "
                    + " ID                SERIAL PRIMARY KEY     NOT NULL, "
                    + " CONTRIBUTION_ID              INT             NOT NULL ,"
                    + " EVENT_DATE                   DATE             NOT NULL,"
                    + " COMMENT                   VARCHAR(250)          "
                    + " ) ";
            stmt.executeUpdate(sql);

            sql = "ALTER TABLE RESOURCE_CIRCLE.EVENT "
                    + " ADD CONSTRAINT FK_CONTRIBUTION "
                    + " FOREIGN KEY (CONTRIBUTION_ID ) REFERENCES RESOURCE_CIRCLE.CONTRIBUTION (ID) MATCH FULL "
                    + " ";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS RESOURCE_CIRCLE.REGULAR_EVENT "
                    + " ( "
                    + " ID                SERIAL PRIMARY KEY     NOT NULL, "
                    + " CONTRIBUTION_ID              INT             NOT NULL ,"
                    + " EVENT_PERIOD                 VARCHAR(30)      NOT NULL,"
                    + " FINISH_DATE                  VARCHAR(30)          "
                    + " ) ";
            stmt.executeUpdate(sql);

            sql = "ALTER TABLE RESOURCE_CIRCLE.REGULAR_EVENT "
                    + " ADD CONSTRAINT FK_CONTRIBUTION "
                    + " FOREIGN KEY (CONTRIBUTION_ID ) REFERENCES RESOURCE_CIRCLE.CONTRIBUTION (ID) MATCH FULL "
                    + " ";
            stmt.executeUpdate(sql);


            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+ e.getMessage());
            System.exit(0);
        }
        System.out.println("Tables are created successfully");
    }
}
