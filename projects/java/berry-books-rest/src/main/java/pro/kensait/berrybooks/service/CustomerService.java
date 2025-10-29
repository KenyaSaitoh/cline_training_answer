package pro.kensait.berrybooks.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pro.kensait.berrybooks.dao.CustomerDao;
import pro.kensait.berrybooks.dao.OrderTranDao;
import pro.kensait.berrybooks.entity.Customer;
import pro.kensait.berrybooks.entity.OrderTran;
import pro.kensait.berrybooks.exception.CustomerExistsException;
import pro.kensait.berrybooks.exception.CustomerNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

// ??????????????????????????
@ApplicationScoped
@Transactional
public class CustomerService {
    private static final String CUSTOMER_NOT_FOUND_MESSAGE =
            "???????????????????";
    private static final String CUSTOMER_EXISTS_MESSAGE =
            "?????????????????????";

    private static final Logger logger = LoggerFactory.getLogger(
            CustomerService.class);

    @Inject
    private CustomerDao customerDao;

    @Inject
    private OrderTranDao orderTranDao;

    // ????????????????????????????
    public Customer getCustomerById(Integer customerId) {
        logger.info("[ CustomerService#getCustomerById ]");

        // ??????????????????????
        Customer customer = customerDao.findById(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }
        return customer;
    }

    // ????????????????????????????
    public Customer getCustomerByEmail(String email) {
        logger.info("[ CustomerService#getCustomerByEmail ]");

        // ??????????????????????
        Customer customer = customerDao.findCustomerByEmail(email);
        if (customer == null) {
            throw new CustomerNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }
        return customer;
    }

    // ???????????????????????????????
    public List<Customer> searchCustomersFromBirthday(LocalDate from) {
        logger.info("[ CustomerService#searchCustomersFromBirthday ]");

        // ?????????????????????
        List<Customer> customers = customerDao.searchCustomersFromBirthday(from);
        return customers;
    }

    // ??????????????????
    public Customer registerCustomer(Customer customer) throws CustomerExistsException { 
        logger.info("[ CustomerService#registerCustomer ]");

        // ??????????????
        Customer existing = customerDao.findCustomerByEmail(customer.getEmail());
        if (existing != null) {
            throw new CustomerExistsException(CUSTOMER_EXISTS_MESSAGE);
        }

        // ??????????????????
        customerDao.persist(customer);
        return customer;
    }

    // ???????????????????
    public void replaceCustomer(Customer customer)
            throws CustomerExistsException { 
        logger.info("[ CustomerService#replaceCustomer ]");

        // ??????????
        Customer existingCustomer = customerDao.findById(customer.getCustomerId());
        if (existingCustomer == null) {
            throw new CustomerNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        // ????????????????
        existingCustomer.setCustomerName(customer.getCustomerName());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setBirthday(customer.getBirthday());
        existingCustomer.setAddress(customer.getAddress());
        // ???????????????

        // ??????????????????
        customerDao.merge(existingCustomer);
    }

    // ????????????????
    public void deleteCustomer(Integer customerId) {
        logger.info("[ CustomerService#deleteCustomer ]");

        // ???????ID????????????????
        Customer customer = customerDao.findById(customerId);
        if (customer != null) {
            customerDao.remove(customer);
        }
    }

    // ?????????????????????
    public List<OrderTran> getOrderHistory(Integer customerId) {
        logger.info("[ CustomerService#getOrderHistory ]");
        
        // ???????
        Customer customer = customerDao.findById(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }
        
        return orderTranDao.findByCustomerId(customerId);
    }

    // ?????????????????
    public List<Customer> getAllCustomers() {
        logger.info("[ CustomerService#getAllCustomers ]");
        return customerDao.findAll();
    }

    // ?????????????????????
    public Long getOrderCount(Integer customerId) {
        logger.info("[ CustomerService#getOrderCount ]");
        return orderTranDao.countOrdersByCustomerId(customerId);
    }

    // ?????????????????????
    public Long getTotalBookCount(Integer customerId) {
        logger.info("[ CustomerService#getTotalBookCount ]");
        return orderTranDao.sumBookCountByCustomerId(customerId);
    }
}
