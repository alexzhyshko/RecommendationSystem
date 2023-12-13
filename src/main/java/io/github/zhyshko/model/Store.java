package io.github.zhyshko.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity(name="Store")
@Table(name="stores")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Store {


     @Id
     @EqualsAndHashCode.Include
     private UUID id;

}
