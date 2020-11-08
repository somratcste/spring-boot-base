package info.somrat.rest.utils;

public final class CommonUtils {
    /**
     * Check the string is empty or not
     * @param string
     * @return
     */
    public static boolean isNullOrEmpty( final String string) {
        return string == null || string.trim().isEmpty();
    }
}
