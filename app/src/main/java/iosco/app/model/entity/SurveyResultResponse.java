package iosco.app.model.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JTITOIN on 11/04/2016.
 */
public class SurveyResultResponse  {

  @SerializedName("results")

  SurveyItemResult[] results;

  public SurveyItemResult[] getResults() {
    return results;
  }

  public void setResults(SurveyItemResult[] results) {
    this.results = results;
  }
}
