package co.edu.usbcali.bank.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class TransferDTO {

	@NotNull
	private String accoIdOrigin;

	@NotNull
	private String accoIdDestination;

	@NotNull
	@Min(1)
	private BigDecimal amount;

	@NotNull
	@Email
	private String userEmail;

	public TransferDTO() {
		super();
	}

	public TransferDTO(@NotNull String accoIdOrigin, @NotNull String accoIdDestination,
			@NotNull @Min(1) BigDecimal amount, @NotNull @Email String userEmail) {
		super();
		this.accoIdOrigin = accoIdOrigin;
		this.accoIdDestination = accoIdDestination;
		this.amount = amount;
		this.userEmail = userEmail;
	}

	public String getAccoIdOrigin() {
		return accoIdOrigin;
	}

	public void setAccoIdOrigin(String accoIdOrigin) {
		this.accoIdOrigin = accoIdOrigin;
	}

	public String getAccoIdDestination() {
		return accoIdDestination;
	}

	public void setAccoIdDestination(String accoIdDestination) {
		this.accoIdDestination = accoIdDestination;
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
