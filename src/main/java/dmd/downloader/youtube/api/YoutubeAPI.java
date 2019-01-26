package dmd.downloader.youtube.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface YoutubeAPI {

    @GET("youtube/v3/search")
    Call<VideoSearch> searchVideo(
            @Header("Authorization") String credentials,
            @Query("part") String part,
            @Query("q") String search);

}
