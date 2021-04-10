/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dat.daos;

import dat.dto.CarOrdersDTO;
import dat.dto.CartDTO;
import dat.utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author macbook
 */
public class CartDAO {

    public int verifyDiscount(String code) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int result = -1;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "select percentOfDiscount\n"
                        + "from tblDiscount\n"
                        + "where code = ? and expiryDate > CURRENT_TIMESTAMP";
                pst = cn.prepareStatement(sql);
                pst.setString(1, code);
                rs = pst.executeQuery();
                if (rs.next()) {
                    result = rs.getInt("percentOfDiscount");
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
        return result;
    }

    public CarOrdersDTO save(CartDTO cart, String userID, String address, String phone, float total, String code, int percentOfDiscount) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        CarOrdersDTO car = null;
        int result = -1;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                cn.setAutoCommit(false);
                String sql = "";
                if (percentOfDiscount == -1) {
                    sql = "insert into tblOrders(userID, total, createDate, address, phone, status) values (?, ?, CURRENT_TIMESTAMP, ?, ?, 'Active')";
                } else {
                    sql = "insert into tblOrders(userID, total, createDate, address, phone, code, percentOfDiscount, status) values (?, ?, CURRENT_TIMESTAMP, ?, ?, ?, ?,'Active')";
                }
                pst = cn.prepareStatement(sql);
                pst.setString(1, userID);
                pst.setFloat(2, total);
                pst.setString(3, address);
                pst.setString(4, phone);
                if (percentOfDiscount != -1) {
                    pst.setString(5, code);
                    pst.setInt(6, percentOfDiscount);
                }
                result = pst.executeUpdate();
                if (result == 1) {
                    for (CarOrdersDTO dto : cart.getCart()) {
                        String sql2 = "if(select c.quantity - isnull(tmp2.quantity, 0) as quantity\n"
                                + "from tblCars c left JOIN\n"
                                + "(select d.carID as carID, sum(d.quantity) as quantity\n"
                                + "from tblOrderDetail d JOIN tblOrders o on d.orderID = o.orderID\n"
                                + "where o.status = 'Active' and not(rentalDate > ? or returnDate < ?)\n"
                                + " group by d.carID)tmp2 on tmp2.carID = c.carID\n"
                                + "where status = 'Active' and c.carID = ? and c.quantity - isnull(tmp2.quantity, 0) >= 0) - ? >= 0\n"
                                + "BEGIN\n"
                                + "insert into tblOrderDetail(carID, quantity, price, returnDate, rentalDate, orderID) values(?, ?, ?, ?, ?, (SELECT max(orderID)\n"
                                + "from tblOrders))\n"
                                + "end";
                        pst = cn.prepareStatement(sql2);
                        pst.setString(1, dto.getReturnDate());
                        pst.setString(2, dto.getRentalDate());
                        pst.setString(3, dto.getCarID());
                        pst.setInt(4, dto.getQuantity());
                        pst.setString(5, dto.getCarID());
                        pst.setInt(6, dto.getQuantity());
                        pst.setFloat(7, dto.getPrice());
                        pst.setString(8, dto.getReturnDate());
                        pst.setString(9, dto.getRentalDate());
                        result = pst.executeUpdate();
                        if (result != 1) {
                            car = new CarOrdersDTO(dto.getCarID(), dto.getCarName(), dto.getPrice(), dto.getQuantity(), dto.getRentalDate(), dto.getReturnDate());
                            cn.rollback();
                        }
                    }
                } else {
                    cn.rollback();
                }
                cn.commit();
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
        return car;
    }
}
