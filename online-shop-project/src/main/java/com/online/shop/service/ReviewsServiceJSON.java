package com.online.shop.service;

import com.online.shop.model.Reviews;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReviewsServiceJSON {

    private static final String FILE_PATH = "src/main/resources/json/reviews.json";

    public List<Reviews> loadReviews() {
        List<Reviews> reviews = new ArrayList<>();
        try {
            String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
            JSONArray array = new JSONArray(content);

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Reviews r = new Reviews(
                        obj.getInt("reviewId"),
                        obj.getInt("productId"),
                        obj.getInt("userId"),
                        obj.getInt("rating"),
                        obj.getString("comment")
                );
                reviews.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return reviews;
    }
}
