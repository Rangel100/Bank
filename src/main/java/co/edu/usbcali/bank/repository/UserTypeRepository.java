package co.edu.usbcali.bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.usbcali.bank.domain.UserType;

public interface UserTypeRepository extends JpaRepository<UserType, Long> {

	public List<UserType> findByName(String name);
}
