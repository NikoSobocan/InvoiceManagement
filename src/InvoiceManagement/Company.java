package InvoiceManagement;
import java.util.*;

public class Company implements Searchable{

	private UUID uuid;
	private String name;
	private String taxNumber;
	private String registrationNumber;
	private boolean isTaxPayer;
	private String phoneNo;
	private String address;
	
	public Company() {
		
		this.uuid = UUID.randomUUID();
		
	}
	
	
	
	public Company(String name, String taxNumber, String registrationNumber, boolean isTaxPayer, String phoneNo,
			String address) {
		this(); // klièe public Company()
		this.name = name;
		this.taxNumber = taxNumber;
		this.registrationNumber = registrationNumber;
		this.isTaxPayer = isTaxPayer;
		this.phoneNo = phoneNo;
		this.address = address;
	}



	public String getPhoneNo() {
		return phoneNo;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTaxNumber() {
		return taxNumber;
	}

	public void setTaxNumber(String taxNumber) {
		this.taxNumber = taxNumber;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public boolean isTaxPayer() {
		return isTaxPayer;
	}

	public void setTaxPayer(boolean isTaxPayer) {
		this.isTaxPayer = isTaxPayer;
	}

	
	public boolean search(String hasString) {
		
		if(hasString.equals(this.name) || hasString.equals(String.valueOf(this.taxNumber)) || 
				hasString.equals(String.valueOf(this.registrationNumber)) || 
				hasString.equals(String.valueOf(this.isTaxPayer)))
		{
			return true;

		}
		else {
			return false;
		}
	}
	
}
