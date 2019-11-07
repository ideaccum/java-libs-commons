package org.ideaccum.libs.commons.toys;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.ideaccum.libs.commons.util.StringUtil;

/**
 * 簡易的な計算を行う為のインタフェースを提供します。<br>
 * <p>
 * 内部的な数値管理や計算処理は{@link java.math.BigDecimal}に委譲し、文字列やプリミティブな値を直接パラメータとして指定する簡略実装のために設置されました。<br>
 * </p>
 * 
 *<!--
 * 更新日      更新者           更新内容
 * 2016/03/03  Kitagawa         新規作成
 * 2018/05/16  Kitagawa         再構築(SourceForge.jpからGitHubへの移行に併せて全面改訂)
 *-->
 */
public class SimpleCalculator {

	/** 内部保持値 */
	private BigDecimal value;

	/** 計算中エラー発生状況 */
	private boolean error;

	/** ゼロ除算無視フラぎ */
	private boolean ignoreZeroDivide;

	/** パースエラー無視フラグ */
	private boolean ignoreParseError;

	/**
	 * コンストラクタ<br>
	 * 内部数値は0として初期化し、ゼロ除算及び、パースエラーはエラーとせずにゼロ扱いとして動作するモードで初期化されます。<br>
	 */
	public SimpleCalculator() {
		super();
		this.error = false;
		this.ignoreZeroDivide = true;
		this.ignoreParseError = true;
		set(BigDecimal.ZERO);
	}

	/**
	 * コンストラクタ<br>
	 * 指定された値で内部数値を初期化し、ゼロ除算及び、パースエラーはエラーとせずにゼロ扱いとして動作するモードで初期化されます。<br>
	 * @param value 初期値(nullの場合は0扱いになります)
	 */
	public SimpleCalculator(BigDecimal value) {
		this();
		set(value);
	}

	/**
	 * コンストラクタ<br>
	 * 指定された値で内部数値を初期化し、ゼロ除算及び、パースエラーはエラーとせずにゼロ扱いとして動作するモードで初期化されます。<br>
	 * @param value 初期値(nullの場合は0扱いになります)
	 */
	public SimpleCalculator(Double value) {
		this();
		set(value);
	}

	/**
	 * コンストラクタ<br>
	 * 指定された値で内部数値を初期化し、ゼロ除算及び、パースエラーはエラーとせずにゼロ扱いとして動作するモードで初期化されます。<br>
	 * @param value 初期値(nullの場合は0扱いになります)
	 */
	public SimpleCalculator(Float value) {
		this();
		set(value);
	}

	/**
	 * コンストラクタ<br>
	 * 指定された値で内部数値を初期化し、ゼロ除算及び、パースエラーはエラーとせずにゼロ扱いとして動作するモードで初期化されます。<br>
	 * @param value 初期値(nullの場合は0扱いになります)
	 */
	public SimpleCalculator(Long value) {
		this();
		set(value);
	}

	/**
	 * コンストラクタ<br>
	 * 指定された値で内部数値を初期化し、ゼロ除算及び、パースエラーはエラーとせずにゼロ扱いとして動作するモードで初期化されます。<br>
	 * @param value 初期値(nullの場合は0扱いになります)
	 */
	public SimpleCalculator(Integer value) {
		this();
		set(value);
	}

	/**
	 * コンストラクタ<br>
	 * 指定された値で内部数値を初期化し、ゼロ除算及び、パースエラーはエラーとせずにゼロ扱いとして動作するモードで初期化されます。<br>
	 * @param value 初期値(nullの場合は0扱いになります)
	 */
	public SimpleCalculator(Short value) {
		this();
		set(value);
	}

	/**
	 * コンストラクタ<br>
	 * 指定された値で内部数値を初期化し、ゼロ除算及び、パースエラーはエラーとせずにゼロ扱いとして動作するモードで初期化されます。<br>
	 * @param value 初期値(nullの場合は0扱いになります)
	 * @param pattern 数値文字列書式
	 */
	public SimpleCalculator(String value, String pattern) {
		this();
		set(value, pattern);
	}

	/**
	 * コンストラクタ<br>
	 * 指定された値で内部数値を初期化し、ゼロ除算及び、パースエラーはエラーとせずにゼロ扱いとして動作するモードで初期化されます。<br>
	 * @param value 初期値(nullの場合は0扱いになります)
	 */
	public SimpleCalculator(String value) {
		this();
		set(value);
	}

