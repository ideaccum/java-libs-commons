package org.ideaccum.libs.commons.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文字列操作を行う際の支援的な操作メソッドを提供します。<br>
 * <p>
 * システム開発時に利用する文字列操作で利用頻度の高い操作を提供します。<br>
 * </p>
 * 
 * @author Kitagawa<br>
 * 
 *<!--
 * 更新日		更新者			更新内容
 * 2005/07/02	Kitagawa		新規作成
 * 2018/05/02	Kitagawa		再構築(SourceForge.jpからGitHubへの移行に併せて全面改訂)
 *-->
 */
public final class StringUtil {

	/** 空文字列 */
	public static final String EMPTY = "";

	/** ヘキサ文字列 */
	private static final String[] HEX_STRINGS = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/** 全角-半角変換マップ(数字) */
	private static final Map<Character, Character> MAP_J2A_NUMERIC;
	static {
		MAP_J2A_NUMERIC = new HashMap<>();
		MAP_J2A_NUMERIC.put('０', '0');
		MAP_J2A_NUMERIC.put('１', '1');
		MAP_J2A_NUMERIC.put('２', '2');
		MAP_J2A_NUMERIC.put('３', '3');
		MAP_J2A_NUMERIC.put('４', '4');
		MAP_J2A_NUMERIC.put('５', '5');
		MAP_J2A_NUMERIC.put('６', '6');
		MAP_J2A_NUMERIC.put('７', '7');
		MAP_J2A_NUMERIC.put('８', '8');
		MAP_J2A_NUMERIC.put('９', '9');
	}

	/** 半角-全角変換マップ(数字) */
	private static final Map<Character, Character> MAP_A2J_NUMERIC;
	static {
		MAP_A2J_NUMERIC = new HashMap<>();
		MAP_A2J_NUMERIC.put('0', '０');
		MAP_A2J_NUMERIC.put('1', '１');
		MAP_A2J_NUMERIC.put('2', '２');
		MAP_A2J_NUMERIC.put('3', '３');
		MAP_A2J_NUMERIC.put('4', '４');
		MAP_A2J_NUMERIC.put('5', '５');
		MAP_A2J_NUMERIC.put('6', '６');
		MAP_A2J_NUMERIC.put('7', '７');
		MAP_A2J_NUMERIC.put('8', '８');
		MAP_A2J_NUMERIC.put('9', '９');
	}

	/** 全角-半角変換マップ(英字) */
	private static final Map<Character, Character> MAP_J2A_ALPHABET;
	static {
		MAP_J2A_ALPHABET = new HashMap<>();
		MAP_J2A_ALPHABET.put('Ａ', 'A');
		MAP_J2A_ALPHABET.put('Ｂ', 'B');
		MAP_J2A_ALPHABET.put('Ｃ', 'C');
		MAP_J2A_ALPHABET.put('Ｄ', 'D');
		MAP_J2A_ALPHABET.put('Ｅ', 'E');
		MAP_J2A_ALPHABET.put('Ｆ', 'F');
		MAP_J2A_ALPHABET.put('Ｇ', 'G');
		MAP_J2A_ALPHABET.put('Ｈ', 'H');
		MAP_J2A_ALPHABET.put('Ｉ', 'I');
		MAP_J2A_ALPHABET.put('Ｊ', 'J');
		MAP_J2A_ALPHABET.put('Ｋ', 'K');
		MAP_J2A_ALPHABET.put('Ｌ', 'L');
		MAP_J2A_ALPHABET.put('Ｍ', 'M');
		MAP_J2A_ALPHABET.put('Ｎ', 'N');
		MAP_J2A_ALPHABET.put('Ｏ', 'O');
		MAP_J2A_ALPHABET.put('Ｐ', 'P');
		MAP_J2A_ALPHABET.put('Ｑ', 'Q');
		MAP_J2A_ALPHABET.put('Ｒ', 'R');
		MAP_J2A_ALPHABET.put('Ｓ', 'S');
		MAP_J2A_ALPHABET.put('Ｔ', 'T');
		MAP_J2A_ALPHABET.put('Ｕ', 'U');
		MAP_J2A_ALPHABET.put('Ｖ', 'V');
		MAP_J2A_ALPHABET.put('Ｗ', 'W');
		MAP_J2A_ALPHABET.put('Ｘ', 'X');
		MAP_J2A_ALPHABET.put('Ｙ', 'Y');
		MAP_J2A_ALPHABET.put('Ｚ', 'Z');
		MAP_J2A_ALPHABET.put('ａ', 'a');
		MAP_J2A_ALPHABET.put('ｂ', 'b');
		MAP_J2A_ALPHABET.put('ｃ', 'c');
		MAP_J2A_ALPHABET.put('ｄ', 'd');
		MAP_J2A_ALPHABET.put('ｅ', 'e');
		MAP_J2A_ALPHABET.put('ｆ', 'f');
		MAP_J2A_ALPHABET.put('ｇ', 'g');
		MAP_J2A_ALPHABET.put('ｈ', 'h');
		MAP_J2A_ALPHABET.put('ｉ', 'i');
		MAP_J2A_ALPHABET.put('ｊ', 'j');
		MAP_J2A_ALPHABET.put('ｋ', 'k');
		MAP_J2A_ALPHABET.put('ｌ', 'l');
		MAP_J2A_ALPHABET.put('ｍ', 'm');
		MAP_J2A_ALPHABET.put('ｎ', 'n');
		MAP_J2A_ALPHABET.put('ｏ', 'o');
		MAP_J2A_ALPHABET.put('ｐ', 'p');
		MAP_J2A_ALPHABET.put('ｑ', 'q');
		MAP_J2A_ALPHABET.put('ｒ', 'r');
		MAP_J2A_ALPHABET.put('ｓ', 's');
		MAP_J2A_ALPHABET.put('ｔ', 't');
		MAP_J2A_ALPHABET.put('ｕ', 'u');
		MAP_J2A_ALPHABET.put('ｖ', 'v');
		MAP_J2A_ALPHABET.put('ｗ', 'w');
		MAP_J2A_ALPHABET.put('ｘ', 'x');
		MAP_J2A_ALPHABET.put('ｙ', 'y');
		MAP_J2A_ALPHABET.put('ｚ', 'z');
	}

	/** 半角-全角変換マップ(英字) */
	private static final Map<Character, Character> MAP_A2J_ALPHABET;
	static {
		MAP_A2J_ALPHABET = new HashMap<>();
		MAP_A2J_ALPHABET.put('A', 'Ａ');
		MAP_A2J_ALPHABET.put('B', 'Ｂ');
		MAP_A2J_ALPHABET.put('C', 'Ｃ');
		MAP_A2J_ALPHABET.put('D', 'Ｄ');
		MAP_A2J_ALPHABET.put('E', 'Ｅ');
		MAP_A2J_ALPHABET.put('F', 'Ｆ');
		MAP_A2J_ALPHABET.put('G', 'Ｇ');
		MAP_A2J_ALPHABET.put('H', 'Ｈ');
		MAP_A2J_ALPHABET.put('I', 'Ｉ');
		MAP_A2J_ALPHABET.put('J', 'Ｊ');
		MAP_A2J_ALPHABET.put('K', 'Ｋ');
		MAP_A2J_ALPHABET.put('L', 'Ｌ');
		MAP_A2J_ALPHABET.put('M', 'Ｍ');
		MAP_A2J_ALPHABET.put('N', 'Ｎ');
		MAP_A2J_ALPHABET.put('O', 'Ｏ');
		MAP_A2J_ALPHABET.put('P', 'Ｐ');
		MAP_A2J_ALPHABET.put('Q', 'Ｑ');
		MAP_A2J_ALPHABET.put('R', 'Ｒ');
		MAP_A2J_ALPHABET.put('S', 'Ｓ');
		MAP_A2J_ALPHABET.put('T', 'Ｔ');
		MAP_A2J_ALPHABET.put('U', 'Ｕ');
		MAP_A2J_ALPHABET.put('V', 'Ｖ');
		MAP_A2J_ALPHABET.put('W', 'Ｗ');
		MAP_A2J_ALPHABET.put('X', 'Ｘ');
		MAP_A2J_ALPHABET.put('Y', 'Ｙ');
		MAP_A2J_ALPHABET.put('Z', 'Ｚ');
		MAP_A2J_ALPHABET.put('a', 'ａ');
		MAP_A2J_ALPHABET.put('b', 'ｂ');
		MAP_A2J_ALPHABET.put('c', 'ｃ');
		MAP_A2J_ALPHABET.put('d', 'ｄ');
		MAP_A2J_ALPHABET.put('e', 'ｅ');
		MAP_A2J_ALPHABET.put('f', 'ｆ');
		MAP_A2J_ALPHABET.put('g', 'ｇ');
		MAP_A2J_ALPHABET.put('h', 'ｈ');
		MAP_A2J_ALPHABET.put('i', 'ｉ');
		MAP_A2J_ALPHABET.put('j', 'ｊ');
		MAP_A2J_ALPHABET.put('k', 'ｋ');
		MAP_A2J_ALPHABET.put('l', 'ｌ');
		MAP_A2J_ALPHABET.put('m', 'ｍ');
		MAP_A2J_ALPHABET.put('n', 'ｎ');
		MAP_A2J_ALPHABET.put('o', 'ｏ');
		MAP_A2J_ALPHABET.put('p', 'ｐ');
		MAP_A2J_ALPHABET.put('q', 'ｑ');
		MAP_A2J_ALPHABET.put('r', 'ｒ');
		MAP_A2J_ALPHABET.put('s', 'ｓ');
		MAP_A2J_ALPHABET.put('t', 'ｔ');
		MAP_A2J_ALPHABET.put('u', 'ｕ');
		MAP_A2J_ALPHABET.put('v', 'ｖ');
		MAP_A2J_ALPHABET.put('w', 'ｗ');
		MAP_A2J_ALPHABET.put('x', 'ｘ');
		MAP_A2J_ALPHABET.put('y', 'ｙ');
		MAP_A2J_ALPHABET.put('z', 'ｚ');
	}

	/** 全角-半角変換マップ(かな) */
	private static final Map<Character, String> MAP_J2A_KANA;
	static {
		MAP_J2A_KANA = new HashMap<>();
		MAP_J2A_KANA.put('あ', "ｱ");
		MAP_J2A_KANA.put('い', "ｲ");
		MAP_J2A_KANA.put('う', "ｳ");
		MAP_J2A_KANA.put('え', "ｴ");
		MAP_J2A_KANA.put('お', "ｵ");
		MAP_J2A_KANA.put('か', "ｶ");
		MAP_J2A_KANA.put('き', "ｷ");
		MAP_J2A_KANA.put('く', "ｸ");
		MAP_J2A_KANA.put('け', "ｹ");
		MAP_J2A_KANA.put('こ', "ｺ");
		MAP_J2A_KANA.put('さ', "ｻ");
		MAP_J2A_KANA.put('し', "ｼ");
		MAP_J2A_KANA.put('す', "ｽ");
		MAP_J2A_KANA.put('せ', "ｾ");
		MAP_J2A_KANA.put('そ', "ｿ");
		MAP_J2A_KANA.put('た', "ﾀ");
		MAP_J2A_KANA.put('ち', "ﾁ");
		MAP_J2A_KANA.put('つ', "ﾂ");
		MAP_J2A_KANA.put('て', "ﾃ");
		MAP_J2A_KANA.put('と', "ﾄ");
		MAP_J2A_KANA.put('な', "ﾅ");
		MAP_J2A_KANA.put('に', "ﾆ");
		MAP_J2A_KANA.put('ぬ', "ﾇ");
		MAP_J2A_KANA.put('ね', "ﾈ");
		MAP_J2A_KANA.put('の', "ﾉ");
		MAP_J2A_KANA.put('は', "ﾊ");
		MAP_J2A_KANA.put('ひ', "ﾋ");
		MAP_J2A_KANA.put('ふ', "ﾌ");
		MAP_J2A_KANA.put('へ', "ﾍ");
		MAP_J2A_KANA.put('ほ', "ﾎ");
		MAP_J2A_KANA.put('ま', "ﾏ");
		MAP_J2A_KANA.put('み', "ﾐ");
		MAP_J2A_KANA.put('む', "ﾑ");
		MAP_J2A_KANA.put('め', "ﾒ");
		MAP_J2A_KANA.put('も', "ﾓ");
		MAP_J2A_KANA.put('や', "ﾔ");
		MAP_J2A_KANA.put('ゆ', "ﾕ");
		MAP_J2A_KANA.put('よ', "ﾖ");
		MAP_J2A_KANA.put('ら', "ﾗ");
		MAP_J2A_KANA.put('り', "ﾘ");
		MAP_J2A_KANA.put('る', "ﾙ");
		MAP_J2A_KANA.put('れ', "ﾚ");
		MAP_J2A_KANA.put('ろ', "ﾛ");
		MAP_J2A_KANA.put('わ', "ﾜ");
		MAP_J2A_KANA.put('を', "ｦ");
		MAP_J2A_KANA.put('ん', "ﾝ");
		MAP_J2A_KANA.put('ゃ', "ｬ");
		MAP_J2A_KANA.put('ゅ', "ｭ");
		MAP_J2A_KANA.put('ょ', "ｮ");
		MAP_J2A_KANA.put('っ', "ｯ");
		MAP_J2A_KANA.put('ぁ', "ｧ");
		MAP_J2A_KANA.put('ぃ', "ｨ");
		MAP_J2A_KANA.put('ぅ', "ｩ");
		MAP_J2A_KANA.put('ぇ', "ｪ");
		MAP_J2A_KANA.put('ぉ', "ｫ");
		MAP_J2A_KANA.put('が', "ｶﾞ");
		MAP_J2A_KANA.put('ぎ', "ｷﾞ");
		MAP_J2A_KANA.put('ぐ', "ｸﾞ");
		MAP_J2A_KANA.put('げ', "ｹﾞ");
		MAP_J2A_KANA.put('ご', "ｺﾞ");
		MAP_J2A_KANA.put('ざ', "ｻﾞ");
		MAP_J2A_KANA.put('じ', "ｼﾞ");
		MAP_J2A_KANA.put('ず', "ｽﾞ");
		MAP_J2A_KANA.put('ぜ', "ｾﾞ");
		MAP_J2A_KANA.put('ぞ', "ｿﾞ");
		MAP_J2A_KANA.put('だ', "ﾀﾞ");
		MAP_J2A_KANA.put('ぢ', "ﾁﾞ");
		MAP_J2A_KANA.put('づ', "ﾂﾞ");
		MAP_J2A_KANA.put('で', "ﾃﾞ");
		MAP_J2A_KANA.put('ど', "ﾄﾞ");
		MAP_J2A_KANA.put('ば', "ﾊﾞ");
		MAP_J2A_KANA.put('び', "ﾋﾞ");
		MAP_J2A_KANA.put('ぶ', "ﾌﾞ");
		MAP_J2A_KANA.put('べ', "ﾍﾞ");
		MAP_J2A_KANA.put('ぼ', "ﾎﾞ");
		MAP_J2A_KANA.put('ぱ', "ﾊﾟ");
		MAP_J2A_KANA.put('ぴ', "ﾋﾟ");
		MAP_J2A_KANA.put('ぷ', "ﾌﾟ");
		MAP_J2A_KANA.put('ぺ', "ﾍﾟ");
		MAP_J2A_KANA.put('ぽ', "ﾎﾟ");
		MAP_J2A_KANA.put('ア', "ｱ");
		MAP_J2A_KANA.put('イ', "ｲ");
		MAP_J2A_KANA.put('ウ', "ｳ");
		MAP_J2A_KANA.put('エ', "ｴ");
		MAP_J2A_KANA.put('オ', "ｵ");
		MAP_J2A_KANA.put('カ', "ｶ");
		MAP_J2A_KANA.put('キ', "ｷ");
		MAP_J2A_KANA.put('ク', "ｸ");
		MAP_J2A_KANA.put('ケ', "ｹ");
		MAP_J2A_KANA.put('コ', "ｺ");
		MAP_J2A_KANA.put('サ', "ｻ");
		MAP_J2A_KANA.put('シ', "ｼ");
		MAP_J2A_KANA.put('ス', "ｽ");
		MAP_J2A_KANA.put('セ', "ｾ");
		MAP_J2A_KANA.put('ソ', "ｿ");
		MAP_J2A_KANA.put('タ', "ﾀ");
		MAP_J2A_KANA.put('チ', "ﾁ");
		MAP_J2A_KANA.put('ツ', "ﾂ");
		MAP_J2A_KANA.put('テ', "ﾃ");
		MAP_J2A_KANA.put('ト', "ﾄ");
		MAP_J2A_KANA.put('ナ', "ﾅ");
		MAP_J2A_KANA.put('ニ', "ﾆ");
		MAP_J2A_KANA.put('ヌ', "ﾇ");
		MAP_J2A_KANA.put('ネ', "ﾈ");
		MAP_J2A_KANA.put('ノ', "ﾉ");
		MAP_J2A_KANA.put('ハ', "ﾊ");
		MAP_J2A_KANA.put('ヒ', "ﾋ");
		MAP_J2A_KANA.put('フ', "ﾌ");
		MAP_J2A_KANA.put('ヘ', "ﾍ");
		MAP_J2A_KANA.put('ホ', "ﾎ");
		MAP_J2A_KANA.put('マ', "ﾏ");
		MAP_J2A_KANA.put('ミ', "ﾐ");
		MAP_J2A_KANA.put('ム', "ﾑ");
		MAP_J2A_KANA.put('メ', "ﾒ");
		MAP_J2A_KANA.put('モ', "ﾓ");
		MAP_J2A_KANA.put('ヤ', "ﾔ");
		MAP_J2A_KANA.put('ユ', "ﾕ");
		MAP_J2A_KANA.put('ヨ', "ﾖ");
		MAP_J2A_KANA.put('ラ', "ﾗ");
		MAP_J2A_KANA.put('リ', "ﾘ");
		MAP_J2A_KANA.put('ル', "ﾙ");
		MAP_J2A_KANA.put('レ', "ﾚ");
		MAP_J2A_KANA.put('ロ', "ﾛ");
		MAP_J2A_KANA.put('ワ', "ﾜ");
		MAP_J2A_KANA.put('ヲ', "ｦ");
		MAP_J2A_KANA.put('ン', "ﾝ");
		MAP_J2A_KANA.put('ャ', "ｬ");
		MAP_J2A_KANA.put('ュ', "ｭ");
		MAP_J2A_KANA.put('ョ', "ｮ");
		MAP_J2A_KANA.put('ッ', "ｯ");
		MAP_J2A_KANA.put('ァ', "ｧ");
		MAP_J2A_KANA.put('ィ', "ｨ");
		MAP_J2A_KANA.put('ゥ', "ｩ");
		MAP_J2A_KANA.put('ェ', "ｪ");
		MAP_J2A_KANA.put('ォ', "ｫ");
		MAP_J2A_KANA.put('ガ', "ｶﾞ");
		MAP_J2A_KANA.put('ギ', "ｷﾞ");
		MAP_J2A_KANA.put('グ', "ｸﾞ");
		MAP_J2A_KANA.put('ゲ', "ｹﾞ");
		MAP_J2A_KANA.put('ゴ', "ｺﾞ");
		MAP_J2A_KANA.put('ザ', "ｻﾞ");
		MAP_J2A_KANA.put('ジ', "ｼﾞ");
		MAP_J2A_KANA.put('ズ', "ｽﾞ");
		MAP_J2A_KANA.put('ゼ', "ｾﾞ");
		MAP_J2A_KANA.put('ゾ', "ｿﾞ");
		MAP_J2A_KANA.put('ダ', "ﾀﾞ");
		MAP_J2A_KANA.put('ヂ', "ﾁﾞ");
		MAP_J2A_KANA.put('ヅ', "ﾂﾞ");
		MAP_J2A_KANA.put('デ', "ﾃﾞ");
		MAP_J2A_KANA.put('ド', "ﾄﾞ");
		MAP_J2A_KANA.put('バ', "ﾊﾞ");
		MAP_J2A_KANA.put('ビ', "ﾋﾞ");
		MAP_J2A_KANA.put('ブ', "ﾌﾞ");
		MAP_J2A_KANA.put('ベ', "ﾍﾞ");
		MAP_J2A_KANA.put('ボ', "ﾎﾞ");
		MAP_J2A_KANA.put('パ', "ﾊﾟ");
		MAP_J2A_KANA.put('ピ', "ﾋﾟ");
		MAP_J2A_KANA.put('プ', "ﾌﾟ");
		MAP_J2A_KANA.put('ペ', "ﾍﾟ");
		MAP_J2A_KANA.put('ポ', "ﾎﾟ");
		MAP_J2A_KANA.put('ヴ', "ｳﾞ");
		MAP_J2A_KANA.put('ヵ', "ｶ");
		MAP_J2A_KANA.put('ヶ', "ｹ");
	}

