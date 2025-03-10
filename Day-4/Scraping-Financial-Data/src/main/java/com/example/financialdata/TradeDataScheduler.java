package com.example.financialdata;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TradeDataScheduler {
    private final NseScraper scraper;

    public TradeDataScheduler(NseScraper scraper) {
        this.scraper = scraper;
    }

    @Scheduled(fixedRate = 3600000)
    public void fetchTradeData() {
        System.out.println("Fetching trade data...");
        scraper.scrapeData();
    }
}
