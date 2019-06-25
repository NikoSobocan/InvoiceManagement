package DataAccess;

import java.util.*;
import java.math.BigDecimal;
import java.sql.*;
import InvoiceManagement.Article;
import si.um.feri.database.DBHelper;
import java.nio.*;

public class MySqlArticle implements ArticleDao{
	
	
	
	
	@Override
	public Article getByBarcode(String code) {
		
		try(Connection con = DBHelper.getConnection();){
			
			PreparedStatement ps = con.prepareStatement("SELECT * FROM article WHERE barcode = ?");
			ps.setString(1, code);
			ResultSet rs = ps.executeQuery();

			if(rs.first()) {
				return extractFromResultSet(rs);
			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	@Override
	public Article getById(UUID id) {
		
		try(Connection con = DBHelper.getConnection();){
			
			PreparedStatement ps = con.prepareStatement("SELECT * FROM article WHERE idArticle = ?");
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
	
	@Override
	public List<Article> getAll(){
		
		List<Article> tempList = new ArrayList<Article>();
		
		try(Connection con = DBHelper.getConnection();){
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM article" );

			Article tempArticle;
			
			while(rs.next()) {

				tempArticle =  extractFromResultSet(rs);
				tempList.add(tempArticle);
				System.out.println(tempArticle.getName());
				
			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return tempList;
		
		
	}
	
	@Override
	public boolean insert(Article ar) {
		
		try(Connection con = DBHelper.getConnection();){
			
			PreparedStatement ps = con.prepareStatement
					("INSERT INTO article (idArticle, barcode, name, price, vat, stock, deleted, created)"
							+ " VALUES (?,?,?,?,?,?,?,NOW())");
			ps.setBytes(1,DBHelper.getBytesFromUUID(ar.getUuid()));
			ps.setString(2, ar.getEAN());
			ps.setString(3, ar.getName());
			ps.setBigDecimal(4, ar.getPrice());
			ps.setDouble(5, ar.getTaxLevel());
			ps.setInt(6, 1000);
			ps.setInt(7, 0);

			ps.execute();
			return true;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean update(Article ar) {
		
		try(Connection con = DBHelper.getConnection();){
			
			PreparedStatement ps = con.prepareStatement("UPDATE article SET barcode = ?,"
					+ "name = ?, price = ?, vat = ?"
					+ " WHERE barcode = ?");
			
			ps.setString(1, ar.getEAN());
			ps.setString(2, ar.getName());
			ps.setBigDecimal(3, ar.getPrice());
			ps.setDouble(4, ar.getTaxLevel());
			ps.setString(5, ar.getEAN());
			ps.execute();
			
			return true;
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	@Override
	public boolean delete(Article ar) {
		
		try(Connection con = DBHelper.getConnection();){
			
			PreparedStatement ps = con.prepareStatement("DELETE FROM article WHERE barcode = ?");
			ps.setString(1, ar.getEAN());
			ps.execute();
			return true;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
			
		
		return false;
	}
	
	@Override
	public Article extractFromResultSet(ResultSet rs) throws SQLException{
		
		Article temp = new Article();

		temp.setUuid(DBHelper.getUUIDFromBytes(rs.getBytes("idArticle")));
		temp.setName(rs.getString("name")); 
		temp.setPrice(rs.getBigDecimal("price"));
		temp.setEAN(rs.getString("barcode"));
		
		return temp;
		
	}

	
}
