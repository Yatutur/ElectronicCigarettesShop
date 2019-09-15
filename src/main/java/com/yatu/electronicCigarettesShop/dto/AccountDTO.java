package com.yatu.electronicCigarettesShop.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// Generated Aug 23, 2019, 11:49:59 PM by Hibernate Tools 5.2.12.Final

/**
 * Account generated by hbm2java
 */
@Entity
@Table(name = "account")
public class AccountDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -111647953260408825L;
	private Integer id;
	private String userName;
	private boolean active;
	private String password;
	private String userRole;

	public AccountDTO() {
	}

	public AccountDTO(String userName, boolean active, String password, String userRole) {
		this.userName = userName;
		this.active = active;
		this.password = password;
		this.userRole = userRole;
	}

    @Id
    @Column(name = "id", length = 11)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "user_name", length = 20, nullable = false)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "active", length = 1, nullable = false)
	public boolean isActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Column(name = "password", length = 20, nullable = false)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "user_role", length = 20, nullable = false)
	public String getUserRole() {
		return this.userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

}