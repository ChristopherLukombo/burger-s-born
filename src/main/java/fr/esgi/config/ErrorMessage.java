package fr.esgi.config;

/**
 * Application constants for managing Errors Message.
 * @author christopher
 */
public final class ErrorMessage {

    public static final String PASSWORD_IS_NOT_VALID = "error.password.is.not.valid";
    public static final String PSEUDO_IS_ALREADY_REGISTERED = "error.pseudo.is.already.registered";
    public static final String EMAIL_IS_ALREADY_USED = "error.email.is.already.used";
    public static final String ERROR_FAIL_TO_UPLOAD = "error.fail.to.upload";
    public static final String ERROR_DURING_SAVING_FILE = "Error during saving file";
    public static final String ERROR_NEW_USER_CANNOT_ALREADY_HAVE_AN_ID = "error.new.user.cannot.already.have.an.id";
    public static final String ERROR_FAIL_BATCH_IMPORT_PRODUCT= "error.batch.product.duplicate";
    
    public static final String ERROR_HAPPENED_DURING_PAYMENT_CREATION = "error.happened.during.payment.creation";

    private ErrorMessage() {}
}
