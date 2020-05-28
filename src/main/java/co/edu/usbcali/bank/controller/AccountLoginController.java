package co.edu.usbcali.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usbcali.bank.dto.AccountLoginDTO;
import co.edu.usbcali.bank.service.AccountLoginService;

@RestController
@RequestMapping("/api/logincli/")
@CrossOrigin("*")
public class AccountLoginController {

	@Autowired
	AccountLoginService accountLoginService;

	@PostMapping("login")
	public ResponseEntity<?> login(@RequestBody AccountLoginDTO loginDTO) {
		try {
			String response = accountLoginService.login(loginDTO);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ResponseError("400", e.getMessage()));
		}
	}
}
