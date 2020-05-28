package co.edu.usbcali.bank.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import co.edu.usbcali.bank.domain.TransactionType;
import co.edu.usbcali.bank.dto.TransactionTypeDTO;

@Mapper
public interface TransactionTypeMapper {

	TransactionTypeDTO toTransactionTypeDTO(TransactionType transactionType);

	TransactionType toTransactionType(TransactionTypeDTO transactionTypeDTO);

	List<TransactionTypeDTO> toTransactionTypeDTOs(List<TransactionType> transactionTypes);

	List<TransactionType> toTransactionTypes(List<TransactionTypeDTO> transactionTypeDTOs);
}
