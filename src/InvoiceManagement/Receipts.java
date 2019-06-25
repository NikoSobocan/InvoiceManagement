package InvoiceManagement;
import java.util.*;

import com.google.gson.Gson;

public class Receipts implements JsonSupport{

	private List<Receipt> receiptList = new ArrayList<Receipt>();

	public void addToList(Receipt receiptToAdd) {
		receiptList.add(receiptToAdd);
	}
	
	public List getReceiptList() {
		return receiptList;
	}

	public void setReceiptList(List receiptList) {
		this.receiptList = receiptList;
	}
	
	public Gson toJson() {
		Gson gson = new Gson();
		gson.toJson(this, Receipts.class);
		return gson;
	}

	public Articles fromJson(Gson gson) {
		//gson.fromJson(json, classOfT)
		Articles temp = new Articles();
		return temp;
		
	}
}
