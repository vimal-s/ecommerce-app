package com.example.demo.service;

import com.example.demo.ItemNotFoundException;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
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
            logger.info("ItemNotFoundException occurred");
            throw new ItemNotFoundException(itemName);
        }
        return items;
    }

    public Item getItem(Long itemId) {
        return itemRepository
                .findById(itemId)
                .orElseThrow(() -> {
                    logger.info("ItemNotFoundException occurred");
                    return new ItemNotFoundException(itemId);
                });
    }
}
