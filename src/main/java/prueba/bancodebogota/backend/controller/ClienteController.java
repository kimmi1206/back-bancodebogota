package prueba.bancodebogota.backend.controller;


import org.springframework.web.bind.annotation.*;
import prueba.bancodebogota.backend.domain.dto.ClienteDTO;
import prueba.bancodebogota.backend.domain.entity.Cliente;
import prueba.bancodebogota.backend.service.IClienteService;

@RestController
@CrossOrigin
@RequestMapping("api/v1/clientes")
public class ClienteController {
    private final IClienteService clienteService;

    public ClienteController(IClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/")
    public ClienteDTO getClienteDTO(@RequestParam String tipoDocumento, @RequestParam String numeroDocumento) {
        return clienteService.findByTipoDocumentoAndNumeroDocumento(tipoDocumento, numeroDocumento);
    }
}
