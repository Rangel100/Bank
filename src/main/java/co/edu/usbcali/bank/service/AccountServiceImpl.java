package co.edu.usbcali.bank.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.bank.domain.Account;
import co.edu.usbcali.bank.repository.AccountRepository;
import co.edu.usbcali.bank.repository.ClientRepository;

@Service
@Scope("singleton")
public class AccountServiceImpl implements AccountService {

	// Cambio por proyecto
	//private final static Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);
	
	@Autowired
	SendMailServiceImpl sendMailServiceImpl;
	//

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	Validator validator;

	@Override
	@Transactional(readOnly = true)
	public List<Account> findAll() {
		return accountRepository.findAll(Sort.by("accoId"));
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Account> findById(String id) {
		return accountRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return accountRepository.count();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Account save(Account entity) throws Exception {
		validate(entity);
		// validar si ese account no existe
		if (accountRepository.findById(entity.getAccoId()).isPresent() == true) {
			throw new Exception("El Account con id " + entity.getAccoId() + " ya existe");
		}
		if (entity.getClient() == null
				|| clientRepository.findById(entity.getClient().getClieId()).isPresent() == false) {
			throw new Exception("El client no existe");
		}
		// Cambio por proyecto
//		Client client = clientRepository.findById(entity.getClient().getClieId()).get();
//		entity.setClient(client);
		entity.setAccoId(generarAccoId());
		entity.setPassword(generarPassword());
		//

		Account savedAccount=accountRepository.save(entity);
		sendMailServiceImpl.sendMailNewAccount(savedAccount.getClient().getEmail(), savedAccount);
		return entity;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Account update(Account entity) throws Exception {
		validate(entity);
		// validar si ese account existe
		if (accountRepository.findById(entity.getAccoId()).isPresent() == false) {
			throw new Exception("El Account con id " + entity.getAccoId() + " no existe");
		}
		if (entity.getClient() == null
				|| clientRepository.findById(entity.getClient().getClieId()).isPresent() == false) {
			throw new Exception("El client no existe");
		}

		accountRepository.save(entity);
		// Cambio por proyecto, validaciones para mandar correo de cambio de contraseña
		Optional<Account> accountOptional = accountRepository.findById(entity.getAccoId());
		if (accountOptional.get().getPassword() != entity.getPassword()) {
			sendMailServiceImpl.sendMailUpdatePassword(entity.getClient().getEmail(), entity.getAccoId(),
					entity.getPassword());
			//log.info("Correo enviado" + entity.getClient().getEmail());
		}
		//
		return entity;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(Account entity) throws Exception {
		validate(entity);
		// validar si ese account existe
		if (accountRepository.findById(entity.getAccoId()).isPresent() == false) {
			throw new Exception("El account con id " + entity.getAccoId() + " no existe");
		}
		entity = accountRepository.findById(entity.getAccoId()).get();
		if (entity.getRegisteredAccounts().size() > 0) {
			throw new Exception("El account tiene Accounts registradas");
		}
		if (entity.getTransactions().size() > 0) {
			throw new Exception("El account tiene transactions asociadas");
		}
		accountRepository.delete(entity);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(String id) throws Exception {
		if (id == null) {
			throw new Exception("El id es obligatorio");
		}
		if (accountRepository.findById(id).isPresent() == false) {
			throw new Exception("El account con id " + id + " no existe");
		}
		// Cambio por proyecto
		Account account = accountRepository.findById(id).get();
		account.setEnable("N");
		update(account);
		//
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void validate(Account entity) throws Exception {
		if (entity == null) {
			throw new Exception("El account es nulo");
		}
		Set<ConstraintViolation<Account>> constraintViolations = validator.validate(entity);
		if (constraintViolations.size() > 0) {
			StringBuilder strMessage = new StringBuilder();

			for (ConstraintViolation<Account> constraintViolation : constraintViolations) {
				strMessage.append(constraintViolation.getPropertyPath().toString());
				strMessage.append(" - ");
				strMessage.append(constraintViolation.getMessage());
				strMessage.append(". \n");
			}
			throw new Exception(strMessage.toString());
		}
	}

	// Cambio por proyecto
	// Implementacion de metodo para generar automaticamente el id de la cuenta
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public String generarAccoId() {
		String AccoId = "";
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				// Valor entre M y N con M menor que N, ambos incluidos (N-M+1)+M).
				Integer numero = (int) Math.floor(Math.random() * (9 - 0 + 1) + 0);
				AccoId += numero.toString();
				// log.info("Acco es: " + AccoId);
			}
			if (i != 3) {
				AccoId += "-";
			}
			// log.info("Final: " + AccoId);
		}
		return AccoId;
	}

	// Implementacion de metodo para generar automaticamente el id de la cuenta
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public String generarPassword() {
		char[] caracteres = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
				'q', 'r', 's', 't', 'u', 'w', 'x', 'y', 'z', '1', '2', '3', '3', '5', '6', '7', '8', '9', '0' };
		// log.info("caracter: " + caracteres.length);
		String password = "";
		for (int i = 0; i < 8; i++) {
			char caracter = caracteres[(int) (Math.random() * 35)];
			password += String.valueOf(caracter);
		}
		// log.info("Password: " + password);
		return password;
	}

	// Implementacion de metodo para saber las cuentas de un cliente pasando como
	// parametro el clieId
	@Override
	@Transactional(readOnly = true)
	public List<Account> findAccountByClient(Long id) {
		// Client client = clientRepository.findById(id).get();
		return accountRepository.findAccountsByClient(id);
	}
	//
}
