package org.writeo.utils.CommonConstants;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CommonConstants {
    public static final String ENTITY_NOT_FOUND = "Entity not found ";
    public static final String INVALID_REQUEST = "Invalid request ";
    public static final String DUPLICATE_ENTITY = "Entity already exists ";
    public static final String FOREIGN_KEY_CONSTRAINT_VIOLATION = "Foreign key constraint violated";
    public static final String UNAUTHORIZED_ACCESS = "Unauthorized access ";
    public static final String OPERATION_FAILED = "Operation failed ";
    public static final String VALIDATION_ERROR = "Validation error ";
    public static final String DATA_ACCESS_ERROR = "Data access error ";

    // Successful
    public final static String SUCCESSFUL_CODE = "00";

    public final static String SUCCESSFUL_MESSAGE = "Operation successful ";

    // System Errors
    public final static String SYSTEM_MALFUNCTION_CODE = "96";
    public final static String SYSTEM_MALFUNCTION_MESSAGE = "System malfunction ";

    // Input and Request Errors
    public final static String INVALID_INPUT_CODE = "10";
    public final static String INVALID_INPUT_MESSAGE = "Invalid input data ";

    public final static String INVALID_REQUEST_CODE = "11";
    public final static String INVALID_REQUEST_MESSAGE = "Invalid request ";

    public final static String INVALID_FORMAT_CODE = "12";
    public final static String INVALID_FORMAT_MESSAGE = "Invalid format";

    // Record Errors
    public final static String DUPLICATE_RECORD_CODE = "20";
    public final static String DUPLICATE_RECORD_MESSAGE = "Record already exists ";

    public final static String RECORD_NOT_FOUND_CODE = "21";
    public final static String RECORD_NOT_FOUND_MESSAGE = "Record not found ";

    // Authorization and Authentication Errors
    public final static String UNAUTHORIZED_CODE = "30";
    public final static String UNAUTHORIZED_MESSAGE = "Unauthorized access ";

    public final static String FORBIDDEN_CODE = "31";
    public final static String FORBIDDEN_MESSAGE = "Forbidden access ";

    // Validation Errors
    public final static String VALIDATION_ERROR_CODE = "40";
    public final static String VALIDATION_ERROR_MESSAGE = "Validation failed ";

    // Database Errors
    public final static String DATABASE_ERROR_CODE = "50";
    public final static String DATABASE_ERROR_MESSAGE = "Database error ";

    // Server Errors
    public final static String INTERNAL_SERVER_ERROR_CODE = "500";
    public final static String INTERNAL_SERVER_ERROR_MESSAGE = "Internal server error ";

    public final static String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public final static String TIME_FORMAT_HH_MM_SS = "HH:mm:ss";

    public static final DateTimeFormatter isoDateFormatter = DateTimeFormatter.ofPattern(CommonConstants.dateFormatInIso)
            .withLocale(Locale.ENGLISH);
    public static final DateTimeFormatter utcDateFormatter = DateTimeFormatter.ofPattern(CommonConstants.dateFormatInUtc)
            .withLocale(Locale.ENGLISH);

    public final static String STRING_NULL_CHAR = "\u0000";

    public static String dateFormatInUtc ="yyyy-MM-dd'T'HH:mm:ss.SSSX";
    public static String dateFormatInIso ="yyyy-MM-dd";

    public static final String modelFeatures = "modelFeatures";
    public static final String novelsTable = "Novels";
    public static final String novelIdPositiveMessage = "";
    public static final String volumesTable = "Volumes";
    public static final String volumeIdPositiveMessage = "";
    public static final String chaptersTable = "Chapters";
    public static final String notesTable = "Notes";
    public static final String noteIdPositiveMessage = "";
    public static final String charactersTable = "Characters";
    public static final String characteristicsTable = "Characteristics";
    public static final String userAccountTable = "UserAccount";
    public static final String userStatsTable = "UserStats";
    public static final String characterRelationsTable = "CharacterRelations";
    public static String features;
    public static final String srNoPositiveMessage = "Positive message";
    public static final String maxKeyCharacter = "Maximum characters allowed is 255";
    public static final String findAllModelFeaturesByModelCode="";
    public static final String findMaxModelFeatureId= "";
    public static final String findAllModelFeaturesByModelCodeAndFeatureId="";
    public static final String chapterIdPositiveMessage = "";
    public static final String userAuthTable = "UserAuth";
    public static final String writoNewsTable = "WritoNews";
    public static final String characterMentionsTable = "CharacterMentions";

    public static final String defaultMaxPageSize = "10";
    public static final String defaultCurrentPage = "0";

}