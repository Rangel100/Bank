package co.edu.usbcali.bank.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DocumentTypeDTO {

	@NotNull
	private Long dotyId;

	@NotNull
	@Size(min = 1, max = 1)
	private String enable;

	@NotNull
	@Size(min = 2, max = 50)
	private String name;

	public DocumentTypeDTO() {
		super();
	}

	public DocumentTypeDTO(@NotNull Long dotyId, @NotNull @Size(min = 1, max = 1) String enable,
			@NotNull @Size(min = 2, max = 50) String name) {
		super();
		this.dotyId = dotyId;
		this.enable = enable;
		this.name = name;
	}

	public Long getDotyId() {
		return dotyId;
	}

	public void setDotyId(Long dotyId) {
		this.dotyId = dotyId;
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
