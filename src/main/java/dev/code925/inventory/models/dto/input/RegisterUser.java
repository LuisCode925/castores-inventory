package dev.code925.inventory.models.dto.input;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUser {

    @NotBlank(message = "El campo name es obligatorio.")
    @Length(min = 10, max = 100, message = "El nombre completo debe tener entre 10-100 caracteres.")
    private String name;

    @Email(message = "El formato no concuerda con un correo electronico valido.")
    @NotBlank(message = "El campo email es obligatorio.")
    @Length(min = 10, max = 50, message = "El correo electronico debe tener entre 10-50 caracteres.")
    private String email;

    @NotBlank(message = "El campo password es obligatorio.")
    @Length(min = 8, max = 25, message = "Su contraseña debe tener entre 8-25 caracteres incluidos: numeros, minusculas, mayusculas y un caracter especial.")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W_]).{8,25}$")
    private String password;
}
