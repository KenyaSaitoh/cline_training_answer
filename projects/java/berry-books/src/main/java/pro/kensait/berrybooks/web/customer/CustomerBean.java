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

// 鬘ｧ螳｢逋ｻ骭ｲ逕ｻ髱｢縺ｮ繝舌ャ繧ｭ繝ｳ繧ｰBean
@Named
@SessionScoped
public class CustomerBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(
            CustomerBean.class);

    @Inject
    private CustomerService customerService;

    // 迴ｾ蝨ｨ繝ｭ繧ｰ繧､繝ｳ荳ｭ縺ｮ鬘ｧ螳｢
    private Customer customer;

    // 逋ｻ骭ｲ繝輔か繝ｼ繝縺ｮ蜈･蜉帛､
    @NotBlank(message = "鬘ｧ螳｢蜷阪ｒ蜈･蜉帙＠縺ｦ縺上□縺輔＞")
    @Size(max = 50, message = "鬘ｧ螳｢蜷阪・50譁・ｭ嶺ｻ･蜀・〒蜈･蜉帙＠縺ｦ縺上□縺輔＞")
    private String customerName;
    
    @NotBlank(message = "繝｡繝ｼ繝ｫ繧｢繝峨Ξ繧ｹ繧貞・蜉帙＠縺ｦ縺上□縺輔＞")
    @Email(message = "譛牙柑縺ｪ繝｡繝ｼ繝ｫ繧｢繝峨Ξ繧ｹ繧貞・蜉帙＠縺ｦ縺上□縺輔＞")
    @Size(max = 100, message = "繝｡繝ｼ繝ｫ繧｢繝峨Ξ繧ｹ縺ｯ100譁・ｭ嶺ｻ･蜀・〒蜈･蜉帙＠縺ｦ縺上□縺輔＞")
    private String email;
    
    @NotBlank(message = "繝代せ繝ｯ繝ｼ繝峨ｒ蜈･蜉帙＠縺ｦ縺上□縺輔＞")
    @Size(min = 8, max = 20, message = "繝代せ繝ｯ繝ｼ繝峨・8譁・ｭ嶺ｻ･荳・0譁・ｭ嶺ｻ･蜀・〒蜈･蜉帙＠縺ｦ縺上□縺輔＞")
    private String password;
    
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$|^$", 
             message = "逕溷ｹｴ譛域律縺ｯyyyy-MM-dd蠖｢蠑上〒蜈･蜉帙＠縺ｦ縺上□縺輔＞・井ｾ具ｼ・990-01-15・・)
    private String birthday; // yyyy-MM-dd蠖｢蠑上・譁・ｭ怜・
    
    @Size(max = 200, message = "菴乗園縺ｯ200譁・ｭ嶺ｻ･蜀・〒蜈･蜉帙＠縺ｦ縺上□縺輔＞")
    private String address;

    /**
     * 鬘ｧ螳｢逋ｻ骭ｲ蜃ｦ逅・
     * 窶ｻ蝓ｺ譛ｬ逧・↑繝舌Μ繝・・繧ｷ繝ｧ繝ｳ縺ｯBean Validation縺ｧ閾ｪ蜍慕噪縺ｫ螳溯｡後＆繧後ｋ
     */
    public String register() {
        logger.info("[ CustomerBean#register ]");

        try {
            // 菴乗園縺ｫ蟇ｾ縺吶ｋ蜈･蜉帙メ繧ｧ繝・け・域ｭ｣縺励＞驛ｽ驕灘ｺ懃恁蜷阪〒蟋九∪縺｣縺ｦ縺・ｋ縺具ｼ・
            if (address != null && !address.isBlank() && !AddressUtil.startsWithValidPrefecture(address)) {
                logger.info("[ CustomerBean#register ] 菴乗園蜈･蜉帙お繝ｩ繝ｼ");
                addErrorMessage("菴乗園縺ｯ豁｣縺励＞驛ｽ驕灘ｺ懃恁蜷阪〒蟋九∪繧句ｿ・ｦ√′縺ゅｊ縺ｾ縺・);
                return null;
            }

            // Customer繧ｨ繝ｳ繝・ぅ繝・ぅ繧堤函謌・
            Customer newCustomer = new Customer();
            newCustomer.setCustomerName(customerName);
            newCustomer.setEmail(email);
            newCustomer.setPassword(password); // TODO: 繝代せ繝ｯ繝ｼ繝峨お繝ｳ繧ｳ繝ｼ繝・ぅ繝ｳ繧ｰ
            
            // 逕溷ｹｴ譛域律縺ｮ繝代・繧ｹ・・Pattern縺ｧ蠖｢蠑上メ繧ｧ繝・け貂医∩・・
            if (birthday != null && !birthday.isEmpty()) {
                try {
                    LocalDate birthDate = LocalDate.parse(birthday, 
                            DateTimeFormatter.ISO_LOCAL_DATE);
                    newCustomer.setBirthday(birthDate);
                } catch (Exception e) {
                    logger.warn("Birthday parse error: " + birthday, e);
                    addErrorMessage("逕溷ｹｴ譛域律縺ｮ蠖｢蠑上′豁｣縺励￥縺ゅｊ縺ｾ縺帙ｓ・井ｾ具ｼ・990-01-15・・);
                    return null;
                }
            }
            
            newCustomer.setAddress(address);

            // 鬘ｧ螳｢逋ｻ骭ｲ
            customer = customerService.registerCustomer(newCustomer);

            logger.info("Customer registered: " + customer);

            // 逋ｻ骭ｲ螳御ｺ・・繝ｼ繧ｸ縺ｸ驕ｷ遘ｻ
            return "customerOutput?faces-redirect=true";

        } catch (IllegalArgumentException e) {
            logger.error("Registration error", e);
            addErrorMessage(e.getMessage());
            return null;
        } catch (Exception e) {
            logger.error("Registration error", e);
            addErrorMessage("逋ｻ骭ｲ荳ｭ縺ｫ繧ｨ繝ｩ繝ｼ縺檎匱逕溘＠縺ｾ縺励◆");
            return null;
        }
    }

    /**
     * 繧ｨ繝ｩ繝ｼ繝｡繝・そ繝ｼ繧ｸ繧定ｿｽ蜉
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

