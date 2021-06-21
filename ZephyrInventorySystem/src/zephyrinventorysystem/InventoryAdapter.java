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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *
 * @author Spenc
 */
public class InventoryAdapter {
    
    Connection connection;
    
    
     
    public InventoryAdapter(Connection conn, Boolean reset) throws SQLException {
        connection = conn;
        if (reset) {
            Statement stmt = connection.createStatement();
            try {
                // Remove tables if database tables have been created.
            // This will throw an exception if the tables do not exist
            stmt.execute("DROP TABLE Inventory");
                // then do finally
            } catch (SQLException ex) {
                // No need to report an error.
                // The table simply did not exist.
                // do finally to create it
            } finally {
                stmt.execute("CREATE TABLE Inventory ("
                        + "ZephyrBarcode VARCHAR(20), "
                        + "GKPBarcode VARCHAR(30), "
                        + "DateProduced VARCHAR(50), "
                        + "BRNumber VARCHAR(20), "
                        + "PackSlip VARCHAR(20), "
                        + "DateShipped VARCHAR(50), "
                        + "ColourLotNumber VARCHAR(20), "
                        + "ResinLotNumber VARCHAR(20), "
                        + "Technician VARCHAR(50))");                      
            }
        }
    }
    
    public boolean isDuplicateZ(String ZCode) throws SQLException{
        Statement stmt = connection.createStatement();
        
        ResultSet rs = stmt.executeQuery("SELECT * FROM Inventory WHERE ZephyrBarcode = '" + ZCode + "'");
        return rs.next();
    }
    
    public boolean isDuplicateG(String BCode) throws SQLException{
        Statement stmt = connection.createStatement();
        
        ResultSet rs = stmt.executeQuery("SELECT * FROM Inventory WHERE GKPBarcode = '" + BCode + "'");
        return rs.next();
    }
    
    public void AddInventory(ObservableList<TableBox> boxList) throws SQLException{
        
        Statement stmt = connection.createStatement();
        for (int i = 0; i < boxList.size(); i++){
            stmt.executeUpdate("INSERT INTO Inventory (ZephyrBarcode, GKPBarcode, DateProduced, BRNumber, PackSlip, DateShipped, ColourLotNumber, ResinLotNumber, Technician) VALUES ("
                    + "'" + boxList.get(i).getZephyrBarcode() + "',"
                    + "'" + boxList.get(i).getGKPBarcode() + "',"
                    + "'" + boxList.get(i).getDateProduced() + "',"
                    + "'" + boxList.get(i).getBRNumber() + "',"
                    + "'" + boxList.get(i).getPackSlip() + "',"
                    + "'" + boxList.get(i).getDateShipped() + "',"
                    + "'" + boxList.get(i).getColourLotNumber() + "',"
                    + "'" + boxList.get(i).getResinLotNumber() + "',"
                    + "'" + boxList.get(i).getTechnician() + "')");
        }
    }
    
    public void RemoveInventory(String ZBarcode) throws SQLException{
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DELETE FROM Inventory WHERE ZephyrBarcode = '" + ZBarcode + "'"); 
    }
    
    public void RemoveInventoryList(ObservableList<TableBox> boxList) throws SQLException{
        Statement stmt = connection.createStatement();
        for (int i = 0; i< boxList.size(); i++){
            stmt.executeUpdate("DELETE FROM Inventory WHERE ZephyrBarcode = '" + boxList.get(i).getZephyrBarcode() + "'");
        }
    }
    
    public TableBox getEntry(String ZBarcode) throws SQLException{
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Inventory WHERE ZephyrBarcode = '" + ZBarcode + "'");
        
        if (rs.next()){
            TableBox tBox = new TableBox(rs.getString("ZephyrBarcode"), rs.getString("GKPBarcode"));
            tBox.setDateProduced(rs.getString("DateProduced"));
            tBox.setColourLotNumber(rs.getString("ColourLotNumber"));
            tBox.setResinLotNumber(rs.getString("ResinLotNumber"));
            tBox.setTechnician(rs.getString("Technician"));
            return tBox;
        }
        return new TableBox();
    }
    
    public ObservableList<TableBox> GetInventoryList() throws SQLException{
        ObservableList<TableBox> boxList = FXCollections.observableArrayList();
        
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Inventory");
        
        while (rs.next()){
            boxList.add(new TableBox(rs.getString("ZephyrBarcode"), rs.getString("GKPBarcode"), rs.getString("DateProduced"), rs.getString("BRNumber"), rs.getString("PackSlip"), rs.getString("DateShipped"), rs.getString("ColourLotNumber"), rs.getString("ResinLotNumber"), rs.getString("Technician")));
        }
        return boxList;
    }
    
    public boolean isPrevBox(String ZBarcode) throws SQLException{
        String orderNum = ZBarcode.substring(0, 14);
        String boxNum = Integer.toString(Integer.parseInt(ZBarcode.substring(14, 17)) - 1);
        while(boxNum.length()<3){
            boxNum = "0" + boxNum;
        }
        String newBarcode = orderNum + boxNum;
        //If its the first box return true
        if(Integer.parseInt(ZBarcode.substring(14, 17)) - 1 == 0){
            return true;
        }
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Inventory WHERE ZephyrBarcode ='" + newBarcode + "'");
        
        return rs.next();
    }
    

    public boolean testDB(){
        try{
            ObservableList<TableBox> boxList = FXCollections.observableArrayList();
            boxList.add(new TableBox());
            AddInventory(boxList);
            RemoveInventory(boxList.get(0).getZephyrBarcode());
            return true;
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
}
