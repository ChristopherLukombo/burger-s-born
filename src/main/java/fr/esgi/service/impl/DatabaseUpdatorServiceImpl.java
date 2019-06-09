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
import java.util.ArrayList;

@Service("DatabaseUpdatorService")
@Transactional
public class DatabaseUpdatorServiceImpl implements DatabaseUpdatorService {

    /**
     * Logger du Service DatabaseUpdatorService
     */
    private static Logger LOGGER = LoggerFactory.getLogger(DatabaseUpdatorService.class);

    private ProductRepository productRepository;

    private ProductMapper productMapper;

    // Pas sur que ça soit necessaire du coup
    // si on stocke pas dans un fichier
    private final ConfigurationService configurationService;

    private final ProductService productService;

    // Mettre l'annotation @Autowired au dessus du constructeur
    
    public DatabaseUpdatorServiceImpl(ConfigurationService configurationService, ProductService productService) {
        this.configurationService = configurationService;
        this.productService = productService;
    }

    // JavaDoc
    @Override
    public JSONArray importFile(MultipartFile fileToImport, String fileFormat) {
         // Tester la condition inverse pour propager une exception FAST et cela évite de retourner un null et réduit la compléxité
    	 // throw new BurgerSTerminalException(HttpStatus.NOT_FOUND.value(), "Produits non trouvé");
        if (fileFormat.equals(FilenameUtils.getExtension(fileToImport.getOriginalFilename())) && !fileToImport.isEmpty() ) {
        	  // Pas besoin de stocker dans un répertoire
            // La classe MultipartFile peut retourner un InputStream avec la méthode getInputStream tu peux lire le fichier ligne par ligne.
        	// A discuter pour plus d'infos
                File destinationFile = new File("C:\\tmp\\" + fileToImport.getOriginalFilename());
                try {
                    fileToImport.transferTo(destinationFile);
                    String datas = FileUtils.readFileToString(destinationFile, "UTF-8");
                    JSONArray objects = new JSONArray(datas);

                    return destinationFile.delete() ? objects : null;

                } catch (IOException e) {
                    LOGGER.debug("Error while importing file " + fileToImport.getOriginalFilename() +  " :  " + e.getMessage());
                    // Supprimer le log et propager exception au lieu de loguer car quand l'exception est propagé elle sera automatiquement logué.
                    return null;
                }

        } else {
            LOGGER.debug("Error while importing file " + fileToImport.getOriginalFilename() + " , wrong file format");
            return null;
        }
    }
}
