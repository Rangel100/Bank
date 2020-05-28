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

import co.edu.usbcali.bank.domain.UserType;

@SpringBootTest
class UserTypeServiceTest {

	private static Long ustyId = null;

	@Autowired
	UserTypeService userTypeService;

	@Test
	@DisplayName("save")
	@Rollback(false)
	void aTest() {
		assertNotNull(userTypeService, "El userTypeService es nulo");

		UserType userType = new UserType();
		userType.setUstyId(ustyId);
		userType.setName("nuevo");
		userType.setEnable("S");

		try {
			userTypeService.save(userType);
			assertNotNull(userType.getUstyId(), "No genero id");
			ustyId = userType.getUstyId();
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}
	}

	@Test
	@DisplayName("findById")
	void bTest() {
		assertTrue(userTypeService.findById(ustyId).isPresent());
		UserType userType = userTypeService.findById(ustyId).get();
		assertNotNull(userType, "El userType con id " + ustyId + " es nulo");
	}

	@Test
	@DisplayName("update")
	@Rollback(false)
	void cTest() {
		assertNotNull(userTypeService, "El userTypeService es nulo");
		assertTrue(userTypeService.findById(ustyId).isPresent(), "El userType con id " + ustyId + " es nulo");

		UserType userType = userTypeService.findById(ustyId).get();
		userType.setName("admin");

		try {
			userTypeService.update(userType);
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}
	}

	@Test
	@DisplayName("delete")
	@Rollback(false)
	void dTest() {
		assertNotNull(userTypeService, "El userTypeService es nulo");
		assertTrue(userTypeService.findById(ustyId).isPresent(), "El userType con id " + ustyId + " es nulo");

		UserType userType = userTypeService.findById(ustyId).get();
		try {
			userTypeService.delete(userType);
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}
	}

	@Test
	@DisplayName("findAll")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void eTest() {
		assertNotNull(userTypeService, "El userTypeService es nulo");

		List<UserType> userTypes = userTypeService.findAll();
		assertFalse(userTypes.isEmpty(), "Los userTypes esta vacio");
	}

}
