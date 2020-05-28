package co.edu.usbcali.bank.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import co.edu.usbcali.bank.domain.UserType;
import co.edu.usbcali.bank.dto.UserTypeDTO;

@Mapper
public interface UserTypeMapper {

	UserTypeDTO toUserTypeDTO(UserType userType);

	UserType toUserType(UserTypeDTO userTypeDTO);

	List<UserTypeDTO> toUserTypeDTOs(List<UserType> userTypes);

	List<UserType> userTypes(List<UserTypeDTO> userTypeDTOs);
}
