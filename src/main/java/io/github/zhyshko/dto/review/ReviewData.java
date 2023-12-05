package io.github.zhyshko.dto.review;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.UUID;

import io.github.zhyshko.dto.StoreData;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
public class ReviewData {

    private Long id;
    private UUID externalId;
    private StoreData store;

    private List<ReviewEntryData> reviewEntries;

}
