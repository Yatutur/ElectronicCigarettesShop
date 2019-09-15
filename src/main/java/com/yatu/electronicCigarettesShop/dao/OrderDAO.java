package com.yatu.electronicCigarettesShop.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.yatu.electronicCigarettesShop.dto.OrderDTO;
import com.yatu.electronicCigarettesShop.dto.OrderDetailDTO;
import com.yatu.electronicCigarettesShop.dto.ProductDTO;
import com.yatu.electronicCigarettesShop.model.CartInfo;
import com.yatu.electronicCigarettesShop.model.CartLineInfo;
import com.yatu.electronicCigarettesShop.model.CustomerInfo;
import com.yatu.electronicCigarettesShop.model.OrderDetailInfo;
import com.yatu.electronicCigarettesShop.model.OrderInfo;
import com.yatu.electronicCigarettesShop.model.PaginationResult;

// Generated Aug 23, 2019, 11:50:01 PM by Hibernate Tools 5.2.12.Final

/**
 * Home object for domain model class Order.
 * @see com.yatu.electronicCigarettesShop.dto.OrderDTO
 * @author Hibernate Tools
 */
@Transactional
public class OrderDAO {

	private static final Log log = LogFactory.getLog(OrderDAO.class);

    @Autowired
    private SessionFactory sessionFactory;
 
    @Autowired
    private ProductDAO productDAO;
//
//	protected SessionFactory getSessionFactory() {
//		try {
//			return (SessionFactory) new InitialContext().lookup("SessionFactory");
//		} catch (Exception e) {
//			log.error("Could not locate SessionFactory in JNDI", e);
//			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
//		}
//	}
//
//	public void persist(OrderDTO transientInstance) {
//		log.debug("persisting Order instance");
//		try {
//			sessionFactory.getCurrentSession().persist(transientInstance);
//			log.debug("persist successful");
//		} catch (RuntimeException re) {
//			log.error("persist failed", re);
//			throw re;
//		}
//	}
//
//	public void attachDirty(OrderDTO instance) {
//		log.debug("attaching dirty Order instance");
//		try {
//			sessionFactory.getCurrentSession().saveOrUpdate(instance);
//			log.debug("attach successful");
//		} catch (RuntimeException re) {
//			log.error("attach failed", re);
//			throw re;
//		}
//	}
//
//	public void attachClean(OrderDTO instance) {
//		log.debug("attaching clean Order instance");
//		try {
//			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
//			log.debug("attach successful");
//		} catch (RuntimeException re) {
//			log.error("attach failed", re);
//			throw re;
//		}
//	}
//
//	public void delete(OrderDTO persistentInstance) {
//		log.debug("deleting Order instance");
//		try {
//			sessionFactory.getCurrentSession().delete(persistentInstance);
//			log.debug("delete successful");
//		} catch (RuntimeException re) {
//			log.error("delete failed", re);
//			throw re;
//		}
//	}
//
//	public OrderDTO merge(OrderDTO detachedInstance) {
//		log.debug("merging Order instance");
//		try {
//			OrderDTO result = (OrderDTO) sessionFactory.getCurrentSession().merge(detachedInstance);
//			log.debug("merge successful");
//			return result;
//		} catch (RuntimeException re) {
//			log.error("merge failed", re);
//			throw re;
//		}
//	}
//
//	public OrderDTO findById(java.lang.Integer id) {
//		log.debug("getting Order instance with id: " + id);
//		try {
//			OrderDTO instance = (OrderDTO) sessionFactory.getCurrentSession()
//					.get("com.yatu.electronicCigarettesShop.dto.Order", id);
//			if (instance == null) {
//				log.debug("get successful, no instance found");
//			} else {
//				log.debug("get successful, instance found");
//			}
//			return instance;
//		} catch (RuntimeException re) {
//			log.error("get failed", re);
//			throw re;
//		}
//	}
//
//	public List findByExample(OrderDTO instance) {
//		log.debug("finding Order instance by example");
//		try {
//			List results = sessionFactory.getCurrentSession()
//					.createCriteria("com.yatu.electronicCigarettesShop.dto.Order").add(Example.create(instance)).list();
//			log.debug("find by example successful, result size: " + results.size());
//			return results;
//		} catch (RuntimeException re) {
//			log.error("find by example failed", re);
//			throw re;
//		}
//	}
	
