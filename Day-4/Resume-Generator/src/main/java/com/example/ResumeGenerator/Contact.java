package com.example.ResumeGenerator;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Contact {
    String email,phone,address;

    public Contact(String email, String phone, String address) {
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
    public Contact(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
