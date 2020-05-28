package co.edu.usbcali.bank.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AccountDTO {

	@NotNull
	private String accoId;

	@NotNull
	private BigDecimal balance;

	@NotNull
	@Size(min = 1, max = 1)
	private String enable;

	@NotNull
	@Size(min = 4)
	private String password;

	@NotNull
	@Max(1)
	private Long version;

	@NotNull
	private Long clieId;

	public AccountDTO() {
		super();
	}

	public AccountDTO(@NotNull String accoId, @NotNull BigDecimal balance,
			@NotNull @Size(min = 1, max = 1) String enable, @NotNull @Size(min = 4) String password,
			@NotNull @Max(1) Long version, @NotNull Long clieId) {
		super();
		this.accoId = accoId;
		this.balance = balance;
		this.enable = enable;
		this.password = password;
		this.version = version;
		this.clieId = clieId;
	}

	public String getAccoId() {
		return accoId;
	}

	public void setAccoId(String accoId) {
		this.accoId = accoId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public long getClieId() {
		return clieId;
	}

	public void setClieId(long clieId) {
		this.clieId = clieId;
	}

}
