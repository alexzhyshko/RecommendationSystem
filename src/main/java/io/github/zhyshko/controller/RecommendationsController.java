package io.github.zhyshko.controller;

import io.github.zhyshko.dto.StoreData;
import io.github.zhyshko.dto.order.OrderData;
import io.github.zhyshko.dto.product.*;
import io.github.zhyshko.dto.review.ReviewData;
import io.github.zhyshko.dto.review.ReviewEntryData;
import io.github.zhyshko.dto.user.UserData;
import io.github.zhyshko.facade.RecommendationsFacade;
import io.github.zhyshko.wsdto.ProductWsDto;
import io.github.zhyshko.wsdto.ProductWsDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    public List<ProductWsDto> r(@RequestParam UUID userId) {
        return recommendationsFacade.getForUser(userId).entrySet().stream().map(productWsDtoMapper::toWsDto).toList();
    }

}
