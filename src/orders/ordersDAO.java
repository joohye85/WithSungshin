package orders;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ordersDAO {
	private Connection conn; // �����ͺ��̽��� �����ϱ� ���� ��ü
	// private PreparedStatement pstmt;
	private ResultSet rs; // ������ ���� �� �ִ� ������ ������ش�.
	// mysql ó���κ�
	public ordersDAO() {
		// �����ڸ� ������ش�.
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
	//�ð� �������� �Լ�
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
	
	public int getNext() { //orders_id�� ����� �Լ�,
		String SQL = "SELECT orders_id FROM orders ORDER BY orders_id DESC";

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

	// ������ ���� �ۼ��ϴ� �Լ�

	public int buy(String user_id, String dplace,  int orders_amount) {

		String SQL = "INSERT INTO orders VALUES(?, ?, ?, ?, ?)";

		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext()); //orders_id
			pstmt.setString(2, user_id);
			pstmt.setString(3, dplace);
			pstmt.setString(4, getDate());
			pstmt.setInt(5, orders_amount);


			return pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // �����ͺ��̽� ����
	}
	
}
