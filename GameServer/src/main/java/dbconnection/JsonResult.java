package dbconnection;

import com.google.gson.annotations.SerializedName;

/** class for the json Result from the question database or the textfile
 * @author IG4
 *
 */
public class  JsonResult{
	
	@SerializedName("response_code") private int responseCode;
	@SerializedName("results") private Result[] results;

	public JsonResult(int responseCode, Result[] results) {
		this.setResponseCode(responseCode);
		this.setResults(results);
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public Result[] getResults() {
		return results;
	}

	public void setResults(Result[] results) {
		this.results = results;
	}

}
