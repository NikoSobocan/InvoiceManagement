package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import InvoiceManagement.Company;
import si.um.feri.database.DBHelper;

public class MySqlCompany  implements CompanyDao{


	@Override
	public Company getByTaxNo(String taxNo){
		
		try(Connection con = DBHelper.getConnection();){
			
			PreparedStatement ps = con.prepareStatement("SELECT * FROM company WHERE tax_number = ?");
			ps.setString(1, taxNo);
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
	public Company getById(UUID id) {
		
		try(Connection con = DBHelper.getConnection();){
			
			PreparedStatement ps = con.prepareStatement("SELECT * FROM company WHERE idCompany = ?");
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
	public List<Company> getAll(){
		
		List<Company> tempList = new ArrayList<Company>();
		
		try(Connection con = DBHelper.getConnection();){
			
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM company" );

			Company tempCompany;
			
			while(rs.next()) {

				tempCompany =  extractFromResultSet(rs);
				tempList.add(tempCompany);
				
			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return tempList;
		
		
	}
	
	@Override
	public boolean insert(Company co) {
		
		try(Connection con = DBHelper.getConnection();){
			
			PreparedStatement ps = con.prepareStatement
					("INSERT INTO company (idCompany, name, tax_number, registration_number, taxpayer,"
							+ "phone_number, address, deleted, created)"
							+ " VALUES (?,?,?,?,?,?,?,?,NOW())");
			ps.setBytes(1, DBHelper.getBytesFromUUID(co.getUuid()));
			ps.setString(2, co.getName());
			ps.setString(3, co.getTaxNumber());
			ps.setString(4, co.getRegistrationNumber());
			ps.setBoolean(5, co.isTaxPayer());
			ps.setString(6, co.getPhoneNo());
			ps.setString(7, co.getAddress());
			ps.setInt(8, 0);

			ps.execute();
			return true;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean update(Company co) {
		
		try(Connection con = DBHelper.getConnection();){
			
			PreparedStatement ps = con.prepareStatement("UPDATE company SET name = ?, tax_number = ?, "
					+ "registration_number = ?, taxpayer = ?, phone_number = ?, address = ?"
					+ " WHERE tax_number = ?");
			
			ps.setString(1, co.getName());
			ps.setString(2, co.getTaxNumber());
			ps.setString(3, co.getRegistrationNumber());
			ps.setBoolean(4, co.isTaxPayer());
			ps.setString(5, co.getPhoneNo());
			ps.setString(6, co.getAddress());
			ps.setString(7, co.getTaxNumber());
			
			ps.execute();
			
			return true;
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	@Override
	public boolean delete(Company co) {
		
		try(Connection con = DBHelper.getConnection();){
			
			PreparedStatement ps = con.prepareStatement("DELETE FROM company WHERE tax_number = ?");
			ps.setString(1, co.getTaxNumber());
			ps.execute();
			return true;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
			
		
		return false;
	}
	
	@Override
	public Company extractFromResultSet(ResultSet rs) throws SQLException{
		
		Company temp = new Company();

		temp.setUuid(DBHelper.getUUIDFromBytes(rs.getBytes("idCompany")));
		temp.setName(rs.getString("name"));
		temp.setTaxNumber(rs.getString("tax_number"));
		temp.setRegistrationNumber(rs.getString("registration_number"));
		temp.setTaxPayer(rs.getBoolean("taxpayer"));
		temp.setPhoneNo(rs.getString("phone_number"));
		temp.setAddress(rs.getString("address"));
				
		return temp;
		
	}
	
}
