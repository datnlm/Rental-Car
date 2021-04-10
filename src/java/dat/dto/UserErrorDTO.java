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
public class UserErrorDTO {
    private String userIDError;
    private String passwordError;
    private String nameError;
    private String phoneError;
    private String adrressError;
    public UserErrorDTO() {
    }

    public UserErrorDTO(String userIDError, String passwordError, String nameError, String phoneError, String adrressError) {
        this.userIDError = userIDError;
        this.passwordError = passwordError;
        this.nameError = nameError;
        this.phoneError = phoneError;
        this.adrressError = adrressError;
    }

    public String getUserIDError() {
        return userIDError;
    }

    public void setUserIDError(String userIDError) {
        this.userIDError = userIDError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getNameError() {
        return nameError;
    }

    public void setNameError(String nameError) {
        this.nameError = nameError;
    }

    public String getPhoneError() {
        return phoneError;
    }

    public void setPhoneError(String phoneError) {
        this.phoneError = phoneError;
    }

    public String getAdrressError() {
        return adrressError;
    }

    public void setAdrressError(String adrressError) {
        this.adrressError = adrressError;
    }

    

  
}
