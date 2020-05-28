package co.edu.usbcali.bank.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.usbcali.bank.domain.Client;
import co.edu.usbcali.bank.dto.ClientDTO;

@Mapper
public interface ClientMapper {

	@Mapping(source = "documentType.dotyId", target = "dotyId")
	ClientDTO toClientDTO(Client client);

	@Mapping(source = "dotyId", target = "documentType.dotyId")
	Client toClient(ClientDTO clientDTO);

	List<ClientDTO> toClientDTOs(List<Client> clients);

	List<Client> toClients(List<ClientDTO> clientDTOs);

}
