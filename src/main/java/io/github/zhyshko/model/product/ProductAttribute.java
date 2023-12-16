package io.github.zhyshko.model.product;

import io.github.zhyshko.model.Base;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity(name="ProductAttribute")
@Table(name="product_attributes")
@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
public class ProductAttribute extends Base {

}
