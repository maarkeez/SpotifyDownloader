package dmd.downloader.youtube;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import dmd.downloader.Application;
import dmd.downloader.connector.CommandService;
import dmd.downloader.spotify.SpotifyProperties;
import dmd.downloader.spotify.SpotifyService;
import dmd.downloader.youtube.api.Id;
import dmd.downloader.youtube.api.Item;
import dmd.downloader.youtube.api.VideoSearch;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

@Slf4j
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
public class YoutubeServiceTest {

    @Autowired
    private SpotifyProperties spotifyProperties;

    @Autowired
    private SpotifyService spotifyService;

    @Autowired
    private YoutubeService youtubeService;

    @Autowired
    private CommandService commandService;

    @Test
    public void test_downloadSpotifyPlaylistFromYoutube() throws IOException, SpotifyWebApiException {
       List<String> songs = spotifyService.searchPlayList(spotifyProperties.getPlayListId());
       songs.forEach(song -> downloadSongAsMp3FromYoutube(song));
    }

    void downloadSongAsMp3FromYoutube(String videoSearchStr)  {
        log.info("Starting download for: {}", videoSearchStr);
        try {
            VideoSearch videoSearch = youtubeService.searchVideo(videoSearchStr);
            log.debug("Search result: {}", videoSearch);
            Optional<String> firstVideoId = videoSearch.getItems().stream()
                    .filter(hasVideoKind())
                    .map(Item::getId)
                    .map(Id::getVideoId)
                    .filter(Objects::nonNull)
                    .findFirst();

            if (firstVideoId.isPresent()) {
                String videoId = firstVideoId.get();
                log.debug("Id found: {}", videoId);

                commandService.execute(
                        "youtube-dl.exe",
                        "https://www.youtube.com/watch?v=" + videoId,
                        "-x",
                        "--audio-format",
                        "\"mp3\"");
            }

            log.info("Finished download of: {}", videoSearchStr);
        }catch (Exception e){
            log.error("Could not download song: {}. Error: ", videoSearchStr, e);
        }

    }

    private Predicate<Item> hasVideoKind() {
        return item -> "youtube#video".equals(item.getId().getKind());
    }
}