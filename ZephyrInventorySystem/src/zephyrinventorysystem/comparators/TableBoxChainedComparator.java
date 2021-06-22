/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zephyrinventorysystem.comparators;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import zephyrinventorysystem.TableBox;
/**
 *
 * @author Spenc
 */
public class TableBoxChainedComparator implements Comparator<TableBox>{
    private List<Comparator<TableBox>> listComparators;
    
    @SafeVarargs
    public TableBoxChainedComparator(Comparator<TableBox> ... comparators){
        this.listComparators = Arrays.asList(comparators);
    }
    @Override
    public int compare(TableBox box1, TableBox box2){
        for (Comparator<TableBox> comparator : listComparators){
            int result = comparator.compare(box1, box2);
            if (result != 0){
                return result;
            }
        }
        return 0;
    }
}
