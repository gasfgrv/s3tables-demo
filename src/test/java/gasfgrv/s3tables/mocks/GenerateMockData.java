package gasfgrv.s3tables.mocks;

import gasfgrv.s3tables.domain.model.DadosTabela;
import org.instancio.Instancio;

public class GenerateMockData {

    public static DadosTabela generateDomain() {
        return Instancio.of(DadosTabela.class)
                .create();
    }

}
