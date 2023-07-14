package co.com.juan.invbill.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Juan Felipe
 */
public class Utilities {

    private static final Logger log = LoggerFactory.getLogger(Utilities.class);
    private static final String PATTERN_DATE = "yyyy-MM-dd";
    private static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    private static final String PATTERN_DECIMAL = "##.################";
    private static final String REGEX_NUMERIC = "-?\\d+(\\.\\d+)?";

    private Utilities() {
        super();
    }

    public static boolean isNumeric(String word) {
        Pattern pattern = Pattern.compile(REGEX_NUMERIC);
        return pattern.matcher(word).matches();
    }

    public static boolean isDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_DATE);
        sdf.setLenient(false);
        try {
            sdf.parse(dateString);
            return true;
        } catch (ParseException e) {
            log.error("an error has been reached unexpectedly while parsing {}", dateString);
            return false;
        }
    }

    public static boolean checkNumberAndCheckWithPrecisionAndScale(String fieldValue, Integer precision,
                                                                   Integer scale) {
        if (fieldValue != null && precision != null && scale != null) {
            try {
                if (fieldValue.contains("E") && scale != 0) {
                    DecimalFormat df = new DecimalFormat(PATTERN_DECIMAL);
                    fieldValue = df.format(Double.valueOf(fieldValue));
                    if (fieldValue.length() > 0 && !fieldValue.contains(".")) {
                        fieldValue = fieldValue + ".0";
                    }
                }
                String[] spitedFieldValue = fieldValue.replace(".", "%").split("%");
                return checkPrecisionAndScale(spitedFieldValue, precision, scale);
            } catch (IllegalArgumentException iae) {
                log.error("the Format cannot format the given object");
            }
        }
        return false;
    }

    public static boolean checkPrecisionAndScale(String[] spitedFieldValue, Integer precision, Integer scale) {
        if (spitedFieldValue.length == 2 && precision != 0) {
            String precisionTmp = spitedFieldValue[0];
            String scaleTmp = spitedFieldValue[1];
            return (isNumeric(precisionTmp) && precisionTmp.length() <= precision) && (isNumeric(scaleTmp) && scaleTmp.length() <= scale);
        } else if (spitedFieldValue.length == 1 && precision != 0 && scale == 0) {
            String precisionTmp = spitedFieldValue[0];
            if (!isNumeric(precisionTmp)) {
                return false;
            }
            return (isNumeric(precisionTmp) && precisionTmp.length() <= precision);
        }
        return false;
    }

    public static boolean checkWordAndCheckWithLength(String word, Integer length) {
        return word.length() <= length;
    }

    public static String formatDateWithoutTimeInAStringForBetweenWhere(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_DATE_TIME);
        return dateTime.format(formatter);
    }

    public static String constructCriteria(Object[] variables, Object[] variablesBetween,
                                           Object[] variablesBetweenDates) {
        String tempWhere = "";

        if (variables != null) {
            tempWhere = buildCriteriaCondition(variables, tempWhere, 4);
        }

        if (variablesBetween != null) {
            tempWhere = buildCriteriaCondition(variablesBetween, tempWhere, 5);
        }

        if (variablesBetweenDates != null) {
            tempWhere = buildCriteriaCondition(variablesBetweenDates, tempWhere, 3);
        }

        return tempWhere.length() == 0 ? null : "(" + tempWhere + ")";
    }

    public static String buildCriteriaCondition(Object[] variables, String tempWhere, int type) {
        boolean flagVariable = false;
        for (int i = 0; i < variables.length; i = i + type) {
            for (int j = 0; j < type; j++) {
                if (variables[j] != null) {
                    flagVariable = true;
                    break;
                }
            }
            if (flagVariable) {
                String variable = (String) variables[i];
                Object object2;
                String comparator1, comparator2;
                switch (type) {
                    case 3:
                        LocalDateTime date1 = (LocalDateTime) variables[i + 1];
                        LocalDateTime date2 = (LocalDateTime) variables[i + 2];
                        String value = formatDateWithoutTimeInAStringForBetweenWhere(date1);
                        String value2 = formatDateWithoutTimeInAStringForBetweenWhere(date2);
                        tempWhere = setVariablesBetweenDatesCriteriaCondition(tempWhere, variable, value, value2);
                        break;
                    case 4:
                        Boolean booVariable = (Boolean) variables[i + 1];
                        object2 = variables[i + 2];
                        comparator1 = (String) variables[i + 3];
                        tempWhere = setVariablesCriteriaCondition(tempWhere, variable, booVariable, object2, comparator1);
                        break;
                    case 5:
                        Object object1 = variables[i + 1];
                        object2 = variables[i + 2];
                        comparator1 = (String) variables[i + 3];
                        comparator2 = (String) variables[i + 4];
                        tempWhere = setVariablesBetweenCriteriaCondition(tempWhere, variable, object1, object2, comparator1,
                                comparator2);
                        break;
                    default:
                        break;
                }
            }
        }

        return tempWhere;
    }

    public static String setVariablesCriteriaCondition(String tempWhere, String variable, Boolean booVariable,
                                                       Object value, String comparator) {
        String tempWhereBoo1 = "(model." + variable + " " + comparator;
        String tempWhereBoo2 = tempWhere + " AND (model." + variable + " " + comparator;

        if ((booVariable) && (tempWhere.length() != 0)) {
            return (tempWhereBoo2 + " ('" + value + "') )");
        } else if (booVariable) {
            return (tempWhereBoo1 + " ('" + value + "') )");
        }

        return (tempWhereBoo2 + " (" + value + ") )");
    }

    public static String setVariablesBetweenCriteriaCondition(String tempWhere, String variable, Object value,
                                                              Object value2, String comparator1, String comparator2) {
        return (tempWhere.length() == 0)
                ? ("(" + value + " " + comparator1 + " " + variable + " and " + variable + " " + comparator2 + " "
                + value2 + " )")
                : (tempWhere + " AND (" + value + " " + comparator1 + " " + variable + " and " + variable + " "
                + comparator2 + " " + value2 + " )");
    }

    public static String setVariablesBetweenDatesCriteriaCondition(String tempWhere, String variable, String value,
                                                                   String value2) {
        return (tempWhere.length() == 0) ? ("(model." + variable + " between '" + value + "' and '" + value2 + "')")
                : (tempWhere + " AND (model." + variable + " between '" + value + "' and '" + value2 + "')");
    }

    public static List<Object> constructVariablesCriteriaCondition(String variable, Object value) {
        return (value != null && !value.toString().isEmpty()) ? Arrays.asList(variable, true, value, "=") : Collections.emptyList();
    }

    public static List<Object> constructVariablesBetweenDatesCriteriaCondition(String variable, Object value) {
        if (value != null) {
            LocalDate localDate = convertToLocalDate((Date) value);
            LocalDateTime startOfDay = LocalDateTime.of(localDate, LocalTime.MIDNIGHT);
            LocalDateTime endOfDay = localDate.atTime(LocalTime.MAX);
            return Arrays.asList(variable, startOfDay, endOfDay);
        }
        return Collections.emptyList();
    }

    public static LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

}
