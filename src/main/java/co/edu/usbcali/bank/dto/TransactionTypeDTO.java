package co.edu.usbcali.bank.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TransactionTypeDTO {

	@NotNull
	private Long trtyId;

	@NotNull
	@Size(min = 1, max = 1)
	private String enable;

	@NotNull
	@Size(min = 4, max = 50)
	private String name;

	public TransactionTypeDTO() {
		super();
	}

	public TransactionTypeDTO(@NotNull Long trtyId, @NotNull @Size(min = 1, max = 1) String enable,
			@NotNull @Size(min = 4, max = 50) String name) {
		super();
		this.trtyId = trtyId;
		this.enable = enable;
		this.name = name;
	}

	public Long getTrtyId() {
		return trtyId;
	}

	public void setTrtyId(Long trtyId) {
		this.trtyId = trtyId;
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

}
