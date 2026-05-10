package gasfgrv.s3tables.infrastructure.adapters.in;

import gasfgrv.s3tables.domain.model.DadosTabela;
import gasfgrv.s3tables.domain.ports.in.ConsultaPort;
import gasfgrv.s3tables.domain.ports.out.TabelaRepositoryPort;
import gasfgrv.s3tables.infrastructure.mapper.TableMapper;
import org.springframework.stereotype.Service;

@Service
public class ConsultaService implements ConsultaPort {

    private final TabelaRepositoryPort tabelaRepositoryPort;
    private final TableMapper mapper;

    public ConsultaService(TabelaRepositoryPort tabelaRepositoryPort, TableMapper mapper) {
        this.tabelaRepositoryPort = tabelaRepositoryPort;
        this.mapper = mapper;
    }

    @Override
    public DadosTabela consultarTabela() {
        return mapper.toDomain(tabelaRepositoryPort.consultarTabela());
    }

}
