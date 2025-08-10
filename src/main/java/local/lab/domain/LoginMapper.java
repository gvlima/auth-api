package local.lab.domain;

import local.lab.dto.LoginResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginMapper {
    LoginResponseDTO toLoginResponseDTO(Login login);
}
