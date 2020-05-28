package co.edu.usbcali.bank.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.usbcali.bank.domain.Transaction;
import co.edu.usbcali.bank.dto.TransactionDTO;

@Mapper
public interface TransactionMapper {

	@Mapping(source = "account.accoId", target = "accoId")
	@Mapping(source = "transactionType.trtyId", target = "trtyId")
	@Mapping(source = "user.userEmail", target = "userEmail")
	TransactionDTO toTransactionDTO(Transaction transaction);

	@Mapping(source = "accoId", target = "account.accoId")
	@Mapping(source = "trtyId", target = "transactionType.trtyId")
	@Mapping(source = "userEmail", target = "user.userEmail")
	Transaction toTransaction(TransactionDTO transactionDTO);

	List<TransactionDTO> transactionDTOs(List<Transaction> transactions);

	List<Transaction> transactions(List<TransactionDTO> transactionDTOs);
}
