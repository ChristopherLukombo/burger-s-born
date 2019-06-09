package fr.esgi.config;

import java.util.Locale;

/**
 * Class contains methods utils
 */
public final class Utils {

    public static Locale getLang(String lang) {
        return ("fr".equals(lang)) ? Locale.FRENCH : Locale.ENGLISH;
    }
}
