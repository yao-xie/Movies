package com.xieyao.movies.data.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xieyao on 2019-10-11.
 */
public class TrailerResult {

    /**
     * id : 475557
     * results : [{"id":"5ccf56c8925141044e2c88d6","iso_639_1":"en","iso_3166_1":"US","key":"t433PEQGErc","name":"JOKER - Teaser Trailer","site":"YouTube","size":1080,"type":"Trailer"},{"id":"5d66ab95ba4802610c26a044","iso_639_1":"en","iso_3166_1":"US","key":"xRjvmVaFHkk","name":"JOKER \u2013 Final Trailer \u2013 Warner Bros.","site":"YouTube","size":1080,"type":"Trailer"},{"id":"5d95ddbb35818f0013d7c1b3","iso_639_1":"en","iso_3166_1":"US","key":"jk41yJH3pyQ","name":"Playing the mad clown | The Straits Times","site":"YouTube","size":1080,"type":"Featurette"}]
     */
    @SerializedName("id")
    private int id;

    @SerializedName("results")
    private List<TrailerItem> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<TrailerItem> getResults() {
        return results;
    }

    public void setResults(List<TrailerItem> results) {
        this.results = results;
    }


}
