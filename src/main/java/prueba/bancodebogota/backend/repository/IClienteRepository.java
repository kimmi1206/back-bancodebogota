package prueba.bancodebogota.backend.repository;

import prueba.bancodebogota.backend.domain.entity.Cliente;

public interface IClienteRepository {

    Cliente findByTipoDocumentoAndNumeroDocumento(String tipoDocumento, String numeroDocumento);
}
