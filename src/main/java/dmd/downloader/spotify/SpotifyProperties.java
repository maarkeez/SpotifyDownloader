package dmd.downloader.spotify;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "spotify")
@Data
public class SpotifyProperties {
    private String clientId;
    private String clientSecret;
    private String playListId;
}
