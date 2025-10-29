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

// 鬘ｧ螳｢諠・ｱ縺ｮ蜿門ｾ励・逋ｻ骭ｲ繝ｻ譖ｴ譁ｰ繝ｻ蜑企勁繧定｡後≧繧ｵ繝ｼ繝薙せ繧ｯ繝ｩ繧ｹ
@ApplicationScoped
@Transactional
public class CustomerService {
    private static final String CUSTOMER_NOT_FOUND_MESSAGE =
            "謖・ｮ壹＆繧後◆繝｡繝ｼ繝ｫ繧｢繝峨Ξ繧ｹ縺ｯ蟄伜惠縺励∪縺帙ｓ";
    private static final String CUSTOMER_EXISTS_MESSAGE =
            "謖・ｮ壹＆繧後◆繝｡繝ｼ繝ｫ繧｢繝峨Ξ繧ｹ縺ｯ縺吶〒縺ｫ蟄伜惠縺励∪縺・;

    private static final Logger logger = LoggerFactory.getLogger(
            CustomerService.class);

    @Inject
    private CustomerDao customerDao;

    @Inject
    private OrderTranDao orderTranDao;

    // 繧ｵ繝ｼ繝薙せ繝｡繧ｽ繝・ラ・夐｡ｧ螳｢繧貞叙蠕励☆繧具ｼ井ｸ諢上く繝ｼ縺九ｉ縺ｮ譚｡莉ｶ讀懃ｴ｢・・
    public Customer getCustomerById(Integer customerId) {
        logger.info("[ CustomerService#getCustomerById ]");

        // 繝｡繝ｼ繝ｫ繧｢繝峨Ξ繧ｹ縺九ｉ鬘ｧ螳｢繧ｨ繝ｳ繝・ぅ繝・ぅ繧呈､懃ｴ｢縺吶ｋ
        Customer customer = customerDao.findById(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }
        return customer;
    }

    // 繧ｵ繝ｼ繝薙せ繝｡繧ｽ繝・ラ・夐｡ｧ螳｢繧貞叙蠕励☆繧具ｼ井ｸ諢上く繝ｼ縺九ｉ縺ｮ譚｡莉ｶ讀懃ｴ｢・・
    public Customer getCustomerByEmail(String email) {
        logger.info("[ CustomerService#getCustomerByEmail ]");

        // 繝｡繝ｼ繝ｫ繧｢繝峨Ξ繧ｹ縺九ｉ鬘ｧ螳｢繧ｨ繝ｳ繝・ぅ繝・ぅ繧呈､懃ｴ｢縺吶ｋ
        Customer customer = customerDao.findCustomerByEmail(email);
        if (customer == null) {
            throw new CustomerNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }
        return customer;
    }

    // 繧ｵ繝ｼ繝薙せ繝｡繧ｽ繝・ラ・夐｡ｧ螳｢繝ｪ繧ｹ繝医ｒ蜿門ｾ励☆繧具ｼ郁ｪ慕函譌･縺九ｉ縺ｮ譚｡莉ｶ讀懃ｴ｢・・
    public List<Customer> searchCustomersFromBirthday(LocalDate from) {
        logger.info("[ CustomerService#searchCustomersFromBirthday ]");

        // 隱慕函譌･髢句ｧ区律縺九ｉ鬘ｧ螳｢繧ｨ繝ｳ繝・ぅ繝・ぅ繧呈､懃ｴ｢縺吶ｋ
        List<Customer> customers = customerDao.searchCustomersFromBirthday(from);
        return customers;
    }

    // 繧ｵ繝ｼ繝薙せ繝｡繧ｽ繝・ラ・夐｡ｧ螳｢繧呈眠隕冗匳骭ｲ縺吶ｋ
    public Customer registerCustomer(Customer customer) throws CustomerExistsException { 
        logger.info("[ CustomerService#registerCustomer ]");

        // 繝｡繝ｼ繝ｫ繧｢繝峨Ξ繧ｹ縺ｮ驥崎､・メ繧ｧ繝・け
        Customer existing = customerDao.findCustomerByEmail(customer.getEmail());
        if (existing != null) {
            throw new CustomerExistsException(CUSTOMER_EXISTS_MESSAGE);
        }

        // 蜿励￠蜿悶▲縺滄｡ｧ螳｢繧ｨ繝ｳ繝・ぅ繝・ぅ繧剃ｿ晏ｭ倥☆繧・
        customerDao.persist(customer);
        return customer;
    }

