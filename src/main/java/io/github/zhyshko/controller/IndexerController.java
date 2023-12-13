package io.github.zhyshko.controller;

import io.github.zhyshko.DatabasePopulator;
import io.github.zhyshko.dto.order.OrderData;
import io.github.zhyshko.facade.IndexerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/indexer")
public class IndexerController {

    @Autowired
    private IndexerFacade indexerFacade;
    @Autowired
    private DatabasePopulator databasePopulator;

    public IndexerController(IndexerFacade indexerFacade) {
        this.indexerFacade = indexerFacade;
    }

    @PostMapping("/order")
    public HttpStatus orderIndex(@RequestBody OrderData orderData) {
        indexerFacade.indexOrder(orderData);
        return HttpStatus.ACCEPTED;
    }

    @PostMapping("/review")
    public HttpStatus reviewIndex(@RequestBody OrderData orderData) {
        indexerFacade.indexReview(orderData);
        return HttpStatus.ACCEPTED;
    }

    @GetMapping("/init")
    public void init() {
        databasePopulator.populateDatabase(indexerFacade);
    }

}
