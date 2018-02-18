package com.udacity.sandwichclub.utils;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String LOG_TAG = JsonUtils.class.getSimpleName();
    private static final String SANDWICH_NAME = "name";
    private static final String SANDWICH_MAIN_NAME = "mainName";
    private static final String SANDWICH_ALSO = "alsoKnownAs";
    private static final String SANDWICH_PLACE_ORIGIN = "placeOfOrigin";
    private static final String SANDWICH_DESCRIPTION = "description";
    private static final String SANDWICH_IMAGE = "image";
    private static final String SANDWICH_INGREDIENTS = "ingredients";


    public static Sandwich parseSandwichJson(String json) {

        JSONObject jsonObject;
        String mainName = null;
        String placeOfOrigin = null;
        String description = null;
        String image = null;
        List<String> ingredients = new ArrayList<>();
        List<String> alsoKnownAs = new ArrayList<>();

        // If the JSON string is empty or null, then return null.
        if (TextUtils.isEmpty(json)) {
            return null;

        }
        try {
            //Create JSONObject of whole json string
            jsonObject = new JSONObject(json);

            //Get name JsonObject
            JSONObject jsonObjectName = jsonObject.getJSONObject(SANDWICH_NAME);
            //Get mainName and aka values
            mainName = jsonObjectName.optString(SANDWICH_MAIN_NAME);

            //Get aka array
            alsoKnownAs = jsonArrayList(jsonObjectName.getJSONArray(SANDWICH_ALSO));

            //Get PlaceOfOrigin
            placeOfOrigin = jsonObject.optString(SANDWICH_PLACE_ORIGIN);

            //Get Description
            description = jsonObject.optString(SANDWICH_DESCRIPTION);
            //GET image path
            image = jsonObject.optString(SANDWICH_IMAGE);

            //Get ingredient array
            ingredients = jsonArrayList(jsonObject.getJSONArray(SANDWICH_INGREDIENTS));

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problems with parse sandwich", e);
        }

        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }

    //Iterate through the array of aka and ingredients and add it to list
    private static List<String> jsonArrayList(JSONArray jsonArray) {
        List<String> list = new ArrayList<>(0);
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    list.add(jsonArray.getString(i));
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "Problems with array list", e);
                }
            }
        }
        return list;
    }
}
