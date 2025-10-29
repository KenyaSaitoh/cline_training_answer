package pro.kensait.berrybooks.dao;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pro.kensait.berrybooks.entity.Customer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

// 鬘ｧ螳｢繝・・繝悶Ν縺ｸ縺ｮ繧｢繧ｯ繧ｻ繧ｹ繧定｡後≧DAO繧ｯ繝ｩ繧ｹ
@ApplicationScoped
public class CustomerDao {
    private static final Logger logger = LoggerFactory.getLogger(
            CustomerDao.class);

    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;

    // DAO繝｡繧ｽ繝・ラ・夐｡ｧ螳｢繧剃ｸｻ繧ｭ繝ｼ縺ｧ讀懃ｴ｢
    public Customer findById(Integer customerId) {
        logger.info("[ CustomerDao#findById ]");
        return em.find(Customer.class, customerId);
    }

    // DAO繝｡繧ｽ繝・ラ・夐｡ｧ螳｢繧偵Γ繝ｼ繝ｫ繧｢繝峨Ξ繧ｹ縺ｧ讀懃ｴ｢
    public Customer findCustomerByEmail(String email) {
        logger.info("[ CustomerDao#findCustomerByEmail ]");
        
        TypedQuery<Customer> query = em.createQuery(
                "SELECT c FROM Customer c WHERE c.email = :email", Customer.class);
        query.setParameter("email", email);
        
        List<Customer> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    // DAO繝｡繧ｽ繝・ラ・夊ｪ慕函譌･莉･髯阪・鬘ｧ螳｢繧呈､懃ｴ｢
    public List<Customer> searchCustomersFromBirthday(LocalDate from) {
        logger.info("[ CustomerDao#searchCustomersFromBirthday ]");
        
        TypedQuery<Customer> query = em.createQuery(
                "SELECT c FROM Customer c WHERE :from <= c.birthday", Customer.class);
        query.setParameter("from", from);
        
        return query.getResultList();
    }

    // DAO繝｡繧ｽ繝・ラ・夐｡ｧ螳｢繧呈眠隕冗匳骭ｲ
    public void persist(Customer customer) {
        logger.info("[ CustomerDao#persist ]");
        em.persist(customer);
    }

    // DAO繝｡繧ｽ繝・ラ・夐｡ｧ螳｢繧呈峩譁ｰ
    public Customer merge(Customer customer) {
        logger.info("[ CustomerDao#merge ]");
        return em.merge(customer);
    }

    // DAO繝｡繧ｽ繝・ラ・夐｡ｧ螳｢繧貞炎髯､
    public void remove(Customer customer) {
        logger.info("[ CustomerDao#remove ]");
        em.remove(customer);
    }

    // DAO繝｡繧ｽ繝・ラ・壼・鬘ｧ螳｢繧貞叙蠕・
    public List<Customer> findAll() {
        logger.info("[ CustomerDao#findAll ]");
        
        TypedQuery<Customer> query = em.createQuery(
                "SELECT c FROM Customer c", Customer.class);
        
        return query.getResultList();
    }
}

