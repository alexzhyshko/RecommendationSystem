package io.github.zhyshko.dto.user;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

import io.github.zhyshko.dto.StoreData;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
public class UserData {

    private Long id;
    private UUID externalId;
    private StoreData store;

}
