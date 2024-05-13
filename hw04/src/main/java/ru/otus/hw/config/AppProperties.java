package ru.otus.hw.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "test")
public class AppProperties implements TestConfig, TestFileNameProvider, LocaleConfig {

    private int rightAnswersCountToPass;

    private Locale locale;

    private Map<String, String> fileNameByLocaleTag;

    public void setLocale(String locale) {
        this.locale = Locale.forLanguageTag(locale);
    }

    @Override
    public String getTestFileName() {
        return fileNameByLocaleTag.get(locale.toLanguageTag());
    }
}
