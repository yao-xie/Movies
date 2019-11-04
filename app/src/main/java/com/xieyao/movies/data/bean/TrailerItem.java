package com.xieyao.movies.data.bean;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
import com.xieyao.movies.data.local.DBConstant;

/**
 * Created by xieyao on 2019-10-11.
 */
@Entity(tableName = DBConstant.TRAILERS_TABLE_NAME,
        foreignKeys = @ForeignKey(entity = MovieItem.class, parentColumns = "id", childColumns = "movie_id"))
public class TrailerItem {
    /**
     * id : 5ccf56c8925141044e2c88d6
     * iso_639_1 : en
     * iso_3166_1 : US
     * key : t433PEQGErc
     * name : JOKER - Teaser Trailer
     * site : YouTube
     * size : 1080
     * type : Trailer
     */
    @PrimaryKey
    @NonNull
    @SerializedName("id")
    private String id;
    @SerializedName("iso_639_1")
    private String iso_639_1;
    @SerializedName("iso_3166_1")
    private String iso_3166_1;
    @SerializedName("key")
    private String key;
    @SerializedName("name")
    private String name;
    @SerializedName("site")
    private String site;
    @SerializedName("size")
    private int size;
    @SerializedName("type")
    private String type;

    @ColumnInfo(name = "movie_id")
    public int movieId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}