package com.mazhe.util;


import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by edison on 17/6/14.
 */
public abstract class Strings {
    private static final String LINE_SEPARATOR = System
        .getProperty("line.separator");

    public static byte[] toUTF8ByteArray(String value) {

        byte[] result = null;
        try {
            result = value.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return result;
    }

    public static String fromUTF8ByteArray(byte[] bytes) {

        String result = null;
        try {
            result = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return result;
    }

    public static boolean isDigitsOnly(String value) {

        if (value == null || value == "") {
            return false;
        }

        for (int i = 0; i < value.length(); i++) {
            if (!Character.isDigit(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <blockquote><pre>
     *     Strings.substitute("Hello {0}!", "Edison") => "Hello Edison!";
     * </pre></blockquote>
     *
     * @param source
     * @param args
     *
     * @return The substituted string
     */
    public static String substitute(
        String source,
        Object... args) {

        if (args == null || args.length == 0) {
            return source;
        }

        String result = source;

        for (int i = 0; i < args.length; i++) {
            String key = String.valueOf(i);
            String value = String.valueOf(args[i]);
            String toBeReplaced = "{" + key + "}";
            while (result.contains(toBeReplaced)) {
                result = result.replace(toBeReplaced, value);
            }
        }

        return result;
    }


    /**
     * <blockquote><pre>
     *     Map<String,Object> props = ...;
     * 		 props.put("name","Edison");
     * 		 StringUtils.replace("hello ${name}!",props) => "Hello Edison!";
     * </pre></blockquote>
     *
     * @param source
     * @param map
     *
     * @return
     */
    public static String substitute(
        String source,
        Map<String, Object> map) {

        if (map == null || map.size() == 0) {
            return source;
        }

        return substitute(source, map, "${", "}");
    }

    /**
     * <blockquote><pre>
     *     Map<String,Object> props = ...;
     * 		 props.put("name","Edison");
     * 		 StringUtils.replace("hello {name}!",props) => "Hello Edison!";
     * </pre></blockquote>
     *
     * @param source
     * @param map
     *
     * @return
     */
    public static String substitute2(
        String source,
        Map<String, Object> map) {

        if (map == null || map.size() == 0) {
            return source;
        }

        return substitute(source, map, "{", "}");
    }

    public static String substitute(
        String source,
        Map<String, Object> map,
        String tokenPrefix,
        String tokenSuffix) {

        if (map == null || map.size() == 0) {
            return source;
        }

        String result = source;

        for (String key : map.keySet()) {
            String value = String.valueOf(map.get(key));
            String pattern = Pattern.quote(tokenPrefix) + key + "?" + Pattern.quote(tokenSuffix);
            result = result.replaceAll(pattern, value);
        }

        return result;
    }

    public static StringConcator concat(String string) {

        return new StringConcatorImpl(string, false);
    }

    public static StringConcator newline(String string) {

        return new StringConcatorImpl(string, true);
    }

    public static String trimLeft(String string) {

        if (string == null || string == "") {
            return string;
        }
        return string.replaceAll("^\\s+", "");
    }

    public static String trimRight(String string) {

        if (string == null || string == "") {
            return string;
        }
        return string.replaceAll("\\s+$", "");
    }

    public static interface StringConcator {
        StringConcator concat(String string);

        StringConcator newline(String string);

        StringConcator newline();

        String toString();
    }


    private static class StringConcatorImpl implements StringConcator {
        public StringConcatorImpl(
            String string,
            boolean newline) {

            this.builder = new StringBuilder();
            if (newline) {
                newline(string);
            } else {
                concat(string);
            }
        }

        private final StringBuilder builder;

        @Override
        public StringConcator concat(String string) {

            this.builder.append(string);
            return this;
        }

        public StringConcator newline() {

            this.builder.append(LINE_SEPARATOR);
            return this;
        }

        public StringConcator newline(String string) {

            this.builder.append(string);
            this.builder.append(LINE_SEPARATOR);
            return this;
        }

        @Override
        public String toString() {

            return this.builder.toString();
        }
    }

    public static String capitalize(String string) {

        return StringUtils.capitalize(string);
    }

    public static Boolean isEmpty(final CharSequence cs) {

        return StringUtils.isEmpty(cs);
    }

    public static String stripPrefix(
        String string,
        String prefix) {

        String result = string;
        if (!isEmpty(string) && !isEmpty(prefix) && string.startsWith(prefix)) {
            result = string.substring(prefix.length());
        }
        return result;
    }

    public static String stripSuffix(
        String string,
        String suffix) {

        String result = string;
        if (!isEmpty(string) && !isEmpty(suffix) && string.endsWith(suffix)) {
            result = string.substring(0, string.length() - suffix.length());
        }
        return result;
    }

    public static String stripFromFirstMatch(
        String string,
        String match) {

        String result = string;
        if (!isEmpty(string) && !isEmpty(match) && string.contains(match)) {
            result = string.substring(string.indexOf(match));
        }
        return result;
    }

    public static String stripFromFirstMatchWithoutMatch(
        String string,
        String match) {

        String result = string;
        if (!isEmpty(string) && !isEmpty(match) && string.contains(match)) {
            result = string.substring(string.indexOf(match) + match.length());
        }
        return result;
    }

    public static String stripFromLastMatch(
        String string,
        String match) {

        String result = string;
        if (!isEmpty(string) && !isEmpty(match) && string.contains(match)) {
            result = string.substring(string.lastIndexOf(match));
        }
        return result;
    }

    public static String stripFromLastMatchWithoutMatch(
        String string,
        String match) {

        String result = string;
        if (!isEmpty(string) && !isEmpty(match) && string.contains(match)) {
            result = string.substring(string.lastIndexOf(match)
                + match.length());
        }
        return result;
    }

    public static String stripToFirstMatch(
        String string,
        String match) {

        String result = string;
        if (!isEmpty(string) && !isEmpty(match) && string.contains(match)) {
            result = string.substring(0, string.indexOf(match) + 1);
        }
        return result;
    }

    public static String stripToFirstMatchWithMatch(
        String string,
        String match) {

        String result = string;
        if (!isEmpty(string) && !isEmpty(match) && string.contains(match)) {
            result = string
                .substring(0, string.indexOf(match) + match.length());
        }
        return result;
    }

    public static String stripToLastMatch(
        String string,
        String match) {

        String result = string;
        if (!isEmpty(string) && !isEmpty(match) && string.contains(match)) {
            result = string.substring(0, string.lastIndexOf(match) + 1);
        }
        return result;
    }

    public static String stripToLastMatchWithMatch(
        String string,
        String match) {

        String result = string;
        if (!isEmpty(string) && !isEmpty(match) && string.contains(match)) {
            result = string.substring(
                0,
                string.lastIndexOf(match) + match.length());
        }
        return result;
    }
}
