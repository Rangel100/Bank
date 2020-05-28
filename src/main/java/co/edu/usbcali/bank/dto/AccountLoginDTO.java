package co.edu.usbcali.bank.dto;

import javax.validation.constraints.NotNull;

public class AccountLoginDTO {

	@NotNull
	private String accoId;

	@NotNull
	private String password;

	@NotNull
	private Long clieId;

	public String getAccoId() {
		return accoId;
	}

	public void setAccoId(String accoId) {
		this.accoId = accoId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getClieId() {
		return clieId;
	}

	public void setClieId(Long clieId) {
		this.clieId = clieId;
	}

	public AccountLoginDTO(@NotNull String accoId, @NotNull String password, @NotNull Long clieId) {
		super();
		this.accoId = accoId;
		this.password = password;
		this.clieId = clieId;
	}

	public AccountLoginDTO() {
		super();

	}
}
