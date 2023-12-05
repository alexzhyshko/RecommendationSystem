package io.github.zhyshko.model.review;

import io.github.zhyshko.model.Base;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity(name="Review")
@Table(name="reviews")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Review extends Base {

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name = "review_id", referencedColumnName = "id")
    private List<ReviewEntry> reviewEntries;

}
