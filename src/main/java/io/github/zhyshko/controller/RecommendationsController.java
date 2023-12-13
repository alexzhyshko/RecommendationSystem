package io.github.zhyshko.controller;

import io.github.zhyshko.facade.RecommendationsFacade;
import io.github.zhyshko.wsdto.ProductWsDto;
import io.github.zhyshko.wsdto.ProductWsDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/recommendations")
public class RecommendationsController {

    @Autowired
    private RecommendationsFacade recommendationsFacade;
    @Autowired
    private ProductWsDtoMapper productWsDtoMapper;

    @GetMapping
    public List<ProductWsDto> getRecommendationsForUser(@RequestParam UUID userId) {
        return recommendationsFacade.getForUser(userId)
                .entrySet().stream()
                .map(productWsDtoMapper::toWsDto)
                .toList();
    }

}
