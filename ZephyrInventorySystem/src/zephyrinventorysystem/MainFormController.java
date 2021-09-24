/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zephyrinventorysystem;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import java.util.Optional;
import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableCell;
import javafx.collections.ObservableList;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Ellipse;
import javafx.scene.control.Label;
import javafx.application.Platform;
import javafx.util.Callback;
import javafx.scene.paint.Color;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.text.*;
import java.util.stream.Collectors;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

/**
 *
 * @author Spenc
 */




public class MainFormController implements Initializable {

    @FXML
    public TableView tableView;
    @FXML
    public TextArea tArea;
    @FXML
    public Ellipse scanCircle;
    @FXML
    public Label scanLabel;
    @FXML
    public Button startTruckButton;
    @FXML
    public Button addInventoryButton;
    @FXML
    public Button viewInventoryButton;
    @FXML
    public Button removeButton;
    @FXML
    public Button replaceButton;
    @FXML
    public Button insertButton;
    @FXML
    public Button cancelButton;
    @FXML
    public MenuBar menuBar;
    @FXML
    public Menu start;
    @FXML
    public Label BRLabel,packSlipLabel,cLotNumLabel, rLotNumLabel, techLabel;
    @FXML
    public TextField cLotNumField, rLotNumField, techField;
    @FXML
    public Button resumeScanning, clear, upload, viewTotals;

    private InventoryAdapter inventoryAdapter;
    private MoldAdapter moldAdapter;
    private Connection conn;
    
    
    private final ObservableList<TableBox> data = FXCollections.observableArrayList();
    
    public void newPartsDatabase() throws IOException{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateDatabaseForm.fxml"));
        Parent parent = (Parent) loader.load();
        UpdateDatabaseFormController controller = (UpdateDatabaseFormController) loader.getController();
        
        controller.setModel(moldAdapter, conn, false);
        
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Update Parts Database");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
    
    public void viewPartsDatabase() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateDatabaseForm.fxml"));
        Parent parent = (Parent) loader.load();
        UpdateDatabaseFormController controller = (UpdateDatabaseFormController) loader.getController();
        
        controller.setModel(moldAdapter, conn, true);
        
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("View Parts Database");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
    
    public void addPart(){
        String Part, Pre, Preform, ColorName, ColorNum, LDR, PCR, PtPerGay, WtPerSkid, Machine, PCRGaylord, PCRSilo;
        
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Enter part number");
        dialog.setHeaderText(null);
        dialog.setContentText("Input part number \n e.g. 443");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            Part = result.get();
        }
        else{
            return;
        }
        
        dialog = new TextInputDialog("");
        dialog.setHeaderText(null);
        dialog.setTitle("Enter Pre");
        dialog.setContentText("Input pre number \n e.g. PRE3840024");
        result = dialog.showAndWait();
        if (result.isPresent()){
            Pre = result.get();
        }
        else{
            return;
        }
        
        dialog = new TextInputDialog("");
        dialog.setHeaderText(null);
        dialog.setTitle("Enter Preform");
        dialog.setContentText("Input prefrom description \n e.g. 38mm 23.6 Gram {48 Cavity}");
        result = dialog.showAndWait();
        if (result.isPresent()){
            Preform = result.get();
        }
        else{
            return;
        }
        
        dialog = new TextInputDialog("");
        dialog.setHeaderText(null);
        dialog.setTitle("Enter Color Name");
        dialog.setContentText("Input color name \n e.g. Blue UV or Clear");
        result = dialog.showAndWait();
        if (result.isPresent()){
            ColorName = result.get();
        }
        else{
            return;
        }
        
        dialog = new TextInputDialog("");
        dialog.setHeaderText(null);
        dialog.setTitle("Enter Color Number");
        dialog.setContentText("Input color number \n e.g. 175.2110 or leave blank for clear");
        result = dialog.showAndWait();
        if (result.isPresent()){
            ColorNum = result.get();
        }
        else{
            ColorNum = "";
        }
        
        dialog = new TextInputDialog("");
        dialog.setHeaderText(null);
        dialog.setTitle("Enter LDR");
        dialog.setContentText("Input LDR percentage \n e.g. 0.0018");
        result = dialog.showAndWait();
        if (result.isPresent()){
            LDR = result.get();
        }
        else{
            return;
        }
        
        dialog = new TextInputDialog("");
        dialog.setHeaderText(null);
        dialog.setTitle("Enter PCR");
        dialog.setContentText("Input PCR percentage \n e.g. 0.25");
        result = dialog.showAndWait();
        if (result.isPresent()){
            PCR = result.get();
        }
        else{
            return;
        }
        
        dialog = new TextInputDialog("");
        dialog.setHeaderText(null);
        dialog.setTitle("Enter parts per gaylord");
        dialog.setContentText("Input the number of parts per gaylord \n e.g. 11500");
        result = dialog.showAndWait();
        if (result.isPresent()){
            PtPerGay = result.get();
        }
        else{
            return;
        }
        
        dialog = new TextInputDialog("");
        dialog.setHeaderText(null);
        dialog.setTitle("Enter weight per skid");
        dialog.setContentText("Input the weight of one skid \n e.g. 325");
        result = dialog.showAndWait();
        if (result.isPresent()){
            WtPerSkid = result.get();
        }
        else{
            return;
        }
        
        dialog = new TextInputDialog("");
        dialog.setHeaderText(null);
        dialog.setTitle("Enter machine");
        dialog.setContentText("Input the type of machine \n e.g. 300T");
        result = dialog.showAndWait();
        if (result.isPresent()){
            Machine = result.get();
        }
        else{
            return;
        }
        
        dialog = new TextInputDialog("");
        dialog.setHeaderText(null);
        dialog.setTitle("Enter Gaylord PCR");
        dialog.setContentText("Input the percentage of PCR from gaylord \n e.g. 100 or 0");
        result = dialog.showAndWait();
        if (result.isPresent()){
            PCRGaylord = result.get();
        }
        else{
            return;
        }
        
        dialog = new TextInputDialog("");
        dialog.setHeaderText(null);
        dialog.setTitle("Enter Silo PCR");
        dialog.setContentText("Input the percentage of PCR from silo \n e.g. 100 or 0");
        result = dialog.showAndWait();
        if (result.isPresent()){
            PCRSilo = result.get();
        }
        else{
            return;
        }
        
        try{
            moldAdapter.add(Part, Pre, Preform, ColorName, ColorNum, LDR, PCR, PtPerGay, WtPerSkid, Machine, PCRGaylord, PCRSilo);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("New part added to database");

            alert.showAndWait();
        }
        catch(SQLException ex){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(ex.getMessage());

                alert.showAndWait();
        }
    }
    
