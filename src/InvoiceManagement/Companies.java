package InvoiceManagement;
import java.util.*;

import com.google.gson.Gson;

public class Companies extends Company implements JsonSupport{
	
	private List<Company> listOfComanies = new ArrayList<Company>();
	public Gson toJson() {
		Gson gson = new Gson();
		return gson;
	}

	public Articles fromJson(Gson gson) {
		Articles temp = new Articles();
		return temp;
		
	}
	
}
