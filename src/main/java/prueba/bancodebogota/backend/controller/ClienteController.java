package prueba.bancodebogota.backend.controller;


import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;
import prueba.bancodebogota.backend.domain.dto.ClienteDTO;
import prueba.bancodebogota.backend.exception.type.*;
import prueba.bancodebogota.backend.service.IClienteService;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/clientes")
public class ClienteController {
    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);
    private final IClienteService clienteService;
    private final RestTemplate restTemplate;

    @Value("${bucket.url}")
    private String bucketUrl;

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
        String url = bucketUrl + fileName;

        try {
            File myFile = new File(fileName);
            Files.delete(myFile.toPath());
        } catch (Exception e) {
            if (!(e instanceof NoSuchFileException)) {
                logger.error("Failed to delete the file", e);
            }
        }

        int fileSize = -1;
         try {
            URL urlObj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
            connection.setRequestMethod("HEAD"); 
            fileSize = connection.getContentLength();
            connection.disconnect();
        } catch (Exception e) {
            logger.error("Error while checking file size", e);
        }

        if (fileSize == -1) {
            throw new InsufficientStorageException("File size is unknown");
        } else if (fileSize > 8192) {
            throw new InsufficientStorageException("File size exceeds the limit of 8Kb: " + fileSize + " bytes");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new HttpClientErrorException(response.getStatusCode());
        }

        Files.write(Paths.get(fileName), response.getBody());
        Path filePath = Paths.get(System.getProperty("user.dir"))
                .toAbsolutePath().normalize().resolve(fileName).normalize();
        Resource file = new UrlResource(filePath.toUri());

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(file);
    }
}
