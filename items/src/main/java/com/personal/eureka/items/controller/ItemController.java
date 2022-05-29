package com.personal.eureka.items.controller;

import com.personal.eureka.commons.models.entity.Product;
import com.personal.eureka.items.models.Item;
import com.personal.eureka.items.service.IItemService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RefreshScope
@RestController
public class ItemController {

    @Autowired
    private IItemService itemService;

    @Value("${config.test.message}")
    private String testMessage;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

    @GetMapping(value = "/items")
    public List<Item> list(@RequestHeader(name = "token-request") String token, @RequestParam(name = "name") String name) {
        System.out.println("token:" + token);
        System.out.println("name:" + name);
        return itemService.findAll();
    }

    @GetMapping(value = "/item/{id}/amount/{amount}")
    public Item get(@PathVariable Long id, @PathVariable Integer amount) {
        return circuitBreakerFactory.create("items")
                .run(() -> itemService.findById(id, amount), e -> defaultItem(id, amount, e));
    }

    @CircuitBreaker(name = "items", fallbackMethod = "defaultItem")
    @GetMapping(value = "/item/cb/{id}/amount/{amount}")
    public Item getCB(@PathVariable Long id, @PathVariable Integer amount) {
        return itemService.findById(id, amount);
    }

    @TimeLimiter(name = "items", fallbackMethod = "defaultItemTl")
    @GetMapping(value = "/item/tl/{id}/amount/{amount}")
    public CompletableFuture<Item> getTL(@PathVariable Long id, @PathVariable Integer amount) {
        return CompletableFuture.supplyAsync(() -> itemService.findById(id, amount));
    }

    @TimeLimiter(name = "items") // fallbackMethod has a workaround, it should use only one when it's combined
    @CircuitBreaker(name = "items", fallbackMethod = "defaultItem")
    @GetMapping(value = "/item/cb/tl/{id}/amount/{amount}")
    public CompletableFuture<Item> getCbTL(@PathVariable Long id, @PathVariable Integer amount) {
        return CompletableFuture.supplyAsync(() -> itemService.findById(id, amount));
    }

    @GetMapping(value = "/test/message")
    public ResponseEntity<?> getTestMessage() {
        Map<String, String> json = new HashMap<>();
        json.put("text", testMessage);
        json.put("port", serverPort);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @PostMapping("/item")
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {
        return itemService.save(product);
    }

    @PutMapping("/item/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Product update(@RequestBody Product product, @PathVariable Long id) {
        return itemService.update(product, id);
    }

    @DeleteMapping("/item/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        itemService.delete(id);
    }

    private Item defaultItem(Long id, Integer amount, Throwable ex) {
        log.error(ex.getMessage());
        Product product = new Product();
        product.setProductId(id);
        product.setDescription("Default Product");
        product.setPrice(0.0);
        return new Item(product, amount);
    }

    private CompletableFuture<Item> defaultItemTl(Long id, Integer amount, Throwable ex) {
        log.error(ex.getMessage());
        Product product = new Product();
        product.setProductId(id);
        product.setDescription("Default Product 2");
        product.setPrice(0.0);
        return CompletableFuture.supplyAsync(() -> new Item(product, amount));
    }
}
