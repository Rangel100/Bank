package co.edu.usbcali.bank.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The persistent class for the client database table.
 * 
 */
@Entity
@NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c")
public class Client implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "clie_id")
	@NotNull
	private long clieId;

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

	// bi-directional many-to-one association to DocumentType
	@ManyToOne
	@JoinColumn(name = "doty_id")
	@NotNull
	private DocumentType documentType;

	// bi-directional many-to-one association to Account
	@OneToMany(mappedBy = "client")
	private List<Account> accounts;

	// bi-directional many-to-one association to RegisteredAccount
	@OneToMany(mappedBy = "client")
	private List<RegisteredAccount> registeredAccounts;

	public Client() {
	}

	public long getClieId() {
		return this.clieId;
	}

	public void setClieId(long clieId) {
		this.clieId = clieId;
	}

	public String getAdress() {
		return this.adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Account> getAccounts() {
		return this.accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public Account addAccount(Account account) {
		getAccounts().add(account);
		account.setClient(this);

		return account;
	}

	public Account removeAccount(Account account) {
		getAccounts().remove(account);
		account.setClient(null);

		return account;
	}

	public DocumentType getDocumentType() {
		return this.documentType;
	}

	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}

	public List<RegisteredAccount> getRegisteredAccounts() {
		return this.registeredAccounts;
	}

	public void setRegisteredAccounts(List<RegisteredAccount> registeredAccounts) {
		this.registeredAccounts = registeredAccounts;
	}

	public RegisteredAccount addRegisteredAccount(RegisteredAccount registeredAccount) {
		getRegisteredAccounts().add(registeredAccount);
		registeredAccount.setClient(this);

		return registeredAccount;
	}

	public RegisteredAccount removeRegisteredAccount(RegisteredAccount registeredAccount) {
		getRegisteredAccounts().remove(registeredAccount);
		registeredAccount.setClient(null);

		return registeredAccount;
	}

}