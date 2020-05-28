package co.edu.usbcali.bank.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The persistent class for the document_type database table.
 * 
 */
@Entity
@Table(name = "document_type")
@NamedQuery(name = "DocumentType.findAll", query = "SELECT d FROM DocumentType d")
public class DocumentType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "doty_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long dotyId;

	@NotNull
	@Size(min = 1, max = 1)
	private String enable;

	@NotNull
	@Size(min = 2, max = 50)
	private String name;

	// bi-directional many-to-one association to Client
	@OneToMany(mappedBy = "documentType")
	private List<Client> clients;

	public DocumentType() {
	}

	public Long getDotyId() {
		return this.dotyId;
	}

	public void setDotyId(Long dotyId) {
		this.dotyId = dotyId;
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

	public List<Client> getClients() {
		return this.clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}

	public Client addClient(Client client) {
		getClients().add(client);
		client.setDocumentType(this);

		return client;
	}

	public Client removeClient(Client client) {
		getClients().remove(client);
		client.setDocumentType(null);

		return client;
	}

}