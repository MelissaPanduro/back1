package pe.edu.vallegrande.spring_reactive.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.edu.vallegrande.spring_reactive.modal.AiQuery;
import reactor.core.publisher.Flux;

public interface AiQueryRepository extends ReactiveCrudRepository<AiQuery, Long> {

    // Listar todos los activos (active = 'A')
    @Query("SELECT * FROM ai_query WHERE active = 'A' ORDER BY id")
    Flux<AiQuery> findAllActiveByOrderById();

    // Listar todos los inactivos (active = 'I')
    @Query("SELECT * FROM ai_query WHERE active = 'I' ORDER BY id")
    Flux<AiQuery> findAllInactiveByOrderById();
}