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
public class CarDTO {

    private String carID;
    private String carName;
    private String color;
    private String year;
    private float price;
    private int quantity;
    private String category;
    private String img;
    private String rate;

    public CarDTO() {
    }

    public CarDTO(String carID, String carName, String color, String year, float price, int quantity, String category, String img, String rate) {
        this.carID = carID;
        this.carName = carName;
        this.color = color;
        this.year = year;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.img = img;
        this.rate = rate;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

   
}
