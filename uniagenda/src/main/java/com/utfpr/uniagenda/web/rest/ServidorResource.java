package com.utfpr.uniagenda.web.rest;

import com.utfpr.uniagenda.domain.Servidor;
import com.utfpr.uniagenda.repository.ServidorRepository;
import com.utfpr.uniagenda.repository.search.ServidorSearchRepository;
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
 * REST controller for managing {@link com.utfpr.uniagenda.domain.Servidor}.
 */
@RestController
@RequestMapping("/api")
public class ServidorResource {

    private final Logger log = LoggerFactory.getLogger(ServidorResource.class);

    private static final String ENTITY_NAME = "servidor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServidorRepository servidorRepository;

    private final ServidorSearchRepository servidorSearchRepository;

    public ServidorResource(ServidorRepository servidorRepository, ServidorSearchRepository servidorSearchRepository) {
        this.servidorRepository = servidorRepository;
        this.servidorSearchRepository = servidorSearchRepository;
    }

    /**
     * {@code POST  /servidors} : Create a new servidor.
     *
     * @param servidor the servidor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new servidor, or with status {@code 400 (Bad Request)} if the servidor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/servidors")
    public ResponseEntity<Servidor> createServidor(@RequestBody Servidor servidor) throws URISyntaxException {
        log.debug("REST request to save Servidor : {}", servidor);
        if (servidor.getId() != null) {
            throw new BadRequestAlertException("A new servidor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Servidor result = servidorRepository.save(servidor);
        servidorSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/servidors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /servidors} : Updates an existing servidor.
     *
     * @param servidor the servidor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servidor,
     * or with status {@code 400 (Bad Request)} if the servidor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the servidor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/servidors")
    public ResponseEntity<Servidor> updateServidor(@RequestBody Servidor servidor) throws URISyntaxException {
        log.debug("REST request to update Servidor : {}", servidor);
        if (servidor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Servidor result = servidorRepository.save(servidor);
        servidorSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, servidor.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /servidors} : get all the servidors.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of servidors in body.
     */
    @GetMapping("/servidors")
    public List<Servidor> getAllServidors() {
        log.debug("REST request to get all Servidors");
        return servidorRepository.findAll();
    }

    /**
     * {@code GET  /servidors/:id} : get the "id" servidor.
     *
     * @param id the id of the servidor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the servidor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/servidors/{id}")
    public ResponseEntity<Servidor> getServidor(@PathVariable Long id) {
        log.debug("REST request to get Servidor : {}", id);
        Optional<Servidor> servidor = servidorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(servidor);
    }

    /**
     * {@code DELETE  /servidors/:id} : delete the "id" servidor.
     *
     * @param id the id of the servidor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/servidors/{id}")
    public ResponseEntity<Void> deleteServidor(@PathVariable Long id) {
        log.debug("REST request to delete Servidor : {}", id);
        servidorRepository.deleteById(id);
        servidorSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/servidors?query=:query} : search for the servidor corresponding
     * to the query.
     *
     * @param query the query of the servidor search.
     * @return the result of the search.
     */
    @GetMapping("/_search/servidors")
    public List<Servidor> searchServidors(@RequestParam String query) {
        log.debug("REST request to search Servidors for query {}", query);
        return StreamSupport
            .stream(servidorSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
