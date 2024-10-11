package yurkov.cloudFileStorage.adapter.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRegistrationRequest (@NotBlank @NotNull @Size(min = 2, max = 50, message = "Username length must be in between 2 and 50 symbols") String username,
                                       @NotBlank @NotNull @Size(min = 5, max = 100, message = "Password length must be in between 5 and 100 symbols") String password) {
}
