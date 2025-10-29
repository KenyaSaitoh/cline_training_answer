package pro.kensait.berrybooks.web.customer;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pro.kensait.berrybooks.entity.Customer;
import pro.kensait.berrybooks.service.customer.CustomerService;
import pro.kensait.berrybooks.util.AddressUtil;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// 顧客登録画面のバッキングBean
@Named
@SessionScoped
public class CustomerBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(
            CustomerBean.class);

    @Inject
    private CustomerService customerService;

    // 現在ログイン中の顧客
    private Customer customer;

    // 登録フォームの入力値
    @NotBlank(message = "顧客名を入力してください")
    @Size(max = 50, message = "顧客名�E50斁E��以冁E��入力してください")
    private String customerName;
    
    @NotBlank(message = "メールアドレスを�E力してください")
    @Email(message = "有効なメールアドレスを�E力してください")
    @Size(max = 100, message = "メールアドレスは100斁E��以冁E��入力してください")
    private String email;
    
    @NotBlank(message = "パスワードを入力してください")
    @Size(min = 8, max = 20, message = "パスワード�E8斁E��以丁E0斁E��以冁E��入力してください")
    private String password;
    
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$|^$", 
             message = "生年月日はyyyy-MM-dd形式で入力してください�E�例！E990-01-15�E�E)
    private String birthday; // yyyy-MM-dd形式�E斁E���E
    
    @Size(max = 200, message = "住所は200斁E��以冁E��入力してください")
    private String address;

    /**
     * 顧客登録処琁E
     * ※基本皁E��バリチE�EションはBean Validationで自動的に実行される
     */
    public String register() {
        logger.info("[ CustomerBean#register ]");

        try {
            // 住所に対する入力チェチE���E�正しい都道府県名で始まってぁE��か！E
            if (address != null && !address.isBlank() && !AddressUtil.startsWithValidPrefecture(address)) {
                logger.info("[ CustomerBean#register ] 住所入力エラー");
                addErrorMessage("住所は正しい都道府県名で始まる忁E��がありまぁE);
                return null;
            }

            // CustomerエンチE��チE��を生戁E
            Customer newCustomer = new Customer();
            newCustomer.setCustomerName(customerName);
            newCustomer.setEmail(email);
            newCustomer.setPassword(password); // TODO: パスワードエンコーチE��ング
            
            // 生年月日のパ�Eス�E�EPatternで形式チェチE��済み�E�E
            if (birthday != null && !birthday.isEmpty()) {
                try {
                    LocalDate birthDate = LocalDate.parse(birthday, 
                            DateTimeFormatter.ISO_LOCAL_DATE);
                    newCustomer.setBirthday(birthDate);
                } catch (Exception e) {
                    logger.warn("Birthday parse error: " + birthday, e);
                    addErrorMessage("生年月日の形式が正しくありません�E�例！E990-01-15�E�E);
                    return null;
                }
            }
            
            newCustomer.setAddress(address);

            // 顧客登録
            customer = customerService.registerCustomer(newCustomer);

            logger.info("Customer registered: " + customer);

            // 登録完亁E�Eージへ遷移
            return "customerOutput?faces-redirect=true";

        } catch (IllegalArgumentException e) {
            logger.error("Registration error", e);
            addErrorMessage(e.getMessage());
            return null;
        } catch (Exception e) {
            logger.error("Registration error", e);
            addErrorMessage("登録中にエラーが発生しました");
            return null;
        }
    }

    /**
     * エラーメチE��ージを追加
     */
    private void addErrorMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }

    // Getters and Setters
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

