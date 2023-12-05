package io.github.zhyshko.model.user;

import io.github.zhyshko.model.Base;
import io.github.zhyshko.model.order.Order;
import io.github.zhyshko.model.review.Review;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity(name="User")
@Table(name="users")
@Data
@AllArgsConstructor
@SuperBuilder
public class User extends Base {



}
