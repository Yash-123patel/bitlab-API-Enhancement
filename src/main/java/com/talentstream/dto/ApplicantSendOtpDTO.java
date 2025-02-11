package com.talentstream.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
 
public class ApplicantSendOtpDTO {
    
    @NotNull(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
 
    @NotNull(message = "Mobile number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Invalid mobile number format")
    private String mobilenumber;
 
    // Constructors
    public ApplicantSendOtpDTO() {}
 
    public ApplicantSendOtpDTO(String email, String mobilenumber) {
        this.email = email;
        this.mobilenumber = mobilenumber;
    }
 
    // Getters and Setters
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getMobilenumber() {
        return mobilenumber;
    }
 
    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }
}
