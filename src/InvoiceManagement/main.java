package InvoiceManagement;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.google.gson.Gson;

import DataAccess.MySqlCompany;
import DataAccess.MySqlArticle;
import DataAccess.MySqlReceipt;
import si.um.feri.database.DBHelper;

public class main {
	
public static void main(String[] args) {
		
		//create articles
		Article glasses = new Article();
		glasses.setTaxLevel(Article.getReducedtax());
		glasses.setPrice(new BigDecimal("6.90"));
		glasses.setName("Funny glasses");
		glasses.setEAN("2116763000980");

		
		Article hairband = new Article();
		hairband.setTaxLevel(Article.getStandardtax());
		hairband.setPrice(new BigDecimal("5.50"));
		hairband.setName("Hairband 50");
		hairband.setEAN("6291041500213");

		
		Article loaf = new Article();
		loaf.setTaxLevel(Article.getReducedtax());
		loaf.setPrice(new BigDecimal("1.51"));
		loaf.setName("Dark loaf");
		
		Article milk = new Article();
		milk.setTaxLevel(Article.getReducedtax());
		milk.setPrice(new BigDecimal("1.69"));
		milk.setName("Milk without lactose");
		
		
		
		//create companies
		
		Company src = new Company();
		src.setName("SRC sistemske integracije d.o.o.");
		src.setTaxNumber("20453957");
		src.setRegistrationNumber("1447190000");
		src.setTaxPayer(true);
		src.setAddress("Tržaška cesta 116, Ljubljana, 1000 Ljubljana");
		src.setPhoneNo("01 6007000");
		
		Company comtrade = new Company();
		comtrade.setName("Comtrade d.o.o.");
		comtrade.setTaxNumber("96513624");
		comtrade.setRegistrationNumber("3281841000");
		comtrade.setTaxPayer(true);
		comtrade.setAddress("Ob Dravi 6, 2000 Maribor");
		comtrade.setPhoneNo("(02)4508800");
		
		Company eles = new Company();
		eles.setName("ELES, d.o.o.");
		eles.setTaxNumber("20874731");
		eles.setRegistrationNumber("5427223000");
		eles.setTaxPayer(true);
		eles.setAddress("tempAddress");
		eles.setPhoneNo("(02)4508800");


		Articles articles = new Articles();
		articles.addToList(glasses);
		articles.addToList(hairband);

		Receipt originalReceipt = new Receipt(articles);
		originalReceipt.setIssuer(src);
				
		
		Gson gson = new Gson();
		
		String json = gson.toJson(comtrade);
		String jsonGet = "";
		System.out.println(json);
		Helper.write(json);
		jsonGet = Helper.read();
		System.out.println(jsonGet);
		
		//test
		MySqlArticle tempSqlArticle = new MySqlArticle();
		tempSqlArticle.insert(glasses);
		Article tempArticle = tempSqlArticle.getByBarcode("2116763000980");
		System.out.println(tempArticle.getName());
		tempSqlArticle.update(glasses);
		tempSqlArticle.insert(hairband);
		List<Article> tempListArticle = tempSqlArticle.getAll();	
		Article articleGetByID = tempSqlArticle.getById(glasses.getUuid());
		
		
		
		
				
	}

}
