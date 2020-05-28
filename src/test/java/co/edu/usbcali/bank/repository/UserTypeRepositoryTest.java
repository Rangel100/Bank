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

import co.edu.usbcali.bank.domain.UserType;

@SpringBootTest
class UserTypeRepositoryTest {

	private static final Long ustyId = 10L;

	@Autowired
	UserTypeRepository userTypeRepositoryer;

	@Test
	@DisplayName("save")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void aTest() {
		assertNotNull(userTypeRepositoryer, "El userTypeRepositoryer es nulo");

		UserType userType = new UserType();
		userType.setUstyId(ustyId);
		userType.setName("nueva");
		userType.setEnable("S");

		userTypeRepositoryer.save(userType);

	}

	@Test
	@DisplayName("findById")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	void bTest() {
		assertNotNull(userTypeRepositoryer, "El userTypeRepositoryer es nulo");
		assertTrue(userTypeRepositoryer.findById(ustyId).isPresent(), "El userType con id " + ustyId + " es nulo");
	}

	@Test
	@DisplayName("update")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void cTest() {
		assertNotNull(userTypeRepositoryer, "El userTypeRepositoryer es nulo");
		assertTrue(userTypeRepositoryer.findById(ustyId).isPresent(), "El userType con id " + ustyId + " es nulo");

		UserType userType = userTypeRepositoryer.findById(ustyId).get();
		userType.setName("nueva2");
		userTypeRepositoryer.save(userType);
	}

	@Test
	@DisplayName("delete")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void dTest() {
		assertNotNull(userTypeRepositoryer, "es userTypeRepositoryer es nulo");
		assertTrue(userTypeRepositoryer.findById(ustyId).isPresent(), "El userType con id " + ustyId + " es nulo");

		UserType userType = userTypeRepositoryer.findById(ustyId).get();
		userTypeRepositoryer.delete(userType);
	}

	@Test
	@DisplayName("findAll")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void eTest() {
		assertNotNull(userTypeRepositoryer, "es userTypeRepositoryer es nulo");

		List<UserType> userTypes = userTypeRepositoryer.findAll();
		assertFalse(userTypes.isEmpty(), "Los userType esta vacio");
	}

}
