package gasfgrv.s3tables.domain.ports.in;

import gasfgrv.s3tables.domain.model.DadosTabela;

public interface ConsultaPort {
    DadosTabela consultarTabela();
}
