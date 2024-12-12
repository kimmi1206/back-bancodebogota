package prueba.bancodebogota.backend.service;

import prueba.bancodebogota.backend.domain.dto.ClienteDTO;

public interface IClienteService {
    ClienteDTO findByTipoDocumentoAndNumeroDocumento(String tipoDocumento, String numeroDocumento);
}