	/** 半角-全角変換マップ(かな) */
	private static final Map<String, Character> MAP_A2J_KANA;
	static {
		// 濁点文字列が含まれるため、優先順位保証が必要であり、LinkedHashMapを利用する
		MAP_A2J_KANA = new LinkedHashMap<>();
		MAP_A2J_KANA.put("ｶﾞ", 'ガ');
		MAP_A2J_KANA.put("ｷﾞ", 'ギ');
		MAP_A2J_KANA.put("ｸﾞ", 'グ');
		MAP_A2J_KANA.put("ｹﾞ", 'ゲ');
		MAP_A2J_KANA.put("ｺﾞ", 'ゴ');
		MAP_A2J_KANA.put("ｻﾞ", 'ザ');
		MAP_A2J_KANA.put("ｼﾞ", 'ジ');
		MAP_A2J_KANA.put("ｽﾞ", 'ズ');
		MAP_A2J_KANA.put("ｾﾞ", 'ゼ');
		MAP_A2J_KANA.put("ｿﾞ", 'ゾ');
		MAP_A2J_KANA.put("ﾀﾞ", 'ダ');
		MAP_A2J_KANA.put("ﾁﾞ", 'ヂ');
		MAP_A2J_KANA.put("ﾂﾞ", 'ヅ');
		MAP_A2J_KANA.put("ﾃﾞ", 'デ');
		MAP_A2J_KANA.put("ﾄﾞ", 'ド');
		MAP_A2J_KANA.put("ﾊﾞ", 'バ');
		MAP_A2J_KANA.put("ﾋﾞ", 'ビ');
		MAP_A2J_KANA.put("ﾌﾞ", 'ブ');
		MAP_A2J_KANA.put("ﾍﾞ", 'ベ');
		MAP_A2J_KANA.put("ﾎﾞ", 'ボ');
		MAP_A2J_KANA.put("ﾊﾟ", 'パ');
		MAP_A2J_KANA.put("ﾋﾟ", 'ピ');
		MAP_A2J_KANA.put("ﾌﾟ", 'プ');
		MAP_A2J_KANA.put("ﾍﾟ", 'ペ');
		MAP_A2J_KANA.put("ﾎﾟ", 'ポ');
		MAP_A2J_KANA.put("ｳﾞ", 'ヴ');
		MAP_A2J_KANA.put("ｱ", 'ア');
		MAP_A2J_KANA.put("ｲ", 'イ');
		MAP_A2J_KANA.put("ｳ", 'ウ');
		MAP_A2J_KANA.put("ｴ", 'エ');
		MAP_A2J_KANA.put("ｵ", 'オ');
		MAP_A2J_KANA.put("ｶ", 'カ');
		MAP_A2J_KANA.put("ｷ", 'キ');
		MAP_A2J_KANA.put("ｸ", 'ク');
		MAP_A2J_KANA.put("ｹ", 'ケ');
		MAP_A2J_KANA.put("ｺ", 'コ');
		MAP_A2J_KANA.put("ｻ", 'サ');
		MAP_A2J_KANA.put("ｼ", 'シ');
		MAP_A2J_KANA.put("ｽ", 'ス');
		MAP_A2J_KANA.put("ｾ", 'セ');
		MAP_A2J_KANA.put("ｿ", 'ソ');
		MAP_A2J_KANA.put("ﾀ", 'タ');
		MAP_A2J_KANA.put("ﾁ", 'チ');
		MAP_A2J_KANA.put("ﾂ", 'ツ');
		MAP_A2J_KANA.put("ﾃ", 'テ');
		MAP_A2J_KANA.put("ﾄ", 'ト');
		MAP_A2J_KANA.put("ﾅ", 'ナ');
		MAP_A2J_KANA.put("ﾆ", 'ニ');
		MAP_A2J_KANA.put("ﾇ", 'ヌ');
		MAP_A2J_KANA.put("ﾈ", 'ネ');
		MAP_A2J_KANA.put("ﾉ", 'ノ');
		MAP_A2J_KANA.put("ﾊ", 'ハ');
		MAP_A2J_KANA.put("ﾋ", 'ヒ');
		MAP_A2J_KANA.put("ﾌ", 'フ');
		MAP_A2J_KANA.put("ﾍ", 'ヘ');
		MAP_A2J_KANA.put("ﾎ", 'ホ');
		MAP_A2J_KANA.put("ﾏ", 'マ');
		MAP_A2J_KANA.put("ﾐ", 'ミ');
		MAP_A2J_KANA.put("ﾑ", 'ム');
		MAP_A2J_KANA.put("ﾒ", 'メ');
		MAP_A2J_KANA.put("ﾓ", 'モ');
		MAP_A2J_KANA.put("ﾔ", 'ヤ');
		MAP_A2J_KANA.put("ﾕ", 'ユ');
		MAP_A2J_KANA.put("ﾖ", 'ヨ');
		MAP_A2J_KANA.put("ﾗ", 'ラ');
		MAP_A2J_KANA.put("ﾘ", 'リ');
		MAP_A2J_KANA.put("ﾙ", 'ル');
		MAP_A2J_KANA.put("ﾚ", 'レ');
		MAP_A2J_KANA.put("ﾛ", 'ロ');
		MAP_A2J_KANA.put("ﾜ", 'ワ');
		MAP_A2J_KANA.put("ｦ", 'ヲ');
		MAP_A2J_KANA.put("ﾝ", 'ン');
		MAP_A2J_KANA.put("ｬ", 'ャ');
		MAP_A2J_KANA.put("ｭ", 'ュ');
		MAP_A2J_KANA.put("ｮ", 'ョ');
		MAP_A2J_KANA.put("ｯ", 'ッ');
		MAP_A2J_KANA.put("ｧ", 'ァ');
		MAP_A2J_KANA.put("ｨ", 'ィ');
		MAP_A2J_KANA.put("ｩ", 'ゥ');
		MAP_A2J_KANA.put("ｪ", 'ェ');
		MAP_A2J_KANA.put("ｫ", 'ォ');
		MAP_A2J_KANA.put("ﾞ", '゛');
		MAP_A2J_KANA.put("ﾟ", '゜');
	}

	/** 全角-半角変換マップ(記号) */
	private static final Map<Character, Character> MAP_J2A_SIGN;
	static {
		MAP_J2A_SIGN = new HashMap<>();
		MAP_J2A_SIGN.put('　', ' ');
		MAP_J2A_SIGN.put('、', '､');
		MAP_J2A_SIGN.put('。', '｡');
		MAP_J2A_SIGN.put('，', ',');
		MAP_J2A_SIGN.put('．', '.');
		MAP_J2A_SIGN.put('・', '･');
		MAP_J2A_SIGN.put('：', ':');
		MAP_J2A_SIGN.put('；', ';');
		MAP_J2A_SIGN.put('？', '?');
		MAP_J2A_SIGN.put('！', '!');
		MAP_J2A_SIGN.put('゛', 'ﾞ');
		MAP_J2A_SIGN.put('゜', 'ﾟ');
		MAP_J2A_SIGN.put('´', '\'');
		MAP_J2A_SIGN.put('｀', '\'');
		MAP_J2A_SIGN.put('＾', '^');
		MAP_J2A_SIGN.put('￣', '~');
		MAP_J2A_SIGN.put('＿', '_');
		MAP_J2A_SIGN.put('ー', '-');
		MAP_J2A_SIGN.put('―', '-');
		MAP_J2A_SIGN.put('‐', '-');
		MAP_J2A_SIGN.put('／', '/');
		MAP_J2A_SIGN.put('＼', '\\');
		MAP_J2A_SIGN.put('～', '~');
		MAP_J2A_SIGN.put('∥', '|');
		MAP_J2A_SIGN.put('｜', '|');
		MAP_J2A_SIGN.put('‘', '\'');
		MAP_J2A_SIGN.put('’', '\'');
		MAP_J2A_SIGN.put('“', '\'');
		MAP_J2A_SIGN.put('”', '\'');
		MAP_J2A_SIGN.put('（', '(');
		MAP_J2A_SIGN.put('）', ')');
		MAP_J2A_SIGN.put('〔', '[');
		MAP_J2A_SIGN.put('〕', ']');
		MAP_J2A_SIGN.put('［', '[');
		MAP_J2A_SIGN.put('］', ']');
		MAP_J2A_SIGN.put('｛', '{');
		MAP_J2A_SIGN.put('｝', '}');
		MAP_J2A_SIGN.put('〈', '<');
		MAP_J2A_SIGN.put('〉', '>');
		MAP_J2A_SIGN.put('《', '<');
		MAP_J2A_SIGN.put('》', '>');
		MAP_J2A_SIGN.put('「', '｢');
		MAP_J2A_SIGN.put('」', '｣');
		MAP_J2A_SIGN.put('『', '｢');
		MAP_J2A_SIGN.put('』', '｣');
		MAP_J2A_SIGN.put('【', '(');
		MAP_J2A_SIGN.put('】', ')');
		MAP_J2A_SIGN.put('＋', '+');
		MAP_J2A_SIGN.put('－', '-');
		MAP_J2A_SIGN.put('×', '*');
		MAP_J2A_SIGN.put('÷', '/');
		MAP_J2A_SIGN.put('＝', '=');
		MAP_J2A_SIGN.put('＜', '<');
		MAP_J2A_SIGN.put('＞', '>');
		MAP_J2A_SIGN.put('≦', '<');
		MAP_J2A_SIGN.put('≧', '>');
		MAP_J2A_SIGN.put('°', 'ﾟ');
		MAP_J2A_SIGN.put('′', '\'');
		MAP_J2A_SIGN.put('″', '\'');
		MAP_J2A_SIGN.put('￥', '\\');
		MAP_J2A_SIGN.put('＄', '$');
		MAP_J2A_SIGN.put('％', '%');
		MAP_J2A_SIGN.put('＃', '#');
		MAP_J2A_SIGN.put('＆', '&');
		MAP_J2A_SIGN.put('＊', '*');
		MAP_J2A_SIGN.put('＠', '@');
		MAP_J2A_SIGN.put('≪', '<');
		MAP_J2A_SIGN.put('≫', '>');
	}

	/** 半角-全角変換マップ(記号) */
	private static final Map<Character, Character> MAP_A2J_SIGN;
	static {
		MAP_A2J_SIGN = new HashMap<>();
		MAP_A2J_SIGN.put(' ', '　');
		MAP_A2J_SIGN.put('､', '、');
		MAP_A2J_SIGN.put('｡', '。');
		MAP_A2J_SIGN.put(',', '，');
		MAP_A2J_SIGN.put('.', '．');
		MAP_A2J_SIGN.put('･', '・');
		MAP_A2J_SIGN.put(':', '：');
		MAP_A2J_SIGN.put(';', '；');
		MAP_A2J_SIGN.put('?', '？');
		MAP_A2J_SIGN.put('!', '！');
		MAP_A2J_SIGN.put('ﾞ', '゛');
		MAP_A2J_SIGN.put('ﾟ', '゜');
		//	MAP_A2J_SIGN.put(''', '´');
		//	MAP_A2J_SIGN.put(''', '｀');
		MAP_A2J_SIGN.put('^', '＾');
		MAP_A2J_SIGN.put('~', '￣');
		MAP_A2J_SIGN.put('_', '＿');
		//	MAP_A2J_SIGN.put('-', 'ー');
		MAP_A2J_SIGN.put('-', '―');
		//	MAP_A2J_SIGN.put('-', '‐');
		MAP_A2J_SIGN.put('/', '／');
		//	MAP_A2J_SIGN.put('\\', '＼');
		//	MAP_A2J_SIGN.put('~', '～');
		//	MAP_A2J_SIGN.put('|', '∥');
		MAP_A2J_SIGN.put('|', '｜');
		//	MAP_A2J_SIGN.put(''', '‘');
		MAP_A2J_SIGN.put('\'', '’');
		//	MAP_A2J_SIGN.put('\'', '“');
		MAP_A2J_SIGN.put('"', '”');
		MAP_A2J_SIGN.put('(', '（');
		MAP_A2J_SIGN.put(')', '）');
		//	MAP_A2J_SIGN.put('[', '〔');
		//	MAP_A2J_SIGN.put(']', '〕');
		MAP_A2J_SIGN.put('[', '［');
		MAP_A2J_SIGN.put(']', '］');
		MAP_A2J_SIGN.put('{', '｛');
		MAP_A2J_SIGN.put('}', '｝');
		//	MAP_A2J_SIGN.put('<', '〈');
		//	MAP_A2J_SIGN.put('>', '〉');
		//	MAP_A2J_SIGN.put('<', '《');
		//	MAP_A2J_SIGN.put('>', '》');
		MAP_A2J_SIGN.put('｢', '「');
		MAP_A2J_SIGN.put('｣', '」');
		//	MAP_A2J_SIGN.put('｢', '『');
		//	MAP_A2J_SIGN.put('｣', '』');
		//	MAP_A2J_SIGN.put('(', '【');
		//	MAP_A2J_SIGN.put(')', '】');
		MAP_A2J_SIGN.put('+', '＋');
		MAP_A2J_SIGN.put('-', '－');
		//	MAP_A2J_SIGN.put('*', '×');
		//	MAP_A2J_SIGN.put('/', '÷');
		MAP_A2J_SIGN.put('=', '＝');
		MAP_A2J_SIGN.put('<', '＜');
		MAP_A2J_SIGN.put('>', '＞');
		//	MAP_A2J_SIGN.put('<', '≦');
		//	MAP_A2J_SIGN.put('>', '≧');
		//	MAP_A2J_SIGN.put('ﾟ', '°');
		//	MAP_A2J_SIGN.put(''', '′');
		//	MAP_A2J_SIGN.put('\'', '″');
		MAP_A2J_SIGN.put('\\', '￥');
		MAP_A2J_SIGN.put('$', '＄');
		MAP_A2J_SIGN.put('%', '％');
		MAP_A2J_SIGN.put('#', '＃');
		MAP_A2J_SIGN.put('&', '＆');
		MAP_A2J_SIGN.put('*', '＊');
		MAP_A2J_SIGN.put('@', '＠');
		//	MAP_A2J_SIGN.put('<', '≪');
		//	MAP_A2J_SIGN.put('>', '≫');
	}

	/** ひらがな-カタカナ変換マップ */
	private static final Map<Character, Character> MAP_HIRA2KANA;
	static {
		MAP_HIRA2KANA = new HashMap<>();
		MAP_HIRA2KANA.put('あ', 'ア');
		MAP_HIRA2KANA.put('い', 'イ');
		MAP_HIRA2KANA.put('う', 'ウ');
		MAP_HIRA2KANA.put('え', 'エ');
		MAP_HIRA2KANA.put('お', 'オ');
		MAP_HIRA2KANA.put('か', 'カ');
		MAP_HIRA2KANA.put('き', 'キ');
		MAP_HIRA2KANA.put('く', 'ク');
		MAP_HIRA2KANA.put('け', 'ケ');
		MAP_HIRA2KANA.put('こ', 'コ');
		MAP_HIRA2KANA.put('さ', 'サ');
		MAP_HIRA2KANA.put('し', 'シ');
		MAP_HIRA2KANA.put('す', 'ス');
		MAP_HIRA2KANA.put('せ', 'セ');
		MAP_HIRA2KANA.put('そ', 'ソ');
		MAP_HIRA2KANA.put('た', 'タ');
		MAP_HIRA2KANA.put('ち', 'チ');
		MAP_HIRA2KANA.put('つ', 'ツ');
		MAP_HIRA2KANA.put('て', 'テ');
		MAP_HIRA2KANA.put('と', 'ト');
		MAP_HIRA2KANA.put('な', 'ナ');
		MAP_HIRA2KANA.put('に', 'ニ');
		MAP_HIRA2KANA.put('ぬ', 'ヌ');
		MAP_HIRA2KANA.put('ね', 'ネ');
		MAP_HIRA2KANA.put('の', 'ノ');
		MAP_HIRA2KANA.put('は', 'ハ');
		MAP_HIRA2KANA.put('ひ', 'ヒ');
		MAP_HIRA2KANA.put('ふ', 'フ');
		MAP_HIRA2KANA.put('へ', 'ヘ');
		MAP_HIRA2KANA.put('ほ', 'ホ');
		MAP_HIRA2KANA.put('ま', 'マ');
		MAP_HIRA2KANA.put('み', 'ミ');
		MAP_HIRA2KANA.put('む', 'ム');
		MAP_HIRA2KANA.put('め', 'メ');
		MAP_HIRA2KANA.put('も', 'モ');
		MAP_HIRA2KANA.put('や', 'ヤ');
		MAP_HIRA2KANA.put('ゆ', 'ユ');
		MAP_HIRA2KANA.put('よ', 'ヨ');
		MAP_HIRA2KANA.put('ら', 'ラ');
		MAP_HIRA2KANA.put('り', 'リ');
		MAP_HIRA2KANA.put('る', 'ル');
		MAP_HIRA2KANA.put('れ', 'レ');
		MAP_HIRA2KANA.put('ろ', 'ロ');
		MAP_HIRA2KANA.put('わ', 'ワ');
		MAP_HIRA2KANA.put('を', 'ヲ');
		MAP_HIRA2KANA.put('ん', 'ン');
		MAP_HIRA2KANA.put('ゃ', 'ャ');
		MAP_HIRA2KANA.put('ゅ', 'ュ');
		MAP_HIRA2KANA.put('ょ', 'ョ');
		MAP_HIRA2KANA.put('っ', 'ッ');
		MAP_HIRA2KANA.put('ぁ', 'ァ');
		MAP_HIRA2KANA.put('ぃ', 'ィ');
		MAP_HIRA2KANA.put('ぅ', 'ゥ');
		MAP_HIRA2KANA.put('ぇ', 'ェ');
		MAP_HIRA2KANA.put('ぉ', 'ォ');
		MAP_HIRA2KANA.put('が', 'ガ');
		MAP_HIRA2KANA.put('ぎ', 'ギ');
		MAP_HIRA2KANA.put('ぐ', 'グ');
		MAP_HIRA2KANA.put('げ', 'ゲ');
		MAP_HIRA2KANA.put('ご', 'ゴ');
		MAP_HIRA2KANA.put('ざ', 'ザ');
		MAP_HIRA2KANA.put('じ', 'ジ');
		MAP_HIRA2KANA.put('ず', 'ズ');
		MAP_HIRA2KANA.put('ぜ', 'ゼ');
		MAP_HIRA2KANA.put('ぞ', 'ゾ');
		MAP_HIRA2KANA.put('だ', 'ダ');
		MAP_HIRA2KANA.put('ぢ', 'ヂ');
		MAP_HIRA2KANA.put('づ', 'ヅ');
		MAP_HIRA2KANA.put('で', 'デ');
		MAP_HIRA2KANA.put('ど', 'ド');
		MAP_HIRA2KANA.put('ば', 'バ');
		MAP_HIRA2KANA.put('び', 'ビ');
		MAP_HIRA2KANA.put('ぶ', 'ブ');
		MAP_HIRA2KANA.put('べ', 'ベ');
		MAP_HIRA2KANA.put('ぼ', 'ボ');
		MAP_HIRA2KANA.put('ぱ', 'パ');
		MAP_HIRA2KANA.put('ぴ', 'ピ');
		MAP_HIRA2KANA.put('ぷ', 'プ');
		MAP_HIRA2KANA.put('ぺ', 'ペ');
		MAP_HIRA2KANA.put('ぽ', 'ポ');
	}

	/** カタカナ-ひらがな変換マップ */
	private static final Map<Character, Character> MAP_KANA2HIRA;
	static {
		MAP_KANA2HIRA = new HashMap<>();
		MAP_KANA2HIRA.put('ア', 'あ');
		MAP_KANA2HIRA.put('イ', 'い');
		MAP_KANA2HIRA.put('ウ', 'う');
		MAP_KANA2HIRA.put('エ', 'え');
		MAP_KANA2HIRA.put('オ', 'お');
		MAP_KANA2HIRA.put('カ', 'か');
		MAP_KANA2HIRA.put('キ', 'き');
		MAP_KANA2HIRA.put('ク', 'く');
		MAP_KANA2HIRA.put('ケ', 'け');
		MAP_KANA2HIRA.put('コ', 'こ');
		MAP_KANA2HIRA.put('サ', 'さ');
		MAP_KANA2HIRA.put('シ', 'し');
		MAP_KANA2HIRA.put('ス', 'す');
		MAP_KANA2HIRA.put('セ', 'せ');
		MAP_KANA2HIRA.put('ソ', 'そ');
		MAP_KANA2HIRA.put('タ', 'た');
		MAP_KANA2HIRA.put('チ', 'ち');
		MAP_KANA2HIRA.put('ツ', 'つ');
		MAP_KANA2HIRA.put('テ', 'て');
		MAP_KANA2HIRA.put('ト', 'と');
		MAP_KANA2HIRA.put('ナ', 'な');
		MAP_KANA2HIRA.put('ニ', 'に');
		MAP_KANA2HIRA.put('ヌ', 'ぬ');
		MAP_KANA2HIRA.put('ネ', 'ね');
		MAP_KANA2HIRA.put('ノ', 'の');
		MAP_KANA2HIRA.put('ハ', 'は');
		MAP_KANA2HIRA.put('ヒ', 'ひ');
		MAP_KANA2HIRA.put('フ', 'ふ');
		MAP_KANA2HIRA.put('ヘ', 'へ');
		MAP_KANA2HIRA.put('ホ', 'ほ');
		MAP_KANA2HIRA.put('マ', 'ま');
		MAP_KANA2HIRA.put('ミ', 'み');
		MAP_KANA2HIRA.put('ム', 'む');
		MAP_KANA2HIRA.put('メ', 'め');
		MAP_KANA2HIRA.put('モ', 'も');
		MAP_KANA2HIRA.put('ヤ', 'や');
		MAP_KANA2HIRA.put('ユ', 'ゆ');
		MAP_KANA2HIRA.put('ヨ', 'よ');
		MAP_KANA2HIRA.put('ラ', 'ら');
		MAP_KANA2HIRA.put('リ', 'り');
		MAP_KANA2HIRA.put('ル', 'る');
		MAP_KANA2HIRA.put('レ', 'れ');
		MAP_KANA2HIRA.put('ロ', 'ろ');
		MAP_KANA2HIRA.put('ワ', 'わ');
		MAP_KANA2HIRA.put('ヲ', 'を');
		MAP_KANA2HIRA.put('ン', 'ん');
		MAP_KANA2HIRA.put('ャ', 'ゃ');
		MAP_KANA2HIRA.put('ュ', 'ゅ');
		MAP_KANA2HIRA.put('ョ', 'ょ');
		MAP_KANA2HIRA.put('ッ', 'っ');
		MAP_KANA2HIRA.put('ァ', 'ぁ');
		MAP_KANA2HIRA.put('ィ', 'ぃ');
		MAP_KANA2HIRA.put('ゥ', 'ぅ');
		MAP_KANA2HIRA.put('ェ', 'ぇ');
		MAP_KANA2HIRA.put('ォ', 'ぉ');
		MAP_KANA2HIRA.put('ガ', 'が');
		MAP_KANA2HIRA.put('ギ', 'ぎ');
		MAP_KANA2HIRA.put('グ', 'ぐ');
		MAP_KANA2HIRA.put('ゲ', 'げ');
		MAP_KANA2HIRA.put('ゴ', 'ご');
		MAP_KANA2HIRA.put('ザ', 'ざ');
		MAP_KANA2HIRA.put('ジ', 'じ');
		MAP_KANA2HIRA.put('ズ', 'ず');
		MAP_KANA2HIRA.put('ゼ', 'ぜ');
		MAP_KANA2HIRA.put('ゾ', 'ぞ');
		MAP_KANA2HIRA.put('ダ', 'だ');
		MAP_KANA2HIRA.put('ヂ', 'ぢ');
		MAP_KANA2HIRA.put('ヅ', 'づ');
		MAP_KANA2HIRA.put('デ', 'で');
		MAP_KANA2HIRA.put('ド', 'ど');
		MAP_KANA2HIRA.put('バ', 'ば');
		MAP_KANA2HIRA.put('ビ', 'び');
		MAP_KANA2HIRA.put('ブ', 'ぶ');
		MAP_KANA2HIRA.put('ベ', 'べ');
		MAP_KANA2HIRA.put('ボ', 'ぼ');
		MAP_KANA2HIRA.put('パ', 'ぱ');
		MAP_KANA2HIRA.put('ピ', 'ぴ');
		MAP_KANA2HIRA.put('プ', 'ぷ');
		MAP_KANA2HIRA.put('ペ', 'ぺ');
		MAP_KANA2HIRA.put('ポ', 'ぽ');
	}

	/** 不明なレガシー全角文字 */
	public static final String UNKOWN_LERGACY_JCHAR = "〓";

	/** 不明なレガシー半角文字 */
	public static final String UNKOWN_LEGACY_ACHAR = "?";

	/** EBCDICデコードSJISコードマッピング配列(0x00～0xFF) */
	private static final int EBCDIC_SJIS_MAP[] = new int[] { //
			//
			0, 1, 2, 3, 156, 9, 134, 127, 151, 141, 142, 11, 12, 13, 14, 15, //
			16, 17, 18, 19, 157, 10, 8, 135, 24, 25, 146, 143, 28, 29, 30, 31, //
			128, 129, 130, 131, 132, 133, 23, 27, 136, 137, 138, 139, 140, 5, 6, 7, //
			144, 145, 22, 147, 148, 149, 150, 4, 152, 153, 154, 155, 20, 21, 158, 26, //
			32, 160, 161, 162, 163, 164, 165, 166, 167, 168, 91, 46, 60, 40, 43, 33, //
			38, 169, 170, 171, 172, 173, 174, 175, 176, 177, 93, 92, 42, 41, 59, 94, //
			45, 47, 178, 179, 180, 181, 182, 183, 184, 185, 124, 44, 37, 95, 62, 63, //
			186, 187, 188, 189, 190, 191, 192, 193, 194, 96, 58, 35, 64, 39, 61, 34, //
			195, 97, 98, 99, 100, 101, 102, 103, 104, 105, 196, 197, 198, 199, 200, 201, //
			202, 106, 107, 108, 109, 110, 111, 112, 113, 114, 203, 204, 205, 206, 207, 208, //
			209, 126, 115, 116, 117, 118, 119, 120, 121, 122, 210, 211, 212, 213, 214, 215, //
			216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231, //
			123, 65, 66, 67, 68, 69, 70, 71, 72, 73, 232, 233, 234, 235, 236, 237, //
			125, 74, 75, 76, 77, 78, 79, 80, 81, 82, 238, 239, 240, 241, 242, 243, //
			36, 32, 83, 84, 85, 86, 87, 88, 89, 90, 244, 245, 246, 247, 248, 249, //
			48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 250, 251, 252, 253, 254, 255, //
			//
	};

