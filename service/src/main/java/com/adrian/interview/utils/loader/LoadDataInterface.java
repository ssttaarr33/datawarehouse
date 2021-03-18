package com.adrian.interview.utils.loader;

import com.adrian.interview.repository.CampaignRepository;

public interface LoadDataInterface {
    boolean loadData(String url, CampaignRepository campaignRepository);
}
