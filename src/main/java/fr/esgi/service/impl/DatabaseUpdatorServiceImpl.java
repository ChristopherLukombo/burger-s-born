package fr.esgi.service.impl;


import fr.esgi.config.ConfigurationService;
import fr.esgi.config.Constants;
import fr.esgi.dao.ProductRepository;
import fr.esgi.domain.Product;
import fr.esgi.exception.BurgerSTerminalException;
import fr.esgi.service.DatabaseUpdatorService;
import fr.esgi.service.mapper.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

@Service("DatabaseUpdatorService")
@Transactional
public class DatabaseUpdatorServiceImpl implements DatabaseUpdatorService {

    /**
     * Logger du Service DatabaseUpdatorService
     */
    private static Logger LOGGER = LoggerFactory.getLogger(DatabaseUpdatorService.class);

    private ProductRepository productRepository;

    private ProductMapper productMapper;

    private final ConfigurationService configurationService;

    public DatabaseUpdatorServiceImpl(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }


    @Override
    public void importFile(MultipartFile fileToImport) throws BurgerSTerminalException, IOException {

//        // Creation d'un répertoire temporaire pour le stockage du fichier a importer
//
//
//
//
//        List<Product> productsToImpot = Collections.emptyList();
//
//        if (!fileToImport.isEmpty() && fileToImport != null) {
//
//
//
//        }
//

    }
}