	/** EBCDIKデコードSJISコードマッピング配列(0x00～0xFF) */
	private static final int EBCDIK_SJIS_MAP[] = new int[] { //
			//
			0, 1, 2, 3, 156, 9, 134, 127, 151, 141, 142, 11, 12, 13, 14, 15, //
			16, 17, 18, 19, 157, 10, 8, 135, 24, 25, 146, 143, 28, 29, 30, 31, //
			128, 129, 130, 131, 132, 133, 23, 27, 136, 137, 138, 139, 140, 5, 6, 7, //
			144, 145, 22, 147, 148, 149, 150, 4, 152, 153, 154, 155, 20, 21, 158, 26, //
			32, 161, 162, 163, 164, 165, 166, 167, 168, 169, 91, 46, 60, 40, 43, 33, //
			38, 170, 171, 172, 173, 174, 175, 160, 176, 97, 93, 92, 42, 41, 59, 94, //
			45, 47, 98, 99, 100, 101, 102, 103, 104, 105, 124, 44, 37, 95, 62, 63, //
			106, 107, 108, 109, 110, 111, 112, 113, 114, 96, 58, 35, 64, 39, 61, 34, //
			115, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 116, 187, 188, 189, 190, //
			191, 192, 193, 194, 195, 196, 197, 198, 199, 200, 201, 117, 118, 202, 203, 204, //
			119, 126, 205, 206, 207, 208, 209, 210, 211, 212, 213, 120, 214, 215, 216, 217, //
			121, 122, 224, 225, 226, 227, 228, 229, 230, 231, 218, 219, 220, 221, 222, 223, //
			123, 65, 66, 67, 68, 69, 70, 71, 72, 73, 232, 233, 234, 235, 236, 237, //
			125, 74, 75, 76, 77, 78, 79, 80, 81, 82, 238, 239, 240, 241, 242, 243, //
			36, 159, 83, 84, 85, 86, 87, 88, 89, 250, 244, 245, 246, 247, 248, 249, //
			48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 250, 251, 252, 253, 254, 255, //
			//
	};

	/** EBCDIKデーコード外字マップ */
	private static final Map<String, String> EBCDIK_GAIJI_MAP;
	static {
		EBCDIK_GAIJI_MAP = new LinkedHashMap<>();
		try {
			Properties properties = PropertiesUtil.load("/" + StringUtil.class.getPackage().getName().replace('.', '/') + "/StringUtil-EBCDIK-Gaiji.properties");
			for (Object key : properties.keySet()) {
				EBCDIK_GAIJI_MAP.put((String) key, properties.getProperty((String) key));
			}
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	/** KEIS83デコード上位バイトSJIS変換先コード範囲定義(0x81～0x9F、0xE0～0xEF) */
	private static final int KEIS83_SJIS_MAP_UPPER[] = new int[] { //
			//
			129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, // 0x81～
			145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, // ～0x9F
			224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235, 236, 237, 238, 239, // 0xE0～0xEF
			//
	};

	/** KEIS83デコード下位バイトSJIS変換先コード範囲定義(0x40～0x7E、0x80～0xFC) */
	private static final int KEIS83_SJIS_MAP_LOWER[] = new int[] { //
			//
			64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, // 0x40～
			80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, //
			96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, //
			112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, // ～0x7E 
			128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, // 0x80～
			145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, //
			162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 178, //
			179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, //
			196, 197, 198, 199, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, //
			213, 214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, //
			230, 231, 232, 233, 234, 235, 236, 237, 238, 239, 240, 241, 242, 243, 244, 245, 246, //
			247, 248, 249, 250, 251, 252, // ～0xFC
			//
	};

	/**
	 * EBCDICコードタイプ列挙型クラス<br>
	 * 
	 * @author Kitagawa<br>
	 * 
	 *<!--
	 * 更新日		更新者			更新内容
	 * 2011/05/17	Kitagawa		新規作成
	 * 2018/05/02	Kitagawa		再構築(SourceForge.jpからGitHubへの移行に併せて全面改訂)
	 *-->
	 */
	public enum EBCDICType {

		/** EBCDIC */
		EBCDIC("EBCDIC"),

		/** EBCDIK */
		EBCDIK("EBCDIK"),

		;

		/** EBCDICタイプ */
		private String type;

		/**
		 * コンストラクタ<br>
		 * @param type EBCDICタイプ
		 */
		private EBCDICType(String type) {
			this.type = type;
		}

		/**
		 * EBCDICタイプを取得します。<br>
		 * @return EBCDICタイプ
		 */
		public String getType() {
			return type;
		}
	}

	/**
	 * コンストラクタ<br>
	 */
	private StringUtil() {
		super();
	}

	/**
	 * 任意のオブジェクトの{@link Object#toString()}メソッドを呼び出して文字列として提供します。<br>
	 * nullオブジェクトが指定された際には空文字列を返却し、{@link java.lang.NullPointerException}はスローしません。<br>
	 * また、オブジェクトの配列の場合は{@link java.util.Arrays#toString()}ではなく、再帰的に当メソッドにて文字列構築の処理が行われます。<br>
	 * このメソッドは主にデバッグログ等で利用することを想定したメソッドです。<br>
	 * @param object オブジェクト
	 * @return オブジェクトが提供する文字列
	 */
	public static String toString(Object object) {
		if (object == null) {
			return EMPTY;
		}
		String string = EMPTY;
		if (object.getClass().isArray()) {
			Class<?> clazz = object.getClass().getComponentType();
			if (clazz == boolean.class) {
				string = Arrays.toString((boolean[]) object);
			} else if (clazz == int.class) {
				string = Arrays.toString((int[]) object);
			} else if (clazz == long.class) {
				string = Arrays.toString((long[]) object);
			} else if (clazz == byte.class) {
				string = Arrays.toString((byte[]) object);
			} else if (clazz == short.class) {
				string = Arrays.toString((short[]) object);
			} else if (clazz == float.class) {
				string = Arrays.toString((float[]) object);
			} else if (clazz == double.class) {
				string = Arrays.toString((double[]) object);
			} else if (clazz == char.class) {
				string = Arrays.toString((char[]) object);
			} else {
				StringBuilder builder = new StringBuilder();
				builder.append("[");
				for (int i = 0; i <= ((Object[]) object).length - 1; i++) {
					Object e = ((Object[]) object)[i];
					String s = toString(e);
					builder.append(s);
					if (i < ((Object[]) object).length - 1) {
						builder.append(", ");
					}
				}
				builder.append("]");
				string = builder.toString();
			}
		} else {
			string = object.toString();
		}
		return string;
	}

	/**
	 * バイト値を16進文字列で提供します。<br>
	 * @param b 対象バイト値
	 * @return 16進文字列
	 */
	public static String toHex(byte b) {
		int l = b & 0xf;
		int s = (b & 0xf0) >> 4;
		return HEX_STRINGS[s] + HEX_STRINGS[l];
	}

	/**
	 * バイト値配列を16進文字列で提供します。<br>
	 * nullオブジェクトが指定された際には空文字列を返却し、{@link java.lang.NullPointerException}はスローしません。<br>
	 * @param bs 対象バイト値配列
	 * @return 16進数編集をした16進文字列
	 */
	public static String toHex(byte... bs) {
		if (bs == null) {
			return EMPTY;
		}
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < bs.length; i++) {
			builder.append(toHex(bs[i]));
		}
		return builder.toString();
	}

	/**
	 * 16進数表記文字列(先頭"0x"は不要)をバイト配列に変換して提供します。<br>
	 * nullオブジェクトが指定された際には空バイト配列を返却し、{@link java.lang.NullPointerException}はスローしません。<br>
	 * @param string 対象文字列
	 * @return 変換されたバイト配列
	 */
	public static byte[] toBytesByHex(String string) {
		if (isEmpty(string)) {
			return new byte[0];
		}
		byte[] bytes = new byte[string.length() / 2];
		for (int index = 0; index < bytes.length; index++) {
			bytes[index] = (byte) Integer.parseInt(string.substring(index * 2, (index + 1) * 2), 16);
		}
		return bytes;
	}

	/**
	 * スネークケース文字列(snake_case)からキャメルケース文字列(camelCase)に変換して提供します。<br>
	 * nullオブジェクトが指定された際には空文字列を返却し、{@link java.lang.NullPointerException}はスローしません。<br>
	 * <b>Microsoft ExcelのPROPER関数を利用した場合、数字後の文字が大文字化されますが、当該メソッドでは標準挙動としている為、大文字化されない事に注意して下さい。</b><br>
	 * @param string 対象文字列
	 * @return ロアーキャメルケース文字列
	 */
	public static String toCamelCase(String string) {
		if (isEmpty(string)) {
			return EMPTY;
		}

		/*
		 * 先頭/末端アンダースコア除去("_foo_bar"→"foo_bar")
		 */
		string = string.replaceAll("^_+", "");
		string = string.replaceAll("_+$", "");

		/*
		 * スネークケース部変換
		 */
		Matcher matcher = Pattern.compile("(_+)([a-z0-9])").matcher(string.toLowerCase());
		StringBuffer buffer = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(buffer, matcher.group(2).toUpperCase());
		}
		matcher.appendTail(buffer);

		return buffer.toString();
	}

	/**
	 * スネークケース文字列(snake_case)からパスカルケース(アッパーキャメルケース)文字列(CamelCase)に変換して提供します。<br>
	 * nullオブジェクトが指定された際には空文字列を返却し、{@link java.lang.NullPointerException}はスローしません。<br>
	 * <b>Microsoft ExcelのPROPER関数を利用した場合、数字後の文字が大文字化されますが、当該メソッドでは標準挙動としている為、大文字化されない事に注意して下さい。</b><br>
	 * @param string 対象文字列
	 * @return アッパーキャメルケース(アッパーキャメルケース)文字列
	 */
	public static String toPascalCase(String string) {
		String buffer = toCamelCase(string);
		return buffer.length() <= 0 ? buffer : buffer.substring(0, 1).toUpperCase() + buffer.substring(1);
	}

	/**
	 * キャメルケース文字列(camelCase)からスネークケース文字列(snake_case)に変換します。<br>
	 * nullオブジェクトが指定された際には空文字列を返却し、{@link java.lang.NullPointerException}はスローしません。<br>
	 * また、数値が間に入る場合、当処理では前後依存を判断する事が出来ない為、一律手前に寄せた形の変換となる事に注意して下さい("foo0Bar"→"foo0_bar")。<br>
	 * @param string 対象文字列
	 * @param upper 大文字化して返却する場合にtrueを返却
	 * @return スネークケース(snake_case)文字列
	 */
	public static String toSnakeCase(String string, boolean upper) {
		if (isEmpty(string)) {
			return EMPTY;
		}
		String buffer = string;
		buffer = buffer.replaceAll("([A-Z0-9]+)([A-Z0-9][a-z0-9])", "$1_$2");
		buffer = buffer.replaceAll("([a-z])([A-Z])", "$1_$2");
		if (upper) {
			buffer = buffer.toUpperCase();
		} else {
			buffer = buffer.toLowerCase();
		}
		return buffer;
	}

	/**
	 * キャメルケース文字列(camelCase)からスネークケース文字列(snake_case)に変換します。<br>
	 * nullオブジェクトが指定された際には空文字列を返却し、{@link java.lang.NullPointerException}はスローしません。<br>
	 * また、数値が間に入る場合、当処理では前後依存を判断する事が出来ない為、一律手前に寄せた形の変換となる事に注意して下さい("foo0Bar"→"foo0_bar")。<br>
	 * @param string 対象文字列
	 * @return スネークケース(snake_case)文字列
	 */
	public static String toSnakeCase(String string) {
		return toSnakeCase(string, false);
	}

	/**
	 * キャメルケース文字列(camelCase)からチェーンケース文字列(chain-case)に変換します。<br>
	 * nullオブジェクトが指定された際には空文字列を返却し、{@link java.lang.NullPointerException}はスローしません。<br>
	 * また、数値が間に入る場合、当処理では前後依存を判断する事が出来ない為、一律手前に寄せた形の変換となる事に注意して下さい("foo0Bar"→"foo0_bar")。<br>
	 * @param string 対象文字列
	 * @param upper 大文字化して返却する場合にtrueを返却
	 * @return チェーンケース(chain-case)文字列
	 */
	public static String toChainCase(String string, boolean upper) {
		if (isEmpty(string)) {
			return EMPTY;
		}
		String buffer = string;
		buffer = buffer.replaceAll("([A-Z0-9]+)([A-Z0-9][a-z0-9])", "$1-$2");
		buffer = buffer.replaceAll("([a-z])([A-Z])", "$1-$2");
		if (upper) {
			buffer = buffer.toUpperCase();
		} else {
			buffer = buffer.toLowerCase();
		}
		return buffer;
	}

	/**
	 * キャメルケース文字列(camelCase)からチェーンケース文字列(chain-case)に変換します。<br>
	 * nullオブジェクトが指定された際には空文字列を返却し、{@link java.lang.NullPointerException}はスローしません。<br>
	 * また、数値が間に入る場合、当処理では前後依存を判断する事が出来ない為、一律手前に寄せた形の変換となる事に注意して下さい("foo0Bar"→"foo0_bar")。<br>
	 * @param string 対象文字列
	 * @return チェーンケース(chain-case)文字列
	 */
	public static String toChainCase(String string) {
		return toChainCase(string, false);
	}

