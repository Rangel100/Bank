package co.edu.usbcali.bank.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.List;

/**
 * The persistent class for the transaction_type database table.
 * 
 */
@Entity
@Table(name = "transaction_type")
@NamedQuery(name = "TransactionType.findAll", query = "SELECT t FROM TransactionType t")
public class TransactionType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "trty_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long trtyId;

	@NotNull
	@Size(min = 1, max = 1)
	private String enable;

	@NotNull
	@Size(min = 4, max = 50)
	private String name;

	// bi-directional many-to-one association to Transaction
	@OneToMany(mappedBy = "transactionType")
	private List<Transaction> transactions;

	public TransactionType() {
	}

	public Long getTrtyId() {
		return this.trtyId;
	}

	public void setTrtyId(Long trtyId) {
		this.trtyId = trtyId;
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

	public List<Transaction> getTransactions() {
		return this.transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Transaction addTransaction(Transaction transaction) {
		getTransactions().add(transaction);
		transaction.setTransactionType(this);

		return transaction;
	}

	public Transaction removeTransaction(Transaction transaction) {
		getTransactions().remove(transaction);
		transaction.setTransactionType(null);

		return transaction;
	}

}