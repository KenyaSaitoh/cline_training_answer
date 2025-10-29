package pro.kensait.berrybooks.web.login;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pro.kensait.berrybooks.entity.Customer;
import pro.kensait.berrybooks.service.customer.CustomerService;
import pro.kensait.berrybooks.web.customer.CustomerBean;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// ????????????Bean
@Named
@SessionScoped
public class LoginBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(
            LoginBean.class);

    @Inject
    private CustomerService customerService;

    @Inject
    private CustomerBean customerBean;

    // ????????????
    @NotBlank(message = "????????????????")
    @Email(message = "???????????????????")
    private String email;
    
    @NotBlank(message = "??????????????")
    private String password;

    // ?????????
    private boolean loggedIn = false;

    // ??????
    public String processLogin() {
        logger.info("[ LoginBean#processLogin ] email=" + email);

        try {
            Customer customer = customerService.authenticate(email, password);
            
            if (customer == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "???????????",
                                "????????????????????????"));
                return null;
            }

            // CustomerBean????????
            customerBean.setCustomer(customer);
            loggedIn = true;

            logger.info("Login successful: " + customer.getCustomerName());
            
            // ??????????
            return "bookSelect?faces-redirect=true";

        } catch (Exception e) {
            logger.error("Login error", e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "??????????", e.getMessage()));
            return null;
        }
    }

    // ???????
    public String processLogout() {
        logger.info("[ LoginBean#processLogout ]");
        
        // ?????????
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        
        // ?????????
        return "index?faces-redirect=true";
    }

    // Getters and Setters
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

    public boolean isLoggedIn() {
        return loggedIn;
    }
}
