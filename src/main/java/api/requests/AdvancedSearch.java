package api.requests;

import api.model.advancedsearchnodes.Query;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import utils.FileLoader;

public class AdvancedSearch {

  private String firstName;
  private String lastName;
  private String enabledPhoneticSearch;
  private String text;
  private int offset;
  private int limit;

  public AdvancedSearch(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.enabledPhoneticSearch = "false";
    this.text = this.firstName + " " + this.lastName;
    this.offset = 0;
    this.limit = 10;
  }

  public AdvancedSearch(String firstName, String lastName, String enablePhoneticSearch,
      int offset, int limit) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.enabledPhoneticSearch = enablePhoneticSearch;
    this.text = this.firstName + " " + this.lastName;
    this.offset = offset;
    this.limit = limit;
  }

  public String returnAdvancedSearchBody() {
    Gson gson = new Gson();
    return gson.toJson(setupQuery());
  }

  private Query setupQuery() {
    Query query = FileLoader.asClass("json/advancedsearch.json", Query.class)
        .setFirstNameIgnoreCase(setupNamesList(this.firstName))
        .setLastNameIgnoreCase(setupNamesList(this.lastName));
    return query;
  }

  private List<String> setupNamesList(String firstName) {
    List<String> names = new ArrayList<>();
    names.add(firstName);
    return names;
  }

}