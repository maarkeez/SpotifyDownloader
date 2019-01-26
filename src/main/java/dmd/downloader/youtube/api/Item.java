package dmd.downloader.youtube.api;

import lombok.Data;

@Data
public class Item {
    private String kind;
    private String etag;
    private Id id;
}
