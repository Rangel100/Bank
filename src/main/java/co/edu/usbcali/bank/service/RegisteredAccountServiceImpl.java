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
import co.edu.usbcali.bank.domain.RegisteredAccount;
import co.edu.usbcali.bank.repository.AccountRepository;
import co.edu.usbcali.bank.repository.ClientRepository;
import co.edu.usbcali.bank.repository.RegisteredAccountRepository;

@Service
@Scope("singleton")
public class RegisteredAccountServiceImpl implements RegisteredAccountService {

	@Autowired
	RegisteredAccountRepository registeredAccountRepository;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	Validator validator;

	@Override
	@Transactional(readOnly = true)
	public List<RegisteredAccount> findAll() {
		return registeredAccountRepository.findAll(Sort.by("reacId"));
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<RegisteredAccount> findById(Long id) {
		return registeredAccountRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return registeredAccountRepository.count();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public RegisteredAccount save(RegisteredAccount entity) throws Exception {
		validate(entity);
		if (entity.getAccount() == null
				|| accountRepository.findById(entity.getAccount().getAccoId()).isPresent() == false) {
			throw new Exception("El account no existe");
		}
		if (entity.getClient() == null
				|| clientRepository.findById(entity.getClient().getClieId()).isPresent() == false) {
			throw new Exception("El client no existe");
		}
		//cambio por proyecto
		Account account=accountRepository.findById(entity.getAccount().getAccoId()).get();
		if (account.getEnable().equals("N")) {
			throw new Exception("La cuenta esta desactivada");
		}
		if (findRegisteredAccountsByClientAndAccount(entity.getClient().getClieId(), entity.getAccount().getAccoId())) {
			throw new Exception("La cuenta ya esta registrada");
		}
		//
		registeredAccountRepository.save(entity);
		return entity;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public RegisteredAccount update(RegisteredAccount entity) throws Exception {
		validate(entity);
		if (entity.getAccount() == null
				|| accountRepository.findById(entity.getAccount().getAccoId()).isPresent() == false) {
			throw new Exception("El account no existe");
		}
		if (entity.getClient() == null
				|| clientRepository.findById(entity.getClient().getClieId()).isPresent() == false) {
			throw new Exception("El client no existe");
		}
		registeredAccountRepository.save(entity);
		return entity;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(RegisteredAccount entity) throws Exception {
		validate(entity);
		entity = registeredAccountRepository.findById(entity.getReacId()).get();
		registeredAccountRepository.delete(entity);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(Long id) throws Exception {
		if (id == null || id < 1) {
			throw new Exception("El id es obligatorio");
		}
		if (registeredAccountRepository.findById(id).isPresent() == false) {
			throw new Exception("El registeredAccount con id " + id + " no existe");
		}
		delete(registeredAccountRepository.findById(id).get());
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void validate(RegisteredAccount entity) throws Exception {
		if (entity == null) {
			throw new Exception("El registeredAccount es nulo");
		}
		Set<ConstraintViolation<RegisteredAccount>> constraintViolations = validator.validate(entity);
		if (constraintViolations.size() > 0) {
			StringBuilder strMessage = new StringBuilder();

			for (ConstraintViolation<RegisteredAccount> constraintViolation : constraintViolations) {
				strMessage.append(constraintViolation.getPropertyPath().toString());
				strMessage.append(" - ");
				strMessage.append(constraintViolation.getMessage());
				strMessage.append(". \n");
			}
			throw new Exception(strMessage.toString());
		}
	}

	// cambio por proyecto
	// metodo para saber las cuentas regisradas de un cliente pasando
	// como parametro el clieId
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<RegisteredAccount> findRegisteredAccountsByClient(Long id) {
		return registeredAccountRepository.findRegisteredAccountsByClient(id);
	}
	
	//metodo para saber si una cuenta ya tiene una cuenta especifica
	// registrada
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean findRegisteredAccountsByClientAndAccount(Long id,String accoId) {
		RegisteredAccount registeredAccount=registeredAccountRepository.findRegisteredAccountsByClientAndAccount(id, accoId);
		if (registeredAccount!=null) {
			return true;
		}
		return false;
	}
	//
}
