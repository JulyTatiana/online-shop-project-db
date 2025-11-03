package com.online.shop;

import com.online.shop.dao.mybatisiml.ProductMyBatisDAO;
import com.online.shop.dao.mybatisiml.UserMyBatisDAO;
import com.online.shop.model.*;
import com.online.shop.service.AddressesJAXBService;
import com.online.shop.service.Implementation.*;
import com.online.shop.service.ReviewsServiceJSON;
import com.online.shop.service.ShipmentServiceJSON;
import com.online.shop.util.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        UserServiceImplementation userService = new UserServiceImplementation();
        ProductServiceImplementation productService = new ProductServiceImplementation();
        InventoryServiceImplementation inventoryService = new InventoryServiceImplementation();
        OrderServiceImplementation orderService = new OrderServiceImplementation();
        PaymentServiceImplementation paymentService = new PaymentServiceImplementation();

        System.out.println("=== ONLINE SHOP SERVICE TEST ===");

        DbToXml xmlExporter = new DbToXml();
        DbToJson jsonExporter = new DbToJson();

        xmlExporter.exportAll();
        jsonExporter.exportAll();

        AddressesJAXBService jaxbService = new AddressesJAXBService();
        AddressesList list = jaxbService.loadAddresses();

        System.out.println("=== ADDRESSES (Loaded via JAXB) ===");
        if (list != null && list.getAddresses() != null) {
            for (Addresses a : list.getAddresses()) {
                System.out.println(a);
            }
        }

        ReviewsServiceJSON reviewService = new ReviewsServiceJSON();
        ShipmentServiceJSON shipmentService = new ShipmentServiceJSON();

        List<Reviews> reviews = reviewService.loadReviews();
        List<Shipment> shipments = shipmentService.loadShipments();

        System.out.println("=== Reviews from JSON ===");
        reviews.forEach(System.out::println);

        System.out.println("\n=== Shipments from JSON ===");
        shipments.forEach(System.out::println);

        ProductMyBatisDAO productDao = new ProductMyBatisDAO();
        UserMyBatisDAO userDao = new UserMyBatisDAO();

        Product p = productDao.findById(1);
        System.out.println("Product: " + p.getName() + " - $" + p.getPrice());

        User u = userDao.findById(1);
        System.out.println("User: " + u.getUsername() + " - " + u.getEmail());

        XmlAddressSaxParser saxParser = new XmlAddressSaxParser();
        saxParser.parseXml("src/main/java/com/online/shop/util/format/xml/addresses.xml");

        System.out.println("All export and parsing operations completed.");

        Exporter exporter = ExporterFactory.getExporter("xml");
        exporter.exportData();

        PaymentStrategy payment = new PayPal();
        payment.pay(100.0);

        User user = new User.UserBuilder()
                .id(1)
                .name("Joe")
                .email("joe@yahoo.com")
                .build();

        System.out.println("User: " + user.getUsername());

        Product newProduct = new Product(0, "Laptop",  "Dell", 12000);
        boolean productAdded = productService.addProduct(newProduct);
        System.out.println("Product added: " + productAdded);

        Inventory newInventory = new Inventory(0, newProduct.getProductId(), 10);
        boolean inventoryAdded = inventoryService.addInventory(newInventory);
        System.out.println("Inventory added: " + inventoryAdded);


        Map<Integer, Integer> orderItems = new HashMap<>();
        orderItems.put(newProduct.getProductId(), 2);
        Order newOrder = orderService.createOrder(user.getUserId(), orderItems);
        System.out.println("Order created: " + (newOrder != null));


        boolean paymentProcessed = paymentService.processPayment(newOrder.getOrderId(), newProduct.getPrice() * 2);
        System.out.println("Payment processed: " + paymentProcessed);


        User fetchedUser = userService.getUserById(user.getUserId());
        Product fetchedProduct = productService.getProductById(newProduct.getProductId());
        Inventory fetchedInventory = inventoryService.getInventoryByProductId(newProduct.getProductId());

        System.out.println("Fetched User: " + fetchedUser);
        System.out.println("Fetched Product: " + fetchedProduct);
        System.out.println("Fetched Inventory: " + fetchedInventory);

        System.out.println("=== TEST COMPLETE ===");
    }
}

