package pro.kensait.berrybooks.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pro.kensait.berrybooks.entity.Customer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

// 顧客チE�Eブルへのアクセスを行うDAOクラス
@ApplicationScoped
public class CustomerDao {
    private static final Logger logger = LoggerFactory.getLogger(
            CustomerDao.class);

    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;

    // メールアドレスで顧客を検索する
    public Customer findByEmail(String email) {
        logger.info("[ CustomerDao#findByEmail ] email=" + email);
        
        TypedQuery<Customer> query = em.createQuery(
                "SELECT c FROM Customer c WHERE c.email = :email",
                Customer.class);
        query.setParameter("email", email);
        
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    // 顧客IDで顧客を検索する
    public Customer findById(Integer customerId) {
        logger.info("[ CustomerDao#findById ] customerId=" + customerId);
        return em.find(Customer.class, customerId);
    }

    // 顧客を登録する
    public void register(Customer customer) {
        logger.info("[ CustomerDao#register ] customer=" + customer);
        em.persist(customer);
    }

    // 顧客を更新する
    public void update(Customer customer) {
        logger.info("[ CustomerDao#update ] customer=" + customer);
        em.merge(customer);
    }
}

