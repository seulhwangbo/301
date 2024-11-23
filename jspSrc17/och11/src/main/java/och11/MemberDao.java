package och11;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MemberDao {
   private Connection getConnection() throws SQLException {
      Connection conn = null;
      
      try {
         Context ctx = new InitialContext();
         DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/OracleDB");
         conn = ds.getConnection();
      } catch (NamingException e) {
         System.out.println("e.getMessage()->"+e.getMessage());
      }
      
      return conn;
   }
   
   public int insert(MemberDto member) throws SQLException {
	  Connection conn = null;
	  PreparedStatement pstmt = null;
      int result = 0;
      String sql = "INSERT INTO member1 VALUES (?,?,?,sysdate)";

      try {
         conn = getConnection();
         
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, member.getId());
         pstmt.setString(2, member.getPassword());
         pstmt.setString(3, member.getName());
         
         result = pstmt.executeUpdate();
         
      } catch (Exception e) {
         System.out.println(e.getMessage());
      } finally {
         if (pstmt != null)    pstmt.close();
         if (conn != null)    conn.close();
      }
      
      return result;
   }
   
   		public int check(String id, String password) throws SQLException {
   		 Connection conn = null;
   		 int result = 0;
         PreparedStatement pstmt = null;
         ResultSet rs = null;
         String sql = "Select password FROM MEMBER1 where id=?";
         try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String dbPassword = rs.getString(1);
				if(dbPassword.equals(password)) result = 1;
				else result = 0;
			}else result =-1;
         }catch(Exception e){
        	 System.out.println(e.getMessage());
		} finally {
         	if (rs !=null)    rs.close();
         	if (pstmt !=null) pstmt.close();
         	if (conn !=null)  conn.close();
		}
         return result;
   	
   }
}
