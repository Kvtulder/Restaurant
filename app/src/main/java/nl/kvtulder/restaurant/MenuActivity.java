package nl.kvtulder.restaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity implements MenuRequest.Callback{

    ListView menuList;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // retrieve the clicked category
        Intent intent = getIntent();
        category = intent.getStringExtra("category");
        menuList = findViewById(R.id.menuList);
        menuList.setOnItemClickListener(new ListViewItemClickListener());

        // retrieve all the menu items
        MenuRequest request = new MenuRequest(this);
        request.getMenuItems(this);

    }

    // click listener for the menu listview
    public class ListViewItemClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(MenuActivity.this,MenuItemActivity.class);
            MenuItem menuItem = (MenuItem) adapterView.getItemAtPosition(i);
            intent.putExtra("menuItem",menuItem);
            startActivity(intent);

        }
    }

    // callback for the menu request
    @Override
    public void gotMenuItems(ArrayList<MenuItem> menuItems) {
        ArrayList<MenuItem> filteredMenuItems = new ArrayList<>();
        for(MenuItem menuItem : menuItems)
            if(menuItem.getCategory().equals(category))
                filteredMenuItems.add(menuItem);

        menuList.setAdapter(new MenuAdapter(this,R.layout.menu_item,filteredMenuItems));

    }

    // display a toast with the error message
    @Override
    public void gotMenuError(String message) {

        Toast toast = Toast.makeText(this,message,Toast.LENGTH_LONG);
        toast.show();

    }
}
