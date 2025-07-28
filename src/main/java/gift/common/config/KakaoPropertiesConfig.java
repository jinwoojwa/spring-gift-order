package gift.common.config;

import gift.oauth.config.KakaoOauthProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(KakaoOauthProperties.class)
public class KakaoPropertiesConfig {
}
