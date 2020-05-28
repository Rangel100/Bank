package co.edu.usbcali.bank.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.List;

/**
 * The persistent class for the user_type database table.
 * 
 */
@Entity
@Table(name = "user_type")
@NamedQuery(name = "UserType.findAll", query = "SELECT u FROM UserType u")
public class UserType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "usty_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ustyId;

	@NotNull
	@Size(min = 1, max = 1)
	private String enable;

	@NotNull
	@Size(min = 2, max = 50)
	private String name;

	// bi-directional many-to-one association to User
	@OneToMany(mappedBy = "userType")
	private List<User> users;

	public UserType() {
	}

	public Long getUstyId() {
		return this.ustyId;
	}

	public void setUstyId(Long ustyId) {
		this.ustyId = ustyId;
	}

	public String getEnable() {
		return this.enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		getUsers().add(user);
		user.setUserType(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setUserType(null);

		return user;
	}

}