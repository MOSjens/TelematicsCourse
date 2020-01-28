package dbconnection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.apache.commons.text.StringEscapeUtils;

/**
 * Connection to a question database
 * 
 * @author IG4
 *
 */
public class QuestionDatabase {
	private URL url;

	public ArrayList<Question> getQuestion(int amount, Category category, Difficulty difficulty) {
		HttpURLConnection con = null;
		ArrayList<Question> listOfQuestions = new ArrayList<Question>();
		try {
			url = new URL("https://opentdb.com/api.php?amount=1");
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setDoOutput(true);

			Map<String, String> parameters = new HashMap<>();
			parameters.put("amount", Integer.toString(amount));
			if (category != null)
				parameters.put("category", Integer.toString(category.getCategoryId()));
			if (difficulty != null)
				parameters.put("difficulty", difficulty.toString().toLowerCase());
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
			in.close();

			System.out.println(content.toString());
			ResultParser parser = new ResultParser();
			JsonResult jsonResult = parser.parseResult(content.toString());
			listOfQuestions = this.jsonResultToQuestions(jsonResult);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listOfQuestions;
	}

	public ArrayList<Question> getRandomQuestions(int amount) {
		return this.getQuestion(amount, null, null);
	}

	public ArrayList<Question> jsonResultToQuestions(JsonResult jsonResult) {
		Random random = new Random();
		ArrayList<Question> listOfQuestions = new ArrayList<Question>();
		ArrayList<String> answerOptions = new ArrayList<String>();
		int amountOptions = 0;
		int randomIndex = 0;

		Result[] results = jsonResult.getResults();
		Question question = new Question();

		for (Result entry : results) {
			question = new Question();
			answerOptions = new ArrayList<String>();
			question.setQuestionText(StringEscapeUtils.unescapeHtml4(entry.getQuestion()));
			question.setCategory(Category.fromString(entry.getCategory()));
			question.setDifficulty(Difficulty.valueOf(entry.getDifficulty().toUpperCase()));
			if (entry.getType().equalsIgnoreCase("boolean")) {
				answerOptions.add(0, "True");
				answerOptions.add(1, "False");
				for (int i =0; i <2 ; i++) {
					if(answerOptions.get(i).equalsIgnoreCase(entry.getCorrectAnswer())) {
						question.setCorrectAnswerIndex(i);
					}
					
				}
				question.setAnswerOptions(answerOptions);
			} else {

				amountOptions = entry.getIncorrectAnswers().length + 1;
				randomIndex = random.nextInt(amountOptions);
				question.setCorrectAnswerIndex(randomIndex);
				int j = 0;
				for (int i = 0; i < amountOptions; i++) {
					if (i == randomIndex) {
						answerOptions.add(i, StringEscapeUtils.unescapeHtml4(entry.getCorrectAnswer()));
					} else {
						answerOptions.add(i, StringEscapeUtils.unescapeHtml4(entry.getIncorrectAnswers()[j]));
						j++;
					}
				}				
				question.setAnswerOptions(answerOptions);
			}
			
			listOfQuestions.add(question);
		}

		return listOfQuestions;

	}
}
