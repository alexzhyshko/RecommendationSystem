package io.github.zhyshko.dto.order;

import io.github.zhyshko.dto.user.UserData;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import io.github.zhyshko.dto.StoreData;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
public class OrderData {

    private Long id;
    private UUID externalId;
    private StoreData store;

    private UserData owner;
    private List<OrderEntryData> orderEntries;
    private LocalDateTime timeCreated;

}
