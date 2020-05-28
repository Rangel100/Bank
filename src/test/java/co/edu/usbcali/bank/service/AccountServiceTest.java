package co.edu.usbcali.bank.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
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
import co.edu.usbcali.bank.domain.Client;

@SpringBootTest
class AccountServiceTest {

	private final static String accoId = "0000-0000-0000-0000";

	@Autowired
	AccountService accountService;

	@Autowired
	ClientService clientService;

	@Test
	@DisplayName("save")
	@Rollback(false)
	void aTest() {
		assertNotNull(accountService, "El accountService es nulo");

		Account account = new Account();
		account.setAccoId(accountService.generarAccoId());
		account.setBalance(new BigDecimal(10000));;
		account.setEnable("S");
		account.setPassword(accountService.generarPassword());
		account.setVersion(1L);

		Optional<Client> optionalClient = clientService.findById(1L);
		assertTrue(optionalClient.isPresent(), "El client no existe");
		Client client = optionalClient.get();

		account.setClient(client);

		try {
			accountService.save(account);
		}catch (Exception e) {
			assertNull(e,e.getMessage());
		}		
	}

	@Test
	@DisplayName("findById")
	void bTest() {
		assertNotNull(accountService, "El accountService es nulo");
		assertTrue(accountService.findById(accoId).isPresent(), "El account con id " + accoId + " es nulo");
	}

	@Test
	@DisplayName("update")
	@Rollback(false)
	void cTest() {
		assertNotNull(accountService, "El accountService es nulo");
		assertTrue(accountService.findById(accoId).isPresent(), "El account con id " + accoId + " es nulo");

		Account account = accountService.findById(accoId).get();
		account.setEnable("S");
		
		try {
			accountService.update(account);
		}catch (Exception e) {
			assertNull(e,e.getMessage());
		}	
	}

	@Test
	@DisplayName("delete")
	@Rollback(false)
	void dTest() {
		assertNotNull(accountService, "El accountService es nulo");
		assertTrue(accountService.findById(accoId).isPresent(), "El account con id " + accoId + " es nulo");

		//Account account = accountService.findById(accoId).get();
		try {
			accountService.deleteById(accoId);
		} catch (Exception e) {
			assertNull(e,e.getMessage());
		}
	}

	@Test
	@DisplayName("findAll")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void eTest() {
		assertNotNull(accountService, "El accountService es nulo");

		List<Account> accounts = accountService.findAll();
		assertFalse(accounts.isEmpty(), "Los accounts esta vacio");
	}
	
	@Test
	@DisplayName("genererAccoId")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	void fTest() {
		accountService.generarAccoId();
	}
	
	@Test
	@DisplayName("genererPassword")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	void gTest() {
		accountService.generarPassword();
	}
	
	@Test
	@DisplayName("findAccountByClient")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void hTest() {
		assertNotNull(accountService, "El accountService es nulo");

		List<Account> accounts = accountService.findAccountByClient(111111111L);
		assertFalse(accounts.isEmpty(), "Los accounts esta vacio");
	}

}
