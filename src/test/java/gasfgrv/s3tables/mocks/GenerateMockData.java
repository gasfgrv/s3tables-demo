package gasfgrv.s3tables.mocks;

import gasfgrv.s3tables.domain.model.DadosTabela;
import gasfgrv.s3tables.infrastructure.dto.TableDto;
import org.instancio.Instancio;

public class GenerateMockData {

    public static DadosTabela generateDomain() {
        return Instancio.create(DadosTabela.class);
    }

    public static TableDto generateDto() {
        return Instancio.create(TableDto.class);
    }
}
