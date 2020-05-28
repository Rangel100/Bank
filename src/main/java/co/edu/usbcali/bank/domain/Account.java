package co.edu.usbcali.bank.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

/**
 * The persistent class for the account database table.
 * 
 */
@Entity
@NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a")
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "acco_id")
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

	// bi-directional many-to-one association to Client
	@ManyToOne
	@JoinColumn(name = "clie_id")
	@NotNull
	private Client client;

	// bi-directional many-to-one association to RegisteredAccount
	@OneToMany(mappedBy = "account")
	private List<RegisteredAccount> registeredAccounts;

	// bi-directional many-to-one association to Transaction
	@OneToMany(mappedBy = "account")
	private List<Transaction> transactions;

	public Account() {
	}

	public String getAccoId() {
		return this.accoId;
	}

	public void setAccoId(String accoId) {
		this.accoId = accoId;
	}

	public BigDecimal getBalance() {
		return this.balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getEnable() {
		return this.enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getVersion() {
		return this.version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Client getClient() {
		return this.client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<RegisteredAccount> getRegisteredAccounts() {
		return this.registeredAccounts;
	}

	public void setRegisteredAccounts(List<RegisteredAccount> registeredAccounts) {
		this.registeredAccounts = registeredAccounts;
	}

	public RegisteredAccount addRegisteredAccount(RegisteredAccount registeredAccount) {
		getRegisteredAccounts().add(registeredAccount);
		registeredAccount.setAccount(this);

		return registeredAccount;
	}

	public RegisteredAccount removeRegisteredAccount(RegisteredAccount registeredAccount) {
		getRegisteredAccounts().remove(registeredAccount);
		registeredAccount.setAccount(null);

		return registeredAccount;
	}

	public List<Transaction> getTransactions() {
		return this.transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Transaction addTransaction(Transaction transaction) {
		getTransactions().add(transaction);
		transaction.setAccount(this);

		return transaction;
	}

	public Transaction removeTransaction(Transaction transaction) {
		getTransactions().remove(transaction);
		transaction.setAccount(null);

		return transaction;
	}

}