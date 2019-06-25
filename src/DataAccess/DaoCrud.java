package DataAccess;

import java.util.*;
import java.sql.*;

public interface DaoCrud <T>{

	T getById(UUID id);
	List<T>getAll();
	boolean insert(T m);
	boolean update(T m);
	boolean delete(T m);
	T extractFromResultSet(ResultSet rs) throws SQLException;
	
}
