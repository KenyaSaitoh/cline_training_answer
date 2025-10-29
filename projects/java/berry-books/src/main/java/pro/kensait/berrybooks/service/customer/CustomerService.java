package pro.kensait.berrybooks.service.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pro.kensait.berrybooks.dao.CustomerDao;
import pro.kensait.berrybooks.entity.Customer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

// ?????????????????
@ApplicationScoped
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(
            CustomerService.class);

    @Inject
    private CustomerDao customerDao;

    // ????????????????????????
    @Transactional
    public Customer registerCustomer(Customer customer) {
        logger.info("[ CustomerService#registerCustomer ]");
        
        // ??????????????
        Customer existing = customerDao.findByEmail(customer.getEmail());
        if (existing != null) {
            throw new IllegalArgumentException("????????????????????");
        }
        
        customerDao.register(customer);
        return customer;
    }

    // ?????????
    public Customer authenticate(String email, String password) {
        logger.info("[ CustomerService#authenticate ] email=" + email);
        
        Customer customer = customerDao.findByEmail(email);
        if (customer == null) {
            logger.warn("Customer not found: " + email);
            return null;
        }
        
        // ??????????
        if (!customer.getPassword().equals(password)) {
            logger.warn("Password mismatch for: " + email);
            return null;
        }
        
        return customer;
    }

    // ??ID????????
    public Customer getCustomer(Integer customerId) {
        logger.info("[ CustomerService#getCustomer ] customerId=" + customerId);
        return customerDao.findById(customerId);
    }
}
