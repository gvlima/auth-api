package local.lab.domain;

import local.lab.dto.LoginResponseDTO;
import org.mapstruct.Mapper;

@Mapper
public interface LoginMapper {
    LoginResponseDTO toLoginResponseDTO(Login login);
}
