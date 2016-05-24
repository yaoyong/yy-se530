package edu.olivet.se530;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.olivet.se530.annotations.Profile;
import edu.olivet.se530.model.Condition;
import edu.olivet.se530.model.Offer;
import edu.olivet.se530.model.Seller;

public class HtmlParser {
	
	@Profile(desc = "解析一个给定的html document，返回其中的offer列表")
	public List<Offer> parseOffer(Document doc) {
		List<Offer> results = new ArrayList<Offer>(); 
		Elements rows = doc.select("div.a-row.a-spacing-mini.olpOffer");
		for (int i = 0; i < rows.size(); i++) {
			Element row = rows.get(i);
			Offer offer = new Offer();
			
			offer.setPrice(Float.parseFloat(this.getText(row, "span.olpOfferPrice").replace("$", "")));
			String shippingFeeText = this.getText(row, "span.olpShippingPrice").replace("$", "");
			
			if (shippingFeeText != null && shippingFeeText.trim().length() > 0) {
				offer.setShippingPrice(Float.parseFloat(shippingFeeText));
			}
			
			Seller seller = this.parseSeller(row);
			Condition condition = parseCondition(row);
			offer.setSeller(seller);
			offer.setCondition(condition);
			
			results.add(offer);
		}
		
		return results;
	}

	@Profile
	public Condition parseCondition(Element row) {
		String cond = this.getText(row, "h3.a-spacing-small.olpCondition");	
		Condition condition = new Condition();
		
		if(cond.trim().equals("New")){
			condition.setPrimary("New");
			condition.setSecondary("");
		}
		else{
			String[] array = cond.split("-");
			String primarystr = array[0].trim();
			String secondarystr = array[1].trim();
			condition.setPrimary(primarystr);
			condition.setSecondary(secondarystr);
		}

		return condition;
	}

	@Profile
	public Seller parseSeller(Element row) {
		Seller seller = new Seller();
		String sellerNameSelector = "p.a-spacing-small.olpSellerName";
		seller.setName(this.getText(row, sellerNameSelector));	

//		System.out.println("getnamelink=================="+ seller.getName());
		
		String link="";

		if(!(seller.getName().equals(""))){
			link = row.select(sellerNameSelector + " a").get(0).attr("href");
			seller.setUuid(link.replaceFirst(".*&seller=", ""));
			String ratingText = this.getText(row, "p.a-spacing-small > a > b");
			int rating = Integer.parseInt(ratingText.replaceAll("[^0-9]", ""));
			seller.setRating(rating);
			
			String ratingCountText = this.getText(row, "div.a-column.a-span2.olpSellerColumn > p:nth-child(2)");
			ratingCountText = ratingCountText.substring(ratingCountText.indexOf('('), ratingCountText.indexOf(')')).replaceAll("[^0-9]", "");
			seller.setRatingCount(Integer.parseInt(ratingCountText));
		}else{
			seller.setName("AP");
			seller.setRating(95);
			seller.setRatingCount(100);
		}

		

		
		Elements deliveries = row.select("ul.a-vertical > li > span.a-list-item");
		for (int j = 0; j < deliveries.size(); j++) {
			String text = deliveries.get(j).text();
			if (text.contains("Expedited shipping available")) {
				seller.setExpeditedShippingAvailable(true);
			} else if (text.contains("International & domestic shipping rates and return policy")) {
				seller.setIntlShippingAvailable(true);
			} else if (text.matches("Ships from [A-Z]{2}, United States[.]")) {
				String[] array = text.replace("Ships from", "").split(",");
				seller.setShippingState(array[0].trim());
				seller.setShippingCountry(array[1].trim().replace(".", ""));
			}else if (text.contains("Ships from United Kingdom.")){
				seller.setShippingCountry("uk"); 
			}
		}
		return seller;
	}

	@Profile
	public String getText(Element element, String selector) {
		Elements elements = element.select(selector);
		if (elements.size() <= 0) {
			return "";
		}
		return elements.get(0).text();
	}
	
}