	/**
	 * 計算中にエラーが発生しているか判定します。<br>
	 * ゼロ除算やパースエラーを無視するモードを利用している場合に有効なメソッドです。<br>
	 * @return 計算中にエラーが発生している場合にtrueを返却
	 */
	public boolean hasError() {
		return error;
	}

	/**
	 * ゼロ除算が発生した場合にエラーとはせずに結果をゼロ扱いとするか判定します。<br>
	 * @return ゼロ除算が発生した場合にエラーとはせずに結果をゼロ扱いとする場合にtrueを返却
	 */
	public boolean isIgnoreZeroDivide() {
		return ignoreZeroDivide;
	}

	/**
	 * ゼロ除算が発生した場合にエラーとはせずに結果をゼロ扱いとするを設定します。<br>
	 * @param ignoreZeroDivide ゼロ除算が発生した場合にエラーとはせずに結果をゼロ扱いとする場合にtrueを指定
	 */
	public void setIgnoreZeroDivide(boolean ignoreZeroDivide) {
		this.ignoreZeroDivide = ignoreZeroDivide;
	}

	/**
	 * 文字列での値設定時にパースエラーとなった場合にゼロとして扱うかを判定します。<br>
	 * @return 文字列での値設定時にパースエラーとなった場合にゼロとして扱う場合にtrueを返却
	 */
	public boolean isIgnoreParseError() {
		return ignoreParseError;
	}

	/**
	 * 文字列での値設定時にパースエラーとなった場合にゼロとして扱うかを設定します。<br>
	 * @param ignoreParseError 文字列での値設定時にパースエラーとなった場合にゼロとして扱う場合にtrueを指定
	 */
	public void setIgnoreParseError(boolean ignoreParseError) {
		this.ignoreParseError = ignoreParseError;
	}

	/**
	 * オブジェクトハッシュコードを提供します。<br>
	 * @return オブジェクトハッシュコード
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		result = prime * result + (ignoreParseError ? 1231 : 1237);
		result = prime * result + (ignoreZeroDivide ? 1231 : 1237);
		return result;
	}

	/**
	 * オブジェクトの等価比較を行います。<br>
	 * @param object 比較オブジェクト
	 * @return 等価の場合にtrueを返却
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (getClass() != object.getClass()) {
			return false;
		}
		SimpleCalculator other = (SimpleCalculator) object;
		if (ignoreParseError != other.ignoreParseError) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		if (ignoreZeroDivide != other.ignoreZeroDivide) {
			return false;
		}
		return true;
	}

	/**
	 * クラス情報を文字列として提供します。<br>
	 * @return クラス情報文字列
	 * @see java.lang.Object#toString()
	 * @see java.math.BigDecimal#toString()
	 */
	@Override
	public String toString() {
		return value.toString();
	}

	/**
	 * 計算結果を提供します。<br>
	 * @return 計算結果
	 */
	public BigDecimal toBigDecimal() {
		return value;
	}

	/**
	 * 計算結果を提供します。<br>
	 * @return 計算結果
	 * @see java.math.BigDecimal#toBigInteger()
	 */
	public BigInteger toBigInteger() {
		return value.toBigInteger();
	}

	/**
	 * 計算結果を提供します。<br>
	 * @return 計算結果
	 * @see java.math.BigDecimal#doubleValue()
	 */
	public Double toDouble() {
		return value.doubleValue();
	}

	/**
	 * 計算結果を提供します。<br>
	 * @return 計算結果
	 * @see java.math.BigDecimal#floatValue()
	 */
	public Float toFloat() {
		return value.floatValue();
	}

	/**
	 * 計算結果を提供します。<br>
	 * @return 計算結果
	 * @see java.math.BigDecimal#longValue()
	 */
	public Long toLong() {
		return value.longValue();
	}

	/**
	 * 計算結果を提供します。<br>
	 * @return 計算結果
	 * @see java.math.BigDecimal#intValue()
	 */
	public Integer toInteger() {
		return value.intValue();
	}

	/**
	 * 計算結果を提供します。<br>
	 * @return 計算結果
	 * @see java.math.BigDecimal#shortValue()
	 */
	public Short toShort() {
		return value.shortValue();
	}

