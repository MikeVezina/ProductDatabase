/**
 * 
 */
package productdatabase;

import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;

/**
 * @author MVezina
 */
public class RetailerInfo implements Disposable
{
	private Retailer retailer;
	private double price;
	private String stock;
	private String productURL;
	private double productRating;

	public RetailerInfo(Retailer retailer, double price, String stock, String productURL, double productRating)
	{
		this.setRetailer(retailer);
		this.setPrice(price);
		this.setStock(stock);
		this.setProductURL(productURL);
		this.setProductRating(productRating);
	}

	public Retailer getRetailer()
	{
		return retailer;
	}

	public void setRetailer(Retailer retailer)
	{
		this.retailer = retailer;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public String getStock()
	{
		return stock;
	}

	public void setStock(String stock)
	{
		this.stock = stock;
	}

	public String getProductURL()
	{
		return productURL;
	}

	public void setProductURL(String productURL)
	{
		this.productURL = productURL;
	}

	public double getProductRating()
	{
		return productRating;
	}

	public void setProductRating(double productRating)
	{
		this.productRating = productRating;
	}

	@Override
	public void dispose()
	{
		/* Currently Nothing to Dispose of. But still needs to implement
		 * interface method */
	}
}
