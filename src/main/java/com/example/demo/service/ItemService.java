package com.example.demo.service;

import com.example.demo.ItemNotFoundException;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    private ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public List<Item> getItems(String itemName) {
        List<Item> items = itemRepository.findByName(itemName);
        if (items.isEmpty()) {
            throw new ItemNotFoundException(itemName);
        }
        return items;
    }

    public Item getItem(Long itemId) {
        return itemRepository
                .findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));
    }
}
