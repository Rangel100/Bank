package co.edu.usbcali.bank.service;

import co.edu.usbcali.bank.dto.AccountLoginDTO;

public interface AccountLoginService {
	
	public String login(AccountLoginDTO loginDTO) throws Exception;

}
