package co.edu.usbcali.bank.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.usbcali.bank.domain.RegisteredAccount;
import co.edu.usbcali.bank.dto.RegisteredAccountDTO;

@Mapper
public interface RegisteredAccountMapper {

	@Mapping(source = "account.accoId", target = "accoId")
	@Mapping(source = "client.clieId", target = "clieId")
	RegisteredAccountDTO toRegisteredAccountDTO(RegisteredAccount registeredAccount);

	@Mapping(source = "accoId", target = "account.accoId")
	@Mapping(source = "clieId", target = "client.clieId")
	RegisteredAccount toRegisteredAccount(RegisteredAccountDTO registeredAccountDTO);

	List<RegisteredAccountDTO> toRegisteredAccountDTOs(List<RegisteredAccount> registeredAccounts);

	List<RegisteredAccount> toRegisteredAccounts(List<RegisteredAccountDTO> registeredAccountDTOs);

}
