package ba.newsportal.model;

import ba.newsportal.model.enums.Gender;
import ba.newsportal.model.enums.Role;
import ba.newsportal.validation.annotation.ValueOfEnumGender;
import ba.newsportal.validation.annotation.ValueOfEnumRole;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "first_name", nullable = false)
    @Size(max = 15, message = "First name can't be longer than fifteen characters")
    @NotBlank(message = "First name can't be blank")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Size(max = 15, message = "Last name can't be longer than fifteen characters")
    @NotBlank(message = "Last name can't be blank")
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    @NotBlank(message = "Email can't be blank")
    @Email(message = "Invalid email format")
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "Password can't be blank")
    @Size(min = 8, message = "Password must contain at least eight characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",
            message = "Password must contain at least one lowercase, one uppercase and one digit")
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Gender can't be null")
    @ValueOfEnumGender(enumClass = Gender.class)
    private String gender;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role can't be null")
    @ValueOfEnumRole(enumClass = Role.class)
    private String role;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
}
