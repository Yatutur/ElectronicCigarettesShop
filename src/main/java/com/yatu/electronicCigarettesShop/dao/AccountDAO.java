package com.yatu.electronicCigarettesShop.dao;
// Generated Aug 23, 2019, 11:50:01 PM by Hibernate Tools 5.2.12.Final

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.yatu.electronicCigarettesShop.dto.AccountDTO;

/**
 * Home object for domain model class Account.
 * @see com.yatu.electronicCigarettesShop.dto.AccountDTO
 * @author Hibernate Tools
 */
@Transactional
public class AccountDAO {

//	private static final Log log = LogFactory.getLog(AccountDAO.class);

    @Autowired
    private SessionFactory sessionFactory;
    
    public AccountDTO findAccount(String userName ) {
//    	log.debug("findAccount starts");
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<AccountDTO> criteria = builder.createQuery(AccountDTO.class); 
        Root<AccountDTO> accountCrit = criteria.from(AccountDTO.class);
        criteria.select(accountCrit);
        criteria.where(builder.equal(accountCrit.get("userName"), userName));
//        log.debug("findAccount ends");
        return (AccountDTO) session.createQuery(criteria).getResultList().get(0);
    }
}
