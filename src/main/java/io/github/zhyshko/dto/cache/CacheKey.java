package io.github.zhyshko.dto.cache;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@Builder
public class CacheKey {

    private UUID storeId;
    private UUID userExternalId;

}
