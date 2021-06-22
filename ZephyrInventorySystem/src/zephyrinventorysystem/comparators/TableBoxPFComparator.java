/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zephyrinventorysystem.comparators;
import java.util.Comparator;
import zephyrinventorysystem.TableBox;
/**
 *
 * @author Spenc
 */
public class TableBoxPFComparator implements Comparator<TableBox>{
    @Override
    public int compare (TableBox box1, TableBox box2){
        return (box1.getZephyrBarcode().substring(4, 6) + "00" + box1.getZephyrBarcode().substring(6,9)).compareTo(box2.getZephyrBarcode().substring(4, 6) + "00" + box2.getZephyrBarcode().substring(6,9));
    }
}
