package se.kth.md.lyo.samples;

import com.omertron.imdbapi.ImdbApi;
import com.omertron.imdbapi.model.ImdbList;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.SystemDefaultCredentialsProvider;
import org.eclipse.lyo.tools.store.ModelUnmarshallingException;
import org.eclipse.lyo.tools.store.Store;
import org.eclipse.lyo.tools.store.StoreAccessException;
import org.eclipse.lyo.tools.store.StoreFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
  private final static Logger log = LoggerFactory.getLogger(App.class);
  private static final Store store = StoreFactory.inMemory();
  public static final URI URI_TOP250 = URI.create("http://store.sample/top250");

  public static void main(String[] args) {
    try {
      long api_start = System.nanoTime();
      List<Movie> movies = getMovies();
      double api_usec = durationSince(api_start);
      log.warn("API retrieval took {}µs", api_usec);
      for (Movie movie : movies) {
        System.out.println(movie);
      }
      long store_start = System.nanoTime();
      getMovies();
      double store_usec = durationSince(store_start);
      log.warn("Store retrieval took {}µs", store_usec);
    } catch (StoreAccessException | ModelUnmarshallingException e) {
      log.error("Error accessing triplestore", e);
    }
  }

  private static double durationSince(final long start) {
    return (System.nanoTime() - start) / 1000.0;
  }

  private static List<Movie> getMovies() throws StoreAccessException, ModelUnmarshallingException {
    List<Movie> movies;
    final boolean inTripleStore = store.containsKey(URI_TOP250);
    if(!inTripleStore) {
      movies = getMoviesApi();
      store.putResources(URI_TOP250, movies);
    } else {
      movies = getMoviesStore();
    }
    return movies;
  }

  private static List<Movie> getMoviesStore()
    throws StoreAccessException, ModelUnmarshallingException {
    return store.getResources(URI_TOP250, Movie.class);
  }

  private static List<Movie> getMoviesApi() {
    ImdbApi api = getImdbApi();
    List<ImdbList> top250 = api.getTop250();
    final List<Movie> movies = new ArrayList<>();
    for (ImdbList m : top250) {
      movies.add(new Movie(m.getTitle(), m.getImdbId(), m.getRating(), m.getNumVotes()));
    }
    return movies;
  }

  private static ImdbApi getImdbApi() {
    HttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(
      RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build()).build();
    ImdbApi api = new ImdbApi(httpClient);
    api.setLocale(Locale.US);
    return api;
  }
}
