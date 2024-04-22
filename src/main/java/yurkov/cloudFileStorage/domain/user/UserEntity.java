package yurkov.cloudFileStorage.domain.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "user_entity")
@NoArgsConstructor
public class UserEntity {

     @Id
     @Column(name = "id")
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     int id;

     @NotEmpty(message = "Username must not be empty")
     @Size(min=2, max=100, message ="Username length must be between 2 and 100 characters")
     String username;

     @NotEmpty(message = "Password must not be empty")
     String password;


     String role;
}
