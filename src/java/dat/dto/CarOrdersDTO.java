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
public class CarOrdersDTO {

    private String carID;
    private String carName;
    private float price;
    private int quantity;
    private String rentalDate;
    private String returnDate;

    public CarOrdersDTO() {
    }

    public CarOrdersDTO(String carID, String carName, float price, int quantity, String rentalDate, String returnDate) {
        this.carID = carID;
        this.carName = carName;
        this.price = price;
        this.quantity = quantity;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(String rentalDate) {
        this.rentalDate = rentalDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

}
