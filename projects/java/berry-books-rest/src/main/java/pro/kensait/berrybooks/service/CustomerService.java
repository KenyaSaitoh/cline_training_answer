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

// 顧客惁E��の取得�E登録・更新・削除を行うサービスクラス
@ApplicationScoped
@Transactional
public class CustomerService {
    private static final String CUSTOMER_NOT_FOUND_MESSAGE =
            "持E��されたメールアドレスは存在しません";
    private static final String CUSTOMER_EXISTS_MESSAGE =
            "持E��されたメールアドレスはすでに存在しまぁE;

    private static final Logger logger = LoggerFactory.getLogger(
            CustomerService.class);

    @Inject
    private CustomerDao customerDao;

    @Inject
    private OrderTranDao orderTranDao;

    // サービスメソチE���E�顧客を取得する（一意キーからの条件検索�E�E
    public Customer getCustomerById(Integer customerId) {
        logger.info("[ CustomerService#getCustomerById ]");

        // メールアドレスから顧客エンチE��チE��を検索する
        Customer customer = customerDao.findById(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }
        return customer;
    }

    // サービスメソチE���E�顧客を取得する（一意キーからの条件検索�E�E
    public Customer getCustomerByEmail(String email) {
        logger.info("[ CustomerService#getCustomerByEmail ]");

        // メールアドレスから顧客エンチE��チE��を検索する
        Customer customer = customerDao.findCustomerByEmail(email);
        if (customer == null) {
            throw new CustomerNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }
        return customer;
    }

    // サービスメソチE���E�顧客リストを取得する（誕生日からの条件検索�E�E
    public List<Customer> searchCustomersFromBirthday(LocalDate from) {
        logger.info("[ CustomerService#searchCustomersFromBirthday ]");

        // 誕生日開始日から顧客エンチE��チE��を検索する
        List<Customer> customers = customerDao.searchCustomersFromBirthday(from);
        return customers;
    }

    // サービスメソチE���E�顧客を新規登録する
    public Customer registerCustomer(Customer customer) throws CustomerExistsException { 
        logger.info("[ CustomerService#registerCustomer ]");

        // メールアドレスの重褁E��ェチE��
        Customer existing = customerDao.findCustomerByEmail(customer.getEmail());
        if (existing != null) {
            throw new CustomerExistsException(CUSTOMER_EXISTS_MESSAGE);
        }

        // 受け取った顧客エンチE��チE��を保存すめE
        customerDao.persist(customer);
        return customer;
    }

    // サービスメソチE���E�顧客を上書き登録する
    public void replaceCustomer(Customer customer)
            throws CustomerExistsException { 
        logger.info("[ CustomerService#replaceCustomer ]");

        // 既存�E顧客惁E��を取征E
        Customer existingCustomer = customerDao.findById(customer.getCustomerId());
        if (existingCustomer == null) {
            throw new CustomerNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        // パスワード以外�Eフィールドを更新
        existingCustomer.setCustomerName(customer.getCustomerName());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setBirthday(customer.getBirthday());
        existingCustomer.setAddress(customer.getAddress());
        // パスワード�E保持�E�更新しなぁE��E

        // 受け取った顧客エンチE��チE��を保存すめE
        customerDao.merge(existingCustomer);
    }

    // サービスメソチE���E�顧客を削除する
    public void deleteCustomer(Integer customerId) {
        logger.info("[ CustomerService#deleteCustomer ]");

        // 受け取った顧客IDをキーにエンチE��チE��を削除する
        Customer customer = customerDao.findById(customerId);
        if (customer != null) {
            customerDao.remove(customer);
        }
    }

    // サービスメソチE���E�顧客の注斁E��歴を取得すめE
    public List<OrderTran> getOrderHistory(Integer customerId) {
        logger.info("[ CustomerService#getOrderHistory ]");
        
        // 顧客の存在確誁E
        Customer customer = customerDao.findById(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }
        
        return orderTranDao.findByCustomerId(customerId);
    }

    // サービスメソチE���E��E顧客を取得すめE
    public List<Customer> getAllCustomers() {
        logger.info("[ CustomerService#getAllCustomers ]");
        return customerDao.findAll();
    }

    // サービスメソチE���E�顧客の注斁E��数を取得すめE
    public Long getOrderCount(Integer customerId) {
        logger.info("[ CustomerService#getOrderCount ]");
        return orderTranDao.countOrdersByCustomerId(customerId);
    }

    // サービスメソチE���E�顧客の購入冊数を取得すめE
    public Long getTotalBookCount(Integer customerId) {
        logger.info("[ CustomerService#getTotalBookCount ]");
        return orderTranDao.sumBookCountByCustomerId(customerId);
    }
}

