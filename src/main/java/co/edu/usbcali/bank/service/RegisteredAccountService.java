package co.edu.usbcali.bank.service;

import java.util.List;

import co.edu.usbcali.bank.domain.RegisteredAccount;

public interface RegisteredAccountService extends GenericService<RegisteredAccount, Long> {

	// cambio por proyecto
	// Declaracion de metodo para saber las cuentas registradas de un cliente
	public List<RegisteredAccount> findRegisteredAccountsByClient(Long id);
	
	// Declaracion para saber si una cuenta ya tiene una cuenta especifica
	// registrada
	public boolean findRegisteredAccountsByClientAndAccount(Long id, String accoId);
	//
}
