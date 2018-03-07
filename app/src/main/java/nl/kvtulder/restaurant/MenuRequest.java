package nl.kvtulder.restaurant;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MenuRequest implements Response.ErrorListener,Response.Listener<JSONObject>{

    private static final String MENU_LINK = "https://resto.mprog.nl/menu";

    Context ctx;
    Callback activity;

    public MenuRequest(Context ctx)
    {
    this.ctx = ctx;
    }

    public void getMenuItems(Callback activity) {

        RequestQueue queue = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(MENU_LINK,null, this,this);
        this.activity = activity;
        queue.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotMenuError("Fout bij het ophalen van het menu!");
        Log.e("Restaurant",error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        ArrayList<MenuItem> menuItemList = new ArrayList<>();

        try {
            JSONArray items = response.getJSONArray("items");
            for(int i = 0; i < items.length();i++) {
                JSONObject menuJsonItem = items.getJSONObject(i);
                String category = menuJsonItem.getString("category");
                String description = menuJsonItem.getString("description");
                String imageUrl = menuJsonItem.getString("image_url");
                int price = menuJsonItem.getInt("price");
                String name = menuJsonItem.getString("name");

                menuItemList.add(new MenuItem(name,description,imageUrl,price,category));
            }
        } catch (JSONException e) {
            activity.gotMenuError("Fout bij het ophalen van de categorieen!");
        }

        activity.gotMenuItems(menuItemList);
    }

    public interface Callback
    {
        void gotMenuItems(ArrayList<MenuItem> menuItems);
        void gotMenuError(String message);
    }
}
