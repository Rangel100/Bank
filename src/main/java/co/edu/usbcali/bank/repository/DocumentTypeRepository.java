package co.edu.usbcali.bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.usbcali.bank.domain.DocumentType;

public interface DocumentTypeRepository extends JpaRepository<DocumentType, Long> {

	public List<DocumentType> findByName(String name);
}
