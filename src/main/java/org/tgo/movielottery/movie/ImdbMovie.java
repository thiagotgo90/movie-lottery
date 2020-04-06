package org.tgo.movielottery.movie;

import lombok.Data;

@Data
public class ImdbMovie {
	
	private String title;
	private String year;
	private String rated;
	private String released;
	private String runtime;
	private String genre;
	private String director;
	private String writer;
	private String actors;
	private String plot;
	private String language;
	private String country;
	private String awards;
	private String poster;
	private Rating[] ratings;
	private String metascore;
	private String imdbRating;
	private String imdbVotes;
	private String imdbID;
	private String type;
	private String dvd;
	private String boxOffice;
	private String production;
	private String website;
	private String response;

}
