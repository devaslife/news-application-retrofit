package com.example.krnews;

import com.example.krnews.Models.NewsHeadlines;

import java.util.List;

public interface OnFetchDataListener<NewsApiResponse> {
    // i need to handle the data
    void onFetchData(List<NewsHeadlines> list,String message);
    void onError(String message);
}
