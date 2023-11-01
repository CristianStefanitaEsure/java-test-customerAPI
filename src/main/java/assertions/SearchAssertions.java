package assertions;

import com.mashape.unirest.http.HttpResponse;

public class SearchAssertions extends CustomerCommonAssertions {

    public SearchAssertions(HttpResponse response) {
        super(response);
    }

}
