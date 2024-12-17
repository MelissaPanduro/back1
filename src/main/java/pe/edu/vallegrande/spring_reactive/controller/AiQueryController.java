package pe.edu.vallegrande.spring_reactive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pe.edu.vallegrande.spring_reactive.modal.AiQuery;
import pe.edu.vallegrande.spring_reactive.modal.QueryRequest;
import pe.edu.vallegrande.spring_reactive.service.AiQueryService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/queries")
@CrossOrigin(origins = "*")
public class AiQueryController {

    @Autowired
    private AiQueryService aiQueryService;

    // Listar todas las consultas activas
    @GetMapping("/active")
    public Flux<AiQuery> getAllActiveQueries() {
        return aiQueryService.getAllActiveQueriesOrderedById();
    }

    // Listar todas las consultas inactivas
    @GetMapping("/inactive")
    public Flux<AiQuery> getAllInactiveQueries() {
        return aiQueryService.getAllInactiveQueriesOrderedById();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AiQuery> createQuery(@RequestBody QueryRequest queryRequest) {
        return aiQueryService.createQuery(queryRequest.getQuery())
            .switchIfEmpty(Mono.error(new IllegalArgumentException("Query creation failed")));
    }

    @PutMapping("/{id}")
    public Mono<AiQuery> updateQuery(@PathVariable Long id, @RequestBody QueryRequest queryRequest) {
        return aiQueryService.updateQuery(id, queryRequest.getQuery())
            .switchIfEmpty(Mono.error(new IllegalArgumentException("Query update failed")));
    }

    @PutMapping("/delete/{id}")
    public Mono<ResponseEntity<Object>> deleteQuery(@PathVariable Long id) {
        return aiQueryService.deleteQueryById(id) // Cambia este método en tu servicio para manejar el eliminado lógico
            .then(Mono.just(ResponseEntity.noContent().build()))
            .onErrorResume(e -> {
                if (e instanceof IllegalArgumentException) {
                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()));
                }
                return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred"));
            });
    }
    
    @PutMapping("/active/{id}")
    public Mono<ResponseEntity<Object>> activeQuery(@PathVariable Long id) {
        return aiQueryService.activeQueryById(id) // Cambia este método en tu servicio para manejar el eliminado lógico
            .then(Mono.just(ResponseEntity.noContent().build()))
            .onErrorResume(e -> {
                if (e instanceof IllegalArgumentException) {
                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()));
                }
                return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred"));
            });
    }
}