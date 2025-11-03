package com.online.shop.model;

public class Reviews {
    private int reviewId;
    private int productId;
    private int userId;
    private int rating;
    private String comment;

    public Reviews(int reviewId, int productId, int userId, int rating, String comment) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
    }

    public Reviews(){}

    public int getReviewId() {
        return reviewId;
    }

    public int getProductId() {
        return productId;
    }

    public int getUserId() {
        return userId;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
