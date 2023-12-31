package io.github.zhyshko.model.user;

import io.github.zhyshko.model.Base;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity(name="User")
@Table(name="users")
@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
public class User extends Base {



}
