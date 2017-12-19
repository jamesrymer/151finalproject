/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whiteboard;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
public class TableModel extends AbstractTableModel implements ModelListener {
	public static ArrayList<ArrayList<DShapeModel>> array = new ArrayList<ArrayList<DShapeModel>>();
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 6;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		//this.
		return null;
	}
	@Override
	public void ModelChanged(DShapeModel model) {
		for(ArrayList<DShapeModel> arr : array) {
			if(arr.contains(model)) {
				this.fireTableRowsUpdated(arr.indexOf(model),arr.indexOf(model));
			}
		}
		
	}

}