/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dat.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author macbook
 */
public class CartDTO {

    private String userID;
    private List<CarOrdersDTO> cart;

    public CartDTO() {
    }

    public CartDTO(String userID, List<CarOrdersDTO> cart) {
        this.userID = userID;
        this.cart = cart;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public List<CarOrdersDTO> getCart() {
        return cart;
    }

    public void setCart(List<CarOrdersDTO> cart) {
        this.cart = cart;
    }

    public void add(CarOrdersDTO dto) {
        if (this.cart == null) {
            this.cart = new ArrayList<>();
        }
        boolean flag = true;
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getCarID().equals(dto.getCarID())
                    && cart.get(i).getRentalDate().equals(dto.getRentalDate())
                    && cart.get(i).getReturnDate().equals(dto.getReturnDate())) {
                cart.get(i).setQuantity(cart.get(i).getQuantity() + dto.getQuantity());
                flag = false;
                break;
            }
        }
        if (flag) {
            this.cart.add(dto);
        }
    }

    public void update(CarOrdersDTO dto) {
        if (this.cart == null) {
            return;
        }
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getCarID().equals(dto.getCarID())
                    && cart.get(i).getRentalDate().equals(dto.getRentalDate())
                    && cart.get(i).getReturnDate().equals(dto.getReturnDate())) {
                cart.set(i, dto);
                break;
            }
        }

    }

    public void delete(String carID, String rentalDate, String returnDate) {
        if (this.cart == null) {
            return;
        }
        for (CarOrdersDTO dto : cart) {
            if (dto.getCarID().equals(carID) && dto.getRentalDate().equals(rentalDate) && dto.getReturnDate().equals(returnDate)) {
                this.cart.remove(dto);
                break;
            }
        }
    }
}
