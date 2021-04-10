/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dat.daos;

import dat.dto.UserDTO;
import dat.utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author macbook
 */
public class UserDAO {

    public UserDTO checkLogin(String userID, String password) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        UserDTO user = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "select name, phone, address, status, code\n"
                        + "from tblUsers\n"
                        + "where userID = ? and password = ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, userID);
                pst.setString(2, password);
                rs = pst.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("name");
                    String phone = rs.getString("phone");
                    String address = rs.getString("address");
                    String status = rs.getString("status");
                    String code = rs.getString("code");
                    user = new UserDTO(userID, "", name, phone, address, status, code);
                }
            }
        } catch (Exception e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return user;
    }

    public int create(UserDTO user) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        int result = -1;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "insert into tblUsers(userID, password, name, phone, address, createDate, code, status) values(?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, 'New')";
                pst = cn.prepareStatement(sql);
                pst.setString(1, user.getUserID());
                pst.setString(2, user.getPassword());
                pst.setString(3, user.getName());
                pst.setString(4, user.getPhone());
                pst.setString(5, user.getAddress());
                pst.setString(6, user.getCode());
                result = pst.executeUpdate();
            }
        } catch (Exception e) {
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return result;
    }

    public int verifyAccount(String userID, String code) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        int result = -1;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "update tblUsers\n"
                        + "set status = 'Active'\n"
                        + "where userID = (select userID from tblUsers where userID = ? and code = ?)";
                pst = cn.prepareStatement(sql);
                pst.setString(1, userID);
                pst.setString(2, code);
                result = pst.executeUpdate();
            }
        } catch (Exception e) {
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return result;
    }

    public void resendEmail(String userID, String code) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;

        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "update tblUsers\n"
                        + "set code = ?\n"
                        + "where userID = ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, code);
                pst.setString(2, userID);
                pst.executeUpdate();
            }
        } catch (Exception e) {
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
    }
}
