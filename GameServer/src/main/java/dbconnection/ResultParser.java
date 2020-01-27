package dbconnection;

import com.google.gson.Gson;

public class ResultParser {

	public ResultParser() {
		// TODO Auto-generated constructor stub
	}

	public JsonResult parseResult(String json) {
		Gson gson = new Gson();
	    JsonResult result = gson.fromJson(json, JsonResult.class);
		return result;
		
		
	}
}
