package edu.olivet.se530.model;

/**
 * 亚马逊某种产品Offer，包含了定价、运费、商家等信息
 * <a href="mailto:nathanaelibport@gmail.com">Nathanael Yang</a> Jan 14, 2015 10:01:53 AM
 * @version 1.0
 */
public class Offer implements Comparable<Offer> {
	@Override
	public String toString() {
		return "Offer [seller=" + seller + ", product=" + product + ", condition=" + condition + ", price=" + price + ", shippingPrice="
				+ shippingPrice + "]";
	}

	private Seller seller;
	private Product product;
	private Condition condition;
	private float price;
	private float shippingPrice;
	private String shippingCountry;
	
	public Seller getSeller() {		
		return seller;
	}
	public void setSeller(Seller seller) {
		this.seller = seller;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getShippingPrice() {
		return shippingPrice;
	}
	public void setShippingPrice(float shippingPrice) {
		this.shippingPrice = shippingPrice;
	}
	public Condition getCondition() {
		return condition;
	}
	public void setCondition(Condition condition) {
		this.condition = condition;
	}
	
	public String getShippingCountry() {
		return shippingCountry;
	}
	
	public void setShippingCountry(String shippingCountry) {
		this.shippingCountry = shippingCountry;
	}
	
	@Override
	public int compareTo(Offer o) {
		int rc = Float.compare(this.price, o.price);
		if (rc == 0) {
			rc = Float.compare(this.shippingPrice, o.shippingPrice);
			if (rc == 0) {
				rc = -Integer.compare(this.getSeller().getRating(), o.getSeller().getRating());
				if (rc == 0) {
					return -Integer.compare(this.getSeller().getRatingCount(), o.getSeller().getRatingCount());
				}
			}
		}
		return rc;
	}
}
