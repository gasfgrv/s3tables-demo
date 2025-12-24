package gasfgrv.s3tables.infrastructure.controller;

import gasfgrv.s3tables.infrastructure.dto.TableDto;
import gasfgrv.s3tables.application.ConsultaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teste")
public class TesteController {

    private final ConsultaService consultaService;

    public TesteController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @GetMapping
    public ResponseEntity<TableDto> teste() {
        return ResponseEntity.ok(consultaService.consultarTabela());
    }

}
