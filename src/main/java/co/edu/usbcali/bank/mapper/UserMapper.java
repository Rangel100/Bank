package co.edu.usbcali.bank.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.usbcali.bank.domain.Client;
import co.edu.usbcali.bank.domain.User;
import co.edu.usbcali.bank.dto.ClientDTO;
import co.edu.usbcali.bank.dto.UserDTO;

@Mapper
public interface UserMapper {

	@Mapping(source = "userType.ustyId", target = "ustyId")
	UserDTO toUserDTO(User user);

	@Mapping(source = "ustyId", target = "userType.ustyId")
	User toUser(UserDTO userDTO);

	List<UserDTO> toUserDTOs(List<User> user);

	List<User> toUsers(List<UserDTO> userDTO);
}