    private int getMaxOrderNum() {
        String sql = "Select COALESCE(max(o.orderNum), 0) from " + OrderDTO.class.getName() + " o ";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        Integer value = (Integer) query.uniqueResult();
        if (value == null) {
            return 0;
        }
        return value;
    }
	
    public void saveOrder(CartInfo cartInfo) {
        Session session = this.sessionFactory.getCurrentSession();
 
        int orderNum = this.getMaxOrderNum() + 1;
        OrderDTO order = new OrderDTO();
 
        //order.setId(UUID.randomUUID());
        order.setOrderNum(orderNum);
        order.setOrderDate(new Date());
        order.setAmount(cartInfo.getAmountTotal());
 
        CustomerInfo customerInfo = cartInfo.getCustomerInfo();
        order.setCustomerName(customerInfo.getName());
        order.setCustomerEmail(customerInfo.getEmail());
        order.setCustomerPhone(customerInfo.getPhone());
        order.setCustomerAddress(customerInfo.getAddress());
 
        session.persist(order);
        
        log.debug("Order Master inserted");
        
        // We need to find the order which we just inserted, because mysql auto increment id by itself
        order = this.findOrderByOrderNum(orderNum);
 
        List<CartLineInfo> lines = cartInfo.getCartLines();
 
        // Setting ID to every line because other way, hibernate thinks that is the same entity
//        int lineId = 0;
        for (CartLineInfo line : lines) {
        	log.debug("Order Detail starting");
            OrderDetailDTO detail = new OrderDetailDTO();
            //detail.setId(UUID.randomUUID().toString());
//            detail.setId(lineId);
            detail.setOrder(order);
            detail.setAmount(line.getAmount());
            detail.setPrice(line.getProductInfo().getPrice());
            detail.setQuantity(line.getQuantity());
 
            int id = line.getProductInfo().getId();
            ProductDTO product = this.productDAO.findById(id);
            detail.setProduct(product);
            log.debug("Order Detail ending");
 
            session.persist(detail);
//            lineId++;
        }
 
        // Set OrderNum for report.
        cartInfo.setOrderNum(orderNum);
    }
    
    public PaginationResult<OrderInfo> listOrderInfo(int page, int maxResult, int maxNavigationPage) {
        String sql = "Select new " + OrderInfo.class.getName()//
                + "(ord.id, ord.orderDate, ord.orderNum, ord.amount, "
                + " ord.customerName, ord.customerAddress, ord.customerEmail, ord.customerPhone) " + " from "
                + OrderDTO.class.getName() + " ord "//
                + " order by ord.orderNum desc";
        Session session = this.sessionFactory.getCurrentSession();
 
        Query query = session.createQuery(sql);
 
        return new PaginationResult<OrderInfo>(query, page, maxResult, maxNavigationPage);
    }
    
    public OrderDTO findOrderById(int orderId) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<OrderDTO> criteria = builder.createQuery(OrderDTO.class);
        Root<OrderDTO> orderCrit = criteria.from(OrderDTO.class);
        criteria.select(orderCrit);
        criteria.where(builder.equal(orderCrit.get("id"), orderId));
        return (OrderDTO) session.createQuery(criteria).getResultList().get(0);
    }
    
    public OrderDTO findOrderByOrderNum(int orderNum) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<OrderDTO> criteria = builder.createQuery(OrderDTO.class);
        Root<OrderDTO> orderCrit = criteria.from(OrderDTO.class);
        criteria.select(orderCrit);
        criteria.where(builder.equal(orderCrit.get("orderNum"), orderNum));
        return (OrderDTO) session.createQuery(criteria).getResultList().get(0);
    }
    
    public OrderInfo getOrderInfo(int orderId) {
        OrderDTO order = this.findOrderById(orderId);
        if (order == null) {
            return null;
        }
        return new OrderInfo(order.getId(), order.getOrderDate(), //
                order.getOrderNum(), order.getAmount(), order.getCustomerName(), //
                order.getCustomerAddress(), order.getCustomerEmail(), order.getCustomerPhone());
    }
 
    public List<OrderDetailInfo> listOrderDetailInfos(int orderId) {
        String sql = "Select new " + OrderDetailInfo.class.getName() //
                + " (d.id, d.product.id, d.product.name , d.quantity, d.price, d.amount) "//
                + " from " + OrderDetailDTO.class.getName() + " d "//
                + " where d.order.id = :orderId ";
 
        Session session = this.sessionFactory.getCurrentSession();
 
        Query query = session.createQuery(sql);
        query.setParameter("orderId", orderId);
 
        return query.list();
    }
}
