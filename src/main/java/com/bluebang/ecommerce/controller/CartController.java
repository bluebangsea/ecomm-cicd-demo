package com.bluebang.ecommerce.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/cart")
public class CartController {

    Map<Integer, List<Item>> cart = new HashMap<>();

    @GetMapping
    public Map<Integer, List<Item>> getCart() {
        return cart;
    }

    @PostMapping("/{userId}")
    public Item addCart(@PathVariable Integer userId,@RequestBody Item item) {
        cart.computeIfAbsent(userId, k -> new ArrayList<>())
                .add(item);
        return item;
    }
}
