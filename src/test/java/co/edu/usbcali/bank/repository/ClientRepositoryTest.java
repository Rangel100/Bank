package co.edu.usbcali.bank.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.bank.domain.Client;
import co.edu.usbcali.bank.domain.DocumentType;

@SpringBootTest
class ClientRepositoryTest {

	private final static Long clieId = 202020L;

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	DocumentTypeRepository documentTypeRepository;

	@Test
	@DisplayName("save")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void atest() {
		assertNotNull(clientRepository, "El clientRepository es nulo");

		Client client = new Client();
		client.setClieId(clieId);
		client.setAdress("avenida siempre viva 123");
		client.setEmail("hola12@hotla.com");
		client.setEnable("S");
		client.setName("Homero J Simpson");
		client.setPhone("555 55 555 555");

		Optional<DocumentType> documentTypeOptional = documentTypeRepository.findById(1L);
		assertTrue(documentTypeOptional.isPresent(), "El tipo de documento no existe");
		DocumentType documentType = documentTypeOptional.get();

		client.setDocumentType(documentType);

		clientRepository.save(client);
	}

	@Test
	@DisplayName("findById")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	void btest() {
		assertNotNull(clientRepository, "El clientRepository es nulo");
		assertTrue(clientRepository.findById(clieId).isPresent(), "El client con id " + clieId + " es nulo");
	}

	@Test
	@DisplayName("update")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void ctest() {
		assertNotNull(clientRepository, "El clientRepository es nulo");
		assertTrue(clientRepository.findById(clieId).isPresent(), "El client con id " + clieId + " es nulo");

		Client client = clientRepository.findById(clieId).get();
		client.setName("Jhonatan rangel");
		clientRepository.save(client);
	}

	@Test
	@DisplayName("delete")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void dtest() {
		assertNotNull(clientRepository, "El clientRepository es nulo");
		assertTrue(clientRepository.findById(clieId).isPresent(), "El client con id " + clieId + " es nulo");

		Client client = clientRepository.findById(clieId).get();
		clientRepository.delete(client);
	}

	@Test
	@DisplayName("findAll")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void etest() {
		assertNotNull(clientRepository, "El clientRepository es nulo");

		List<Client> clients = clientRepository.findAll();
		assertFalse(clients.isEmpty(), "Los clientes esta vacio");
	}

	@Test
	@DisplayName("findByName")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void fTest() {
		assertNotNull(clientRepository, "El clientRepository es nulo");

		List<Client> clients = clientRepository.findByName("Karole Dunge");
		assertFalse(clients.isEmpty(), "Los clientes esta vacio");
	}

	@Test
	@DisplayName("findByEmail")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void gTest() {
		assertNotNull(clientRepository, "El clientRepository es nulo");

		List<Client> clients = clientRepository.findByEmail("hdownes0@bloomberg.com");
		assertFalse(clients.isEmpty(), "Los clientes esta vacio");
	}

	@Test
	@DisplayName("findByEmailAndName")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void findByEmailAndName() {
		assertNotNull(clientRepository, "El clientRepository es nulo");

		List<Client> clients = clientRepository.findByEmailAndName("jcannell1@stanford.edu", "Jerrie Cannell");
		assertFalse(clients.isEmpty(), "Los clientes esta vacio");
	}

	@Test
	@DisplayName("findByDocumentType")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void findByDocumentType() {
		assertNotNull(clientRepository, "El clientRepository es nulo");

		List<Client> clients = clientRepository.findByDocumentType(2L);
		assertFalse(clients.isEmpty(), "Los clientes esta vacio");
	}

	private final static Logger log = LoggerFactory.getLogger(ClientRepositoryTest.class);

	@Test
	@DisplayName("findAllSort")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void findAllSort() {
		assertNotNull(clientRepository, "El clientRepository es nulo");

		Sort sort = Sort.by("name").ascending();
		List<Client> clients = clientRepository.findAll(sort);

		for (Client client : clients) {
			log.info("Name: " + client.getName());
		}
	}
	
	@Test
	@DisplayName("findByNameContains")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void findByNameContains() {
		assertNotNull(clientRepository, "El clientRepository es nulo");

		List<Client> clients = clientRepository.findByNameContains("J");
		
		for (Client client : clients) {
			log.info("Name: " + client.getName());
		}
	}
}
