package pro.kensait.berrybooks.model;

// 顧客更新用の転送オブジェクト
public class CustomerTO {
    private String customerName;
    private String email;
    private String birthDate; // yyyy-MM-dd形式
    private String address;

    // デフォルトコンストラクタ
    public CustomerTO() {}

    // コンストラクタ
    public CustomerTO(String customerName, String email, String birthDate, String address) {
        this.customerName = customerName;
        this.email = email;
        this.birthDate = birthDate;
        this.address = address;
    }

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
