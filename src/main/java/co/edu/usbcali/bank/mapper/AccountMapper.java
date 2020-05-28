package co.edu.usbcali.bank.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.usbcali.bank.domain.Account;
import co.edu.usbcali.bank.dto.AccountDTO;

@Mapper
public interface AccountMapper {

	@Mapping(source = "client.clieId", target = "clieId")
	AccountDTO toAccountDTO(Account account);

	@Mapping(source = "clieId", target = "client.clieId")
	Account toAccount(AccountDTO accountDTO);

	List<AccountDTO> toAccountDTOs(List<Account> accounts);

	List<Account> toAccounts(List<AccountDTO> accountDTOs);

}
