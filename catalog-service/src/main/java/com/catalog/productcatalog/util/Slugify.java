package com.catalog.productcatalog.util;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public interface Slugify {
	 Pattern NONLATIN = Pattern.compile("[^\\w-]");
     Pattern WHITESPACE = Pattern.compile("[\\s]");

     static String toSlug(String input) {
        if (input == null)
            throw new IllegalArgumentException();

        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("").replaceAll("[-]+", "-").replaceAll("^-", "")
                .replaceAll("-$", "");
        return slug.toLowerCase(Locale.ENGLISH);}
     
}
