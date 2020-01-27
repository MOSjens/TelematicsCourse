package dbconnection;

import com.google.gson.Gson;

public class ResultParser {

	public ResultParser() {
		// TODO Auto-generated constructor stub
	}

	public JsonResult parseResult(String json) {
		Gson gson = new Gson();
	    JsonResult result = gson.fromJson(json, JsonResult.class);
	    System.out.print(result.getResponseCode());
	    System.out.print(result.getResults()[0].getQuestion());
	    System.out.print(result.getResults()[0].getCorrectAnswer());
		return result;
		
		
	}
}
