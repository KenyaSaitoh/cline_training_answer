package pro.kensait.berrybooks.model;

/**
 * ??????????????
 */
public class CustomerTO {
    private String customerName;
    private String email;
    private String birthDate; // yyyy-MM-dd??
    private String address;

    // ????????????
    public CustomerTO() {}

    // ???????
    public CustomerTO(String customerName, String email, String birthDate, String address) {
        this.customerName = customerName;
        this.email = email;
        this.birthDate = birthDate;
        this.address = address;
    }

    // Getter/Setter
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
