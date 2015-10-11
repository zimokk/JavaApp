
package db;
import subjectArea.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseManager {
    protected Connection dbConnection;
    protected Statement dbStatement;
    
    public DatabaseManager(Connection connection) throws SQLException{
        this.dbConnection = connection;
        this.dbStatement = dbConnection.createStatement();
        initializeTables();
    }
    
    public ResultSet getSouvenirs() throws SQLException{
        return query("SELECT * FROM Souvenirs");
    }
    
    public ResultSet getManufacturers() throws SQLException{
        return query("SELECT * FROM Manufacturers");
    }
     
    public void insertManufacturer(Manufacturer manufacturer) throws SQLException{
        String queryStr = "INSERT INTO Manufacturers (title, country) VALUES ('" + manufacturer.getTitle() + "', '" + manufacturer.getCountry() + "');";
        update(queryStr);
    }
    
    public void insertSouvenir(Souvenir souvenir) throws SQLException{
        String queryStr = "INSERT INTO Souvenirs (title, manufacturer_id, price, date) VALUES ('" + souvenir.getTitle() + "', '" + souvenir.getManufacturerId() + "', '" + souvenir.getPrice() + "', '" + souvenir.getIssueDate() + "');";
        update(queryStr);
    }
    
    public void deleteManufacturer(String manufacturerTitle) throws SQLException{
        String queryStr = "DELETE FROM Manufacturers WHERE title = '" + manufacturerTitle + "'";
        update(queryStr);
    }
    
    public ResultSet getSouvenirsByManufacturers(String manufacturerTitle) throws SQLException{
        String queryStr = "SELECT * FROM Souvenirs INNER JOIN Manufacturers ON Souvenirs.manufacturer_id = Manufacturers.id WHERE Manufacturers.title = '" + manufacturerTitle + "'";
        return query(queryStr);
    }
    
    public ResultSet getSouvenirsByCountry(String country) throws SQLException{ 
        String queryStr = "SELECT * FROM Souvenirs INNER JOIN Manufacturers ON Souvenirs.manufacturer_id = Manufacturers.id WHERE Manufacturers.country = '" + country + "'"; 
        return query(queryStr); 
    } 

    public ResultSet getManufacturersByTitleYear (String title, Date dateMin, Date dateMax) throws SQLException{ 
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String queryStr = "SELECT DISTINCT * FROM Manufacturers INNER JOIN Souvenirs ON Manufacturers.id = Souvenirs.manufacturer_id WHERE Souvenirs.title = '" + title + " ' AND Souvenirs.date < ' "+ formatter.format(dateMax) +"' AND Souvenirs.date > '"+formatter.format(dateMin)+"'"; 
        return query(queryStr); 
    } 

    public ResultSet getCheapestManufacturers(double priceBound) throws SQLException{ 
        String queryStr = "SELECT DISTINCT * FROM Manufacturers INNER JOIN Souvenirs ON Manufacturers.id = Souvenirs.manufacturer_id WHERE Souvenirs.price < '" + priceBound + "'"; 
        return query(queryStr); 
}
    
    private void initializeTables() throws SQLException{
        ResultSet souvenirsTable = query("show tables like 'Souvenirs'");
        if (!souvenirsTable.next()){
            update("CREATE TABLE IF NOT EXISTS Manufacturers (id INT AUTO_INCREMENT NOT NULL PRIMARY KEY, title VARCHAR(50), country VARCHAR(50));");
            update("CREATE INDEX manufacturers_titles ON Manufacturers (title);");
            update("CREATE INDEX manufacturers_countries ON Manufacturers (country);");
            update("CREATE TABLE IF NOT EXISTS Souvenirs (id INT AUTO_INCREMENT NOT NULL, title VARCHAR(50), manufacturer_id INT(5), price DECIMAL(10, 2), date DATE, PRIMARY KEY(id), FOREIGN KEY (manufacturer_id) REFERENCES Manufacturers(id) ON DELETE CASCADE);");
            update("CREATE INDEX souvenirs_titles ON Souvenirs (title);");
            update("CREATE INDEX souvenirs_prices ON Souvenirs (price);");
        }
    }
    
    private ResultSet query(String query) throws SQLException{
        return dbStatement.executeQuery(query); 
    }
    
    private int update(String query) throws SQLException{
        return dbStatement.executeUpdate(query); 
    }
}
