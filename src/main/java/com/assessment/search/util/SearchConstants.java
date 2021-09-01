package com.assessment.search.util;

import java.util.regex.Pattern;

public class SearchConstants {

    public static final Pattern SEARCH_QUERY_PATTERN = Pattern.compile("(OR-)?(N-|S-|D-|B-)(\\w+?)(=|<|>|<=|>=|#|!=|%)(\"([^\"]+)\")");
    public static final String EMPTY = "";
    public static final String DOUBLE_QUOTES = "\"";
    public static final String COMMA = ",";
    public static final String LIKE_PRE_POST = "%%%s%%";
    public static final String EQUALS = "=";
    public static final String L_T = "<";
    public static final String NOT_EQUALS = "!=";
    public static final String G_T = ">";
    public static final String L_T_EQUALS = "<=";
    public static final String G_T_EQUALS = ">=";
    public static final String IN = "#";
    public static final String LIKE = "%";
    public static final String NOT_LIKE = "!%";
    public static final String DEFAULT_PROP = "releaseYear";
    public static final int DEFAULT_PAGE_SIZE = 30;
    public static final int DEFAULT_PAGE_INDEX = 0;
}
