/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zephyrinventorysystem;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import java.util.*;
import zephyrinventorysystem.comparators.*;
/**
 *
 * @author Spenc
 */
public class CSVWriter {
    
    //private final ObservableList<TableBox> data = FXCollections.observableArrayList();
    private MoldAdapter moldAdapter;
    private final ObservableList<TableBox> boxList = FXCollections.observableArrayList();
    
    public CSVWriter(ObservableList<TableBox> bList, File path, boolean shipped, MoldAdapter mAdapter) throws FileNotFoundException{
        moldAdapter = mAdapter;
        PrintWriter pw = new PrintWriter(path);
        boxList.addAll(bList);

        StringBuilder sb;
        if (shipped){
            sb = getShippedString();
        }
        else{
            sb = getInventoryString();
        }
        
        if (sb.toString().equals("")){
            throw new FileNotFoundException("Part number not found in database");
            
        }

        pw.write(sb.toString());
        pw.close();
    }
    
    private StringBuilder getShippedString(){
        StringBuilder sb = new StringBuilder();
        
        //Add columns
        sb.append("GKPBarcode");
        sb.append(',');
        sb.append("Date");
        sb.append(',');
        sb.append("Lot");
        sb.append(',');
        sb.append("GKP");
        sb.append(',');
        sb.append("Box");
        sb.append(',');
        sb.append("Pre");
        sb.append(',');
        sb.append("Preform");
        sb.append(',');
        sb.append("Color Name");
        sb.append(',');
        sb.append("Color Number");
        sb.append(',');
        sb.append("LDR");
        sb.append(',');
        sb.append("PCR");
        sb.append(',');
        sb.append("PtPerGay");
        sb.append(',');
        sb.append("WtSkid");
        sb.append(',');
        sb.append("Machine");
        sb.append(',');
        sb.append("Shipped");
        sb.append(',');
        sb.append("ShipDate");
        sb.append(',');
        sb.append("MachineNo");
        sb.append(',');
        sb.append("BRNumber");
        sb.append(',');
        sb.append("ColourLotNumber");
        sb.append(',');
        sb.append("ResinLotNumber");
        sb.append(',');
        sb.append("% PCR Gaylord");
        sb.append(',');
        sb.append("% PCR Silo");
        sb.append(',');
        sb.append("Technician");
        sb.append("\r\n");
        
        //Add rows
        String[] moldArr = new String[8];
        TableBox tBox;
        
        for(int i = 0; i < boxList.size(); i++){
            //moldArr = moldAdapter.getInfo(boxList.get(i).getPartNumber());
            tBox = boxList.get(i);
            try{
                moldArr = moldAdapter.get(tBox.getPartNumber());
            }
            catch(SQLException ex){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(ex.getMessage());

                alert.showAndWait();
                sb = new StringBuilder();
                return sb;
            }
            
            //If part number was not found successfully then return
            if (moldArr[0].equals("")){
                sb = new StringBuilder();
                return sb;
            }
           
            sb.append(tBox.getGKPBarcode());
            sb.append(',');
            sb.append(tBox.getDateProduced());
            sb.append(',');
            sb.append("PF" + tBox.getZephyrBarcode().substring(4, 6) + "00" + tBox.getZephyrBarcode().substring(6,9));
            sb.append(',');
            sb.append(tBox.getPartNumber());
            sb.append(',');
            sb.append(tBox.getBoxNumber());
            sb.append(',');
            sb.append(moldArr[0]);
            sb.append(',');
            sb.append(moldArr[1]);
            sb.append(',');
            sb.append(moldArr[2]);
            sb.append(',');
            sb.append(moldArr[3]);
            sb.append(',');
            sb.append(moldArr[4]);
            sb.append(',');
            sb.append(moldArr[5]);
            sb.append(',');
            sb.append(moldArr[6]);
            sb.append(',');
            sb.append(moldArr[7]);
            sb.append(',');
            sb.append(moldArr[8]);
            sb.append(',');
            sb.append("Shipped");
            sb.append(',');
            sb.append(tBox.getDateShipped());
            sb.append(',');
            sb.append(tBox.getZephyrBarcode().substring(2, 4).replaceFirst("^0+(?!$)", ""));
            sb.append(',');
            sb.append(tBox.getBRNumber());
            sb.append(',');
            sb.append(tBox.getColourLotNumber());
            sb.append(',');
            sb.append(tBox.getResinLotNumber());
            sb.append(',');
            sb.append(moldArr[9]);
            sb.append(',');
            sb.append(moldArr[10]);
            sb.append(',');
            sb.append(tBox.getTechnician());
            sb.append("\r\n");
        }
        return sb;
    }

    private StringBuilder getInventoryString(){
        StringBuilder sb = new StringBuilder();
        
        //Add columns
        sb.append("GKP");
        sb.append(',');
        sb.append("Lot");
        sb.append(',');
        sb.append("Box");
        sb.append(',');
        sb.append("Date");
        sb.append("\r\n");
        
        //Add rows
        String[] moldArr = new String[10];
        TableBox tBox;
        
        //Create sorted list
        ObservableList<TableBox> sortedBoxList = FXCollections.observableArrayList();
        sortedBoxList = boxList;
        
        Collections.sort(sortedBoxList, new TableBoxChainedComparator(
                new TableBoxPartComparator(),
                new TableBoxPFComparator(),
                new TableBoxBoxComparator())
        );
        
        
        for(int i = 0; i < sortedBoxList.size(); i++){

            tBox = sortedBoxList.get(i);
           
            sb.append(tBox.getPartNumber());
            sb.append(',');
            sb.append("PF" + tBox.getZephyrBarcode().substring(4, 6) + "00" + tBox.getZephyrBarcode().substring(6,9));
            sb.append(',');
            sb.append(tBox.getBoxNumber());
            sb.append(',');
            sb.append(tBox.getDateProduced());
            sb.append("\r\n");
        }
        
        sb.append("\r\n");
        sb.append("TOTALS");
        
        String currentPF = sortedBoxList.get(0).getZephyrBarcode().substring(4, 6) + "00" + sortedBoxList.get(0).getZephyrBarcode().substring(6,9);
        String currentPart = sortedBoxList.get(0).getPartNumber();
                   
        int numCurrentPF = 1;
        for (int i = 0; i < sortedBoxList.size(); i++){
            if ((sortedBoxList.get(i).getZephyrBarcode().substring(4, 6) + "00" + sortedBoxList.get(i).getZephyrBarcode().substring(6,9)).equals(currentPF)){
                numCurrentPF ++;
            }
            else{
                sb.append("\r\n");
                sb.append(currentPart);
                sb.append(',');
                sb.append("PF" + currentPF);
                sb.append(',');
                sb.append(numCurrentPF);
                
                currentPF = sortedBoxList.get(i).getZephyrBarcode().substring(4, 6) + "00" + sortedBoxList.get(i).getZephyrBarcode().substring(6,9);
                numCurrentPF = 1;
            }
            if(!(sortedBoxList.get(i).getPartNumber().equals(currentPart))){
                currentPart = sortedBoxList.get(i).getPartNumber();
            }
        }
        sb.append("\r\n");
        sb.append(currentPart);
        sb.append(',');
        sb.append("PF" + currentPF);
        sb.append(',');
        sb.append(numCurrentPF);
        
        return sb;
    }
    
    }

