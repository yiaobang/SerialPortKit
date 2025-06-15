package com.yiaobang.serialportkit.i18n;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import java.util.*;


public class I18nManager {
    private static final Locale LOCALE_ZH = Locale.CHINESE;
    private static final Locale LOCALE_EN = Locale.ENGLISH;
    private static final Locale LOCALE_JA = Locale.JAPANESE;

    private static final List<Locale> SUPPORTED_LOCALES = Arrays.asList(LOCALE_EN, LOCALE_ZH, LOCALE_JA);
    private static final Map<Locale, Map<String, String>> languagePool = new HashMap<>();
    private static final ObjectProperty<Locale> currentLocaleProperty = new SimpleObjectProperty<>(LOCALE_EN);

    static {
        for (Locale locale : SUPPORTED_LOCALES) {
            loadLocale(locale);
        }
    }
    public static void bindL10nBatch(L18nBinding... binds) {
        for (L18nBinding b : binds) {
            bindL10n(b.property(), b.key());
        }
    }

    // 用于简化国际化属性绑定
    public static void bindL10n(StringProperty property, String key) {
        property.set(get(key));
        localeProperty().addListener((_, _, _) -> property.set(get(key)));
    }
    private static void loadLocale(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("com.yiaobang.serialportkit.i18n.messages", locale);
        Map<String, String> map = new HashMap<>();
        for (String key : bundle.keySet()) {
            map.put(key, bundle.getString(key));
        }
        languagePool.put(locale, map);
    }

    public static String get(String key) {
        Map<String, String> map = languagePool.get(getLocale());
        return map != null ? map.getOrDefault(key, key) : key;
    }

    public static void switchLocale(Locale locale) {
        if (SUPPORTED_LOCALES.contains(locale)) {
            currentLocaleProperty.set(locale);
        }
    }

    public static Locale getLocale() {
        return currentLocaleProperty.get();
    }

    public static ObjectProperty<Locale> localeProperty() {
        return currentLocaleProperty;
    }

    public static List<Locale> getSupportedLocales() {
        return SUPPORTED_LOCALES;
    }

    public static String getDisplayName(Locale locale) {
        if (locale.equals(LOCALE_ZH)) return "中文";
        if (locale.equals(LOCALE_EN)) return "English";
        if (locale.equals(LOCALE_JA)) return "日本語";
        return locale.getDisplayName(locale);
    }
}