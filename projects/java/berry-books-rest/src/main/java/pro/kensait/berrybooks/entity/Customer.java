package pro.kensait.berrybooks.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "CUSTOMER")
public class Customer {
    // ??ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUSTOMER_ID")
    private Integer customerId;

    // ???
    @Column(name = "CUSTOMER_NAME")
    private String customerName;

    // ?????
    @Column(name = "PASSWORD")
    private String password;

    // ???????
    @Column(name = "EMAIL")
    private String email;

    // ????
    @Column(name = "BIRTHDAY")
    private LocalDate birthday;

    // ??
    @Column(name = "ADDRESS")
    private String address;

    // ???????????
    public Customer() {
    }

    // ???????
    public Customer(String customerName, String password, String email,
            LocalDate birthday, String address) {
        this.customerName = customerName;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
        this.address = address;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer [customerId=" + customerId + ", customerName=" + customerName
                + ", password=" + password + ", email=" + email + ", birthday=" + birthday
                + ", address=" + address + "]";
    }
}
