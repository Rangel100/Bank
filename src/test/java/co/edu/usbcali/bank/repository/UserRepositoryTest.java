package co.edu.usbcali.bank.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
class UserRepositoryTest {

	private static final String userEmail = "hola.com";

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserTypeRepository userTypeRepository;

	@Test
	@DisplayName("save")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void aTest() {
		assertNotNull(userRepository, "El userRepository es nulo");

		User user = new User();
		user.setUserEmail(userEmail);
		user.setName("jhon");
		user.setEnable("S");

		Optional<UserType> optinalUserType = userTypeRepository.findById(2L);
		assertTrue(optinalUserType.isPresent(), "El cliente no esxiste");
		UserType userType = optinalUserType.get();

		user.setUserType(userType);

		userRepository.save(user);

	}

	@Test
	@DisplayName("findById")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	void bTest() {
		assertNotNull(userRepository, "El userRepository es nulo");
		assertTrue(userRepository.findById(userEmail).isPresent(), "El user con id " + userEmail + " es nulo");
	}

	@Test
	@DisplayName("update")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void cTest() {
		assertNotNull(userRepository, "El userRepository es nulo");
		assertTrue(userRepository.findById(userEmail).isPresent(), "El user con id " + userEmail + " es nulo");

		User user = userRepository.findById(userEmail).get();
		user.setName("jon");
		userRepository.save(user);
	}

	@Test
	@DisplayName("delete")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void dTest() {
		assertNotNull(userRepository, "es userRepository es nulo");
		assertTrue(userRepository.findById(userEmail).isPresent(), "El user con id " + userEmail + " es nulo");

		User user = userRepository.findById(userEmail).get();
		userRepository.delete(user);
	}

	@Test
	@DisplayName("findAll")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void eTest() {
		assertNotNull(userRepository, "es userRepository es nulo");

		List<User> users = userRepository.findAll();
		assertFalse(users.isEmpty(), "Los user esta vacio");
	}

}
