package dev.anhcraft.craftkit.common.utils;

import dev.anhcraft.jvmkit.lang.annotation.Beta;
import org.jetbrains.annotations.NotNull;

public class VersionUtil {
    /**
     * Compares two given versions in string.<br>
     * The default separator is dot (".").
     * @param version the current version
     * @param target the target version
     * @return result; positive if the current version is newer; negative
     *         if the target is newer; and zero if both equals each other.
     */
    @Beta
    public static int compareVersion(@NotNull String version, @NotNull String target) {
        return compareVersion(version, target, "\\.");
    }

    /**
     * Compares two given versions in string.
     * @param version the current version
     * @param target the target version
     * @param separator the separator between sequences
     * @return result; positive if the current version is newer; negative
     *         if the target is newer; and zero if both equals each other.
     */
    @Beta
    public static int compareVersion(@NotNull String version, @NotNull String target, @NotNull String separator) {
        version = version.trim();
        target = target.trim();
        if(version.isEmpty()) return -target.length();
        if(target.isEmpty()) return version.length();
        String[] a = version.split(separator);
        String[] b = target.split(separator);
        if(a.length < b.length) {
            String[] na = new String[b.length];
            System.arraycopy(a, 0, na, 0, a.length);
            for (int i = a.length; i < b.length; i++) {
                StringBuilder x = new StringBuilder();
                for(int j = 0; j < b[i].length(); j++){
                    x.append('0');
                }
                na[i] = x.toString();
            }
            a = na;
        } else if(b.length < a.length) {
            String[] nb = new String[a.length];
            System.arraycopy(b, 0, nb, 0, b.length);
            for (int i = b.length; i < a.length; i++) {
                StringBuilder x = new StringBuilder();
                for(int j = 0; j < a[i].length(); j++){
                    x.append('0');
                }
                nb[i] = x.toString();
            }
            b = nb;
        }
        for(int i = 0; i < a.length; i++){
            int ia = 0;
            int ib = 0;

            int n = 1;
            char[] ac = a[i].toCharArray();
            for (int j = ac.length; j > 0; j--){
                if(Character.isDigit(ac[j - 1])) {
                    ia += (ac[j - 1] - 48) * n;
                    n *= 10;
                }
            }
            int m = 1;
            char[] bc = b[i].toCharArray();
            for (int j = bc.length; j > 0; j--){
                if(Character.isDigit(bc[j - 1])) {
                    ib += (bc[j - 1] - 48) * m;
                    m *= 10;
                }
            }
            if(ia != ib) {
                return ia - ib;
            }
        }
        return 0;
    }
}
