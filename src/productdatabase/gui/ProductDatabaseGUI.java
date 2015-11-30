/**
 * 
 */
package productdatabase.gui;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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

	private List<String> colAttributes;

	/* Filter Panel Components */
	private JPanel searchFilterPanel;

	private JPanel manufacturerSearchFilterPanel;
	private JComboBox<Manufacturer> manufacturerComboBox;

	private JPanel retailerSearchFilterPanel;
	private JComboBox<Retailer> retailerComboBox;

	private JPanel productSearchPanel;
	private JTextField productSearchField;
	private JButton productSearchButton;
	
	private JPanel sortOptionsPanel;
	private ButtonGroup sortButtonGroup;
	private JRadioButton nameSortRadioButton;
	private JRadioButton manufacturerSortRadioButton;
	private JRadioButton retailerSortRadioButton;
	private JRadioButton priceSortRadioButton;
	private JRadioButton ratingSortRadioButton;

	public ProductDatabaseGUI() throws Exception
	{
		super("Product Database");

		productDatabase = new ProductDatabase();

		colAttributes = new ArrayList<>();
		// Set up the column attributes
		colAttributes.add(Product.AttributeName.NAME_ATTR.toString());
		colAttributes.add(Manufacturer.AttributeName.NAME_ATTR.toString());
		colAttributes.add(Retailer.AttributeName.NAME_ATTR.toString());
		colAttributes.add(Product.AttributeName.PRICE_ATTR.toString());
		colAttributes.add(Product.AttributeName.STOCK_ATTR.toString());
		colAttributes.add(Product.AttributeName.URL_ATTR.toString());
		colAttributes.add(Product.AttributeName.RELEASE_DATE_ATTR.toString());
		colAttributes.add(Product.AttributeName.RATING_ATTR.toString());
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
		this.setMinimumSize(new Dimension(700, 450));
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		// Set up filter components
		productSearchPanel = new JPanel();
		productSearchField = new JTextField();
		productSearchButton = new JButton("Search");
		searchFilterPanel = new JPanel();
		manufacturerComboBox = new JComboBox<>();
		manufacturerSearchFilterPanel = new JPanel();
		retailerComboBox = new JComboBox<>();
		retailerSearchFilterPanel = new JPanel();

		// Initialize the product Search Panel
		productSearchPanel.setBorder(BorderFactory.createTitledBorder("Search Product Name"));
		productSearchPanel.setLayout(new BoxLayout(productSearchPanel, BoxLayout.X_AXIS));
		
		searchFilterPanel.setMaximumSize(new Dimension(searchFilterPanel.getMaximumSize().width, searchFilterPanel.minimumSize().height ));
		productSearchPanel.add(productSearchField);
		
		
		
		
		productSearchButton.addActionListener(al -> updateQuery());
		productSearchPanel.add(productSearchButton);

		// Initialize the manufacturer combo box

		// Populate the manufacturer combo box
		manufacturerComboBox.addItemListener(il -> updateQuery());
		manufacturerComboBox.addItem(new Manufacturer(-1, "All Manufacturers"));		
		for (Manufacturer m : productDatabase.getAllManufacturers())
			manufacturerComboBox.addItem(m);

		

		// Create the Manufacturer Search panel with a titled border
		manufacturerSearchFilterPanel.setBorder(BorderFactory.createTitledBorder("Filter By Manufacturer"));
		manufacturerSearchFilterPanel.add(manufacturerComboBox);


		// Populate the manufacturer combo box
		retailerComboBox.addItemListener(il -> updateQuery());
		retailerComboBox.addItem(new Retailer(-1, "All Retailers", ""));
		for (Retailer r : productDatabase.getAllRetailers())
			retailerComboBox.addItem(r);

		

		// Create the Retailer Search panel with a titled border
		retailerSearchFilterPanel.setBorder(BorderFactory.createTitledBorder("Filter By Retailer"));
		retailerSearchFilterPanel.add(retailerComboBox);

		// Initialize the search filter panel
		searchFilterPanel.setLayout(new BoxLayout(searchFilterPanel, BoxLayout.X_AXIS));
		searchFilterPanel.setBorder(BorderFactory.createTitledBorder("Filter Options"));

		searchFilterPanel.add(productSearchPanel);
		searchFilterPanel.add(manufacturerSearchFilterPanel);
		searchFilterPanel.add(retailerSearchFilterPanel);

		// Initialize the results table
		resultsTable = new ResultsTable();

		// Populate the initial query table
		postQuery();

		// Add the components to the frame
		this.add(searchFilterPanel);
		this.add(new JScrollPane(resultsTable));

	}

	/**
	 * @return
	 * @author MVezina
	 * @throws SQLException
	 */
	private void updateQuery()
	{

		System.out.println(this.size());
		System.out.println(this.searchFilterPanel.getSize());
		try
		{
			postQuery();
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private String getManufacturerSearchFilter()
	{
		Manufacturer m = (Manufacturer) manufacturerComboBox.getSelectedItem();
		if (m.getManufacturerID() == -1)
			return ("");
		else
			return ("manufacturers.manufacturerID=" + m.getManufacturerID());

	}

	private String getReailerSearchFilter()
	{
		Retailer r = (Retailer) retailerComboBox.getSelectedItem();

		if (r.getRetailerID() == -1)
			return ("");
		else
			return ("retailers.retailerID=" + r.getRetailerID());

	}

	private void postQuery() throws SQLException
	{
		String baseQuery = "SELECT * FROM (((sold_by NATURAL JOIN products) NATURAL JOIN manufacturers) NATURAL JOIN retailers)";

		Connection c = productDatabase.getDatabaseConnection();

		String manufacturerFilter = getManufacturerSearchFilter();
		String retailerFilter = getReailerSearchFilter();

		if (!manufacturerFilter.isEmpty())
		{
			if (!baseQuery.toLowerCase().contains("where"))
				baseQuery += " WHERE ";
			else
				baseQuery += " AND ";

			baseQuery += manufacturerFilter;
		}

		if (!retailerFilter.isEmpty())
		{
			if (!baseQuery.toLowerCase().contains("where"))
				baseQuery += " WHERE ";
			else
				baseQuery += " AND ";

			baseQuery += retailerFilter;
		}

		ResultSet rs = null;

		boolean useSearchFilter = false;

		if (!productSearchField.getText().trim().isEmpty())
		{
			if (!baseQuery.toLowerCase().contains("where"))
				baseQuery += " WHERE ";
			else
				baseQuery += " AND ";

			baseQuery += "products.productName LIKE ?";

			useSearchFilter = true;

		}

		PreparedStatement ps = c.prepareStatement(baseQuery);

		if (useSearchFilter)
			ps.setString(1, "%" + productSearchField.getText().trim() + "%");

		rs = ps.executeQuery();

		// Clear the table
		resultsTable.clear();
		resultsTable.repaint();
		if (rs == null)
			return;

		while (rs.next())
		{
			ArrayList<Object> rowData = new ArrayList<>();

			for (String s : this.colAttributes)
			{
				if (hasColumnName(rs, s))
				{
					rowData.add(rs.getString(s));
				}
			}

			resultsTable.getDefaultModel().addRow(rowData.toArray());

		}
	}

	/**
	 * Used to determine if a result set contains a column with the specified
	 * name
	 * 
	 * @param rs The result set to check
	 * @param colName The column name to check for
	 * @return Whether or not the result set has a column name colName
	 * @throws SQLException
	 * @author MVezina
	 */
	private boolean hasColumnName(ResultSet rs, String colName) throws SQLException
	{
		for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
		{
			if (rs.getMetaData().getColumnName(i).equals(colName))
				return true;
		}
		return false;
	}

}
