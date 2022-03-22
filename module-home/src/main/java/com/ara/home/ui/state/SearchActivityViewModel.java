package com.ara.home.ui.state;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.ara.home.domain.request.SearchRequest;

/**
 * Created by XieXin on 2022/3/10.
 */
public class SearchActivityViewModel extends ViewModel {
    public SearchRequest searchRequest = new SearchRequest();
}
