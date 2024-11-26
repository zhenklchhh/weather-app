package logic.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    private Long id;

    @NotEmpty(message="Login can't be empty")
    @Size(min=2, message="Login should be more than 2 characters")
    private String login;

    @Size(min=6, message="Password should be more than 6 characters")
    @NotEmpty(message="Password can't be empty")
    private String password;
}
