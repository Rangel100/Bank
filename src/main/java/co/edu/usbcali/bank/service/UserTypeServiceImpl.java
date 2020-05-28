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

import co.edu.usbcali.bank.domain.UserType;
import co.edu.usbcali.bank.repository.UserTypeRepository;

@Service
@Scope("singleton")
public class UserTypeServiceImpl implements UserTypeService {

	@Autowired
	UserTypeRepository userTypeRepository;

	@Autowired
	Validator validator;

	@Override
	@Transactional(readOnly = true)
	public List<UserType> findAll() {
		return userTypeRepository.findAll(Sort.by("ustyId"));
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<UserType> findById(Long id) {
		return userTypeRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return userTypeRepository.count();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserType save(UserType entity) throws Exception {
		validate(entity);
		if (userTypeRepository.findByName(entity.getName()).size() > 0) {
			throw new Exception("El userType con nombre " + entity.getName() + " ya existe");
		}
		userTypeRepository.save(entity);
		return entity;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserType update(UserType entity) throws Exception {
		validate(entity);
		if (userTypeRepository.findByName(entity.getName()).size() > 0) {
			throw new Exception("El userType con id " + entity.getName() + " ya existe");
		}
		userTypeRepository.save(entity);
		return entity;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(UserType entity) throws Exception {
		validate(entity);
		entity = userTypeRepository.findById(entity.getUstyId()).get();
		if (entity.getUsers().size() > 0) {
			throw new Exception("El userType tiene users asociados");
		}
		userTypeRepository.delete(entity);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(Long id) throws Exception {
		if (id == null || id < 1) {
			throw new Exception("El id es obligatorio");
		}
		if (userTypeRepository.findById(id).isPresent() == false) {
			throw new Exception("El userType con id " + id + " no existe");
		}
		delete(userTypeRepository.findById(id).get());
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void validate(UserType entity) throws Exception {
		if (entity == null) {
			throw new Exception("El client es nulo");
		}
		Set<ConstraintViolation<UserType>> constraintViolations = validator.validate(entity);
		if (constraintViolations.size() > 0) {
			StringBuilder strMessage = new StringBuilder();

			for (ConstraintViolation<UserType> constraintViolation : constraintViolations) {
				strMessage.append(constraintViolation.getPropertyPath().toString());
				strMessage.append(" - ");
				strMessage.append(constraintViolation.getMessage());
				strMessage.append(". \n");
			}
			throw new Exception(strMessage.toString());
		}
	}
}
