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

import co.edu.usbcali.bank.domain.Client;
import co.edu.usbcali.bank.dto.ClientDTO;
import co.edu.usbcali.bank.mapper.ClientMapper;
import co.edu.usbcali.bank.service.ClientService;

@RestController
@RequestMapping("/api/client/")
@CrossOrigin("*")
public class ClientController {

	private final static Logger log = LoggerFactory.getLogger(ClientController.class);

	@Autowired
	ClientService clientService;

	@Autowired
	ClientMapper clientMapper;

	@PostMapping("save")
	public ResponseEntity<?> save(@RequestBody ClientDTO clientDTO) {
		log.info("call save");
		try {
			Client client = clientMapper.toClient(clientDTO);
			client = clientService.save(client);
			clientDTO = clientMapper.toClientDTO(client);
			return ResponseEntity.ok().body(clientDTO);
		} catch (Exception e) {
			log.error("save {}", e.getMessage());
			return ResponseEntity.badRequest().body(new ResponseError("400", e.getMessage()));
		}
	}

	@PutMapping("update")
	public ResponseEntity<?> update(@RequestBody ClientDTO clientDTO) {
		log.info("call update");
		try {
			Client client = clientMapper.toClient(clientDTO);
			client = clientService.update(client);
			clientDTO = clientMapper.toClientDTO(client);
			return ResponseEntity.ok().body(clientDTO);
		} catch (Exception e) {
			log.error("update {}", e.getMessage());
			return ResponseEntity.badRequest().body(new ResponseError("400", e.getMessage()));
		}
	}

	@GetMapping("count")
	public ResponseEntity<?> count() {
		return ResponseEntity.ok().body(clientService.count());
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		log.info("call deleteById with id:{}", id);
		try {
			clientService.deleteById(id);
			return ResponseEntity.ok().body("");
		} catch (Exception e) {
			log.error("delete {}", e.getMessage());
			return ResponseEntity.badRequest().body(new ResponseError("400", e.getMessage()));
		}
	}

	@GetMapping("findAll")
	public ResponseEntity<?> findAll() {
		log.info("call findAll");
		List<Client> clients = clientService.findAll();
		List<ClientDTO> clientDTOs = clientMapper.toClientDTOs(clients);
		return ResponseEntity.ok().body(clientDTOs);
	}

	@GetMapping("findById/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		log.info("call findById with id:{}", id);
		Optional<Client> clientOptional = clientService.findById(id);
		if (clientOptional.isPresent() == false) {
			return ResponseEntity.badRequest().body("el client no existe");
		}

		Client client = clientOptional.get();

		ClientDTO clientDTO = clientMapper.toClientDTO(client);

		return ResponseEntity.ok().body(clientDTO);
	}

}
