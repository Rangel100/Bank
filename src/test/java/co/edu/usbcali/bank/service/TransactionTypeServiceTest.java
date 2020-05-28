package co.edu.usbcali.bank.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
class TransactionTypeServiceTest {

	private static Long trtyId = null;

	@Autowired
	TransactionTypeService transactionTypeService;

	@Test
	@DisplayName("save")
	@Rollback(false)
	void aTest() {
		assertNotNull(transactionTypeService, "El transactionTypeService es nulo");
		
		TransactionType transactionType = new TransactionType();
		transactionType.setTrtyId(trtyId);
		transactionType.setName("nueva");
		transactionType.setEnable("S");

		try {
			transactionTypeService.save(transactionType);
			assertNotNull(transactionType.getTrtyId(), "No genero id");
			trtyId = transactionType.getTrtyId();
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}
	}

	@Test
	@DisplayName("findById")
	void bTest() {
		assertTrue(transactionTypeService.findById(trtyId).isPresent());
		TransactionType transactionType = transactionTypeService.findById(trtyId).get();
		assertNotNull(transactionType, "El transactionType con id " + trtyId + " es nulo");
	}

	@Test
	@DisplayName("update")
	@Rollback(false)
	void cTest() {
		assertNotNull(transactionTypeService, "El transactionTypeService es nulo");
		assertTrue(transactionTypeService.findById(trtyId).isPresent(), "El transactionType con id " + trtyId + " es nulo");

		TransactionType transactionType = transactionTypeService.findById(trtyId).get();
		transactionType.setName("serviceTest");

		try {
			transactionTypeService.update(transactionType);
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}
	}

	@Test
	@DisplayName("delete")
	@Rollback(false)
	void dTest() {
		assertNotNull(transactionTypeService, "El transactionTypeService es nulo");
		assertTrue(transactionTypeService.findById(trtyId).isPresent(), "El transactionType con id " + trtyId + " es nulo");

		TransactionType transactionType = transactionTypeService.findById(trtyId).get();
		try {
			transactionTypeService.delete(transactionType);
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}
	}

	@Test
	@DisplayName("findAll")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void eTest() {
		assertNotNull(transactionTypeService, "El transactionTypeService es nulo");

		List<TransactionType> transactionTypes = transactionTypeService.findAll();
		assertFalse(transactionTypes.isEmpty(), "Los transactionTypes esta vacio");
	}

}
