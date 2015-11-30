/**
 * 
 */
package productdatabase.gui;

import java.sql.*;
import java.util.*;

import javax.swing.*;

import productdatabase.*;

/**
 * @author MVezina
 */
public class ProductDatabaseGUI extends JFrame
{
	private ResultsTable resultsTable;
	private ProductDatabase productDatabase;

	/* Filter Panel Components */
	private JPanel searchFilterPanel;
	
	
	private JPanel manufacturerSearchFilterPanel;
	private JComboBox<Manufacturer> manufacturerComboBox;
	
	

	public ProductDatabaseGUI() throws Exception
	{
		super("Product Database");
		productDatabase = new ProductDatabase();

		InitializeComponents();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.pack();
		this.setVisible(true);
	}

	/**
	 * @author MVezina
	 */
	private void InitializeComponents() throws Exception
	{
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		// Set up filter components
		
		manufacturerSearchFilterPanel = new JPanel();
		manufacturerSearchFilterPanel.setBorder(BorderFactory.createTitledBorder("Filter By Manufacturer"));
		
		
		
		manufacturerComboBox = new JComboBox<>();
		for(Manufacturer m : productDatabase.getAllManufacturers())
			manufacturerComboBox.addItem(m);
		
		manufacturerSearchFilterPanel.add(manufacturerComboBox);
		
		
		
		// Initialize the search filter panel
		searchFilterPanel = new JPanel();
		searchFilterPanel.setLayout(new BoxLayout(searchFilterPanel, BoxLayout.X_AXIS));
		searchFilterPanel.setBorder(BorderFactory.createTitledBorder("Filter Options"));
		
		searchFilterPanel.add(manufacturerSearchFilterPanel);
		
		
		
		
		

		// Initialize the results table
		resultsTable = new ResultsTable();

		// Add the components to the frame
		this.add(searchFilterPanel);
		this.add(new JScrollPane(resultsTable));

	}

	private void postQuery(String query) throws SQLException
	{
		ResultSet rs = productDatabase.getResults("SELECT * FROM ((sold_by NATURAL JOIN products) NATURAL JOIN manufacturers) NATURAL JOIN retailers");

		while (rs.next())
		{
			ArrayList<Object> rowData = new ArrayList<>();
			rowData.add(rs.getString(Product.AttributeName.NAME_ATTR.toString()));
			rowData.add(rs.getString(Manufacturer.AttributeName.NAME_ATTR.toString()));
			rowData.add(rs.getString(Retailer.AttributeName.NAME_ATTR.toString()));
			rowData.add(rs.getString(Product.AttributeName.PRICE_ATTR.toString()));
			rowData.add(rs.getString(Product.AttributeName.STOCK_ATTR.toString()));
			rowData.add(rs.getString(Product.AttributeName.URL_ATTR.toString()));
			rowData.add(rs.getString(Product.AttributeName.RATING_ATTR.toString()));

			resultsTable.getDefaultModel().addRow(rowData.toArray());

		}
	}

}
