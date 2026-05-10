package gasfgrv.s3tables.domain.ports.out;

import gasfgrv.s3tables.infrastructure.dto.TableDto;

public interface TabelaRepositoryPort {
    TableDto consultarTabela();
}
