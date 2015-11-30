/**
 * 
 */
package productdatabase;

import java.sql.*;
import java.util.*;

import productdatabase.gui.ProductDatabaseGUI;

/**
 * @author MVezina
 */
public class ProductDatabase
{

	private List<Manufacturer> manufacturers;
	private List<Product> products;
	private List<Retailer> retailers;

	
	private Statement stat;
	public ProductDatabase()
	{
		// Initialize lists
		manufacturers = new ArrayList<>();
		products = new ArrayList<>();
		retailers = new ArrayList<>();

		try
		{
			retrieveDatabaseInformation();

		} catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}

	}

	public ResultSet getResults(String query)
	{
		try
		{
			ResultSet rs =  stat.executeQuery(query);
			
			return rs;
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * @author MVezina
	 */
	public void retrieveDatabaseInformation() throws Exception
	{
		// Load the JDBC Class
		Class.forName("org.sqlite.JDBC");

		// Create database connection
		Connection conn = DriverManager.getConnection("jdbc:sqlite:ProductData.db");

		// Create a statement from the database connection
		stat = conn.createStatement();

		// Gather all information from the database
		populateDatabaseLists(stat);
		populateDatabaseLists(stat);
	}
	
	
	

	/**
	 * Populates The database lists
	 * 
	 * @author MVezina
	 */
	public void populateDatabaseLists(Statement stat) throws Exception
	{

		/* Dispose of all present lists */
		for (Manufacturer m : manufacturers)
		{
			m.dispose();
		}

		for (Product p : products)
		{
			p.dispose();
		}

		for (Retailer r : retailers)
		{
			r.dispose();
		}

		// Clear all lists
		manufacturers.clear();
		products.clear();
		retailers.clear();

		retrieveAllManufacturers(stat);
		retrieveAllRetailers(stat);
		retrieveAllProducts(stat);
	}
	
	

	/**
	 * Obtains all manufacturers from the database and adds them to the list of
	 * manufacturers
	 * 
	 * @param stat The Statement to execute the query on
	 * @author MVezina
	 */
	private void retrieveAllManufacturers(Statement stat) throws Exception
	{
		// Obtain all manufacturer information
		String query = "SELECT * FROM manufacturers";
		ResultSet rs = stat.executeQuery(query);

		// Iterate through the results and add each manufacturer to the List
		while (rs.next())
		{
			manufacturers.add(new Manufacturer(rs.getInt(Manufacturer.AttributeName.ID_ATTR.toString()), rs.getString(Manufacturer.AttributeName.NAME_ATTR.toString())));
		}

		// Close the results set
		rs.close();
	}

	/**
	 * @param stat The statemenet to execute the query on
	 * @author MVezina
	 */
	private void retrieveAllProducts(Statement stat) throws Exception
	{
		// Obtain all manufacturer information
		String query = "SELECT * FROM products";
		ResultSet rs = stat.executeQuery(query);

		// Iterate through the results and add each manufacturer to the List
		while (rs.next())
		{
			// Obtain the product information
			Manufacturer prodManufacturer = getManufacturerByID(rs.getInt(Manufacturer.AttributeName.ID_ATTR.toString()));
			products.add(new Product(prodManufacturer, rs.getInt(Product.AttributeName.ID_ATTR.toString()), rs.getString(Product.AttributeName.NAME_ATTR.toString()), rs.getString(Product.AttributeName.RELEASE_DATE_ATTR.toString())));
		}

		// Close the results set
		rs.close();

		// Now we populate the products with retailer information
		retrieveAllProductRetailerInformation(stat);
	}

	private void retrieveAllProductRetailerInformation(Statement stat) throws Exception
	{
		// Obtain all manufacturer information
		String query = "SELECT * FROM sold_by";
		ResultSet rs = stat.executeQuery(query);

		// Iterate through the results and add each Retailer Info
		while (rs.next())
		{

			// Obtain the product information
			Manufacturer prodManufacturer = getManufacturerByID(rs.getInt(Manufacturer.AttributeName.ID_ATTR.toString()));
			Retailer prodRetailer = getRetailerByID(rs.getInt(Retailer.AttributeName.ID_ATTR.toString()));
			Product product = getProductByID(rs.getInt(Product.AttributeName.ID_ATTR.toString()), prodManufacturer);

			// Create the retailer information
			RetailerInfo retailerInfo = new RetailerInfo(prodRetailer, rs.getDouble(Product.AttributeName.PRICE_ATTR.toString()), rs.getString(Product.AttributeName.STOCK_ATTR.toString()), rs.getString(Product.AttributeName.URL_ATTR.toString()), rs.getDouble(Product.AttributeName.RATING_ATTR.toString()));

			// Add Retailer Info to the product's retailer info list
			product.addRetailerInfo(retailerInfo);

		}

		// Close the results set
		rs.close();

	}
	
	public List<Product> getAllProducts()
	{
		return this.products;
	}
	
	public List<Manufacturer> getAllManufacturers()
	{
		return this.manufacturers;
	}
	
	public List<Retailer> getAllRetailers()
	{
		return this.retailers;
	}
	

	/**
	 * Adds retailers in database to Retailers List
	 * 
	 * @param stat The statement to execute the query on
	 * @author MVezina
	 */
	private void retrieveAllRetailers(Statement stat) throws Exception
	{
		// Obtain all manufacturer information
		String query = "SELECT * FROM retailers";
		ResultSet rs = stat.executeQuery(query);

		// Iterate through the results and add each manufacturer to the List
		while (rs.next())
		{
			retailers.add(new Retailer(rs.getInt(Retailer.AttributeName.ID_ATTR.toString()), rs.getString(Retailer.AttributeName.NAME_ATTR.toString()), rs.getString(Retailer.AttributeName.WEBSITE_ATTR.toString())));
		}

		// Close the results set
		rs.close();
	}

	/**
	 * Obtains the Manufacturer with the matching manufacturerID
	 * 
	 * @param manufacturerID The ID of the manufacturer to get
	 * @return The Manufacturer that matches the manufacturerID
	 * @author MVezina
	 */
	public Manufacturer getManufacturerByID(int manufacturerID)
	{
		for (Manufacturer m : manufacturers)
		{
			if (m.getManufacturerID() == manufacturerID)
				return m;
		}

		return null;
	}

	/**
	 * Obtains the Product with the matching ProductID from the Manufacturer
	 * 
	 * @param productID The ID of the product to get
	 * @param manufacturerID The ID of the manufacturer to search
	 * @return The Product (Produced by Manufacturer) that matches the productID
	 * @author MVezina
	 */
	public Product getProductByID(int productID, Manufacturer manufacturer)
	{
		if (manufacturer == null)
			return null;

		for (Product p : manufacturer.getProducts())
		{
			if (p.getProductID() == productID)
			{
				return p;
			}
		}

		return null;
	}

	/**
	 * Obtains the Retailer with the matching retailerID from the Retailer List
	 * 
	 * @param retailerID The ID of the retailer to get
	 * @return The Retailer that matches the retailerID
	 * @author MVezina
	 */
	public Retailer getRetailerByID(int retailerID)
	{
		for (Retailer r : retailers)
		{
			if (r.getRetailerID() == retailerID)
			{
				return r;
			}
		}

		return null;
	}

	/**
	 * Get the product that matches the productID from the manufacturer with
	 * manufacturerID
	 * 
	 * @param productID The ID of the product to get
	 * @param manufacturerID The ID of the manufacturer to search
	 * @return The product that matches the product ID from the manufacturer
	 *         with the manufacturer ID
	 * @author MVezina
	 */
	public Product getProductByID(int productID, int manufacturerID)
	{
		return getProductByID(productID, getManufacturerByID(manufacturerID));
	}

	public void PrintAllData()
	{
		System.out.println("Manufacturers:");

		// Print all manufacturers
		for (Manufacturer m : manufacturers)
		{
			System.out.println(m.toString());
		}

		System.out.println();
		System.out.println("Retailers");

		// Print all Retailers
		for (Retailer r : retailers)
		{
			System.out.println(r.toString());
		}

		System.out.println();
		System.out.println("Products");

		// Print all Retailers
		for (Product p : products)
		{
			System.out.println(p.toString());
		}

	}

	public static void main(String[] args) throws Exception
	{
		
		
		new ProductDatabaseGUI();
	}

}
