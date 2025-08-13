package local.lab.dto;

public record ChangePasswordRequestDTO (String oldPassword, String newPassword, String confirmPassword) {
}
