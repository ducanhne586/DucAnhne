package vn.iostar.DAO.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import vn.iostar.DAO.IUserDao;
import vn.iostar.configs.DBConnectMySQL;
import vn.iostar.models.UserModel;

public class UserDAOimpl extends DBConnectMySQL implements IUserDao {

	public Connection conn = null;
	public PreparedStatement ps = null;
	public ResultSet rs = null;
	
	
	
	@Override
	public List<UserModel> findAll() {
		
		String sql ="select * from users";
		
		List<UserModel> list = new ArrayList<>(); // Tạo 1 list để truyền dữ liệu
		
		try {
			conn = super.getDatabaseConnection(); // kết nối database
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next() /*Next từng DÒNG tới cuối bảng*/) {
				list.add(new UserModel(
						rs.getInt("id"),
						rs.getString("username"),
						rs.getString("password"), rs.getString("email"),
						rs.getString("fullname"), rs.getString("images")
						)
					); //Add vào
			}
			return list;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return null;
	}

	
	
	
	@Override
	public UserModel findById(int id) {
		// TODO Auto-generated method stub
		
		    String sql = "SELECT * FROM users WHERE id = ?";

		    try {
		        conn = super.getDatabaseConnection(); // Kết nối tới cơ sở dữ liệu
		        ps = conn.prepareStatement(sql); // Chuẩn bị câu truy vấn SQL
		        ps.setInt(1, id); // Gán giá trị tham số id vào câu truy vấn
		        rs = ps.executeQuery(); 

		        if (rs.next()) {
		           
		            return new UserModel(
		                    rs.getInt("id"),
		                    rs.getString("username"),
		                    rs.getString("password"),
		                    rs.getString("email"),
		                    rs.getString("fullname"),
		                    rs.getString("images")
		            );
		        }
		    } catch (Exception e) {
		        e.printStackTrace(); 
		    } finally {
		        try {
		            if (rs != null) rs.close(); 
		            if (ps != null) ps.close(); 
		            if (conn != null) conn.close(); 
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }

		    return null; 
		}

	
	
	
	@Override
	public void insert(UserModel user) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO users(id, username, password, images, fullname) VALUE (?, ?, ?, ?, ?, ?)";
		
		try {
			conn = super.getDatabaseConnection(); // kết nối database
			
			ps = conn.prepareStatement(sql); // ném câu sql vào cho thực thi
			
			ps.setInt(1, user.getId());
			ps.setString(2, user.getUsername());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getEmail());
			ps.setString(5, user.getImages());
			ps.setString(6, user.getFullname());
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}

	public static void main(String[] args) {
	    UserDAOimpl userDAO = new UserDAOimpl();
	    
	    // Gọi phương thức findById
	    UserModel user = userDAO.findById(1); // Tìm user có ID là 1
	    if (user != null) {
	        System.out.println(user);
	    } else {
	        System.out.println("User không tồn tại.");
	    }
	}

	
	
}
