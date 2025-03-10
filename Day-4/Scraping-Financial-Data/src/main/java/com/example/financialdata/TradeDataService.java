package com.example.financialdata;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TradeDataService {
    private final TradeDataRepository repository;

    public TradeDataService(TradeDataRepository repository) {
        this.repository = repository;
    }

    public void saveTradeData(TradeData data) {
        repository.save(data);
    }

    public List<TradeData> getAllTradeData() {
        return repository.findAll();
    }
}
