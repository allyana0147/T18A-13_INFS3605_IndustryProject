package com.example.infs3605_industry_project;

import java.util.ArrayList;

public class Movie {
    public Movie(String id, String movie_name, String release_date, String rating, String genre, String description, String image, String browser_link, String time)  {
        this.id = id;
        this.movie_name = movie_name;
        this.release_date = release_date;
        this.rating = rating;
        this.genre = genre;
        this.description = description;
        this.image = image;
        this.browser_link = browser_link;
        this.time = time;

    }

    private String movie_name;
    private String id;
    private String release_date;
    //rating tells the rating of the movie out of 10
    private String rating;
    private String genre;
    //description tells a short overview of the movie
    private String description;
    private String image;
    //browser link directs to trailer on youtube
    private String browser_link;
    // time tells the duration or length of movie
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBrowser_link() {
        return browser_link;
    }

    public void setBrowser_link(String browser_link) {
        this.browser_link = browser_link;
    }

    //creates top 10 movies objects stored in array list called 'movies'
    public static ArrayList<Movie> getMovies() {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("mv1", "Lion King", "1994", "9.9", "Animation", "Lion prince Simba and his father are targeted by his bitter uncle, who wants to ascend the throne himself.", "lion_king_image", "https://www.youtube.com/watch?v=lFzVJEksoDY", "120 min"));
        movies.add(new Movie("mv2", "John Wick", "2014", "9.5", "Action", "An ex-hit-man comes out of retirement to track down the gangsters that killed his dog and took everything from him.", "john_wick_image", "https://www.youtube.com/watch?v=2AUmvWm5ZDQ", "110 min"));
        movies.add(new Movie("mv3", "The Notebook", "2004", "9.5", "Drama", "A poor yet passionate young man falls in love with a rich young woman, giving her a sense of freedom, but they are soon separated because of their social differences.", "notebook_image", "https://www.youtube.com/watch?v=FC6biTjEyZw", "130 min"));
        movies.add(new Movie("mv4", "Mulan", "1998", "9.4", "Animation", "To save her father from death in the army, a young maiden secretly goes in his place and becomes one of China's greatest heroines in the process.", "mulan_image", "https://www.youtube.com/watch?v=HKH7_n425Ss", "140 min"));
        movies.add(new Movie("mv5", "Alice in Wonderland", "2010", "9.4", "Adventure", "Nineteen-year-old Alice returns to the magical world from her childhood adventure, where she reunites with her old friends and learns of her true destiny: to end the Red Queen's reign of terror.", "alice_in_wonderland_image", "https://www.youtube.com/watch?v=9POCgSRVvf0", "150 min"));
        movies.add(new Movie("mv6", "Beauty and the Beast", "1992", "9.3",  "Animation","A prince cursed to spend his days as a hideous monster sets out to regain his humanity by earning a young woman's love.","beauty_and_the_beast_image","https://www.youtube.com/watch?v=iurbZwxKFUE", "120 min"));
        movies.add(new Movie("mv7", "Jumanji", "2017", "9.2",  "Comedy", "Four teenagers are sucked into a magical video game, and the only way they can escape is to work together to finish the game.", "jumanji_image", "https://www.youtube.com/watch?v=2QKg5SZ_35I", "150 min"));
        movies.add(new Movie("mv8", "Avengers", "2012", "9.0", "Action", "Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity.", "avengers_image", "https://www.youtube.com/watch?v=eOrNdBpGMv8", "160 min"));
        movies.add(new Movie("mv9", "Shawshank Redemption", "1995", "8.9",  "Drama", "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.", "shawshank_redemption_image", "https://www.youtube.com/watch?v=6hB3S9bIaco", "120 min"));
        movies.add(new Movie("mv10", "Ted", "2012", "8.8",  "Comedy", "John Bennett, a man whose childhood wish of bringing his teddy bear to life came true, now must decide between keeping the relationship with the bear, Ted or his girlfriend, Lori.","ted_image", "https://www.youtube.com/watch?v=9fbo_pQvU7M", "130 min"));

        return movies;
    }

    //returns a movie based on id
    public static Movie getMovie(String id) {
        ArrayList<Movie> movies = Movie.getMovies();
        for(final Movie movie : movies) {
            if (movie.getId().equals(id)) {
                return movie;
            }
        }
        return null;
    }
}

