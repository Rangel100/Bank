package co.edu.usbcali.bank.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDTO {

	@NotNull
	@Email
	private String userEmail;

	@NotNull
	@Size(min = 1, max = 1)
	private String enable;

	@NotNull
	@Size(min = 5, max = 50)
	private String name;

	@NotNull
	private Long ustyId;

	public UserDTO() {
		super();
	}

	public UserDTO(@NotNull @Email String userEmail, @NotNull @Size(min = 1, max = 1) String enable,
			@NotNull @Size(min = 5, max = 50) String name, @NotNull Long ustyId) {
		super();
		this.userEmail = userEmail;
		this.enable = enable;
		this.name = name;
		this.ustyId = ustyId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getUstyId() {
		return ustyId;
	}

	public void setUstyId(Long ustyId) {
		this.ustyId = ustyId;
	}

}
