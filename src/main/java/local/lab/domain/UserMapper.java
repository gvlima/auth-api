package local.lab.domain;

import local.lab.dto.LoginResponseDTO;
import local.lab.dto.RegisterResponseDTO;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    LoginResponseDTO toLoginResponseDTO(Login login);
    RegisterResponseDTO toRegisterResponseDTO(User user);
}
