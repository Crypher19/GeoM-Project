package appserver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBclass {
	Connection con=null;
    Statement stmt=null;
    String url ="";
    
    DBclass(){

	    String url = "jdbc:mysql://localhost:1010/test?";
	    
	    try {
	        Class.forName("com.mysql.jdbc.Driver");
	        
	        con = DriverManager.getConnection(url, "root", "ciao123");
	        stmt = con.createStatement();
	        
	    } catch (SQLException ex) {
	        System.err.println("SQLException: " + ex.getMessage());
	    } catch (ClassNotFoundException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	       
	    }
	     try {
			stmt.execute("USE app_db;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public boolean addnum(String numTel){
    	String sql = "INSERT INTO utenti (numTel) VALUES ('" + numTel + "');";
    	
    	try {
    		if(checkNum(numTel)){
    			stmt.execute(sql);
    		}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return false;
    }
    
    public boolean setNick(String nick, String nTel){
    	String sql = "UPDATE utenti SET nickname = '" + nick + "' WHERE numTel = '" + nTel + "';";
    	
    	try {
			stmt.execute(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return false;
    }
    
    public boolean setMex(String mex, String nTel){
    	String sql = "UPDATE utenti SET mexPers = '" + mex + "' WHERE numTel = '" + nTel + "';";
       	
       	try {
   			stmt.execute(sql);
    			return true;
       	} catch (SQLException e) {
    		e.printStackTrace();
   		}
    	return false;
    }
    
    public boolean setStato(String stato, String nTel){
    	String sql = "UPDATE utenti SET live = '" + stato + "' WHERE numTel = '" + nTel + "';";
    	
       	try {
   			stmt.execute(sql);
    			return true;
       	} catch (SQLException e) {
    		e.printStackTrace();
   		}
    	return false;
    }
    
    public boolean setlastLogin(String lastlogin, String nTel){
    	String sql = "UPDATE utenti SET lastLogin = '" + lastlogin + "' WHERE numTel = '" + nTel + "';";
    	
       	try {
   			stmt.execute(sql);
    			return true;
       	} catch (SQLException e) {
    		e.printStackTrace();
   		}
    	return false;
    }
    
    public boolean setImgPath(String path, String nTel){
    	String sql = "UPDATE utenti SET fotoPath = '" + path + "' WHERE numTel = '" + nTel + "';";       	
    	
       	try {
   			stmt.execute(sql);
    			return true;
       	} catch (SQLException e) {
    		e.printStackTrace();
   		}
    	return false;
    }
    
    public boolean setpos(String posx, String posy, String nTel){
    	String sql = "UPDATE utenti SET posX = '" + posx + "', posY = '" + posy + "' WHERE numTel = '" + nTel + "';";
    	
       	try {
   			stmt.execute(sql);
    			return true;
       	} catch (SQLException e) {
    		e.printStackTrace();
   		}
    	return false;
    }
    
    public boolean checkNum(String numTel){
    	String sql = "SELECT numTel FROM utenti WHERE numTel = '" + numTel + "');";
    	
    	try {
			ResultSet rs = stmt.executeQuery(sql);
			if(!rs.next()){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return false;
    }
    
    public void close(){
		try {
			//Rilascio la risorsa Database, va richiamato ogni volta che si usa una funzione del database
			//Per permettere agli altri thread di accedervi.
			stmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}