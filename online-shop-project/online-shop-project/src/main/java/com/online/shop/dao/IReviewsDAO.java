package com.online.shop.dao;

import com.online.shop.model.Reviews;

public interface IReviewsDAO {
    Reviews findById(Integer reviewId);
    boolean insert(Reviews review);
    boolean update(Reviews review);
    boolean delete(Integer reviewId);
}
