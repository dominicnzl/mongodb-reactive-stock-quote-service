package nl.ktmc.mongodbreactivestockquoteservice.client;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.ktmc.mongodbreactivestockquoteservice.domain.Quote;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Slf4j
@Setter
@Component
@ConfigurationProperties("nl")
public class StockQuoteClient {

    private String host;
    private String port;
    private String path;

    public Flux<Quote> getQuoteStream() {
        String url = "http://" + host + ":" + port;

        log.debug("Url set is: " + url);

        return WebClient.builder()
                .baseUrl(url)
                .build()
                .get()
                .uri(path)
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .retrieve()
                .bodyToFlux(Quote.class);
    }
}
