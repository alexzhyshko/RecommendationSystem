package io.github.zhyshko.model.order;

import io.github.zhyshko.model.Base;
import io.github.zhyshko.model.product.Product;
import io.github.zhyshko.model.review.ReviewEntry;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity(name="OrderEntry")
@Table(name="order_entries")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class OrderEntry extends Base {

    private LocalDateTime timeCreated;
    @OneToOne(cascade = CascadeType.ALL)
    private ReviewEntry reviewEntry;
    @ManyToOne(cascade=CascadeType.ALL)
    private Product product;

}
