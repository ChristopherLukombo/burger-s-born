package fr.esgi.service.impl;


import fr.esgi.config.ConfigurationService;
import fr.esgi.dao.ProductRepository;
import fr.esgi.exception.BurgerSTerminalException;
import fr.esgi.service.DatabaseUpdatorService;
import fr.esgi.service.ProductService;
import fr.esgi.service.dto.ProductDTO;
import fr.esgi.service.mapper.ProductMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.aspectj.util.FileUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@Service("DatabaseUpdatorService")
@Transactional
public class DatabaseUpdatorServiceImpl implements DatabaseUpdatorService {

    /**
     * Logger du Service DatabaseUpdatorService
     */
    private static Logger LOGGER = LoggerFactory.getLogger(DatabaseUpdatorService.class);

    // Mettre l'annotation @Autowired au dessus du constructeur

    public DatabaseUpdatorServiceImpl() { }

    // JavaDoc
    @Override
    public JSONArray importFile(MultipartFile fileToImport, String fileFormat) throws BurgerSTerminalException {

        if (fileFormat.isEmpty()) {
            throw new BurgerSTerminalException(HttpStatus.BAD_REQUEST.value(), "Le fichier d'import est vide)");
        }

        if (!fileFormat.equals(FilenameUtils.getExtension(fileToImport.getOriginalFilename()))) {
            throw new BurgerSTerminalException((HttpStatus.BAD_REQUEST.value()), "Format de fichier invalide");
        } else {

            try {
                InputStream targetStream = fileToImport.getInputStream();
                String datas = IOUtils.toString(targetStream, StandardCharsets.UTF_8.name());
                targetStream.close();

                return new JSONArray(datas);
            } catch (IOException e) {
                throw new BurgerSTerminalException(HttpStatus.BAD_REQUEST.value(), "Erreur lors de la récupération du JSON");
            }

        }
    }
}
