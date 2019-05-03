package goods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import bbs.bbs;

public class goodsDAO {
	
	private Connection conn; 
	private ResultSet rs; 
	public goodsDAO() {
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

	// goods_id �������� �Լ�
	public int getNext() {
		String SQL = "SELECT goods_id FROM goods ORDER BY goods_id DESC";

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
	
	
	//goods���̺� �Է��� goods�� ����.
	public int goods(String goods_name, String goods_type, int goods_price, String user_id, int goods_available) {

		String SQL = "INSERT INTO goods VALUES(?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext());  //goods_id�� �ڵ����� �����ϴ� �Լ� ȣ��
			pstmt.setString(2, goods_name);
			pstmt.setString(3, goods_type);
			pstmt.setInt(4, goods_price);
			pstmt.setString(5, user_id);
			pstmt.setInt(6,  1);

			return pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // �����ͺ��̽� ����
	}
	
	public ArrayList<goods> getList(int pageNumber){
		  String SQL = "SELECT * FROM goods WHERE goods_id < ? AND goods_available = 1 ORDER BY goods_id DESC LIMIT 10";
		  ArrayList<goods> list = new ArrayList<goods>();

		  try {
		    PreparedStatement pstmt = conn.prepareStatement(SQL);
		    pstmt.setInt(1,  getNext() - (pageNumber - 1) * 10);
		    rs = pstmt.executeQuery();
		    while(rs.next()) {
		    	goods goods = new goods();
		    	  goods.setGoods_id(rs.getInt(1));
			      goods.setGoods_name(rs.getString(2));
			      goods.setGoods_type(rs.getString(3));
			      goods.setGoods_price(rs.getInt(4));
			      goods.setUser_id(rs.getString(5));
			      goods.setGoods_available(rs.getInt(6));
			      list.add(goods);
		    }

		  } catch (Exception e) {
		    e.printStackTrace();
		  }
		  return list;

		}



		// �Խù��� 10,20������ ������ �Խù��� 10����� ���� �������� ���� ��
		public boolean nextPage(int pageNumber) {
		  String SQL = "SELECT * FROM goods WHERE goods_id < ? AND goods_available = 1 ORDER BY goods_id DESC LIMIT 10";

		  try {
		    PreparedStatement pstmt = conn.prepareStatement(SQL);
		    pstmt.setInt(1,  getNext() - (pageNumber - 1) * 10);
		    rs = pstmt.executeQuery();

		    if(rs.next()) {
		      return true;
		    }
		  } catch (Exception e) {
		    e.printStackTrace();
		  }
		  return false;	 
		}
		
		public goods getGoods(int goods_id) {
			String SQL = "SELECT * FROM goods WHERE goods_id = ?";
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, goods_id);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					goods goods = new goods();
					goods.setGoods_id(rs.getInt(1));
					goods.setGoods_name(rs.getString(2));
					goods.setGoods_type(rs.getString(3));
					goods.setGoods_price(rs.getInt(4));
					goods.setUser_id(rs.getString(5));
					goods.setGoods_available(rs.getInt(6));
					return goods;
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	

}
