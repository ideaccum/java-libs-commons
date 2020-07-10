package org.ideaccum.libs.commons.util;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日時情報を管理するインタフェースを提供します。<br>
 * <p>
 * このクラスは{@link java.util.Date}は継承せず、クラス内で独自に日時情報を管理します。<br>
 * {@link java.util.Date}と異なり1970年1月1日午前0時を基準としたミリ秒管理はなく、それ以前の日時情報も管理します。<br>
 * </p>
 * 
 *<!--
 * 更新日      更新者           更新内容
 * 2019/04/26  Kitagawa         新規作成
 *-->
 */
public class Datetime implements Serializable, Cloneable, Comparable<Datetime> {

	/** 年 */
	private int year;

	/** 月 */
	private int month;

	/** 日 */
	private int day;

	/** 時 */
	private int hour;

	/** 分 */
	private int minute;

	/** 秒 */
	private int second;

	/** ナノ秒 */
	private int nanos;

	/**
	 * コンストラクタ<br>
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @param hour 時
	 * @param minute 分
	 * @param second 秒
	 * @param nanos ナノ秒
	 */
	public Datetime(int year, int month, int day, int hour, int minute, int second, int nanos) {
		setDatetime(year, month, day, hour, minute, second, nanos);
	}

	/**
	* コンストラクタ<br>
	* @param year 年
	* @param month 月
	* @param day 日
	* @param hour 時
	* @param minute 分
	* @param second 秒
	*/
	public Datetime(int year, int month, int day, int hour, int minute, int second) {
		setDatetime(year, month, day, hour, minute, second, 0);
	}

	/**
	* コンストラクタ<br>
	* @param year 年
	* @param month 月
	* @param day 日
	*/
	public Datetime(int year, int month, int day) {
		setDatetime(year, month, day, 0, 0, 0, 0);
	}

	/**
	* コンストラクタ<br>
	* @param calendar カレンダーオブジェクト
	* @param nanos ナノ秒
	*/
	public Datetime(Calendar calendar, int nanos) {
		setDatetime(calendar, nanos);
	}

	/**
	* コンストラクタ<br>
	* @param calendar カレンダーオブジェクト
	*/
	public Datetime(Calendar calendar) {
		setDatetime(calendar);
	}

	/**
	* コンストラクタ<br>
	* @param timestamp 時刻オブジェクト
	*/
	public Datetime(Timestamp timestamp) {
		setDatetime(timestamp);
	}

	/**
	* コンストラクタ<br>
	* @param date 日付オブジェクト
	*/
	public Datetime(Date date) {
		setDatetime(date);
	}

	/**
	 * コンストラクタ<br>
	 */
	public Datetime() {
		setDatetime(new Date());
	}

