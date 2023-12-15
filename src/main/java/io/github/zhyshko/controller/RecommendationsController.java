package io.github.zhyshko.controller;

import io.github.zhyshko.dto.product.ProductData;
import io.github.zhyshko.facade.RecommendationsFacade;
import io.github.zhyshko.wsdto.ProductWsDto;
import io.github.zhyshko.wsdto.ProductWsDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/recommendations")
public class RecommendationsController {

    @Autowired
    @Qualifier("cachedRecommendationsFacade")
    private RecommendationsFacade recommendationsFacade;
    @Autowired
    private ProductWsDtoMapper productWsDtoMapper;

    @GetMapping
    public List<ProductWsDto> getRecommendations(@RequestParam UUID storeId,
                                                        @RequestParam(required = false) UUID userId) {
        Map<ProductData, Long> result;
        if (userId == null) {
            result = recommendationsFacade.getGeneral(storeId);
        } else {
            result = recommendationsFacade.getForUser(storeId, userId);
        }
        return result
                .entrySet().stream()
                .map(productWsDtoMapper::toWsDto)
                .toList();
    }
}
