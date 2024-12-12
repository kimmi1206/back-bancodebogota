package prueba.bancodebogota.backend.controller;


import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;
import prueba.bancodebogota.backend.domain.dto.ClienteDTO;
import prueba.bancodebogota.backend.service.IClienteService;

import java.io.*;
import java.nio.file.*;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1/clientes")
public class ClienteController {
    private final IClienteService clienteService;
    private final RestTemplate restTemplate;

    @Value("${base.url}")
    private String baseUrl;

    @Autowired
    public ClienteController(IClienteService clienteService, RestTemplateBuilder restTemplate) {
        this.clienteService = clienteService;
        this.restTemplate = restTemplate.build();
    }

    @GetMapping("/")
    public ClienteDTO getClienteDTO(@RequestParam String tipoDocumento, @RequestParam String numeroDocumento) {
        return clienteService.findByTipoDocumentoAndNumeroDocumento(tipoDocumento, numeroDocumento);
    }

    @GetMapping("/download")
    public ResponseEntity<?> downloadFile() throws IOException {
        String fileName = "clientes.json";
        String url = baseUrl + fileName;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class);

        System.out.println(response.getStatusCode());

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to download file");
        }

        Files.write(Paths.get(fileName), response.getBody());
        Path filePath = Paths.get(System.getProperty("user.dir"))
                .toAbsolutePath().normalize().resolve(fileName).normalize();
        Resource file = new UrlResource(filePath.toUri());

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(file);
    }
}
