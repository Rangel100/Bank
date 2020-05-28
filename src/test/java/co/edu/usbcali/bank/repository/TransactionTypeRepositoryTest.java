package co.edu.usbcali.bank.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.bank.domain.TransactionType;

@SpringBootTest
class TransactionTypeRepositoryTest {

	private static final Long trtyId = 10L;

	@Autowired
	TransactionTypeRepository transactionTypeRepository;

	@Test
	@DisplayName("save")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void aTest() {
		assertNotNull(transactionTypeRepository, "El transactionTypeRepository es nulo");

		TransactionType transactionType = new TransactionType();
		transactionType.setTrtyId(trtyId);
		transactionType.setName("nueva");
		transactionType.setEnable("S");

		transactionTypeRepository.save(transactionType);

	}

	@Test
	@DisplayName("findById")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	void bTest() {
		assertNotNull(transactionTypeRepository, "El transactionTypeRepository es nulo");
		assertTrue(transactionTypeRepository.findById(trtyId).isPresent(), "El transactionType con id " + trtyId + " es nulo");
	}

	@Test
	@DisplayName("update")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void cTest() {
		assertNotNull(transactionTypeRepository, "El transactionTypeRepository es nulo");
		assertTrue(transactionTypeRepository.findById(trtyId).isPresent(), "El transactionType con id " + trtyId + " es nulo");

		TransactionType transactionType = transactionTypeRepository.findById(trtyId).get();
		transactionType.setName("nueva2");
		transactionTypeRepository.save(transactionType);
	}

	@Test
	@DisplayName("delete")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void dTest() {
		assertNotNull(transactionTypeRepository, "es transactionTypeRepository es nulo");
		assertTrue(transactionTypeRepository.findById(trtyId).isPresent(), "El transactionType con id " + trtyId + " es nulo");

		TransactionType transactionType = transactionTypeRepository.findById(trtyId).get();
		transactionTypeRepository.delete(transactionType);
	}

	@Test
	@DisplayName("findAll")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void eTest() {
		assertNotNull(transactionTypeRepository, "es transactionTypeRepository es nulo");

		List<TransactionType> transactionTypes = transactionTypeRepository.findAll();
		assertFalse(transactionTypes.isEmpty(), "Los transactionType esta vacio");
	}

}
