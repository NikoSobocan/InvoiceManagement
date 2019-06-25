package InvoiceManagement;
import java.util.*;

public class InternalArticle extends Article{

	private UUID uuid;
	
	public InternalArticle(){
		
		this.uuid = UUID.randomUUID();
		
	}
	
	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	private static Map<Integer, String> codeFood = new HashMap<Integer,String>(){{
		
		put(211, "sadje");
		put(212, "zelenjava");
		put(213, "meso");
		
	}};
	
	
	public static void setEANcodeForFreshFood(int code, String food) {
		
		if(200 > code && code > 299) {
			System.out.println("Input code between 200 and 299");
			return;
		}
		codeFood.put(code, food);
		
		
	}
	
	public static int readFreshFoodEAN(String EAN) {
		
		String code = "";
		String ID = "";
		String weightStr = "";
		
		for(int i = 0; i < 3; i++) {
			code += EAN.charAt(i);
		}
		
		for(int i = 3; i < 7; i++) {
			ID += EAN.charAt(i);
		}
		
		for(int i = 7; i < 12; i ++) {
			weightStr += EAN.charAt(i);
		}
		
		int weight = Integer.parseInt(weightStr);
		
		return weight;
		
	}
	
	public static String weightChange(String EAN, int newWeight) {
		
		String newEAN = "";
		String weightStr = String.format("%05d", newWeight);
		
		for(int i = 0; i < 7; i++) {
			
			newEAN += EAN.charAt(i);
			
		}
		
		for(int i = 0; i < 5; i++) {
			
			newEAN += weightStr.charAt(i);
			
		}
		
		newEAN += EAN.charAt(12);
	
		
		return newEAN;
		
	}
	
}
