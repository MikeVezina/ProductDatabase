/**
 * 
 */
package productdatabase;

import java.util.*;

import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;

/**
 * @author MVezina
 */
public class Manufacturer implements Disposable
{

	public static enum AttributeName
	{
		ID_ATTR("manufacturerID"),
		NAME_ATTR("manufacturerName");

		private String attrName;

		private AttributeName(String attributeName)
		{
			this.attrName = attributeName;
		}

		@Override
		public String toString()
		{
			return this.attrName;
		}
	}

	private List<Product> allProducts;
	private int manufacturerID;
	private String manufacturerName;

	public Manufacturer(int manufacturerID, String manufacturerName)
	{
		this.allProducts = new ArrayList<>();
		this.manufacturerID = manufacturerID;
		this.manufacturerName = manufacturerName;
	}

	public void addProduct(Product product)
	{
		if (this.allProducts.contains(product) || product == null)
			return;

		this.allProducts.add(product);
	}

	public List<Product> getProducts()
	{
		return this.allProducts;
	}

	public int getManufacturerID()
	{
		return manufacturerID;
	}

	public String getManufacturerName()
	{
		return manufacturerName;
	}


	@Override
	public String toString()
	{
		return "Manufacturer ID: " + this.manufacturerID + ". Manufacturer Name: " + this.manufacturerName + ". Number of Products: " + this.allProducts.size();
	}

	
	@Override
	public void dispose()
	{
		for(Product p : allProducts)
		{
			p.dispose();
		}
		
		allProducts.clear();
		
	}

}
