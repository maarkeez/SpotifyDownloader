package dmd.downloader.spotify;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class SpotifyService {

    @Autowired
    private SpotifyProperties spotifyProperties;

    public List<String> searchPlayList(String playListId) throws IOException, SpotifyWebApiException {

        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(spotifyProperties.getClientId())
                .setClientSecret(spotifyProperties.getClientSecret())
                .build();

        ClientCredentials clientCredentials = spotifyApi.clientCredentials().build().execute();

        spotifyApi.setAccessToken(clientCredentials.getAccessToken());
        log.info("Your Spotify session expires in: {}", clientCredentials.getExpiresIn());

        Playlist playlist = spotifyApi.getPlaylist(playListId).build().execute();

        Paging<PlaylistTrack> playlistTrackPaging = playlist.getTracks();

        return Arrays.stream(playlistTrackPaging.getItems())
                .map(track -> {
                    Track trackObj = track.getTrack();

                    String trackName = trackObj.getName();

                    String author = Arrays.stream(trackObj.getArtists())
                            .map(ArtistSimplified::getName
                            ).collect(joining(" "));

                    return trackName + " " + author;

                })
                .collect(toList());
    }
}
