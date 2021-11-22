package com.company.DBConnect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static java.sql.DriverManager.getConnection;

public class DbHandler extends ConnectConfig{
    private Connection dbConnection;
    public String dbName1 = "DB1";
    public String dbName2 = "DB2";

    private Connection getDbConnection(String dbName) throws Exception {
        String connectionString = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;
        dbConnection = getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }

    /* пример SELECT id,productname,price FROM "oldpricelist"; */
    public ResultSet getInformationTable(String sql,String dbName){
        try {
            dbConnection = getDbConnection(dbName);
            Statement statement = dbConnection.createStatement();
            // выбираем данные с БД
            return statement.executeQuery(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /* пример INSERT INTO \"oldpricelist\"(productname,price) VALUES('Черника',130); */
    public void setInformationTable(String sql,String dbName){
        try {
            dbConnection = getDbConnection(dbName);
            Statement statement = dbConnection.createStatement();
            // добавлем данные в БД
            statement.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /* Пример DELETE FROM "oldpricelist" WHERE id = 1; */
    public void deleteInformationTable(String sql,String dbName){
        try {
            dbConnection = getDbConnection(dbName);
            Statement statement = dbConnection.createStatement();
            // выполняем запрос delete SQL
            statement.execute(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
