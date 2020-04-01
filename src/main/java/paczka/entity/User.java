package paczka.entity;

import lombok.Data;
import paczka.entity.Role;

import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "user")
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "firstname")
	private String firstname;

	@Column(name = "lastname")
	private String lastname;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "locality")
	private String locality;
	
	@Column(name = "street")
	private String street;
	
	@Column(name = "zip_code")
	private String zipcode;

	@Column(name = "phone")
	private String phone;

	@Column(name = "active")
	private int active;

	@JoinTable(name="user_role", joinColumns=@JoinColumn(name="user_id"), inverseJoinColumns=@JoinColumn(name="role_id"))
	@ManyToMany
	private List<Role> roles;

	@OneToMany
	private List<GymPass> gymPasses;

	@OneToMany
	private List<MAPass> maPasses;
}