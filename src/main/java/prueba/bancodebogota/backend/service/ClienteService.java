package prueba.bancodebogota.backend.service;

import org.springframework.stereotype.Service;
import prueba.bancodebogota.backend.domain.dto.ClienteDTO;
import prueba.bancodebogota.backend.domain.enums.TipoDocumento;
import prueba.bancodebogota.backend.domain.mapper.ClienteMapper;
import prueba.bancodebogota.backend.exception.type.NotFoundException;
import prueba.bancodebogota.backend.repository.IClienteRepository;

import java.io.IOException;
import java.util.Optional;

@Service
public class ClienteService implements IClienteService {
    private final IClienteRepository clienteRepository;

    public ClienteService(IClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public ClienteDTO findByTipoDocumentoAndNumeroDocumento(String tipoDocumento, String numeroDocumento) {
        if (tipoDocumento == null || numeroDocumento == null) {
            throw new IllegalArgumentException("Tipo de documento y numero de documento son requeridos");
        }
        if (TipoDocumento.findByValue(tipoDocumento) == null) {
            throw new IllegalArgumentException("Tipo de documento no valido");
        }
        if (Integer.parseInt(numeroDocumento) <= 0) {
            throw new IllegalArgumentException("Numero de documento no valido");
        }
        return ClienteMapper.mapToClienteDTO(
                Optional.ofNullable(clienteRepository
                                .findByTipoDocumentoAndNumeroDocumento(tipoDocumento, numeroDocumento))
                        .orElseThrow(() -> new NotFoundException("Cliente no encontrado")));
    }

}
