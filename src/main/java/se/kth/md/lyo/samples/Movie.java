package se.kth.md.lyo.samples;

import org.eclipse.lyo.oslc4j.core.annotation.OslcName;
import org.eclipse.lyo.oslc4j.core.annotation.OslcNamespace;
import org.eclipse.lyo.oslc4j.core.annotation.OslcPropertyDefinition;
import org.eclipse.lyo.oslc4j.core.annotation.OslcResourceShape;
import org.eclipse.lyo.oslc4j.core.model.AbstractResource;

/**
 * Created on 18.01.17
 * @author Andrew Berezovskyi (andriib@kth.se)
 * @version $version-stub$
 * @since 0.0.1
 */
@OslcName("movie")
@OslcNamespace(Movie.MOVIE_NS)
@OslcResourceShape(title = "Movie Resource Shape", describes = Movie.MOVIE_NS + "movie")
public class Movie extends AbstractResource {
  static final String MOVIE_NS = "http://store.sample/ns#";
  private String title;
  private String externalId;
  private double rating;
  private int votesCount;

  public Movie() {
  }

  public Movie(final String title, final String externalId, final double rating,
    final int votesCount) {
    this.title = title;
    this.externalId = externalId;
    this.rating = rating;
    this.votesCount = votesCount;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Movie{");
    sb.append("title='").append(title).append('\'');
    sb.append(", externalId='").append(externalId).append('\'');
    sb.append(", rating=").append(rating);
    sb.append(", votesCount=").append(votesCount);
    sb.append('}');
    return sb.toString();
  }

  @OslcName("title")
  @OslcPropertyDefinition(MOVIE_NS + "title")
  public String getTitle() {
    return title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  @OslcName("external_id")
  @OslcPropertyDefinition(MOVIE_NS + "external_id")
  public String getExternalId() {
    return externalId;
  }

  public void setExternalId(final String externalId) {
    this.externalId = externalId;
  }

  @OslcName("rating")
  @OslcPropertyDefinition(MOVIE_NS + "rating")
  public double getRating() {
    return rating;
  }

  public void setRating(final double rating) {
    this.rating = rating;
  }

  @OslcName("votes")
  @OslcPropertyDefinition(MOVIE_NS + "votes")
  public int getVotesCount() {
    return votesCount;
  }

  public void setVotesCount(final int votesCount) {
    this.votesCount = votesCount;
  }

}
