package io.github.zhyshko.dto.cache;

import io.github.zhyshko.dto.product.ProductData;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
public class CacheValue {

    private Map<ProductData, Long> result;
    private LocalDateTime cachingTime;

}
