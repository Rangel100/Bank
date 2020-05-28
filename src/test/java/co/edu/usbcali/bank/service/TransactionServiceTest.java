package co.edu.usbcali.bank.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.bank.domain.Account;
import co.edu.usbcali.bank.domain.Transaction;
import co.edu.usbcali.bank.domain.TransactionType;
import co.edu.usbcali.bank.domain.User;

@SpringBootTest
class TransactionServiceTest {

	private static Long tranId = null;

	@Autowired
	TransactionService transactionService;
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	TransactionTypeService TransactionTypeService;
	
	@Autowired
	UserService userService;

	@Test
	@DisplayName("save")
	@Rollback(false)
	void aTest() {
		assertNotNull(transactionService, "El transactionService es nulo");
		
		Transaction transaction = new Transaction();
		transaction.setTranId(tranId);
		transaction.setAmount(new BigDecimal(10000));
		transaction.setDate(new Timestamp(System.currentTimeMillis()));
		
		Optional<Account> optinalAccount = accountService.findById("2928-4331-8647-0560");
		assertTrue(optinalAccount.isPresent(), "El account no esxiste");
		Account account = optinalAccount.get();

		transaction.setAccount(account);

		Optional<TransactionType> optinalTransactionType = TransactionTypeService.findById(1L);
		assertTrue(optinalTransactionType.isPresent(), "El transactionType no esxiste");
		TransactionType transactionType = optinalTransactionType.get();

		transaction.setTransactionType(transactionType);

		Optional<User> optinalUser = userService.findById("acowhig9@un.org");
		assertTrue(optinalUser.isPresent(), "El user no esxiste");
		User user = optinalUser.get();

		transaction.setUser(user);

		try {
			transactionService.save(transaction);
			assertNotNull(transaction.getTranId(), "No genero id");
			tranId = transaction.getTranId();
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}
	}

	@Test
	@DisplayName("findById")
	void bTest() {
		assertTrue(transactionService.findById(tranId).isPresent());
		Transaction transaction = transactionService.findById(tranId).get();
		assertNotNull(transaction, "El transaction con id " + tranId + " es nulo");
	}

	@Test
	@DisplayName("update")
	@Rollback(false)
	void cTest() {
		assertNotNull(transactionService, "El transactionService es nulo");
		assertTrue(transactionService.findById(tranId).isPresent(), "El transaction con id " + tranId + " es nulo");

		Transaction transaction = transactionService.findById(tranId).get();
		transaction.setAmount(new BigDecimal(2000));;

		try {
			transactionService.update(transaction);
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}
	}

	@Test
	@DisplayName("delete")
	@Rollback(false)
	void dTest() {
		assertNotNull(transactionService, "El transactionService es nulo");
		assertTrue(transactionService.findById(tranId).isPresent(), "El transaction con id " + tranId + " es nulo");

		Transaction transaction = transactionService.findById(tranId).get();
		try {
			transactionService.delete(transaction);
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}
	}

	@Test
	@DisplayName("findAll")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void eTest() {
		assertNotNull(transactionService, "El transactionService es nulo");

		List<Transaction> transactions = transactionService.findAll();
		assertFalse(transactions.isEmpty(), "Los transactions esta vacio");
	}
	
	@Test
	@DisplayName("findTransactionsByAccounts")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void hTest() {
		assertNotNull(transactionService, "El transaction es nulo");

		List<Transaction> transactions = transactionService.findTransactionsByAccounts("0000-0000-0000-0000");
		assertFalse(transactions.isEmpty(), "Los transactions esta vacio");
	}
	

}
