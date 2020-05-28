package co.edu.usbcali.bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.usbcali.bank.domain.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	// Cambio por proyecto,repository para saber las transaction las cuentas de un
	// cliente pasando como parametro el clieId
	// Query SQL select * from transaction where acco_id in (select acco_id from account where clie_id='101010')
	//@Query("SELECT t FROM Transaction t WHERE t.account.accoId in (SELECT a FROM Account a WHERE a.client.clieId = :clieId)")
	@Query("SELECT t FROM Transaction t WHERE t.account.accoId = :accoId")
	public List<Transaction> findTransactionsByAccounts(@Param("accoId") String id);
	//
}
