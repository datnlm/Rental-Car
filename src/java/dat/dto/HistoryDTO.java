/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dat.dto;

/**
 *
 * @author macbook
 */
public class HistoryDTO {

    private String createDate;
    private float total;
    private String address;
    private String phone;
    private String code;
    private String percentOfDiscount;
    private String orderID;
    private String orderDetailID;

    public HistoryDTO() {
    }

    public HistoryDTO(String createDate, float total, String address, String phone, String code, String percentOfDiscount, String orderID, String orderDetailID) {
        this.createDate = createDate;
        this.total = total;
        this.address = address;
        this.phone = phone;
        this.code = code;
        this.percentOfDiscount = percentOfDiscount;
        this.orderID = orderID;
        this.orderDetailID = orderDetailID;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPercentOfDiscount() {
        return percentOfDiscount;
    }

    public void setPercentOfDiscount(String percentOfDiscount) {
        this.percentOfDiscount = percentOfDiscount;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(String orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

   

}
