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
public class TableBoxBoxComparator implements Comparator<TableBox>{
    @Override
    public int compare (TableBox box1, TableBox box2){
        return box1.getBoxNumber().compareTo(box2.getBoxNumber());
    }
}
