package strategy.framework;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDao {
	
	public String selectPassword(String userId, ConnectionMaker connectionFactory) {
		String  password = "";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = connectionFactory.getConnection();
			String query = "select password from member where user_id = ?";
			pstm = conn.prepareStatement(query);
			pstm.setString(1, userId);
			rset = pstm.executeQuery();
			
			if(rset.next()) {
				password = rset.getString("password");
			}
			return password;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				rset.close();
				pstm.close();
				conn.close();
			} catch (SQLException e) {
				// TODO: handle exception
			}
		}
		return password;
	}
}