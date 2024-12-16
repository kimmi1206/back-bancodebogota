package prueba.bancodebogota.backend.repository;

import org.springframework.stereotype.Repository;
import prueba.bancodebogota.backend.domain.entity.Cliente;

import java.util.*;

@Repository
public class ClienteRepository implements IClienteRepository {

    private final Map<String, Cliente> clienteMap = Map.ofEntries(
            new AbstractMap.SimpleEntry<String, Cliente>("C-23445322",
                    new Cliente("C",
                            "23445322",
                            "juan",
                            "andres",
                            "perez",
                            "aristizabal",
                            "3001234567",
                            "calle 123 # 45 - 67",
                            "bogota"))

    );


    /**
     * @param tipoDocumento   String con el tipo de documento del cliente (C - Cedula, P - Pasaporte)
     * @param numeroDocumento String con el numero de documento del cliente
     * @return Retorna un Objeto Cliente con los datos del cliente
     */
    @Override
    public Cliente findByTipoDocumentoAndNumeroDocumento(String tipoDocumento, String numeroDocumento) {
        return clienteMap.get(tipoDocumento + "-" + numeroDocumento);
    }
}
