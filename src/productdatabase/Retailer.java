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
	private String name;
	private String website;

	public Retailer(int retailerID, String retailerName, String retailerWebsite)
	{
		this.allProducts = new ArrayList<>();
		this.retailerID = retailerID;
		this.setName(retailerName);
		this.setWebsite(retailerWebsite);
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

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		if (name == null)
			return;

		this.name = name;
	}

	public String getWebsite()
	{
		return website;
	}

	public void setWebsite(String website)
	{
		if (website == null)
			return;

		this.website = website;
	}

	@Override
	public String toString()
	{
		return "Retailer ID: " + this.retailerID + ". Retailer Name: " + this.name + ". Number of Products: " + this.allProducts.size();
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
