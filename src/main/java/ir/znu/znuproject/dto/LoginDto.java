package ir.znu.znuproject.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@Builder
public class LoginDto {
    private String token;
    private Optional<UserDTO> user;

}