package de.grado.backofficeservice.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StartProcessDTO
{
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String dateOfBirth;

    @NotBlank
    private String accountNumber;

    @Nullable
    private String fileName;
}
