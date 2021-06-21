/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zephyrinventorysystem;
import javafx.beans.property.SimpleStringProperty;
/**
 *
 * @author Spenc
 */
public class TableBox {
    
    private SimpleStringProperty partNumber;
    private SimpleStringProperty boxNumber;
    private SimpleStringProperty GKPBarcode;
    private SimpleStringProperty ZephyrBarcode;
    private SimpleStringProperty dateProduced;
    private SimpleStringProperty BRNumber;
    private SimpleStringProperty PackSlip;
    private SimpleStringProperty dateShipped;
    private SimpleStringProperty colourLotNumber;
    private SimpleStringProperty resinLotNumber;
    private SimpleStringProperty technician;
    private SimpleStringProperty message;
    
    public TableBox(String ZBarcode, String GBarcode, String dProduced, String brNum, String packSlip, String dShipped, String cLotNum, String rLotNum, String tech, String message){
        this.GKPBarcode = new SimpleStringProperty(GBarcode.replaceFirst("^0+(?!$)", ""));
        this.ZephyrBarcode = new SimpleStringProperty(ZBarcode);
        this.partNumber = new SimpleStringProperty(ZBarcode.substring(9, 14).replaceFirst("^0+(?!$)", ""));
        this.boxNumber = new SimpleStringProperty(ZBarcode.substring(14, 17).replaceFirst("^0+(?!$)", ""));
        this.dateProduced = new SimpleStringProperty(dProduced);
        this.BRNumber = new SimpleStringProperty(brNum);
        this.PackSlip = new SimpleStringProperty(packSlip);
        this.dateShipped = new SimpleStringProperty(dShipped);
        this.colourLotNumber = new SimpleStringProperty(cLotNum);
        this.resinLotNumber = new SimpleStringProperty(rLotNum);
        this.technician = new SimpleStringProperty(tech);
        this.message = new SimpleStringProperty(message);
    }
    public TableBox(String ZBarcode, String GBarcode, String dProduced, String brNum, String packSlip, String dShipped, String cLotNum, String rLotNum, String tech){
        this.GKPBarcode = new SimpleStringProperty(GBarcode.replaceFirst("^0+(?!$)", ""));
        this.ZephyrBarcode = new SimpleStringProperty(ZBarcode);
        this.partNumber = new SimpleStringProperty(ZBarcode.substring(9, 14).replaceFirst("^0+(?!$)", ""));
        this.boxNumber = new SimpleStringProperty(ZBarcode.substring(14, 17).replaceFirst("^0+(?!$)", ""));
        this.dateProduced = new SimpleStringProperty(dProduced);
        this.BRNumber = new SimpleStringProperty(brNum);
        this.PackSlip = new SimpleStringProperty(packSlip);
        this.dateShipped = new SimpleStringProperty(dShipped);
        this.colourLotNumber = new SimpleStringProperty(cLotNum);
        this.resinLotNumber = new SimpleStringProperty(rLotNum);
        this.technician = new SimpleStringProperty(tech);
        this.message = new SimpleStringProperty("");
    }

