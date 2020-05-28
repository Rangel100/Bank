package co.edu.usbcali.bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.usbcali.bank.domain.TransactionType;

public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long> {

	public List<TransactionType> findByName(String name);
}
