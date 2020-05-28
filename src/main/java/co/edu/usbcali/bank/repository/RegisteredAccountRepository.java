package co.edu.usbcali.bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.usbcali.bank.domain.RegisteredAccount;

public interface RegisteredAccountRepository extends JpaRepository<RegisteredAccount, Long> {

	// Cambio por proyecto
	// repository para saber las cuentas regisradas de un cliente pasando como
	// parametro el clieId
	@Query("SELECT r FROM RegisteredAccount r WHERE r.client.clieId=:clieId")
	public List<RegisteredAccount> findRegisteredAccountsByClient(@Param("clieId") Long id);

	// repositorio para saber si una cuenta ya tiene una cuenta especifica
	// registrada
	@Query("SELECT r FROM RegisteredAccount r WHERE r.client.clieId=:clieId AND r.account.accoId=:accoId")
	public RegisteredAccount findRegisteredAccountsByClientAndAccount(@Param("clieId") Long id,@Param("accoId") String accoId);
	//

}
