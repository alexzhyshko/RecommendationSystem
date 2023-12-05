package io.github.zhyshko.wsdto;

import io.github.zhyshko.dto.product.ProductData;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProductWsDtoMapper {

    public ProductWsDto toWsDto(Map.Entry<Long, ProductData> productDataScoreInfo){
        return ProductWsDto.builder()
                .score(productDataScoreInfo.getKey())
                .externalId(productDataScoreInfo.getValue().getExternalId())
                .build();
    }

}
