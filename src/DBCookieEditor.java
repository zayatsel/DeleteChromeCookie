
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBCookieEditor {
	private String fileName;

	public DBCookieEditor(String fileName){
		this.fileName = fileName;
	}
	
	public Cookie[] getAllCookies(String domainFilter, String nameFilter) throws ClassNotFoundException, SQLException{
		Class.forName("org.sqlite.JDBC");
		try(Connection connection = DriverManager.getConnection("jdbc:sqlite:"+this.fileName)){
			//Statement statement;
			try (
				Statement statement = connection.createStatement()){
	            statement.setQueryTimeout(30); // set timeout to 30 seconds
	            ResultSet result = null;
	            String query = "select * from cookies" + getFilter(domainFilter, nameFilter);
	            result = statement.executeQuery(query);	            
	            List<Cookie> cookies = new ArrayList<Cookie>();
	            while(result.next()){
	            	cookies.add(new Cookie(result.getString("name"), result.getString("host_key"), result.getLong("creation_utc")));
	            }
	            return cookies.toArray(new Cookie[cookies.size()]);
			}	
			
		}
		//return null;
		
	}
	
	private String getFilter(String domainFilter, String nameFilter) {
		String query = "";
		boolean hasFilter = false;
		if((domainFilter != null && !domainFilter.isEmpty())){
			query += " host_key like \"%" + domainFilter + "%\"";
			hasFilter = true;
        }
        if(nameFilter != null && !nameFilter.isEmpty()) {
        	if(hasFilter){
        		query += "and";
        	}
            query += " name like \"%" + nameFilter + "%\"";
            hasFilter = true;
        }
        if(hasFilter){
        	query = " where"+query;
        }
		return query;
	}

	public void deleteCookies(String domainFilter, String nameFilter) throws ClassNotFoundException, SQLException{
		Class.forName("org.sqlite.JDBC");
		try(Connection connection = DriverManager.getConnection("jdbc:sqlite:"+this.fileName)){
			//Statement statement;
			try (
				Statement statement = connection.createStatement()){
	            statement.setQueryTimeout(30); // set timeout to 30 seconds
	            String query = "DELETE FROM cookies" + getFilter(domainFilter, nameFilter);
	            statement.executeUpdate(query); 
			}	
			
		}
	}
	
}