	/**
	 * 日時情報を設定します。<br>
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @param hour 時
	 * @param minute 分
	 * @param second 秒
	 * @param nanos ナノ秒
	 */
	public void setDatetime(int year, int month, int day, int hour, int minute, int second, int nanos) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
		this.nanos = nanos;
		validate();
	}

	/**
	 * 日時情報を設定します。<br>
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @param hour 時
	 * @param minute 分
	 * @param second 秒
	 */
	public void setDatetime(int year, int month, int day, int hour, int minute, int second) {
		setDatetime(year, month, day, hour, minute, second, 0);
	}

	/**
	 * 日時情報を設定します。<br>
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 */
	public void setDatetime(int year, int month, int day) {
		setDatetime(year, month, day, 0, 0, 0, 0);
	}

	/**
	 * 日時情報を設定します。<br>
	 * @param calendar カレンダーオブジェクト
	 * @param nanos ナノ秒
	 */
	public void setDatetime(Calendar calendar, int nanos) {
		if (calendar == null) {
			setDatetime(0, 0, 0, 0, 0, 0, nanos);
		} else {
			setDatetime(calendar.get(Calendar.YEAR) //
					, calendar.get(Calendar.MONTH) + 1 //
					, calendar.get(Calendar.DAY_OF_MONTH) //
					, calendar.get(Calendar.HOUR_OF_DAY) //
					, calendar.get(Calendar.MINUTE) //
					, calendar.get(Calendar.SECOND) //
					, nanos //
			);
		}
	}

	/**
	 * 日時情報を設定します。<br>
	 * @param calendar カレンダーオブジェクト
	 */
	public void setDatetime(Calendar calendar) {
		if (calendar == null) {
			setDatetime(0, 0, 0, 0, 0, 0, 0);
		} else {
			setDatetime(calendar.get(Calendar.YEAR) //
					, calendar.get(Calendar.MONTH) + 1 //
					, calendar.get(Calendar.DAY_OF_MONTH) //
					, calendar.get(Calendar.HOUR_OF_DAY) //
					, calendar.get(Calendar.MINUTE) //
					, calendar.get(Calendar.SECOND) //
					, 0 //
			);
		}
	}

	/**
	 * 日時情報を設定します。<br>
	 * @param timestamp 時刻オブジェクト
	 */
	public void setDatetime(Timestamp timestamp) {
		if (timestamp == null) {
			setDatetime(0, 0, 0, 0, 0, 0, 0);
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(timestamp);
			setDatetime(calendar.get(Calendar.YEAR) //
					, calendar.get(Calendar.MONTH) + 1 //
					, calendar.get(Calendar.DAY_OF_MONTH) //
					, calendar.get(Calendar.HOUR_OF_DAY) //
					, calendar.get(Calendar.MINUTE) //
					, calendar.get(Calendar.SECOND) //
					, timestamp.getNanos() //
			);
		}
	}

	/**
	 * 日時情報を設定します。<br>
	 * @param date 日付オブジェクト
	 */
	public void setDatetime(Date date) {
		if (date == null) {
			setDatetime(0, 0, 0, 0, 0, 0, 0);
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			setDatetime(calendar.get(Calendar.YEAR) //
					, calendar.get(Calendar.MONTH) + 1 //
					, calendar.get(Calendar.DAY_OF_MONTH) //
					, calendar.get(Calendar.HOUR_OF_DAY) //
					, calendar.get(Calendar.MINUTE) //
					, calendar.get(Calendar.SECOND) //
					, 0 //
			);
		}
	}

	/**
	 * 指定された年月の末日を取得します。<br>
	 * @param year 年
	 * @param month 月
	 * @return 年月の末日(31、30、28、29)
	 */
	public static int getLastDay(int year, int month) {
		while (month <= 0) {
			month += 12;
		}
		while (month > 12) {
			month -= 12;
		}
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			return 31;
		} else if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		} else {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
	}

	/**
	 * 指定された年がうるう年であるか判定します。<br>
	 * @return うるう年であるである場合にtrueを返却
	 */
	public static boolean isLeapYear(int year) {
		if (year % 4 == 0) {
			if ((year % 100) == 0) {
				if ((year % 400) == 0) {
					return true;
				}
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * 管理されている日時情報の年がうるう年であるか判定します。<br>
	 * @return うるう年であるである場合にtrueを返却
	 */
	public boolean isLeapYear() {
		return isLeapYear(year);
	}

	/**
	 * 管理されている日時情報をカレンダーオブジェクトとして提供します。<br>
	 * @return カレンダーオブジェクト
	 */
	public Calendar toCalendar() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day, hour, minute, second);
		return calendar;
	}

	/**
	 * 管理されている日時情報を日付オブジェクトとして提供します。<br>
	 * @return 日付オブジェクト
	 */
	public Timestamp toTimestamp() {
		Calendar calendar = toCalendar();
		Timestamp timestamp = new Timestamp(calendar.getTime().getTime());
		timestamp.setNanos(nanos);
		return timestamp;
	}

	/**
	 * 管理されている日時情報をエポックミリ秒として提供します。<br>
	 * @return エポックミリ秒
	 */
	public long toTime() {
		return toTimestamp().getTime();
	}

	/**
	 * 管理されている日時情報を日付オブジェクトとして提供します。<br>
	 * @return 日付オブジェクト
	 */
	public Date toDate() {
		return new Date(toTime());
	}

	/**
	 * オブジェクト情報を文字列として提供します。<br>
	 * @param pattern 日付書式文字列
	 * @return オブジェクト情報文字列
	 */
	public String toString(String pattern) {
		return new SimpleDateFormat(pattern).format(toTimestamp());
	}

	/**
	 * オブジェクト情報を文字列として提供します。<br>
	 * @return オブジェクト情報文字列
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return toString("yyyy/MM/dd HH:mm:ss.") + new DecimalFormat("0000000000").format(nanos);
	}

	/**
	 * 日時情報同士を比較して大小比較結果を返却します。<br>
	 * 比較対象は年月日時分秒及び、ナノ秒までの比較が行われます。<br>
	 * @param compare 比較対象日時情報
	 * @return 比較対象日時情報が自身よりも小さい場合に正数を返却、逆の場合は負数、同値の場合は0を返却します
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Datetime compare) {
		if (compare == null) {
			return 1;
		}
		if (year - compare.year > 0) {
			return 1;
		} else if (year - compare.year < 0) {
			return -1;
		}
		if (month - compare.month > 0) {
			return 1;
		} else if (month - compare.month < 0) {
			return -1;
		}
		if (day - compare.day > 0) {
			return 1;
		} else if (day - compare.day < 0) {
			return -1;
		}
		if (hour - compare.hour > 0) {
			return 1;
		} else if (hour - compare.hour < 0) {
			return -1;
		}
		if (minute - compare.minute > 0) {
			return 1;
		} else if (minute - compare.minute < 0) {
			return -1;
		}
		if (second - compare.second > 0) {
			return 1;
		} else if (second - compare.second < 0) {
			return -1;
		}
		if (nanos - compare.nanos > 0) {
			return 1;
		} else if (nanos - compare.nanos < 0) {
			return -1;
		}
		return 0;
	}

	/**
	 * 管理されている日時情報を持つクローンされた別インスタンスを提供します。<br>
	 * @return クローンされた日時情報
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Datetime clone() {
		Datetime clone = new Datetime(year, month, day, hour, minute, second, nanos);
		return clone;
	}

	/**
	 * 管理されている日時情報を指定された日時分シフトしたクローンされた別インスタンスを提供します。<br>
	 * @param year シフト年数
	 * @param month シフト月数
	 * @param day シフト日数
	 * @param hour シフト時間数
	 * @param minute シフト分数
	 * @param second シフト秒数
	 * @param nanos シフトナノ秒数
	 * @return シフトされたクローン日時情報インスタンス
	 */
	public Datetime offset(int year, int month, int day, int hour, int minute, int second, int nanos) {
		Datetime clone = clone();
		clone.setDatetime(this.year + year, this.month + month, this.day + day, this.hour + hour, this.minute + minute, this.second + second, this.nanos + nanos);
		return clone;
	}

	/**
	 * 年を加算します。<br>
	 * @param year 加算年数
	 */
	public void addYear(int year) {
		year += year;
		//validate();
	}

	/**
	 * 月を加算します。<br>
	 * @param month 加算月数
	 */
	public void addMonth(int month) {
		month += month;
		validate();
	}

	/**
	 * 日を加算します。<br>
	 * @param day 加算日数
	 */
	public void addDay(int day) {
		day += day;
		validate();
	}

	/**
	 * 時間を加算します。<br>
	 * @param hour 加算時間数
	 */
	public void addHour(int hour) {
		hour += hour;
		validate();
	}

	/**
	 * 分を加算します。<br>
	 * @param minute 加算分数
	 */
	public void addMinute(int minute) {
		minute += minute;
		validate();
	}

	/**
	 * 秒を加算します。<br>
	 * @param second 加算秒数
	 */
	public void addSecond(int second) {
		second += second;
		validate();
	}

	/**
	 * ナノ秒を加算します。<br>
	 * @param nanos 加算ナノ秒数
	 */
	public void addNanos(int nanos) {
		nanos += nanos;
		validate();
	}

	/**
	 * マイクロ秒を加算します。<br>
	 * @param micros 加算マイクロ秒数
	 */
	public void addMicros(int micros) {
		nanos += micros * 1000;
		validate();
	}

	/**
	 * ミリ秒を加算します。<br>
	 * @param millis 加算ミリ秒数
	 */
	public void addMillis(int millis) {
		nanos += millis * 1000 * 1000;
		validate();
	}

	/**
	 * 週を加算します。<br>
	 * @param week 加算週数
	 */
	public void addWeek(int week) {
		day += week * 7;
		validate();
	}

	/**
	 * 管理されている年月の末日を取得します。<br>
	 * @return 年月の末日(31、30、28、29)
	 */
	public int getLastDay() {
		return getLastDay(year, month);
	}

	/**
	 * 曜日情報を取得します。<br>
	 * @return 曜日
	 */
	public DayOfWeek getDayOfWeek() {
		Calendar calendar = toCalendar();
		return DayOfWeek.getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));
	}

	/**
	 * 和暦元号を取得します。<br>
	 * @return 和暦元号
	 */
	public JapaneseEraNames getJapaneseEra() {
		Date date = toDate();
		JapaneseEraNames yearType = JapaneseEraNames.match(date);
		if (yearType == null) {
			throw new IllegalArgumentException("Cannot treat " + date);
		}
		return yearType;
	}

	/**
	 * 和暦元号名称を取得します。<br>
	 * @return 和暦元号名称
	 */
	public String getJapaneseEraName() {
		return getJapaneseEra().getName();
	}

	/**
	 * 和暦年を取得します。<br>
	 * @return 和暦年
	 */
	public int getJapaneseYear() {
		Date date = toDate();
		JapaneseEraNames yearType = JapaneseEraNames.match(date);
		if (yearType == null) {
			throw new IllegalArgumentException("Cannot treat " + date);
		}
		return getYear() - yearType.getStartYear() + 1;
	}

	/**
	 * フィールドに保持されている日時情報を妥当な年月日時分秒ナノ秒に補正します。<br>
	 */
	private void validate() {
		if (validateMonth()) {
			validate();
		}
		if (validateDay()) {
			validate();
		}
		if (validateHour()) {
			validate();
		}
		if (validateMinute()) {
			validate();
		}
		if (validateSecond()) {
			validate();
		}
		if (validateNano()) {
			validate();
		}
		if (validateSecond()) {
			validate();
		}
		if (validateMinute()) {
			validate();
		}
		if (validateHour()) {
			validate();
		}
		if (validateMonth()) {
			validate();
		}
	}

	/**
	 * フィールドに保持されている月情報を妥当な年月に補正します。<br>
	 * @return 補正が実施された場合にtrueを返却
	 */
	private boolean validateMonth() {
		{
			boolean reprocess = false;
			while (month <= 0) {
				month += 12;
				year -= 1;
				reprocess = true;
			}
			if (reprocess) {
				return true;
			}
		}
		{
			boolean reprocess = false;
			while (month > 12) {
				month -= 12;
				year += 1;
				reprocess = true;
			}
			if (reprocess) {
				return true;
			}
		}
		return false;
	}

	/**
	 * フィールドに保持されている日情報を妥当な月日に補正します。<br>
	 * @return 補正が実施された場合にtrueを返却
	 */
	private boolean validateDay() {
		{
			boolean reprocess = false;
			while (day <= 0) {
				day += getLastDay(year, month - 1);
				month -= 1;
				reprocess = true;
			}
			if (reprocess) {
				return true;
			}
		}
		{
			boolean reprocess = false;
			if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
				if (day > 31) {
					day = day - 31;
					month += 1;
					reprocess = true;
				}
			} else if (month == 4 || month == 6 || month == 9 || month == 11) {
				if (day > 30) {
					day = day - 30;
					month += 1;
					reprocess = true;
				}
			} else {
				if (isLeapYear()) {
					if (day > 29) {
						day = day - 29;
						month += 1;
						reprocess = true;
					}
				} else {
					if (day > 28) {
						day = day - 28;
						month += 1;
						reprocess = true;
					}
				}
			}
			if (reprocess) {
				return true;
			}
		}
		return false;
	}

	/**
	 * フィールドに保持されている時情報を妥当な日時に補正します。<br>
	 * @return 補正が実施された場合にtrueを返却
	 */
	private boolean validateHour() {
		{
			boolean reprocess = false;
			while (hour < 0) {
				hour += 24;
				day -= 1;
				reprocess = true;
			}
			if (reprocess) {
				return true;
			}
		}
		{
			boolean reprocess = false;
			while (hour >= 24) {
				hour -= 24;
				day += 1;
				reprocess = true;
			}
			if (reprocess) {
				return true;
			}
		}
		return false;
	}

	/**
	 * フィールドに保持されている分情報を妥当な時分に補正します。<br>
	 * @return 補正が実施された場合にtrueを返却
	 */
	private boolean validateMinute() {
		{
			boolean reprocess = false;
			while (minute < 0) {
				minute += 60;
				hour -= 1;
				reprocess = true;
			}
			if (reprocess) {
				return true;
			}
		}
		{
			boolean reprocess = false;
			while (minute >= 60) {
				minute -= 60;
				hour += 1;
				reprocess = true;
			}
			if (reprocess) {
				return true;
			}
		}
		return false;
	}

	/**
	 * フィールドに保持されている秒情報を妥当な分秒に補正します。<br>
	 * @return 補正が実施された場合にtrueを返却
	 */
	private boolean validateSecond() {
		{
			boolean reprocess = false;
			while (second < 0) {
				second += 60;
				minute -= 1;
				reprocess = true;
			}
			if (reprocess) {
				return true;
			}
		}
		{
			boolean reprocess = false;
			while (second >= 60) {
				second -= 60;
				minute += 1;
				reprocess = true;
			}
			if (reprocess) {
				return true;
			}
		}
		return false;
	}

	/**
	 * フィールドに保持されているナノ秒情報を妥当な秒ナノ秒に補正します。<br>
	 * @return 補正が実施された場合にtrueを返却
	 */
	private boolean validateNano() {
		{
			boolean reprocess = false;
			while (nanos < 0) {
				nanos += 1000000000;
				second -= 1;
				reprocess = true;
			}
			if (reprocess) {
				return true;
			}
		}
		{
			boolean reprocess = false;
			while (nanos >= 1000000000) {
				nanos -= 1000000000;
				second += 1;
				reprocess = true;
			}
			if (reprocess) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 年を取得します。<br>
	 * @return 年
	 */
	public int getYear() {
		return year;
	}

	/**
	 * 年を設定します。<br>
	 * @param year 年
	 */
	public void setYear(int year) {
		this.year = year;
		validate();
	}

	/**
	 * 月を取得します。<br>
	 * @return 月
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * 月を設定します。<br>
	 * @param month 月
	 */
	public void setMonth(int month) {
		this.month = month;
		validate();
	}

	/**
	 * 日を取得します。<br>
	 * @return 日
	 */
	public int getDay() {
		return day;
	}

	/**
	 * 日を設定します。<br>
	 * @param day 日
	 */
	public void setDay(int day) {
		this.day = day;
		validate();
	}

	/**
	 * 時を取得します。<br>
	 * @return 時
	 */
	public int getHour() {
		return hour;
	}

	/**
	 * 時を設定します。<br>
	 * @param hour 時
	 */
	public void setHour(int hour) {
		this.hour = hour;
		validate();
	}

	/**
	 * 分を取得します。<br>
	 * @return 分
	 */
	public int getMinute() {
		return minute;
	}

	/**
	 * 分を設定します。<br>
	 * @param minute 分
	 */
	public void setMinute(int minute) {
		this.minute = minute;
		validate();
	}

	/**
	 * 秒を取得します。<br>
	 * @return 秒
	 */
	public int getSecond() {
		return second;
	}

	/**
	 * 秒を設定します。<br>
	 * @param second 秒
	 */
	public void setSecond(int second) {
		this.second = second;
		validate();
	}

	/**
	 * ナノ秒を取得します。<br>
	 * @return ナノ秒
	 */
	public int getNanos() {
		return nanos;
	}

	/**
	 * ナノ秒を設定します。<br>
	 * @param nanos ナノ秒
	 */
	public void setNanos(int nanos) {
		this.nanos = nanos;
		validate();
	}

	/**
	 * マイクロ秒を取得します。<br>
	 * @return マイクロ秒
	 */
	public int getMicros() {
		return nanos / 1000;
	}

	/**
	 * マイクロ秒を設定します。<br>
	 * @param micros マイクロ秒
	 */
	public void setMicros(int micros) {
		this.nanos = micros * 1000;
		validate();
	}

	/**
	 * ミリ秒を取得します。<br>
	 * @return ミリ秒
	 */
	public int getMillis() {
		return nanos / 1000 / 1000;
	}

	/**
	 * ミリ秒を設定します。<br>
	 * @param micros ミリ秒
	 */
	public void setMillis(int millis) {
		this.nanos = millis * 1000 * 1000;
		validate();
	}

	/**
	 * 曜日情報を列挙型にて提供します。<br>
	 * <p>
	 * このクラスは単純に曜日情報を列挙型で比較するためにのみ設置されました。<br>
	 * 内部的な曜日値は{@link java.util.Calendar}が提供する曜日定数で管理されます。<br>
	 * </p>
	 * 
	 * @author Kitagawa<br>
	 * 
	 *<!--
	 * 更新日      更新者           更新内容
	 * 2018/03/15  Kitagawa         新規作成
	 *-->
	 */
	public static enum DayOfWeek {

		/** 日曜 */
		SUNDAY(Calendar.SUNDAY), //

		/** 月曜 */
		MONDAY(Calendar.MONDAY), //

		/** 火曜 */
		TUESDAY(Calendar.TUESDAY), //

		/** 水曜 */
		WEDNESDAY(Calendar.WEDNESDAY), //

		/** 木曜 */
		THURSDAY(Calendar.THURSDAY), //

		/** 金曜 */
		FRIDAY(Calendar.FRIDAY), //

		/** 土曜 */
		SATURDAY(Calendar.SATURDAY), //

		;

		/** 曜日値 */
		private int value;

		/**
		 * コンストラクタ<br>
		 * @param value 曜日値
		 */
		private DayOfWeek(int value) {
			this.value = value;
		}

		/**
		 * {@link java.util.Calendar}の曜日番号(日曜1～土曜7)を取得します。<br>
		 * @return {@link java.util.Calendar}の曜日番号
		 */
		public int getValue() {
			return value;
		}

		/**
		 * 利用者が期待する曜日開始値を基準とした曜日番号(日曜n～土曜n+6)を取得します。<br>
		 * @param start 開始値
		 * @return 曜日番号
		 */
		public int getValue(int start) {
			return value - 1 + start;
		}

		/**
		 * {@link java.util.Calendar}の曜日値をもとに曜日情報を取得します。<br>
		 * @param value {@link java.util.Calendar}の曜日値
		 * @return 曜日情報
		 */
		private static DayOfWeek getDayOfWeek(int value) {
			for (DayOfWeek e : values()) {
				if (e.value == value) {
					return e;
				}
			}
			return null;
		}
	}
}
