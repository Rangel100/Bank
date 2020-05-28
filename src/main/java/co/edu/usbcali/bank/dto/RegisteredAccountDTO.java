package co.edu.usbcali.bank.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisteredAccountDTO {

	@NotNull
	private Long reacId;

	@NotNull
	@Size(min = 1, max = 1)
	private String enable;

	@NotNull
	private String accoId;

	@NotNull
	private Long clieId;

	public RegisteredAccountDTO() {
		super();
	}

	public RegisteredAccountDTO(@NotNull Long reacId, @NotNull @Size(min = 1, max = 1) String enable,
			@NotNull String accoId, @NotNull Long clieId) {
		super();
		this.reacId = reacId;
		this.enable = enable;
		this.accoId = accoId;
		this.clieId = clieId;
	}

	public Long getReacId() {
		return reacId;
	}

	public void setReacId(Long reacId) {
		this.reacId = reacId;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getAccoId() {
		return accoId;
	}

	public void setAccoId(String accoId) {
		this.accoId = accoId;
	}

	public Long getClieId() {
		return clieId;
	}

	public void setClieId(Long clieId) {
		this.clieId = clieId;
	}

}
