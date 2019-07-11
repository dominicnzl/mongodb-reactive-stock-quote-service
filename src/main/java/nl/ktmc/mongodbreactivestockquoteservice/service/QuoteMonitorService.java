package nl.ktmc.mongodbreactivestockquoteservice.service;

import nl.ktmc.mongodbreactivestockquoteservice.client.StockQuoteClient;
import nl.ktmc.mongodbreactivestockquoteservice.domain.Quote;
import nl.ktmc.mongodbreactivestockquoteservice.repositories.QuoteRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class QuoteMonitorService implements ApplicationListener<ContextRefreshedEvent> {

    private final StockQuoteClient stockQuoteClient;
    private final QuoteRepository quoteRepository;

    public QuoteMonitorService(StockQuoteClient stockQuoteClient, QuoteRepository quoteRepository) {
        this.stockQuoteClient = stockQuoteClient;
        this.quoteRepository = quoteRepository;
    }

    //wordt gedaan wanneer de context gereed is
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        stockQuoteClient.getQuoteStream()
                .log("quote-monitor-service")
                .subscribe(quote -> {
                    Mono<Quote> savedQuote = quoteRepository.save(quote);
                    savedQuote.subscribe(q -> System.out.println("Quote saved, id: " + q.getId()));
                });
    }
}
