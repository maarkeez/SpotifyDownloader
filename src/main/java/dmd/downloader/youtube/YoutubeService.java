package dmd.downloader.youtube;

import dmd.downloader.youtube.api.VideoSearch;
import dmd.downloader.youtube.api.YoutubeAPI;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Slf4j
@Service
public class YoutubeService {

    @Autowired
    private YoutubeProperties youtubeProperties;

    private YoutubeAPI youtubeApiClient;

    @PostConstruct
    public void init() {
        String API_BASE_URL = "https://www.googleapis.com/";

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        youtubeApiClient = retrofit.create(YoutubeAPI.class);
    }

    public VideoSearch searchVideo(String search) throws IOException {
        return youtubeApiClient.searchVideo(youtubeProperties.getToken(),"id",search).execute().body();
    }
}
