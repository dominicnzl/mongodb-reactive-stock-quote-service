package nl.ktmc.mongodbreactivestockquoteservice.repositories;

import nl.ktmc.mongodbreactivestockquoteservice.domain.Quote;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface QuoteRepository extends ReactiveMongoRepository<Quote, String> {
}
