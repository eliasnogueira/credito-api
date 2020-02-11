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

package com.eliasnogueira.credit;

import com.eliasnogueira.credit.entity.Restricao;
import com.eliasnogueira.credit.entity.Simulacao;
import com.eliasnogueira.credit.entity.TipoRestricao;
import com.eliasnogueira.credit.repository.RestricaoRepository;
import com.eliasnogueira.credit.repository.SimulacaoRepository;
import java.math.BigDecimal;
import java.util.HashMap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initCreditDatabase(RestricaoRepository restricaoRepository) {
        return args -> dataToInsert()
            .forEach((cpf, restriction) -> restricaoRepository.save(new Restricao(cpf, restriction)));
    }

    private HashMap<String, String> dataToInsert() {
        HashMap<String, String> data = new HashMap<>();

        data.put("97093236014", TipoRestricao.BLOQUEIO_JURICIAL.value());
        data.put("60094146012", TipoRestricao.CARTAO_CREDITO.value());
        data.put("84809766080", TipoRestricao.BANCARIA.value());
        data.put("62648716050", TipoRestricao.SPC.value());
        data.put("26276298085", TipoRestricao.SPC.value());
        data.put("01317496094", TipoRestricao.CARTAO_CREDITO.value());
        data.put("55856777050", TipoRestricao.BANCARIA.value());
        data.put("19626829001", TipoRestricao.BANCARIA.value());
        data.put("24094592008", TipoRestricao.BANCARIA.value());
        data.put("58063164083", TipoRestricao.BANCARIA.value());

        return data;
    }

    @Bean
    CommandLineRunner initRestrictionDatabase(SimulacaoRepository simulacaoRepository) {
        return args -> {
            simulacaoRepository.save(Simulacao.builder().cpf("66414919004").nome("João").email("joao@gmail.com")
                .valor(new BigDecimal(11000)).parcelas(3).seguro(true).build());
            simulacaoRepository.save(Simulacao.builder().cpf("17822386034").nome("Maria").email("maria@gmail.com")
                .valor(new BigDecimal(20000)).parcelas(5).seguro(false).build());
        };
    }
}