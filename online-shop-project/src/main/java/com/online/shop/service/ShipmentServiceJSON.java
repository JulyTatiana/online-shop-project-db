package com.online.shop.service;

import com.online.shop.model.Shipment;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ShipmentServiceJSON {

    private static final String FILE_PATH = "src/main/resources/json/shipments.json";

    public List<Shipment> loadShipments() {
        List<Shipment> shipments = new ArrayList<>();
        try {
            String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
            JSONArray array = new JSONArray(content);

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Shipment s = new Shipment(
                        obj.getInt("shipmentId"),
                        obj.getInt("orderId"),
                        obj.getString("trackingNumber"),
                        obj.getString("carrier"),
                        obj.getString("status")
                );
                shipments.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return shipments;
    }
}
