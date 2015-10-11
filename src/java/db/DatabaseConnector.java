/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    protected String driverName;
    protected Connection dbConnection;
    
    public DatabaseConnector() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
        this.driverName = "com.mysql.jdbc.Driver";
        Class.forName(this.driverName).newInstance();
        this.dbConnection = DriverManager.getConnection ("jdbc:mysql://localhost/JavaLab1?user=root&password=");
    }
    
    public Connection getConnection(){
        return this.dbConnection;
    }
    
    public void closeConnection() throws SQLException{
        this.dbConnection.close();
    }
}
