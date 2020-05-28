package co.edu.usbcali.bank.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.bank.domain.DocumentType;

@SpringBootTest
class DocumentTypeServiceTest {

	private static Long dotyId=null; 
	
	@Autowired
	DocumentTypeService documentTypeService;
	
	@Test
	@DisplayName("save")
	@Rollback(false)
	void aTest() {
		assertNotNull(documentTypeService, "El documentTypeService es nulo");
		
		DocumentType documentType=new DocumentType();
		documentType.setDotyId(dotyId);
		documentType.setEnable("S");
		documentType.setName("New documentType");
		
		try {
			documentTypeService.save(documentType);
			assertNotNull(documentType.getDotyId(),"No genero id");
			dotyId=documentType.getDotyId();
		} catch (Exception e) {
			assertNull(e,e.getMessage());
		}
	}
	
	@Test
	@DisplayName("findById")
	void bTest() {
		assertTrue(documentTypeService.findById(dotyId).isPresent());
		DocumentType documentType=documentTypeService.findById(dotyId).get();
		assertNotNull(documentType,"El documentType con id "+dotyId +" es nulo");
	}

	@Test
	@DisplayName("update")
	@Rollback(false)
	void cTest() {
		assertNotNull(documentTypeService, "El documentTypeService es nulo");
		assertTrue(documentTypeService.findById(dotyId).isPresent(), "El documentType con id " + dotyId + " es nulo");

		DocumentType documentType = documentTypeService.findById(dotyId).get();
		documentType.setName("neww");
		
		try {
			documentTypeService.update(documentType);
		}catch (Exception e) {
			assertNull(e,e.getMessage());
		}	
	}

	@Test
	@DisplayName("delete")
	@Rollback(false)
	void dTest() {
		assertNotNull(documentTypeService, "El documentTypeService es nulo");
		assertTrue(documentTypeService.findById(dotyId).isPresent(), "El documentType con id " + dotyId + " es nulo");

		DocumentType documentType = documentTypeService.findById(dotyId).get();
		try {
			documentTypeService.delete(documentType);
		} catch (Exception e) {
			assertNull(e,e.getMessage());
		}
	}

	@Test
	@DisplayName("findAll")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	void eTest() {
		assertNotNull(documentTypeService, "El documentTypeService es nulo");

		List<DocumentType> documentTypes = documentTypeService.findAll();
		assertFalse(documentTypes.isEmpty(), "Los documentTypes esta vacio");
	}
}