    // 繧ｵ繝ｼ繝薙せ繝｡繧ｽ繝・ラ・夐｡ｧ螳｢繧剃ｸ頑嶌縺咲匳骭ｲ縺吶ｋ
    public void replaceCustomer(Customer customer)
            throws CustomerExistsException { 
        logger.info("[ CustomerService#replaceCustomer ]");

        // 譌｢蟄倥・鬘ｧ螳｢諠・ｱ繧貞叙蠕・
        Customer existingCustomer = customerDao.findById(customer.getCustomerId());
        if (existingCustomer == null) {
            throw new CustomerNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        // 繝代せ繝ｯ繝ｼ繝我ｻ･螟悶・繝輔ぅ繝ｼ繝ｫ繝峨ｒ譖ｴ譁ｰ
        existingCustomer.setCustomerName(customer.getCustomerName());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setBirthday(customer.getBirthday());
        existingCustomer.setAddress(customer.getAddress());
        // 繝代せ繝ｯ繝ｼ繝峨・菫晄戟・域峩譁ｰ縺励↑縺・ｼ・

        // 蜿励￠蜿悶▲縺滄｡ｧ螳｢繧ｨ繝ｳ繝・ぅ繝・ぅ繧剃ｿ晏ｭ倥☆繧・
        customerDao.merge(existingCustomer);
    }

    // 繧ｵ繝ｼ繝薙せ繝｡繧ｽ繝・ラ・夐｡ｧ螳｢繧貞炎髯､縺吶ｋ
    public void deleteCustomer(Integer customerId) {
        logger.info("[ CustomerService#deleteCustomer ]");

        // 蜿励￠蜿悶▲縺滄｡ｧ螳｢ID繧偵く繝ｼ縺ｫ繧ｨ繝ｳ繝・ぅ繝・ぅ繧貞炎髯､縺吶ｋ
        Customer customer = customerDao.findById(customerId);
        if (customer != null) {
            customerDao.remove(customer);
        }
    }

    // 繧ｵ繝ｼ繝薙せ繝｡繧ｽ繝・ラ・夐｡ｧ螳｢縺ｮ豕ｨ譁・ｱ･豁ｴ繧貞叙蠕励☆繧・
    public List<OrderTran> getOrderHistory(Integer customerId) {
        logger.info("[ CustomerService#getOrderHistory ]");
        
        // 鬘ｧ螳｢縺ｮ蟄伜惠遒ｺ隱・
        Customer customer = customerDao.findById(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }
        
        return orderTranDao.findByCustomerId(customerId);
    }

    // 繧ｵ繝ｼ繝薙せ繝｡繧ｽ繝・ラ・壼・鬘ｧ螳｢繧貞叙蠕励☆繧・
    public List<Customer> getAllCustomers() {
        logger.info("[ CustomerService#getAllCustomers ]");
        return customerDao.findAll();
    }

    // 繧ｵ繝ｼ繝薙せ繝｡繧ｽ繝・ラ・夐｡ｧ螳｢縺ｮ豕ｨ譁・ｻｶ謨ｰ繧貞叙蠕励☆繧・
    public Long getOrderCount(Integer customerId) {
        logger.info("[ CustomerService#getOrderCount ]");
        return orderTranDao.countOrdersByCustomerId(customerId);
    }

    // 繧ｵ繝ｼ繝薙せ繝｡繧ｽ繝・ラ・夐｡ｧ螳｢縺ｮ雉ｼ蜈･蜀頑焚繧貞叙蠕励☆繧・
    public Long getTotalBookCount(Integer customerId) {
        logger.info("[ CustomerService#getTotalBookCount ]");
        return orderTranDao.sumBookCountByCustomerId(customerId);
    }
}

