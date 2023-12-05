package io.github.zhyshko.model.product;

import io.github.zhyshko.model.Base;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity(name="ProductAttribute")
@Table(name="product_attributes")
@Data
@AllArgsConstructor
@SuperBuilder
public class ProductAttribute extends Base {

}
