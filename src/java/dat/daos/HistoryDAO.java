/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dat.daos;

import dat.dto.HistoryDTO;
import dat.dto.HistoryDetailDTO;
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
public class HistoryDAO {

    public List<HistoryDTO> getHistory() throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<HistoryDTO> list = new ArrayList<>();
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "select  o.createDate as createDate, o.total as total, o.address as address, o.phone as phone, o.code as code, o.percentOfDiscount as percentOfDiscount, o.orderID as orderID, tmp.orderID as orderDetailID\n"
                        + "  from tblOrders o left join(\n"
                        + "         select orderID\n"
                        + "             from tblOrderDetail\n"
                        + "             where CURRENT_TIMESTAMP < rentalDate)tmp on o.orderID = tmp.orderID "
                        + "             where o.status = 'Active'\n"
                        + "  order by createDate desc";
                pst = cn.prepareStatement(sql);
                rs = pst.executeQuery();
                while (rs.next()) {
                    String createDate = rs.getString("createDate");
                    float total = rs.getFloat("total");
                    String address = rs.getString("address");
                    String phone = rs.getString("phone");
                    String code = rs.getString("code");
                    String percentOfDiscount = rs.getString("percentOfDiscount");
                    String orderID = rs.getString("orderID");
                    String orderDetailID = rs.getString("orderDetailID");
                    if (orderDetailID == null) {
                        orderDetailID = "N/A";
                    }
                    list.add(new HistoryDTO(createDate, total, address, phone, code, percentOfDiscount, orderID, orderDetailID));
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

    public int delete(String orderID) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        int result = -1;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "update tblOrders\n"
                        + "set status = 'inActive'\n"
                        + "where orderID = ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, orderID);
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

    public List<HistoryDetailDTO> getHistoryDetail(String orderID) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<HistoryDetailDTO> list = new ArrayList<>();
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "select d.carID as carID, c.carName as carName, d.quantity as quantity, d.price as price, d.rentalDate as rentalDate, d.returnDate as returnDate, tmp.detailID as detailID\n"
                        + "from tblOrderDetail d left join (select detailID\n"
                        + "                          from tblOrderDetail \n"
                        + "                          where orderID = ? and returnDate < CURRENT_TIMESTAMP and detailID not in(select detailID from tblFeedBack))tmp on d.detailID = tmp.detailID\n"
                        + "                          Join tblCars c on d.carID = c.carID\n"
                        + "where d.orderID = ?\n"
                        + "ORDER by rentalDate desc";
                pst = cn.prepareStatement(sql);
                pst.setString(1, orderID);
                pst.setString(2, orderID);
                rs = pst.executeQuery();
                while (rs.next()) {
                    String carID = rs.getString("carID");
                    String carName = rs.getString("carName");
                    float price = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    String rentalDate = rs.getString("rentalDate");
                    String returnDate = rs.getString("returnDate");
                    String detailID = rs.getString("detailID");
                    if (detailID == null) {
                        detailID = "N/A";
                    }
                    list.add(new HistoryDetailDTO(carID, carName, price, quantity, rentalDate, returnDate, detailID));
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

    public int feebBack(int rate, String detailID, String userID, String carID) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        int result = -1;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "insert into tblFeedBack(userID, carID, rate, detailID) values(?, ?, ?, ?)";
                pst = cn.prepareStatement(sql);
                pst.setString(1, userID);
                pst.setString(2, carID);
                pst.setInt(3, rate);
                pst.setString(4, detailID);
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

    public List<HistoryDTO> getHistory(String type, String date, String name) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<HistoryDTO> list = new ArrayList<>();
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "";
                if (type.equals("date")) {
                    sql = "select  o.createDate as createDate, o.total as total, o.address as address, o.phone as phone, o.code as code, o.percentOfDiscount as percentOfDiscount, o.orderID as orderID, tmp.orderID as orderDetailID\n"
                            + "  from tblOrders o left join(\n"
                            + "         select orderID\n"
                            + "             from tblOrderDetail\n"
                            + "             where CURRENT_TIMESTAMP < rentalDate)tmp on o.orderID = tmp.orderID "
                            + "where o.createDate = ? and o.status = 'Active'\n"
                            + "  order by createDate desc";
                } else {
                    sql = "select  o.orderID, o.createDate as createDate, o.total as total, o.address as address, o.phone as phone, o.code as code, o.percentOfDiscount as percentOfDiscount, o.orderID as orderID, tmp.orderID as orderDetailID\n"
                            + " from tblOrders o left join(\n"
                            + "                  select orderID, carID\n"
                            + "                  from tblOrderDetail\n"
                            + "                where CURRENT_TIMESTAMP < rentalDate)tmp on o.orderID = tmp.orderID join (Select d.orderID\n"
                            + "                                                                                    from tblOrderDetail d join tblCars c on d.carID = c.carID\n"
                            + "                                                                                    where c.carName like ?)tmp2 on tmp2.orderID = o.orderID\n"
                            + "where o.status = 'Active'\n"
                            + "order by createDate desc";
                }
                pst = cn.prepareStatement(sql);
                if (type.equals("date")) {
                    pst.setString(1, date);
                } else {
                    pst.setString(1, "%" + name + "%");
                }
                rs = pst.executeQuery();
                while (rs.next()) {
                    String createDate = rs.getString("createDate");
                    float total = rs.getFloat("total");
                    String address = rs.getString("address");
                    String phone = rs.getString("phone");
                    String code = rs.getString("code");
                    String percentOfDiscount = rs.getString("percentOfDiscount");
                    String orderID = rs.getString("orderID");
                    String orderDetailID = rs.getString("orderDetailID");
                    if (orderDetailID == null) {
                        orderDetailID = "N/A";
                    }
                    list.add(new HistoryDTO(createDate, total, address, phone, code, percentOfDiscount, orderID, orderDetailID));
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
}
