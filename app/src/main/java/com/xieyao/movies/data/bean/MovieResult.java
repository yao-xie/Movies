package com.xieyao.movies.data.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xieyao on 2019-10-11.
 */
public class MovieResult {

	@SerializedName("page")
	private int page;

	@SerializedName("total_pages")
	private int totalPages;

	@SerializedName("results")
	private List<MovieItem> results;

	@SerializedName("total_results")
	private int totalResults;

	public void setPage(int page){
		this.page = page;
	}

	public int getPage(){
		return page;
	}

	public void setTotalPages(int totalPages){
		this.totalPages = totalPages;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public void setResults(List<MovieItem> results){
		this.results = results;
	}

	public List<MovieItem> getResults(){
		return results;
	}

	public void setTotalResults(int totalResults){
		this.totalResults = totalResults;
	}

	public int getTotalResults(){
		return totalResults;
	}

	@Override
 	public String toString(){
		return
			"MovieResult{" +
			"page = '" + page + '\'' +
			",total_pages = '" + totalPages + '\'' +
			",results = '" + results + '\'' +
			",total_results = '" + totalResults + '\'' +
			"}";
		}
}