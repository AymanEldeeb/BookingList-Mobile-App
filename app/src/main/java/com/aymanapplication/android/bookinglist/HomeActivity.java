package com.aymanapplication.android.bookinglist;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aymanapplication.android.bookinglist.adapter.HomeAdapter;
import com.aymanapplication.android.bookinglist.model.CategoryData;
import com.aymanapplication.android.bookinglist.model.RowData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private final String url = "https://www.googleapis.com/books/v1/volumes?q=android";
    private ArrayList<String> listCategoryName;
    private ArrayList<RowData> listRowData;
    private ArrayList<CategoryData> groupedCategoryDataList;
    private HomeAdapter homeAdapter;

    //RecyclerView & Adapter
    private RecyclerView rvListCategoryRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        listCategoryName = new ArrayList<>();
        listRowData = new ArrayList<>();
        groupedCategoryDataList = new ArrayList<>();
        rvListCategoryRow = (RecyclerView) findViewById(R.id.rvListCategoryRow);
        rvListCategoryRow.setLayoutManager(new LinearLayoutManager(this));
        rvListCategoryRow.setItemAnimator(new DefaultItemAnimator());

        new lisBookHomeTask().execute(url);
    }

    class lisBookHomeTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {}

        @Override
        protected String doInBackground(String... params) {

            try{
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(7000);

                String inputString = ConvertStreamToString(urlConnection.getInputStream());
                publishProgress(inputString);
            } catch (Exception ex){
                ex.getMessage();
            }

            return " ";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            try {
                JSONObject json = new JSONObject(values[0]);
                JSONArray items = json.getJSONArray("items");

                for (int i = 0; i < items.length(); i++){
                    JSONObject currentItem = items.getJSONObject(i);
                    RowData rowData = new RowData();

                    //Title AND selfLink AND Description
                    JSONObject volumeInfo = currentItem.getJSONObject("volumeInfo");
                    rowData.setSelfLink(currentItem.getString("selfLink"));
                    rowData.setTitle(volumeInfo.getString("title"));
                    if(volumeInfo.has("description")) {
                        rowData.setDescription(volumeInfo.getString("description"));
                    }else{
                        rowData.setDescription("There is no description.");
                    }

                    //CategoryName
                    JSONArray categories = volumeInfo.getJSONArray("categories");
                    String currentCategoryName = categories.get(0).toString();
                    rowData.setCategoryName(currentCategoryName);

                    //Fill Array with unique category name
                    if(!(listCategoryName.contains(currentCategoryName))){
                        listCategoryName.add(currentCategoryName);
                    }

                    //Authors
                    if (volumeInfo.has("authors")) {
                        JSONArray authors = volumeInfo.getJSONArray("authors");
                        for (int a = 0; a < authors.length(); a++) {
                            if (rowData.getAuthors().equals("")) {
                                rowData.setAuthors(authors.get(a).toString());
                            } else {
                                rowData.setAuthors(rowData.getAuthors() + " & " + authors.get(a).toString());
                            }
                        }
                    }else{
                        rowData.setAuthors("Not defined");
                    }

                    //Price
                    JSONObject saleInfo = currentItem.getJSONObject("saleInfo");
                    if (saleInfo.getString("saleability").equals("NOT_FOR_SALE")){
                        rowData.setPrice("Free");
                    }else{
                        JSONObject listPrice = saleInfo.getJSONObject("listPrice");
                        rowData.setPrice(listPrice.getString("currencyCode") + listPrice.getString("amount"));
                    }

                    //Rate
                    if(volumeInfo.has("averageRating")){
                        rowData.setRate(volumeInfo.getString("averageRating"));
                        rowData.setRatingsCount(volumeInfo.getString("ratingsCount"));
                    }else{
                        rowData.setRate("");
                        rowData.setRatingsCount("0");
                    }

                    //ImageUrl
                    JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                    rowData.setImgUrl(imageLinks.getString("smallThumbnail"));

                    //Publisher & publishDate
                    rowData.setPublisher(volumeInfo.getString("publisher"));
                    rowData.setPublishDate(volumeInfo.getString("publishedDate"));

                    //Add rowData to List
                    listRowData.add(rowData);
                }

                for (int main = 0; main < listCategoryName.size(); main++){
                    ArrayList<RowData> groupRowDataList = new ArrayList<>();
                    for (int sub = 0; sub < listRowData.size(); sub++){
                        if (listCategoryName.get(main).equals(listRowData.get(sub).getCategoryName())){
                            groupRowDataList.add(listRowData.get(sub));
                        }
                    }
                    CategoryData categoryData = new CategoryData();
                    categoryData.setCategoryName(listCategoryName.get(main));
                    categoryData.setListRowData(groupRowDataList);
                    groupedCategoryDataList.add(categoryData);
                }

                homeAdapter = new HomeAdapter(HomeActivity.this, groupedCategoryDataList);
                rvListCategoryRow.setAdapter(homeAdapter);

            } catch(Exception ex){
                ex.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {}
    }

    protected String ConvertStreamToString(InputStream inputStream) {
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        String allString = "";
        try {
            do{
                line = bufferReader.readLine();
                if (line != null){
                    allString += line;
                }
            }while (line != null);
            inputStream.close();
        } catch (Exception ex){}

        return allString;
    }
}
