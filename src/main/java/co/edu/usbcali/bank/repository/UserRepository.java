package co.edu.usbcali.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.usbcali.bank.domain.User;

public interface UserRepository extends JpaRepository<User, String> {

}
