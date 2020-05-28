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

import co.edu.usbcali.bank.domain.User;
import co.edu.usbcali.bank.repository.UserRepository;
import co.edu.usbcali.bank.repository.UserTypeRepository;

@Service
@Scope("singleton")
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserTypeRepository userTypeRepository;

	@Autowired
	Validator validator;

	@Override
	@Transactional(readOnly = true)
	public List<User> findAll() {
		return userRepository.findAll(Sort.by("name"));
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> findById(String id) {
		return userRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return userRepository.count();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public User save(User entity) throws Exception {
		validate(entity);
		// validar si ese user no existe
		if (userRepository.findById(entity.getUserEmail()).isPresent() == true) {
			throw new Exception("El user con id " + entity.getUserEmail() + " ya existe");
		}
		if (entity.getUserType() == null
				|| userTypeRepository.findById(entity.getUserType().getUstyId()).isPresent() == false) {
			throw new Exception("El userType no existe");
		}
		userRepository.save(entity);
		return entity;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public User update(User entity) throws Exception {
		validate(entity);
		// validar si ese user existe
		if (userRepository.findById(entity.getUserEmail()).isPresent() == false) {
			throw new Exception("El user con id " + entity.getUserEmail() + " no existe");
		}
		if (entity.getUserType() == null
				|| userTypeRepository.findById(entity.getUserType().getUstyId()).isPresent() == false) {
			throw new Exception("El userType no existe");
		}
		userRepository.save(entity);
		return entity;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(User entity) throws Exception {
		validate(entity);
		// validar si ese user existe
		if (userRepository.findById(entity.getUserEmail()).isPresent() == false) {
			throw new Exception("El user con id " + entity.getUserEmail() + " no existe");
		}
		entity = userRepository.findById(entity.getUserEmail()).get();
		if (entity.getTransactions().size() > 0) {
			throw new Exception("El user tiene transactions asociadas");
		}
		userRepository.delete(entity);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(String id) throws Exception {
		if (id == null) {
			throw new Exception("El id es obligatorio");
		}
		if (userRepository.findById(id).isPresent() == false) {
			throw new Exception("El user con id " + id + " no existe");
		}
		delete(userRepository.findById(id).get());

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void validate(User entity) throws Exception {
		if (entity == null) {
			throw new Exception("El client es nulo");
		}
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(entity);
		if (constraintViolations.size() > 0) {
			StringBuilder strMessage = new StringBuilder();

			for (ConstraintViolation<User> constraintViolation : constraintViolations) {
				strMessage.append(constraintViolation.getPropertyPath().toString());
				strMessage.append(" - ");
				strMessage.append(constraintViolation.getMessage());
				strMessage.append(". \n");
			}
			throw new Exception(strMessage.toString());
		}
	}
}
