package com.company.DBConnect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.company.DBConnect.ConnectConfig.DB_HOST;
import static com.company.DBConnect.ConnectConfig.DB_PORT;
import static com.company.DBConnect.ConnectConfig.DB_USER;
import static com.company.DBConnect.ConnectConfig.DB_PASS;

import static java.sql.DriverManager.getConnection;

public class DbHandler {
    public Connection dbConnection1;
    public Connection dbConnection2;
    public final String dbName1 = "DB1";
    public final String dbName2 = "DB2";

    private Connection getDbConnection(String dbName) throws Exception {
        String connectionString = "jdbc:postgresql://" + DB_HOST + ":" + DB_PORT + "/" + dbName;
        return getConnection(connectionString, DB_USER, DB_PASS);
    }

    public DbHandler() {
        try {
            dbConnection1 = getDbConnection(dbName1);
            dbConnection2 = getDbConnection(dbName2);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /* пример SELECT id,productname,price FROM "oldpricelist"; */
    public ResultSet getInformationTable(String sql,Connection dbConnection){
        try {
            Statement statement = dbConnection.createStatement();
            // выбираем данные с БД
            return statement.executeQuery(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /* пример INSERT INTO \"oldpricelist\"(productname,price) VALUES('Черника',130); */
    public void setInformationTable(String sql,Connection dbConnection){
        try {
            Statement statement = dbConnection.createStatement();
            // добавлем данные в БД
            statement.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /* Пример DELETE FROM "oldpricelist" WHERE id = 1; */
    public void deleteInformationTable(String sql,Connection dbConnection){
        try {
            Statement statement = dbConnection.createStatement();
            // выполняем запрос delete SQL
            statement.execute(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
