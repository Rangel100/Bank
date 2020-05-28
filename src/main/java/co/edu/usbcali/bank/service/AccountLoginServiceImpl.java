package co.edu.usbcali.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import co.edu.usbcali.bank.domain.Account;
import co.edu.usbcali.bank.dto.AccountLoginDTO;

@Service
@Scope("singleton")
public class AccountLoginServiceImpl implements AccountLoginService {

	@Autowired
	AccountService accountService;

	@Override
	public String login(AccountLoginDTO loginDTO) throws Exception {
		if (accountService.findById(loginDTO.getAccoId()).isPresent() == false) {
			throw new Exception("El account con id: " + loginDTO.getAccoId() + " no existe");
		}

		Account account = accountService.findById(loginDTO.getAccoId()).get();
		
		if (account.getEnable().trim().equals("N")==true) {
			throw new Exception("La cuenta se encuentra inactiva");
		}
		if (account.getClient().getClieId() != loginDTO.getClieId()) {
			throw new Exception("El numero del identificacion no coinciden con el dueño de la cuenta");
		}
		if (account.getPassword().trim().equals(loginDTO.getPassword().trim()) == false) {
			throw new Exception("Contraseña incorrecta");
		}
		Long accoId=account.getClient().getClieId();
		return accoId.toString();
	}

}
