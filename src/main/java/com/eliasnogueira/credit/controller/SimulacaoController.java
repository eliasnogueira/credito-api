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
import com.eliasnogueira.credit.dto.ValidacaoDto;
import com.eliasnogueira.credit.entity.Simulacao;
import com.eliasnogueira.credit.exception.SimulacaoPorNomeNaoEncontradaException;
import com.eliasnogueira.credit.exception.SimulacaoException;
import com.eliasnogueira.credit.repository.SimulacaoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import java.net.URI;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import javax.validation.Valid;
import lombok.SneakyThrows;
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

@RestController
@RequestMapping("/api/v1/simulacoes")
@Api(value = "/simulacoes", tags = "Simulações")
public class SimulacaoController {

    private final SimulacaoRepository repository;
    private static final String CPF_NAO_ENCONTRADO = "CPF {0} não encontrado";

    public SimulacaoController(SimulacaoRepository repository) {
        this.repository = repository;
    }

    @GetMapping()
    @ApiOperation(value = "Retorna todas as simulações existentes")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Simulações encontradas", response = SimulacaoDto.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "Nome não encontrado")
    })
    @SneakyThrows
    List<Simulacao> getSimulacao(@ApiParam(value = "Pesquisar uma simulação pelo nome da pessoa")
    @RequestParam(name = "nome", required = false) String name) {
        List<Simulacao> simulationsFound;

        Example<Simulacao> example =
            Example.of(Simulacao.builder().nome(name).build(),
                ExampleMatcher.matchingAny().
                    withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains()));

        simulationsFound = repository.findAll(example);

        if (simulationsFound.isEmpty()) throw new SimulacaoPorNomeNaoEncontradaException();

        return simulationsFound;
    }

    @GetMapping("/{cpf}")
    @ApiOperation(value = "Retorna uma simulação através do CPF")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Simulação encontrada", response = SimulacaoDto.class),
        @ApiResponse(code = 404, message = "Simulação não encontrada")
    })
    ResponseEntity<Simulacao> one(@ApiParam(value = "CPF da pesssoa a ser pesquisada", required = true) @PathVariable String cpf) {
        return repository.findByCpf(cpf).
            map(simulacao -> ResponseEntity.ok().body(simulacao)).
            orElseThrow(() -> new SimulacaoException(MessageFormat.format(CPF_NAO_ENCONTRADO, cpf)));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Cria uma nova simulação", code = 201)
    @ApiResponses({
        @ApiResponse(code = 201, message = "Simulação criada com sucesso", response = Object.class,
            responseHeaders = @ResponseHeader(name = "Location", description = "URI completa contendo o CPF",
                response = String.class)),
        @ApiResponse(code = 422, message = "Falta de informações", response = ValidacaoDto.class),
        @ApiResponse(code = 409, message = "CPF já existente")
    })
    ResponseEntity<?> novaSimulacao(@ApiParam(value = "Objeto da Simulação", required = true)
        @Valid @RequestBody SimulacaoDto simulacao) {
        Simulacao createdSimulation = repository.save(new ModelMapper().map(simulacao, Simulacao.class));
        URI location = ServletUriComponentsBuilder.
            fromCurrentRequest().
            path("/{cpf}").
            buildAndExpand(createdSimulation.getCpf()).
            toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{cpf}")
    @ApiOperation(value = "Atualiza uma simulação existente através do CPF")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Simulação alterada com sucesso", response = SimulacaoDto.class),
        @ApiResponse(code = 404, message = "Simulação não encontrada"),
        @ApiResponse(code = 409, message = "CPF já existente")
    })
    Simulacao atualizarSimulacao(
        @ApiParam(value = "Objeto simulação com os dados que serão atualizados", required = true)
            @RequestBody SimulacaoDto simulacao,
        @ApiParam(value = "CPF da pesssoa para a simulação que será atualizada", required = true)
            @PathVariable String cpf) {
        return update(new ModelMapper().
            map(simulacao, Simulacao.class), cpf).
            orElseThrow(() -> new SimulacaoException(MessageFormat.format(CPF_NAO_ENCONTRADO, cpf)));
    }

    @DeleteMapping("/{cpf}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Remove uma simulação existente através do CPF", code = 204)
    @ApiResponses({
        @ApiResponse(code = 204, message = "Simulação removida com sucesso"),
        @ApiResponse(code = 404, message = "Simulação não encontrada")
    })
    void delete(@ApiParam(value = "CPF da pesssoa para a simulação que será removida", required = true)
        @PathVariable String cpf) {
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
