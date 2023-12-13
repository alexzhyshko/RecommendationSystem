package io.github.zhyshko.wsdto;

import io.github.zhyshko.dto.product.ProductData;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProductWsDtoMapper {

    public ProductWsDto toWsDto(Map.Entry<ProductData, Long> productDataScoreInfo){
        return ProductWsDto.builder()
                .score(productDataScoreInfo.getValue())
                .externalId(productDataScoreInfo.getKey().getExternalId())
                .build();
    }

}
