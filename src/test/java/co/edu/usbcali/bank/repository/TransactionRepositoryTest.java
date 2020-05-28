package co.edu.usbcali.bank.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
class TransactionRepositoryTest {

	private static final Long tranId = 1L;

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	TransactionTypeRepository transactionTypeRepository;

	@Autowired
	UserRepository userRepository;

	@Test
	@DisplayName("save")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void aTest() {
		assertNotNull(transactionRepository, "El accountRepository es nulo");

		Transaction transaction = new Transaction();
		transaction.setTranId(tranId);
		transaction.setAmount(new BigDecimal(10.000));
		transaction.setDate(new Timestamp(System.currentTimeMillis()));

		Optional<Account> optinalAccount = accountRepository.findById("2928-4331-8647-0560");
		assertTrue(optinalAccount.isPresent(), "El account no esxiste");
		Account account = optinalAccount.get();

		transaction.setAccount(account);

		Optional<TransactionType> optinalTransactionType = transactionTypeRepository.findById(1L);
		assertTrue(optinalTransactionType.isPresent(), "El transactionType no esxiste");
		TransactionType transactionType = optinalTransactionType.get();

		transaction.setTransactionType(transactionType);

		Optional<User> optinalUser = userRepository.findById("acowhig9@un.org");
		assertTrue(optinalUser.isPresent(), "El user no esxiste");
		User user = optinalUser.get();

		transaction.setUser(user);

		transactionRepository.save(transaction);
	}

	@Test
	@DisplayName("findById")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	void bTest() {
		assertNotNull(transactionRepository, "El transactionRepository es nulo");
		assertTrue(transactionRepository.findById(tranId).isPresent(), "El transaction con id " + tranId + " es nulo");
	}

	@Test
	@DisplayName("update")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void cTest() {
		assertNotNull(transactionRepository, "El transactionRepository es nulo");
		assertTrue(transactionRepository.findById(tranId).isPresent(), "El transaction con id " + tranId + " es nulo");

		Transaction transaction = transactionRepository.findById(tranId).get();
		transaction.setAmount(new BigDecimal(2000));
		transactionRepository.save(transaction);
	}

	@Test
	@DisplayName("delete")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void eTest() {
		assertNotNull(transactionRepository, "es transactionRepository es nulo");
		assertTrue(transactionRepository.findById(tranId).isPresent(), "El transaction con id " + tranId + " es nulo");

		Transaction transaction = transactionRepository.findById(tranId).get();
		transactionRepository.delete(transaction);
	}

	@Test
	@DisplayName("findAll")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void dTest() {
		assertNotNull(transactionRepository, "es transactionRepository es nulo");

		List<Transaction> transactions = transactionRepository.findAll();
		assertFalse(transactions.isEmpty(), "Los transaction esta vacio");
	}
}
