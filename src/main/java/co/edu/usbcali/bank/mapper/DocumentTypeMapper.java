package co.edu.usbcali.bank.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import co.edu.usbcali.bank.domain.DocumentType;
import co.edu.usbcali.bank.dto.DocumentTypeDTO;

@Mapper
public interface DocumentTypeMapper {

	DocumentTypeDTO toDocumentTypeDTO(DocumentType documentType);

	DocumentType toDocumentType(DocumentTypeDTO documentTypeDTO);

	List<DocumentType> toDocumentTypes(List<DocumentTypeDTO> documentTypeDTOs);

	List<DocumentTypeDTO> toDocumentTypeDTOs(List<DocumentType> documentTypes);
}
