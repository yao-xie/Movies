package com.xieyao.movies.data.bean;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xieyao on 2019-10-12.
 */
public class ReviewItem {
    /**
     * author : SWITCH.
     * content : ‘The Lion King’ is a catastrophe; a new low in the ever-diminishing returns of Disney’s endless run of remakes. There’s nothing redeeming about it, with every decision either ill-conceived or mishandled to the point of incompetence. In Favreau’s hands, ‘The Lion King’ is rendered thunderously dull, lacking in any tension or complex characterisation, taking a laboriously long time to go nowhere and never once justifying its contentious existence. Even with my dislike of the original, I was flabbergasted at how thoroughly this film never attempts to understand why so many people love the 1994 film. If nothing else, this film makes it abundantly clear that Disney has no interest in making great cinema or honouring its own legacy. They don’t care whether the film is good or whether you enjoy it. All they care about is using nostalgia to trick you into buying your ticket so they can make as much money off you as they can, and maybe if they throw some recognisable iconic moments from your childhood on the screen, they may even be able to fool you into thinking you’d had a good time. ‘The Lion King’ is the ultimate diabolical apex of the commercialisation of nostalgia, and its inevitable box office success will just prove how easily we continue to be duped and how thoroughly they have trained us to not care about the quality of what we see. If this really is the future of mainstream cinema, then we are in serious, serious trouble.
     * - Daniel Lammin
     * <p>
     * Read Daniel's full article...
     * https://www.maketheswitch.com.au/article/review-the-lion-king-a-catastrophic-and-soulless-remake-of-a-disney-classic
     * id : 5d2ddc7d6a300b0011a469df
     * url : https://www.themoviedb.org/review/5d2ddc7d6a300b0011a469df
     */
    @SerializedName("author")
    private String author;
    @SerializedName("content")
    private String content;
    @PrimaryKey
    @NonNull
    @SerializedName("id")
    private String id;
    @SerializedName("url")
    private String url;

    @ColumnInfo(name = "movie_id")
    public int movieId;

    private boolean isExpand;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }
}