    public void removePart(){
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Enter part number");
        dialog.setHeaderText(null);
        dialog.setContentText("Input part number that you want to delete from database\n e.g. 443");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            try{
                moldAdapter.remove(result.get());
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Part removed from database");

                alert.showAndWait();
            }
            catch(SQLException ex){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(ex.getMessage());

                alert.showAndWait();
            }
        }
        else{
            return;
        }
    }
    
    public void startTruck(){
        
    Alert alert1 = new Alert(AlertType.CONFIRMATION);
    alert1.setTitle("Confirm");
    alert1.setHeaderText(null);
    alert1.setContentText("Are you sure you want to start a new truck?");

    Optional<ButtonType> result1 = alert1.showAndWait();
    if (result1.get() == ButtonType.OK){
        // ... user chose OK
    } else {
        return;
    }
        data.clear();
        
        removeButton.setDisable(false);
        replaceButton.setDisable(false);
        insertButton.setDisable(false);
        clear.setDisable(false);
        upload.setDisable(false);
        
        
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Br Number");
        dialog.setHeaderText(null);
        dialog.setContentText("Please enter the BR number \n Enter the number that would be in the space BR_______________.21");
        
        

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
           BRLabel.setText("BR" + result.get() + ".21");
           BRLabel.setVisible(true);
        }
        
        dialog = new TextInputDialog();
        dialog.setHeaderText(null);
        dialog.setTitle("Pack Slip");
        dialog.setContentText("Please enter the pack slip number");

        // Traditional way to get the response value.
        result = dialog.showAndWait();
        if (result.isPresent()){
            try{
                packSlipLabel.setText(result.get());
                packSlipLabel.setVisible(true);
                dialog.setResult("");
            }
            catch(NumberFormatException ex){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Pack slip must only contain numbers");

                alert.showAndWait();
            }
        }
 
        initializeShippingTable();
        tArea.requestFocus();
        
        ZephyrInventorySystem.MyStage.setTitle("Creating Truck Log");
    }
    
    public void addToInventory(){
        
        data.clear();
        initializeTable();
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Begin Scanning");
        alert.setHeaderText(null);
        alert.setContentText("Instructions: \n 1. First scan the Zephyr barcode \n 2. Then scan the GKP barcode \n\nPress 'Ok' to begin scanning.");

        alert.showAndWait();
        
        BRLabel.setVisible(false);
        packSlipLabel.setVisible(false);

        removeButton.setDisable(false);
        replaceButton.setDisable(false);
        insertButton.setDisable(false);
        clear.setDisable(false);
        upload.setDisable(false);
        tArea.clear();
        tArea.requestFocus();
        
        ZephyrInventorySystem.MyStage.setTitle("Adding Inventory");

    }
    
    public void viewInventory() throws SQLException{
        initializeInventoryTable();
        removeButton.setDisable(false);
        replaceButton.setDisable(true);
        insertButton.setDisable(true);
        clear.setDisable(true);
        upload.setDisable(true);
        BRLabel.setVisible(false);
        packSlipLabel.setVisible(false);
        
        data.clear();
        data.addAll(inventoryAdapter.GetInventoryList());
        tableView.setItems(data);
        
        ZephyrInventorySystem.MyStage.setTitle("Viewing Current Inventory");

    }
    
    public void ViewTotals() throws SQLException{
        
        ObservableList<TableBox> boxList = FXCollections.observableArrayList();
        ObservableList<String> partList = FXCollections.observableArrayList();
        
        boxList = tableView.getItems();

        
        for(int i = 0; i < boxList.size(); i++){
            partList.add(boxList.get(i).getPartNumber());
        }
        
        Collections.sort(partList);
        
        List distinctList = partList.stream().distinct().collect(Collectors.toList());
        
        String str = "";
	for (Object s: distinctList) {
            str = str + "\r\n" + s.toString() + ":" + Collections.frequency(partList, s.toString());
	}
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Totals Summary");
        alert.setHeaderText(null);
        alert.setContentText(str);

        alert.showAndWait();
    }
    
    public void Upload() throws SQLException{
            //Check for no errors
            for(TableBox tBox : data){
                if (!tBox.getMessage().matches("\\ABox number .{1,3} is not in inventory\\. Did you forget to scan the last box\\?\\Z")
                        &&!tBox.getMessage().equals("")
                        ||tBox.getGKPBarcode().equals("GKP barcode not scanned")
                        ||tBox.getGKPBarcode().equals("Scan GKP barcode")
                        ){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Unable to upload");
                    alert.setHeaderText(null);
                    alert.setContentText("Please make sure you have scanned both barcodes and fix all errors before uploading");
                    alert.showAndWait();
                    return;
                }
            }
            if (BRLabel.isVisible()){
                UploadTruck();
            }
            else{
                UploadInventory();
            }
            
            ZephyrInventorySystem.MyStage.setTitle("Home");
    }
    
    private void UploadTruck() throws SQLException{
        
        Date dNow = new Date( );
        SimpleDateFormat ft = 
        new SimpleDateFormat ("d/M/yyyy h:mm:ss a");
        
        
        //Add shipped date and BRNumber
        for(int i = 0; i < data.size(); i++){
            data.get(i).setDateShipped(ft.format(dNow));
            data.get(i).setBRNumber(BRLabel.getText());
        }
        
        //delete from database
        inventoryAdapter.RemoveInventoryList(data);
        
        //create csv files (inventory must be updated as well)
        try{
            File file = new File("C:\\Users\\Zephyr\\OneDrive\\Inventory\\TruckLogs\\" + ft.format(dNow).replaceAll("/", "-").replaceAll(":", ".") + " " + packSlipLabel.getText() + ".csv");
            CSVWriter writer = new CSVWriter(data,file, true, moldAdapter);
            File file2 = new File("C:\\Users\\Zephyr\\OneDrive\\Inventory\\CurrentInventory.csv");
            file2.delete();
            CSVWriter writer2 = new CSVWriter(inventoryAdapter.GetInventoryList(),file2,false, moldAdapter);
        }
        catch(FileNotFoundException ex){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(ex.getMessage());

            alert.showAndWait();
            return;
        };
        
        data.clear();
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Uploaded");
        alert.setHeaderText(null);
        alert.setContentText("The truck log has been uploaded");

        alert.showAndWait();
        
        removeButton.setDisable(true);
        replaceButton.setDisable(true);
        insertButton.setDisable(true);
        clear.setDisable(true);
        upload.setDisable(true);
        BRLabel.setVisible(false);
        packSlipLabel.setVisible(false);
        tableView.requestFocus();
    }
    
    private void UploadInventory() throws SQLException{
        Date dNow = new Date( );
        SimpleDateFormat ft = 
        new SimpleDateFormat ("d/M/yyyy h:mm:ss a");
        //Add produced date
        for(int i = 0; i < data.size(); i++){
            data.get(i).setDateProduced(ft.format(dNow));
        }
        inventoryAdapter.AddInventory(data);
        data.clear();
        
        //create csv file
        try{
            File file = new File("C:\\Users\\Zephyr\\OneDrive\\Inventory\\CurrentInventory.csv");
            file.delete();
            CSVWriter writer = new CSVWriter(inventoryAdapter.GetInventoryList(),file, false, moldAdapter);
        }
        catch(FileNotFoundException ex){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(ex.getMessage());

            alert.showAndWait();
        };
        
        data.clear();
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Uploaded");
        alert.setHeaderText(null);
        alert.setContentText("The inventory has been uploaded");

        alert.showAndWait();
        
        removeButton.setDisable(true);
        replaceButton.setDisable(true);
        insertButton.setDisable(true);
        clear.setDisable(true);
        upload.setDisable(true);
        tableView.requestFocus();
        BRLabel.setVisible(false);
        packSlipLabel.setVisible(false);
       
        
    }
    
    public void insert(){
        TableBox selected = (TableBox) tableView.getSelectionModel().getSelectedItem();
        data.add(data.indexOf(selected) + 1, new TableBox());
    }
    
    public void remove(){
        
        TableBox box = (TableBox) tableView.getSelectionModel().getSelectedItem();
        
        
        data.remove(tableView.getSelectionModel().getSelectedItem());
        tableView.refresh();
        
        
        try{
            if (ZephyrInventorySystem.MyStage.getTitle().equals("Viewing Current Inventory")){
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirm Remove");
                alert.setHeaderText(null);
                alert.setContentText("Part Number: " + box.getPartNumber() + "\n Box Number: " + box.getBoxNumber() + "\n\n Remove this box from inventory?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    inventoryAdapter.RemoveInventory(box.getZephyrBarcode());
                    //create csv file
                    try{
                        File file = new File("C:\\Users\\Zephyr\\OneDrive\\Inventory\\CurrentInventory.csv");
                        file.delete();
                        CSVWriter writer = new CSVWriter(inventoryAdapter.GetInventoryList(),file, false, moldAdapter);
                    }
                    catch(FileNotFoundException ex){
                        alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText(ex.getMessage());

                        alert.showAndWait();
                    };
                } else {
                    // ... user chose CANCEL or closed the dialog
                }

            }
        }
        catch(SQLException ex){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(ex.getMessage());

            alert.showAndWait();
        };
    }
    
    public void ResumeScanning(){
        if (ZephyrInventorySystem.MyStage.getTitle().equals("Home")|| ZephyrInventorySystem.MyStage.getTitle().equals("Viewing Current Inventory")){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("No Scanning Mode Selected");
            alert.setContentText("You must select a scanning mode to begin scanning.\n\nClick on Start then either \"Start Truck\" or \"Add Inventory\"");
            alert.showAndWait();
            return;
        }
        tArea.clear();
        tArea.requestFocus();
    }
    
    public void Clear(){
        data.clear();
    }
    
    public void Replace(){
        TableBox box = (TableBox) tableView.getSelectionModel().getSelectedItem();
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Replace");
        alert.setHeaderText(null);
        alert.setContentText("Part Number: " + box.getPartNumber() + "\n Box Number: " + box.getBoxNumber() + "\n\n Replace this box?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            data.set(data.indexOf(box), new TableBox());
            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Scan New Box");
            alert.setHeaderText(null);
            alert.setContentText("Please scan the new box");

            alert.showAndWait();
            tArea.requestFocus();
        } else {
            // ... user chose CANCEL or closed the dialog
        }
        
    }
    
    private void initializeShippingTable(){
        tableView.getColumns().clear();
        
        
        TableColumn part = new TableColumn("Part#");
        part.setMaxWidth(600);
        TableColumn box = new TableColumn("Box#");
        box.setMaxWidth(600);
        TableColumn GKPID = new TableColumn("GKP ID");
        GKPID.setMaxWidth(2000);
        TableColumn zephyrBarcode = new TableColumn("Zephyr Barcode");
        zephyrBarcode.setMaxWidth(2100);
        TableColumn producedDate = new TableColumn("Produced Date");
        TableColumn message = new TableColumn("Message");
        message.setPrefWidth(5000);
        
        tableView.getColumns().addAll(part,box,GKPID,zephyrBarcode, producedDate,message);
        
        part.setCellValueFactory(new PropertyValueFactory<TableBox, String>("partNumber"));
        box.setCellValueFactory(new PropertyValueFactory<TableBox, String>("boxNumber"));
        GKPID.setCellValueFactory(new PropertyValueFactory<TableBox, String>("GKPBarcode"));
        zephyrBarcode.setCellValueFactory(new PropertyValueFactory<TableBox, String>("zephyrBarcode"));
        zephyrBarcode.setCellFactory(new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn param) {
                return new TableCell<TableBox, String>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        //reset the style on update
                        this.setStyle(null);
                        TableBox tBox = (TableBox)this.getTableRow().getItem();
                        try{
                            if (item != null && (IsDuplicateZBC(item))) {
                                this.setStyle("-fx-background-color: #ff0000");
                                    tBox.setMessage("Zephyr barcode "+ item +" has been scanned twice");
                            }
                            else if (item != null){
                                this.setStyle("-fx-background-color: #18ff00");
                                if(tBox.getMessage().matches("\\AZephyr barcode ................. has been scanned twice\\Z")){
                                    tBox.setMessage("");
                                }
                            }
                        }
                        catch(NullPointerException ex){}

                        try{
                            if (item!= null && !inventoryAdapter.isDuplicateZ(item) && !item.equals("")){
                                this.setStyle("-fx-background-color: #ff0000");
                                    tBox.setMessage("Zephyr barcode "+ item +" is not in inventory");
                            }
                            else{
                                if(tBox.getMessage().matches("\\AZephyr barcode ................. is not in inventory")){
                                    tBox.setMessage("");
                                }
                            }
                        }
                        catch(NullPointerException ex){}
                        catch(SQLException ex){
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setContentText(ex.getMessage());

                            alert.showAndWait();
                        }
                        
                        this.setText(item);
                        super.updateItem(item, empty);

                    }
                };
            }
        });
        producedDate.setCellValueFactory(new PropertyValueFactory<TableBox, String>("dateProduced"));
        message.setCellValueFactory(new PropertyValueFactory<TableBox, String>("message"));
        message.setCellFactory(new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn param) {
                return new TableCell<TableBox, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        if(item!=null&&(
                                item.matches("\\AGKP barcode ...... already used\\. Please use a new one\\Z")
                                ||item.matches("\\AZephyr barcode ................. has been scanned twice\\Z")
                                ||item.matches("\\AZephyr barcode ................. is not in inventory")
                                )){
                            this.setStyle("-fx-background-color: #ff0000");
                        }
                        else{
                            this.setStyle(null);
                        }

                        this.setText(item);
                        super.updateItem(item, empty);
                    }
                    
                };
            }
        });
        
    }
    
    private void initializeInventoryTable(){
        tableView.getColumns().clear();
        
        TableColumn part = new TableColumn("Part#");
        part.setMaxWidth(1500);
        TableColumn box = new TableColumn("Box#");
        box.setMaxWidth(1500);
        TableColumn dateProduced = new TableColumn("Date Produced");
        TableColumn GKPID = new TableColumn("GKP ID");
        TableColumn zephyrBarcode = new TableColumn("Zephyr Barcode");
        TableColumn colourLotNumber = new TableColumn("Colour Lot #");
        colourLotNumber.setMaxWidth(3000);
        TableColumn resinLotNumber = new TableColumn("Railcar #");
        resinLotNumber.setMaxWidth(3000);
        TableColumn tech = new TableColumn("Technician");
        
        tableView.getColumns().addAll(part,box,dateProduced,GKPID,zephyrBarcode,colourLotNumber,resinLotNumber, tech);
        
        
        part.setCellValueFactory(new PropertyValueFactory<TableBox, String>("partNumber"));
        box.setCellValueFactory(new PropertyValueFactory<TableBox, String>("boxNumber"));
        GKPID.setCellValueFactory(new PropertyValueFactory<TableBox, String>("GKPBarcode"));      
        zephyrBarcode.setCellValueFactory(new PropertyValueFactory<TableBox, String>("zephyrBarcode"));
        dateProduced.setCellValueFactory(new PropertyValueFactory<TableBox, String>("dateProduced"));
        colourLotNumber.setCellValueFactory(new PropertyValueFactory<TableBox, String>("colourLotNumber"));
        resinLotNumber.setCellValueFactory(new PropertyValueFactory<TableBox, String>("resinLotNumber"));
        tech.setCellValueFactory(new PropertyValueFactory<TableBox, String>("technician"));
    }
    
    private void initializeTable(){
        
        tableView.getColumns().clear();
        
        
        TableColumn part = new TableColumn("Part#");
        part.setMaxWidth(600);
        TableColumn box = new TableColumn("Box#");
        box.setMaxWidth(600);
        TableColumn GKPID = new TableColumn("GKP ID");
        GKPID.setMaxWidth(2000);
        TableColumn zephyrBarcode = new TableColumn("Zephyr Barcode");
        zephyrBarcode.setMaxWidth(2100);
        TableColumn colourLotNumber = new TableColumn ("Colour Lot #");
        colourLotNumber.setMaxWidth(800);
        TableColumn resinLotNumber = new TableColumn ("Railcar #");
        resinLotNumber.setMaxWidth(1000);
        TableColumn tech = new TableColumn("Technician");
        tech.setMaxWidth(1500);
        TableColumn message = new TableColumn("Message");
        message.setPrefWidth(5000);
                
        
        tableView.getColumns().addAll(part,box,GKPID,zephyrBarcode,colourLotNumber,resinLotNumber, tech, message);
        
        part.setCellValueFactory(new PropertyValueFactory<TableBox, String>("partNumber"));
        box.setCellValueFactory(new PropertyValueFactory<TableBox, String>("boxNumber"));
        
        GKPID.setCellValueFactory(new PropertyValueFactory<TableBox, String>("GKPBarcode"));
        GKPID.setCellFactory(new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn param) {
                return new TableCell<TableBox, String>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        try{
                            TableBox tBox = (TableBox)this.getTableRow().getItem();
                            //reset style on update
                            this.setStyle(null);

                            if (item != null && (IsDuplicateGBC(item))) {
                                this.setStyle("-fx-background-color: #ff0000");
                                if (!item.equals("GKP barcode not scanned")){
                                     tBox.setMessage("GKP barcode "+ item +" has been scanned twice. Please use a different barcode");
                                }
                            }
                            else if (item != null){
                                this.setStyle("-fx-background-color: #18ff00");
                                if(tBox.getMessage().matches("\\AGKP barcode ......{1,6} has been scanned twice\\. Please use a different barcode\\Z")){
                                    tBox.setMessage("");
                                }
                            }
                            if (item!= null && inventoryAdapter.isDuplicateG(item) && !item.equals("GKP barcode not scanned")){
                                this.setStyle("-fx-background-color: #ff0000");
                                tBox.setMessage("GKP barcode "+ item +" is already in inventory. Please use a different barcode");
                            }
                            else{
                                if(tBox.getMessage().matches("\\AGKP barcode ......{1,6} is already in inventory\\. Please use a different barcode\\Z")){
                                    tBox.setMessage("");
                                }
                            }

                            if (item != null && (item.equals("GKP barcode not scanned")||item.equals("Scan GKP barcode"))){
                                this.setStyle("-fx-background-color: #fff000");
                            }
                            this.setText(item);
                            super.updateItem(item, empty);
                        }
                        catch(NullPointerException ex){
                        }
                        catch(SQLException ex){
                                Alert alert = new Alert(AlertType.ERROR);
                                alert.setTitle("Error");
                                alert.setContentText(ex.getMessage());

                                alert.showAndWait();
                        }
                    }
                };
            }
        });
        
        zephyrBarcode.setCellValueFactory(new PropertyValueFactory<TableBox, String>("zephyrBarcode"));
        zephyrBarcode.setCellFactory(new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn param) {
                return new TableCell<TableBox, String>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        try{
                            TableBox tBox = (TableBox)this.getTableRow().getItem();
                            //reset style on update
                            this.setStyle(null);

                            if (item != null && (IsDuplicateZBC(item))) {
                                this.setStyle("-fx-background-color: #ff0000");
                                tBox.setMessage("Zephyr barcode " + item + " has been scanned twice");

                            }
                            else if (item != null){
                                this.setStyle("-fx-background-color: #18ff00");
                                
                                if(tBox.getMessage().matches("\\AZephyr barcode ................. has been scanned twice\\Z")){
                                    tBox.setMessage("");
                                }
                            }

                            if (item!= null && inventoryAdapter.isDuplicateZ(item)){
                                this.setStyle("-fx-background-color: #ff0000");
                                tBox.setMessage("Zephyr barcode " + item + " is already in inventory");
                            }
                            else if(tBox.getMessage().matches("\\AZephyr barcode ................. is already in inventory\\Z")){
                                tBox.setMessage("");
                            }


                            this.setText(item);
                            super.updateItem(item, empty);

                        }
                        catch(NullPointerException ex){
                        }
                        catch(SQLException ex){
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setContentText(ex.getMessage());

                            alert.showAndWait();
                        }
                       
                    }
                    
                };
            }
        });
        colourLotNumber.setCellValueFactory(new PropertyValueFactory<TableBox, String>("colourLotNumber"));
        resinLotNumber.setCellValueFactory(new PropertyValueFactory<TableBox, String>("resinLotNumber"));
        tech.setCellValueFactory(new PropertyValueFactory<TableBox, String>("technician"));
        message.setCellValueFactory(new PropertyValueFactory<TableBox, String>("message"));
        message.setCellFactory(new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn param) {
                return new TableCell<TableBox, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        if(item!=null&&(
                                item.matches("\\AGKP barcode ......{1,6} has been scanned twice\\. Please use a different barcode\\Z")
                                ||item.matches("\\AGKP barcode ......{1,6} is already in inventory\\. Please use a different barcode\\Z")
                                ||item.matches("\\AZephyr barcode ................. has been scanned twice\\Z")
                                ||item.matches("\\AZephyr barcode ................. is already in inventory\\Z")
                                )){
                            this.setStyle("-fx-background-color: #ff0000");
                        }
                        else if(item!=null&&item.matches("\\ABox number .{1,3} is not in inventory\\. Did you forget to scan the last box\\?\\Z")){
                            this.setStyle("-fx-background-color: #00ffff");
                        }
                        else{
                            this.setStyle(null);
                        }
                        this.setText(item);
                        super.updateItem(item, empty);
                    }
                    
                };
            }
        });
    }
    
    private boolean IsDuplicateZBC(String ZCode){
        int found = 0;

        for (int i = 0; i < data.size(); i++){
            if (ZCode.equals(data.get(i).getZephyrBarcode())){
                found++;
            }
            if (found == 2){
                return true;
            }
        }
        return false;
    }
    
    private boolean IsDuplicateGBC(String BCode){
        int found = 0;

        for (int i = 0; i < data.size(); i++){
            if (BCode.equals(data.get(i).getGKPBarcode())){
                found++;
            }
            if (found == 2){
                return true;
            }
        }
        return false;
    }
    
    private void ZephyrCodeScanned(){
        //Check if we need to get data from database
        TableBox tBox = new TableBox(tArea.getText().replaceAll("\n", ""), "GKP barcode not scanned", cLotNumField.getText(), rLotNumField.getText(), techField.getText());
      
        if (BRLabel.isVisible()){
            try{
                tBox = inventoryAdapter.getEntry(tArea.getText().replaceAll("\n", ""));
                if (tBox.getZephyrBarcode().equals("")){
                    tBox = new TableBox(tArea.getText().replaceAll("\n", ""), "GKP barcode not scanned");
                }
                if (!moldAdapter.isPart(tBox.getPartNumber())){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Part number not added");
                    alert.setHeaderText(null);
                    alert.setContentText("The part number you are trying to add does not exist in the parts database. Please add it to the database before making the truck log");
                    
                    Button okButton = (Button) alert.getDialogPane().lookupButton( ButtonType.OK );
                    okButton.setDefaultButton( false );
                    
                    alert.showAndWait();
                    return;
                }
            }
            catch(SQLException ex){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(ex.getMessage());

                alert.showAndWait();
            }
        }
        tArea.clear();
        //Check if theres a blank entry to be replaced
        for (int i = 0; i < data.size(); i++){
            if (data.get(i).getZephyrBarcode().equals("")){
                if (!BRLabel.isVisible()){
                    tBox.setGKPBarcode("Scan GKP barcode");
                }
                data.set(i, tBox);
                tableView.setItems(data);
                tArea.clear();
                return;
            }
        }
        
        //Check if box was skipped
        if(!BRLabel.isVisible()){
            try{
                if(!inventoryAdapter.isPrevBox(tBox.getZephyrBarcode())){
                    tBox.setMessage("Box number " + (Integer.parseInt(tBox.getZephyrBarcode().substring(14,17))-1) + " is not in inventory. Did you forget to scan the last box?");
                }
                else{
                    tBox.setMessage("");
                }

            }
            catch(SQLException ex){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(ex.getMessage());

                    alert.showAndWait();
            }
        }
        
        data.add(tBox);
        tableView.setItems(data);
        
    }
    
    private void GKPCodeScanned(){
        if (BRLabel.isVisible()){
            tArea.clear();
            return;
        }
        //Check if a replaced box still needs gkp barcode
        for(int i = 0; i < data.size(); i++){
            if (data.get(i).getGKPBarcode().equals("Scan GKP barcode")){
                data.set(i, new TableBox(data.get(i).getZephyrBarcode(), tArea.getText().replaceAll("\n",""), cLotNumField.getText(),rLotNumField.getText(), techField.getText(), data.get(i).getMessage()));
                tArea.clear();
                return;
            }
        }
        if (!data.isEmpty()){
            data.set(data.size()-1, new TableBox(data.get(data.size()-1).getZephyrBarcode(), tArea.getText().replaceAll("\n",""), cLotNumField.getText(),rLotNumField.getText(), techField.getText(), data.get(data.size()-1).getMessage()));
            tArea.clear();
        }
        tArea.clear();

    }
    
    private void addListeners(){
        tArea.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                scanLabel.setText("SCAN");
                scanCircle.setFill(Color.LIGHTGREEN);
            }
            else{
                scanLabel.setText("DONT SCAN");
                scanCircle.setFill(Color.RED);
            }
        });
        
        tArea.setOnKeyPressed(
                event -> 
                {
                    if(event.getCode().toString().equals("ENTER")){
                        String code = tArea.getText().replace("\n", "");
                        if (code.length() == 17){
                            ZephyrCodeScanned();
                        }
                        else if (code.length() <= 6){
                            GKPCodeScanned();
                        }
                        else{
                            tArea.clear();
                        }
                    }
                }
        );
    }
    
    private void initializeDB() throws SQLException{
        inventoryAdapter = new InventoryAdapter(conn, false);
        if (!inventoryAdapter.testDB()){
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Initialize Database");
            alert.setHeaderText("Database needs to be initialized");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                inventoryAdapter = new InventoryAdapter(conn, true);
            } else {
                
            }
        }
        
        moldAdapter = new MoldAdapter(conn, false);
        if (!moldAdapter.testDB()){
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Initialize Database");
            alert.setHeaderText("Database needs to be initialized");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                moldAdapter = new MoldAdapter(conn, true);
            } else {
                
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        
        
        try {
            String DB_URL = "jdbc:derby:ZephyrInventory;create=true";
            // Create a connection to the database
            conn = DriverManager.getConnection(DB_URL);

        } catch (SQLException ex) {
            
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(ex.getMessage());

            alert.showAndWait();
        }
                
        try{
            initializeDB();
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        
        addListeners();
       
        tArea.toBack();
        removeButton.setDisable(true);
        replaceButton.setDisable(true);
        insertButton.setDisable(true);
        clear.setDisable(true);
        upload.setDisable(true);
        
        Platform.runLater(new Runnable() {
            public void run() {
                tableView.requestFocus();
            }
        });
    }    
    
}
