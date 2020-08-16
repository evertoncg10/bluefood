package br.com.everton.bluefood.application.service;

import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.everton.bluefood.util.IOUtils;

@Service
public class ImageService {

    private static final String TYPE_COMIDA = "comida";
    private static final String TYPE_CATEGORIA = "categoria";
    private static final String TYPE_LOGOTIPO = "logotipo";

    @Value("${bluefood.files.logotipo}")
    private String logotiposDir;

    @Value("${bluefood.files.comida}")
    private String comidasDir;

    @Value("${bluefood.files.categoria}")
    private String categoriasDir;

    public void uploadLogotipo(MultipartFile multipartFile, String fileName) {
        try {
            IOUtils.copy(multipartFile.getInputStream(), fileName, logotiposDir);

        } catch (IOException e) {
            throw new ApplicationServiceException(e);
        }
    }

    public void uploadComida(MultipartFile multipartFile, String fileName) {
        try {
            IOUtils.copy(multipartFile.getInputStream(), fileName, comidasDir);

        } catch (IOException e) {
            throw new ApplicationServiceException(e);
        }
    }

    public byte[] getBytes(String type, String imgName) {

        try {
            String dir;
            if (TYPE_COMIDA.equals(type)) {
                dir = comidasDir;

            } else if (TYPE_LOGOTIPO.equals(type)) {
                dir = logotiposDir;

            } else if (TYPE_CATEGORIA.equals(type)) {
                dir = categoriasDir;
            } else {
                throw new Exception(type + " não é um tipo de imagem válido");
            }

            return IOUtils.getBytes(Paths.get(dir, imgName));

        } catch (Exception e) {
            throw new ApplicationServiceException(e);
        }
    }

}
