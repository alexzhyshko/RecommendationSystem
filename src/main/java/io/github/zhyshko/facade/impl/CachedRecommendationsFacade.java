package io.github.zhyshko.facade.impl;

import io.github.zhyshko.dto.cache.CacheKey;
import io.github.zhyshko.dto.cache.CacheValue;
import io.github.zhyshko.dto.product.ProductData;
import io.github.zhyshko.exception.CacheValueExpiredException;
import io.github.zhyshko.facade.RecommendationsFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;

@Component
public class CachedRecommendationsFacade implements RecommendationsFacade {

    private static final Long CACHE_TTL = 0L;
    private Map<CacheKey, CacheValue> cacheMap = new HashMap<>();

    @Autowired
    private DefaultRecommendationsFacade recommendationsFacade;

    @Override
    public Map<ProductData, Long> getForUser(UUID storeId, UUID userExternalId) {
        return executeWithinCaching(storeId, userExternalId, (u1, u2) -> recommendationsFacade.getForUser(u1, u2));
    }

    @Override
    public Map<ProductData, Long> getGeneral(UUID storeId) {
        return executeWithinCaching(storeId, null, (u1, u2) -> recommendationsFacade.getGeneral(u1));
    }

    private Map<ProductData, Long> executeWithinCaching(UUID storeId, UUID userExternalId,
                                                        BiFunction<UUID, UUID, Map<ProductData, Long>> originalFacadeCall) {
        try {
            return retrieveFromCache(storeId, userExternalId);
        } catch(CacheValueExpiredException e) {
            Map<ProductData, Long> calculatedResult = originalFacadeCall.apply(storeId, userExternalId);
            storeToCache(storeId, userExternalId, calculatedResult);
            return calculatedResult;
        }
    }

    private Map<ProductData, Long> retrieveFromCache(UUID storeId, UUID userExternalId){
        CacheValue cachedValue = cacheMap.get(CacheKey.builder()
                .storeId(storeId)
                .userExternalId(userExternalId)
                .build());
        if(cachedValue == null
                || cachedValue.getCachingTime().plusSeconds(CACHE_TTL).isBefore(LocalDateTime.now())) {
            throw new CacheValueExpiredException(
                    String.format("No cache or expired for store: %s and user: %s", storeId, userExternalId));
        }
        return cachedValue.getResult();
    }

    private void storeToCache(UUID storeId, UUID userExternalId,
                              Map<ProductData, Long> calculatedResult) {
        CacheKey cacheKey = CacheKey.builder()
                .storeId(storeId)
                .userExternalId(userExternalId)
                .build();
        CacheValue cachedValue = CacheValue.builder()
                .result(calculatedResult)
                .cachingTime(LocalDateTime.now())
                .build();
        cacheMap.put(cacheKey, cachedValue);
    }

}
