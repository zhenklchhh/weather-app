package logic.models;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private int userID;

    @NotEmpty
    private double latitude;

    @NotEmpty
    private double longitude;
}
