
package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import user.User;

public class UserDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public UserDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/dbProgram";
			String dbID = "root";
			String dbPassword = "0805";
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection(dbURL,dbID,dbPassword);
		}	catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int login(String user_id, String user_pw) {
		String SQL="SELECT user_pw FROM user WHERE user_id=?";
		try {
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, user_id);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString(1).equals(user_pw))
					return 1; //�α��� ����
				else
					return 0; // ��й�ȣ ����ġ
			}
			return -1; //���̵� ����
		} catch(Exception e) {
			e.printStackTrace();
		} 
		return -2;  //������ ���̽� ����
	}
	
	public int join(User user) {
		String SQL="INSERT INTO user VALUES (?, ?, ?, ?)"; //������ ����
		try { 
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUser_id());
			pstmt.setString(2, user.getUser_pw());
			pstmt.setString(3, user.getUser_name());
			pstmt.setString(4, user.getUser_div());
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		} 
		return -1; //�����ͺ��̽� ����
	}
	

	
}
