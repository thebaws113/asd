package test2;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.repackaged.com.google.common.base.Joiner;

import test2.Auth;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import com.google.api.services.youtube.model.GeoPoint;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListModel;

/**
 * Print a list of videos matching a search term.
 *
 * @author Jeremy Walker
 */
public class Search {

	/**
	 * Define a global variable that identifies the name of a file that contains
	 * the developer's API key.
	 */
	private static final String PROPERTIES_FILENAME = "youtube.properties";

	private static final int NUMBER_OF_VIDEOS_RETURNED = 50;

	/**
	 * Define a global instance of a Youtube object, which will be used to make
	 * YouTube Data API requests.
	 */
	private static YouTube youtube;
	public static String key = "AIzaSyBMZcfkVBb23vO7aj0TirE18r4juWKu3w0";
	/**
	 * Initialize a YouTube object to search for videos on YouTube. Then display
	 * the name and thumbnail image of each video in the result set.
	 *
	 * @param args
	 *            command line args.
	 */
	static String[][] Title = new String[NUMBER_OF_VIDEOS_RETURNED][NUMBER_OF_VIDEOS_RETURNED];

	public static void main(String[] args) {
		// Read the developer key from the properties file.
		Properties properties = new Properties();

		try {
			// This object is used to make YouTube Data API requests. The last
			// argument is required, but since we don't need anything
			// initialized when the HttpRequest is initialized, we override
			// the interface and provide a no-op function.
			youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, new HttpRequestInitializer() {
				public void initialize(HttpRequest request) throws IOException {
				}
			}).setApplicationName("youtube-cmdline-search-sample").build();

			// Prompt the user to enter a query term.
			String queryTerm = getInputQuery();

			// Define the API request for retrieving search results.
			YouTube.Search.List search = youtube.search().list("id,snippet");

			// Set your developer key from the {{ Google Cloud Console }} for
			// non-authenticated requests. See:
			// {{ https://cloud.google.com/console }}
			// String apiKey = properties.getProperty("youtube.apikey");
			search.setKey(key);
			search.setQ(queryTerm);

			// Restrict the search results to only include videos. See:
			// https://developers.google.com/youtube/v3/docs/search/list#type
			search.setType("video");

			// To increase efficiency, only retrieve the fields that the
			// application uses.
			search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
			search.setMaxResults((long) NUMBER_OF_VIDEOS_RETURNED);
			
			
			 
			// Call the API and print results.
			SearchListResponse searchResponse = search.execute();
			List<SearchResult> searchResultList = searchResponse.getItems();
			views(searchResultList.iterator());
			if (searchResultList != null) {
				prettyPrint(searchResultList.iterator(), queryTerm);
			}

			frame();
		} catch (GoogleJsonResponseException e) {
			System.err.println(
					"There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());
		} catch (IOException e) {
			System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	static int i = 0;

	public static void saveImage(String imageUrl, String iu) throws IOException {
		URL url = new URL(imageUrl);
		String fileName = url.getFile();

		i++;
		String destName = "D:\\work\\" + iu + i + ".jpg";
		System.out.println(destName);

		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(destName);

		byte[] b = new byte[2048];
		int length;

		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}

		is.close();
		os.close();
	}

	/*
	 * Prompt the user to enter a query term and return the user-specified term.
	 */
	private static String getInputQuery() throws IOException {

		String inputQuery = "";

		System.out.print("Please enter a search term: ");
		// BufferedReader bReader = new BufferedReader(new
		// InputStreamReader(System.in));
		// inputQuery = bReader.readLine();

		if (inputQuery.length() < 1) {
			// Use the string "YouTube Developers Live" as a default.
			inputQuery = "Tsunami";
		}
		return inputQuery;
	}

	/*
	 * Prints out all results in the Iterator. For each result, print the title,
	 * video ID, and thumbnail.
	 *
	 * @param iteratorSearchResults Iterator of SearchResults to print
	 *
	 * @param query Search query (String)
	 */
	private static void prettyPrint(Iterator<SearchResult> iteratorSearchResults, String query) throws IOException {
		int k = 0;
		// System.out.println("\n=============================================================");
		// System.out.println(" First " + NUMBER_OF_VIDEOS_RETURNED + " videos
		// for search on \"" + query + "\".");
		// System.out.println("=============================================================\n");

		if (!iteratorSearchResults.hasNext()) {
			System.out.println(" There aren't any results for your query.");
		}

		while (iteratorSearchResults.hasNext()) {

			SearchResult singleVideo = iteratorSearchResults.next();
			ResourceId rId = singleVideo.getId();

			// Confirm that the result represents a video. Otherwise, the
			// item will not contain a video ID.
			if (rId.getKind().equals("youtube#video")) {
				Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getDefault();

				// System.out.println(" Video Id: " + rId.getVideoId());
				// System.out.println(" Title: " +
				// singleVideo.getSnippet().getTitle());
				Title[k][0] = singleVideo.getSnippet().getTitle();
				
				// System.out.println(" Thumbnail: " + thumbnail.getUrl());
				// saveImage(thumbnail.getUrl().toString(), rId.getVideoId());
				// System.out.println("\n-------------------------------------------------------------\n");
				k++;
			}
		}
	}

	static void frame() {

		String[] columnNames = { "titluri", "views" };
		JTable table = new JTable(Title, columnNames);

		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("Result	");
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		table.getColumnModel().getColumn(0).setPreferredWidth(300);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

		JScrollPane scrollPane = new JScrollPane(table);

		frame.add(scrollPane);

		frame.pack();
		frame.setVisible(true);
	}

	static void views(Iterator<SearchResult> iteratorSearchResults) throws IOException {
		int a=0;
		while (iteratorSearchResults.hasNext()){
			
			SearchResult singleVideo = iteratorSearchResults.next();
			ResourceId rId = singleVideo.getId();
			
			 
			YouTube.Videos.List list = youtube.videos().list("statistics");
			list.setId(rId.getVideoId());
			list.setKey("AIzaSyBMZcfkVBb23vO7aj0TirE18r4juWKu3w0");
			Video v = list.execute().getItems().get(0);
			Title[a][1] = v.getStatistics().getViewCount().toString();
			 a++;
		 
		}
		
	}

}
