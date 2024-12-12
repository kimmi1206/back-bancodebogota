package prueba.bancodebogota.backend.domain.mapper;

import prueba.bancodebogota.backend.domain.dto.ClienteDTO;
import prueba.bancodebogota.backend.domain.entity.Cliente;

public class ClienteMapper {
    public static Cliente mapToCliente(ClienteDTO clienteDTO, String tipoDocumento, String numeroDocumento) {
        return new Cliente(
                tipoDocumento,
                numeroDocumento,
                clienteDTO.getPrimerNombre(),
                clienteDTO.getSegundoNombre(),
                clienteDTO.getPrimerApellido(),
                clienteDTO.getSegundoApellido(),
                clienteDTO.getTelefono(),
                clienteDTO.getDireccion(),
                clienteDTO.getCiudadResidencia()
        );
    }

    public static ClienteDTO mapToClienteDTO(Cliente cliente) {
        return new ClienteDTO(
                cliente.getPrimerNombre(),
                cliente.getSegundoNombre(),
                cliente.getPrimerApellido(),
                cliente.getSegundoApellido(),
                cliente.getTelefono(),
                cliente.getDireccion(),
                cliente.getCiudadResidencia()
        );
    }
}
