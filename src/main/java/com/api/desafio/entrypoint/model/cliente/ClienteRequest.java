package com.api.desafio.entrypoint.model.cliente;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
        "nome",
        "sobrenome",
        "email",
        "telefone",
        "cep"
})
public class ClienteRequest {
    @NotBlank(message = "O nome precisa ser válido")
    private String nome;
    @NotBlank(message = "O sobrenome precisa ser válido")
    private String sobrenome;

    @Email(message = "O email deve ser válido!")
    private String email;

    @Pattern(regexp = "^\\d{2}\\d{5}\\d{4}$", message = "o telefone deve ser válido!")
    private String telefone;

    @Pattern(regexp = "\\d{8}", message = "CEP deve conter exatamente 8 dígitos")
    private String cep;
}
