package com.example.financialdata;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDate;

@Component
public class NseScraper {

    private final TradeDataService tradeDataService;

    // Constructor injection for TradeDataService
    NseScraper(TradeDataService tradeDataService) {
        this.tradeDataService = tradeDataService;
    }

    public void scrapeData() {
        try {
            File inputFile = new File("C://Spring Boot//financialdata//financialdata//scraped_data.html");

            Document doc = Jsoup.parse(inputFile, "UTF-8");

            Element table = doc.select("table#equityStockTable").first();
            if (table == null) {
                System.out.println("No table found in the HTML document.");
                return;
            }

            Elements rows = table.select("tbody tr");

            LocalDate currentDate = LocalDate.now();

            for (Element row : rows) {
                Elements columns = row.select("td");

                if (columns.size() >= 6) {
                    String stockName = columns.get(0).text(); // Stock Name
                    String openPriceStr = columns.get(1).text(); // Open Price
                    String highPriceStr = columns.get(2).text(); // High Price
                    String lowPriceStr = columns.get(3).text(); // Low Price
                    String prevCloseStr = columns.get(4).text(); // Previous Close
                    String ltpStr = columns.get(5).text(); // Last Traded Price

                    // Convert the string prices to double, remove commas if any
                    double openPrice = parsePrice(openPriceStr);
                    double highPrice = parsePrice(highPriceStr);
                    double lowPrice = parsePrice(lowPriceStr);
                    double prevClose = parsePrice(prevCloseStr);
                    double ltp = parsePrice(ltpStr);

                    TradeData tradeData = new TradeData();
                    tradeData.setCompanyName(stockName);
                    tradeData.setTradeDate(currentDate);
                    tradeData.setOpenPrice(openPrice);
                    tradeData.setHighPrice(highPrice);
                    tradeData.setLowPrice(lowPrice);
                    tradeData.setClosePrice(prevClose); // Assuming 'closePrice' corresponds to 'prevClose'

                    tradeDataService.saveTradeData(tradeData);
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while scraping data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Helper method to safely parse price strings into double
    private double parsePrice(String priceStr) {
        try {
            return Double.parseDouble(priceStr.replace(",", "").trim());
        } catch (NumberFormatException e) {
            return 0.0; // Return 0.0 in case of invalid number
        }
    }
}
