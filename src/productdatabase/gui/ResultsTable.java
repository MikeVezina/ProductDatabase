/**
 * 
 */
package productdatabase.gui;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 * @author MVezina
 */
public class ResultsTable extends JTable implements TableCellRenderer
{

	private static String[] columnData = new String[] { "Product Name", "Manufacturer", "Retailer", "Price", "Stock", "Product URL", "Release Date" };

	protected DefaultTableModel tableModel;
	
	public ResultsTable()
	{
		super();
		this.tableModel = new DefaultTableModel(columnData, 0);
		this.setModel(tableModel);
		this.setFillsViewportHeight(true);
		this.setShowGrid(true);
		this.setShowHorizontalLines(true);
		this.setShowVerticalLines(true);	
		this.setDefaultRenderer(String.class, this);

	}

	public DefaultTableModel getDefaultModel()
	{
		return this.tableModel;
	}
	
	@Override
	public boolean isCellEditable(int row, int column)
	{
		return false;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
		return (new DefaultTableCellRenderer()).getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}

}
