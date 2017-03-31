package by.epam.naumovich.film_ordering.bean;

/**
 * This bean class describes a film entity
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class Film {

	private int id;
	private String name;
	private int year;
	private String director;
	private String country;
	private String genre;
	private String actors;
	private String composer;
	private String description;
	private int length;
	private float rating;
	private float price;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public String getComposer() {
		return composer;
	}

	public void setComposer(String composer) {
		this.composer = composer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		int hash = 7;
	    hash = 7 * hash + id;
	    hash = 19 * hash + (name != null ? name.hashCode() : 0);
	    hash = 13 * hash + (director != null ? director.hashCode() : 0);
	    hash = 7 * hash + year;
	    hash = 23 * hash + (country != null ? country.hashCode() : 0);
	    hash = 29 * hash + (genre != null ? genre.hashCode() : 0);
	    hash = 3 * hash + (actors != null ? actors.hashCode() : 0);
	    hash = 1 * hash + (composer != null ? composer.hashCode() : 0);
	    hash = 61 * hash + (description != null ? description.hashCode() : 0);
	    hash = 31 * hash + length;
	    hash = 47 * hash + Float.floatToIntBits(rating);
	    hash = 3 * hash + Float.floatToIntBits(price);
	    return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { 
			return true; 
		}
		if (null == obj) { 
			return false; 
		}
		if (getClass() != obj.getClass()) { 
			return false;
		}
		
		Film film = (Film)obj;
		if (id != film.id) { 
			return false; 
		}
		if (year != film.year) { 
			return false; 
		}
		if (length != film.length) { 
			return false; 
		}
		if (Float.floatToIntBits(rating) != Float.floatToIntBits(film.rating)) { 
			return false; 
		}
		if (Float.floatToIntBits(price) != Float.floatToIntBits(film.price)) { 
			return false; 
		}
		
		if ((null == name) ? (null != film.name) : (!name.equals(film.name))) {
			return false;
		}
		if ((null == director) ? (null != film.director) : (!director.equals(film.director))) {
			return false;
		}
		if ((null == country) ? (null != film.country) : (!country.equals(film.country))) {
			return false;
		}
		if ((null == genre) ? (null != film.genre) : (!genre.equals(film.genre))) {
			return false;
		}
		if ((null == actors) ? (null != film.actors) : (!actors.equals(film.actors))) {
			return false;
		}
		if ((null == composer) ? (null != film.composer) : (!composer.equals(film.composer))) {
			return false;
		}
		if ((null == description) ? (null != film.description) : (!description.equals(film.description))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();

		result.append(this.getClass().getSimpleName() + " Object {");
		result.append(" ID: " + id);
		result.append(", Name: " + name);
		result.append(", Year: " + year);
		if (director != null) {
			result.append(", Director: " + director);
		}
		if (country != null) {
			result.append(", Country(-ies): " + country);
		}
		if (genre != null) {
			result.append(", Genre(s): " + genre);
		}
		if (actors != null) {
			result.append(", Actors: " + actors);
		}
		if (composer != null) {
			result.append(", Composer: " + composer);
		}
		if (description != null) {
			result.append(", Description: " + description);
		}
		if (length != 0) {
			result.append(", Length: " + length);
		}
		result.append(", Rating: " + rating);
		result.append(", Price: " + price);
		result.append("}");

		return result.toString();
	}
	
}
