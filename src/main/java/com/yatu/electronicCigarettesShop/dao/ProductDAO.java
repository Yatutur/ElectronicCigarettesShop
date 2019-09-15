package com.yatu.electronicCigarettesShop.dao;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.yatu.electronicCigarettesShop.dto.ProductDTO;
import com.yatu.electronicCigarettesShop.model.PaginationResult;
import com.yatu.electronicCigarettesShop.model.ProductInfo;

// Generated Aug 23, 2019, 11:50:01 PM by Hibernate Tools 5.2.12.Final

/**
 * Home object for domain model class Product.
 * @see com.yatu.electronicCigarettesShop.dto.ProductDTO
 * @author Hibernate Tools
 */
@Transactional
public class ProductDAO {

	private static final Log log = LogFactory.getLog(ProductDAO.class);

    @Autowired
    private SessionFactory sessionFactory;

//	protected SessionFactory getSessionFactory() {
//		try {
//			return (SessionFactory) new InitialContext().lookup("SessionFactory");
//		} catch (Exception e) {
//			log.error("Could not locate SessionFactory in JNDI", e);
//			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
//		}
//	}
//
//	public void persist(ProductDTO transientInstance) {
//		log.debug("persisting Product instance");
//		try {
//			sessionFactory.getCurrentSession().persist(transientInstance);
//			log.debug("persist successful");
//		} catch (RuntimeException re) {
//			log.error("persist failed", re);
//			throw re;
//		}
//	}
//
//	public void attachDirty(ProductDTO instance) {
//		log.debug("attaching dirty Product instance");
//		try {
//			sessionFactory.getCurrentSession().saveOrUpdate(instance);
//			log.debug("attach successful");
//		} catch (RuntimeException re) {
//			log.error("attach failed", re);
//			throw re;
//		}
//	}
//
//	public void attachClean(ProductDTO instance) {
//		log.debug("attaching clean Product instance");
//		try {
//			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
//			log.debug("attach successful");
//		} catch (RuntimeException re) {
//			log.error("attach failed", re);
//			throw re;
//		}
//	}
//
//	public void delete(ProductDTO persistentInstance) {
//		log.debug("deleting Product instance");
//		try {
//			sessionFactory.getCurrentSession().delete(persistentInstance);
//			log.debug("delete successful");
//		} catch (RuntimeException re) {
//			log.error("delete failed", re);
//			throw re;
//		}
//	}
//
//	public ProductDTO merge(ProductDTO detachedInstance) {
//		log.debug("merging Product instance");
//		try {
//			ProductDTO result = (ProductDTO) sessionFactory.getCurrentSession().merge(detachedInstance);
//			log.debug("merge successful");
//			return result;
//		} catch (RuntimeException re) {
//			log.error("merge failed", re);
//			throw re;
//		}
//	}
//
	public ProductDTO findById(int id) {
		log.debug("getting Product instance with id: " + id);
		try {
			ProductDTO instance = (ProductDTO) sessionFactory.getCurrentSession()
					.get("com.yatu.electronicCigarettesShop.dto.ProductDTO", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
//
//	public List findByExample(ProductDTO instance) {
//		log.debug("finding Product instance by example");
//		try {
//			List results = sessionFactory.getCurrentSession()
//					.createCriteria("com.yatu.electronicCigarettesShop.dto.ProductDTO").add(Example.create(instance))
//					.list();
//			log.debug("find by example successful, result size: " + results.size());
//			return results;
//		} catch (RuntimeException re) {
//			log.error("find by example failed", re);
//			throw re;
//		}
//	}
    public ProductInfo findProductInfo(int id) {
        ProductDTO product = this.findById(id);
        if (product == null) {
            return null;
        }
        return new ProductInfo(product.getId(), product.getName(), product.getPrice());
    }
	
    public void save(ProductInfo productInfo) {
        int id = productInfo.getId();
 
        ProductDTO product = null;
 
        boolean isNew = false;
        if (id != 0) {
            product = this.findById(id);
        }
        if (product == null) {
            isNew = true;
            product = new ProductDTO();
            product.setCreateDate(new Date());
        }
        product.setId(id);
        product.setName(productInfo.getName());
        product.setPrice(productInfo.getPrice());
 
        if (productInfo.getFileData() != null) {
            byte[] image = productInfo.getFileData().getBytes();
            if (image != null && image.length > 0) {
                product.setImage(image);
            }
        }
        if (isNew) {
            this.sessionFactory.getCurrentSession().persist(product);
        }
        // If error in DB, Exceptions will be thrown out immediately
        this.sessionFactory.getCurrentSession().flush();
    }
    
    public PaginationResult<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage,
            String likeName) {
        String sql = "Select new " + ProductInfo.class.getName() //
                + "(p.id, p.name, p.price) " + " from "//
                + ProductDTO.class.getName() + " p ";
        if (likeName != null && likeName.length() > 0) {
            sql += " Where lower(p.name) like :likeName ";
        }
        sql += " order by p.createDate desc ";
        //
        Session session = sessionFactory.getCurrentSession();
 
        Query query = session.createQuery(sql);
        if (likeName != null && likeName.length() > 0) {
            query.setParameter("likeName", "%" + likeName.toLowerCase() + "%");
        }
        return new PaginationResult<ProductInfo>(query, page, maxResult, maxNavigationPage);
    }
 
    public PaginationResult<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage) {
        return queryProducts(page, maxResult, maxNavigationPage, null);
    }
}
