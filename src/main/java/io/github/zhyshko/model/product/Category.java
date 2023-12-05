package io.github.zhyshko.model.product;

import io.github.zhyshko.model.Base;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity(name = "Category")
@Table(name = "categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Category extends Base {

    @OneToMany(cascade= CascadeType.ALL)
    private List<Category> subcategories;

}
