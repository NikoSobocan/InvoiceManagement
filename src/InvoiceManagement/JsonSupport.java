package InvoiceManagement;
import com.google.gson.*;


public interface JsonSupport {
	Gson toJson();
	JsonSupport fromJson(Gson gson);
	
}
