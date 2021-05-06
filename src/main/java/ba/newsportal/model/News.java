package ba.newsportal.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "news")
@Data
@NoArgsConstructor
public class News {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @NotNull(message = "Id can't be null")
    private UUID id;

    @NotBlank(message = "Title can't be blank")
    @Size(max = 50, message = "Title can contain at most 50 characters")
    @Column(name = "title", nullable = false)
    private String title;

    @NotBlank(message = "Description can't be blank")
    @Column(name = "description", nullable = false)
    private String description;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @CreationTimestamp
    @Column(name = "published_at")
    private Instant publishedAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @NotNull(message = "User can't be null")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User owner;
}
