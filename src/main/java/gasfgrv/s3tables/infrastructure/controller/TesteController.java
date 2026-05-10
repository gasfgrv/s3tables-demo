package gasfgrv.s3tables.infrastructure.controller;

import gasfgrv.s3tables.infrastructure.dto.TableDto;
import gasfgrv.s3tables.application.ConsultaUsecase;
import gasfgrv.s3tables.domain.model.DadosTabela;
import gasfgrv.s3tables.infrastructure.mapper.TableMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teste")
public class TesteController {

    private final ConsultaUsecase consultaUsecase;
    private final TableMapper tableMapper;

    public TesteController(ConsultaUsecase consultaUsecase, TableMapper tableMapper) {
        this.consultaUsecase = consultaUsecase;
        this.tableMapper = tableMapper;
    }

    @GetMapping
    public ResponseEntity<TableDto> teste() {
        DadosTabela dadosTabela = consultaUsecase.consultarTabela();
        return ResponseEntity.ok(tableMapper.toDto(dadosTabela));
    }

}
