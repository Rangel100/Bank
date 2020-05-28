package co.edu.usbcali.bank.repository;

import static org.junit.jupiter.api.Assertions.*;

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
class RegisteredAccountRepositoryTest {

	private static final Long reacId = 10L;

	@Autowired
	RegisteredAccountRepository registeredAccountRepository;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	ClientRepository clientRepository;

	@Test
	@DisplayName("save")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void test() {
		assertNotNull(registeredAccountRepository, "El registeredAccountRepository es nulo");

		RegisteredAccount registeredAccount = new RegisteredAccount();
		registeredAccount.setReacId(reacId);
		registeredAccount.setEnable("S");

		Optional<Client> optinalClient = clientRepository.findById(1L);
		assertNotNull(optinalClient, "el client no existe");
		Client client = optinalClient.get();

		registeredAccount.setClient(client);

		Optional<Account> optinalAccount = accountRepository.findById("2632-8850-8767-6806");
		assertNotNull(optinalAccount, "la cuenta no existe");
		Account account = optinalAccount.get();

		registeredAccount.setAccount(account);

		registeredAccountRepository.save(registeredAccount);
	}

	@Test
	@DisplayName("findById")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	void btest() {
		assertNotNull(registeredAccountRepository, "El registeredAccountRepository es nulo");
		assertTrue(registeredAccountRepository.findById(reacId).isPresent(), "El registeredAccount con id " + reacId + " es nulo");
	}

	@Test
	@DisplayName("update")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void cTest() {
		assertNotNull(registeredAccountRepository, "El registeredAccountRepository es nulo");
		assertTrue(registeredAccountRepository.findById(reacId).isPresent(), "El registeredAccount con id " + reacId + " es nulo");

		RegisteredAccount registeredAccount = registeredAccountRepository.findById(reacId).get();
		registeredAccount.setEnable("N");
		registeredAccountRepository.save(registeredAccount);
	}

	@Test
	@DisplayName("delete")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void dTest() {
		assertNotNull(registeredAccountRepository, "es registeredAccountRepository es nulo");
		assertTrue(registeredAccountRepository.findById(reacId).isPresent(), "El registeredAccount con id " + reacId + " es nulo");

		RegisteredAccount registeredAccount = registeredAccountRepository.findById(reacId).get();
		registeredAccountRepository.delete(registeredAccount);
	}

	@Test
	@DisplayName("findAll")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void eTest() {
		assertNotNull(registeredAccountRepository, "es registeredAccountRepository es nulo");

		List<RegisteredAccount> registeredAccounts = registeredAccountRepository.findAll();
		assertFalse(registeredAccounts.isEmpty(), "Los registeredAccount esta vacio");
	}

}
