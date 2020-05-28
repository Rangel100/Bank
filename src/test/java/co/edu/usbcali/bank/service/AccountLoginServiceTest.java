package co.edu.usbcali.bank.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import co.edu.usbcali.bank.dto.AccountLoginDTO;

@SpringBootTest
class AccountLoginServiceTest {

	private final static String accoId = "0702-9602-7222-2537";
	private final static String password = "13re7djt";
	private final static Long clieId = 101010L;

	@Autowired
	AccountLoginService accountLoginService;

	@Test
	@DisplayName("login")
	@Rollback(false)
	void atest() throws Exception {

		AccountLoginDTO loginDTO = new AccountLoginDTO();
		loginDTO.setAccoId(accoId);
		loginDTO.setClieId(clieId);
		loginDTO.setPassword(password);

		accountLoginService.login(loginDTO);
	}

}
