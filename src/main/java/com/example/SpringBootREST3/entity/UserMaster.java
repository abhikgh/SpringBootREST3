package com.example.SpringBootREST3.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



/**
 * The persistent class for the user_master database table.
 * 
 */
@Entity
@Table(name="user_master")
@Getter
@Setter
@NoArgsConstructor
public class UserMaster {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="user_master_id")
	private Integer userMasterId;

	private String password;

	@Column(name="phone_number")
	private String phoneNumber;

	@Column(name="role_code")
	private String roleCode;

	@Column(name="user_id")
	private String userId;

	@Column(name="user_name")
	private String userName;

}