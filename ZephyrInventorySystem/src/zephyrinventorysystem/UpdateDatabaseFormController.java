/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zephyrinventorysystem;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import java.sql.SQLException;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Spenc
 */
public class UpdateDatabaseFormController implements Initializable {
    
    @FXML
    TextArea tArea;
    @FXML
    Button okBtn;
    @FXML
    Label label;
    
    
    private MoldAdapter moldAdapter;
    
    public void okay(){
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Update Parts Database");
        alert.setContentText("Are you sure you want to overwrite the database with this information? All existing data will be replaced\n\n Press Ok to overwrite the database, press Cancel to return");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
        } else {
            return;
        } 
        
        String[] endLineSplit = tArea.getText().split("\n");
        boolean error = false;
        SQLException exception = new SQLException();
        
        try{
            moldAdapter.clear();
        }
        
        catch(SQLException ex){
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(exception.getMessage());

            alert.showAndWait();
        }
        
        for (String str: endLineSplit){
            String[] tabSplit = new String[16];
            String[] split = str.split("\t");
            for(int i =0; i <=15; i = i+1){
                if (i >= split.length){
                    tabSplit[i] = "N/A";
                }
                else{
                    tabSplit[i] = split[i];
                }
            }
            if (tabSplit[0].length() < 10){

                try{
                    moldAdapter.add(tabSplit[0], tabSplit[4], tabSplit[1], tabSplit[2], tabSplit[3], tabSplit[5], tabSplit[6], tabSplit[7], tabSplit[9],tabSplit[10], tabSplit[14], tabSplit[15]);
                }
                catch(SQLException ex){
                    error = true;
                    exception = ex;
                }
            }

        }
        if(error){
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(exception.getMessage());

                alert.showAndWait();
        }
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Uploaded");
        alert.setHeaderText(null);
        alert.setContentText("The parts database has been updated to new information");

        alert.showAndWait();
        
        Stage stage = (Stage) tArea.getScene().getWindow();
        stage.close();
    }
    
    public void cancel(){
        Stage stage = (Stage) tArea.getScene().getWindow();
        stage.close();
    }
    
    public void setModel(MoldAdapter mAdapter, Connection cn, Boolean viewOnly){
        moldAdapter = mAdapter;
        okBtn.setVisible(!viewOnly);
        label.setVisible(!viewOnly);
        
        
        if (viewOnly){
            try{
                tArea.setText(moldAdapter.getDatabaseString());
            }
            catch(SQLException ex){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(ex.getMessage());

                alert.showAndWait();
            }
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        

    }    
    
}
