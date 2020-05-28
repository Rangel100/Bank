package co.edu.usbcali.bank.service;

import java.util.List;

import co.edu.usbcali.bank.domain.Account;

public interface AccountService extends GenericService<Account, String> {
	
	// Cambio por proyecto
	// Declaracion de metodo para generar automaticamente el id de la cuenta
	public String generarAccoId();

	// Declaracion de metodo para generar automaticamente la contraseña de la cuenta
	public String generarPassword();

	// Declaracion de metodo para saber las cuentas de un cliente
	public List<Account> findAccountByClient(Long id);
	//
}
