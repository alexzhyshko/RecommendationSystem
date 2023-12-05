package io.github.zhyshko;

import io.github.zhyshko.dto.StoreData;
import io.github.zhyshko.dto.order.OrderData;
import io.github.zhyshko.dto.product.*;
import io.github.zhyshko.dto.review.ReviewData;
import io.github.zhyshko.dto.review.ReviewEntryData;
import io.github.zhyshko.dto.user.UserData;
import io.github.zhyshko.facade.IndexerFacade;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class DatabasePopulator{
    private static final UUID STORE_ID = UUID.fromString("becc16ed-557b-4fee-934e-0aff693b8992");

    private static final UUID PRODUCT_1_ID = UUID.fromString("31224ee2-3d49-4a6a-ba73-624ef0eab9b4");
    private static final UUID PRODUCT_2_ID = UUID.fromString("d8678259-9d59-44ce-b96d-2a5c024cab32");
    private static final UUID PRODUCT_3_ID = UUID.fromString("afb19e29-bca8-4b44-b6b7-c4928a249980");

    private static final UUID USER_1_ID = UUID.fromString("d8678259-9d59-44ce-b96d-2a5c024cab32");
    private static final UUID USER_2_ID = UUID.fromString("afb19e29-bca8-4b44-b6b7-c4928a249980");

    public void populateDatabase(IndexerFacade indexerFacade) {

        OrderData orderData1 = createOrder(createReviewData1(), createUserData1());
        OrderData orderData2 = createOrder(createReviewData2(), createUserData1());
        OrderData orderData3 = createOrder(createReviewData3(), createUserData2());

        indexerFacade.indexOrder(orderData1);
        indexerFacade.indexOrder(orderData2);
        indexerFacade.indexOrder(orderData3);
    }

    private OrderData createOrder(ReviewData reviewData, UserData userData) {
        return OrderData.builder()
                .externalId(UUID.randomUUID())
                .owner(userData)
                .review(reviewData)
                .createdTime(LocalDateTime.now())
                .store(createStoreData())
                .build();
    }

    private UserData createUserData1() {
        return UserData.builder()
                .externalId(USER_1_ID)
                .store(createStoreData())
                .build();
    }

    private UserData createUserData2() {
        return UserData.builder()
                .externalId(USER_2_ID)
                .store(createStoreData())
                .build();
    }

    private ReviewData createReviewData1() {
        return ReviewData.builder()
                .externalId(UUID.randomUUID())
                .reviewEntries(List.of(createReviewEntry1(1), createReviewEntry2(1)))
                .store(createStoreData())
                .build();
    }

    private ReviewData createReviewData2() {
        return ReviewData.builder()
                .externalId(UUID.randomUUID())
                .reviewEntries(List.of(createReviewEntry3(-1), createReviewEntry1(1)))
                .store(createStoreData())
                .build();
    }

    private ReviewData createReviewData3() {
        return ReviewData.builder()
                .externalId(UUID.randomUUID())
                .reviewEntries(List.of(createReviewEntry1(1)))
                .store(createStoreData())
                .build();
    }

    private ReviewEntryData createReviewEntry1(int mark) {
        return ReviewEntryData.builder()
                .externalId(UUID.randomUUID())
                .timeCreated(LocalDateTime.now())
                .mark(mark)
                .store(createStoreData())
                .product(createProduct1())
                .build();
    }

    private ReviewEntryData createReviewEntry2(int mark) {
        return ReviewEntryData.builder()
                .externalId(UUID.randomUUID())
                .timeCreated(LocalDateTime.now())
                .mark(mark)
                .store(createStoreData())
                .product(createProduct2())
                .build();
    }

    private ReviewEntryData createReviewEntry3(int mark) {
        return ReviewEntryData.builder()
                .externalId(UUID.randomUUID())
                .timeCreated(LocalDateTime.now())
                .mark(mark)
                .store(createStoreData())
                .product(createProduct3())
                .build();
    }

    private ProductData createProduct1() {
        return ProductData.builder()
                .externalId(PRODUCT_1_ID)
                .productAttributes(List.of(createProductAttributeData()))
                .authors(List.of(createAuthorData()))
                .publishers(List.of(createPublisherData()))
                .categories(List.of(createCategoryData()))
                .store(createStoreData())
                .build();
    }

    private ProductData createProduct2() {
        return ProductData.builder()
                .externalId(PRODUCT_2_ID)
                .productAttributes(List.of(createProductAttributeData()))
                .authors(List.of(createAuthorData()))
                .publishers(List.of(createPublisherData()))
                .categories(List.of(createCategoryData()))
                .store(createStoreData())
                .build();
    }

    private ProductData createProduct3() {
        return ProductData.builder()
                .externalId(PRODUCT_3_ID)
                .productAttributes(List.of(createProductAttributeData()))
                .authors(List.of(createAuthorData()))
                .publishers(List.of(createPublisherData()))
                .categories(List.of(createCategoryData()))
                .store(createStoreData())
                .build();
    }

    private StoreData createStoreData() {
        return StoreData.builder()
                .externalId(STORE_ID)
                .build();
    }

    private CategoryData createCategoryData() {
        CategoryData subCategoryData = CategoryData.builder()
                .externalId(UUID.randomUUID())
                .store(createStoreData())
                .build();

        CategoryData categoryData = CategoryData.builder()
                .externalId(UUID.randomUUID())
                .subcategories(List.of(subCategoryData))
                .store(createStoreData())
                .build();
        return categoryData;
    }

    private PublisherData createPublisherData() {
        return PublisherData.builder()
                .externalId(UUID.randomUUID())
                .store(createStoreData())
                .build();
    }

    private AuthorData createAuthorData() {
        return AuthorData.builder()
                .externalId(UUID.randomUUID())
                .store(createStoreData())
                .build();
    }

    private ProductAttributeData createProductAttributeData() {
        return ProductAttributeData.builder()
                .externalId(UUID.randomUUID())
                .store(createStoreData())
                .build();
    }



}
