package och15_ajax;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

//Singleton + DBCP
public class MemberDao {
	// Singleton ==> resource 절감
	private static MemberDao instance;
	private MemberDao() {
		// default
	}
	
	public static MemberDao getInstance() {
	 if(instance == null) {
		 instance = new MemberDao();
	 }
	 // 한 사람이 두 번 세 번 인스턴스를 중복해서 만들지 않게 하기 위해서 
	 // 인스턴스가 0일 때만 생성이 된다
		return instance;
	}
	
	private Connection getConnection() throws SQLException{
		Connection conn = null;
		try {
		Context ctx = new InitialContext();
		DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/OracleDB");
		conn = ds.getConnection();
		}catch(NamingException e){
			e.printStackTrace();
			
		}
		return conn;
		
	}
	   public int insert(Board board) throws SQLException {
		      Connection conn = null;
		      PreparedStatement pstmt = null;
		      int result = board.getNum();
		      ResultSet rs = null;
		      String sql1 = "SELECT nvl(MAX(num),0) FROM board";
		      String sql = "INSERT INTO board VALUES(?,?,?,?,?,?,?,?,?,?,?,sysdate)";
		       
		      try {
		         conn = getConnection();
		         pstmt = conn.prepareStatement(sql1);
		         rs = pstmt.executeQuery();
		         rs.next();
		         //key인 num 1씩 증가, mysql auto_increment 또는 oracle sequence
		         //sequence를 사용 : values(시퀀스명(board_seq).nextval,?,?...)
		         int number = rs.getInt(1)+1;
		         rs.close();
		         pstmt.close();
		         
		         if (result == 0) board.setRef(number);
		         pstmt = conn.prepareStatement(sql);
		         pstmt.setInt(1, number);
		         pstmt.setString(2, board.getWriter());
		         pstmt.setString(3, board.getSubject());
		         pstmt.setString(4, board.getContent());
		         pstmt.setString(5, board.getEmail());
		         pstmt.setInt(6, board.getReadcount());
		         pstmt.setString(7, board.getPasswd());
		         pstmt.setInt(8, board.getRef());
		         pstmt.setInt(9, board.getRe_step());
		         pstmt.setInt(10, board.getRe_level());
		         pstmt.setString(11, board.getIp());
		         
		         result = pstmt.executeUpdate();
		         
		      } catch (Exception e) {
		         System.out.println(e.getMessage());
		      } finally {
		         if (rs != null)      rs.close();
		         if (pstmt != null)   pstmt.close();
		         if (conn != null)   conn.close();
		      }
		      return result;
		   } 

}

