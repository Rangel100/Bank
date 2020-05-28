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

import co.edu.usbcali.bank.domain.User;
import co.edu.usbcali.bank.domain.UserType;

@SpringBootTest
class UserServiceTest {

	private final static String userEmail = "hola@hola.com";

	@Autowired
	UserService userService;
	
	@Autowired
	UserTypeService userTypeService;

	@Test
	@DisplayName("save")
	@Rollback(false)
	void aTest() {
		assertNotNull(userService, "El userService es nulo");

		User user = new User();
		user.setUserEmail(userEmail);
		user.setName("johan");
		user.setEnable("S");

		Optional<UserType> optinalUserType = userTypeService.findById(2L);
		assertTrue(optinalUserType.isPresent(), "El userType no esxiste");
		UserType userType = optinalUserType.get();

		user.setUserType(userType);

		try {
			userService.save(user);
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}
	}

	@Test
	@DisplayName("findById")
	void bTest() {
		assertNotNull(userService, "El userService es nulo");
		assertTrue(userService.findById(userEmail).isPresent(), "El user con id " + userEmail + " es nulo");
	}

	@Test
	@DisplayName("update")
	@Rollback(false)
	void cTest() {
		assertNotNull(userService, "El userService es nulo");
		assertTrue(userService.findById(userEmail).isPresent(), "El user con id " + userEmail + " es nulo");

		User user = userService.findById(userEmail).get();
		user.setName("Jhonatan rangel");

		try {
			userService.update(user);
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}
	}

	@Test
	@DisplayName("delete")
	@Rollback(false)
	void dTest() {
		assertNotNull(userService, "El userService es nulo");
		assertTrue(userService.findById(userEmail).isPresent(), "El user con id " + userEmail + " es nulo");

		User user = userService.findById(userEmail).get();
		try {
			userService.delete(user);
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}
	}

	@Test
	@DisplayName("findAll")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void eTest() {
		assertNotNull(userService, "El userService es nulo");

		List<User> users = userService.findAll();
		assertFalse(users.isEmpty(), "Los users esta vacio");
	}

}
