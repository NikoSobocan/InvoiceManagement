package InvoiceManagement;
import java.util.*;
import java.math.BigDecimal;
import com.google.gson.*;

public class Articles extends Article implements JsonSupport{
	
	private List<Article> articleList = new ArrayList<Article>();
	private BigDecimal collectivePrice = BigDecimal.ZERO;
	private BigDecimal collectiveTax = BigDecimal.ZERO;
	



	public BigDecimal getCollectivePrice() {
		return this.collectivePrice;
	}

	public void addToList(Article articleToAdd) {
		this.articleList.add(articleToAdd);
		this.collectivePrice = collectivePrice.add(articleToAdd.getPrice());
		BigDecimal temp = articleToAdd.getTax();
		this.collectiveTax = collectiveTax.add(articleToAdd.getTax());
		String temp1;
	}
	
	public BigDecimal getCollectiveTax() {
		return collectiveTax;
	}
	public List<Article> getArticleList() {
		return articleList;
	}

	public void setArticleList(List<Article> articleList) {
		this.articleList = articleList;
	}
	
	public void resetCollectivePrice() {
		this.collectivePrice = BigDecimal.ZERO;
		this.collectiveTax = BigDecimal.ZERO;
	}

	public Gson toJson() {
		Gson gson = new Gson();
		return gson;
	}

	public Articles fromJson(Gson gson) {
		Articles temp = new Articles();
		return temp;
		
	}
	
}
