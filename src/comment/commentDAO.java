package comment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import bbs.bbs;
import comment.comment;

public class commentDAO {

	private Connection conn; 
	private ResultSet rs;
	private PreparedStatement pstmt;

	public commentDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/dbProgram";
			String dbID = "root";
			String dbPassword = "0805";
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection(dbURL,dbID,dbPassword);

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	// ������ �ð��� �������� �Լ�
	public String getDate() {
		String SQL = "SELECT NOW()";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ""; // �����ͺ��̽� ����

	}

	// comment_id �������� �Լ�
	public int getNext() {
		String SQL = "SELECT comment_id FROM comment ORDER BY comment_id DESC";

		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) + 1;
			}

			return 1;// ù ��° �Խù��� ���
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // �����ͺ��̽� ����
	}

	// ������ ���� �ۼ��ϴ� �Լ�---------------���⼭���ͼ���

	public int comment( String comment_content, String user_id) {
		String SQL = "INSERT INTO comment VALUES(?, ?, ?, ?)";

		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext()); //comment_id
			pstmt.setString(2, comment_content); //comment_contente�� ����
			pstmt.setString(3, user_id); //user_id�� ����
			pstmt.setString(4, getDate()); //date���� �������� �Լ� ȣ��

			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // �����ͺ��̽� ����
	}

	public ArrayList<comment> getComment() {
	      ArrayList<comment> datas = new ArrayList<comment>();
	      
	      String SQL="SELECT * FROM comment";
	      try {
	         pstmt = conn.prepareStatement(SQL);
	         ResultSet rs = pstmt.executeQuery();
	         
	         while(rs.next()) {
	        	 comment result = new comment();
	            
	            result.setComment_id(rs.getInt("comment_id"));
	            result.setComment_content(rs.getString("comment_content"));
	            result.setUser_id(rs.getString("user_id"));
	            result.setComment_date(rs.getString("comment_date"));	        
	            datas.add(result);
	         }

	         rs.close();
	         
	      }catch(Exception e) {
	         e.printStackTrace();
	      }
	      
	      return datas;
	      
	   }
	
	public comment getComment(int comment_id) {
		String SQL = "SELECT * FROM comment WHERE comment_id = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, comment_id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				comment comment = new comment();
				comment.setComment_id(rs.getInt(1));
				comment.setComment_content(rs.getString(2));
				comment.setUser_id(rs.getString(3));
				comment.setComment_date(rs.getString(4));
				return comment;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
