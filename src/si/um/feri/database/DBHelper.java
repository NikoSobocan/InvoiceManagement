package si.um.feri.database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;
import InvoiceManagement.Article;

import org.apache.commons.dbcp2.BasicDataSource;



public class DBHelper {

	private static Properties prop = new Properties();
	private static BasicDataSource ds;

	private static BasicDataSource getDataSource() {

		if(ds == null) {
			ds = new BasicDataSource();
			String filePath = new File("").getAbsolutePath();
			filePath+="/config.properties";
			try(InputStream input = new FileInputStream(filePath)){


				prop.load(input);

				ds.setUrl(prop.getProperty("db.url"));
				ds.setUsername(prop.getProperty("db.user"));
				ds.setPassword(prop.getProperty("db.password"));
				ds.setMinIdle(5);
				ds.setMaxIdle(10);
				ds.setMaxOpenPreparedStatements(100);


			}catch(IOException ex) {
				ex.printStackTrace();
			}

		}
		return ds;

	}

	public static Connection getConnection() throws SQLException {
		return getDataSource().getConnection();
	}

	public static void insertArticles(String path) throws SQLException{

		try(Connection con = getConnection();) {

			PreparedStatement updateArticle = con.prepareStatement("insert into article values(?,?,?,?,?,?,?,NOW(),NOW())"); //UUID_TO_BIN(UUID())

			String line = "";
			String cvsSplitBy = ",";
			con.setAutoCommit(false);

			int IDcounter = 0;
			List<List<String>> records = new ArrayList<>();
			String EAN ="";
			String name = "";
			boolean checkEAN;
			Random gen = new Random();

			try (BufferedReader br = new BufferedReader(new FileReader(path))) {

				while ((line = br.readLine()) != null) {

					String[] lineString = line.split(",");
					String[] tempLineString = lineString[0].split("\t");
					records.add(Arrays.asList(lineString));

					EAN = tempLineString[0];
					name = tempLineString[7];


					if(Article.checkDigit(EAN)) {

						updateArticle.setInt(1, IDcounter);
						updateArticle.setString(2, EAN);
						updateArticle.setString(3, name);
						updateArticle.setInt(4, gen.nextInt(9)+1);
						updateArticle.setInt(5, 9);
						updateArticle.setInt(6, 1000);
						updateArticle.setBoolean(7, false);
						updateArticle.executeUpdate();

						updateArticle.addBatch();
						//int[] count = updateArticle.executeBatch();


					}
					else {
						System.out.println("False EAN");
					}



					IDcounter++;
					if(IDcounter % 1000 == 0) {
						con.commit();
					}


					EAN = "";
					name = "";

				}

			} catch (IOException | SQLException e) {
				e.printStackTrace();
				con.rollback();
			}
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}



	}

	public static byte[] getBytesFromUUID(UUID uuid) {
		ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
		bb.putLong(uuid.getMostSignificantBits());
		bb.putLong(uuid.getLeastSignificantBits());
		return bb.array();
	}

	public static UUID getUUIDFromBytes(byte[] bytes) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
		Long high = byteBuffer.getLong();
		Long low = byteBuffer.getLong();

		return new UUID(high, low);
	}





}