	/**
	 * 指定値を{@link java.math.BigDecimal}値として提供します。<br>
	 * 対象値がnullの場合は0として提供します。<br>
	 * @param value 対象値
	 * @return {@link java.math.BigDecimal}値
	 */
	protected BigDecimal toValue(BigDecimal value) {
		if (value == null) {
			value = BigDecimal.ZERO;
		}
		return value;
	}

	/**
	 * 指定値を{@link java.math.BigDecimal}値として提供します。<br>
	 * 対象値がnullの場合は0として提供します。<br>
	 * @param value 対象値
	 * @return {@link java.math.BigDecimal}値
	 */
	protected BigDecimal toValue(Double value) {
		if (value == null) {
			value = 0D;
		}
		return BigDecimal.valueOf(value);
	}

	/**
	 * 指定値を{@link java.math.BigDecimal}値として提供します。<br>
	 * 対象値がnullの場合は0として提供します。<br>
	 * @param value 対象値
	 * @return {@link java.math.BigDecimal}値
	 */
	protected BigDecimal toValue(Float value) {
		if (value == null) {
			value = 0F;
		}
		return BigDecimal.valueOf(value);
	}

	/**
	 * 指定値を{@link java.math.BigDecimal}値として提供します。<br>
	 * 対象値がnullの場合は0として提供します。<br>
	 * @param value 対象値
	 * @return {@link java.math.BigDecimal}値
	 */
	protected BigDecimal toValue(Long value) {
		if (value == null) {
			value = 0L;
		}
		return BigDecimal.valueOf(value);
	}

	/**
	 * 指定値を{@link java.math.BigDecimal}値として提供します。<br>
	 * 対象値がnullの場合は0として提供します。<br>
	 * @param value 対象値
	 * @return {@link java.math.BigDecimal}値
	 */
	protected BigDecimal toValue(Integer value) {
		if (value == null) {
			value = 0;
		}
		return BigDecimal.valueOf(value);
	}

	/**
	 * 指定値を{@link java.math.BigDecimal}値として提供します。<br>
	 * 対象値がnullの場合は0として提供します。<br>
	 * @param value 対象値
	 * @return {@link java.math.BigDecimal}値
	 */
	protected BigDecimal toValue(Short value) {
		if (value == null) {
			value = 0;
		}
		return BigDecimal.valueOf(value);
	}

	/**
	 * 指定値を{@link java.math.BigDecimal}値として提供します。<br>
	 * 対象値がnullの場合は0として提供します。<br>
	 * @param value 対象値
	 * @param pattern 数値文字列書式
	 * @return {@link java.math.BigDecimal}値
	 */
	protected BigDecimal toValue(String value, String pattern) {
		BigDecimal decimal = BigDecimal.ZERO;
		try {
			decimal = StringUtil.toBigDecimal(value, pattern);
		} catch (RuntimeException e) {
			this.error = true;
			if (!ignoreParseError) {
				throw e;
			}
		}
		return decimal;
	}

	/**
	 * 指定値を{@link java.math.BigDecimal}値として提供します。<br>
	 * 対象値がnullの場合は0として提供します。<br>
	 * @param value 対象値
	 * @return {@link java.math.BigDecimal}値
	 */
	protected BigDecimal toValue(String value) {
		return toValue(value, null);
	}

	/**
	 * 計算状況をクリアします。<br>
	 * このメソッドが呼び出されることで管理される値が0に初期化され、発生しているエラーも初期化されます。<br>
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator clear() {
		set(BigDecimal.ZERO);
		this.error = false;
		return this;
	}

	/**
	 * 切り上げします。<br>
	 * @param scale 精度
	 * @return 自身のインスタンス
	 * @see java.math.BigDecimal#setScale(int, java.math.RoundingMode)
	 */
	public SimpleCalculator roundUp(int scale) {
		this.value = this.value.setScale(scale, BigDecimal.ROUND_UP);
		return this;
	}

	/**
	 * 切り捨てします。<br>
	 * @param scale 精度
	 * @return 自身のインスタンス
	 * @see java.math.BigDecimal#setScale(int, java.math.RoundingMode)
	 */
	public SimpleCalculator roundDown(int scale) {
		this.value = this.value.setScale(scale, BigDecimal.ROUND_DOWN);
		return this;
	}

