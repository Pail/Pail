/**
 * Language.java
 *
 * Copyright (C) 2007,  Richard Midwinter
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.google.api.translate;

/**
 * Defines language information for the Google Translate API.
 *
 * @author Richard Midwinter
 * @author alosii
 * @author bjkuczynski
 */
public enum Language {
	AUTO_DETECT("", "Auto Detect"),
	AFRIKAANS("af", "Afrikaans"),
	ALBANIAN("sq", "Albanian"),
	AMHARIC("am", "Amharic"),
	ARABIC("ar", "Arabic"),
	ARMENIAN("hy", "Armenian"),
	AZERBAIJANI("az", "Azerbaijani"),
	BASQUE("eu", "Basque"),
	BELARUSIAN("be", "Belarusian"),
	BENGALI("bn", "Bengali"),
	BIHARI("bh", "Bihari"),
	BULGARIAN("bg", "Bulgarian"),
	BURMESE("my", "Burmese"),
	CATALAN("ca", "Catalan"),
	CHEROKEE("chr", "Cherokee"),
	CHINESE("zh", "Chinese"),
	CHINESE_SIMPLIFIED("zh-CN", "Simplified Chinese"),
	CHINESE_TRADITIONAL("zh-TW", "Traditional Chinese"),
	CROATIAN("hr", "Croatian"),
	CZECH("cs", "Czech"),
	DANISH("da", "Danish"),
	DHIVEHI("dv", "Dhivehi"),
	DUTCH("nl", "Dutch"),
	ENGLISH("en", "English"),
	ESPERANTO("eo", "Esperanto"),
	ESTONIAN("et", "Estonian"),
	FILIPINO("tl", "Filipino"),
	FINNISH("fi", "Finnish"),
	FRENCH("fr", "French"),
	GALICIAN("gl", "Galician"),
	GEORGIAN("ka", "Georgian"),
	GERMAN("de", "German"),
	GREEK("el", "Greek"),
	GUARANI("gn", "Gurani"),
	GUJARATI("gu", "Gujarati"),
	HEBREW("iw", "Hebrew"),
	HINDI("hi", "Hindi"),
	HUNGARIAN("hu", "Hungarian"),
	ICELANDIC("is", "Icelandic"),
	INDONESIAN("id", "Indonesian"),
	INUKTITUT("iu", "Inuktitut"),
	IRISH("ga", "Irish"),
	ITALIAN("it", "Italian"),
	JAPANESE("ja", "Japaneese"),
	KANNADA("kn", "Kannada"),
	KAZAKH("kk", "Kazakh"),
	KHMER("km", "Khmer"),
	KOREAN("ko", "Korean"),
	KURDISH("ku", "Kurdish"),
	KYRGYZ("ky", "Kyrgyz"),
	LAOTHIAN("lo", "Laothian"),
	LATVIAN("lv", "Latvian"),
	LITHUANIAN("lt", "Lithuanian"),
	MACEDONIAN("mk", "Macedonian"),
	MALAY("ms", "Malay"),
	MALAYALAM("ml", "Malayalam"),
	MALTESE("mt", "Maltese"),
	MARATHI("mr", "Marathi"),
	MONGOLIAN("mn", "Mongolian"),
	NEPALI("ne", "Nepali"),
	NORWEGIAN("no", "Norwegian"),
	ORIYA("or", "Oriya"),
	PASHTO("ps", "Pashto"),
	PERSIAN("fa", "Persian"),
	POLISH("pl", "Polish"),
	PORTUGUESE("pt", "Portuguese"),
	PUNJABI("pa", "Punjabi"),
	ROMANIAN("ro", "Romanian"),
	RUSSIAN("ru", "Russian"),
	SANSKRIT("sa", "Sanskrit"),
	SERBIAN("sr", "Serbian"),
	SINDHI("sd", "Sindhi"),
	SINHALESE("si", "Sinhalese"),
	SLOVAK("sk", "Slovak"),
	SLOVENIAN("sl", "Slovenian"),
	SPANISH("es", "Spanish"),
	SWAHILI("sw", "Swahili"),
	SWEDISH("sv", "Swedish"),
	TAJIK("tg", "Tajik"),
	TAMIL("ta", "Tamil"),
	TAGALOG("tl", "Tagalog"),
	TELUGU("te", "Telugu"),
	THAI("th", "Thai"),
	TIBETAN("bo", "Tibetan"),
	TURKISH("tr", "Turkish"),
	UKRANIAN("uk", "Ukranian"),
	URDU("ur", "Urdu"),
	UZBEK("uz", "Uzbek"),
	UIGHUR("ug", "Uighur"),
	VIETNAMESE("vi", "Vietnamese"),
	WELSH("cy", "Welsh"),
	YIDDISH("yi", "Yiddish");
	
	/**
	 * Google's String representation of this language.
	 */
	private final String language;

        private final String fullName;
	
	/**
	 * Enum constructor.
	 * @param pLanguage The language identifier.
	 */
	private Language(final String pLanguage, final String fn) {
            language = pLanguage;
            fullName = fn;
	}
	
	public static Language fromString(final String pLanguage) {
            for (Language l : values()) {
                if (pLanguage.equals(l.toString()) || pLanguage.equals(l.getFullName())) {
                    return l;
                }
            }
            return null;
	}
	
	/**
	 * Returns the String representation of this language.
	 * @return The String representation of this language.
	 */
	@Override
	public String toString() {
            return language;
	}

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }
}