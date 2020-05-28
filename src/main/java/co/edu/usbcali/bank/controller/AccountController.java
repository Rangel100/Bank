package co.edu.usbcali.bank.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usbcali.bank.domain.Account;
import co.edu.usbcali.bank.domain.Client;
import co.edu.usbcali.bank.dto.AccountDTO;
import co.edu.usbcali.bank.mapper.AccountMapper;
import co.edu.usbcali.bank.service.AccountService;
import co.edu.usbcali.bank.service.ClientService;

@RestController
@RequestMapping("/api/account/")
@CrossOrigin("*")
public class AccountController {

	private final static Logger log = LoggerFactory.getLogger(AccountController.class);

	// Cambio por proyecto
	@Autowired
	ClientService clientService;
	//

	@Autowired
	AccountService accountService;

	@Autowired
	AccountMapper accountMapper;

	@PostMapping("save")
	public ResponseEntity<?> save(@RequestBody AccountDTO accountDTO) {
		log.info("call save");
		try {
			Account account = accountMapper.toAccount(accountDTO);
			account = accountService.save(account);
			accountDTO = accountMapper.toAccountDTO(account);
			return ResponseEntity.ok().body(accountDTO);
		} catch (Exception e) {
			log.error("save {}", e.getMessage());
			return ResponseEntity.badRequest().body(new ResponseError("400", e.getMessage()));
		}
	}

	@PutMapping("update")
	public ResponseEntity<?> update(@RequestBody AccountDTO accountDTO) {
		log.info("call update");
		try {
			Account account = accountMapper.toAccount(accountDTO);
			account = accountService.update(account);
			accountDTO = accountMapper.toAccountDTO(account);
			return ResponseEntity.ok().body(accountDTO);
		} catch (Exception e) {
			log.error("update {}", e.getMessage());
			return ResponseEntity.badRequest().body(new ResponseError("400", e.getMessage()));
		}
	}

	@GetMapping("count")
	public ResponseEntity<?> count() {
		log.info("call count");
		return ResponseEntity.ok().body(accountService.count());
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") String id) {
		log.info("call deleteById with id:{}", id);
		try {
			accountService.deleteById(id);
			return ResponseEntity.ok().body("");
		} catch (Exception e) {
			log.error("delete {}", e.getMessage());
			return ResponseEntity.badRequest().body(new ResponseError("400", e.getMessage()));
		}
	}

	@GetMapping("findAll")
	public ResponseEntity<?> findAll() {
		log.info("call findAll");
		List<Account> accounts = accountService.findAll();
		List<AccountDTO> accountDTOs = accountMapper.toAccountDTOs(accounts);
		return ResponseEntity.ok().body(accountDTOs);
	}

	@GetMapping("findById/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") String id) {
		log.info("call findById with id:{}", id);
		Optional<Account> optinalAccount = accountService.findById(id);
		if (optinalAccount.isPresent() == false) {
			return ResponseEntity.badRequest().body("el account no existe");
		}
		Account account = optinalAccount.get();

		AccountDTO accountDTO = accountMapper.toAccountDTO(account);

		return ResponseEntity.ok().body(accountDTO);
	}

	// Cambio por proyecto,controler para buscar cuentas por cliente pasando como
	// parametro el clieId
	@GetMapping("findAccountByClient/{id}")
	public ResponseEntity<?> findAccountByClient(@PathVariable("id") Long id) {
		log.info("call findAccountByClient with clieId:{}", id);
		Optional<Client> optionalClient = clientService.findById(id);
		if (optionalClient.isPresent() == false) {
			return ResponseEntity.badRequest().body("El client no existe");
		}
		List<Account> accounts = accountService.findAccountByClient(id);
		List<AccountDTO> accountDTOs = accountMapper.toAccountDTOs(accounts);
		return ResponseEntity.ok().body(accountDTOs);
	}
	//
}
