package io.github.zhyshko.model.product;

import io.github.zhyshko.model.Base;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity(name = "Category")
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Category extends Base {

    @OneToMany(cascade= CascadeType.ALL)
    private List<Category> subcategories;

}
