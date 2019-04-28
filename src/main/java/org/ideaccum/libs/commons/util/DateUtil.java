package org.ideaccum.libs.commons.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日付操作を行う際の支援的な操作メソッドを提供します。<br>
 * <p>
 * システム開発時に利用する日付操作で利用頻度の高い操作を提供します。<br>
 * </p>
 * 
 * @author Kitagawa<br>
 * 
 *<!--
 * 更新日		更新者			更新内容
 * 2012/07/26	Kitagawa		新規作成
 * 2018/05/02	Kitagawa		再構築(SourceForge.jpからGitHubへの移行に併せて全面改訂)
 * 2019/03/18	Kitagawa		和暦定義列挙型(JapaneseYearType)を追加
 * 2019/03/18	Kitagawa		和暦定義関連のアクセッサを追加
 *-->
 */
public final class DateUtil {

	/**
	 * コンストラクタ<br>
	 */
	private DateUtil() {
		super();
	}

	/**
	 * 日付情報を指定書式にフォーマットした文字列として提供します。<br>
	 * nullの日付情報が指定された場合は、空文字列として返却します。<br>
	 * @param date 日付情報
	 * @param pattern 日付文字列書式
	 * @return フォーマットされた日付文字列
	 */
	public static String format(Date date, String pattern) {
		if (date == null) {
			return "";
		}
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 日付文字列を指定書式に沿ってパース下日付情報を提供します。<br>
	 * 空の文字列が指定された場合は、nullの日付情報として返却します。<br>
	 * @param date 日付文字列
	 * @param pattern 日付文字列書式
	 * @return 日付情報
	 */
	public static Date parse(String date, String pattern) {
		if (StringUtil.isEmpty(date)) {
			return null;
		}
		try {
			return new SimpleDateFormat(pattern).parse(date);
		} catch (Throwable e) {
			throw new IllegalArgumentException(pattern, e);
		}
	}

	/**
	 * 日付情報に指定年数を加算した日付情報を提供します。<br>
	 * @param date 日付情報
	 * @param year 加算年数
	 * @return 指定年数を加算した日付情報
	 */
	public static Date addYear(Date date, int year) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, year);
		return calendar.getTime();
	}

	/**
	 * 日付情報に指定月数を加算した日付情報を提供します。<br>
	 * @param date 日付情報
	 * @param month 加算月数
	 * @return 指定月数を加算した日付情報
	 */
	public static Date addMonth(Date date, int month) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, month);
		return calendar.getTime();
	}

	/**
	 * 日付情報に指定日数を加算した日付情報を提供します。<br>
	 * @param date 日付オブジェクト
	 * @param day 加算日数
	 * @return 指定日数を加算した日付情報
	 */
	public static Date addDay(Date date, int day) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);
		return calendar.getTime();
	}

	/**
	 * 日付情報に指定時間を加算した日付情報を提供します。<br>
	 * @param date 日付オブジェクト
	 * @param hour 加算時間
	 * @return 指定時間を加算した日付情報
	 */
	public static Date addHour(Date date, int hour) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, hour);
		return calendar.getTime();
	}

	/**
	 * 日付情報に指定分算した日付情報を提供します。<br>
	 * @param date 日付オブジェクト
	 * @param minute 加算分
	 * @return 指定分を加算した日付情報
	 */
	public static Date addMinute(Date date, int minute) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minute);
		return calendar.getTime();
	}

	/**
	 * 日付情報に指定秒算した日付情報を提供します。<br>
	 * @param date 日付オブジェクト
	 * @param second 加算秒
	 * @return 指定秒を加算した日付情報
	 */
	public static Date addSecond(Date date, int second) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, second);
		return calendar.getTime();
	}

	/**
	 * 日付情報に指定ミリ秒算した日付情報を提供します。<br>
	 * @param date 日付オブジェクト
	 * @param millisecond 加算ミリ秒
	 * @return 指定ミリ秒を加算した日付情報
	 */
	public static Date addMillisecond(Date date, int millisecond) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.MILLISECOND, millisecond);
		return calendar.getTime();
	}

	/**
	 * 一日前の日付情報を取得します。<br>
	 * @param date 日付情報
	 * @return 一日前の日付情報
	 */
	public static Date yesterday(Date date) {
		return addDay(date, -1);
	}

	/**
	 * 一日先の日付情報を取得します。<br>
	 * @param date 日付情報
	 * @return 一日先の日付情報
	 */
	public static Date tommorrow(Date date) {
		return addDay(date, +1);
	}

	/**
	 * 日付情報に対応する和暦情報を取得します。<br>
	 * @param date 日付情報
	 * @return 日付情報に対応する和暦情報
	 */
	public static JapaneseEraNames getJapaneseYearType(Date date) {
		if (date == null) {
			return null;
		}
		JapaneseEraNames yearType = JapaneseEraNames.match(date);
		if (yearType == null) {
			throw new IllegalArgumentException("Cannot treat " + date);
		}
		return yearType;
	}

	/**
	 * 日付情報に対応する和暦年を取得します。<br>
	 * @param date 日付情報
	 * @return 日付情報に対応する和暦年
	 */
	public static int getJapaneseYear(Date date) {
		if (date == null) {
			return 0;
		}
		JapaneseEraNames yearType = JapaneseEraNames.match(date);
		if (yearType == null) {
			throw new IllegalArgumentException("Cannot treat " + date);
		}
		return getYear(date) - yearType.getStartYear() + 1;
	}

	/**
	 * 日付情報から年を取得します。<br>
	 * @param date 日付情報
	 * @return 年
	 */
	public static int getYear(Date date) {
		return date == null ? 0 : Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
	}

	/**
	 * 日付情報から月を取得します。<br>
	 * @param date 日付情報
	 * @return 月
	 */
	public static int getMonth(Date date) {
		return date == null ? 0 : Integer.parseInt(format(date, "MM"));
	}

	/**
	 * 日付情報から日を取得します。<br>
	 * @param date 日付情報
	 * @return 日
	 */
	public static int getDay(Date date) {
		return date == null ? 0 : Integer.parseInt(format(date, "dd"));
	}

	/**
	 * 日付情報から時間(24時間表記)を取得します。<br>
	 * @param date 日付情報
	 * @return 時間(24時間表記)
	 */
	public static int getHour(Date date) {
		return date == null ? 0 : Integer.parseInt(format(date, "HH"));
	}

	/**
	 * 日付情報から時間(12時間表記)を取得します。<br>
	 * @param date 日付情報
	 * @return 時間(12時間表記)
	 */
	public static int getHour12(Date date) {
		return date == null ? 0 : Integer.parseInt(format(date, "hh"));
	}

	/**
	 * 日付情報から分を取得します。<br>
	 * @param date 日付情報
	 * @return 分
	 */
	public static int getMinute(Date date) {
		return date == null ? 0 : Integer.parseInt(format(date, "mm"));
	}

	/**
	 * 日付情報から秒を取得します。<br>
	 * @param date 日付情報
	 * @return 秒
	 */
	public static int getSecond(Date date) {
		return date == null ? 0 : Integer.parseInt(format(date, "ss"));
	}

	/**
	 * 日付情報からミリ秒を取得します。<br>
	 * @param date 日付情報
	 * @return ミリ秒
	 */
	public static int getMillis(Date date) {
		return date == null ? 0 : Integer.parseInt(format(date, "SSS"));
	}

	/**
	 * 日付情報からナノ秒を取得します。<br>
	 * このメソッドは日付情報が{@link java.sql.Timestamp}の場合にのみ有効な値を返却します。<br>
	 * @param date 日付情報
	 * @return ナノ秒
	 */
	public static int getNanos(Date date) {
		if (!(date instanceof Timestamp)) {
			return 0;
		}
		Timestamp timestamp = (Timestamp) date;
		return timestamp.getNanos();
	}

	/**
	 * 日付情報から"yyyy"書式でフォーマットされた年文字列を取得します。<br>
	 * @param date 日付情報
	 * @return "yyyy"書式でフォーマットされた年文字列
	 */
	public static String getYYYY(Date date) {
		return format(date, "yyyy");
	}

	/**
	 * 日付情報から"MM"書式でフォーマットされた月文字列を取得します。<br>
	 * @param date 日付情報
	 * @return "MM"書式でフォーマットされた月文字列
	 */
	public static String getMM(Date date) {
		return format(date, "MM");
	}

	/**
	 * 日付情報から"dd"書式でフォーマットされた日文字列を取得します。<br>
	 * @param date 日付情報
	 * @return "dd"書式でフォーマットされた日文字列
	 */
	public static String getDD(Date date) {
		return format(date, "dd");
	}

	/**
	 * 特定年月の月末日を取得します。<br>
	 * @param year 年
	 * @param month 月
	 * @return 月末日
	 */
	public static int getLastDay(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		return calendar.getActualMaximum(Calendar.DATE);
	}

	/**
	 * 特定年月の月末日付情報を取得します。<br>
	 * @param year 年
	 * @param month 月
	 * @return 月末日付情報
	 */
	public static Date getLastDate(int year, int month) {
		try {
			return new SimpleDateFormat("yyyy/M/d").parse(year + "/" + month + "/" + getLastDay(year, month));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 特定年月日の日付情報を取得します。<br>
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @return 日付情報
	 */
	public static Date getDate(int year, int month, int day) {
		try {
			return new SimpleDateFormat("yyyy/M/d").parse(year + "/" + month + "/" + day);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 日付情報から曜日情報を取得します。<br>
	 * @param date 日付情報
	 * @return 曜日情報
	 */
	public static DayOfWeek getDayOfWeek(Date date) {
		if (date == null) {
			return null;
		}
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int w = calendar.get(Calendar.DAY_OF_WEEK);
		return DayOfWeek.getDayOfWeek(w);
	}

	/**
	 * 日付情報から該当年における週番号を取得します。<br>
	 * @param date 日付情報
	 * @return 年における週番号
	 */
	public static int getWeekOfYear(Date date) {
		if (date == null) {
			return 0;
		}
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 時間情報を持つ日付情報と同一日付の0時0分0秒000ミリ秒の日付情報を取得します。<br>
	 * @param date 日付情報
	 * @return 指定された日付情報と同一日付の0時0分0秒000ミリ秒の日付情報
	 */
	public static Date getMinOfDate(Date date) {
		if (date == null) {
			return new Date(Long.MIN_VALUE);
		} else {
			String yyyyMMdd = format(date, "yyyyMMdd");
			return parse("yyyyMMddHHmmssSSS", yyyyMMdd + "000000000");
		}
	}

	/**
	 * 時間情報を持つ日付情報と同一日付の23時59分59秒999ミリ秒の日付情報を取得します。<br>
	 * @param date 日付情報
	 * @return 指定された日付情報と同一日付の23時59分59秒999ミリ秒の日付情報
	 */
	public static Date getMaxOfDate(Date date) {
		if (date == null) {
			return new Date(Long.MIN_VALUE);
		} else {
			String yyyyMMdd = format(date, "yyyyMMdd");
			return parse("yyyyMMddHHmmssSSS", yyyyMMdd + "235959999");
		}
	}

	/**
	 * 日付情報が月初日か判定します。<br>
	 * @param date 日付情報
	 * @return 月初日の場合にtrueを返却
	 */
	public static boolean isStartOfMonth(Date date) {
		if (date == null) {
			return false;
		}
		return getDay(date) == 1;
	}

	/**
	 * 日付情報が月末日か判定します。<br>
	 * @param date 日付情報
	 * @return 月末日の場合にtrueを返却
	 */
	public static boolean isEndOfMonth(Date date) {
		if (date == null) {
			return false;
		}
		return getDay(date) == getLastDay(getYear(date), getMonth(date));
	}

	/**
	 * 年がうるう年であるか判定します。<br>
	 * @param year 判定対象年
	 * @return うるう年であるである場合にtrueを返却
	 */
	public static boolean isLeapYear(int year) {
		return getLastDay(year, 2) == 29;
	}

	/**
	 * 日付情報の年がうるう年であるか判定します。<br>
	 * @param date 日付情報
	 * @return うるう年であるである場合にtrueを返却
	 */
	public static boolean isLeapYear(Date date) {
		if (date == null) {
			return false;
		}
		return isLeapYear(getYear(date));
	}

	/**
	 * 日付期間同士が重なり合うか判定します。<br>
	 * @param date1Start 比較元期間開始
	 * @param date1End 比較元期間終了
	 * @param date2Start 比較先期間開始
	 * @param date2End 比較先期間終了
	 * @return 2つの期間が重なりあう場合にtrueを返却
	 */
	public static boolean isOverlapPeriod(Date date1Start, Date date1End, Date date2Start, Date date2End) {
		long date1StartTime = date1Start == null ? Long.MIN_VALUE : date1Start.getTime();
		long date1EndTime = date1End == null ? Long.MAX_VALUE : date1End.getTime();
		long date2StartTime = date2Start == null ? Long.MIN_VALUE : date2Start.getTime();
		long date2EndTime = date2End == null ? Long.MAX_VALUE : date2End.getTime();
		return date1StartTime <= date2EndTime && date2StartTime <= date1EndTime;
	}

	/**
	 * 日付文字列が正しい日付日付であるか判定します。<br>
	 * ここでの判定は{@link #parse(String, String)}によるパースを前提としているため、空文字列でも妥当な文字列として判定します。<br>
	 * 但し、2000/2/31のようにカレンダーオブジェクトでは、2000/3/2に補完されるような日付データについては不正な文字列として結果を返却します。<br>
	 * @param date 日付文字列
	 * @param pattern 日付書式
	 * @return 正しい日付文字列である場合にtrueを返却
	 */
	public static boolean isValidDateString(String date, String pattern) {
		if (StringUtil.isEmpty(date)) {
			return true;
		}
		try {
			String revdate = new SimpleDateFormat(pattern).format(new SimpleDateFormat(pattern).parse(date));
			return date.equals(revdate);
		} catch (Throwable e) {
			return false;
		}
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
	 * 更新日		更新者			更新内容
	 * 2018/03/15	Kitagawa		新規作成
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
