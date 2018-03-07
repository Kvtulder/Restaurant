package nl.kvtulder.restaurant;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class MenuAdapter extends ArrayAdapter{

    List<MenuItem> objects;
    int resource;
    Context context;
    ImageView imageView;

    public MenuAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.objects = objects;
        this.resource = resource;
        this.context = context;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // create view if it doesn't exist
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource,null);
        }

        // get the current menu item
        MenuItem item = objects.get(position);

        // fill in the details
        TextView name = convertView.findViewById(R.id.menuName);
        name.setText(item.getName());
        TextView price = convertView.findViewById(R.id.menuPrice);
        price.setText("€" + item.getPrice());

        // retrieve the image
        imageView =  convertView.findViewById(R.id.menuImage);
        MenuImageRequest imageRequest = new MenuImageRequest(context);
        imageRequest.getImage(item.getImageUrl(),new ImageCallback(imageView));

        return convertView;
    }

    // create callback to handle the image download
    public class ImageCallback implements MenuImageRequest.Callback{

        ImageView image;

        public ImageCallback(ImageView image){
            this.image = image;
        }

        // set the image
        @Override
        public void gotImage(Bitmap bitmap) {
            image.setImageBitmap(bitmap);
        }

        // error while downloading image; display question mark
        @Override
        public void gotImageError(String message) {
            image.setImageResource(R.drawable.unknown);
        }
    }


}
