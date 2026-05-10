package gasfgrv.s3tables.application;

import gasfgrv.s3tables.domain.model.DadosTabela;
import gasfgrv.s3tables.domain.ports.in.ConsultaPort;
import org.springframework.stereotype.Service;

@Service
public class ConsultaUsecase {

    private final ConsultaPort consultaPort;

    public ConsultaUsecase(ConsultaPort consultaPort) {
        this.consultaPort = consultaPort;
    }

    public DadosTabela consultarTabela() {
        return consultaPort.consultarTabela();
    }

}
