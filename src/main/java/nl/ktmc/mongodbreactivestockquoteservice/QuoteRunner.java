package nl.ktmc.mongodbreactivestockquoteservice;

import nl.ktmc.mongodbreactivestockquoteservice.client.StockQuoteClient;
import nl.ktmc.mongodbreactivestockquoteservice.domain.Quote;
import nl.ktmc.mongodbreactivestockquoteservice.repositories.QuoteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.util.concurrent.CountDownLatch;

@Component
public class QuoteRunner implements CommandLineRunner {

    private final StockQuoteClient stockQuoteClient;
    private final QuoteRepository quoteRepository;

    public QuoteRunner(StockQuoteClient stockQuoteClient, QuoteRepository quoteRepository) {
        this.stockQuoteClient = stockQuoteClient;
        this.quoteRepository = quoteRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Flux<Quote> quoteFlux = quoteRepository.findWithTailableCursorBy();
        CountDownLatch latch = new CountDownLatch(1);
        Disposable disposable = quoteFlux.subscribe(q -> System.out.println("########## id: " + q.getId()),
                System.err::println,
                latch::countDown);
        latch.await();
        disposable.dispose();
    }
}
