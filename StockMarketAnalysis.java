import java.util.*;

class Stock {
    String symbol;
    double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }
}

class StockMarket {
    HashMap<String, Double> prices;
    PriorityQueue<Stock> gainers;
    PriorityQueue<Stock> losers;
    TreeMap<String, Stock> stocks;

    public StockMarket() {
        prices = new HashMap<>();
        gainers = new PriorityQueue<>((s1, s2) -> Double.compare(s2.price - s1.price, 0.0));
        losers = new PriorityQueue<>((s1, s2) -> Double.compare(s1.price - s2.price, 0.0));
        stocks = new TreeMap<>();
    }

    public void updatePrice(String symbol, double price) {
        double oldPrice = prices.getOrDefault(symbol, -1.0);
        prices.put(symbol, price);

        if (oldPrice != -1.0) {
            Stock stock = stocks.get(symbol);
            gainers.remove(stock);
            losers.remove(stock);

            stock.price = price;
            gainers.add(stock);
            losers.add(stock);
        } else {
            Stock stock = new Stock(symbol, price);
            stocks.put(symbol, stock);
            gainers.add(stock);
            losers.add(stock);
        }
    }

    public void findTopGainersLosers(int n) {
        System.out.println("Top " + n + " Gainers:");
        for (int i = 0; i < n; i++) {
            Stock stock = gainers.poll();
            System.out.println(stock.symbol + ": $" + stock.price);
        }

        System.out.println("\nTop " + n + " Losers:");
        for (int i = 0; i < n; i++) {
            Stock stock = losers.poll();
            System.out.println(stock.symbol + ": RS." + stock.price);
        }
    }
}

public class StockMarketAnalysis {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StockMarket market = new StockMarket();

        while (true) {
            System.out.println("\n1. Update stock price");
            System.out.println("2. Find top gainers/losers");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter stock symbol: ");
                    String symbol = scanner.next();
                    System.out.print("Enter stock price: ");
                    double price = scanner.nextDouble();
                    market.updatePrice(symbol, price);
                    System.out.println("Stock price updated successfully!");
                    break;

                case 2:
                    System.out.print("Enter the number of top gainers/losers to display: ");
                    int n = scanner.nextInt();
                    market.findTopGainersLosers(n);
                    break;

                case 3:
                    System.out.println("Exiting program...");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
                    break;
            }
        }
    }
}
