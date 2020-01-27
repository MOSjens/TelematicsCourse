package dbconnection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Connection to a question database
 * 
 * @author IG4
 *
 */
public class QuestionDatabase {
	private URL url;
	
	
	
	public void getQuestion(int amount,Category category, Difficulty difficulty) {
		HttpURLConnection con = null;

		try {
			url = new URL("https://opentdb.com/api.php?amount=1");
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setDoOutput(true);

			Map<String, String> parameters = new HashMap<>();
			parameters.put("amount",Integer.toString(amount));
			if(category != null)
			parameters.put("category",Integer.toString(category.getCategoryId()));
			if(difficulty != null)
			parameters.put("difficulty",difficulty.toString().toLowerCase());
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
			out.flush();
			out.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			System.out.println(content.toString());
			ResultParser parser = new ResultParser();
			parser.parseResult(content.toString());
			in.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void getRandomQuestions(int amount) {
		this.getQuestion(amount, null, null);
	}
}
