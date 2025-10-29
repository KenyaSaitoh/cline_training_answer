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

// ????????????Bean
@Named
@SessionScoped
public class CustomerBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(
            CustomerBean.class);

    @Inject
    private CustomerService customerService;

    // ??????????
    private Customer customer;

    // ??????????
    @NotBlank(message = "????????????")
    @Size(max = 50, message = "????50?????????????")
    private String customerName;
    
    @NotBlank(message = "????????????????")
    @Email(message = "???????????????????")
    @Size(max = 100, message = "????????100?????????????")
    private String email;
    
    @NotBlank(message = "??????????????")
    @Size(min = 8, max = 20, message = "??????8????20?????????????")
    private String password;
    
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$|^$", 
             message = "?????yyyy-MM-dd??????????????1990-01-15?")
    private String birthday; // yyyy-MM-dd??????
    
    @Size(max = 200, message = "???200?????????????")
    private String address;

    /**
     * ??????
     * ?????????????Bean Validation??????????
     */
    public String register() {
        logger.info("[ CustomerBean#register ]");

        try {
            // ??????????????????????????????
            if (address != null && !address.isBlank() && !AddressUtil.startsWithValidPrefecture(address)) {
                logger.info("[ CustomerBean#register ] ???????");
                addErrorMessage("??????????????????????");
                return null;
            }

            // Customer?????????
            Customer newCustomer = new Customer();
            newCustomer.setCustomerName(customerName);
            newCustomer.setEmail(email);
            newCustomer.setPassword(password); // TODO: ?????????????
            
            // ?????????Pattern??????????
            if (birthday != null && !birthday.isEmpty()) {
                try {
                    LocalDate birthDate = LocalDate.parse(birthday, 
                            DateTimeFormatter.ISO_LOCAL_DATE);
                    newCustomer.setBirthday(birthDate);
                } catch (Exception e) {
                    logger.warn("Birthday parse error: " + birthday, e);
                    addErrorMessage("???????????????????1990-01-15?");
                    return null;
                }
            }
            
            newCustomer.setAddress(address);

            // ????
            customer = customerService.registerCustomer(newCustomer);

            logger.info("Customer registered: " + customer);

            // ??????????
            return "customerOutput?faces-redirect=true";

        } catch (IllegalArgumentException e) {
            logger.error("Registration error", e);
            addErrorMessage(e.getMessage());
            return null;
        } catch (Exception e) {
            logger.error("Registration error", e);
            addErrorMessage("??????????????");
            return null;
        }
    }

    /**
     * ???????????
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
