package io.github.zhyshko.model.product;

import io.github.zhyshko.model.Base;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity(name="Author")
@Table(name="authors")
@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
public class Author extends Base {


}
