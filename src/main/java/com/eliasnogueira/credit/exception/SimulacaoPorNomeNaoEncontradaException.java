package com.eliasnogueira.credit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "nome não encontrado")
public class SimulacaoPorNomeNaoEncontradaException extends RuntimeException {
}
