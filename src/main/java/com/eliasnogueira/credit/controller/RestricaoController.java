/*
 * MIT License
 *
 * Copyright (c) 2020 Elias Nogueira
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

import com.eliasnogueira.credit.dto.v1.MensagemDto;
import com.eliasnogueira.credit.entity.Restricao;
import com.eliasnogueira.credit.exception.RestricaoException;
import com.eliasnogueira.credit.service.RestricaoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.Optional;

@RestController
public class RestricaoController {

    private final RestricaoService restricaoService;
    private static final String CPF_POSSUI_RESTRICAO = "CPF {0} possui restrição";

    public RestricaoController(RestricaoService restricaoService) {
        this.restricaoService = restricaoService;
    }

    @GetMapping("/api/v1/restricoes/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    public MensagemDto one(@PathVariable String cpf) {
        Optional<Restricao> restrictionOptional = restricaoService.findByCpf(cpf);

        if (restrictionOptional.isPresent()) {
            return new MensagemDto(MessageFormat.format(CPF_POSSUI_RESTRICAO, cpf));
        } else {
            throw new RestricaoException();
        }
    }

    @GetMapping("/api/v2/restricoes/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    public com.eliasnogueira.credit.dto.v2.MensagemDto oneV2(@PathVariable String cpf) {
        Optional<Restricao> restrictionOptional = restricaoService.findByCpf(cpf);

        if (restrictionOptional.isPresent()) {
            return new com.eliasnogueira.credit.dto.v2.MensagemDto(
                    MessageFormat.format(CPF_POSSUI_RESTRICAO, cpf), restrictionOptional.get().getTipoRestricao());
        } else {
            throw new RestricaoException();
        }
    }
}
