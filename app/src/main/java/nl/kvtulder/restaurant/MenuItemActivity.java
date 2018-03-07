package nl.kvtulder.restaurant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuItemActivity extends AppCompatActivity{

    MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item);

        // retrieve the clicked menu item
        Intent intent = getIntent();
        menuItem = (MenuItem) intent.getSerializableExtra("menuItem");

        // fill in the details
        TextView menuName = findViewById(R.id.menuName);
        menuName.setText(menuItem.getName());

        TextView menuPrice = findViewById(R.id.menuPrice);
        menuPrice.setText("€" + menuItem.getPrice());

        TextView menuDescription = findViewById(R.id.menuDescription);
        menuDescription.setText(menuItem.getDescription());

        // download image
        ImageView imageView = findViewById(R.id.menuImage);
        MenuImageRequest request = new MenuImageRequest(this);
        request.getImage(menuItem.getImageUrl(),new ImageCallback(imageView));
    }

    // callback for the image download
    public class ImageCallback implements MenuImageRequest.Callback{

        ImageView imageView;

        public ImageCallback(ImageView imageView){
            this.imageView = imageView;
        }
        @Override
        public void gotImage(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }

        @Override
        public void gotImageError(String message) {
            imageView.setImageResource(R.drawable.unknown);
        }
    }
}
