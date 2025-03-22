package com.example.SpringBootREST3.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderServiceUtil {

    public List<String> allowedItems = List.of("iPhone","Canon 400D","Tom and Jerry","Samsung LED","Nikon 400");
    public List<String> allowedItemTypes = List.of("phone","camera","book","tv");

    public Map<String, String> itemsMap;

    public OrderServiceUtil(){
        itemsMap = new HashMap<>();
        itemsMap.put("iPhone", "Phone");
        itemsMap.put("Canon 400D", "camera");
        itemsMap.put("Tom and Jerry", "book");
        itemsMap.put("Samsung LED", "tv");
        itemsMap.put("Nikon 400", "camera");
    }

}
