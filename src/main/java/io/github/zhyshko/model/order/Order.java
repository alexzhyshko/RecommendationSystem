package io.github.zhyshko.model.order;

import io.github.zhyshko.model.Base;
import io.github.zhyshko.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "Order")
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Order extends Base {

    @ManyToOne(cascade = CascadeType.ALL)
    private User owner;
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private List<OrderEntry> orderEntries;
    private LocalDateTime createdTime;

}
