package com.itheamc.msnackshub.models;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

public class Review {
    private String _user_id;
    private Double rating;
    private String review;

    // Constructor
    public Review() {
    }

    // Constructor with parameters
    public Review(String _user_id, Double rating, String review) {
        this._user_id = _user_id;
        this.rating = rating;
        this.review = review;
    }

    // Getters and Setters
    public String get_user_id() {
        return _user_id;
    }

    public void set_user_id(String _user_id) {
        this._user_id = _user_id;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    // Overriding toString() method for debugging
    @Override
    public String toString() {
        return "Review{" +
                "_user_id='" + _user_id + '\'' +
                ", rating=" + rating +
                ", review='" + review + '\'' +
                '}';
    }

    // Overriding equals() method


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review1 = (Review) o;
        return get_user_id().equals(review1.get_user_id()) &&
                getRating().equals(review1.getRating()) &&
                getReview().equals(review1.getReview());
    }


    // Creating DiffUtil.ItemCallBack object for Review
    public static DiffUtil.ItemCallback<Review> reviewItemCallback = new DiffUtil.ItemCallback<Review>() {
        @Override
        public boolean areItemsTheSame(@NonNull Review oldItem, @NonNull Review newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Review oldItem, @NonNull Review newItem) {
            return oldItem.get_user_id().equals(newItem.get_user_id()) &&
                    oldItem.getRating().equals(newItem.getRating()) &&
                    oldItem.getReview().equals(newItem.getReview());
        }
    };
}
