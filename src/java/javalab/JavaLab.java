/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javalab;
import db.DatabaseConnector;
import db.DatabaseManager;
import java.sql.SQLException;
        
public class JavaLab {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        DatabaseConnector dbConnector = new DatabaseConnector();
        DatabaseManager dbManager = new DatabaseManager(dbConnector.getConnection());
        System.out.println("Hello!");
        dbConnector.closeConnection();
    }
    
}
