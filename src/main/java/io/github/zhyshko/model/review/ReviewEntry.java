package io.github.zhyshko.model.review;

import io.github.zhyshko.model.Base;
import io.github.zhyshko.model.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity(name="ReviewEntry")
@Table(name="review_entries")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ReviewEntry extends Base {

    private LocalDateTime timeCreated;
    private Integer mark;
    @ManyToOne(cascade=CascadeType.ALL)
    private Product product;

}
