package com.eliasnogueira.credit.it;

import com.eliasnogueira.credit.Run;
import com.eliasnogueira.credit.dto.v1.MensagemDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Run.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestricoesIT {

    @Autowired
    private TestRestTemplate restTemplate;
    private final String uriV1 = "/api/v1/restricoes/{cpf}";
    private final String uriV2 = "/api/v2/restricoes/{cpf}";

    @Test
    @DisplayName("CPF sem restricao")
    void semRestricoes() {
        ResponseEntity<String> response = restTemplate.getForEntity(uriV1, String.class, "123456789");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("CPF com restrição utilizando MensagemDto na versão 1")
    void restricaoV1() {
        String cpfComRestricao = "60094146012";
        ResponseEntity<MensagemDto> response = restTemplate.getForEntity(uriV1, MensagemDto.class, cpfComRestricao);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMensagem()).isEqualTo(String.format("CPF %s possui restrição", cpfComRestricao));
    }

    @Test
    @DisplayName("CPF com restrição utilizando MensagemDto na versão 2")
    void restricaoV2() {
        String cpfComRestricao = "60094146012";
        ResponseEntity<com.eliasnogueira.credit.dto.v2.MensagemDto> response =
                restTemplate.getForEntity(uriV2, com.eliasnogueira.credit.dto.v2.MensagemDto.class, cpfComRestricao);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getRetorno()).isEqualTo(String.format("CPF %s possui restrição", cpfComRestricao));
        assertThat(response.getBody().getDetalhe()).isEqualTo("Cartão de Crédito");
    }
}
