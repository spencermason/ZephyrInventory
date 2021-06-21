/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zephyrinventorysystem;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import javafx.scene.control.Alert;
/**
 *
 * @author Spenc
 */
public class MoldAdapter {
    
    Connection connection;
    
    public MoldAdapter(Connection conn, Boolean reset) throws SQLException {
        connection = conn;
        if (reset) {
            Statement stmt = connection.createStatement();
            try {
                // Remove tables if database tables have been created.
            // This will throw an exception if the tables do not exist
            stmt.execute("DROP TABLE Mold");
                // then do finally
            } catch (SQLException ex) {
                // No need to report an error.
                // The table simply did not exist.
                // do finally to create it
            } finally {
                stmt.execute("CREATE TABLE Mold ("
                        + "Part VARCHAR(10), "
                        + "Pre VARCHAR(20), "
                        + "Preform VARCHAR(50), "
                        + "ColorName VARCHAR(50), "
                        + "ColorNum VARCHAR(50), "
                        + "LDR VARCHAR(20), "
                        + "PCR VARCHAR(20), "
                        + "PtPerGay VARCHAR(20), "
                        + "WtPerSkid VARCHAR(20), "
                        + "Machine VARCHAR(50), "
                        + "PCRGaylord VARCHAR(50), "
                        + "PCRSilo VARCHAR(20))");
            }
        }
    }
    
    public void add(String Part, String Pre, String Preform, String ColorName,String ColorNum, String LDR, String PCR, String PtPerGay, String WtPerSkid, String Machine, String PCRGaylord, String PCRSilo) throws SQLException{
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("INSERT INTO Mold (Part, Pre, Preform, ColorName, ColorNum, LDR, PCR, PtPerGay, WtPerSkid, Machine, PCRGaylord, PCRSilo) VALUES ("
                    + "'" + Part + "',"
                    + "'" + Pre + "',"
                    + "'" + Preform + "',"
                    + "'" + ColorName + "',"
                    + "'" + ColorNum + "',"
                    + "'" + LDR + "',"
                    + "'" + PCR + "',"
                    + "'" + PtPerGay + "',"
                    + "'" + WtPerSkid + "',"
                    + "'" + Machine + "',"
                    + "'" + PCRGaylord + "',"
                    + "'" + PCRSilo + "')");
    }
    
    public void remove(String partNum) throws SQLException{
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DELETE FROM Mold WHERE Part = '" + partNum + "'"); 
    }
    
    public void clear() throws SQLException{
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DELETE FROM Mold"); 
    }
    
    public String[] get(String partNum) throws SQLException{
        String[] moldArr = new String[11];
        Statement stmt = connection.createStatement();
         
        ResultSet rs = stmt.executeQuery("SELECT * FROM Mold WHERE Part = '" + partNum + "'");
        if(rs.next()){
            moldArr[0] = rs.getString("Pre");
            moldArr[1] = rs.getString("Preform");
            moldArr[2] = rs.getString("ColorName");
            moldArr[3] = rs.getString("ColorNum");
            moldArr[4] = rs.getString("LDR");
            moldArr[5] = rs.getString("PCR");
            moldArr[6] = rs.getString("PtPerGay");
            moldArr[7] = rs.getString("WtPerSkid");
            moldArr[8] = rs.getString("Machine");
            moldArr[9] = rs.getString("PCRGaylord");
            moldArr[10] = rs.getString("PCRSilo");
        }
        else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Part number " + partNum + " not found in part database");
                alert.showAndWait();
        }
        
        return moldArr;
    }
    
    public String getDatabaseString() throws SQLException{
        Statement stmt = connection.createStatement();
         
        ResultSet rs = stmt.executeQuery("SELECT * FROM Mold ORDER BY Part");
        
        String result = "";
        while ((rs.next())){
            result = result + rs.getString("Part") + "  " + rs.getString("Pre") + "   " + rs.getString("Preform") + "   " + rs.getString("ColorName") + "   " + rs.getString("ColorNum") + "   " + rs.getString("LDR") + "   " + rs.getString("PCR") + "   " + rs.getString("PtPerGay")+ "   " + rs.getString("WtPerSkid")+ "   " + rs.getString("Machine") + "   " + rs.getString("PCRGaylord") + "   " + rs.getString("PCRSilo") +"\n";
        }
        
        return result;
    }
    
    public Boolean isPart(String partNum) throws SQLException{
        Statement stmt = connection.createStatement();
         
        ResultSet rs = stmt.executeQuery("SELECT * FROM Mold WHERE Part = '" + partNum + "'");
        return rs.next();
    }
    
    public boolean testDB(){
        try{
            add("111","","","","","","","","","","","");
            remove("111");
            return true;
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
}
