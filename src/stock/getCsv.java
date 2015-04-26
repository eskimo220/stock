package stock;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class getCsv {

	public static void main(String[] args) {
		insertData();
	}
	
	public static void insertData() {
	    try {
	      Driver d = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
	      String connUrl = "jdbc:sqlserver://localhost:1433;instance=SQLEXPRESS;database=mydata;integratedSecurity=false;user=baiy01;password=baiyan87731";
	      Connection con = d.connect(connUrl, new Properties());

	      if(con != null){
		      String SQL = "SELECT * FROM T_CsvDate";
		      Statement stmt = con.createStatement();
		      ResultSet rs = stmt.executeQuery(SQL);
		      
		      while (rs.next()) {
		        System.out.println(
		          rs.getString("csvDate") 
		          + ", " + rs.getString("inputDate"));
		      }
		      
		      rs.close();
		      stmt.close();
	      }
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
	public void downloadCsv() {
		 try {
			 
		      URL url = new URL("http://k-db.com/?p=all&download=csv&date=2015-04-24");
		  
		      HttpURLConnection conn =
		      	(HttpURLConnection) url.openConnection();
		      conn.setAllowUserInteraction(false);
		      conn.setInstanceFollowRedirects(true);
		      conn.setRequestMethod("GET");
		      conn.connect();
		      
		      int httpStatusCode = conn.getResponseCode();
		      
		      if(httpStatusCode != HttpURLConnection.HTTP_OK){
		        throw new Exception();
		      }
		      
		      // Input Stream
		      DataInputStream dataInStream 
		        = new DataInputStream(
		            conn.getInputStream());
		      
		      // Output Stream
		       DataOutputStream dataOutStream 
		         = new DataOutputStream(
		          new BufferedOutputStream(
		            new FileOutputStream("D:\\Temp\\stock.csv")));
		      
		      // Read Data
		      byte[] b = new byte[4096];
		      int readByte = 0;
		  
		      while(-1 != (readByte = dataInStream.read(b))){
		        dataOutStream.write(b, 0, readByte);
		      }
		      
		      // Close Stream
		      dataInStream.close();
		      dataOutStream.close();
		      
		    } catch (FileNotFoundException e) {
		      e.printStackTrace();
		    } catch (ProtocolException e) {
		      e.printStackTrace();
		    } catch (MalformedURLException e) {
		      e.printStackTrace();
		    } catch (IOException e) {
		      e.printStackTrace();
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
	}
}
