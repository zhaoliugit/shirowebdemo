package com.java1234.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.java1234.Util.DbUtil;
import com.java1234.dao.UserDao;
import com.java1234.entity.User;
import com.mysql.jdbc.Connection;

import sun.security.util.Password;

public class MyRealm extends AuthorizingRealm{
	private UserDao userDao=new UserDao();
	private DbUtil dbUtil=new DbUtil();
	/**
	 * 为当前登录的用户授予角色和权限
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String userName = (String)principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		Connection con = null;
		
		try {
			con = (Connection) dbUtil.getCon();
			authorizationInfo.setRoles(UserDao.getRoles(con,userName));
			authorizationInfo.setStringPermissions(UserDao.getPermissions(con,userName));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return authorizationInfo;
	}
	/**
	 *	验证当前登录的用户 
	 */
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		String userName = (String)token.getPrincipal();
		Connection con=null;
		
		try {
			con=(Connection) dbUtil.getCon();
			User user = userDao.getByUserName(con, userName);
			if (user !=null ) {
				AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), "xx");
				return authenticationInfo;
			}else {
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

}
