package application.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class UtilDate {
	public static final String DEFAULT_DATE_FORMAT = "MM/DD/YYYY";
	public static DateTimeFormatter MM_DD_YYYY_SLASH = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	public static DateTimeFormatter MM_DD_YYYY = DateTimeFormatter.ofPattern("MM-dd-yyyy");
	public static DateTimeFormatter YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final String FMTS_ACCEPTED = "The date string must be in yyyy/mm/dd or " + DEFAULT_DATE_FORMAT
			+ " format.";
	public static final int AGE_MISSING_DATE = -1;

	/**
	 * This formatter should be used to ensure consistency in date formatting within
	 * HILOB.
	 */
	public static DateTimeFormatter UTIL_DATE = MM_DD_YYYY_SLASH;
	// public static final int UNK = 0; // should never happen...
	public static final int VALID = 1;
	public static final int EMPTY = 2;
	public static final int ERROR = 3;

	private LocalDate localDate = null;
	private String dateString = null;
	private String fieldName = "";
	private int status;

	/**
	 * Give a java.sql.Timestamp, like updtTs from database, this returns
	 * 'MM-dd-yyyy h:m a' 10-05-2017 11:14 AM
	 * 
	 * @param t
	 * @return
	 */
	public static String timestamp2FormattedDateString(final Timestamp t) {
		Instant i = Instant.ofEpochMilli(t.getTime());
		LocalDateTime ldt = LocalDateTime.ofInstant(i, ZoneId.systemDefault());

		DateTimeFormatter dTF = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");
		String dStr = dTF.format(ldt);
		return dStr;
	}

	/**
	 * will return a timestamp for the start of the next day. example: 2018-02-05
	 * will return a time stamp value of 2018-02-06 00:00:00.0
	 * 
	 * @return
	 */
	public Timestamp startNextDayTimestamp() {
		if (this.localDate == null) {
			return null;
		}
		return Timestamp.valueOf(this.localDate.plusDays(1).atStartOfDay());
	}

	public static Timestamp startNextDayTimestamp(final LocalDate dateIn) {
		if (dateIn == null) {
			return null;
		}
		return Timestamp.valueOf(dateIn.plusDays(1).atStartOfDay());
	}

	public static Timestamp startNextDayTimestamp(final String dateIn) {
		UtilDate utilDate = new UtilDate(dateIn);
		if (utilDate.isValid()) {
			return Timestamp.valueOf(utilDate.getLocalDate().plusDays(1).atStartOfDay());
		}
		return null;
	}

	/**
	 * Creates an empty instance of a UtilDate. If you want today, use
	 * UtilDate.now()
	 */
	public UtilDate() {
		this.status = EMPTY;
	}

	/**
	 * Creates an instance of a UtilDate from a string. Handles null or no value in
	 * a consistent way.
	 * 
	 * @param dateString Leading zeros are required.
	 *                   {@value application.utils.UtilDate#FMTS_ACCEPTED}
	 * 
	 *                   If the dateString is null or a string with all whitespace,
	 *                   the localDate will be set to null and the dateString will
	 *                   be set to an empty string.
	 * 
	 *                   If dateString has a value, it will be parsed. If the string
	 *                   is invalid, isError() will be true and no other fields are
	 *                   valid.
	 * 
	 * @see isNull()
	 * @see isError()
	 */

	public UtilDate(final String dateString) {
		this.handleString(this, dateString, null);
	}

	public UtilDate(final String dateString, final String fieldName) {
		this.handleString(this, dateString, fieldName);
	}

	private void handleString(final UtilDate utilDate, final String dateString, final String fieldName) {
		// logger.debug("handleString dateString = " + dateString);
		try {
			if (!MiscUtils.hasValue(dateString)) { // no date provided...
				utilDate.localDate = null;
				utilDate.dateString = "";
				utilDate.fieldName = MiscUtils.getParmString(fieldName);
				utilDate.status = EMPTY;
			} else {
				utilDate.localDate = parse(dateString); // some date provided, if invalid an exception will be throw by
				// parse
				utilDate.dateString = this.fmt(UTIL_DATE);
				utilDate.fieldName = MiscUtils.getParmString(fieldName);
				utilDate.status = VALID;
			}
		} catch (Exception ex) {
			utilDate.localDate = null;
			utilDate.dateString = "";
			utilDate.fieldName = MiscUtils.getParmString(fieldName);
			utilDate.status = ERROR;
		}
		// logger.debug("handleString returning = " + BotUtils.objToJson(utilDate));
	}

	public UtilDate(final XMLGregorianCalendar xcal) {
		if (xcal == null) {
			this.localDate = null;
			this.dateString = "";
			this.status = EMPTY;
		} else {
			this.localDate = xcal.toGregorianCalendar().toZonedDateTime().toLocalDate();
			this.dateString = this.fmt(UTIL_DATE);
			this.status = VALID;
		}
	}

	public UtilDate(final LocalDate localDate) {
		if (localDate == null) {
			this.localDate = null;
			this.dateString = "";
			this.status = EMPTY;
		} else {
			this.localDate = localDate;
			this.dateString = this.fmt(UTIL_DATE);
			this.status = VALID;
		}
	}

	public UtilDate(final UtilDate dateIn) {
		if (dateIn == null) {
			this.localDate = null;
			this.dateString = "";
			this.status = EMPTY;
		} else {
			this.localDate = dateIn.localDate;
			this.dateString = this.fmt(UTIL_DATE);
			this.status = dateIn.status;
		}
	}

	public UtilDate(final Date dateIn) {
		if (dateIn == null) {
			this.localDate = null;
			this.dateString = "";
			this.status = EMPTY;
		} else {
			this.localDate = dateIn.toLocalDate();
			this.dateString = this.fmt(UTIL_DATE);
			this.status = VALID;
		}
	}

	public UtilDate(final Date dateIn, final String fieldName) {
		if (dateIn == null) {
			this.localDate = null;
			this.dateString = "";
			this.fieldName = "";
			this.status = EMPTY;
		} else {
			this.localDate = dateIn.toLocalDate();
			this.dateString = this.fmt(UTIL_DATE);
			this.fieldName = fieldName;
			this.status = VALID;
		}
	}

	public UtilDate(final Calendar cal) {
		if (cal == null) {
			this.localDate = null;
			this.dateString = "";
			this.status = EMPTY;
		} else {
			this.localDate = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1,
					cal.get(Calendar.DAY_OF_MONTH));
			this.dateString = this.fmt(UTIL_DATE);
			this.status = VALID;
		}
	}

	public static UtilDate now() {
		UtilDate date = new UtilDate(LocalDate.now());
		return date;
	}

	/**
	 * returns true if the date is less than (earlier) or equal to today. So, today
	 * is retro...
	 * 
	 * @return
	 */
	public boolean isRetro() {
		return !this.isSameOrAfter(UtilDate.now());
	}

	/**
	 * This is a convenience method to see if the localDate is null. Note this is
	 * also true when status is ERROR because the conversion failed... Use this when
	 * you do not care why it is null...
	 * 
	 * @return true is this UtilDate was created with a null or empty string or an
	 *         error occurred.
	 * @see isError()
	 * @see isValid()
	 */
	public boolean isNull() {
		if (this.localDate == null) {
			return true;
		}
		return false;
	}

	/**
	 * This is a convenience method to see if the localDate is present and no
	 * errors. It handles null and null values.
	 * 
	 * @return
	 * @see isError()
	 */
	public boolean isValid() {
		return this.isValid(!EMPTY_VALID);
	}

	/**
	 * boolean to indicate that an empty string is valid (the date was optional)
	 */
	public final static boolean EMPTY_VALID = true;

	/**
	 * This is a convenience method to see if there are no errors. It handles null
	 * and null values. This version has an option to consider and empty value as
	 * valid if emptyValid is true;<br>
	 * <br>
	 * Always us the constant EMPTY_VALID or !EMPTY_VALID for the paramter, do not
	 * use true/false
	 * 
	 * @return
	 * @see isError()
	 */
	public boolean isValid(final boolean emptyValid) {
		if (emptyValid) {
			if (this.localDate == null && this.status != ERROR) {
				return true;
			}
		}
		if (this.localDate != null && this.status == VALID) {
			return true;
		}
		return false;
	}

	/**
	 * This include isValid(), so no need to check for null, empty, etc.
	 * 
	 * @return
	 */
	public String isValidFirstOfMonth() {
		if (!this.isValid()) {
			return " is not a valid date";
		}
		if (!this.isFirstDayOfMonth()) {
			return " must be the first of the month";
		}
		return "";
	}

	public boolean isEmpty() {
		if (this.status == EMPTY) {
			return true;
		}
		return false;
	}

	public boolean isError() {
		if (this.status == ERROR) {
			return true;
		}
		return false;
	}

	public boolean isEqual(final UtilDate date) {
		// localDate.isEqual did not like null for the parm, so just use string values
		// logger.debug("UtilDate.isEqual this.toString = " + this.toString() + "
		// date.toString = " + date.toString());
		if (this.toString().equals(date.toString())) {
			return true;
		}
		return false;
	}

	public boolean isEqual(final Calendar calendar) {
		UtilDate date = new UtilDate(calendar);
		return this.isEqual(date);
	}

	public boolean isEqual(final Date sqlDate) {
		UtilDate date = new UtilDate(sqlDate);
		return this.isEqual(date);
	}

	public boolean isBefore(final UtilDate date) {
		if (this.isNull() || date == null) {
			return false;
		}
		return this.localDate.isBefore(date.getLocalDate());
	}

	public boolean isBefore(final Date date) {
		if (this.isNull() || date == null) {
			return false;
		}
		UtilDate utilDate = new UtilDate(date);
		return this.isBefore(utilDate);
	}

	public boolean isAfter(final UtilDate date) {
		if (this.isNull() || date == null || date.isNull()) {
			return false;
		}
		return this.localDate.isAfter(date.getLocalDate());
	}

	public boolean isAfter(final Date date) {
		if (this.isNull() || date == null) {
			return false;
		}
		UtilDate utilDate = new UtilDate(date);
		return this.isAfter(utilDate);
	}

	public boolean isSameOrAfter(final UtilDate date) {
		if (this.isNull() || date == null) {
			return false;
		}
		return (this.localDate.isAfter(date.getLocalDate()) || this.localDate.isEqual(date.getLocalDate()));
	}

	public boolean isSameOrAfter(final Calendar calendar) {
		if (this.isNull() || calendar == null) {
			return false;
		}
		UtilDate utilDate = new UtilDate(calendar);
		return this.isSameOrAfter(utilDate);
	}

	public boolean isSameOrAfter(final Date date) {
		if (this.isNull() || date == null) {
			return false;
		}
		UtilDate utilDate = new UtilDate(date);
		return this.isSameOrAfter(utilDate);
	}

	/**
	 * This method helps handle nulls
	 * 
	 * @param date
	 * @return
	 */
	public boolean isSameOrAfterWithNull(final UtilDate date) {
		UtilDate tempUtilDate = date == null ? new UtilDate() : date;
		if (!this.isValid() && !tempUtilDate.isValid()) {
			return true;
		}
		if (!this.isValid() && tempUtilDate.isValid()) {
			return true;
		}
		if (this.isValid() && !tempUtilDate.isValid()) {
			return false;
		}
		// at this point both dates are valid
		return this.isSameOrAfter(date);
	}

	public boolean isSameOrBefore(final UtilDate date) {
		if (this.isNull() || date == null || !date.isValid()) {
			return false;
		}

		return (this.localDate.isBefore(date.getLocalDate()) || this.localDate.isEqual(date.getLocalDate()));
	}

	public boolean isSameOrBefore(final Calendar calendar) {
		if (this.isNull() || calendar == null) {
			return false;
		}
		UtilDate utilDate = new UtilDate(calendar);
		return this.isSameOrBefore(utilDate);
	}

	public boolean isSameOrBefore(final Date date) {
		if (this.isNull() || date == null) {
			return false;
		}
		UtilDate utilDate = new UtilDate(date);
		return this.isSameOrBefore(utilDate);
	}

	public boolean isTodayOrAfter() {
		if (this.isNull()) {
			return false;
		}
		LocalDate today = LocalDate.now();
		return !this.localDate.isBefore(today);
	}

	public boolean isTodayOrBefore() {
		if (this.isNull()) {
			return false;
		}
		LocalDate today = LocalDate.now();
		return (this.localDate.isBefore(today) || this.localDate.isEqual(today));
	}

	public boolean isToday() {
		if (this.isNull()) {
			return false;
		}
		LocalDate today = LocalDate.now();
		return this.localDate.isEqual(today);
	}

	/**
	 * This includes isValid(), so no need to check for null, empty, etc. Empty
	 * value is considered as invalid, so value is required
	 * 
	 * @return
	 */
	public boolean isFirstDayOfMonth() {
		return this.isFirstDayOfMonth(!EMPTY_VALID);
	}

	/**
	 * Empty value is considered as valid if emptyValid is true. This includes
	 * isValid(), so no need to check for null, empty, etc.
	 * 
	 * @return
	 */
	public boolean isFirstDayOfMonth(final boolean emptyValid) {
		if (emptyValid && this.status == UtilDate.EMPTY) {
			return true;
		}
		// value required now, so ignore emptyValid
		if (!this.isValid()) {
			return false;
		}
		return (this.getLocalDate().getDayOfMonth() == 1);
	}

	/**
	 * This includes isValid(), so no need to check for null, empty, etc.
	 * 
	 * @return
	 */
	public boolean isLastDayOfMonth() {
		return this.isLastDayOfMonth(!EMPTY_VALID);
	}

	/**
	 * Empty value is considered as valid if emptyValid is true. This includes
	 * isValid(), so no need to check for null, empty, etc.
	 * 
	 * @return
	 */
	public boolean isLastDayOfMonth(final boolean emptyValid) {
		if (emptyValid && this.status == UtilDate.EMPTY) {
			return true;
		}
		// value required now, so ignore emptyValid
		if (!this.isValid()) {
			return false;
		}

		return this.getLocalDate().plusDays(1).getDayOfMonth() == 1;
	}

	public Date toSqlDate() {
		if (this.status == ERROR) {
			System.out.println("UtilDate.toSqlDate passed in invalid date forcing a null: " + this.toString());
			System.out.println("Most common error pattern is: new UtilDate(01/41/2020).toSqlDate() ");
			System.out.println("The error has been lost, the date should be validated prior to .toSqlDate()");
		}
		if (!this.isNull()) {
			java.util.Date date = java.sql.Date.valueOf(this.localDate);
			Date sqlDate = new java.sql.Date(date.getTime());
			return sqlDate;
		}
		return null;
	}

	public java.util.Date toUtilDate() {
		if (!this.isNull()) {
			java.util.Date date = java.sql.Date.valueOf(this.localDate);
			return date;
		}
		return null;
	}

	public Calendar toCalendar() {
		if (!this.isNull()) {
			return GregorianCalendar.from(this.localDate.atStartOfDay(ZoneId.systemDefault()));
		}
		return null;
	}

	public UtilDate plusDays(final int days) {
		return new UtilDate(this.localDate.plusDays(days));
	}

	public UtilDate plusMonths(final int months) {
		return new UtilDate(this.localDate.plusMonths(months));
	}

	public UtilDate plusYears(final int years) {
		return new UtilDate(this.localDate.plusYears(years));
	}

	public UtilDate minusDays(final int days) {
		return new UtilDate(this.localDate.minusDays(days));
	}

	public UtilDate minusMonths(final int months) {
		return new UtilDate(this.localDate.minusMonths(months));
	}

	public UtilDate minusYears(final int years) {
		return new UtilDate(this.localDate.minusYears(years));
	}

	public UtilDate withDayOfMonth(final int day) {
		return new UtilDate(this.localDate.withDayOfMonth(day));
	}

	/**
	 * Getter for the localDate attribute
	 */
	public LocalDate getLocalDate() {
		return this.localDate;
	}

	/**
	 * Setter for the localDate attribute
	 */
	public void setLocalDate(final LocalDate localDate) {
		this.localDate = localDate;
		this.dateString = this.fmt(UTIL_DATE);
	}

	/**
	 * Getter for the String formatted attribute, may be empty string.
	 * 
	 * Note: there is no setter for the dateString, it is read only.
	 */
	public String getDateString() {
		if (this.dateString == null) {
			if (this.localDate == null) {
				this.dateString = "";
			} else {
				this.dateString = this.localDate.toString();
			}
		}
		return this.dateString;
	}

	public void setDateString(final String dateString) {
		this.dateString = dateString;
	}

	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(final String pFieldName) {
		this.fieldName = pFieldName;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(final int pStatus) {
		this.status = pStatus;
	}

	/**
	 * This will format the locaDate using the formatter provided.
	 * 
	 * @param formatter any valid DateTimeFormatter
	 * @return
	 */
	public String fmt(final DateTimeFormatter formatter) {
		if (this.localDate == null) {
			return "";
		}
		return this.localDate.format(formatter);
	}

	/**
	 * In health insurance, we often need the start of the month for an existing
	 * date and this is it...
	 * 
	 * @return a LocalDate for the 1st day of the next month.
	 */
	public LocalDate startOfNextMonth() {
		// push to last day of month
		LocalDate retVal = this.localDate.with(TemporalAdjusters.lastDayOfMonth());
		// push to first date of next month
		retVal = retVal.plusDays(1);
		return retVal;
	}

	public UtilDate endOfCurrentMonth() {
		return new UtilDate(this.localDate.with(TemporalAdjusters.lastDayOfMonth()));
	}

	public UtilDate startOfNextMonthUtilDate() {
		// push to last day of month
		LocalDate retVal = this.localDate.with(TemporalAdjusters.lastDayOfMonth());
		// push to first date of next month
		retVal = retVal.plusDays(1);
		return new UtilDate(retVal);
	}

	public UtilDate startOfPrevMonthUtilDate() {
		// push to last day of month
		LocalDate retVal = this.localDate.with(TemporalAdjusters.firstDayOfMonth());
		// push to first date of next month
		retVal = retVal.minusMonths(1);
		return new UtilDate(retVal);
	}

	public LocalDate endOfPrevMonth() {
		LocalDate retVal = this.localDate.plusMonths(-1);
		retVal = retVal.with(TemporalAdjusters.lastDayOfMonth());
		// push to first date of next month
		return retVal;
	}

	public UtilDate endOfPrevMonthUtilDate() {
		return new UtilDate(this.endOfPrevMonth());
	}

	/**
	 * Evaluates if a date is within the range specified. The range is inclusive, so
	 * if the localDate is equal to the startDate or EndDate, it will return true.
	 * 
	 * @param startDate must be in a valid format indicated in the constructor or
	 *                  parse() method.
	 * @param endDate   must be in a valid format indicated in the constructor or
	 *                  parse() method.
	 * @return returns true is the date is within the inclusive range.
	 */
	public boolean isDateInRange(final String startDate, final String endDate) {
		if (startDate == null || startDate.trim().length() == 0 || endDate == null || endDate.trim().length() == 0) {
			// log this coding error...
			return false;
		}
		return this.isDateInRange(parse(startDate), parse(endDate));
	}

	public boolean isDateInRange(final LocalDate startDate, final LocalDate endDate) {
		return !(this.localDate.isBefore(startDate) || this.localDate.isAfter(endDate));
	}

	public boolean isDateInRange(final UtilDate startDate, final UtilDate endDate) {
		// ensure we have the fields we need...
		if (this.isNull() || startDate == null || startDate.isNull()) {
			return false;
		}
		if (this.localDate.isBefore(startDate.localDate)) {
			return false;
		}
		// if we have an endDate...
		if (endDate != null && !endDate.isNull()) {
			if (this.localDate.isAfter(endDate.localDate)) {
				return false;
			}
		}
		return true;
	}

	public Period getIntervalPeriod(final UtilDate endDate) {
		if (!endDate.isValid()) {
			throw new IllegalArgumentException(
					"UtilDate.getIntervalPeriod Error. Invalid end date provided: " + endDate);
		}

		return this.getIntervalPeriod(this.getLocalDate(), endDate.getLocalDate());
	}

	public Period getIntervalPeriod(final LocalDate startDate, final LocalDate endDate) {
		return Period.between(startDate, endDate);
	}

	public int getIntervalInMonths(final UtilDate endDate) {
		if (!endDate.isValid()) {
			throw new IllegalArgumentException(
					"UtilDate.getIntervalInMonths Error. Invalid end date provided: " + endDate);
		}

		return this.getIntervalInMonths(this.getLocalDate(), endDate.getLocalDate());
	}

	/**
	 * Like getIntervalInMonths, but will not allow 0 months as an answer. For
	 * example the period between Jan 1 - Jan 31 is still considered 1 month.
	 * WARNING: Jan 1-Jan 2 will also be considered 1 month.
	 */
	public int getIntervalInMonthsInclusive(final UtilDate endDate) {
		if (!endDate.isValid()) {
			throw new IllegalArgumentException(
					"UtilDate.getIntervalInMonths Error. Invalid end date provided: " + endDate);
		}

		return this.getIntervalInMonthsInclusive(this.getLocalDate(), endDate.getLocalDate());
	}

	public int getIntervalInMonthsInclusive(final LocalDate startDate, final LocalDate endDate) {
		return ((int) ChronoUnit.MONTHS.between(startDate, endDate) + 1);
	}

	public int getIntervalInMonths(final LocalDate startDate, final LocalDate endDate) {
		return (int) ChronoUnit.MONTHS.between(startDate, endDate);
	}

	public int getIntervalInDays(final UtilDate endDate) {
		return (int) ChronoUnit.DAYS.between(this.localDate, endDate.localDate);
	}

	/**
	 * Should be used to ensure consistent date formatting.
	 * 
	 * @return a string version of the localDate in the default HILOB format.
	 */
	@Override
	public String toString() {
		if (this.dateString == null) {
			if (this.localDate == null) {
				this.dateString = "";
			} else {
				this.dateString = this.fmt(UTIL_DATE);
			}
		}
		return this.dateString;
	}

	/**
	 * Converts this UtilDate to an XMLGregorianCalendar object
	 * 
	 * @return returns XMLGregorianCalendar object or null if this isNull() or error
	 *         on conversion
	 */
	public XMLGregorianCalendar toXMLGregorianCalendar() {
		if (this.localDate == null) {
			return null;
		}
		try {
			XMLGregorianCalendar xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(this.localDate.toString());
			return xgc;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * Will compute age based on the value of this UtilDate field and the asOfDate
	 * passed in.
	 * 
	 * @param asOfDate if null or isNull() is true, it will compute date as of
	 *                 today, otherwise as of date provided.
	 * @return the number of complete years between this date and the asOfDate. It
	 *         will always be positive and never return negative years since age
	 *         cannot be negative.
	 */
	public int getAge(UtilDate asOfDate) {
		if (asOfDate == null || asOfDate.isNull()) {
			asOfDate = UtilDate.now();
		}
		if (this.localDate == null) {
			return AGE_MISSING_DATE;
		}
		int diff = Period.between(this.localDate, asOfDate.getLocalDate()).getYears();
		// logger.debug("getAge asOfDate=" + asOfDate + " age=" + Math.abs(diff));
		return Math.abs(diff);
	}

	/**
	 * Get age as of today...
	 * 
	 * @return
	 */
	public int getAge() {
		return this.getAge(null);
	}

	/************************************************************************
	 * static utility classes
	 ************************************************************************/

	/**
	 * Parses a date string and returns a LocalDate. If you want a UtilDate, use the
	 * constructor.
	 * 
	 * @param dateIn {@value application.utils.UtilDate#FMTS_ACCEPTED}
	 * @return a LocalDate is parse() is successful, otherwise an Exception is
	 *         thrown.
	 */
	public static LocalDate parse(String dateIn) {
		// just in case it is mm/dd/yyyy
		dateIn = dateIn.trim().replace("/", "-");
		// handle 2017-03-04T00:00:00-05:00
		if (dateIn.length() > 10) {
			dateIn = dateIn.substring(0, 10);
		}
		LocalDate date = null;
		try {
			date = LocalDate.parse(dateIn, MM_DD_YYYY);
			return date;
		} catch (Exception ex) {
		}
		try {
			date = LocalDate.parse(dateIn, YYYY_MM_DD); // sql dates are in this format...
			return date;
		} catch (Exception ex) {
		}
		throw new IllegalArgumentException(FMTS_ACCEPTED + " Value provided: " + dateIn);
	}

	// /**
	// * Returns the number of years between two dates. If the second date is
	// * empty or null, it will be set to the current date. Example: for current
	// * age pass date of birth for the first date and null for the second.
	// *
	// * Note that "01-01-2016", "12-31-2016" is one day short of a year! But it
	// * handles leap year, "02-28-2015", "02-29-2016" is one year...
	// *
	// * @param firstDate
	// * first date in mm-dd-yyyy or yyyy-mm-dd format. If empty or
	// * null, 0 will be returned.
	// * @param secondDate
	// * second date, if empty or null will use current date
	// * @param absolute
	// * returns the absolute difference if true (never negative)
	// * @return the complete years between two dates, may be negative if absolute
	// * is not true. DateTimeParseException if not valid format (zero
	// * filled, 1/1/2016 is NOT valid) IllegalArgumentException if
	// * missing firstDate
	// */

	public static String getTodayString() {
		LocalDate now = LocalDate.now();
		return now.format(UTIL_DATE);
	}

	public static UtilDate getmaxDate() {
		// just in case it is mm/dd/yyyy

		LocalDate date = null;
		try {
			date = LocalDate.parse("09/30/2999", MM_DD_YYYY_SLASH);
			return new UtilDate(date);
		} catch (Exception ex) {
		}

		throw new IllegalArgumentException(FMTS_ACCEPTED + " Value provided: ");


	}
}
