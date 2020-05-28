package co.edu.usbcali.bank.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.bank.domain.Client;
import co.edu.usbcali.bank.domain.DocumentType;

@SpringBootTest
class ClientServiceTest {

	private final static Long clieId = 101010L;

	@Autowired
	ClientService clientService;

	@Autowired
	DocumentTypeService documentTypeService;

	@Test
	@DisplayName("save")
	@Rollback(false)
	void aTest() {
		assertNotNull(clientService, "El clientService es nulo");

		Client client = new Client();
		client.setClieId(clieId);
		client.setAdress("avenida siempre viva 123");
		client.setEmail("jhonatan-rangel99@hotmail.com");
		client.setEnable("S");
		client.setName("Homero J Simpson");
		client.setPhone("555 55 555 555");

		Optional<DocumentType> documentTypeOptional = documentTypeService.findById(1L);
		assertTrue(documentTypeOptional.isPresent(), "El tipo de documento no existe");
		DocumentType documentType = documentTypeOptional.get();

		client.setDocumentType(documentType);

		try {
			clientService.save(client);
		}catch (Exception e) {
			assertNull(e,e.getMessage());
		}		
	}

	@Test
	@DisplayName("findById")
	void bTest() {
		assertNotNull(clientService, "El clientService es nulo");
		assertTrue(clientService.findById(clieId).isPresent(), "El client con id " + clieId + " es nulo");
	}

	@Test
	@DisplayName("update")
	@Rollback(false)
	void cTest() {
		assertNotNull(clientService, "El clientService es nulo");
		assertTrue(clientService.findById(clieId).isPresent(), "El client con id " + clieId + " es nulo");

		Client client = clientService.findById(clieId).get();
		client.setName("Jhonatan rangel");
		
		try {
			clientService.update(client);
		}catch (Exception e) {
			assertNull(e,e.getMessage());
		}	
	}

	@Test
	@DisplayName("delete")
	@Rollback(false)
	void dTest() {
		assertNotNull(clientService, "El clientService es nulo");
		assertTrue(clientService.findById(clieId).isPresent(), "El client con id " + clieId + " es nulo");

		Client client = clientService.findById(clieId).get();
		try {
			clientService.delete(client);
		} catch (Exception e) {
			assertNull(e,e.getMessage());
		}
	}

	@Test
	@DisplayName("findAll")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void eTest() {
		assertNotNull(clientService, "El clientService es nulo");

		List<Client> clients = clientService.findAll();
		assertFalse(clients.isEmpty(), "Los clientes esta vacio");
	}
}
