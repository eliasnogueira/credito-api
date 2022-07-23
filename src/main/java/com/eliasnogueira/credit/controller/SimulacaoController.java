/*
 * MIT License
 *
 * Copyright (c) today.year Elias Nogueira
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.eliasnogueira.credit.controller;

import com.eliasnogueira.credit.dto.SimulacaoDto;
import com.eliasnogueira.credit.entity.Simulacao;
import com.eliasnogueira.credit.exception.SimulacaoException;
import com.eliasnogueira.credit.repository.SimulacaoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@RestController
@RequestMapping("/api/v1/simulacoes")
public class SimulacaoController {

    private final SimulacaoRepository repository;
    private static final String CPF_NAO_ENCONTRADO = "CPF {0} não encontrado";

    public SimulacaoController(SimulacaoRepository repository) {
        this.repository = repository;
    }

    @GetMapping()
    public List<Simulacao> getSimulacao(@RequestParam(name = "nome", required = false) String nome) {
        List<Simulacao> simulationsFound;

        Example<Simulacao> example = Example.of(Simulacao.builder().nome(nome).build(),
                ExampleMatcher.matchingAny().withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains()));

        simulationsFound = repository.findAll(example);

        if (simulationsFound.isEmpty()) throw new SimulacaoException("Simulação não encontrada");

        return simulationsFound;
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Simulacao> one(@PathVariable String cpf) {
        return repository.findByCpf(cpf).
                map(simulacao -> ResponseEntity.ok().body(simulacao)).
                orElseThrow(() -> new SimulacaoException(MessageFormat.format(CPF_NAO_ENCONTRADO, cpf)));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Simulacao> novaSimulacao(@Valid @RequestBody SimulacaoDto simulacao) {
        Simulacao createdSimulation = repository.save(new ModelMapper().map(simulacao, Simulacao.class));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{cpf}").
                buildAndExpand(createdSimulation.getCpf()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{cpf}")
    public Simulacao atualizarSimulacao(@RequestBody SimulacaoDto simulacao, @PathVariable String cpf) {
        return update(new ModelMapper().
                map(simulacao, Simulacao.class), cpf).
                orElseThrow(() -> new SimulacaoException(MessageFormat.format(CPF_NAO_ENCONTRADO, cpf)));
    }

    @DeleteMapping("/{cpf}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String cpf) {
        if (repository.findByCpf(cpf).isEmpty())
            throw new SimulacaoException(MessageFormat.format(CPF_NAO_ENCONTRADO, cpf));

        repository.deleteByCpf(cpf);
    }

    private Optional<Simulacao> update(Simulacao newSimulacao, String cpf) {
        return repository.findByCpf(cpf).map(simulacao -> {
            setIfNotNull(simulacao::setId, newSimulacao.getId());
            setIfNotNull(simulacao::setNome, newSimulacao.getNome());
            setIfNotNull(simulacao::setCpf, newSimulacao.getCpf());
            setIfNotNull(simulacao::setEmail, newSimulacao.getEmail());
            setIfNotNull(simulacao::setParcelas, newSimulacao.getParcelas());
            setIfNotNull(simulacao::setValor, newSimulacao.getValor());

            return repository.save(simulacao);
        });
    }

    private <T> void setIfNotNull(final Consumer<T> setter, final T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
