package fr.esgi.ecommerce.mapper;

import fr.esgi.ecommerce.dto.PasswordResetRequest;
import fr.esgi.ecommerce.dto.RegistrationRequest;
import fr.esgi.ecommerce.dto.auth.AuthenticationRequest;
import fr.esgi.ecommerce.dto.auth.AuthenticationResponse;
import fr.esgi.ecommerce.dto.user.UserResponse;
import fr.esgi.ecommerce.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthenticationMapper {

    private final AuthenticationService authenticationService;
    private final UserMapper userMapper;

    public AuthenticationResponse login(AuthenticationRequest request) {
        Map<String, String> credentials = authenticationService.login(request.getEmail(), request.getPassword());
        AuthenticationResponse response = new AuthenticationResponse();
        response.setEmail(credentials.get("email"));
        response.setToken(credentials.get("token"));
        response.setUserRole(credentials.get("userRole"));
        return response;
    }

    public UserResponse findByPasswordResetCode(String code) {
        return userMapper.convertToResponseDto(authenticationService.findByPasswordResetCode(code));
    }

    public String registerUser(String captcha, RegistrationRequest registrationRequest) {
        return authenticationService.registerUser(userMapper.convertToEntity(registrationRequest), captcha, registrationRequest.getPassword2());
    }

    public String activateUser(String code) {
        return authenticationService.activateUser(code);
    }

    public String sendPasswordResetCode(String email) {
        return authenticationService.sendPasswordResetCode(email);
    }

    public String passwordReset(String email, PasswordResetRequest passwordReset) {
        return authenticationService.passwordReset(email, passwordReset.getPassword(), passwordReset.getPassword2());
    }
}
