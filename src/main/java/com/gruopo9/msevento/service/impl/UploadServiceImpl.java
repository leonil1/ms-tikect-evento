package com.gruopo9.msevento.service.impl;


import com.gruopo9.msevento.service.UploadService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadServiceImpl implements UploadService {
    private final static String DIRECTORIO_UPLOAD = "uploads";
    @Override
    public Resource cargar(String nombreFoto) throws MalformedURLException {
        Path rutaArchivo = getPath(nombreFoto);

        Resource recurso = new UrlResource(rutaArchivo.toUri());

        if(!recurso.exists() && !recurso.isReadable()) {
            rutaArchivo = Paths.get("src/main/resources/static/images").resolve("no-usuario.png").toAbsolutePath();

            recurso = new UrlResource(rutaArchivo.toUri());

        }
        return recurso;
    }

    @Override
    public String copiar(MultipartFile archivo) throws IOException {
        String nombreArchivo = UUID.randomUUID().toString() + "_" +  archivo.getOriginalFilename().replace(" ", "");

        Path rutaArchivo = getPath(nombreArchivo);


        Files.copy(archivo.getInputStream(), rutaArchivo);

        return nombreArchivo;
    }

    @Override
    public boolean eliminar(String nombreFoto) {
        if(nombreFoto !=null && nombreFoto.length() >0) {
            Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
            File archivoFotoAnterior = rutaFotoAnterior.toFile();
            if(archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
                archivoFotoAnterior.delete();
                return true;
            }
        }

        return false;
    }

    @Override
    public Path getPath(String nombreFoto) {
        return Paths.get(DIRECTORIO_UPLOAD).resolve(nombreFoto).toAbsolutePath();
    }

}
