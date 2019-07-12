package fr.esgi.config;

/**
 * Application constants for managing Errors Message.
 * @author christopher
 */
public final class ErrorMessage {

    public static final String PASS_IS_NOT_VALID = "error.password.is.not.valid";
    public static final String PSEUDO_IS_ALREADY_REGISTERED = "error.pseudo.is.already.registered";
    public static final String EMAIL_IS_ALREADY_USED = "error.email.is.already.used";
    public static final String ERROR_FAIL_TO_UPLOAD = "error.fail.to.upload";
    public static final String ERROR_DURING_SAVING_FILE = "Error during saving file";
    public static final String ERROR_NEW_USER_CANNOT_ALREADY_HAVE_AN_ID = "error.new.user.cannot.already.have.an.id";
    
    public static final String ERROR_FAIL_BATCH_IMPORT_PRODUCT= "error.batch.product.duplicate";
    public static final String ERROR_BATCH_INVALID = "error.batch.invalid";
    
    public static final String THE_FILE_FORMAT_IS_INVALID = "The file format is invalid";
    public static final String THE_IMPORT_FILE_IS_EMPTY = "The import file is empty";
    public static final String AN_ERROR_OCCURRED_DURING_THE_IMPORT = "An error occurred during the import";
    public static final String THE_FILE_DOES_NOT_CONTAIN_THE_CORRECT_NUMBER_OF_COLUMNS = "The file does not contain the correct number of columns";
    public static final String ERROR_DURING_READING_OF_FILE = "Error during reading of file";
    
    public static final String ERROR_HAPPENED_DURING_PAYMENT_CREATION = "error.happened.during.payment.creation";
    
    public static final String USER_DOES_NOT_EXISTS = "error.user.does.not.exists";
    
    public static final String ERROR_CATEGORY_NOT_FOUND = "error.category.not.found";
    
    public static final String ERROR_COMMAND_MUST_HAVE_ID = "error.command.must.have.id";
    public static final String ERROR_COMMAND_NOT_FOUND = "error.command.not.found";
    public static final String ERROR_NEW_COMMAND_ID_EXIST = "error.new.command.id.exist";
    public static final String ERROR_NOT_COMMANDS = "error.not.commands";
    
    public static final String ERROR_PRODUCT_MUST_HAVE_ID = "error.product.must.have.id";
    public static final String ERROR_NEW_PRODUCT_ID_EXIST = "error.new.product.id.exist";
    public static final String ERROR_PRODUCTS_NOT_FOUND = "error.products.not.found";
    public static final String ERROR_MENUS_NOT_FOUND = "error.menus.not.found";
    
    public static final String ERROR_PAYMENT_REFUND = "error.payment.refund";
    public static final String ERROR_PAYMENT_VALIDATION = "error.payment.validation";
    public static final String ERROR_PAYMENT_CREATION = "error.payment.creation";

    public static final String ERROR_DURING_READING_OF_IMAGE = "error.image.reading";
    
    private ErrorMessage() {}
}
