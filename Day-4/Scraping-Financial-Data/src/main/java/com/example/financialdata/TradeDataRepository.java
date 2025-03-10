package com.example.financialdata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeDataRepository extends JpaRepository<TradeData, Long> {
}
