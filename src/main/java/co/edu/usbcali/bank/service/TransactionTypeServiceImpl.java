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

import co.edu.usbcali.bank.domain.TransactionType;
import co.edu.usbcali.bank.repository.TransactionTypeRepository;

@Service
@Scope("singleton")
public class TransactionTypeServiceImpl implements TransactionTypeService {

	@Autowired
	TransactionTypeRepository transactionTypeRepository;

	@Autowired
	Validator validator;

	@Override
	@Transactional(readOnly = true)
	public List<TransactionType> findAll() {
		return transactionTypeRepository.findAll(Sort.by("trtyId"));
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<TransactionType> findById(Long id) {
		return transactionTypeRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return transactionTypeRepository.count();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public TransactionType save(TransactionType entity) throws Exception {
		validate(entity);
		if (transactionTypeRepository.findByName(entity.getName()).size() > 0) {
			throw new Exception("El transactionType con nombre " + entity.getName() + " ya existe");
		}
		transactionTypeRepository.save(entity);
		return entity;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public TransactionType update(TransactionType entity) throws Exception {
		validate(entity);
		if (transactionTypeRepository.findByName(entity.getName()).size() > 0) {
			throw new Exception("El transactionType con id " + entity.getName() + " ya existe");
		}
		transactionTypeRepository.save(entity);
		return entity;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(TransactionType entity) throws Exception {
		validate(entity);
		entity = transactionTypeRepository.findById(entity.getTrtyId()).get();
		if (entity.getTransactions().size() > 0) {
			throw new Exception("El transactionType tiene transactions asociados");
		}
		transactionTypeRepository.delete(entity);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(Long id) throws Exception {
		if (id == null || id < 1) {
			throw new Exception("El id es obligatorio");
		}
		if (transactionTypeRepository.findById(id).isPresent() == false) {
			throw new Exception("El transactionType con id " + id + " no existe");
		}
		delete(transactionTypeRepository.findById(id).get());
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void validate(TransactionType entity) throws Exception {
		if (entity == null) {
			throw new Exception("El client es nulo");
		}
		Set<ConstraintViolation<TransactionType>> constraintViolations = validator.validate(entity);
		if (constraintViolations.size() > 0) {
			StringBuilder strMessage = new StringBuilder();

			for (ConstraintViolation<TransactionType> constraintViolation : constraintViolations) {
				strMessage.append(constraintViolation.getPropertyPath().toString());
				strMessage.append(" - ");
				strMessage.append(constraintViolation.getMessage());
				strMessage.append(". \n");
			}
			throw new Exception(strMessage.toString());
		}
	}
}