	/**
	 * 数値文字列を{@link java.math.BigDecimal}に変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、nullオブジェクトが返却されます。<br>
	 * @param string 数値文字列
	 * @param pattern 数値文字列書式
	 * @return {@link java.math.BigDecimal}オブジェクト
	 */
	public static BigDecimal toBigDecimal(String string, String pattern) {
		if (isBlank(string)) {
			return null;
		}
		if (!isBlank(pattern)) {
			DecimalFormat format = new DecimalFormat(pattern);
			format.setParseBigDecimal(true);
			BigDecimal decimal = null;
			try {
				decimal = (BigDecimal) format.parse(string);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
			return decimal;
		} else {
			DecimalFormat format = new DecimalFormat();
			format.setParseBigDecimal(true);
			try {
				return (BigDecimal) format.parse(string.trim().toString());
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * 数値文字列を{@link java.math.BigDecimal}に変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、nullオブジェクトが返却されます。<br>
	 * @param string 対象文字列
	 * @return {@link java.math.BigDecimal}オブジェクト
	 */
	public static BigDecimal toBigDecimal(String string) {
		return toBigDecimal(string, null);
	}

	/**
	 * 配列内の数値文字列を{@link java.math.BigDecimal}に変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、nullオブジェクトが返却されます。<br>
	 * @param strings 対象文字列配列
	 * @param pattern 数値文字列書式
	 * @return {@link java.math.BigDecimal}オブジェクト
	 */
	public static BigDecimal[] toBigDecimal(String[] strings, String pattern) {
		if (strings == null) {
			return new BigDecimal[0];
		}
		List<BigDecimal> list = new LinkedList<>();
		for (String value : strings) {
			list.add(toBigDecimal(value, pattern));
		}
		return list.toArray(new BigDecimal[0]);
	}

	/**
	 * 配列内の数値文字列を{@link java.math.BigDecimal}に変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、nullオブジェクトが返却されます。<br>
	 * @param strings 対象文字列配列
	 * @return {@link java.math.BigDecimal}オブジェクト配列
	 */
	public static BigDecimal[] toBigDecimal(String[] strings) {
		return toBigDecimal(strings, null);
	}

	/**
	 * 真偽値を表す文字列を{@link java.lang.Boolean}に変換します。<br>
	 * このメソッドでは大文字、小文字の区別なく"true"に合致したものをtrue値とし、以外のものをfalseとして返却します。<br>
	 * 但し、文字列がnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、nullオブジェクトが返却されます。<br>
	 * @param string 対象文字列
	 * @return {@link java.lang.Boolean}オブジェクト
	 */
	public static Boolean toBoolean(String string) {
		return StringUtil.isBlank(string) ? null : "true".equalsIgnoreCase(string);
	}

	/**
	 * 配列内の真偽値を表す文字列を{@link java.lang.Boolean}に変換します。<br>
	 * このメソッドでは大文字、小文字の区別なく"true"に合致したものをtrue値とし、以外のものをfalseとして返却します。<br>
	 * 但し、文字列がnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、nullオブジェクトが返却されます。<br>
	 * @param strings 対象文字列配列
	 * @return {@link java.lang.Boolean}オブジェクト配列
	 */
	public static Boolean[] toBoolean(String[] strings) {
		if (strings == null) {
			return new Boolean[0];
		}
		List<Boolean> list = new LinkedList<>();
		for (String value : strings) {
			list.add(toBoolean(value));
		}
		return list.toArray(new Boolean[0]);
	}

	/**
	 * 数値文字列を{@link java.lang.Long}に変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、nullオブジェクトが返却されます。<br>
	 * @param string 対象文字列
	 * @param pattern 数値文字列書式
	 * @return {@link java.lang.Long}オブジェクト
	 */
	public static Long toLong(String string, String pattern) {
		BigDecimal decimal = toBigDecimal(string, pattern);
		return decimal == null ? null : decimal.longValue();
	}

	/**
	 * 数値文字列を{@link java.lang.Long}に変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、nullオブジェクトが返却されます。<br>
	 * @param string 対象文字列
	 * @return {@link java.lang.Long}オブジェクト
	 */
	public static Long toLong(String string) {
		return toLong(string, null);
	}

	/**
	 * 配列内の数値文字列を{@link java.lang.Long}に変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、nullオブジェクトが返却されます。<br>
	 * @param strings 対象文字列配列
	 * @param pattern 数値文字列書式
	 * @return {@link java.lang.Long}オブジェクト配列
	 */
	public static Long[] toLong(String[] strings, String pattern) {
		if (strings == null) {
			return new Long[0];
		}
		List<Long> list = new LinkedList<>();
		for (String value : strings) {
			list.add(toLong(value, pattern));
		}
		return list.toArray(new Long[0]);
	}

	/**
	 * 配列内の数値文字列を{@link java.lang.Long}に変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、nullオブジェクトが返却されます。<br>
	 * @param strings 対象文字列配列
	 * @return {@link java.lang.Long}オブジェクト配列
	 */
	public static Long[] toLong(String[] strings) {
		return toLong(strings, null);
	}

	/**
	 * 数値文字列を{@link java.lang.Integer}に変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、nullオブジェクトが返却されます。<br>
	 * @param string 対象文字列
	 * @param pattern 数値文字列書式
	 * @return {@link java.lang.Integer}オブジェクト
	 */
	public static Integer toInteger(String string, String pattern) {
		BigDecimal decimal = toBigDecimal(string, pattern);
		return decimal == null ? null : decimal.intValue();
	}

	/**
	 * 数値文字列を{@link java.lang.Integer}に変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、nullオブジェクトが返却されます。<br>
	 * @param string 対象文字列
	 * @return {@link java.lang.Integer}オブジェクト
	 */
	public static Integer toInteger(String string) {
		return toInteger(string, null);
	}

	/**
	 * 配列内の数値文字列を{@link java.lang.Integer}に変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、nullオブジェクトが返却されます。<br>
	 * @param strings 対象文字列配列
	 * @param pattern 数値文字列書式
	 * @return {@link java.lang.Integer}オブジェクト配列
	 */
	public static Integer[] toInteger(String[] strings, String pattern) {
		if (strings == null) {
			return new Integer[0];
		}
		List<Integer> list = new LinkedList<>();
		for (String value : strings) {
			list.add(toInteger(value, pattern));
		}
		return list.toArray(new Integer[0]);
	}

	/**
	 * 配列内の数値文字列を{@link java.lang.Integer}に変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、nullオブジェクトが返却されます。<br>
	 * @param strings 対象文字列配列
	 * @return {@link java.lang.Integer}オブジェクト配列
	 */
	public static Integer[] toInteger(String[] strings) {
		return toInteger(strings, null);
	}

	/**
	 * 数値文字列を{@link java.lang.Short}に変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、nullオブジェクトが返却されます。<br>
	 * @param string 対象文字列
	 * @param pattern 数値文字列書式
	 * @return {@link java.lang.Short}オブジェクト
	 */
	public static Short toShort(String string, String pattern) {
		BigDecimal decimal = toBigDecimal(string, pattern);
		return decimal == null ? null : decimal.shortValue();
	}

	/**
	 * 数値文字列を{@link java.lang.Short}に変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、nullオブジェクトが返却されます。<br>
	 * @param string 対象文字列
	 * @return {@link java.lang.Short}オブジェクト
	 */
	public static Short toShort(String string) {
		return toShort(string, null);
	}

	/**
	 * 配列内の数値文字列を{@link java.lang.Short}に変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、nullオブジェクトが返却されます。<br>
	 * @param strings 対象文字列配列
	 * @param pattern 数値文字列書式
	 * @return {@link java.lang.Short}オブジェクト配列
	 */
	public static Short[] toShort(String[] strings, String pattern) {
		if (strings == null) {
			return new Short[0];
		}
		List<Short> list = new LinkedList<>();
		for (String value : strings) {
			list.add(toShort(value, pattern));
		}
		return list.toArray(new Short[0]);
	}

	/**
	 * 配列内の数値文字列を{@link java.lang.Short}に変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、nullオブジェクトが返却されます。<br>
	 * @param strings 対象文字列配列
	 * @return {@link java.lang.Short}オブジェクト配列
	 */
	public static Short[] toShort(String[] strings) {
		return toShort(strings, null);
	}

	/**
	 * 数値文字列を{@link java.lang.Double}に変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、nullオブジェクトが返却されます。<br>
	 * @param string 対象文字列
	 * @param pattern 数値文字列書式
	 * @return {@link java.lang.Double}オブジェクト
	 */
	public static Double toDouble(String string, String pattern) {
		BigDecimal decimal = toBigDecimal(string, pattern);
		return decimal == null ? null : decimal.doubleValue();
	}

	/**
	 * 数値文字列を{@link java.lang.Double}に変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、nullオブジェクトが返却されます。<br>
	 * @param string 対象文字列
	 * @return {@link java.lang.Double}オブジェクト
	 */
	public static Double toDouble(String string) {
		return toDouble(string, null);
	}

	/**
	 * 配列内の数値文字列を{@link java.lang.Double}に変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、nullオブジェクトが返却されます。<br>
	 * @param strings 対象文字列配列
	 * @param pattern 数値文字列書式
	 * @return {@link java.lang.Double}オブジェクト配列
	 */
	public static Double[] toDouble(String[] strings, String pattern) {
		if (strings == null) {
			return new Double[0];
		}
		List<Double> list = new LinkedList<>();
		for (String value : strings) {
			list.add(toDouble(value, pattern));
		}
		return list.toArray(new Double[0]);
	}

	/**
	 * 配列内の数値文字列を{@link java.lang.Double}に変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、nullオブジェクトが返却されます。<br>
	 * @param strings 対象文字列配列
	 * @return {@link java.lang.Double}オブジェクト配列
	 */
	public static Double[] toDouble(String[] strings) {
		return toDouble(strings, null);
	}

	/**
	 * 数値文字列を{@link java.lang.Float}に変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、nullオブジェクトが返却されます。<br>
	 * @param string 対象文字列
	 * @param pattern 数値文字列書式
	 * @return {@link java.lang.Float}オブジェクト
	 */
	public static Float toFloat(String string, String pattern) {
		BigDecimal decimal = toBigDecimal(string, pattern);
		return decimal == null ? null : decimal.floatValue();
	}

	/**
	 * 数値文字列を{@link java.lang.Float}に変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、nullオブジェクトが返却されます。<br>
	 * @param string 対象文字列
	 * @return {@link java.lang.Float}オブジェクト
	 */
	public static Float toFloat(String string) {
		return toFloat(string, null);
	}

	/**
	 * 配列内の数値文字列を{@link java.lang.Float}に変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、nullオブジェクトが返却されます。<br>
	 * @param strings 対象文字列配列
	 * @param pattern 数値文字列書式
	 * @return {@link java.lang.Float}オブジェクト配列
	 */
	public static Float[] toFloat(String[] strings, String pattern) {
		if (strings == null) {
			return new Float[0];
		}
		List<Float> list = new LinkedList<>();
		for (String value : strings) {
			list.add(toFloat(value, pattern));
		}
		return list.toArray(new Float[0]);
	}

	/**
	 * 配列内の数値文字列を{@link java.lang.Float}に変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、nullオブジェクトが返却されます。<br>
	 * @param strings 対象文字列配列
	 * @return {@link java.lang.Float}オブジェクト配列
	 */
	public static Float[] toFloat(String[] strings) {
		return toFloat(strings, null);
	}

	/**
	 * 真偽値を表す文字列をプリミティブなbooleanに変換します。<br>
	 * このメソッドでは大文字、小文字の区別なく"true"に合致したものをtrue値とし、以外のものをfalseとして返却します。<br>
	 * 但し、文字列がnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、falseが返却されます。<br>
	 * @param string 対象文字列
	 * @return プリミティブなboolean
	 */
	public static boolean toPBoolean(String string) {
		Boolean object = toBoolean(string);
		return object == null ? false : object;
	}

	/**
	 * 配列内の真偽値を表す文字列をプリミティブなbooleanに変換します。<br>
	 * このメソッドでは大文字、小文字の区別なく"true"に合致したものをtrue値とし、以外のものをfalseとして返却します。<br>
	 * 但し、文字列がnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、falseが返却されます。<br>
	 * @param strings 対象文字列配列
	 * @return プリミティブなboolean配列
	 */
	public static boolean[] toPBoolean(String[] strings) {
		Boolean[] objects = toBoolean(strings);
		boolean[] result = new boolean[objects.length];
		for (Loop<Boolean> loop : Loop.each(objects)) {
			Boolean object = loop.value();
			result[loop.index()] = object == null ? false : object;
		}
		return result;
	}

	/**
	 * 数値文字列をプリミティブなlongに変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、0が返却されます。<br>
	 * @param string 対象文字列
	 * @param pattern 数値文字列書式
	 * @return プリミティブなlong
	 */
	public static long toPLong(String string, String pattern) {
		Long object = toLong(string, pattern);
		return object == null ? 0 : object;
	}

	/**
	 * 数値文字列をプリミティブなlongに変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、0が返却されます。<br>
	 * @param string 対象文字列
	 * @return プリミティブなlong
	 */
	public static long toPLong(String string) {
		return toPLong(string, null);
	}

	/**
	 * 配列内の数値文字列をプリミティブなlongに変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、0が返却されます。<br>
	 * @param strings 対象文字列配列
	 * @param pattern 数値文字列書式
	 * @return プリミティブなlong配列
	 */
	public static long[] toPLong(String[] strings, String pattern) {
		Long[] objects = toLong(strings);
		long[] result = new long[objects.length];
		for (Loop<Long> loop : Loop.each(objects)) {
			Long object = loop.value();
			result[loop.index()] = object == null ? 0 : object;
		}
		return result;
	}

	/**
	 * 配列内の数値文字列をプリミティブなlong配列に変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、0が返却されます。<br>
	 * @param strings 対象文字列配列
	 * @return {@link java.lang.Long}オブジェクト配列
	 */
	public static long[] toPLong(String[] strings) {
		return toPLong(strings, null);
	}

	/**
	 * 数値文字列をプリミティブなintに変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、0が返却されます。<br>
	 * @param string 対象文字列
	 * @param pattern 数値文字列書式
	 * @return プリミティブなint
	 */
	public static int toPInt(String string, String pattern) {
		Integer object = toInteger(string, pattern);
		return object == null ? 0 : object;
	}

	/**
	 * 数値文字列をプリミティブなintに変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、0が返却されます。<br>
	 * @param string 対象文字列
	 * @return プリミティブなint
	 */
	public static int toPInt(String string) {
		return toPInt(string, null);
	}

	/**
	 * 配列内の数値文字列をプリミティブなintに変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、0が返却されます。<br>
	 * @param strings 対象文字列配列
	 * @param pattern 数値文字列書式
	 * @return プリミティブなint配列
	 */
	public static int[] toPInt(String[] strings, String pattern) {
		Integer[] objects = toInteger(strings);
		int[] result = new int[objects.length];
		for (Loop<Integer> loop : Loop.each(objects)) {
			Integer object = loop.value();
			result[loop.index()] = object == null ? 0 : object;
		}
		return result;
	}

	/**
	 * 配列内の数値文字列をプリミティブなint配列に変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、0が返却されます。<br>
	 * @param strings 対象文字列配列
	 * @return {@link java.lang.Integer}オブジェクト配列
	 */
	public static int[] toPInt(String[] strings) {
		return toPInt(strings, null);
	}

	/**
	 * 数値文字列をプリミティブなshortに変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、0が返却されます。<br>
	 * @param string 対象文字列
	 * @param pattern 数値文字列書式
	 * @return プリミティブなshort
	 */
	public static short toPShort(String string, String pattern) {
		Short object = toShort(string, pattern);
		return object == null ? 0 : object;
	}

	/**
	 * 数値文字列をプリミティブなshortに変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、0が返却されます。<br>
	 * @param string 対象文字列
	 * @return プリミティブなshort
	 */
	public static short toPShort(String string) {
		return toPShort(string, null);
	}

	/**
	 * 配列内の数値文字列をプリミティブなshortに変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、0が返却されます。<br>
	 * @param strings 対象文字列配列
	 * @param pattern 数値文字列書式
	 * @return プリミティブなshort配列
	 */
	public static short[] toPShort(String[] strings, String pattern) {
		Short[] objects = toShort(strings);
		short[] result = new short[objects.length];
		for (Loop<Short> loop : Loop.each(objects)) {
			Short object = loop.value();
			result[loop.index()] = object == null ? 0 : object;
		}
		return result;
	}

	/**
	 * 配列内の数値文字列をプリミティブなshort配列に変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、0が返却されます。<br>
	 * @param strings 対象文字列配列
	 * @return {@link java.lang.Short}オブジェクト配列
	 */
	public static short[] toPShort(String[] strings) {
		return toPShort(strings, null);
	}

	/**
	 * 数値文字列をプリミティブなdoubleに変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、0が返却されます。<br>
	 * @param string 対象文字列
	 * @param pattern 数値文字列書式
	 * @return プリミティブなdouble
	 */
	public static double toPDouble(String string, String pattern) {
		Double object = toDouble(string, pattern);
		return object == null ? 0 : object;
	}

	/**
	 * 数値文字列をプリミティブなdoubleに変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、0が返却されます。<br>
	 * @param string 対象文字列
	 * @return プリミティブなdouble
	 */
	public static double toPDouble(String string) {
		return toPDouble(string, null);
	}

	/**
	 * 配列内の数値文字列をプリミティブなdoubleに変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、0が返却されます。<br>
	 * @param strings 対象文字列配列
	 * @param pattern 数値文字列書式
	 * @return プリミティブなdouble配列
	 */
	public static double[] toPDouble(String[] strings, String pattern) {
		Double[] objects = toDouble(strings);
		double[] result = new double[objects.length];
		for (Loop<Double> loop : Loop.each(objects)) {
			Double object = loop.value();
			result[loop.index()] = object == null ? 0 : object;
		}
		return result;
	}

	/**
	 * 配列内の数値文字列をプリミティブなdouble配列に変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、0が返却されます。<br>
	 * @param strings 対象文字列配列
	 * @return {@link java.lang.Double}オブジェクト配列
	 */
	public static double[] toPDouble(String[] strings) {
		return toPDouble(strings, null);
	}

	/**
	 * 数値文字列をプリミティブなfloatに変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、0が返却されます。<br>
	 * @param string 対象文字列
	 * @param pattern 数値文字列書式
	 * @return プリミティブなfloat
	 */
	public static float toPFloat(String string, String pattern) {
		Float object = toFloat(string, pattern);
		return object == null ? 0 : object;
	}

	/**
	 * 数値文字列をプリミティブなfloatに変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、0が返却されます。<br>
	 * @param string 対象文字列
	 * @return プリミティブなfloat
	 */
	public static float toPFloat(String string) {
		return toPFloat(string, null);
	}

	/**
	 * 配列内の数値文字列をプリミティブなfloatに変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、0が返却されます。<br>
	 * @param strings 対象文字列配列
	 * @param pattern 数値文字列書式
	 * @return プリミティブなfloat配列
	 */
	public static float[] toPFloat(String[] strings, String pattern) {
		Float[] objects = toFloat(strings);
		float[] result = new float[objects.length];
		for (Loop<Float> loop : Loop.each(objects)) {
			Float object = loop.value();
			result[loop.index()] = object == null ? 0 : object;
		}
		return result;
	}

	/**
	 * 配列内の数値文字列をプリミティブなfloat配列に変換します。<br>
	 * 数値文字列にnullオブジェクト又は、空白のみで構成される文字列、空文字列が指定された場合は、0が返却されます。<br>
	 * @param strings 対象文字列配列
	 * @return {@link java.lang.Float}オブジェクト配列
	 */
	public static float[] toPFloat(String[] strings) {
		return toPFloat(strings, null);
	}

	/**
	 * 文字列が空文字列又は、nullであるかを判定します。<br>
	 * このメソッドではトリム処理等は行わずに判定するため、空白で構成された文字列の場合はfalseが返却されます。<br>
	 * @param string 対象文字列
	 * @return 文字列が空文字列又は、nullの場合にtrueを返却
	 */
	public static boolean isEmpty(String string) {
		return string == null || string.length() == 0;
	}

	/**
	 * 配列として格納されている文字列全てが空文字列又は、nullであるかを判定します。<br>
	 * このメソッドではトリム処理等は行わずに判定するため、空白で構成された文字列の場合はfalseが返却されます。<br>
	 * 尚、配列自体がnull又は、長さ0の場合についても全ての格納文字列が空であるとしてtrueが返却されます。<br>
	 * @param strings 対象文字列配列
	 * @return 配列に格納されている文字列全てが空文字列又は、nullの場合にtrueを返却
	 */
	public static boolean isEmpty(String... strings) {
		if (strings == null || strings.length == 0) {
			return true;
		}
		for (String string : strings) {
			if (!isEmpty(string)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * コレクション内に格納されている文字列全てが空文字列又は、nullであるかを判定します。<br>
	 * このメソッドではトリム処理等は行わずに判定するため、空白で構成された文字列の場合はfalseが返却されます。<br>
	 * 尚、コレクション自体がnull又は、長さ0の場合についても全ての格納文字列が空であるとしてtrueが返却されます。<br>
	 * @param strings 対象文字列配列
	 * @return コレクション内に格納されている文字列全てが空文字列又は、nullの場合にtrueを返却
	 */
	public static boolean isEmpty(Collection<String> strings) {
		if (strings == null || strings.size() == 0) {
			return true;
		}
		for (String string : strings) {
			if (!isEmpty(string)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 文字列が空文字列、null、空文字列又は、半角空白のみで構成されているかを判定します。<br>
	 * このメソッドは入力値判定時等において、トリム処理後にその値を利用する場合等で、入力値が空であるかを判定する場合等に利用することを想定して設置されました。<br>
	 * @param string 対象文字列
	 * @return 文字列が空文字列、null又は半角空白のみで構成されている場合にtrueを返却
	 */
	public static boolean isBlank(String string) {
		return isEmpty(string) || isEmpty(string.trim());
	}

	/**
	 * 配列として格納されている文字列全てが空文字列、null、空文字列又は、半角空白のみで構成されているかを判定します。<br>
	 * このメソッドは入力値判定時等において、トリム処理後にその値を利用する場合等で、入力値が空であるかを判定する場合等に利用することを想定して設置されました。<br>
	 * 尚、配列自体がnull又は、長さ0の場合についても全ての格納文字列が空であるとしてtrueが返却されることに注意して下さい。<br>
	 * @param strings 対象文字列配列
	 * @return 文字列が空文字列、null又は半角空白のみで構成されている場合にtrueを返却
	 */
	public static boolean isBlank(String... strings) {
		if (strings == null || strings.length == 0) {
			return true;
		}
		for (String string : strings) {
			if (!isBlank(string)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * コレクション内に格納されている文字列全てが空文字列、null、空文字列又は、半角空白のみで構成されているかを判定します。<br>
	 * このメソッドは入力値判定時等において、トリム処理後にその値を利用する場合等で、入力値が空であるかを判定する場合等に利用することを想定して設置されました。<br>
	 * 尚、コレクション自体がnull又は、長さ0の場合についても全ての格納文字列が空であるとしてtrueが返却されることに注意して下さい。<br>
	 * @param strings 対象文字列配列
	 * @return 文字列が空文字列、null又は半角空白のみで構成されている場合にtrueを返却
	 */
	public static boolean isBlank(Collection<String> strings) {
		if (strings == null || strings.size() == 0) {
			return true;
		}
		for (String string : strings) {
			if (!isBlank(string)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 指定された文字が半角文字か判定します。<br>
	 * nullが指定された場合は無条件に半角文字であると判断します。<br>
	 * @param c 対象文字
	 * @return 半角文字の場合はtrueを返却
	 */
	public static boolean isAscii(Character c) {
		if (c == null) {
			return true;
		}
		return String.valueOf(c).getBytes().length <= 1;
	}

	/**
	 * 文字列が全て半角文字で構成されているか判定します。<br>
	 * nullが指定された場合は無条件に半角文字であると判断します。<br>
	 * @param string 対象文字列
	 * @return 全て半角文字で構成されている場合、trueを返却
	 */
	public static boolean isAscii(String string) {
		if (isEmpty(string)) {
			return true;
		}
		char[] cs = string.toCharArray();
		for (int i = 0; i <= cs.length - 1; i++) {
			if (!isAscii(cs[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 文字列が数値として扱えるか判定します。<br>
	 * このメソッドでは空白を末尾、末端に持つような{@link java.math.BigDecimal}でパース出来ない文字列は不正として扱います。<br>
	 * また、{@link java.math.BigDecimal}でパース出来るかどうかが判定基準となる為、指数表記等も許容されることに注意して下さい。<br>
	 * 尚、null又は、空文字列が指定された場合はtrueが返却されることに注意して下さい。<br>
	 * @param string 対象文字列
	 * @return 数値として扱える場合はtrueを返却
	 */
	public static boolean isNumber(String string) {
		if (isEmpty(string)) {
			return true;
		}
		try {
			new BigDecimal(string);
			return true;
		} catch (Throwable e) {
			return false;
		}
	}

	/**
	 * 文字列が期待する書式の数値として扱えるか判定します。<br>
	 * このメソッドでは空白を末尾、末端に持つような{@link java.math.BigDecimal}でパース出来ない文字列は不正として扱います。<br>
	 * また、{@link java.math.BigDecimal}でパース出来るかどうかが判定基準となる為、指数表記等も許容されることに注意して下さい。<br>
	 * 尚、null又は、空文字列が指定された場合はtrueが返却されることに注意して下さい。<br>
	 * @param string 対象文字列
	 * @param pattern 数値書式
	 * @return 数値として扱える場合はtrueを返却
	 */
	public static boolean isNumber(String string, String pattern) {
		if (isEmpty(string)) {
			return true;
		}
		try {
			DecimalFormat format = new DecimalFormat(pattern);
			format.setParseBigDecimal(true);
			BigDecimal number = (BigDecimal) format.parse(string);
			return format.format(number).equals(string);
		} catch (Throwable e) {
			return false;
		}
	}

	/**
	 * 文字列が整数値として扱えるか判定します。<br>
	 * このメソッドでは空白を末尾、末端に持つような{@link java.math.BigDecimal}でパース出来ない文字列は不正として扱います。<br>
	 * また、{@link java.math.BigDecimal}でパース出来るかどうかが判定基準となる為、指数表記等も許容されることに注意して下さい。<br>
	 * 尚、null又は、空文字列が指定された場合はtrueが返却されることに注意して下さい。<br>
	 * @param string 対象文字列
	 * @return 整数値として扱える場合はtrueを返却
	 */
	public static boolean isInteger(String string) {
		if (isEmpty(string)) {
			return true;
		}
		if (!isNumber(string)) {
			return false;
		}
		String buffer = new BigDecimal(string).toPlainString();
		if (buffer.indexOf(".") < 0) {
			return true;
		}
		String decimal = buffer.substring(buffer.indexOf(".") + 1);
		if (Integer.parseInt(decimal) > 0) {
			return false;
		}
		return true;
	}

	/**
	 * 文字列が数字のみで構成された文字列であるか判定します。<br>
	 * 許容文字群に指定がない場合、このメソッドでは符号や小数点が含まれる文字列についてもfalseとして判定します。<br>
	 * 尚、null又は、空文字列が指定された場合はtrueが返却されることに注意して下さい。<br>
	 * @param string 対象文字列
	 * @param allows 許容する文字群
	 * @return 数字のみで構成された文字列である場合はtrueを返却
	 */
	public static boolean isDigits(String string, char... allows) {
		if (isEmpty(string)) {
			return true;
		}
		for (char c : string.toCharArray()) {
			if (allows != null) {
				boolean skip = false;
				for (char allow : allows) {
					if (c == allow) {
						skip = true;
					}
				}
				if (skip) {
					continue;
				}
			}
			if (!(c >= '0' && c <= '9')) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 文字列が数字のみで構成された文字列であるか判定します。<br>
	 * 許容文字群に指定がない場合、このメソッドでは符号や小数点が含まれる文字列についてもfalseとして判定します。<br>
	 * 尚、null又は、空文字列が指定された場合はtrueが返却されることに注意して下さい。<br>
	 * @param string 対象文字列
	 * @param allows 許容する文字群(正規表現ではなく文字集合を指定します)
	 * @return 数字のみで構成された文字列である場合はtrueを返却
	 */
	public static boolean isDigits(String string, String allows) {
		return isDigits(string, allows == null ? null : allows.toCharArray());
	}

	/**
	 * 文字列が大文字英字のみで構成された文字列であるか判定します。<br>
	 * 尚、null又は、空文字列が指定された場合はtrueが返却されることに注意して下さい。<br>
	 * @param string 対象文字列
	 * @param allows 許容する文字群
	 * @return 大文字英字のみで構成された文字列である場合はtrueを返却
	 */
	public static boolean isAlphabetUpper(String string, char... allows) {
		if (isEmpty(string)) {
			return true;
		}
		for (char c : string.toCharArray()) {
			if (allows != null) {
				boolean skip = false;
				for (char allow : allows) {
					if (c == allow) {
						skip = true;
					}
				}
				if (skip) {
					continue;
				}
			}
			if (!(c >= 'A' && c <= 'Z')) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 文字列が大文字英字のみで構成された文字列であるか判定します。<br>
	 * 尚、null又は、空文字列が指定された場合はtrueが返却されることに注意して下さい。<br>
	 * @param string 対象文字列
	 * @param allows 許容する文字群(正規表現ではなく文字集合を指定します)
	 * @return 大文字英字のみで構成された文字列である場合はtrueを返却
	 */
	public static boolean isAlphabetUpper(String string, String allows) {
		return isAlphabetUpper(string, allows == null ? null : allows.toCharArray());
	}

	/**
	 * 文字列が小文字英字のみで構成された文字列であるか判定します。<br>
	 * 尚、null又は、空文字列が指定された場合はtrueが返却されることに注意して下さい。<br>
	 * @param string 対象文字列
	 * @param allows 許容する文字群
	 * @return 小文字英字のみで構成された文字列である場合はtrueを返却
	 */
	public static boolean isAlphabetLower(String string, char... allows) {
		if (isEmpty(string)) {
			return true;
		}
		for (char c : string.toCharArray()) {
			if (allows != null) {
				boolean skip = false;
				for (char allow : allows) {
					if (c == allow) {
						skip = true;
					}
				}
				if (skip) {
					continue;
				}
			}
			if (!(c >= 'a' && c <= 'z')) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 文字列が小文字英字のみで構成された文字列であるか判定します。<br>
	 * 尚、null又は、空文字列が指定された場合はtrueが返却されることに注意して下さい。<br>
	 * @param string 対象文字列
	 * @param allows 許容する文字群(正規表現ではなく文字集合を指定します)
	 * @return 小文字英字のみで構成された文字列である場合はtrueを返却
	 */
	public static boolean isAlphabetLower(String string, String allows) {
		return isAlphabetLower(string, allows == null ? null : allows.toCharArray());
	}

	/**
	 * 文字列が英字のみで構成された文字列であるか判定します。<br>
	 * 尚、null又は、空文字列が指定された場合はtrueが返却されることに注意して下さい。<br>
	 * @param string 対象文字列
	 * @param allows 許容する文字群
	 * @return 英字のみで構成された文字列である場合はtrueを返却
	 */
	public static boolean isAlphabet(String string, char... allows) {
		return isAlphabetUpper(nvl(string).toUpperCase(), allows);
	}

	/**
	 * 文字列が英字のみで構成された文字列であるか判定します。<br>
	 * 尚、null又は、空文字列が指定された場合はtrueが返却されることに注意して下さい。<br>
	 * @param string 対象文字列
	 * @param allows 許容する文字群(正規表現ではなく文字集合を指定します)
	 * @return 英字のみで構成された文字列である場合はtrueを返却
	 */
	public static boolean isAlphabet(String string, String allows) {
		return isAlphabet(string, allows == null ? null : allows.toCharArray());
	}

	/**
	 * 文字列が大文字英数字のみで構成された文字列であるか判定します。<br>
	 * 尚、null又は、空文字列が指定された場合はtrueが返却されることに注意して下さい。<br>
	 * @param string 対象文字列
	 * @param allows 許容する文字群
	 * @return 大文字英数字のみで構成された文字列である場合はtrueを返却
	 */
	public static boolean isAlphaDigitsUpper(String string, char... allows) {
		if (isEmpty(string)) {
			return true;
		}
		for (char c : string.toCharArray()) {
			if (allows != null) {
				boolean skip = false;
				for (char allow : allows) {
					if (c == allow) {
						skip = true;
					}
				}
				if (skip) {
					continue;
				}
			}
			if (!((c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9'))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 文字列が大文字英数字のみで構成された文字列であるか判定します。<br>
	 * 尚、null又は、空文字列が指定された場合はtrueが返却されることに注意して下さい。<br>
	 * @param string 対象文字列
	 * @param allows 許容する文字群(正規表現ではなく文字集合を指定します)
	 * @return 大文字英数字のみで構成された文字列である場合はtrueを返却
	 */
	public static boolean isAlphaDigitsUpper(String string, String allows) {
		return isAlphaDigitsUpper(string, allows == null ? null : allows.toCharArray());
	}

	/**
	 * 文字列が小文字英数字のみで構成された文字列であるか判定します。<br>
	 * 尚、null又は、空文字列が指定された場合はtrueが返却されることに注意して下さい。<br>
	 * @param string 対象文字列
	 * @param allows 許容する文字群
	 * @return 小文字英数字のみで構成された文字列である場合はtrueを返却
	 */
	public static boolean isAlphaDigitsLower(String string, char... allows) {
		if (isEmpty(string)) {
			return true;
		}
		for (char c : string.toCharArray()) {
			if (allows != null) {
				boolean skip = false;
				for (char allow : allows) {
					if (c == allow) {
						skip = true;
					}
				}
				if (skip) {
					continue;
				}
			}
			if (!((c >= 'a' && c <= 'z') || (c >= '0' && c <= '9'))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 文字列が小文字英数字のみで構成された文字列であるか判定します。<br>
	 * 尚、null又は、空文字列が指定された場合はtrueが返却されることに注意して下さい。<br>
	 * @param string 対象文字列
	 * @param allows 許容する文字群(正規表現ではなく文字集合を指定します)
	 * @return 小文字英数字のみで構成された文字列である場合はtrueを返却
	 */
	public static boolean isAlphaDigitsLower(String string, String allows) {
		return isAlphaDigitsLower(string, allows == null ? null : allows.toCharArray());
	}

	/**
	 * 文字列が英数字のみで構成された文字列であるか判定します。<br>
	 * 尚、null又は、空文字列が指定された場合はtrueが返却されることに注意して下さい。<br>
	 * @param string 対象文字列
	 * @param allows 許容する文字群
	 * @return 英数字のみで構成された文字列である場合はtrueを返却
	 */
	public static boolean isAlphaDigits(String string, char... allows) {
		return isAlphaDigitsUpper(nvl(string).toUpperCase(), allows);
	}

	/**
	 * 文字列が英数字のみで構成された文字列であるか判定します。<br>
	 * 尚、null又は、空文字列が指定された場合はtrueが返却されることに注意して下さい。<br>
	 * @param string 対象文字列
	 * @param allows 許容する文字群(正規表現ではなく文字集合を指定します)
	 * @return 英数字のみで構成された文字列である場合はtrueを返却
	 */
	public static boolean isAlphaDigits(String string, String allows) {
		return isAlphaDigits(string, allows == null ? null : allows.toCharArray());
	}

	/**
	 * 文字列が期待する文字のみで構成された文字列であるか判定します。<br>
	 * 期待する文字が1文字も指定されなかった場合、例外はスローされませんが常に不一致として結果が返却されます。<br>
	 * 尚、null又は、空文字列が指定された場合はtrueが返却されることに注意して下さい。<br>
	 * @param string 対象文字列
	 * @param allows 許容する文字群
	 * @return 期待する文字のみで構成された文字列である場合にtrueを返却
	 */
	public static boolean isExpectChars(String string, char... allows) {
		if (isEmpty(string)) {
			return true;
		} else if (allows == null) {
			return false;
		} else {
			for (char c : string.toCharArray()) {
				boolean valid = false;
				for (char allow : allows) {
					if (c == allow) {
						valid = true;
						break;
					}
				}
				if (!valid) {
					return false;
				}
			}
			return true;
		}
	}

	/**
	 * 文字列が期待する文字のみで構成された文字列であるか判定します。<br>
	 * 期待する文字が1文字も指定されなかった場合、例外はスローされませんが常に不一致として結果が返却されます。<br>
	 * 尚、null又は、空文字列が指定された場合はtrueが返却されることに注意して下さい。<br>
	 * @param string 対象文字列
	 * @param allows 許容する文字群文字列(正規表現ではなく文字集合を指定します)
	 * @return 期待する文字のみで構成された文字列である場合にtrueを返却
	 */
	public static boolean isExpectChars(String string, String allows) {
		return isExpectChars(string, allows == null ? null : allows.toCharArray());
	}

	/**
	 * 文字列が指定文字長と一致するか判定します。<br>
	 * nullオブジェクトが指定された場合は長さ0として判定します。<br>
	 * @param string 対象文字列
	 * @param length 文字長
	 * @return 指定文字長と一致する場合にtrueを返却
	 */
	public static boolean isLen(String string, int length) {
		return len(string) == length;
	}

	/**
	 * 文字列が指定バイト長と一致するか判定します。<br>
	 * nullオブジェクトが指定された場合は長さ0として判定します。<br>
	 * @param string 対象文字列
	 * @param length バイト長
	 * @param charset キャラクタセット
	 * @return 指定バイト長と一致する場合にtrueを返却
	 */
	public static boolean isLenb(String string, int length, String charset) {
		return lenb(string, charset) == length;
	}

	/**
	 * 文字列が指定バイト長と一致するか判定します。<br>
	 * nullオブジェクトが指定された場合は長さ0として判定します。<br>
	 * @param string 対象文字列
	 * @param length バイト長
	 * @return 指定バイト長と一致する場合にtrueを返却
	 */
	public static boolean isLenb(String string, int length) {
		return lenb(string) == length;
	}

	/**
	 * 文字が指定バイト長と一致するか判定します。<br>
	 * nullオブジェクトが指定された場合は長さ0として判定します。<br>
	 * @param c 対象文字
	 * @param length バイト長
	 * @param charset キャラクタセット
	 * @return 指定バイト長と一致する場合にtrueを返却
	 */
	public static boolean isLenb(Character c, int length, String charset) {
		return lenb(c, charset) == length;
	}

	/**
	 * 文字が指定バイト長と一致するか判定します。<br>
	 * nullオブジェクトが指定された場合は長さ0として判定します。<br>
	 * @param c 対象文字
	 * @param length バイト長
	 * @return 指定バイト長と一致する場合にtrueを返却
	 */
	public static boolean isLenb(Character c, int length) {
		return lenb(c) == length;
	}

	/**
	 * 文字列が半角ベースでの文字列長と一致するか判定します。<br>
	 * nullオブジェクトが指定された場合は長さ0として判定します。<br>
	 * @param string 対象文字列
	 * @param length 半角ベースでの文字列長
	 * @return 半角ベースでの文字列長と一致する場合にtrueを返却
	 */
	public static boolean isLena(String string, int length) {
		return lena(string) == length;
	}

	/**
	 * 文字が指定バイト長と一致するか判定します。<br>
	 * nullオブジェクトが指定された場合は長さ0として判定します。<br>
	 * @param c 対象文字
	 * @param length 半角ベースでの文字列長
	 * @return 指定バイト長と一致する場合にtrueを返却
	 */
	public static boolean isLena(Character c, int length) {
		return lena(c) == length;
	}

	/**
	 * 文字列が指定文字長以下であるか判定します。<br>
	 * nullオブジェクトが指定された場合は長さ0として判定します。<br>
	 * @param string 対象文字列
	 * @param length 文字長
	 * @return 指定文字長以下である場合にtrueを返却
	 */
	public static boolean isLeLen(String string, int length) {
		return len(string) <= length;
	}

	/**
	 * 文字列が指定バイト長以下であるか判定します。<br>
	 * nullオブジェクトが指定された場合は長さ0として判定します。<br>
	 * @param string 対象文字列
	 * @param length バイト長
	 * @param charset キャラクタセット
	 * @return 指定バイト長以下である場合にtrueを返却
	 */
	public static boolean isLeLenb(String string, int length, String charset) {
		return lenb(string, charset) <= length;
	}

	/**
	 * 文字列が指定バイト長以下であるか判定します。<br>
	 * nullオブジェクトが指定された場合は長さ0として判定します。<br>
	 * @param string 対象文字列
	 * @param length バイト長
	 * @return 指定バイト長以下である場合にtrueを返却
	 */
	public static boolean isLeLenb(String string, int length) {
		return lenb(string) <= length;
	}

	/**
	 * 文字が指定バイト長以下であるか判定します。<br>
	 * nullオブジェクトが指定された場合は長さ0として判定します。<br>
	 * @param c 対象文字
	 * @param length バイト長
	 * @param charset キャラクタセット
	 * @return 指定バイト長以下である場合にtrueを返却
	 */
	public static boolean isLeLenb(Character c, int length, String charset) {
		return lenb(c, charset) <= length;
	}

	/**
	 * 文字が指定バイト長以下であるか判定します。<br>
	 * nullオブジェクトが指定された場合は長さ0として判定します。<br>
	 * @param c 対象文字
	 * @param length バイト長
	 * @return 指定バイト長以下である場合にtrueを返却
	 */
	public static boolean isLeLenb(Character c, int length) {
		return lenb(c) <= length;
	}

	/**
	 * 文字列が半角ベースでの文字列長以下であるか判定します。<br>
	 * nullオブジェクトが指定された場合は長さ0として判定します。<br>
	 * @param string 対象文字列
	 * @param length 半角ベースでの文字列長
	 * @return 半角ベースでの文字列長以下である場合にtrueを返却
	 */
	public static boolean isLeLena(String string, int length) {
		return lena(string) <= length;
	}

	/**
	 * 文字が指定バイト長以下であるか判定します。<br>
	 * nullオブジェクトが指定された場合は長さ0として判定します。<br>
	 * @param c 対象文字
	 * @param length 半角ベースでの文字列長
	 * @return 指定半角ベースでの文字列長以下である場合にtrueを返却
	 */
	public static boolean isLeLena(Character c, int length) {
		return lena(c) <= length;
	}

	/**
	 * 文字列が指定文字長以下であるか判定します。<br>
	 * nullオブジェクトが指定された場合は長さ0として判定します。<br>
	 * @param string 対象文字列
	 * @param lower 下限文字長(この値を範囲に含む)
	 * @param upper 上限文字長(この値を範囲に含む)
	 * @return 指定範囲内である場合にtrueを返却
	 */
	public static boolean isInLen(String string, int lower, int upper) {
		return len(string) >= lower && len(string) <= upper;
	}

	/**
	 * 文字列が指定バイト長以下であるか判定します。<br>
	 * nullオブジェクトが指定された場合は長さ0として判定します。<br>
	 * @param string 対象文字列
	 * @param lower 下限バイト長(この値を範囲に含む)
	 * @param upper 上限バイト長(この値を範囲に含む)
	 * @param charset キャラクタセット
	 * @return 指定範囲内である場合にtrueを返却
	 */
	public static boolean isInLenb(String string, int lower, int upper, String charset) {
		return lenb(string, charset) >= lower && lenb(string, charset) <= upper;
	}

	/**
	 * 文字列が指定バイト長以下であるか判定します。<br>
	 * nullオブジェクトが指定された場合は長さ0として判定します。<br>
	 * @param string 対象文字列
	 * @param lower 下限バイト長(この値を範囲に含む)
	 * @param upper 上限バイト長(この値を範囲に含む)
	 * @return 指定範囲内である場合にtrueを返却
	 */
	public static boolean isInLenb(String string, int lower, int upper) {
		return lenb(string) >= lower && lenb(string) <= upper;
	}

	/**
	 * 文字が指定バイト長以下であるか判定します。<br>
	 * nullオブジェクトが指定された場合は長さ0として判定します。<br>
	 * @param c 対象文字
	 * @param lower 下限バイト長(この値を範囲に含む)
	 * @param upper 上限バイト長(この値を範囲に含む)
	 * @param charset キャラクタセット
	 * @return 指定範囲内である場合にtrueを返却
	 */
	public static boolean isInLenb(Character c, int lower, int upper, String charset) {
		return lenb(c, charset) >= lower && lenb(c, charset) <= upper;
	}

	/**
	 * 文字が指定バイト長以下であるか判定します。<br>
	 * nullオブジェクトが指定された場合は長さ0として判定します。<br>
	 * @param c 対象文字
	 * @param lower 下限バイト長(この値を範囲に含む)
	 * @param upper 上限バイト長(この値を範囲に含む)
	 * @return 指定範囲内である場合にtrueを返却
	 */
	public static boolean isInLenb(Character c, int lower, int upper) {
		return lenb(c) >= lower && lenb(c) <= upper;
	}

	/**
	 * 文字列が半角ベースでの文字列長以下であるか判定します。<br>
	 * nullオブジェクトが指定された場合は長さ0として判定します。<br>
	 * @param string 対象文字列
	 * @param lower 半角ベースでの下限文字列長(この値を範囲に含む)
	 * @param upper 半角ベースでの上限文字列長(この値を範囲に含む)
	 * @return 指定範囲内である場合にtrueを返却
	 */
	public static boolean isInLena(String string, int lower, int upper) {
		return lena(string) >= lower && lena(string) <= upper;
	}

	/**
	 * 文字が指定バイト長以下であるか判定します。<br>
	 * nullオブジェクトが指定された場合は長さ0として判定します。<br>
	 * @param c 対象文字
	 * @param lower 半角ベースでの下限文字列長(この値を範囲に含む)
	 * @param upper 半角ベースでの上限文字列長(この値を範囲に含む)
	 * @return 指定範囲内である場合にtrueを返却
	 */
	public static boolean isInLena(Character c, int lower, int upper) {
		return lena(c) >= lower && lena(c) <= upper;
	}

	/**
	 * 指定された文字列内に半角文字が含まれているか判定します。<br>
	 * nullが指定された場合は含まれていないと判断します。<br>
	 * @param string 対象文字列
	 * @return 指定された文字列内に半角文字が含まれている場合、trueを返却
	 */
	public static boolean hasAscii(String string) {
		if (isEmpty(string)) {
			return false;
		}
		char[] cs = string.toCharArray();
		for (int i = 0; i <= cs.length - 1; i++) {
			if (isAscii(cs[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 文字列がnullの場合に指定されたディフォルトの文字列に置換して提供します。<br>
	 * nullでない文字列オブジェクトが指定された場合は、そのままの文字列オブジェクトが返却されます。<br>
	 * @param string 対象文字列
	 * @param defaultValue nullの場合に適用する初期値文字列
	 * @return null考慮された文字列
	 */
	public static String nvl(String string, String defaultValue) {
		return string == null ? defaultValue : string;
	}

	/**
	 * 文字列がnullの場合に空文字列に置換して提供します。<br>
	 * nullでない文字列オブジェクトが指定された場合は、そのままの文字列オブジェクトが返却されます。<br>
	 * @param string 対象文字列
	 * @return null考慮された文字列
	 */
	public static String nvl(String string) {
		return nvl(string, EMPTY);
	}

	/**
	 * 文字列が空文字の場合に指定されたディフォルトの文字列に置換して提供します。<br>
	 * 空文字列以外の文字列が指定された場合は、そのままの文字列オブジェクトが返却されます。<br>
	 * @param string 対象文字列
	 * @param defaultValue 空文字列の場合に適用する初期値文字列(nullを指定する事も可能です)
	 * @return 編集された文字列
	 */
	public static String evl(String string, String defaultValue) {
		return isEmpty(string) ? defaultValue : string;
	}

	/**
	 * 文字列が空文字列、null、空文字列又は、半角空白のみで構成されている場合に指定されたディフォルトの文字列に置換して提供します。<br>
	 * 文字列が空文字列、null、空文字列又は、半角空白のみで構成されている文字列以外が指定された場合は、そのままの文字列オブジェクトが返却されます。<br>
	 * @param string 対象文字列
	 * @param defaultValue 空文字列の場合に適用する初期値文字列
	 * @return 編集された文字列
	 */
	public static String bvl(String string, String defaultValue) {
		return isBlank(string) ? defaultValue : string;
	}

	/**
	 * 文字列が空文字の場合に指定された場合にnullオブジェクトとして提供します。。<br>
	 * 空文字列以外の文字列が指定された場合は、そのままの文字列オブジェクトが返却されます。<br>
	 * @param string 対象文字列
	 * @return 編集された文字列
	 */
	public static String e2n(String string) {
		return evl(string, null);
	}

	/**
	 * 文字列が空文字列、null、空文字列又は、半角空白のみで構成されている場合にnullオブジェクトとして提供します。<br>
	 * 文字列が空文字列、null、空文字列又は、半角空白のみで構成されている文字列以外が指定された場合は、そのままの文字列オブジェクトが返却されます。<br>
	 * @param string 対象文字列
	 * @return 編集された文字列
	 */
	public static String b2n(String string) {
		return bvl(string, null);
	}

	/**
	 * 文字列同士が同一文字でつであるか判定します。<br>
	 * このメソッドでは比較対象同士がnull同士である場合にも{@link java.lang.NullPointerException}はスローせずに同一として判定します。<br>
	 * @param string1 比較対象文字列
	 * @param string2 比較対象文字列
	 * @return 同一オブジェクトである場合にtrueを返却
	 */
	public static boolean equals(String string1, String string2) {
		return ObjectUtil.equals(string1, string2);
	}

	/**
	 * 文字列同士が大文字小文字を区別せずに同一文字でつであるか判定します。<br>
	 * このメソッドでは比較対象同士がnull同士である場合にも{@link java.lang.NullPointerException}はスローせずに同一として判定します。<br>
	 * @param string1 比較対象オブジェクト
	 * @param string2 比較対象オブジェクト
	 * @return 同一オブジェクトである場合にtrueを返却
	 */
	public static boolean equalsIgnoreCase(String string1, String string2) {
		return ObjectUtil.equals(nvl(string1).toUpperCase(), nvl(string2).toUpperCase());
	}

	/**
	 * 文字列配列内に期待する文字列が存在するか判定します。<br>
	 * @param strings 判定対象文字列配列
	 * @param value 判定文字列
	 * @return 存在する場合にtrueを返却
	 */
	public static boolean contains(String[] strings, String value) {
		if (strings == null || strings.length <= 0) {
			return false;
		}
		for (String string : strings) {
			if (equals(string, value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 文字列配列内に期待する文字列が存在するか大文字小文字を区別せずに判定します。<br>
	 * @param strings 判定対象文字列配列
	 * @param value 判定文字列
	 * @return 存在する場合にtrueを返却
	 */
	public static boolean containsIgnoreCase(String[] strings, String value) {
		if (strings == null || strings.length <= 0) {
			return false;
		}
		for (String string : strings) {
			if (equalsIgnoreCase(string, value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 文字列同士を比較した結果を返却します。<br>
	 * このメソッドではnullが考慮された処理を行い、null同士の場合は0、インスタンス存在から見たnullは1として判定します。<br>
	 * @param string1 比較元文字列
	 * @param string2 比較先文字列
	 * @return 比較元文字列が大きい場合に1以上、一致する場合は0、比較先文字列が大きい場合に-1以下を返却
	 */
	public static int compare(String string1, String string2) {
		return ObjectUtil.compare(string1, string2);
	}

	/**
	 * 文字を繰返した文字列を生成します。<br>
	 * @param c 繰返す文字
	 * @param count 生成する文字数(0以下を指定した場合は空文字列を返却します)
	 * @return 生成した文字列
	 */
	public static String repeat(Character c, int count) {
		if (c == null) {
			return EMPTY;
		}
		if (count <= 0) {
			return EMPTY;
		}
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i <= count - 1; i++) {
			builder.append(c);
		}
		return builder.toString();
	}

	/**
	 * 半角スペースを繰返した生成します。<br>
	 * @param count 生成するスペース数(0以下を指定した場合は空文字列を返却します)
	 * @return 生成した文字列
	 */
	public static String spaces(int count) {
		if (count <= 0) {
			return EMPTY;
		}
		return repeat(' ', count);
	}

	/**
	 * 文字列の文字長を取得します。<br>
	 * nullオブジェクトが指定された場合は長さ0を返却します。<br>
	 * @param string 対象文字列
	 * @return 文字長
	 */
	public static int len(String string) {
		return nvl(string).length();
	}

	/**
	 * 文字列の文字長を取得します。<br>
	 * nullオブジェクトが指定された場合は長さ0を返却します。<br>
	 * @param string 対象文字列
	 * @return 文字長
	 */
	public static int len(StringBuffer string) {
		return string == null ? 0 : string.toString().length();
	}

	/**
	 * 文字列の文字長を取得します。<br>
	 * nullオブジェクトが指定された場合は長さ0を返却します。<br>
	 * @param string 対象文字列
	 * @return 文字長
	 */
	public static int len(StringBuilder string) {
		return string == null ? 0 : string.toString().length();
	}

	/**
	 * 文字列のバイト長を取得します。<br>
	 * @param string 対象文字列
	 * @param charset キャラクタセット
	 * @return 文字列バイト長
	 */
	public static int lenb(String string, String charset) {
		try {
			if (isEmpty(charset)) {
				return nvl(string).getBytes().length;
			} else {
				return nvl(string).getBytes(charset).length;
			}
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("unsupported charset (" + charset + ")");
		}
	}

	/**
	 * 文字列のバイト長を取得します。<br>
	 * @param string 対象文字列
	 * @param charset キャラクタセット
	 * @return 文字列バイト長
	 */
	public static int lenb(StringBuffer string, String charset) {
		return lenb(string == null ? null : string.toString(), charset);
	}

	/**
	 * 文字列のバイト長を取得します。<br>
	 * @param string 対象文字列
	 * @param charset キャラクタセット
	 * @return 文字列バイト長
	 */
	public static int lenb(StringBuilder string, String charset) {
		return lenb(string == null ? null : string.toString(), charset);
	}

	/**
	 * 文字列のバイト長を取得します。<br>
	 * @param string 対象文字列
	 * @return 文字列バイト長
	 */
	public static int lenb(String string) {
		return lenb(string, null);
	}

	/**
	 * 文字列のバイト長を取得します。<br>
	 * @param string 対象文字列
	 * @return 文字列バイト長
	 */
	public static int lenb(StringBuffer string) {
		return lenb(string == null ? null : string.toString(), null);
	}

	/**
	 * 文字列のバイト長を取得します。<br>
	 * @param string 対象文字列
	 * @return 文字列バイト長
	 */
	public static int lenb(StringBuilder string) {
		return lenb(string == null ? null : string.toString(), null);
	}

	/**
	 * 文字のバイト長を取得します。<br>
	 * @param c 対象文字
	 * @param charset キャラクタセット
	 * @return 文字バイト長
	 */
	public static int lenb(Character c, String charset) {
		if (c == null) {
			return 0;
		}
		return lenb(String.valueOf(c), charset);
	}

	/**
	 * 文字のバイト長を取得します。<br>
	 * @param c 対象文字
	 * @return 文字バイト長
	 */
	public static int lenb(Character c) {
		if (c == null) {
			return 0;
		}
		return lenb(String.valueOf(c));
	}

	/**
	 * 文字コードに関係なく半角ベースでの文字列長を取得します。<br>
	 * @param string 対象文字列
	 * @return 半角ベースでの文字列長
	 */
	public static int lena(String string) {
		String buffer = nvl(string);
		int length = 0;
		for (char c : buffer.toCharArray()) {
			if (isAscii(c)) {
				length += 1;
			} else {
				length += 2;
			}
		}
		return length;
	}

	/**
	 * 文字コードに関係なく半角ベースでの文字列長を取得します。<br>
	 * @param string 対象文字列
	 * @return 半角ベースでの文字列長
	 */
	public static int lena(StringBuffer string) {
		return lena(string == null ? null : string.toString());
	}

	/**
	 * 文字コードに関係なく半角ベースでの文字列長を取得します。<br>
	 * @param string 対象文字列
	 * @return 半角ベースでの文字列長
	 */
	public static int lena(StringBuilder string) {
		return lena(string == null ? null : string.toString());
	}

	/**
	 * 文字コードに関係なく半角ベースでの文字長を取得します。<br>
	 * @param c 対象文字
	 * @return 半角ベースでの文字長
	 */
	public static int lena(Character c) {
		if (c == null) {
			return 0;
		}
		return lena(String.valueOf(c));
	}

	/**
	 * 数値を指定パターンに沿ってフォーマットして提供します。<br>
	 * 尚、数値としてnullが指定された場合は例外とはせずに空文字列を返却します。<br>
	 * @param number 数値
	 * @param pattern フォーマットパターン文字列
	 * @return 編集後文字列
	 */
	public static String format(Number number, String pattern) {
		if (number == null) {
			return EMPTY;
		}
		DecimalFormat format = new DecimalFormat(pattern);
		String result = format.format(number);
		return result;
	}

	/**
	 * 日付を指定パターンに沿ってフォーマットして提供します。<br>
	 * 尚、日付としてnullが指定された場合は例外とはせずに空文字列を返却します。<br>
	 * @param date 日付
	 * @param pattern フォーマットパターン文字列
	 * @return 編集後文字列
	 */
	public static String format(Date date, String pattern) {
		if (date == null) {
			return EMPTY;
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String result = format.format(date);
		return result;
	}

	/**
	 * 文字列のトークン分割を行います。<br>
	 * @param string 対象文字列
	 * @param delimRegex 分割句正規表現文字列
	 * @param skipEmpty トークンが空文字列(トリム指定がある場合は空白文字列も含む)の場合はトークンリストから除外する場合にtrueを指定
	 * @param trim トークンリストに追加する場合にトリムする場合にtrueを指定
	 * @return トークン配列
	 */
	public static String[] split(String string, String delimRegex, boolean skipEmpty, boolean trim) {
		if (isEmpty(string)) {
			return new String[0];
		}
		if (isEmpty(delimRegex)) {
			return new String[] { string };
		}
		String[] tokens = string.split(delimRegex);
		if (!skipEmpty && !trim) {
			return tokens;
		}
		List<String> list = new LinkedList<>();
		for (String token : tokens) {
			String value = trim ? token.trim() : token;
			if (skipEmpty && isEmpty(value)) {
				continue;
			}
			list.add(value);
		}
		return list.toArray(new String[0]);
	}

	/**
	 * 文字列のトークン分割を行います。<br>
	 * このメソッドでは純粋に分割句に沿ったトークン分解内容を提供します。<br>
	 * 空トークン未取得やトークントリムを行う場合は{@link #split(String, String, boolean, boolean)}を利用して下さい。<br>
	 * @param string 対象文字列
	 * @param delimRegex 分割句正規表現文字列
	 * @return トークン配列
	 */
	public static String[] split(String string, String delimRegex) {
		return split(string, delimRegex, false, false);
	}

	/**
	 * 文字列を特定位置から見た特定長の範囲で切り取って提供します。<br>
	 * このメソッドでは対象文字列の範囲外の範囲が指定された場合でも例外はスローせず、その範囲を無視して処理します。<br>
	 * 尚、このメソッドは{@link java.lang.String#substring(int, int)}とは異なり、開始及び、終了位置指定ではなく、開始位置とそこからの長さを指定するインタフェースであることに注意して下さい。<br>
	 * @param string 対象文字列
	 * @param position 開始位置
	 * @param length 取得文字数
	 * @return 編集後文字列
	 */
	public static String substr(String string, int position, int length) {
		if (isEmpty(string) || position > len(string)) {
			return EMPTY;
		}
		for (int i = position; i < 0; i++) {
			length--;
		}
		if (position < 0) {
			position = 0;
		}
		if (length < 0) {
			length = 0;
		}
		if (len(string) < position + length) {
			length = len(string) - position;
		}
		return string.substring(position, position + length);
	}

	/**
	 * 文字列を特定位置から見た特定バイト長の範囲で切り取って提供します。<br>
	 * このメソッドでは対象文字列の範囲外の範囲が指定された場合でも例外はスローせず、その範囲を無視して処理します。<br>
	 * 指定バイト位置に2バイト以上の文字が存在する場合は、欠落バイト分は半角スペースとして補完されます(UTF-8のように3バイト～1文字構成の場合、"あいう"を位置=1, 長さ=6で指定すると"  い "となります(半角桁数が多くなる))。<br>
	 * 尚、このメソッドは{@link java.lang.String#substring(int, int)}とは異なり、開始及び、終了位置指定ではなく、開始位置とそこからの長さを指定するインタフェースであることに注意して下さい。<br>
	 * @param string 対象文字列
	 * @param position 開始位置
	 * @param length 取得バイト数
	 * @param charset キャラクタセット
	 * @return 編集を行った文字列
	 */
	public static String substrb(String string, int position, int length, String charset) {
		if (isEmpty(string) || position > lenb(string, charset) - 1) {
			return EMPTY;
		}
		for (int i = position; i < 0; i++) {
			length--;
		}
		if (position < 0) {
			position = 0;
		}
		if (length < 0) {
			length = 0;
		}
		if (lenb(string, charset) < position + length) {
			length = lenb(string, charset) - position;
		}
		StringBuilder header = new StringBuilder();
		for (int i = 0; true; i++) {
			if (lenb(header.toString(), charset) >= position) {
				break;
			}
			header.append(string.substring(i, i + 1));
		}
		String space = spaces(lenb(header.toString(), charset) - position);
		String buffer = new StringBuilder(space).append(string.substring(header.toString().length())).toString();
		while (lenb(buffer, charset) > length) {
			buffer = buffer.substring(0, buffer.length() - 1);
		}
		return new StringBuilder(buffer).append(spaces(length - lenb(buffer, charset))).toString();
	}

	/**
	 * 文字列を特定位置から見た特定バイト長の範囲で切り取って提供します。<br>
	 * このメソッドでは対象文字列の範囲外の範囲が指定された場合でも例外はスローせず、その範囲を無視して処理します。<br>
	 * 指定バイト位置に2バイト以上の文字が存在する場合は、欠落バイト分は半角スペースとして補完されます(UTF-8のように3バイト～1文字構成の場合、"あいう"を位置=1, 長さ=6で指定すると"  い "となります(半角桁数が多くなる))。<br>
	 * 尚、このメソッドは{@link java.lang.String#substring(int, int)}とは異なり、開始及び、終了位置指定ではなく、開始位置とそこからの長さを指定するインタフェースであることに注意して下さい。<br>
	 * @param string 対象文字列
	 * @param position 開始位置
	 * @param length 取得バイト数
	 * @return 編集を行った文字列
	 */
	public static String substrb(String string, int position, int length) {
		return substrb(string, position, length, null);
	}

	/**
	 * 文字列を特定位置から見た特定半角文字長の範囲で切り取って提供します。<br>
	 * このメソッドでは対象文字列の範囲外の範囲が指定された場合でも例外はスローせず、その範囲を無視して処理します。<br>
	 * 指定バイト位置に半角2桁以上の文字が存在する場合は、欠落桁分は半角スペースとして補完されます。<br>
	 * 尚、このメソッドは{@link java.lang.String#substring(int, int)}とは異なり、開始及び、終了位置指定ではなく、開始位置とそこからの長さを指定するインタフェースであることに注意して下さい。<br>
	 * @param string 対象文字列
	 * @param position 開始位置
	 * @param length 取得半角文字数
	 * @return 編集を行った文字列
	 */
	public static String substra(String string, int position, int length) {
		if (isEmpty(string) || position > lena(string) - 1) {
			return EMPTY;
		}
		for (int i = position; i < 0; i++) {
			length--;
		}
		if (position < 0) {
			position = 0;
		}
		if (length < 0) {
			length = 0;
		}
		if (lena(string) < position + length) {
			length = lena(string) - position;
		}
		StringBuilder header = new StringBuilder();
		for (int i = 0; true; i++) {
			if (lena(header.toString()) >= position) {
				break;
			}
			header.append(string.substring(i, i + 1));
		}
		String space = spaces(lena(header.toString()) - position);
		String buffer = new StringBuilder(space).append(string.substring(header.toString().length())).toString();
		while (lena(buffer) > length) {
			buffer = buffer.substring(0, buffer.length() - 1);
		}
		return new StringBuilder(buffer).append(spaces(length - lena(buffer))).toString();
	}

	/**
	 * 文字列から特定範囲の文字列に切り取って提供します。<br>
	 * このメソッドでは対象文字列の範囲外の範囲が指定された場合でも例外はスローせず、その範囲を無視して処理します。<br>
	 * パラメータ挙動は{@link java.lang.String#substring(int, int)}に準拠します。<br>
	 * @param string 対象文字列
	 * @param start 範囲開始インデックス(この値を含む)
	 * @param end 範囲終了インデックス(この値を含まない)
	 * @return 編集後文字列
	 */
	public static String substring(String string, int start, int end) {
		return substr(string, start, end - start);
	}

	/**
	 * 文字列から指定位置以降の文字列に切り取って提供します。<br>
	 * このメソッドでは対象文字列の範囲外の範囲が指定された場合でも例外はスローせず、その範囲を無視して処理します。<br>
	 * パラメータ挙動は{@link java.lang.String#substring(int, int)}に準拠します。<br>
	 * @param string 対象文字列
	 * @param start 範囲開始インデックス(この値を含む)
	 * @return 編集後文字列
	 */
	public static String substring(String string, int start) {
		return substr(string, start, len(string) - start);
	}

	/**
	 * 文字列から特定バイト範囲の文字列に切り取って提供します。<br>
	 * このメソッドでは対象文字列の範囲外の範囲が指定された場合でも例外はスローせず、その範囲を無視して処理します。<br>
	 * 指定バイト位置に2バイト以上の文字が存在する場合は、欠落バイト分は半角スペースとして補完されます(UTF-8のように3バイト～1文字構成の場合、"あいう"を位置=1, 長さ=6で指定すると"  い "となります(半角桁数が多くなる))。<br>
	 * パラメータ挙動は{@link java.lang.String#substring(int, int)}に準拠します。<br>
	 * @param string 対象文字列
	 * @param start 範囲開始バイトインデックス(この値を含む)
	 * @param end 範囲終了バイトインデックス(この値を含まない)
	 * @param charset キャラクタセット
	 * @return 編集後文字列
	 */
	public static String substringb(String string, int start, int end, String charset) {
		return substrb(string, start, end - start, charset);
	}

	/**
	 * 文字列から特定バイト範囲の文字列に切り取って提供します。<br>
	 * このメソッドでは対象文字列の範囲外の範囲が指定された場合でも例外はスローせず、その範囲を無視して処理します。<br>
	 * 指定バイト位置に2バイト以上の文字が存在する場合は、欠落バイト分は半角スペースとして補完されます(UTF-8のように3バイト～1文字構成の場合、"あいう"を位置=1, 長さ=6で指定すると"  い "となります(半角桁数が多くなる))。<br>
	 * パラメータ挙動は{@link java.lang.String#substring(int, int)}に準拠します。<br>
	 * @param string 対象文字列
	 * @param start 範囲開始バイトインデックス(この値を含む)
	 * @param end 範囲終了バイトインデックス(この値を含まない)
	 * @return 編集後文字列
	 */
	public static String substringb(String string, int start, int end) {
		return substrb(string, start, end - start);
	}

	/**
	 * 文字列から特定半角桁範囲の文字列に切り取って提供します。<br>
	 * このメソッドでは対象文字列の範囲外の範囲が指定された場合でも例外はスローせず、その範囲を無視して処理します。<br>
	 * 指定バイト位置に半角2桁以上の文字が存在する場合は、欠落バイト分は半角スペースとして補完されます。<br>
	 * パラメータ挙動は{@link java.lang.String#substring(int, int)}に準拠します。<br>
	 * @param string 対象文字列
	 * @param start 範囲開始半角桁インデックス(この値を含む)
	 * @param end 範囲終了半角桁インデックス(この値を含まない)
	 * @return 編集後文字列
	 */
	public static String substringa(String string, int start, int end) {
		return substra(string, start, end - start);
	}

	/**
	 * 文字列を左側から特定文字数分切り取って提供します。<br>
	 * このメソッドでは対象文字列の範囲外の範囲が指定された場合でも例外はスローせず、その範囲を無視して処理します。<br>
	 * @param string 対象文字列
	 * @param length 取得文字数
	 * @return 編集後文字列
	 */
	public static String left(String string, int length) {
		return substr(string, 0, length);
	}

	/**
	 * 文字列を左側から特定バイト数分切り取って提供します。<br>
	 * このメソッドでは対象文字列の範囲外の範囲が指定された場合でも例外はスローせず、その範囲を無視して処理します。<br>
	 * @param string 対象文字列
	 * @param length 取得バイト数
	 * @param charset キャラクタセット
	 * @return 編集後文字列
	 */
	public static String leftb(String string, int length, String charset) {
		return substrb(string, 0, length, charset);
	}

	/**
	 * 文字列を左側から特定バイト数分切り取って提供します。<br>
	 * このメソッドでは対象文字列の範囲外の範囲が指定された場合でも例外はスローせず、その範囲を無視して処理します。<br>
	 * @param string 対象文字列
	 * @param length 取得バイト数
	 * @return 編集後文字列
	 */
	public static String leftb(String string, int length) {
		return substrb(string, 0, length);
	}

	/**
	 * 文字列を左側から特定半角文字数分切り取って提供します。<br>
	 * このメソッドでは対象文字列の範囲外の範囲が指定された場合でも例外はスローせず、その範囲を無視して処理します。<br>
	 * @param string 対象文字列
	 * @param length 取得半角文字数
	 * @return 編集後文字列
	 */
	public static String lefta(String string, int length) {
		return substra(string, 0, length);
	}

	/**
	 * 文字列を右側から特定文字数分切り取って提供します。<br>
	 * このメソッドでは対象文字列の範囲外の範囲が指定された場合でも例外はスローせず、その範囲を無視して処理します。<br>
	 * @param string 対象文字列
	 * @param length 取得文字数
	 * @return 編集後文字列
	 */
	public static String right(String string, int length) {
		return substr(string, len(string) - length, length);
	}

	/**
	 * 文字列を右側から特定バイト数分切り取って提供します。<br>
	 * このメソッドでは対象文字列の範囲外の範囲が指定された場合でも例外はスローせず、その範囲を無視して処理します。<br>
	 * @param string 対象文字列
	 * @param length 取得バイト数
	 * @param charset キャラクタセット
	 * @return 編集後文字列
	 */
	public static String rightb(String string, int length, String charset) {
		return substrb(string, lenb(string, charset) - length, length, charset);
	}

	/**
	 * 文字列を右側から特定バイト数分切り取って提供します。<br>
	 * このメソッドでは対象文字列の範囲外の範囲が指定された場合でも例外はスローせず、その範囲を無視して処理します。<br>
	 * @param string 対象文字列
	 * @param length 取得バイト数
	 * @return 編集後文字列
	 */
	public static String rightb(String string, int length) {
		return substrb(string, lenb(string) - length, length);
	}

	/**
	 * 文字列を右側から特定半角文字数分切り取って提供します。<br>
	 * このメソッドでは対象文字列の範囲外の範囲が指定された場合でも例外はスローせず、その範囲を無視して処理します。<br>
	 * @param string 対象文字列
	 * @param length 取得半角文字数
	 * @return 編集後文字列
	 */
	public static String righta(String string, int length) {
		return substra(string, lena(string) - length, length);
	}

	/**
	 * 文字列を左右トリムして取得します。<br>
	 * 尚、nullが指定された場合は空文字列として返却されます(nullのまま返却されない事に注意して下さい)。<br>
	 * @param string 対象文字列
	 * @return 編集後文字列
	 */
	public static String trim(String string) {
		if (isEmpty(string)) {
			return EMPTY;
		}
		return nvl(string).trim();
	}

	/**
	 * 文字列を左トリムして取得します。<br>
	 * 尚、nullが指定された場合は空文字列として返却されます(nullのまま返却されない事に注意して下さい)。<br>
	 * @param string 対象文字列
	 * @return 編集後文字列
	 */
	public static String ltrim(String string) {
		if (isEmpty(string)) {
			return EMPTY;
		}
		String buffer = nvl(string);
		//while (buffer.startsWith(" ")) {
		//	buffer = right(buffer, buffer.length() - 1);
		//}
		//return buffer;
		// ↓パフォーマンスチューニング(1000000繰返しで55ms→9ms)
		int start = 0;
		while (buffer.charAt(start) == ' ') {
			start++;
			if (start > buffer.length() - 1) {
				break;
			}
		}
		return buffer.substring(start);
	}

	/**
	 * 文字列を右トリムして取得します。<br>
	 * 尚、nullが指定された場合は空文字列として返却されます(nullのまま返却されない事に注意して下さい)。<br>
	 * @param string 対象文字列
	 * @return 編集後文字列
	 */
	public static String rtrim(String string) {
		if (isEmpty(string)) {
			return EMPTY;
		}
		String buffer = nvl(string);
		//while (buffer.endsWith(" ")) {
		//	buffer = left(buffer, buffer.length() - 1);
		//}
		//return buffer;
		// ↓パフォーマンスチューニング(1000000繰返しで55ms→9ms)
		int end = 0;
		end = string.length() - 1;
		while (buffer.charAt(end) == ' ') {
			end--;
			if (end < 0) {
				break;
			}
		}
		return buffer.substring(0, end + 1);
	}

	/**
	 * 文字列を全角スペースを含めて左右トリムして取得します。<br>
	 * 尚、nullが指定された場合は空文字列として返却されます(nullのまま返却されない事に注意して下さい)。<br>
	 * @param string 対象文字列
	 * @return 編集後文字列
	 */
	public static String trimjp(String string) {
		if (isEmpty(string)) {
			return EMPTY;
		}
		String buffer = nvl(string);
		//buffer = buffer.replaceAll("^[　 ]+", "");
		//buffer = buffer.replaceAll("[　 ]+$", "");
		//return buffer;
		// ↓パフォーマンスチューニング(1000000繰返しで1460ms→36ms)
		int start = 0;
		int end = 0;
		while (buffer.charAt(start) == ' ' || buffer.charAt(start) == '　') {
			start++;
			if (start > buffer.length() - 1) {
				break;
			}
		}
		end = string.length() - 1;
		if (end > start) {
			while (buffer.charAt(end) == ' ' || buffer.charAt(end) == '　') {
				end--;
			}
		}
		return buffer.substring(start, end + 1);
	}

	/**
	 * 文字列を全角スペースを含めて左トリムして取得します。<br>
	 * 尚、nullが指定された場合は空文字列として返却されます(nullのまま返却されない事に注意して下さい)。<br>
	 * @param string 対象文字列
	 * @return 編集後文字列
	 */
	public static String ltrimjp(String string) {
		if (isEmpty(string)) {
			return EMPTY;
		}
		String buffer = nvl(string);
		//buffer = buffer.replaceAll("^[　 ]+", "");
		//return buffer;
		// ↓パフォーマンスチューニング(1000000繰返しで687ms→10ms)
		int start = 0;
		while (buffer.charAt(start) == ' ' || buffer.charAt(start) == '　') {
			start++;
			if (start > buffer.length() - 1) {
				break;
			}
		}
		return buffer.substring(start);
	}

	/**
	 * 文字列を全角スペースを含めて右トリムして取得します。<br>
	 * 尚、nullが指定された場合は空文字列として返却されます(nullのまま返却されない事に注意して下さい)。<br>
	 * @param string 対象文字列
	 * @return 編集後文字列
	 */
	public static String rtrimjp(String string) {
		if (isEmpty(string)) {
			return EMPTY;
		}
		String buffer = nvl(string);
		//buffer = buffer.replaceAll("[　 ]+$", "");
		//return buffer;
		// ↓パフォーマンスチューニング(1000000繰返しで687ms→10ms)
		int end = 0;
		end = string.length() - 1;
		while (buffer.charAt(end) == ' ' || buffer.charAt(end) == '　') {
			end--;
			if (end < 0) {
				break;
			}
		}
		return buffer.substring(0, end + 1);
	}

	/**
	 * 文字列における指定位置にある文字を取得します。<br>
	 * 該当位置に文字が存在しない場合は\0を返却します。<br>
	 * このメソッドはその他の{@link #charAtb(String, int)}、{@link #charAta(String, int)}に併せて統一化の為に設置されました。<br>
	 * 基本的な挙動は{@link String#charAt(int)}と同様で、nullオブジェクト、空文字列、範囲外が考慮されたメソッドとなります。<br>
	 * @param string 対象文字列
	 * @param index 半角桁インデックス
	 * @return 文字列における半角桁位置上の文字
	 */
	public static char charAt(String string, int index) {
		if (isEmpty(string)) {
			return '\0';
		}
		if (index < 0) {
			return '\0';
		}
		String result = substr(string, index, len(string));
		return result.length() == 0 ? '\0' : result.toCharArray()[0];
	}

	/**
	 * 文字列におけるバイト位置にある文字を取得します。<br>
	 * 該当位置に文字が存在しない場合は\0を返却します。<br>
	 * また、該当位置の文字が全角の場合は、開始位置に合致する場合は全角文字を返却しますが、中間位置の場合は半角スペースが返却されます。<br>
	 * @param string 対象文字列
	 * @param index バイトインデックス
	 * @param charset キャラクタセット
	 * @return 文字列におけるバイト位置上の文字
	 */
	public static char charAtb(String string, int index, String charset) {
		if (isEmpty(string)) {
			return '\0';
		}
		if (index < 0) {
			return '\0';
		}
		String result = substrb(string, index, lenb(string, charset), charset);
		return result.length() == 0 ? '\0' : result.toCharArray()[0];
	}

	/**
	 * 文字列におけるバイト位置にある文字を取得します。<br>
	 * 該当位置に文字が存在しない場合は\0を返却します。<br>
	 * また、該当位置の文字が全角の場合は、開始位置に合致する場合は全角文字を返却しますが、中間位置の場合は半角スペースが返却されます。<br>
	 * @param string 対象文字列
	 * @param index バイトインデックス
	 * @return 文字列におけるバイト位置上の文字
	 */
	public static char charAtb(String string, int index) {
		return charAtb(string, index, null);
	}

	/**
	 * 文字列における半角桁位置にある文字を取得します。<br>
	 * 該当位置に文字が存在しない場合は\0を返却します。<br>
	 * また、該当位置の文字が全角の場合は、開始位置に合致する場合は全角文字を返却しますが、中間位置の場合は半角スペースが返却されます。<br>
	 * @param string 対象文字列
	 * @param index 半角桁インデックス
	 * @return 文字列における半角桁位置上の文字
	 */
	public static char charAta(String string, int index) {
		if (isEmpty(string)) {
			return '\0';
		}
		if (index < 0) {
			return '\0';
		}
		String result = substra(string, index, lena(string));
		return result.length() == 0 ? '\0' : result.toCharArray()[0];
	}

	/**
	 * 文字列内に含まれる特定文字の数を取得します。<br>
	 * @param string 対象文字列
	 * @param c 検索文字
	 * @return 文字列内に含まれる指定文字の数
	 */
	public static int count(String string, char c) {
		if (isEmpty(string)) {
			return 0;
		}
		int count = 0;
		for (int i = 0; i <= string.length() - 1; i++) {
			if (string.charAt(i) == c) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 文字列内の文字を逆順に並べなおして提供します。<br>
	 * 尚、nullが指定された場合は空文字列として返却されます(nullのまま返却されない事に注意して下さい)。<br>
	 * @param string 並べ替える対象の文字列
	 * @return 編集後文字列
	 */
	public static String reverse(String string) {
		if (isEmpty(string)) {
			return EMPTY;
		}
		return new StringBuilder(string).reverse().toString();
	}

	/**
	 * 文字列内の特定文字列を置換してその結果を提供します。<br>
	 * 尚、当メソッドは{@link java.lang.String#replaceAll(String, String)}とは異なり、正規表現による置換は行いません。<br>
	 * また、nullが指定された場合は空文字列として返却されます(nullのまま返却されない事に注意して下さい)。<br>
	 * @param string 対象文字列
	 * @param before 置換前の文字列
	 * @param after 置換後の文字列
	 * @return 編集後文字列
	 */
	public static String replace(String string, String before, String after) {
		if (isEmpty(string)) {
			return EMPTY;
		}
		if (string.indexOf(before) == -1) {
			return string;
		}
		StringBuilder builder = new StringBuilder();
		int index = string.indexOf(before);
		builder.append(string.substring(0, index) + after);
		if (index + before.length() < string.length()) {
			String buffer = string.substring(index + before.length(), string.length());
			builder.append(replace(buffer, before, after));
		}
		return builder.toString();
	}

	/**
	 * 文字列内のバインド文字列部("{0～}"形式)に対して、指定されたオブジェクト配列をバインドして文字列を提供します。<br>
	 * バインドする際のオブジェクトがnullの場合は空文字列としてバインドされ、それ以外は{@link Object#toString()}の値がバインドされます。<br>
	 * @param string 対象文字列
	 * @param params バインドオブジェクト配列
	 * @return 編集後文字列
	 */
	public static String bind(String string, Object... params) {
		if (isEmpty(string)) {
			return EMPTY;
		}
		if (params == null) {
			return string;
		}
		String buffer = string;
		for (int i = 0; i <= params.length - 1; i++) {
			Object object = params[i];
			String value = object == null ? "" : object.toString();
			buffer = replace(buffer, "{" + i + "}", value);
		}
		return buffer;
	}

	/**
	 * 文字列内のバインド文字列部("${key}"形式)に対して、指定されたオブジェクトマップをバインドして文字列を提供します。<br>
	 * バインドする際のオブジェクトがnullの場合は空文字列としてバインドされ、それ以外は{@link Object#toString()}の値がバインドされます。<br>
	 * @param string 対象文字列
	 * @param params バインドオブジェクトマップ
	 * @return 編集後文字列
	 */
	public static String bind(String string, Map<String, ?> params) {
		if (isEmpty(string)) {
			return EMPTY;
		}
		if (params == null) {
			return string;
		}
		String buffer = string;
		for (String key : params.keySet()) {
			Object object = params.get(key);
			String value = object == null ? "" : object.toString();
			buffer = replace(buffer, "${" + key + "}", value);
		}
		return buffer;
	}

	/**
	 * 文字列を指定文字長となるまで末端に文字を追加して提供します。<br>
	 * 既に指定文字長を超える文字列が指定された場合はそのままの文字列が返却されます。<br>
	 * また、nullが指定された場合は空文字列として返却されます(nullのまま返却されない事に注意して下さい)。<br>
	 * @param string 対象文字列
	 * @param length 補正後文字長
	 * @param c 補正文字
	 * @return 編集後文字列
	 */
	public static String padding(String string, int length, char c) {
		StringBuilder builder = new StringBuilder(nvl(string));
		while (len(builder) < length) {
			builder.append(c);
		}
		return builder.toString();
	}

	/**
	 * 文字列を指定文字長となるまで末端に繰返し文字群から提供される文字を追加して提供します。<br>
	 * 既に指定文字長を超える文字列が指定された場合はそのままの文字列が返却されます。<br>
	 * また、nullが指定された場合は空文字列として返却されます(nullのまま返却されない事に注意して下さい)。<br>
	 * @param string 対象文字列
	 * @param length 補正後文字長
	 * @param chars 補正対象文字群
	 * @return 編集後文字列
	 */
	public static String padding(String string, int length, StringBelt chars) {
		if (chars == null) {
			throw new NullPointerException("CharLoop");
		}
		StringBuilder builder = new StringBuilder(nvl(string));
		while (len(builder) < length) {
			builder.append(chars.next());
		}
		return builder.toString();
	}

	/**
	 * 文字列を指定バイト長となるまで末端に文字を追加して提供します。<br>
	 * 既に指定文字長を超える文字列が指定された場合はそのままの文字列が返却されます。<br>
	 * 尚、文字追加処理中にバイト長で1バイト分が欠落する文字追加となる場合は半角スペースとして補完されます("A"→'あ'で4バイト補完→"Aあ ")。
	 * また、nullが指定された場合は空文字列として返却されます(nullのまま返却されない事に注意して下さい)。<br>
	 * @param string 対象文字列
	 * @param length 補正後バイト長
	 * @param c 補正文字
	 * @param charset キャラクタセット
	 * @return 編集後文字列
	 */
	public static String paddingb(String string, int length, char c, String charset) {
		StringBuilder builder = new StringBuilder(nvl(string));
		for (int nlen; (nlen = lenb(builder, charset)) < length;) {
			int clen = lenb(c, charset);
			if (nlen + clen > length) {
				while (nlen < length) {
					builder.append(" ");
					nlen++;
				}
			} else {
				builder.append(c);
			}
		}
		return builder.toString();
	}

	/**
	 * 文字列を指定バイト長となるまで末端に文字を追加して提供します。<br>
	 * 既に指定文字長を超える文字列が指定された場合はそのままの文字列が返却されます。<br>
	 * 尚、文字追加処理中にバイト長で1バイト分が欠落する文字追加となる場合は半角スペースとして補完されます("A"→'あ'で4バイト補完→"Aあ ")。
	 * また、nullが指定された場合は空文字列として返却されます(nullのまま返却されない事に注意して下さい)。<br>
	 * @param string 対象文字列
	 * @param length 補正後バイト長
	 * @param c 補正文字
	 * @return 編集後文字列
	 */
	public static String paddingb(String string, int length, char c) {
		return paddingb(string, length, c, null);
	}

	/**
	 * 文字列を指定バイト長となるまで末端に繰返し文字群から提供される文字を追加して提供します。<br>
	 * 既に指定文字長を超える文字列が指定された場合はそのままの文字列が返却されます。<br>
	 * 尚、文字追加処理中にバイト長で1バイト分が欠落する文字追加となる場合は半角スペースとして補完されます("A"→'あ'で4バイト補完→"Aあ ")。
	 * また、nullが指定された場合は空文字列として返却されます(nullのまま返却されない事に注意して下さい)。<br>
	 * @param string 対象文字列
	 * @param length 補正後バイト長
	 * @param chars 補正対象文字群
	 * @param charset キャラクタセット
	 * @return 編集後文字列
	 */
	public static String paddingb(String string, int length, StringBelt chars, String charset) {
		if (chars == null) {
			throw new NullPointerException("CharLoop");
		}
		StringBuilder builder = new StringBuilder(nvl(string));
		for (int nlen; (nlen = lenb(builder, charset)) < length;) {
			char c = chars.next();
			int clen = lenb(c, charset);
			if (nlen + clen > length) {
				while (nlen < length) {
					builder.append(" ");
					nlen++;
				}
			} else {
				builder.append(c);
			}
		}
		return builder.toString();
	}

	/**
	 * 文字列を指定バイト長となるまで末端に繰返し文字群から提供される文字を追加して提供します。<br>
	 * 既に指定文字長を超える文字列が指定された場合はそのままの文字列が返却されます。<br>
	 * 尚、文字追加処理中にバイト長で1バイト分が欠落する文字追加となる場合は半角スペースとして補完されます("A"→'あ'で4バイト補完→"Aあ ")。
	 * また、nullが指定された場合は空文字列として返却されます(nullのまま返却されない事に注意して下さい)。<br>
	 * @param string 対象文字列
	 * @param length 補正後バイト長
	 * @param chars 補正対象文字群
	 * @return 編集後文字列
	 */
	public static String paddingb(String string, int length, StringBelt chars) {
		return paddingb(string, length, chars, null);
	}

	/**
	 * 文字列を指定半角桁長となるまで末端に文字を追加して提供します。<br>
	 * 既に指定文字長を超える文字列が指定された場合はそのままの文字列が返却されます。<br>
	 * 尚、文字追加処理中に半角桁長で1半角桁分が欠落する文字追加となる場合は半角スペースとして補完されます("A"→'あ'で4半角桁補完→"Aあ ")。
	 * また、nullが指定された場合は空文字列として返却されます(nullのまま返却されない事に注意して下さい)。<br>
	 * @param string 対象文字列
	 * @param length 補正後半角桁長
	 * @param c 補正文字
	 * @return 編集後文字列
	 */
	public static String paddinga(String string, int length, char c) {
		StringBuilder builder = new StringBuilder(nvl(string));
		for (int nlen; (nlen = lena(builder)) < length;) {
			int clen = lena(c);
			if (nlen + clen > length) {
				builder.append(" ");
				nlen++;
			} else {
				builder.append(c);
			}
		}
		return builder.toString();
	}

	/**
	 * 文字列を指定半角桁長となるまで末端に繰返し文字群から提供される文字を追加して提供します。<br>
	 * 既に指定文字長を超える文字列が指定された場合はそのままの文字列が返却されます。<br>
	 * 尚、文字追加処理中に半角桁長で1半角桁分が欠落する文字追加となる場合は半角スペースとして補完されます("A"→'あ'で4半角桁補完→"Aあ ")。
	 * また、nullが指定された場合は空文字列として返却されます(nullのまま返却されない事に注意して下さい)。<br>
	 * @param string 対象文字列
	 * @param length 補正後半角桁長
	 * @param chars 補正対象文字群
	 * @return 編集後文字列
	 */
	public static String paddinga(String string, int length, StringBelt chars) {
		if (chars == null) {
			throw new NullPointerException("CharLoop");
		}
		StringBuilder builder = new StringBuilder(nvl(string));
		for (int nlen; (nlen = lena(builder)) < length;) {
			char c = chars.next();
			int clen = lena(c);
			if (nlen + clen > length) {
				builder.append(" ");
				nlen++;
			} else {
				builder.append(c);
			}
		}
		return builder.toString();
	}

	/**
	 * 文字列を指定文字長となるまで先頭に文字を追加して提供します。<br>
	 * 既に指定文字長を超える文字列が指定された場合はそのままの文字列が返却されます。<br>
	 * また、nullが指定された場合は空文字列として返却されます(nullのまま返却されない事に注意して下さい)。<br>
	 * @param string 対象文字列
	 * @param length 補正後文字長
	 * @param c 補正文字
	 * @return 編集後文字列
	 */
	public static String insert(String string, int length, char c) {
		StringBuilder builder = new StringBuilder(nvl(string));
		while (len(builder) < length) {
			builder.insert(0, c);
		}
		return builder.toString();
	}

	/**
	 * 文字列を指定文字長となるまで先頭に繰返し文字群から提供される文字を追加して提供します。<br>
	 * 既に指定文字長を超える文字列が指定された場合はそのままの文字列が返却されます。<br>
	 * また、nullが指定された場合は空文字列として返却されます(nullのまま返却されない事に注意して下さい)。<br>
	 * @param string 対象文字列
	 * @param length 補正後文字長
	 * @param chars 補正対象文字群
	 * @return 編集後文字列
	 */
	public static String insert(String string, int length, StringBelt chars) {
		if (chars == null) {
			throw new NullPointerException("CharLoop");
		}
		StringBuilder builder = new StringBuilder(nvl(string));
		int index = 0;
		while (len(builder) < length) {
			builder.insert(index++, chars.next());
		}
		return builder.toString();
	}

	/**
	 * 文字列を指定バイト長となるまで先頭に文字を追加して提供します。<br>
	 * 既に指定文字長を超える文字列が指定された場合はそのままの文字列が返却されます。<br>
	 * 尚、文字追加処理中にバイト長で1バイト分が欠落する文字追加となる場合は半角スペースとして補完されます("A"→'あ'で4バイト補完→"Aあ ")。
	 * また、nullが指定された場合は空文字列として返却されます(nullのまま返却されない事に注意して下さい)。<br>
	 * @param string 対象文字列
	 * @param length 補正後バイト長
	 * @param c 補正文字
	 * @param charset キャラクタセット
	 * @return 編集後文字列
	 */
	public static String insertb(String string, int length, char c, String charset) {
		StringBuilder builder = new StringBuilder(nvl(string));
		for (int nlen; (nlen = lenb(builder, charset)) < length;) {
			int clen = lenb(c, charset);
			if (nlen + clen > length) {
				while (nlen < length) {
					builder.insert(0, " ");
					nlen++;
				}
			} else {
				builder.insert(0, c);
			}
		}
		return builder.toString();
	}

	/**
	 * 文字列を指定バイト長となるまで先頭に文字を追加して提供します。<br>
	 * 既に指定文字長を超える文字列が指定された場合はそのままの文字列が返却されます。<br>
	 * 尚、文字追加処理中にバイト長で1バイト分が欠落する文字追加となる場合は半角スペースとして補完されます("A"→'あ'で4バイト補完→"Aあ ")。
	 * また、nullが指定された場合は空文字列として返却されます(nullのまま返却されない事に注意して下さい)。<br>
	 * @param string 対象文字列
	 * @param length 補正後バイト長
	 * @param c 補正文字
	 * @return 編集後文字列
	 */
	public static String insertb(String string, int length, char c) {
		return insertb(string, length, c, null);
	}

	/**
	 * 文字列を指定バイト長となるまで先頭に繰返し文字群から提供される文字を追加して提供します。<br>
	 * 既に指定文字長を超える文字列が指定された場合はそのままの文字列が返却されます。<br>
	 * 尚、文字追加処理中にバイト長で1バイト分が欠落する文字追加となる場合は半角スペースとして補完されます("A"→'あ'で4バイト補完→"Aあ ")。
	 * また、nullが指定された場合は空文字列として返却されます(nullのまま返却されない事に注意して下さい)。<br>
	 * @param string 対象文字列
	 * @param length 補正後バイト長
	 * @param chars 補正対象文字群
	 * @param charset キャラクタセット
	 * @return 編集後文字列
	 */
	public static String insertb(String string, int length, StringBelt chars, String charset) {
		if (chars == null) {
			throw new NullPointerException("CharLoop");
		}
		StringBuilder builder = new StringBuilder(nvl(string));
		int index = 0;
		for (int nlen; (nlen = lenb(builder, charset)) < length;) {
			char c = chars.next();
			int clen = lenb(c, charset);
			if (nlen + clen > length) {
				while (nlen < length) {
					builder.insert(0, " ");
					nlen++;
				}
			} else {
				builder.insert(index++, c);
			}
		}
		return builder.toString();
	}

	/**
	 * 文字列を指定バイト長となるまで先頭に繰返し文字群から提供される文字を追加して提供します。<br>
	 * 既に指定文字長を超える文字列が指定された場合はそのままの文字列が返却されます。<br>
	 * 尚、文字追加処理中にバイト長で1バイト分が欠落する文字追加となる場合は半角スペースとして補完されます("A"→'あ'で4バイト補完→"Aあ ")。
	 * また、nullが指定された場合は空文字列として返却されます(nullのまま返却されない事に注意して下さい)。<br>
	 * @param string 対象文字列
	 * @param length 補正後バイト長
	 * @param chars 補正対象文字群
	 * @return 編集後文字列
	 */
	public static String insertb(String string, int length, StringBelt chars) {
		return insertb(string, length, chars, null);
	}

	/**
	 * 文字列を指定半角桁長となるまで先頭に文字を追加して提供します。<br>
	 * 既に指定文字長を超える文字列が指定された場合はそのままの文字列が返却されます。<br>
	 * 尚、文字追加処理中に半角桁長で1半角桁分が欠落する文字追加となる場合は半角スペースとして補完されます("A"→'あ'で4半角桁補完→"Aあ ")。
	 * また、nullが指定された場合は空文字列として返却されます(nullのまま返却されない事に注意して下さい)。<br>
	 * @param string 対象文字列
	 * @param length 補正後半角桁長
	 * @param c 補正文字
	 * @return 編集後文字列
	 */
	public static String inserta(String string, int length, char c) {
		StringBuilder builder = new StringBuilder(nvl(string));
		for (int nlen; (nlen = lena(builder)) < length;) {
			int clen = lena(c);
			if (nlen + clen > length) {
				builder.insert(0, " ");
				nlen++;
			} else {
				builder.insert(0, c);
			}
		}
		return builder.toString();
	}

	/**
	 * 文字列を指定半角桁長となるまで先頭に繰返し文字群から提供される文字を追加して提供します。<br>
	 * 既に指定文字長を超える文字列が指定された場合はそのままの文字列が返却されます。<br>
	 * 尚、文字追加処理中に半角桁長で1半角桁分が欠落する文字追加となる場合は半角スペースとして補完されます("A"→'あ'で4半角桁補完→"Aあ ")。
	 * また、nullが指定された場合は空文字列として返却されます(nullのまま返却されない事に注意して下さい)。<br>
	 * @param string 対象文字列
	 * @param length 補正後半角桁長
	 * @param chars 補正対象文字群
	 * @return 編集後文字列
	 */
	public static String inserta(String string, int length, StringBelt chars) {
		if (chars == null) {
			throw new NullPointerException("CharLoop");
		}
		StringBuilder builder = new StringBuilder(nvl(string));
		int index = 0;
		for (int nlen; (nlen = lena(builder)) < length;) {
			char c = chars.next();
			int clen = lena(c);
			if (nlen + clen > length) {
				builder.insert(0, " ");
				nlen++;
			} else {
				builder.insert(index++, c);
			}
		}
		return builder.toString();
	}

	/**
	 * 文字列内の数値に関して全角から半角に変換して提供します。<br>
	 * @param string 対象文字列
	 * @return 変換後文字列
	 */
	public static String convertJp2AsciiNumeric(String string) {
		if (isEmpty(string)) {
			return EMPTY;
		}
		//for (String s : MAP_J2A_NUMERIC.keySet()) {
		//	string = string.replaceAll(Pattern.quote(s), MAP_J2A_NUMERIC.get(s));
		//}
		//return string;
		// ↓パフォーマンスチューニング(1000000繰返しで5800ms→330ms)
		StringBuilder builder = new StringBuilder();
		for (char c : string.toCharArray()) {
			if (MAP_J2A_NUMERIC.containsKey(c)) {
				builder.append(MAP_J2A_NUMERIC.get(c));
			} else {
				builder.append(c);
			}
		}
		return builder.toString();
	}

	/**
	 * 文字列内の英字に関して全角から半角に変換して提供します。<br>
	 * @param string 対象文字列
	 * @return 変換後文字列
	 */
	public static String convertJp2AsciiAlphabet(String string) {
		if (isEmpty(string)) {
			return EMPTY;
		}
		//for (String s : MAP_J2A_ALPHABET.keySet()) {
		//	string = string.replaceAll(Pattern.quote(s), MAP_J2A_ALPHABET.get(s));
		//}
		//return string;
		// ↓パフォーマンスチューニング(1000000繰返しで5800ms→330ms)
		StringBuilder builder = new StringBuilder();
		for (char c : string.toCharArray()) {
			if (MAP_J2A_ALPHABET.containsKey(c)) {
				builder.append(MAP_J2A_ALPHABET.get(c));
			} else {
				builder.append(c);
			}
		}
		return builder.toString();
	}

	/**
	 * 文字列内のかな文字に関して全角から半角に変換して提供します。<br>
	 * @param string 対象文字列
	 * @return 変換後文字列
	 */
	public static String converJp2AsciiKana(String string) {
		if (isEmpty(string)) {
			return EMPTY;
		}
		//for (String s : MAP_J2A_KANA.keySet()) {
		//	string = string.replaceAll(Pattern.quote(s), MAP_J2A_KANA.get(s));
		//}
		//return string;
		// ↓パフォーマンスチューニング(1000000繰返しで5800ms→330ms)
		StringBuilder builder = new StringBuilder();
		for (char c : string.toCharArray()) {
			if (MAP_J2A_KANA.containsKey(c)) {
				builder.append(MAP_J2A_KANA.get(c));
			} else {
				builder.append(c);
			}
		}
		return builder.toString();
	}

	/**
	 * 文字列内の半角変換可能な記号に関して全角から半角に変換して提供します。<br>
	 * @param string 対象文字列
	 * @return 変換後文字列
	 */
	public static String convertJp2AsciiSign(String string) {
		if (isEmpty(string)) {
			return EMPTY;
		}
		//for (String s : MAP_J2A_SIGN.keySet()) {
		//	string = string.replaceAll(Pattern.quote(s), MAP_J2A_SIGN.get(s));
		//}
		//return string;
		// ↓パフォーマンスチューニング(1000000繰返しで5800ms→330ms)
		StringBuilder builder = new StringBuilder();
		for (char c : string.toCharArray()) {
			if (MAP_J2A_SIGN.containsKey(c)) {
				builder.append(MAP_J2A_SIGN.get(c));
			} else {
				builder.append(c);
			}
		}
		return builder.toString();
	}

	/**
	 * 文字列内の半角変換可能な記号に関して全角から半角に変換して提供します。<br>
	 * @param string 対象文字列
	 * @return 変換後文字列
	 */
	public static String convertJp2Ascii(String string) {
		if (isEmpty(string)) {
			return EMPTY;
		}
		String buffer = string;
		buffer = convertJp2AsciiNumeric(buffer);
		buffer = convertJp2AsciiAlphabet(buffer);
		buffer = converJp2AsciiKana(buffer);
		buffer = convertJp2AsciiSign(buffer);
		return buffer;
	}

	/**
	 * 文字列内の数値に関して半角から全角に変換して提供します。<br>
	 * @param string 対象文字列
	 * @return 変換後文字列
	 */
	public static String convertAscii2JpNumeric(String string) {
		if (isEmpty(string)) {
			return EMPTY;
		}
		//for (String s : MAP_A2J_NUMERIC.keySet()) {
		//	string = string.replaceAll(Pattern.quote(s), MAP_A2J_NUMERIC.get(s));
		//}
		//return string;
		// ↓パフォーマンスチューニング(1000000繰返しで5800ms→330ms)
		StringBuilder builder = new StringBuilder();
		for (char c : string.toCharArray()) {
			if (MAP_A2J_NUMERIC.containsKey(c)) {
				builder.append(MAP_A2J_NUMERIC.get(c));
			} else {
				builder.append(c);
			}
		}
		return builder.toString();
	}

	/**
	 * 文字列内の英字に関して半角から全角に変換して提供します。<br>
	 * @param string 対象文字列
	 * @return 変換後文字列
	 */
	public static String convertAscii2JpAlphabet(String string) {
		if (isEmpty(string)) {
			return EMPTY;
		}
		//for (String s : MAP_A2J_ALPHABET.keySet()) {
		//	string = string.replaceAll(Pattern.quote(s), MAP_A2J_ALPHABET.get(s));
		//}
		//return string;
		// ↓パフォーマンスチューニング(1000000繰返しで5800ms→330ms)
		StringBuilder builder = new StringBuilder();
		for (char c : string.toCharArray()) {
			if (MAP_A2J_ALPHABET.containsKey(c)) {
				builder.append(MAP_A2J_ALPHABET.get(c));
			} else {
				builder.append(c);
			}
		}
		return builder.toString();
	}

	/**
	 * 文字列内のかな文字に関して半角から全角に変換して提供します。<br>
	 * @param string 対象文字列
	 * @return 変換後文字列
	 */
	public static String convertAscii2JpKana(String string) {
		if (isEmpty(string)) {
			return EMPTY;
		}
		for (String s : MAP_A2J_KANA.keySet()) {
			string = string.replaceAll(Pattern.quote(s), String.valueOf(MAP_A2J_KANA.get(s)));
		}
		return string;
		// ↓パフォーマンスチューニング不可↑2文字セットで1文字変換がある為
		//StringBuilder builder = new StringBuilder();
		//for (char c : string.toCharArray()) {
		//	if (MAP_A2J_KANA.containsKey(c)) {
		//		builder.append(MAP_A2J_KANA.get(c));
		//	} else {
		//		builder.append(c);
		//	}
		//}
		//return builder.toString();
	}

	/**
	 * 文字列内の半角変換可能な記号に関して半角から全角に変換して提供します。<br>
	 * @param string 対象文字列
	 * @return 変換後文字列
	 */
	public static String convertAscii2JpSign(String string) {
		if (isEmpty(string)) {
			return EMPTY;
		}
		//for (String s : MAP_A2J_SIGN.keySet()) {
		//	string = string.replaceAll(Pattern.quote(s), MAP_A2J_SIGN.get(s));
		//}
		//return string;
		// ↓パフォーマンスチューニング(1000000繰返しで5800ms→330ms)
		StringBuilder builder = new StringBuilder();
		for (char c : string.toCharArray()) {
			if (MAP_A2J_SIGN.containsKey(c)) {
				builder.append(MAP_A2J_SIGN.get(c));
			} else {
				builder.append(c);
			}
		}
		return builder.toString();
	}

	/**
	 * 文字列内の半角変換可能な記号に関して半角から全角に変換して提供します。<br>
	 * @param string 対象文字列
	 * @return 変換後文字列
	 */
	public static String convertAscii2Jp(String string) {
		if (isEmpty(string)) {
			return EMPTY;
		}
		String buffer = string;
		buffer = convertAscii2JpNumeric(buffer);
		buffer = convertAscii2JpAlphabet(buffer);
		buffer = convertAscii2JpKana(buffer);
		buffer = convertAscii2JpSign(buffer);
		return buffer;
	}

	/**
	 * 文字列内のカタナカ-ひらがな変換して提供します。<br>
	 * @param string 対象文字列
	 * @return 変換後文字列
	 */
	public static String convertKana2Hira(String string) {
		if (isEmpty(string)) {
			return EMPTY;
		}
		//for (String s : MAP_KANA2HIRA.keySet()) {
		//	string = string.replaceAll(Pattern.quote(s), MAP_A2J_SIGN.get(s));
		//}
		//return string;
		// ↓パフォーマンスチューニング(1000000繰返しで5800ms→330ms)
		StringBuilder builder = new StringBuilder();
		for (char c : string.toCharArray()) {
			if (MAP_KANA2HIRA.containsKey(c)) {
				builder.append(MAP_KANA2HIRA.get(c));
			} else {
				builder.append(c);
			}
		}
		return builder.toString();
	}

	/**
	 * 文字列内のひらがな-カタナカ変換して提供します。<br>
	 * @param string 対象文字列
	 * @return 変換後文字列
	 */
	public static String convertHira2Kana(String string) {
		if (isEmpty(string)) {
			return EMPTY;
		}
		//for (String s : MAP_HIRA2KANA.keySet()) {
		//	string = string.replaceAll(Pattern.quote(s), MAP_A2J_SIGN.get(s));
		//}
		//return string;
		// ↓パフォーマンスチューニング(1000000繰返しで5800ms→330ms)
		StringBuilder builder = new StringBuilder();
		for (char c : string.toCharArray()) {
			if (MAP_HIRA2KANA.containsKey(c)) {
				builder.append(MAP_HIRA2KANA.get(c));
			} else {
				builder.append(c);
			}
		}
		return builder.toString();
	}

	/**
	 * 文字列をHTMLソースとして出力する際に必要なエスケープ処理を行った上での文字列を提供します。<br>
	 * @param string 対象文字列
	 * @param textarea テキストエリアモードとする場合はtrueを指定(textareaやpreタグ内の場合に改行やタブを&lt;br&gt;等に変換しません)
	 * @return 変換後文字列
	 */
	public static String escapeHTML(String string, boolean textarea) {
		String buffer = string == null ? "" : string;
		buffer = replace(buffer, "&", "&amp;");
		buffer = replace(buffer, "<", "&lt;");
		buffer = replace(buffer, ">", "&gt;");
		buffer = replace(buffer, " ", "&nbsp;");
		if (!textarea) {
			buffer = replace(buffer, "\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
			buffer = replace(buffer, "\n", "<br>");
		}
		return buffer;
	}

	/**
	 * 文字列をHTMLソースとして出力する際に必要なエスケープ処理を行った上での文字列を提供します。<br>
	 * @param string 対象文字列
	 * @return 変換後文字列
	 */
	public static String escapeHTML(String string) {
		return escapeHTML(string, false);
	}

	/**
	 * 文字列をHTML属性値として出力する際に必要なエスケープ処理を行った上での文字列を提供します。<br>
	 * @param string 対象文字列
	 * @return 変換後文字列
	 */
	public static String escapeHTMLAttr(String string) {
		String buffer = string == null ? "" : string;
		buffer = replace(buffer, "&", "&amp;");
		buffer = replace(buffer, "<", "&lt;");
		buffer = replace(buffer, ">", "&gt;");
		buffer = replace(buffer, "\"", "&quot;");
		buffer = replace(buffer, "'", "&#039;");
		buffer = replace(buffer, " ", "&nbsp;");
		return buffer;
	}

	/**
	 * 文字列をURL文字列ににエンコードします。<br>
	 * @param value 対象文字列
	 * @param charset キャラクタセット
	 * @return エンコードされたURL文字列
	 */
	public static String encodeURL(String value, String charset) {
		try {
			return URLEncoder.encode(nvl(value), charset);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("unsupported charset (" + charset + ")");
		}
	}

	/**
	 * URLにエンコードされた文字列を通常の文字列にデコードします。<br>
	 * @param value 変対象文字列
	 * @param charset キャラクタセット
	 * @return デコードされた文字列
	 */
	public static String decodeURL(String value, String charset) {
		try {
			return URLDecoder.decode(nvl(value), charset);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("unsupported charset (" + charset + ")");
		}
	}

	/**
	 * 正規表現に合致する指定グループの文字列を取得します。<br>
	 * @param value 対象文字列
	 * @param regexp 正規表現(グループ取得可能とする為に"()"を正規表現文字列に含める必要があります)
	 * @param group 取得グループ番号(1～)
	 * @return 正規表現に合致した文字列結果配列
	 */
	public static String[] find(String value, String regexp, int group) {
		Matcher matcher = Pattern.compile(regexp).matcher(value);
		List<String> result = new LinkedList<>();
		while (matcher.find()) {
			if (group >= 1) {
				if (group <= matcher.groupCount()) {
					result.add(matcher.group(group));
				}
			} else {
				for (int i = 1; i <= matcher.groupCount(); i++) {
					result.add(matcher.group(i));
				}
			}
		}
		return result.toArray(new String[0]);
	}

	/**
	 * 正規表現に合致する文字列を全て取得します。<br>
	 * @param value 対象文字列
	 * @param regexp 正規表現(グループ取得可能とする為に"()"を正規表現文字列に含める必要があります)
	 * @return 正規表現に合致した文字列結果配列
	 */
	public static String[] find(String value, String regexp) {
		return find(value, regexp, 0);
	}

	/**
	 * 正規表現で最初に合致する文字列を取得します。<br>
	 * @param value 検索対象文字列
	 * @param regexp 正規表現(グループ取得可能とする為に"()"を正規表現文字列に含める必要があります)
	 * @return 正規表現に合致した文字列結果
	 */
	public static String findAtFirst(String value, String regexp) {
		String[] result = find(value, regexp, 1);
		return result.length > 0 ? result[0] : "";
	}

	/**
	 * 文字列がマッチするインデックスをバイト位置ベースで取得します。<br>
	 * @param value 対象文字列
	 * @param match 検索文字列
	 * @param charset バイト処理を行う際のキャラクタセット
	 * @return インデックス
	 */
	public static int indexbOf(String value, String match, String charset) {
		if (isEmpty(value)) {
			return -1;
		}
		if (isEmpty(match)) {
			return 0;
		}
		int position = value.indexOf(match);
		if (position < 0) {
			return -1;
		}
		return lenb(value.substring(0, position), charset);
	}

	/**
	 * 文字列がマッチするインデックスをバイト位置ベースで取得します。<br>
	 * @param value 対象文字列
	 * @param match 検索文字列
	 * @return インデックス
	 */
	public static int indexbOf(String value, String match) {
		return indexbOf(value, match, null);
	}

	/**
	 * EBCDIC文字の16進コードが制御文字であるか判定します。<br>
	 * @param hex EBCDIC文字の16進コード表記文字列
	 * @return 制御文字である場合にtrueを返却
	 */
	public static boolean isEBCDICControlCharacter(String hex) {
		if (!hex.matches("[A-Za-z0-9]+")) {
			throw new IllegalArgumentException(hex + " not [A-Za-z0-9]+");
		}
		int code = Integer.decode("0x" + hex).intValue();
		if (code > EBCDIC_SJIS_MAP.length - 1) {
			return true;
		}
		if (code < 0) {
			return true;
		}
		int b = EBCDIC_SJIS_MAP[code];
		if ((b >= 0 && b <= 31) || (b >= 128 && b <= 160) || (b >= 224 && b <= 255) || (b == 127)) {
			return true;
		}
		return false;
	}

	/**
	 * EBCDIK文字の16進コードが制御文字であるか判定します。<br>
	 * @param hex EBCDIK文字の16進コード表記文字列
	 * @return 制御文字である場合にtrueを返却
	 */
	public static boolean isEBCDIKControlCharacter(String hex) {
		if (!hex.matches("[A-Za-z0-9]+")) {
			throw new IllegalArgumentException(hex + " not [A-Za-z0-9]+");
		}
		int code = Integer.decode("0x" + hex).intValue();
		if (code > EBCDIK_SJIS_MAP.length - 1) {
			return true;
		}
		if (code < 0) {
			return true;
		}
		int b = EBCDIK_SJIS_MAP[code];
		if ((b >= 0 && b <= 31) || (b >= 128 && b <= 160) || (b >= 224 && b <= 255) || (b == 127)) {
			return true;
		}
		return false;
	}

	/**
	 * KEIS83文字列の16進コードをデコードするうえで制御文字が含まれるか判定します。<br>
	 * @param hex KEIS83文字列の16進コード表記文字列
	 * @param type EBCDICコードタイプ
	 * @param so デコード処理時にシフトアウト状態で実行する場合にtrueを指定
	 * @return 制御文字が含まれる場合にtrueを返却
	 */
	public static boolean containsKEIS83ControlCharacter(String hex, EBCDICType type, boolean so) {
		try {
			decodeKEIS83(hex, type, so, true);
		} catch (IllegalArgumentException e) {
			return true;
		}
		return false;
	}

	/**
	 * KEIS83文字列の16進コードをデコードするうえで制御文字が含まれるか判定します。<br>
	 * シフトイン、シフトアウト状態については、シフトイン状態で判定した際に制御文字が含まれる際に、再帰的にシフトアウト状態で判定処理を行った結果を返却します。<br>
	 * これは利用者がシフトイン、シフトアウト状態を曖昧な状態で当処理を利用することを想定した動作仕様となります。<br>
	 * @param hex KEIS83文字列の16進コード表記文字列
	 * @param type EBCDICコードタイプ
	 * @return 制御文字が含まれる場合にtrueを返却
	 */
	public static boolean containsKEIS83ControlCharacter(String hex, EBCDICType type) {
		if (containsKEIS83ControlCharacter(hex, type, false)) {
			if (!containsKEIS83ControlCharacter(hex, type, true)) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	/**
	 * EBCDIC文字の16進コードをデコードした文字を提供します。<br>
	 * @param hex EBCDIC文字の16進コード表記文字列
	 * @return デコードした文字
	 */
	public static char decodeEBCDICCharacter(String hex) {
		if (!hex.matches("[A-Za-z0-9]+")) {
			throw new IllegalArgumentException(hex + " not [A-Za-z0-9]+");
		}
		int code = Integer.decode("0x" + hex).intValue();
		if (code > EBCDIC_SJIS_MAP.length - 1) {
			throw new IllegalArgumentException("0x" + hex + " > 255");
		}
		if (code < 0) {
			throw new IllegalArgumentException("0x" + hex + " < 0");
		}
		try {
			int b = EBCDIC_SJIS_MAP[code];
			//return new String(new byte[] { (byte) b }, "Windows-31J").charAt(0);
			return new String(new byte[] { (byte) b }, "Shift_JIS").charAt(0);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(hex, e);
		}
	}

	/**
	 * EBCDIK文字の16進コードをデコードした文字を提供します。<br>
	 * @param hex EBCDIK文字の16進コード表記文字列
	 * @return デコードした文字
	 */
	public static char decodeEBCDIKCharacter(String hex) {
		if (!hex.matches("[A-Za-z0-9]+")) {
			throw new IllegalArgumentException(hex + " not [A-Za-z0-9]+");
		}
		int code = Integer.decode("0x" + hex).intValue();
		if (code > EBCDIK_SJIS_MAP.length - 1) {
			throw new IllegalArgumentException("0x" + hex + " > 255");
		}
		if (code < 0) {
			throw new IllegalArgumentException("0x" + hex + " < 0");
		}
		try {
			int b = EBCDIK_SJIS_MAP[code];
			//return new String(new byte[] { (byte) b }, "Windows-31J").charAt(0);
			return new String(new byte[] { (byte) b }, "Shift_JIS").charAt(0);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(hex, e);
		}
	}

	/**
	 * KEIS83文字の16進コードをデコードした文字を提供します。<br>
	 * @param hex KEIS83文字の16進コード表記文字列
	 * @return デコードした文字
	 */
	public static char decodeKEIS83Character(String hex) {
		try {
			// 4文字byte文字列でない場合は半角スペースを返却
			if (hex.length() != 4) {
				//throw new IllegalArgumentException("hex.length != 4");
				return ' ';
			}

			/*
			 * 特殊コード対応
			 */
			// 0x4040の場合は全角スペース変換
			if ("4040".equals(hex)) {
				return '　';
			}
			// 0xA1A1の場合は全角スペース変換
			if ("A1A1".equals(hex)) {
				return '　';
			}

			/*
			 * KEIS上位/下位バイト分割
			 */
			int keis_hi_byte = Integer.decode("0x" + hex.substring(0, 2)).intValue();
			int keis_lo_byte = Integer.decode("0x" + hex.substring(2)).intValue();

			/*
			 * 上位/下位バイトの開始位置からのオフセットインデックス算出
			 * (KEIS上位/下位バイト範囲:A1(161)～FE(254) -> 00(0)～5D(93))
			 */
			int index_hb = keis_hi_byte - 161;
			int index_lb = keis_lo_byte - 161;
			int index = (index_hb * 94) + index_lb;

			// 1byte目変換
			int rhb = 0;
			rhb += KEIS83_SJIS_MAP_UPPER[index / KEIS83_SJIS_MAP_LOWER.length];

			// 2byte目変換
			int rlb = 0;
			rlb += KEIS83_SJIS_MAP_LOWER[index - ((index / KEIS83_SJIS_MAP_LOWER.length) * KEIS83_SJIS_MAP_LOWER.length)];

			// 文字列変換
			byte[] bs = new byte[2];
			bs[0] = (byte) rhb;
			bs[1] = (byte) rlb;
			String value = "";
			try {
				//value = new String(bs, "Windows-31J");
				value = new String(bs, "Shift_JIS");
			} catch (UnsupportedEncodingException e) {
				throw new IllegalArgumentException(hex, e);
			}

			/*
			 * UnicodeチルダとWindows-31Jチルダ置換
			 */
			if ("〜".equals(value)) {
				value = "～";
			}

			return value.charAt(0);
		} catch (Throwable e) {
			throw new IllegalArgumentException(hex, e);
		}
	}

	/**
	 * KEIS83文字列の16進コードをデコードして提供します。<br>
	 * @param hex KEIS83文字の16進コード表記文字列
	 * @param type EBCDICコードタイプ
	 * @param so デコード処理時にシフトアウト状態で実行する場合にtrueを指定
	 * @param errorOnControl 半角制御文字が含まれる場合に例外としてスローする場合はtrueを指定
	 * @return 変換後文字列
	 */
	public static String decodeKEIS83(String hex, EBCDICType type, boolean so, boolean errorOnControl) {
		StringBuilder buffer = new StringBuilder();

		/*
		 * 解析対象文字列に全角文字がある場合は、そのままの文字列として返却する
		 * (KEISコード指定方法が厳密でない定義資源から利用する場合に利用元で処理分岐が必要となる事を回避するため)
		 */
		if (!isAscii(hex)) {
			return hex;
		}

		/*
		 * シフトアウト状態(全角解析中)デコードが1byteの場合はエラー
		 */
		if (so && hex.length() < 4) {
			if (errorOnControl) {
				throw new IllegalArgumentException("0x" + hex + " is illegal character code.");
			} else {
				return UNKOWN_LERGACY_JCHAR;
			}
		}

		for (int i = 0; i <= hex.length() - 1; i += 4) {
			/*
			 * 上位ビット、下位ビット特定
			 */
			String value = substrb(hex, i, 4);
			String valueH = substrb(value, 0, 2).trim();
			if (valueH.length() == 0) {
				valueH = "00";
			}
			String valueL = substrb(value, 2, 2).trim();
			if (valueL.length() == 0) {
				valueL = "00";
			}
			int codeH = Integer.decode("0x" + valueH).intValue();
			int codeL = Integer.decode("0x" + valueL).intValue();

			/*
			 * 機能キャラクタ範囲内処理(無変換)
			 */
			if ("0A41".equals(value) || "0A42".equals(value)) {
				i += 4;
				// 末尾が機能キャラクタの場合は処理終了
				if (i > hex.length() - 1) {
					break;
				}
				while (true) {
					value = substrb(hex, i, 4);
					if ("0A41".equals(value) || "0A42".equals(value)) {
						break;
					}
					value = substrb(hex, i, 2);
					if (EBCDICType.EBCDIC.equals(type)) {
						buffer.append(decodeEBCDICCharacter(value));
						//} else if (EBCDICType.EBCDIK.equals(type)) {
					} else {
						buffer.append(decodeEBCDIKCharacter(value));
					}
					i += 2;
					if (i > hex.length() - 1) {
						break;
					}
				}
				continue;
			}

			/*
			 * SI/SO解析対象の場合のシフトコードは無変換
			 */
			//if (!shiftOut && ("28".equals(valueH) || "38".equals(valueH))) {
			if ("28".equals(valueH) || "38".equals(valueH)) {
				i -= 2;
				so = true;
				continue;
			}
			//if (shiftOut && ("29".equals(valueH))) {
			if ("29".equals(valueH)) {
				i -= 2;
				so = false;
				continue;
			}

			/*
			 * シフトアウト状態での全角スペース変換
			 */
			if (so) {
				// 0x4040の場合は全角スペース変換
				if ("4040".equals(value)) {
					buffer.append("　");
					continue;
				}
				// 0xA1A1の場合は全角スペース変換
				if ("A1A1".equals(value)) {
					buffer.append("　");
					continue;
				}
			}

			/*
			 * 1バイトコード(EBCDIKコード/EBCDICコード)判別(上位バイトが0x00～0x40)
			 */
			//boolean asciiCode = (charcode >= 0x00 && charcode <= 0x40);
			boolean asciiCode = true // 
					&& !(codeH >= 0x00 && codeH <= 0x30) //
					&& !(codeH >= 0x41 && codeH <= 0x41) //
					&& !(codeH >= 0xB8 && codeH <= 0xBF) //
					&& !(codeH >= 0xCA && codeH <= 0xCF) //
					&& !(codeH >= 0xDA && codeH <= 0xDF) //
					&& !(codeH >= 0xE1 && codeH <= 0xE1) //
					&& !(codeH >= 0xEA && codeH <= 0xEF) //
					&& !(codeH >= 0xFA && codeH <= 0xFF) //
			;

			/*
			 * シフトイン状態(半角解析中)で半角コードではない場合はエラー
			 */
			if (!so && !asciiCode) {
				if (errorOnControl) {
					throw new IllegalArgumentException("0x" + valueH + " is illegal character code.");
				} else {
					buffer.append(UNKOWN_LEGACY_ACHAR);
					i -= 2;
					continue;
				}
			}

			/*
			 * シフトイン状態(半角解析中)は半角コードとして変換
			 */
			if (!so) {
				if (EBCDICType.EBCDIC.equals(type)) {
					buffer.append(decodeEBCDICCharacter(valueH));
					//} else if (EBCDICType.EBCDIK.equals(type)) {
				} else {
					buffer.append(decodeEBCDIKCharacter(valueH));
				}
				i -= 2;
				continue;
			}

			/*
			 * SJIS外字コード(上位バイトが0x41～0xA0かつ下位バイトが0xA1～FE)
			 */
			if (true //
					&& (codeH >= 0x41 && codeH <= 0xA0) //
					&& (codeL >= 0xA1 && codeL <= 0xFE) //
			) {
				String sjisHex = (Integer.toHexString(codeH) + Integer.toHexString(codeL)).toUpperCase();
				if (EBCDIK_GAIJI_MAP.containsKey(sjisHex)) {
					buffer.append(EBCDIK_GAIJI_MAP.get(sjisHex));
				} else {
					buffer.append(UNKOWN_LERGACY_JCHAR);
				}
				continue;
			}

			/*
			 * SJIS未対応コード(上位バイトが0x41～0xFEかつ下位バイトが0x00～0x0A、または上位バイトが0xFF、または下位バイトが0xFF)
			 */
			if (false //
					|| (codeH >= 0x41 && codeH <= 0xFE && codeL >= 0x00 && codeL <= 0x0A) //
					|| (codeH == 0xFF || codeL == 0xFF) //
			) {
				String sjisHex = (Integer.toHexString(codeH) + Integer.toHexString(codeL)).toUpperCase();
				if (EBCDIK_GAIJI_MAP.containsKey(sjisHex)) {
					buffer.append(EBCDIK_GAIJI_MAP.get(sjisHex));
				} else {
					buffer.append(UNKOWN_LERGACY_JCHAR);
				}
				continue;
			}

			// 全角文字変換
			try {
				buffer.append(decodeKEIS83Character(value));
			} catch (IllegalArgumentException e) {
				if (errorOnControl) {
					throw e;
				}
			}
		}

		return buffer.toString();
	}

	/**
	 * KEIS83文字列の16進コードをデコードして提供します。<br>
	 * @param hex KEIS83文字の16進コード表記文字列
	 * @param type EBCDICコードタイプ
	 * @param so デコード処理時にシフトアウト状態で実行する場合にtrueを指定
	 * @return 変換後文字列
	 */
	public static String decodeKEIS83(String hex, EBCDICType type, boolean so) {
		return decodeKEIS83(hex, type, so, false);
	}

	/**
	 * KEIS83文字列の16進コードをデコードして提供します。<br>
	 * デコード対象文字列のシフトイン、シフトアウト状態については、{@link #containsKEIS83ControlCharacter(String, EBCDICType)}の判定仕様に基づいて自動判定を行います。<br>
	 * @param hex KEIS83文字の16進コード表記文字列
	 * @param type EBCDICコードタイプ
	 * @return 変換後文字列
	 */
	public static String decodeKEIS83(String hex, EBCDICType type) {
		if (containsKEIS83ControlCharacter(hex, type, false)) {
			if (!containsKEIS83ControlCharacter(hex, type, true)) {
				return decodeKEIS83(hex, type, true);
			} else {
				return decodeKEIS83(hex, type, false);
			}
		} else {
			return decodeKEIS83(hex, type, false);
		}
	}
}
