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

import co.edu.usbcali.bank.domain.TransactionType;
import co.edu.usbcali.bank.dto.TransactionTypeDTO;
import co.edu.usbcali.bank.mapper.TransactionTypeMapper;
import co.edu.usbcali.bank.service.TransactionTypeService;

@RestController
@RequestMapping("/api/transactionType/")
@CrossOrigin("*")
public class TransactionTypeController {

	private static final Logger log = LoggerFactory.getLogger(TransactionController.class);

	@Autowired
	TransactionTypeService transactionTypeService;

	@Autowired
	TransactionTypeMapper transactionTypeMapper;

	@PostMapping("save")
	public ResponseEntity<?> save(@RequestBody TransactionTypeDTO transactionTypeDTO) {
		log.info("call save");
		try {
			TransactionType transactionType = transactionTypeMapper.toTransactionType(transactionTypeDTO);
			transactionType = transactionTypeService.save(transactionType);
			transactionTypeDTO = transactionTypeMapper.toTransactionTypeDTO(transactionType);
			return ResponseEntity.ok().body(transactionTypeDTO);
		} catch (Exception e) {
			log.error("save {}", e.getMessage());
			return ResponseEntity.badRequest().body(new ResponseError("400", e.getMessage()));
		}
	}

	@PutMapping("update")
	public ResponseEntity<?> update(@RequestBody TransactionTypeDTO transactionTypeDTO) {
		log.info("call update");
		try {
			TransactionType transactionType = transactionTypeMapper.toTransactionType(transactionTypeDTO);
			transactionType = transactionTypeService.update(transactionType);
			transactionTypeDTO = transactionTypeMapper.toTransactionTypeDTO(transactionType);
			return ResponseEntity.ok().body(transactionTypeDTO);
		} catch (Exception e) {
			log.error("update {}", e.getMessage());
			return ResponseEntity.badRequest().body(new ResponseError("400", e.getMessage()));
		}
	}

	@GetMapping("count")
	public ResponseEntity<?> count() {
		log.info("call count");
		return ResponseEntity.ok().body(transactionTypeService.count());
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Long id) {
		log.info("call deleteById with id:{}", id);
		try {
			transactionTypeService.deleteById(id);
			return ResponseEntity.ok().body("");
		} catch (Exception e) {
			log.error("delete {}", e.getMessage());
			return ResponseEntity.badRequest().body(new ResponseError("400", e.getMessage()));
		}
	}

	@GetMapping("findAll")
	public ResponseEntity<?> findAll() {
		log.info("call findAll");
		List<TransactionType> transactionTypes = transactionTypeService.findAll();
		List<TransactionTypeDTO> transactionTypeDTOs = transactionTypeMapper.toTransactionTypeDTOs(transactionTypes);
		return ResponseEntity.ok().body(transactionTypeDTOs);
	}

	@GetMapping("findById/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		log.info("call findById with id:{}", id);
		Optional<TransactionType> optionalTransactionType = transactionTypeService.findById(id);
		if (optionalTransactionType.isPresent() == false) {
			return ResponseEntity.badRequest().body("El transactionType no existe");
		}
		TransactionType transactionType = optionalTransactionType.get();
		TransactionTypeDTO transactionTypeDTO = transactionTypeMapper.toTransactionTypeDTO(transactionType);
		return ResponseEntity.ok().body(transactionTypeDTO);
	}
}
