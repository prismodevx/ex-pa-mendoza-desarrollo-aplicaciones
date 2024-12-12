package com.mendoza.examen01.rest;

import com.mendoza.examen01.converter.UsuarioConverter;
import com.mendoza.examen01.dto.UsuarioDto;
import com.mendoza.examen01.entity.Usuario;
import com.mendoza.examen01.service.PdfService;
import com.mendoza.examen01.service.UsuarioService;
import com.mendoza.examen01.util.WrapperResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/v1/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService service;

    @Autowired
    private UsuarioConverter converter;

    @Autowired
    private PdfService pdfService;

    @GetMapping
    public ResponseEntity<List<UsuarioDto>> findAll() {
        List<UsuarioDto> registros = converter.fromEntities(service.findAll());
        return new WrapperResponse(true, "success", registros).createResponse(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> findById (@PathVariable("id") int id) {
        UsuarioDto dto = converter.fromEntity(service.findById(id));

        return new WrapperResponse(true, "success", dto).createResponse(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UsuarioDto> create(@RequestBody Usuario usuario) {
        UsuarioDto registro = converter.fromEntity(service.save(usuario));
        return new WrapperResponse(true, "success", registro).createResponse(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> update(@PathVariable("id") int id, @RequestBody Usuario usuario) {
        UsuarioDto registro = converter.fromEntity(service.save(usuario));
        return new WrapperResponse(true, "success", registro).createResponse(HttpStatus.OK);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<UsuarioDto> deactivate(@PathVariable("id") int id) {
        UsuarioDto registro = converter.fromEntity(service.deactivate(id));
        return new WrapperResponse(true, "success", registro).createResponse(HttpStatus.OK);
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<UsuarioDto> activate(@PathVariable("id") int id) {
        UsuarioDto registro = converter.fromEntity(service.activate(id));
        return new WrapperResponse(true, "success", registro).createResponse(HttpStatus.OK);
    }

    @GetMapping("/report")
    public ResponseEntity<byte[]> generateReport() {
        List<UsuarioDto> usuarios = converter.fromEntities(service.findAll());

        LocalDateTime fecha = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fechaHora = fecha.format(formato);

        // crear el contexto de Thymeleaf con los datos
        Context context = new Context();
        context.setVariable("registros", usuarios);
        context.setVariable("fecha", fechaHora);

        // Llamar al servicio PdfService para generar el PDF
        byte[] pdfBytes = pdfService.createPdf("usuarioReporte", context);

        // Configurar los encabezados de la respuesta HTTP para devolver el PDF
        // import org.springframework.http.HttpHeaders;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "reporte_usuarios.pdf");

        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
}
