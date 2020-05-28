package co.edu.usbcali.bank.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class DepositDTO {

	@NotNull
	private String accoId;

	@NotNull
	@Min(1)
	private BigDecimal amount;

	@NotNull
	@Email
	private String userEmail;

	public DepositDTO() {
		super();
	}

	public DepositDTO(@NotNull String accoId, @NotNull @Min(1) BigDecimal amount, @NotNull @Email String userEmail) {
		super();
		this.accoId = accoId;
		this.amount = amount;
		this.userEmail = userEmail;
	}

	public String getAccoId() {
		return accoId;
	}

	public void setAccoId(String accoId) {
		this.accoId = accoId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

}
