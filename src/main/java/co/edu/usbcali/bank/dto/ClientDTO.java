package co.edu.usbcali.bank.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ClientDTO {

	@NotNull
	private Long clieId;

	@NotNull
	@Size(min = 10, max = 50)
	private String adress;

	@NotNull
	@Email
	private String email;

	@NotNull
	@Size(min = 1, max = 1)
	private String enable;

	@NotNull
	@Size(min = 5, max = 50)
	private String name;

	@NotNull
	@Size(min = 10, max = 30)
	private String phone;

	@NotNull
	private Long dotyId;

	public ClientDTO() {
		super();
	}

	public ClientDTO(@NotNull long clieId, @NotNull @Size(min = 10, max = 50) String adress,
			@NotNull @Email String email, @NotNull @Size(min = 1, max = 1) String enable,
			@NotNull @Size(min = 5, max = 50) String name, @NotNull @Size(min = 10, max = 30) String phone,
			@NotNull Long dotyId) {
		super();
		this.clieId = clieId;
		this.adress = adress;
		this.email = email;
		this.enable = enable;
		this.name = name;
		this.phone = phone;
		this.dotyId = dotyId;
	}

	public long getClieId() {
		return clieId;
	}

	public void setClieId(long clieId) {
		this.clieId = clieId;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getDotyId() {
		return dotyId;
	}

	public void setDotyId(Long dotyId) {
		this.dotyId = dotyId;
	}

}
