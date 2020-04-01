package paczka.entity;

import lombok.Data;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "role")
@Data
public class Role {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "role_id")
	private int id;

	@Column(name = "role")
	private String role;

	@ManyToMany(mappedBy = "roles")
	private List<User> users;
}
