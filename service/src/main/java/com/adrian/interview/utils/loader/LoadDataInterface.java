package com.adrian.interview.utils.loader;

import com.adrian.interview.repository.CampaignRepository;

import java.net.MalformedURLException;

public interface LoadDataInterface {
    boolean loadData(String url, CampaignRepository campaignRepository) throws MalformedURLException;
}
