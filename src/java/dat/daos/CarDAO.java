/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dat.daos;

import dat.dto.CarDTO;
import dat.utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author macbook
 */
public class CarDAO {

    public List<String> getCategory() throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<String> list = new ArrayList<>();
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "select category from tblCategory";
                pst = cn.prepareStatement(sql);
                rs = pst.executeQuery();
                while (rs.next()) {
                    String category = rs.getString("category");
                    list.add(category);
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
        return list;
    }

    public int getPaging(String name, String rentalDate, String returnDate, int quantitySearch, String type) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int result = -1;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "select count(c.carID) as number\n"
                        + "from tblCars c left JOIN (select d.carID as carID, sum(d.quantity) as quantity\n"
                        + "                        from tblOrderDetail d JOIN tblOrders o on d.orderID = o.orderID\n"
                        + "                          where o.status = 'Active' and not(rentalDate > ? or returnDate < ?)"
                        + "                         group by d.carID)tmp2 on tmp2.carID = c.carID\n"
                        + "where status = 'Active' and c." + type + " like ? and c.quantity - isnull(tmp2.quantity, 0) >= ?";

                pst = cn.prepareStatement(sql);
                pst.setString(1, returnDate);
                pst.setString(2, rentalDate);
                pst.setString(3, "%" + name + "%");
                pst.setInt(4, quantitySearch);
                rs = pst.executeQuery();
                if (rs.next()) {
                    result = rs.getInt("number");
                    result = result % 4 == 0 ? result / 4 : result / 4 + 1;
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

    public List<CarDTO> getCars(String name, String rentalDate, String returnDate, int quantitySearch, int index, String type) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<CarDTO> list = new ArrayList<>();
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "select c.carID as carID, c.carName as carName, color, year, c.price as price, c.img as img, c.quantity - isnull(tmp2.quantity, 0) as quantity, tmp.avg as rate, category\n"
                        + "from tblCars c left join\n"
                        + "              (select carID, AVG(rate) as avg\n"
                        + "              from tblFeedBack\n"
                        + "              GROUP BY carID) tmp on c.carID = tmp.carID left JOIN (select d.carID as carID, sum(d.quantity) as quantity\n"
                        + "                                                                    from tblOrderDetail d JOIN tblOrders o on d.orderID = o.orderID\n"
                        + "                                                                     where o.status = 'Active' and not(rentalDate > ? or returnDate < ?)"
                        + "                                                                     group by d.carID)tmp2 on tmp2.carID = c.carID\n"
                        + "where status = 'Active' and c." + type + " like ? and c.quantity - isnull(tmp2.quantity, 0) >= ?\n"
                        + "order by year desc\n"
                        + "OFFSET ? ROWS\n"
                        + "FETCH FIRST 4 ROW ONLY";
                pst = cn.prepareStatement(sql);
                pst.setString(1, returnDate);
                pst.setString(2, rentalDate);
                pst.setString(3, "%" + name + "%");
                pst.setInt(4, quantitySearch);
                pst.setInt(5, index);
                rs = pst.executeQuery();
                while (rs.next()) {
                    String carID = rs.getString("carID");
                    String carName = rs.getString("carName");
                    String color = rs.getString("color");
                    String year = rs.getString("year");
                    float price = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    String category = rs.getString("category");
                    String img = rs.getString("img");
                    String rate = rs.getString("rate");
                    if (rate == null) {
                        rate = "N/A";
                    }
                    list.add(new CarDTO(carID, carName, color, year, price, quantity, category, img, rate));
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
        return list;
    }

    public int getQuantity(String carID, String rentalDate, String returnDate) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int result = -1;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "select c.quantity - isnull(tmp2.quantity, 0) as quantity\n"
                        + "from tblCars c left JOIN \n"
                        + "                (select d.carID as carID, sum(d.quantity) as quantity\n"
                        + "                from tblOrderDetail d JOIN tblOrders o on d.orderID = o.orderID\n"
                        + "                where o.status = 'Active' and not(rentalDate > ? or returnDate < ?)\n"
                        + "                group by d.carID)tmp2 on tmp2.carID = c.carID\n"
                        + "where status = 'Active' and c.carID = ? and c.quantity - isnull(tmp2.quantity, 0) >= 0";

                pst = cn.prepareStatement(sql);
                pst.setString(1, returnDate);
                pst.setString(2, rentalDate);
                pst.setString(3, carID);
                rs = pst.executeQuery();
                if (rs.next()) {
                    result = rs.getInt("quantity");
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
}
