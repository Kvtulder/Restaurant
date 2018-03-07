package nl.kvtulder.restaurant;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

public class MenuImageRequest{

    private Context ctx;
    private Callback activity;

    public MenuImageRequest(Context ctx)
    {
        this.ctx = ctx;
    }

    // create interface to force to implement the callback methods
    public interface Callback{
        void gotImage(Bitmap bitmap);
        void gotImageError(String message);
    }

    // get the image
    public void getImage(String url,Callback activity)
    {
        RequestQueue queue = Volley.newRequestQueue(ctx);
        ImageHandler imageHandler = new ImageHandler(activity);
        ImageRequest imageRequest = new ImageRequest(url,imageHandler,0,0,
                ImageView.ScaleType.CENTER_INSIDE,Bitmap.Config.RGB_565,imageHandler);
        queue.add(imageRequest);
        this.activity = activity;
    }

    // response listeners
    public class ImageHandler implements Response.Listener<Bitmap>,Response.ErrorListener{
        Callback activity;

        public ImageHandler(Callback activity){
            this.activity = activity;
        }
        @Override
        public void onErrorResponse(VolleyError error) {
            activity.gotImageError(error.getMessage());
        }

        @Override
        public void onResponse(Bitmap response) {
            activity.gotImage(response);
        }

    }
}