    public TableBox(String ZBarcode, String GBarcode, String dProduced){
        this.GKPBarcode = new SimpleStringProperty(GBarcode.replaceFirst("^0+(?!$)", ""));
        this.ZephyrBarcode = new SimpleStringProperty(ZBarcode);
        this.partNumber = new SimpleStringProperty(ZBarcode.substring(9, 14).replaceFirst("^0+(?!$)", ""));
        this.boxNumber = new SimpleStringProperty(ZBarcode.substring(14, 17).replaceFirst("^0+(?!$)", ""));
        this.dateProduced = new SimpleStringProperty(dProduced);
        this.BRNumber = new SimpleStringProperty("");
        this.PackSlip = new SimpleStringProperty("");
        this.dateShipped = new SimpleStringProperty("");
        this.colourLotNumber = new SimpleStringProperty("");
        this.resinLotNumber = new SimpleStringProperty("");
        this.technician = new SimpleStringProperty("");
        this.message = new SimpleStringProperty("");
    }
    public TableBox(String ZBarcode, String GBarcode){
        this.GKPBarcode = new SimpleStringProperty(GBarcode.replaceFirst("^0+(?!$)", ""));
        this.ZephyrBarcode = new SimpleStringProperty(ZBarcode);
        this.partNumber = new SimpleStringProperty(ZBarcode.substring(9, 14).replaceFirst("^0+(?!$)", ""));
        this.boxNumber = new SimpleStringProperty(ZBarcode.substring(14, 17).replaceFirst("^0+(?!$)", ""));
        this.dateProduced = new SimpleStringProperty("");
        this.BRNumber = new SimpleStringProperty("");
        this.PackSlip = new SimpleStringProperty("");
        this.dateShipped = new SimpleStringProperty("");
        this.colourLotNumber = new SimpleStringProperty("");
        this.resinLotNumber = new SimpleStringProperty("");
        this.technician = new SimpleStringProperty("");
        this.message = new SimpleStringProperty("");
    }
    public TableBox(String ZBarcode, String GBarcode, String cLotNum, String rLotNum, String tech){
        this.GKPBarcode = new SimpleStringProperty(GBarcode.replaceFirst("^0+(?!$)", ""));
        this.ZephyrBarcode = new SimpleStringProperty(ZBarcode);
        this.partNumber = new SimpleStringProperty(ZBarcode.substring(9, 14).replaceFirst("^0+(?!$)", ""));
        this.boxNumber = new SimpleStringProperty(ZBarcode.substring(14, 17).replaceFirst("^0+(?!$)", ""));
        this.dateProduced = new SimpleStringProperty("");
        this.BRNumber = new SimpleStringProperty("");
        this.PackSlip = new SimpleStringProperty("");
        this.dateShipped = new SimpleStringProperty("");
        this.colourLotNumber = new SimpleStringProperty(cLotNum);
        this.resinLotNumber = new SimpleStringProperty(rLotNum);
        this.technician = new SimpleStringProperty(tech);
        this.message = new SimpleStringProperty("");
    }
        public TableBox(String ZBarcode, String GBarcode, String cLotNum, String rLotNum, String tech, String message){
        this.GKPBarcode = new SimpleStringProperty(GBarcode.replaceFirst("^0+(?!$)", ""));
        this.ZephyrBarcode = new SimpleStringProperty(ZBarcode);
        this.partNumber = new SimpleStringProperty(ZBarcode.substring(9, 14).replaceFirst("^0+(?!$)", ""));
        this.boxNumber = new SimpleStringProperty(ZBarcode.substring(14, 17).replaceFirst("^0+(?!$)", ""));
        this.dateProduced = new SimpleStringProperty("");
        this.BRNumber = new SimpleStringProperty("");
        this.PackSlip = new SimpleStringProperty("");
        this.dateShipped = new SimpleStringProperty("");
        this.colourLotNumber = new SimpleStringProperty(cLotNum);
        this.resinLotNumber = new SimpleStringProperty(rLotNum);
        this.technician = new SimpleStringProperty(tech);
        this.message = new SimpleStringProperty(message);
    }
    
    public TableBox(){
        this.GKPBarcode = new SimpleStringProperty("");
        this.ZephyrBarcode = new SimpleStringProperty("");
        this.partNumber = new SimpleStringProperty("");
        this.boxNumber = new SimpleStringProperty("");
        this.dateProduced = new SimpleStringProperty("");
        this.BRNumber = new SimpleStringProperty("");
        this.PackSlip = new SimpleStringProperty("");
        this.dateShipped = new SimpleStringProperty("");
        this.colourLotNumber = new SimpleStringProperty("");
        this.resinLotNumber = new SimpleStringProperty("");
        this.technician = new SimpleStringProperty("");
        this.message = new SimpleStringProperty("");
    }
    
    public String getPartNumber(){
        return partNumber.get();
    }
    public void setPartNumber(String part){
        this.partNumber.set(part);
    }
    
    public String getBoxNumber(){
        return boxNumber.get();
    }
    public void setBoxNumber(String box){
        this.boxNumber.set(box);
    }
    
    public String getGKPBarcode(){
        return GKPBarcode.get();
    }
    
    public void setGKPBarcode(String code){
        this.GKPBarcode.set(code);
    }
    
    public String getZephyrBarcode(){
        return ZephyrBarcode.get();
    }
    public void setZephyrBarcode(String code){
        this.partNumber.set(code);
    }
    
    public String getDateProduced(){
        return dateProduced.get();
    }
    public void setDateProduced(String date){
        this.dateProduced.set(date);
    }
    
    public String getBRNumber(){
        return BRNumber.get();
    }
    public void setBRNumber(String number){
        this.BRNumber.set(number);
    }
    
    public String getPackSlip(){
        return PackSlip.get();
    }
    public void setPackSlip(String packSlip){
        this.PackSlip.set(packSlip);
    }
    
    public String getDateShipped(){
        return dateShipped.get();
    }
    public void setDateShipped(String date){
        this.dateShipped.set(date);
    }
    
    public String getColourLotNumber(){
        return colourLotNumber.get();
    }
    public void setColourLotNumber(String cLotNum){
        this.colourLotNumber.set(cLotNum);
    }
    
    public String getResinLotNumber(){
        return resinLotNumber.get();
    }
    public void setResinLotNumber(String rLotNum){
        resinLotNumber.set(rLotNum);
    }
    
    public String getTechnician(){
        return technician.get();
    }
    public void setTechnician(String tech){
        technician.set(tech);
    }
    public String getMessage(){
        return message.get();
    }
    public void setMessage(String msg){
        message.set(msg);
    }
}
