package com.java1234.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import com.java1234.entity.User;
import com.mysql.jdbc.Connection;


public class UserDao {
	public User getByUserName(Connection con,String userName)throws Exception{
		User resultUser =  null;
		String sql = "select * from t_user where userName=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, userName);
		ResultSet rSet = pstmt.executeQuery();
		if (rSet.next()) {
			resultUser = new User();
			resultUser.setId(rSet.getInt("id"));
			resultUser.setUserName(rSet.getString("userName"));
			resultUser.setPassword(rSet.getString("password"));
		}
		return resultUser;
	}

	public static Set<String> getRoles(Connection con, String userName) throws Exception {
		Set<String> roles=new HashSet<String>();
		String sql = "SELECT * FROM t_user u,t_roles r WHERE u.roleId=r.id AND u.userName=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, userName);
		ResultSet rSet = pstmt.executeQuery();
		while (rSet.next()) {
			roles.add(rSet.getString("roles"));
		}
	
		return roles;
	}

	public static Set<String> getPermissions(Connection con, String userName) throws Exception{
		Set<String> permisions = new HashSet<String>();
		String sql = "SELECT * FROM t_user u,t_roles r,t_permissionname t WHERE u.roleId=r.id AND t.roleId=r.id AND u.userName=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, userName);
		ResultSet rSet = pstmt.executeQuery();
		while (rSet.next()) {
			permisions.add(rSet.getString("permissionName"));
		}
			
		
		return permisions;
	}

}
