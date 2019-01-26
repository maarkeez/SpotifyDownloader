package dmd.downloader.youtube.api;

import lombok.Data;

import java.util.List;

@Data
public class VideoSearch {

    private String kind;
    private String etag;
    private String nextPageToken;
    private String regionCode;
    private PageInfo pageInfo;
    private List<Item> items;

}
