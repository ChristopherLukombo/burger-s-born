package fr.esgi.service;

import fr.esgi.exception.BurgerSTerminalException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Interface for the file import based database update
 * @author Guillaume Tako
 */
@Service
public interface DatabaseUpdatorService {

    void importFile(MultipartFile fileToImport) throws BurgerSTerminalException, IOException;
}
