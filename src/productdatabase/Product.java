/**
 * 
 */
package productdatabase;

import java.util.*;

import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;

/**
 * @author MVezina
 */
public class Product implements Disposable
{

	public static enum AttributeName
	{
		ID_ATTR("productID"),
		RATING_ATTR("productRating"),
		URL_ATTR("productURL"),
		STOCK_ATTR("stock"),
		PRICE_ATTR("price"),
		RELEASE_DATE_ATTR("releaseDate"),
		NAME_ATTR("productName");

		private String attrName;

		private AttributeName(String attributeName)
		{
			this.attrName = attributeName;
		}

		public String toString()
		{
			return this.attrName;
		}
	}

	private Manufacturer manufacturer;

	private int productID;
	private String productName;
	private String releaseDate;

	private List<RetailerInfo> retailerInfoList;

	public Product(Manufacturer manufacturer, int productID, String name, String releaseDate)
	{
		this.retailerInfoList = new ArrayList<>();
		this.manufacturer = manufacturer;

		// Add this product to the manufacturer's product list
		this.manufacturer.addProduct(this);

		// Set Product Attributes
		this.productID = productID;
		this.productName = name;
		this.releaseDate = releaseDate;

	}

	public int getProductID()
	{
		return productID;
	}

	public void addRetailerInfo(RetailerInfo retailerInfo)
	{
		// Ensure the retailer info does not exist & is valid (not null)
		if (retailerInfo == null || this.retailerInfoList.contains(retailerInfo))
			return;

		// Do not allow more than one retailer info object for every product /
		// retailer combo
		for (RetailerInfo rI : this.retailerInfoList)
		{
			if (rI.getRetailer().getRetailerID() == retailerInfo.getRetailer().getRetailerID())
				return;
		}

		// Add retailer info to the list
		this.retailerInfoList.add(retailerInfo);

		// Add this product to the retailers product list
		retailerInfo.getRetailer().addProduct(this);

	}

	/**
	 * @return The List of all retailer information
	 */

	public List<RetailerInfo> getRetailerInfoList()
	{
		return this.retailerInfoList;
	}

	/**
	 * Gets RetailerInfo given the retailerID
	 * 
	 * @param retailerID The ID of the retailer to find retailer info
	 * @return The retailer info with the retailer that matches the retailerID
	 * @author MVezina
	 */
	public RetailerInfo getRetailerInfo(int retailerID)
	{
		for (RetailerInfo rI : retailerInfoList)
		{
			if (rI.getRetailer().getRetailerID() == retailerID)
			{
				return rI;
			}
		}

		return null;
	}

	public Manufacturer getManufacturer()
	{
		return this.manufacturer;
	}

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String name)
	{
		if (name == null)
			return;

		this.productName = name;
	}

	public String getReleaseDate()
	{
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate)
	{
		if (releaseDate == null)
			return;

		this.releaseDate = releaseDate;
	}

	public String toString()
	{
		String retStr = "";
		retStr += "Product ID: " + this.productID + "\n";
		retStr += "Manufacturer ID: " + this.manufacturer.getManufacturerID() + " (" + this.manufacturer.getManufacturerName() + ")\n";
		retStr += "Product Name: " + this.productName + "\n";
		retStr += "Product Release Date: " + this.releaseDate + "\n";
		retStr += "Number of Retailers: " + this.retailerInfoList.size() + "\n";

		return retStr;
	}

	
	@Override
	public void dispose()
	{
		for(RetailerInfo rI : retailerInfoList)
		{
			rI.dispose();
		}
		
		retailerInfoList.clear();
	}

}
