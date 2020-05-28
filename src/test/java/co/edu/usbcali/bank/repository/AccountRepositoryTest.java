package co.edu.usbcali.bank.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.bank.domain.Account;
import co.edu.usbcali.bank.domain.Client;

@SpringBootTest
class AccountRepositoryTest {

	private final static String accoId = "4444-1111-1111-1111";

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	ClientRepository clientRepository;

	@Test
	@DisplayName("save")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void aTest() {
		assertNotNull(accountRepository, "El accountRepository es nulo");

		Account account = new Account();
		account.setAccoId(accoId);
		account.setBalance(new BigDecimal(1000.000000));
		account.setEnable("S");
		account.setPassword("hola");
		account.setVersion(1L);

		Optional<Client> optinalClient = clientRepository.findById(1L);
		assertTrue(optinalClient.isPresent(), "El cliente no esxiste");
		Client client = optinalClient.get();

		account.setClient(client);

		accountRepository.save(account);

	}

	@Test
	@DisplayName("findById")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	void bTest() {
		assertNotNull(accountRepository, "El accountRepository es nulo");
		assertTrue(accountRepository.findById(accoId).isPresent(), "El account con id " + accoId + " es nulo");
	}

	@Test
	@DisplayName("update")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void cTest() {
		assertNotNull(accountRepository, "El accountRepository es nulo");
		assertTrue(accountRepository.findById(accoId).isPresent(), "El account con id " + accoId + " es nulo");

		Account account = accountRepository.findById(accoId).get();
		account.setPassword("hola2");
		accountRepository.save(account);
	}

	@Test
	@DisplayName("delete")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void dTest() {
		assertNotNull(accountRepository, "es accountRepository es nulo");
		assertTrue(accountRepository.findById(accoId).isPresent(), "El account con id " + accoId + " es nulo");

		Account account = accountRepository.findById(accoId).get();
		accountRepository.delete(account);
	}

	@Test
	@DisplayName("findAll")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void eTest() {
		assertNotNull(accountRepository, "es accountRepository es nulo");

		List<Account> accounts = accountRepository.findAll();
		assertFalse(accounts.isEmpty(), "Los account esta vacio");
	}
	
	//cambio por proyecto
	private final static Logger log = LoggerFactory.getLogger(AccountRepositoryTest.class);
	
	@Test
	@DisplayName("findAccountsByClient")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void findAccountsByClient() {
		assertNotNull(accountRepository, "El clientRepository es nulo");

		List<Account> accounts = accountRepository.findAccountsByClient(111111111L);
		
		for (Account account : accounts) {
			log.info("cuentas: " + account.getAccoId());
		}
	}
	//
}
