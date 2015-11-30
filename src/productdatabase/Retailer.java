/**
 * 
 */
package productdatabase;

import java.util.*;

import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;

/**
 * @author MVezina
 */
public class Retailer implements Disposable
{
	public static enum AttributeName
	{
		NAME_ATTR("retailerName"),
		WEBSITE_ATTR("website"),
		ID_ATTR("retailerID");

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

	private List<Product> allProducts;

	private int retailerID;
	private String retailerName;
	private String website;

	public Retailer(int retailerID, String retailerName, String retailerWebsite)
	{
		this.allProducts = new ArrayList<>();
		this.retailerID = retailerID;
		this.retailerName = (retailerName);
		this.website = (retailerWebsite);
	}

	public void addProduct(Product product)
	{
		if (this.allProducts.contains(product) || product == null)
			return;

		// We can only add the product if we are listed under the retailer info
		// for the product
		if (product.getRetailerInfo(this.retailerID) != null)
		{
			this.allProducts.add(product);
		}

	}

	public List<Product> getProducts()
	{
		return this.allProducts;
	}

	public int getRetailerID()
	{
		return retailerID;
	}

	public String getRetailerName()
	{
		return retailerName;
	}

	public String getWebsite()
	{
		return website;
	}

	@Override
	public String toString()
	{
		return this.retailerName + " (ID: " + this.retailerID + ")";
	}


	@Override
	public void dispose()
	{
		for(Product p : allProducts)
		{
			p.dispose();
		}
		
	}

}
