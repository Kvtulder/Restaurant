package nl.kvtulder.restaurant;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class CategoriesRequest{


    // constants
    public static final String CATEGORY_LINK = "https://resto.mprog.nl/categories";

    // variables
    Context ctx;

    public CategoriesRequest(Context ctx) {
        this.ctx = ctx;
    }

    // create interface to force to implement the callback methods
    public interface Callback {
        void gotCategories(ArrayList<String> categories);
        void gotCategoriesError(String message);
    }

    // download the categories
    public void getCategories(Callback activity) {

        // start new asynchronous volley task
        RequestQueue queue = Volley.newRequestQueue(ctx);
        RequestListener requestListener = new RequestListener(activity);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(CATEGORY_LINK,null,
                requestListener,requestListener);
        queue.add(jsonObjectRequest);
    }


    // create a inner class as callback for the request
    public class RequestListener implements Response.Listener<JSONObject>, Response.ErrorListener
    {
        Callback activity;

        public RequestListener(Callback activity){
            this.activity = activity;
        }

        // error handler
        @Override
        public void onErrorResponse(VolleyError error) {
            activity.gotCategoriesError("Fout bij het ophalen van de categorieen!");
            Log.e("Restaurant","Error: " + error.getMessage());
        }

        // response handler
        @Override
        public void onResponse(JSONObject response) {
            ArrayList<String> categoriesList = new ArrayList<>();
            try {

                JSONArray categories = response.getJSONArray("categories");

                // create a list with the categories
                for(int i = 0;i < categories.length();i++)
                    categoriesList.add(categories.get(i).toString());

            } catch (JSONException e) {

                // unexpected JSON, call error callback
                activity.gotCategoriesError("Fout bij het ophalen van de categorieen!");
            }

            // json parse successful!
            activity.gotCategories(categoriesList);
        }
    }
}
