package pro.kensait.berrybooks.service.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pro.kensait.berrybooks.dao.CustomerDao;
import pro.kensait.berrybooks.entity.Customer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

// 鬘ｧ螳｢逋ｻ骭ｲ縺ｨ隱崎ｨｼ繧定｡後≧繧ｵ繝ｼ繝薙せ繧ｯ繝ｩ繧ｹ
@ApplicationScoped
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(
            CustomerService.class);

    @Inject
    private CustomerDao customerDao;

    // 鬘ｧ螳｢繧堤匳骭ｲ縺吶ｋ・医Γ繝ｼ繝ｫ繧｢繝峨Ξ繧ｹ驥崎､・メ繧ｧ繝・け蜷ｫ繧・・
    @Transactional
    public Customer registerCustomer(Customer customer) {
        logger.info("[ CustomerService#registerCustomer ]");
        
        // 繝｡繝ｼ繝ｫ繧｢繝峨Ξ繧ｹ縺ｮ驥崎､・メ繧ｧ繝・け
        Customer existing = customerDao.findByEmail(customer.getEmail());
        if (existing != null) {
            throw new IllegalArgumentException("縺薙・繝｡繝ｼ繝ｫ繧｢繝峨Ξ繧ｹ縺ｯ譌｢縺ｫ逋ｻ骭ｲ縺輔ｌ縺ｦ縺・∪縺・);
        }
        
        customerDao.register(customer);
        return customer;
    }

    // 繝ｭ繧ｰ繧､繝ｳ隱崎ｨｼ繧定｡後≧
    public Customer authenticate(String email, String password) {
        logger.info("[ CustomerService#authenticate ] email=" + email);
        
        Customer customer = customerDao.findByEmail(email);
        if (customer == null) {
            logger.warn("Customer not found: " + email);
            return null;
        }
        
        // 蟷ｳ譁・〒繝代せ繝ｯ繝ｼ繝峨ｒ讀懆ｨｼ
        if (!customer.getPassword().equals(password)) {
            logger.warn("Password mismatch for: " + email);
            return null;
        }
        
        return customer;
    }

    // 鬘ｧ螳｢ID縺ｧ鬘ｧ螳｢繧貞叙蠕励☆繧・
    public Customer getCustomer(Integer customerId) {
        logger.info("[ CustomerService#getCustomer ] customerId=" + customerId);
        return customerDao.findById(customerId);
    }
}

