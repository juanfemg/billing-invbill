package co.com.juan.invbill.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Juan Felipe
 * 
 */
public class Utilities {

	private static final Logger log = LoggerFactory.getLogger(Utilities.class);

	private Utilities() {
		super();
	}

	public static boolean isNumeric(String word) {
		boolean ret = false;
		Pattern pat = Pattern.compile("[^0-9',.\\s]");
		Matcher mat = pat.matcher(word);
		if (!mat.find()) {
			ret = true;
		}
		return ret;
	}

	public static boolean isDate(String dateString) {
		boolean ret = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		try {
			sdf.parse(dateString);
			ret = true;
		} catch (ParseException e) {
			return ret;
		}
		return ret;
	}

	public static boolean isDecimal(String word) {
		boolean ret = false;
		Pattern pat = Pattern.compile("(\\d){1,8}\\.(\\d){0,2}");
		Matcher mat = pat.matcher(word);
		if (!mat.find()) {
			ret = true;
		}
		return ret;
	}

	public static boolean checkNumberAndCheckWithPrecisionAndScale(String fieldValue, Integer precision,
			Integer scale) {
		boolean ret = false;
		if (fieldValue != null && precision != null && scale != null) {
			if (fieldValue.contains("E") && scale != 0) {
				String dfString = "# # . # # # # # # # # # # # # # # # #";
				dfString = dfString.replace(" ", "");
				DecimalFormat df = new DecimalFormat(dfString);
				fieldValue = df.format(new Double(fieldValue));
				if (fieldValue.length() > 0 && !fieldValue.contains(".")) {
					fieldValue = fieldValue + ".0";
				}
			}
			fieldValue = fieldValue.replace(".", "%");
			String[] spitedFieldValue = fieldValue.split("%");
			ret = checkPrecisionAndScale(spitedFieldValue, precision, scale);
		}
		return ret;
	}

	public static boolean checkPrecisionAndScale(String[] spitedFieldValue, Integer precision, Integer scale) {
		boolean ret = false;
		if (spitedFieldValue.length == 2 && precision != 0) {
			String precisionTmp = spitedFieldValue[0];
			String scaleTmp = spitedFieldValue[1];
			if (!isNumeric(precisionTmp) || !isNumeric(scaleTmp)) {
				return false;
			}
			if ((precisionTmp.length() <= precision) && (scaleTmp.length() <= scale)) {
				ret = true;
			}
		} else if (spitedFieldValue.length == 1 && precision != 0 && scale == 0) {
			String precisionTmp = spitedFieldValue[0];
			if (!isNumeric(precisionTmp)) {
				return false;
			}
			if ((precisionTmp.length() <= precision)) {
				ret = true;
			}
		} else {
			return false;
		}

		return ret;
	}

	public static boolean checkWordAndCheckWithlength(String word, Integer length) {
		boolean ret = false;
		if (word.length() <= length) {
			ret = true;
		}
		return ret;
	}

	public static boolean isOnlyLetters(String word) {
		boolean ret = false;
		Pattern pat = Pattern.compile("[^A-Za-z0-9',.\\s]");
		Matcher mat = pat.matcher(word);
		if (!mat.find()) {
			ret = true;
		}
		return ret;
	}

