package io.github.zhyshko.model;

import io.github.zhyshko.dto.StoreData;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(
        uniqueConstraints=
        @UniqueConstraint(columnNames={"external_id", "store_id"})
)
public class Base {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(unique=true, nullable = false)
    @EqualsAndHashCode.Include
    //@JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID externalId;
    @EqualsAndHashCode.Include
    @ManyToOne(cascade=CascadeType.ALL)
    private Store store;

}
