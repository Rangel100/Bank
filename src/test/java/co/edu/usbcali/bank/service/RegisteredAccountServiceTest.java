package co.edu.usbcali.bank.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import co.edu.usbcali.bank.domain.RegisteredAccount;

@SpringBootTest
class RegisteredAccountServiceTest {

	private static Long reacId = null;

	@Autowired
	RegisteredAccountService registeredAccountService;
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	AccountService accountService;

	@Test
	@DisplayName("save")
	@Rollback(false)
	void aTest() {
		assertNotNull(registeredAccountService, "El registeredAccountService es nulo");
		
		RegisteredAccount registeredAccount = new RegisteredAccount();
		registeredAccount.setReacId(reacId);
		registeredAccount.setEnable("S");
		
		Optional<Account> accountOptional = accountService.findById("1215-0877-5497-4162");
		assertTrue(accountOptional.isPresent(), "El account no existe");
		Account account = accountOptional.get();
		
		registeredAccount.setAccount(account);
		
		Optional<Client> clientOptional = clientService.findById(10L);
		assertTrue(clientOptional.isPresent(), "El client no existe");
		Client client = clientOptional.get();
		
		registeredAccount.setClient(client);

		try {
			registeredAccountService.save(registeredAccount);
			assertNotNull(registeredAccount.getReacId(), "No genero id");
			reacId = registeredAccount.getReacId();
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}
	}

	@Test
	@DisplayName("findById")
	void bTest() {
		assertTrue(registeredAccountService.findById(reacId).isPresent());
		RegisteredAccount registeredAccount = registeredAccountService.findById(reacId).get();
		assertNotNull(registeredAccount, "El registeredAccount con id " + reacId + " es nulo");
	}

	@Test
	@DisplayName("update")
	@Rollback(false)
	void cTest() {
		assertNotNull(registeredAccountService, "El registeredAccountService es nulo");
		assertTrue(registeredAccountService.findById(reacId).isPresent(), "El registeredAccount con id " + reacId + " es nulo");

		RegisteredAccount registeredAccount = registeredAccountService.findById(reacId).get();
		
		Optional<Account> accountOptional = accountService.findById("2632-3183-7851-1820");
		assertTrue(accountOptional.isPresent(), "El account no existe");
		Account account = accountOptional.get();
		registeredAccount.setAccount(account);

		try {
			registeredAccountService.update(registeredAccount);
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}
	}

	@Test
	@DisplayName("delete")
	@Rollback(false)
	void dTest() {
		assertNotNull(registeredAccountService, "El registeredAccountService es nulo");
		assertTrue(registeredAccountService.findById(reacId).isPresent(), "El registeredAccount con id " + reacId + " es nulo");

		RegisteredAccount registeredAccount = registeredAccountService.findById(reacId).get();
		try {
			registeredAccountService.delete(registeredAccount);
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}
	}

	@Test
	@DisplayName("findAll")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void eTest() {
		assertNotNull(registeredAccountService, "El registeredAccountService es nulo");

		List<RegisteredAccount> registeredAccounts = registeredAccountService.findAll();
		assertFalse(registeredAccounts.isEmpty(), "Los registeredAccount esta vacio");
	}

}
