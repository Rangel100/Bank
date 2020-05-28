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

import co.edu.usbcali.bank.domain.UserType;
import co.edu.usbcali.bank.dto.UserTypeDTO;
import co.edu.usbcali.bank.mapper.UserTypeMapper;
import co.edu.usbcali.bank.service.UserTypeService;

@RestController
@RequestMapping("/api/userType/")
@CrossOrigin("*")
public class UserTypeController {

	private static final Logger log = LoggerFactory.getLogger(UserTypeController.class);

	@Autowired
	UserTypeService userTypeService;

	@Autowired
	UserTypeMapper userTypeMapper;

	@PostMapping("save")
	public ResponseEntity<?> save(@RequestBody UserTypeDTO userTypeDTO) {
		log.info("call save");
		try {
			UserType userType = userTypeMapper.toUserType(userTypeDTO);
			userType = userTypeService.save(userType);
			userTypeDTO = userTypeMapper.toUserTypeDTO(userType);
			return ResponseEntity.ok().body(userTypeDTO);
		} catch (Exception e) {
			log.error("save {}", e.getMessage());
			return ResponseEntity.badRequest().body(new ResponseError("400", e.getMessage()));
		}
	}

	@PutMapping("update")
	public ResponseEntity<?> update(@RequestBody UserTypeDTO userTypeDTO) {
		log.info("call update");
		try {
			UserType userType = userTypeMapper.toUserType(userTypeDTO);
			userType = userTypeService.update(userType);
			userTypeDTO = userTypeMapper.toUserTypeDTO(userType);
			return ResponseEntity.ok().body(userTypeDTO);
		} catch (Exception e) {
			log.error("update {}", e.getMessage());
			return ResponseEntity.badRequest().body(new ResponseError("400", e.getMessage()));
		}
	}

	@GetMapping("count")
	public ResponseEntity<?> count() {
		log.info("call count");
		return ResponseEntity.ok().body(userTypeService.count());
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Long id) {
		log.info("call deleteById with id:{}", id);
		try {
			userTypeService.deleteById(id);
			return ResponseEntity.ok().body("");
		} catch (Exception e) {
			log.error("delete {}", e.getMessage());
			return ResponseEntity.badRequest().body(new ResponseError("400", e.getMessage()));
		}
	}

	@GetMapping("findAll")
	public ResponseEntity<?> findAll() {
		log.info("call findAll");
		List<UserType> userTypes = userTypeService.findAll();
		List<UserTypeDTO> userTypeDTOs = userTypeMapper.toUserTypeDTOs(userTypes);
		return ResponseEntity.ok().body(userTypeDTOs);
	}

	@GetMapping("findById/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		log.info("call findById with id:{}", id);
		Optional<UserType> optinalUserType = userTypeService.findById(id);
		if (optinalUserType.isPresent() == false) {
			return ResponseEntity.badRequest().body("El userType no existe");
		}
		UserType userType = optinalUserType.get();
		UserTypeDTO userTypeDTO = userTypeMapper.toUserTypeDTO(userType);
		return ResponseEntity.ok().body(userTypeDTO);
	}

}