	/**
	 * 四捨五入します。<br>
	 * @param scale 精度
	 * @return 自身のインスタンス
	 * @see java.math.BigDecimal#setScale(int, java.math.RoundingMode)
	 */
	public SimpleCalculator roundHalfUp(int scale) {
		this.value = this.value.setScale(scale, BigDecimal.ROUND_HALF_UP);
		return this;
	}

	/**
	 * 値を新たにセットします。<br>
	 * @param value セットする値(nullの場合は0扱いになります)
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator set(BigDecimal value) {
		this.value = toValue(value);
		return this;
	}

	/**
	 * 値を新たにセットします。<br>
	 * @param value セットする値(nullの場合は0扱いになります)
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator set(Double value) {
		return set(toValue(value));
	}

	/**
	 * 値を新たにセットします。<br>
	 * @param value セットする値(nullの場合は0扱いになります)
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator set(Float value) {
		return set(toValue(value));
	}

	/**
	 * 値を新たにセットします。<br>
	 * @param value セットする値(nullの場合は0扱いになります)
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator set(Long value) {
		return set(toValue(value));
	}

	/**
	 * 値を新たにセットします。<br>
	 * @param value セットする値(nullの場合は0扱いになります)
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator set(Integer value) {
		return set(toValue(value));
	}

	/**
	 * 値を新たにセットします。<br>
	 * @param value セットする値(nullの場合は0扱いになります)
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator set(Short value) {
		return set(toValue(value));
	}

	/**
	 * 値を新たにセットします。<br>
	 * @param value セットする値(nullの場合は0扱いになります)
	 * @param pattern 数値文字列書式
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator set(String value, String pattern) {
		return set(toValue(value, pattern));
	}

	/**
	 * 値を新たにセットします。<br>
	 * @param value セットする値(nullの場合は0扱いになります)
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator set(String value) {
		return set(toValue(value));
	}

	/**
	 * 加算処理を行います。<br>
	 * @param value 加算値(nullの場合は0扱いになります)
	 * @return 自身のインスタンス
	 * @see java.math.BigDecimal#add(BigDecimal)
	 */
	public SimpleCalculator add(BigDecimal value) {
		this.value = this.value.add(toValue(value));
		return this;
	}

