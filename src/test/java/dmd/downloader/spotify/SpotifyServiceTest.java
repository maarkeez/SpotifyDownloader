package dmd.downloader.spotify;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import dmd.downloader.Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
public class SpotifyServiceTest {

    @Autowired
    private SpotifyService spotifyService;

    @Autowired
    private SpotifyProperties spotifyProperties;

    @Test
    public void searchPlayList() throws IOException, SpotifyWebApiException {
        List<String> songsNames = spotifyService.searchPlayList(spotifyProperties.getPlayListId());
        log.info("Songs in play list:");
        songsNames.forEach(song -> log.info(song));
    }
}