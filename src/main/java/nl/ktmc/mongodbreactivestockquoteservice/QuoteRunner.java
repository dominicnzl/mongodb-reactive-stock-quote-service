package nl.ktmc.mongodbreactivestockquoteservice;

import nl.ktmc.mongodbreactivestockquoteservice.client.StockQuoteClient;
import nl.ktmc.mongodbreactivestockquoteservice.domain.Quote;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class QuoteRunner implements CommandLineRunner {

    private final StockQuoteClient stockQuoteClient;

    public QuoteRunner(StockQuoteClient stockQuoteClient) {
        this.stockQuoteClient = stockQuoteClient;
    }

    @Override
    public void run(String... args) throws Exception {
        Flux<Quote> quoteFlux = stockQuoteClient.getQuoteStream();
        quoteFlux.subscribe(System.out::println);
    }
}
