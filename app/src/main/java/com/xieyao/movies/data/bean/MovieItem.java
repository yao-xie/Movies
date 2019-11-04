package com.xieyao.movies.data.bean;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by xieyao on 2019-10-11.
 */
public class MovieItem implements Parcelable {

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("original_language")
    @ColumnInfo(name = "original_language")
    @Expose
    private String originalLanguage;

    @SerializedName("original_title")
    @ColumnInfo(name = "original_title")
    @Expose
    private String originalTitle;

    @SerializedName("video")
    @Expose
    private boolean video;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    @Expose
    private String posterPath;

    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdrop_path")
    @Expose
    private String backdropPath;

    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    @Expose
    private String releaseDate;

    @SerializedName("vote_average")
    @ColumnInfo(name = "vote_average")
    @Expose
    private double voteAverage;

    @SerializedName("popularity")
    @Expose
    private double popularity;

    @SerializedName("adult")
    @Expose
    private boolean adult;

    @SerializedName("vote_count")
    @ColumnInfo(name = "vote_count")
    @Expose
    private int voteCount;

    @SerializedName("runtime")
    @ColumnInfo(name = "runtime")
    @Expose
    private int runTime;

    private int favorite;

    public MovieItem() {
    }

    @Ignore
    public MovieItem(int id) {
        this.id = id;
    }

    @Ignore
    public MovieItem(int id, int favorite) {
        this.id = id;
        this.favorite = favorite;
    }

    protected MovieItem(Parcel in) {
        id = in.readInt();
        overview = in.readString();
        originalLanguage = in.readString();
        originalTitle = in.readString();
        video = in.readByte() != 0;
        title = in.readString();
        posterPath = in.readString();
        backdropPath = in.readString();
        releaseDate = in.readString();
        voteAverage = in.readDouble();
        popularity = in.readDouble();
        adult = in.readByte() != 0;
        voteCount = in.readInt();
        runTime = in.readInt();
    }

    public static final Creator<MovieItem> CREATOR = new Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel in) {
            return new MovieItem(in);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOverview() {
        return overview;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public boolean isVideo() {
        return video;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        return
                "MovieItem{" +
                        "id = '" + id + '\'' +
                        ",overview = '" + overview + '\'' +
                        ",original_language = '" + originalLanguage + '\'' +
                        ",original_title = '" + originalTitle + '\'' +
                        ",video = '" + video + '\'' +
                        ",title = '" + title + '\'' +
//                        ",genre_ids = '" + genreIds + '\'' +
                        ",poster_path = '" + posterPath + '\'' +
                        ",backdrop_path = '" + backdropPath + '\'' +
                        ",release_date = '" + releaseDate + '\'' +
                        ",vote_average = '" + voteAverage + '\'' +
                        ",popularity = '" + popularity + '\'' +
                        ",adult = '" + adult + '\'' +
                        ",vote_count = '" + voteCount + '\'' +
                        ",runtime = '" + runTime + '\'' +
                        "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(overview);
        dest.writeString(originalLanguage);
        dest.writeString(originalTitle);
        dest.writeByte((byte) (video ? 1 : 0));
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(backdropPath);
        dest.writeString(releaseDate);
        dest.writeDouble(voteAverage);
        dest.writeDouble(popularity);
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeInt(voteCount);
        dest.writeInt(runTime);
    }

    public static MovieItem fromContentValues(ContentValues values) {
        MovieItem movieItem = new MovieItem();
        if (values.containsKey("id")) {
            movieItem.id = values.getAsInteger("id");
        }
        if (values.containsKey("overview")) {
            movieItem.overview = values.getAsString("overview");
        }
        if (values.containsKey("original_language")) {
            movieItem.originalLanguage = values.getAsString("original_language");
        }
        if (values.containsKey("original_title")) {
            movieItem.originalTitle = values.getAsString("original_title");
        }
        if (values.containsKey("video")) {
            movieItem.video = values.getAsBoolean("video");
        }
        if (values.containsKey("title")) {
            movieItem.title = values.getAsString("title");
        }
        if (values.containsKey("poster_path")) {
            movieItem.posterPath = values.getAsString("poster_path");
        }
        if (values.containsKey("backdrop_path")) {
            movieItem.backdropPath = values.getAsString("backdrop_path");
        }
        if (values.containsKey("release_date")) {
            movieItem.releaseDate = values.getAsString("release_date");
        }
        if (values.containsKey("vote_average")) {
            movieItem.voteAverage = values.getAsDouble("vote_average");
        }
        if (values.containsKey("popularity")) {
            movieItem.popularity = values.getAsDouble("popularity");
        }
        if (values.containsKey("adult")) {
            movieItem.adult = values.getAsBoolean("adult");
        }
        if (values.containsKey("vote_count")) {
            movieItem.voteCount = values.getAsInteger("vote_count");
        }
        if (values.containsKey("runtime")) {
            movieItem.runTime = values.getAsInteger("runtime");
        }
        if (values.containsKey("favorite")) {
            movieItem.favorite = values.getAsInteger("favorite");
        }
        return movieItem;
    }


}