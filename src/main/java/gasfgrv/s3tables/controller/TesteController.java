package gasfgrv.s3tables.controller;

import gasfgrv.s3tables.controller.dto.TableDto;
import gasfgrv.s3tables.service.ConsultaService;
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
