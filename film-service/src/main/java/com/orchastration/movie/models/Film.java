package com.orchastration.movie.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Film {

  @JsonProperty("film_id")
  private Integer filmId;

  @JsonProperty("title")
  private String title;

  @JsonProperty("description")
  private String description;

  @JsonProperty("release_year")
  private Integer releaseYear;

  @JsonProperty("language_id")
  private Integer languageId;

  @JsonProperty("original_language_id")
  private Object originalLanguageId;

  @JsonProperty("rental_duration")
  private Integer rentalDuration;

  @JsonProperty("rental_rate")
  private Double rentalRate;

  @JsonProperty("length")
  private Integer length;

  @JsonProperty("replacement_cost")
  private Double replacementCost;

  @JsonProperty("rating")
  private String rating;

  @JsonProperty("special_features")
  private String specialFeatures;

  @JsonProperty("last_update")
  private String lastUpdate;
  
  private List<Actor> actors;

  private int totalCast;
}
