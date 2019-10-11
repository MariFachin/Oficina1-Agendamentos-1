package com.utfpr.uniagenda.web.rest;

import com.utfpr.uniagenda.domain.AgendaReservaSala;
import com.utfpr.uniagenda.repository.AgendaReservaSalaRepository;
import com.utfpr.uniagenda.repository.search.AgendaReservaSalaSearchRepository;
import com.utfpr.uniagenda.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.utfpr.uniagenda.domain.AgendaReservaSala}.
 */
@RestController
@RequestMapping("/api")
public class AgendaReservaSalaResource {

    private final Logger log = LoggerFactory.getLogger(AgendaReservaSalaResource.class);

    private static final String ENTITY_NAME = "agendaReservaSala";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AgendaReservaSalaRepository agendaReservaSalaRepository;

    private final AgendaReservaSalaSearchRepository agendaReservaSalaSearchRepository;

    public AgendaReservaSalaResource(AgendaReservaSalaRepository agendaReservaSalaRepository, AgendaReservaSalaSearchRepository agendaReservaSalaSearchRepository) {
        this.agendaReservaSalaRepository = agendaReservaSalaRepository;
        this.agendaReservaSalaSearchRepository = agendaReservaSalaSearchRepository;
    }

    /**
     * {@code POST  /agenda-reserva-salas} : Create a new agendaReservaSala.
     *
     * @param agendaReservaSala the agendaReservaSala to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new agendaReservaSala, or with status {@code 400 (Bad Request)} if the agendaReservaSala has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/agenda-reserva-salas")
    public ResponseEntity<AgendaReservaSala> createAgendaReservaSala(@RequestBody AgendaReservaSala agendaReservaSala) throws URISyntaxException {
        log.debug("REST request to save AgendaReservaSala : {}", agendaReservaSala);
        if (agendaReservaSala.getId() != null) {
            throw new BadRequestAlertException("A new agendaReservaSala cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AgendaReservaSala result = agendaReservaSalaRepository.save(agendaReservaSala);
        agendaReservaSalaSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/agenda-reserva-salas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /agenda-reserva-salas} : Updates an existing agendaReservaSala.
     *
     * @param agendaReservaSala the agendaReservaSala to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agendaReservaSala,
     * or with status {@code 400 (Bad Request)} if the agendaReservaSala is not valid,
     * or with status {@code 500 (Internal Server Error)} if the agendaReservaSala couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/agenda-reserva-salas")
    public ResponseEntity<AgendaReservaSala> updateAgendaReservaSala(@RequestBody AgendaReservaSala agendaReservaSala) throws URISyntaxException {
        log.debug("REST request to update AgendaReservaSala : {}", agendaReservaSala);
        if (agendaReservaSala.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AgendaReservaSala result = agendaReservaSalaRepository.save(agendaReservaSala);
        agendaReservaSalaSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, agendaReservaSala.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /agenda-reserva-salas} : get all the agendaReservaSalas.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of agendaReservaSalas in body.
     */
    @GetMapping("/agenda-reserva-salas")
    public List<AgendaReservaSala> getAllAgendaReservaSalas() {
        log.debug("REST request to get all AgendaReservaSalas");
        return agendaReservaSalaRepository.findAll();
    }

    /**
     * {@code GET  /agenda-reserva-salas/:id} : get the "id" agendaReservaSala.
     *
     * @param id the id of the agendaReservaSala to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the agendaReservaSala, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/agenda-reserva-salas/{id}")
    public ResponseEntity<AgendaReservaSala> getAgendaReservaSala(@PathVariable Long id) {
        log.debug("REST request to get AgendaReservaSala : {}", id);
        Optional<AgendaReservaSala> agendaReservaSala = agendaReservaSalaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(agendaReservaSala);
    }

    /**
     * {@code DELETE  /agenda-reserva-salas/:id} : delete the "id" agendaReservaSala.
     *
     * @param id the id of the agendaReservaSala to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/agenda-reserva-salas/{id}")
    public ResponseEntity<Void> deleteAgendaReservaSala(@PathVariable Long id) {
        log.debug("REST request to delete AgendaReservaSala : {}", id);
        agendaReservaSalaRepository.deleteById(id);
        agendaReservaSalaSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/agenda-reserva-salas?query=:query} : search for the agendaReservaSala corresponding
     * to the query.
     *
     * @param query the query of the agendaReservaSala search.
     * @return the result of the search.
     */
    @GetMapping("/_search/agenda-reserva-salas")
    public List<AgendaReservaSala> searchAgendaReservaSalas(@RequestParam String query) {
        log.debug("REST request to search AgendaReservaSalas for query {}", query);
        return StreamSupport
            .stream(agendaReservaSalaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
