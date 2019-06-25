package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.time.*;
import org.joda.time.*;
import java.sql.*;

import InvoiceManagement.Receipt;
import InvoiceManagement.Article;
import InvoiceManagement.Company;
import si.um.feri.database.DBHelper;

public class MySqlReceipt {

	
	public Receipt getById(UUID id) {
		
		try(Connection con = DBHelper.getConnection();){
			
			PreparedStatement ps = con.prepareStatement("SELECT * FROM invoice WHERE idInvoice = ?");
			ps.setBytes(1, DBHelper.getBytesFromUUID(id));
			ResultSet rs = ps.executeQuery();
			
			if(rs.first()) {
				return extractFromResultSet(rs);
			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public List<Receipt> getAll(){
		
		List<Receipt> tempList = new ArrayList<Receipt>();
		
		try(Connection con = DBHelper.getConnection();){
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM invoice" );

			Receipt tempReceipt;
			
			while(rs.next()) {

				tempReceipt =  extractFromResultSet(rs);
				tempList.add(tempReceipt);
				System.out.println(tempReceipt.getTotal().toString());
				
			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return tempList;
		
		
	}
	
	public boolean insert(Receipt re) {
		
		try(Connection con = DBHelper.getConnection();){
			
			PreparedStatement ps = con.prepareStatement
					("INSERT INTO invoice (idInvoice, total, total_vat, created, deleted, issuer_id)"
							+ " VALUES (?,?,?,?,?,?)");

			Timestamp timeStamp = new Timestamp(re.getDate().getMillis());
			
			Company issuer = re.getIssuer();
			Company customer = re.getCustomer();
			if(re.getCustomer() != null) {
				
				ps = con.prepareStatement
						("INSERT INTO invoice (idInvoice, total, total_vat, created, deleted, issuer_id, customer_id)"
								+ " VALUES (?,?,?,?,?,?,?)");
				ps.setBytes(7, DBHelper.getBytesFromUUID(customer.getUuid()));
				
			}
			ps.setBytes(1, DBHelper.getBytesFromUUID(re.getUuid()));
			ps.setBigDecimal(2, re.getTotal());
			ps.setBigDecimal(3, re.getTotalTax());
			ps.setTimestamp(4, timeStamp);
			ps.setInt(5, 0);
			ps.setBytes(6, DBHelper.getBytesFromUUID(issuer.getUuid()));
			
			
			
			//ps.setBytes(6, re.getIssuer());
			ps.execute();
			return true;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(Receipt re) {
		
		try(Connection con = DBHelper.getConnection();){
			
			PreparedStatement ps = con.prepareStatement("UPDATE invoice SET total = ?, total_vat = ?, "
					+ "issuer_id = ? WHERE idInvoice = ?");
			
			Company issuer = re.getIssuer();
			Company customer = re.getCustomer();
			
			ps.setBigDecimal(1, re.getTotal());
			ps.setBigDecimal(2, re.getTotalTax());
			ps.setBytes(3, DBHelper.getBytesFromUUID(issuer.getUuid()));
			
			if(re.getCustomer() !=null) {
				ps = con.prepareStatement("UPDATE invoice SET total = ?, total_vat = ?, "
						+ "issuer_id = ?, customer_id = ? WHERE idInvoice = ?");
				ps.setBytes(4, DBHelper.getBytesFromUUID(customer.getUuid()));
				ps.setBytes(5, DBHelper.getBytesFromUUID(re.getUuid()));
			}
			else {
				ps.setBytes(4, DBHelper.getBytesFromUUID(re.getUuid()));

			}

			
			ps.execute();
			
			return true;
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean delete(Receipt re) {
		
		try(Connection con = DBHelper.getConnection();){
			
			PreparedStatement ps = con.prepareStatement("DELETE FROM invoice WHERE idInvoice = ?");
			ps.setBytes(1, DBHelper.getBytesFromUUID(re.getUuid()));
			ps.execute();
			return true;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
			
		
		return false;
	}
	
	
	public Receipt extractFromResultSet(ResultSet rs) throws SQLException{
		
		Receipt temp = new Receipt();

		DateTime date = new DateTime(rs.getTimestamp("created"));
		
		MySqlCompany tempSqlCompany = new MySqlCompany();
		UUID issuerUUID = DBHelper.getUUIDFromBytes(rs.getBytes("issuer_id"));
		Company issuer = tempSqlCompany.getById(issuerUUID);
		
		Company customer;
		
		UUID articleUUID = DBHelper.getUUIDFromBytes(rs.getBytes("idInvoice"));
		
		temp.setTotal(rs.getBigDecimal("total"));
		temp.setDate(date);
		temp.setIssuer(issuer);
		temp.setUuid(articleUUID);
		temp.setTotalTax(rs.getBigDecimal("total_vat"));
		
		if(rs.getBytes("customer_id") != null) {
			
			UUID customerUUID = DBHelper.getUUIDFromBytes(rs.getBytes("customer_id"));
			customer = tempSqlCompany.getById(customerUUID);
			temp.setCustomer(customer);
			temp.setTaxNumber(customer.getTaxNumber());
			
		}
				
		return temp;
		
	}
	
	
}