	/**
	 * 加算処理を行います。<br>
	 * @param value 加算値(nullの場合は0扱いになります)
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator add(Double value) {
		return add(toValue(value));
	}

	/**
	 * 加算処理を行います。<br>
	 * @param value 加算値(nullの場合は0扱いになります)
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator add(Float value) {
		return add(toValue(value));
	}

	/**
	 * 加算処理を行います。<br>
	 * @param value 加算値(nullの場合は0扱いになります)
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator add(Long value) {
		return add(toValue(value));
	}

	/**
	 * 加算処理を行います。<br>
	 * @param value 加算値(nullの場合は0扱いになります)
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator add(Integer value) {
		return add(toValue(value));
	}

	/**
	 * 加算処理を行います。<br>
	 * @param value 加算値(nullの場合は0扱いになります)
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator add(Short value) {
		return add(toValue(value));
	}

	/**
	 * 加算処理を行います。<br>
	 * @param value 加算値(nullの場合は0扱いになります)
	 * @param pattern 数値文字列書式
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator add(String value, String pattern) {
		return add(toValue(value, pattern));
	}

	/**
	 * 加算処理を行います。<br>
	 * @param value 加算値(nullの場合は0扱いになります)
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator add(String value) {
		return add(toValue(value));
	}

	/**
	 * 減算処理を行います。<br>
	 * @param value 減算値(nullの場合は0扱いになります)
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator subtract(BigDecimal value) {
		this.value = this.value.subtract(toValue(value));
		return this;
	}

	/**
	 * 減算処理を行います。<br>
	 * @param value 減算値(nullの場合は0扱いになります)
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator subtract(Double value) {
		return subtract(toValue(value));
	}

	/**
	 * 減算処理を行います。<br>
	 * @param value 減算値(nullの場合は0扱いになります)
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator subtract(Float value) {
		return subtract(toValue(value));
	}

	/**
	 * 減算処理を行います。<br>
	 * @param value 減算値(nullの場合は0扱いになります)
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator subtract(Long value) {
		return subtract(toValue(value));
	}

	/**
	 * 減算処理を行います。<br>
	 * @param value 減算値(nullの場合は0扱いになります)
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator subtract(Integer value) {
		return subtract(toValue(value));
	}

	/**
	 * 減算処理を行います。<br>
	 * @param value 減算値(nullの場合は0扱いになります)
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator subtract(Short value) {
		return subtract(toValue(value));
	}

	/**
	 * 減算処理を行います。<br>
	 * @param value 減算値(nullの場合は0扱いになります)
	 * @param pattern 数値文字列書式
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator subtract(String value, String pattern) {
		return subtract(toValue(value, pattern));
	}

	/**
	 * 減算処理を行います。<br>
	 * @param value 減算値(nullの場合は0扱いになります)
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator subtract(String value) {
		return subtract(toValue(value));
	}

	/**
	 * 乗算処理を行います。<br>
	 * @param value 乗算値(nullの場合は0扱いになります)
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator multiply(BigDecimal value) {
		this.value = this.value.multiply(toValue(value));
		return this;
	}

	/**
	 * 乗算処理を行います。<br>
	 * @param value 乗算値(nullの場合は0扱いになります)
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator multiply(Double value) {
		return multiply(toValue(value));
	}

	/**
	 * 乗算処理を行います。<br>
	 * @param value 乗算値(nullの場合は0扱いになります)
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator multiply(Float value) {
		return multiply(toValue(value));
	}

	/**
	 * 乗算処理を行います。<br>
	 * @param value 乗算値(nullの場合は0扱いになります)
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator multiply(Long value) {
		return multiply(toValue(value));
	}

	/**
	 * 乗算処理を行います。<br>
	 * @param value 乗算値(nullの場合は0扱いになります)
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator multiply(Integer value) {
		return multiply(toValue(value));
	}

	/**
	 * 乗算処理を行います。<br>
	 * @param value 乗算値(nullの場合は0扱いになります)
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator multiply(Short value) {
		return multiply(toValue(value));
	}

	/**
	 * 乗算処理を行います。<br>
	 * @param value 乗算値(nullの場合は0扱いになります)
	 * @param pattern 数値文字列書式
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator multiply(String value, String pattern) {
		return multiply(toValue(value, pattern));
	}

	/**
	 * 乗算処理を行います。<br>
	 * @param value 乗算値(nullの場合は0扱いになります)
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator multiply(String value) {
		return multiply(toValue(value));
	}

	/**
	 * 除算処理を行います。<br>
	 * @param value 除算値(nullの場合は0扱いになります)
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator divide(BigDecimal value) {
		if (value == null) {
			value = BigDecimal.ZERO;
		}
		if (BigDecimal.ZERO.equals(value)) {
			if (ignoreZeroDivide) {
				this.value = BigDecimal.ZERO;
				this.error = true;
				return this;
			} else {
				throw new ArithmeticException("/ by zero");
			}
		}
		this.value = this.value.divide(value);
		return this;
	}

	/**
	 * 除算処理を行います。<br>
	 * @param value 除算値(nullの場合は0扱いになります)
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator divide(Double value) {
		return divide(toValue(value));
	}

	/**
	 * 除算処理を行います。<br>
	 * @param value 除算値(nullの場合は0扱いになります)
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator divide(Float value) {
		return divide(toValue(value));
	}

	/**
	 * 除算処理を行います。<br>
	 * @param value 除算値(nullの場合は0扱いになります)
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator divide(Long value) {
		return divide(toValue(value));
	}

	/**
	 * 除算処理を行います。<br>
	 * @param value 除算値(nullの場合は0扱いになります)
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator divide(Integer value) {
		return divide(toValue(value));
	}

	/**
	 * 除算処理を行います。<br>
	 * @param value 除算値(nullの場合は0扱いになります)
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator divide(Short value) {
		return divide(toValue(value));
	}

	/**
	 * 除算処理を行います。<br>
	 * @param value 除算値(nullの場合は0扱いになります)
	 * @param pattern 数値文字列書式
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator divide(String value, String pattern) {
		return divide(toValue(value, pattern));
	}

	/**
	 * 除算処理を行います。<br>
	 * @param value 除算値(nullの場合は0扱いになります)
	 * @return 自身のインスタンス
	 */
	public SimpleCalculator divide(String value) {
		return divide(toValue(value));
	}
}
