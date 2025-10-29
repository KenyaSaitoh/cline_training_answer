package pro.kensait.berrybooks.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pro.kensait.berrybooks.entity.Customer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

// ???????????????DAO???
@ApplicationScoped
public class CustomerDao {
    private static final Logger logger = LoggerFactory.getLogger(
            CustomerDao.class);

    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;

    // ???????????????
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

    // ??ID????????
    public Customer findById(Integer customerId) {
        logger.info("[ CustomerDao#findById ] customerId=" + customerId);
        return em.find(Customer.class, customerId);
    }

    // ???????
    public void register(Customer customer) {
        logger.info("[ CustomerDao#register ] customer=" + customer);
        em.persist(customer);
    }

    // ???????
    public void update(Customer customer) {
        logger.info("[ CustomerDao#update ] customer=" + customer);
        em.merge(customer);
    }
}
