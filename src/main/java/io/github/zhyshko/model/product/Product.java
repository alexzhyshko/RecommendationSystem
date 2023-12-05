package io.github.zhyshko.model.product;

import io.github.zhyshko.model.Base;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.util.List;

@Entity(name="Product")
@Table(name="products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Product extends Base {

    @OneToMany(cascade= CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private List<ProductAttribute> productAttributes;
    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="product_authors",
            joinColumns={@JoinColumn(name="product_id")},
            inverseJoinColumns={@JoinColumn(name="author_id")})
    private List<Author> authors;
    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="product_publishers",
            joinColumns={@JoinColumn(name="product_id")},
            inverseJoinColumns={@JoinColumn(name="publisher_id")})
    private List<Publisher> publishers;
    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="product_categories",
            joinColumns={@JoinColumn(name="product_id")},
            inverseJoinColumns={@JoinColumn(name="category_id")})
    private List<Category> categories;


}
