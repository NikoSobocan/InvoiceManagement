package InvoiceManagement;

import java.util.*;
import java.math.BigDecimal;
import org.joda.time.*;


public class Receipt implements Searchable{
	
	private UUID uuid;
	private DateTime date; //TODO LocalDateTime
	private Company issuer;
	private Company customer;
	private BigDecimal total;
	private BigDecimal totalTax;
	private String taxNumber;

	private List<Article> articlesReceipt = new ArrayList<Article>();
	
	
	
	public Company getCustomer() {
		return customer;
	}


	public void setCustomer(Company customer) {
		this.customer = customer;
		this.taxNumber = customer.getTaxNumber();
	}



	public Receipt(Articles articles) {
		
		this.articlesReceipt = articles.getArticleList();
		this.date = DateTime.now();
		this.total = articles.getCollectivePrice();
		this.totalTax = articles.getCollectiveTax();
		this.uuid = UUID.randomUUID();
		this.customer = null;
		this.taxNumber = null;
		
	}

	public Receipt() {
	}


	public UUID getUuid() {
		return uuid;
	}
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public BigDecimal getTotalTax() {
		return totalTax;
	}
	public void setTotalTax(BigDecimal totalTax) {
		this.totalTax = totalTax;
	}

	public List<Article> getArticlesReceipt() {
		return articlesReceipt;
	}

	public void setArticlesReceipt(List<Article> articlesReceipt) {
		this.articlesReceipt = articlesReceipt;
	}

	public void getArticleList(Articles articles) {
		
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

	public Company getIssuer() {
		return issuer;
	}

	public void setIssuer(Company issuer) {
		this.issuer = issuer;
	}

	public String getTaxNumber() {
		return taxNumber;
	}

	public void setTaxNumber(String taxNumber) {
		this.taxNumber = taxNumber;
	}
	
	public void printArticles() {
		System.out.println(this.articlesReceipt.size());
		for(int i = 0; i < this.articlesReceipt.size(); i++) {
			System.out.println(this.articlesReceipt.get(i).getName());
		}
	}

	


	public boolean search(String hasString) {
		
		if(hasString.equals(this.issuer) || 
				hasString.equals(String.valueOf(this.taxNumber)) || hasString.equals(this.date.toString()))
		{
			return true;
		}
		else {
			return false;
		}
	}
	
}