	public static String formatDateWithoutTimeInAStringForBetweenWhere(Date fecha) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(fecha);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		return year + "-" + month + "-" + day;
	}

	public static boolean validationsList(List<?> list) {
		return (list != null && !list.isEmpty());
	}

	public static boolean validateEmailAddress(String sEmail) {
		EmailValidator emailValidator = EmailValidator.getInstance();
		return emailValidator.isValid(sEmail);
	}

	public static boolean matchClasses(Object object, Object object2, boolean privateFields) {
		boolean couldPerformTask = false;
		Object[] paramsObj = {};

		Class<? extends Object> tmpClass = object.getClass();
		Class<? extends Object> tmpClass2 = object2.getClass();

		Field[] field = tmpClass.getDeclaredFields();
		Field[] field2 = tmpClass2.getDeclaredFields();

		Method[] method = tmpClass.getMethods();
		Method[] method2 = tmpClass2.getMethods();

		if (privateFields) {
			couldPerformTask = matchClassesWithinPrivateFields(method, method2, object, object2, paramsObj,
					couldPerformTask);
		} else {
			couldPerformTask = matchClassesWithoutPrivateFields(field, field2, object, object2, couldPerformTask);
		}

		return couldPerformTask;
	}

	public static boolean matchClassesWithinPrivateFields(Method[] method, Method[] method2, Object object,
			Object object2, Object[] paramsObj, boolean couldPerformTask) {
		String tmpName;
		String tmpName2;
		Method tmpMethod;
		Method tmpMethod2;

		try {
			if (method != null) {

				for (int i = 0; i < method.length; i++) {
					tmpMethod = method[i];
					tmpMethod2 = method2[i];

					if (tmpMethod != null && tmpMethod2 != null) {
						tmpName = tmpMethod.getName().substring(0, 3);
						tmpName2 = tmpMethod2.getName().substring(0, 3);

						if (validateGetMethod(tmpName, tmpName2, tmpMethod, tmpMethod2, object, object2, paramsObj)) {
							couldPerformTask = true;
						} else {
							return false;
						}
					}
				}

			}
		} catch (Exception e) {
			log.error("An error has occurred: ", e);
		}

		return couldPerformTask;
	}

	public static boolean matchClassesWithoutPrivateFields(Field[] field, Field[] field2, Object object, Object object2,
			boolean couldPerformTask) {
		Object tmpInfo;
		Object tmpInfo2;
		Field tmpField;
		Field tmpField2;

		try {
			for (int j = 0; j < field.length; j++) {
				tmpField = field[j];
				tmpField2 = field2[j];

				if (tmpField != null && tmpField2 != null) {

					tmpInfo = tmpField.get(object);
					tmpInfo2 = tmpField2.get(object2);

					if (tmpInfo != null && tmpInfo2 != null) {
						if (performTask(tmpInfo, tmpInfo2)) {
							couldPerformTask = true;
						} else {
							return false;
						}
					}
				}
			}
		} catch (IllegalAccessException ea) {
			log.error(
					"One of the objects you are trying to compare has its fields private please use the method way. An error has occurred: ",
					ea);
		} catch (Exception e) {
			log.error("An error has occurred: ", e);
		}

		return couldPerformTask;
	}

	public static boolean performTask(Object tmpInfo, Object tmpInfo2) {
		try {
			return tmpInfo.equals(tmpInfo2);
		} catch (Exception e) {
			return tmpInfo == tmpInfo2;
		}
	}

	public static boolean validateGetMethod(String tmpName, String tmpName2, Method tmpMethod, Method tmpMethod2,
			Object object, Object object2, Object[] paramsObj)
			throws IllegalAccessException, InvocationTargetException {
		if (tmpName.equals("get") && tmpName2.equals("get")) {
			Object tmpInfo = tmpMethod.invoke(object, paramsObj);
			Object tmpInfo2 = tmpMethod2.invoke(object2, paramsObj);

			if (tmpInfo != null && tmpInfo2 != null && performTask(tmpInfo, tmpInfo2)) {
				return true;
			}
		}
		return false;
	}

	public static String constructCriteria(Object[] variables, Object[] variablesBetween,
			Object[] variablesBetweenDates) {
		String where;
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

		if (tempWhere.length() == 0) {
			where = null;
		} else {
			where = "(" + tempWhere + ")";
		}

		return where;
	}

	public static String buildCriteriaCondition(Object[] variables, String tempWhere, int type) {
		boolean flagVariable = false;
		for (int i = 0; i < variables.length; i = i + type) {
			for (int j = 0; j < type; j++) {
				if (variables[j] != null) {
					flagVariable = true;
				}
			}
			if (flagVariable && type == 4) {
				String variable = (String) variables[i];
				Boolean booVariable = (Boolean) variables[i + 1];
				Object value = variables[i + 2];
				String comparator = (String) variables[i + 3];
				tempWhere = setVariablesCriteriaCondition(tempWhere, variable, booVariable, value, comparator);
			} else if (flagVariable && type == 5) {
				String variable = (String) variables[i];
				Object value = variables[i + 1];
				Object value2 = variables[i + 2];
				String comparator1 = (String) variables[i + 3];
				String comparator2 = (String) variables[i + 4];
				tempWhere = setVariablesBetweenCriteriaCondition(tempWhere, variable, value, value2, comparator1,
						comparator2);
			} else if (flagVariable && type == 3) {
				String variable = (String) variables[i];
				Object object1 = variables[i + 1];
				Object object2 = variables[i + 2];
				String value = null;
				String value2 = null;
				Date date1 = (Date) object1;
				Date date2 = (Date) object2;
				value = formatDateWithoutTimeInAStringForBetweenWhere(date1);
				value2 = formatDateWithoutTimeInAStringForBetweenWhere(date2);
				tempWhere = setVariablesBetweenDatesCriteriaCondition(tempWhere, variable, value, value2);
			}
		}

		return tempWhere;
	}

	public static String setVariablesCriteriaCondition(String tempWhere, String variable, Boolean booVariable,
			Object value, String comparator) {
		String tempWhereBoo1 = "(model." + variable + " " + comparator;
		String tempWhereBoo2 = tempWhere + " AND (model." + variable + " " + comparator;

		if ((booVariable.booleanValue()) && (tempWhere.length() == 0)) {
			tempWhere = (tempWhereBoo1 + " \'" + value + "\' )");
		} else if ((booVariable.booleanValue()) && (tempWhere.length() != 0)) {
			tempWhere = (tempWhereBoo2 + " \'" + value + "\' )");
		} else if ((booVariable.booleanValue()) || (tempWhere.length() == 0)) {
			tempWhere = (tempWhereBoo1 + " " + value + " )");
		} else {
			tempWhere = (tempWhereBoo2 + " " + value + " )");
		}

		return tempWhere;
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
		return (tempWhere.length() == 0) ? ("(model." + variable + " between \'" + value + "\' and \'" + value2 + "\')")
				: (tempWhere + " AND (model." + variable + " between \'" + value + "\' and \'" + value2 + "\')");
	}

	public static byte[] readFully(InputStream input) throws IOException {
		byte[] buffer = new byte[8192];
		int bytesRead;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		while ((bytesRead = input.read(buffer)) != -1) {
			output.write(buffer, 0, bytesRead);
		}
		return output.toByteArray();
	}
}
