package InvoiceManagement;


import java.math.BigDecimal;
import java.util.*;


public class Article implements Searchable{
	
	private UUID uuid;
	private String name;
	private BigDecimal price;
	private BigDecimal tax;

	private String EAN;

	private double taxLevel;
	private static final double standardTax = 0.22;
	private static final double reducedTax = 0.095;

	public Article(){
		
		this.uuid = UUID.randomUUID();
		
	}
	
	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public BigDecimal getTax() {
		return tax;
	}

	public static double getStandardtax() {
		return standardTax;
	}
	public static double getReducedtax() {
		return reducedTax;
	}
	
	public String getEAN() {
		return EAN;
	}
	public void setEAN(String eAN) {
		EAN = eAN;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
		double temp = this.getTaxLevel();
		this.tax =  this.price.multiply(BigDecimal.valueOf(temp));
	}
	public double getTaxLevel() {
		return taxLevel;
	}
	public void setTaxLevel(double taxLevel) {
		this.taxLevel = taxLevel;
	}


	public boolean search(String hasString) {
		
		if(hasString.contains(getName()) || hasString.contains(this.getPrice().toString()) || 
				hasString.contains(getTaxLevel()+"") || hasString.contains(EAN))
		{
			return true;
		}
		else {
			return false;
		}
	}
	
	public static boolean checkDigit(String EAN) {
		
		int sum = 0;
		
		switch(EAN.length()) {
		
			case 8:
				EAN = "000000" + EAN;
				break;
			case 12:
				EAN = "00" + EAN;
				break;
			case 13:
				EAN = "0" + EAN;
				break;
			case 14:
				break;
			default:
				return false;
			
		}
		
		
		for(int i = 0; i < EAN.length(); i++) {
			
			if((i%2)==0) {
				sum += Character.getNumericValue(EAN.charAt(i))*3;

			}
			else {
				sum += Character.getNumericValue(EAN.charAt(i));
			}
			
		}
		
		int check = (10 - (sum % 10)) % 10;
		int lastDigit = Character.getNumericValue(EAN.charAt(13));
		
		if(lastDigit == check) {
			return true;
		}
		else {
			
			return false;
		}
		
	}
	
	
}

