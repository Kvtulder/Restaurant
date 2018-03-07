package nl.kvtulder.restaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity implements CategoriesRequest.Callback{

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        // download the categories
        CategoriesRequest request = new CategoriesRequest(this);
        request.getCategories(this);

        listView = findViewById(R.id.categoryList);
        listView.setOnItemClickListener(new ListViewItemClickListener());


    }

    // callback for the categories request
    @Override
    public void gotCategories(ArrayList<String> categories) {
        listView.setAdapter(new ArrayAdapter<String>(this,R.layout.category_item,R.id.item,categories));

    }

    // display a toast with the error message
    @Override
    public void gotCategoriesError(String message) {
        Toast toast = Toast.makeText(this,message,Toast.LENGTH_LONG);
        toast.show();
    }

    // click listener for the category list view
    public class ListViewItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            // start the menu activity
            String category = (String) adapterView.getItemAtPosition(i);
            Intent intent = new Intent(CategoriesActivity.this,MenuActivity.class);
            intent.putExtra("category",category);
            startActivity(intent);

        }
    }

}
