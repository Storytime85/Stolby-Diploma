package diploma.storytime.stolbysassistant.utils;

import android.content.Context;

import androidx.annotation.NonNull;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Polyline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import diploma.storytime.stolbysassistant.utils.maputils.AlpineHat;
import diploma.storytime.stolbysassistant.utils.maputils.Pillar;

public class ReadJSON {
    static public ArrayList<GeoPoint> readCoordinates(@NonNull Context context, int resId)
            throws IOException, ParseException {
        InputStream is = context.getResources().openRawResource(resId);
        JSONParser parser = new JSONParser();

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        JSONArray array = (JSONArray) parser.parse(br);
        ArrayList<GeoPoint> result = new ArrayList<>();
        for (Object obj : array) {
            result.add(parseCoordinatesObject((JSONObject) obj));
        }
        return result;
    }

    private static GeoPoint parseCoordinatesObject(@NonNull JSONObject object) {
        JSONArray solution = (JSONArray) object.get("coordinates");
        double[] array = new double[2];
        if (solution == null) {
            throw new AssertionError();
        }
        Iterator iterator = solution.iterator();
        for (int i = 0; i < 2; i++) {
            array[i] = (Double) iterator.next();
        }
        return new GeoPoint(array[0], array[1]);
    }

    static public ArrayList<Pillar> readPillars(@NonNull Context context, int resId)
            throws IOException, ParseException {
        InputStream is = context.getResources().openRawResource(resId);
        JSONParser parser = new JSONParser();

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        JSONArray array = (JSONArray) parser.parse(br);
        ArrayList<Pillar> result = new ArrayList<>();
        for (Object obj : array) {
            result.add(parsePillarObject((JSONObject) obj));
        }
        return result;
    }

    private static Pillar parsePillarObject(JSONObject obj) {
        double[] coordinates = new double[2];
        String[] names = new String[4];
        String[] descriptions = new String[4];
        JSONArray jsonNames = (JSONArray) obj.get("name");
        JSONArray jsonDescriptions = (JSONArray) obj.get("description");
        JSONArray jsonCoordinates = (JSONArray) obj.get("coordinates");
        if ((jsonCoordinates == null) || (jsonDescriptions == null) || (jsonNames == null)) {
            throw new AssertionError();
        }
        Iterator itNames = jsonNames.iterator();
        Iterator itDescriptions = jsonDescriptions.iterator();
        Iterator itCoordinates = jsonCoordinates.iterator();
        for (int i = 0; i < 2; i++) {
            coordinates[i] = (Double) itCoordinates.next();
        }
        for (int i = 0; i < 4; i++) {
            names[i] = (String) itNames.next();
            descriptions[i] = (String) itDescriptions.next();
        }
        return new Pillar(coordinates, names, descriptions);
    }

    static public ArrayList<Polyline> readRailroads(@NonNull Context context, int resId)
            throws IOException, ParseException {

        InputStream is = context.getResources().openRawResource(resId);
        JSONParser parser = new JSONParser();

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        JSONArray array = (JSONArray) parser.parse(br);
        ArrayList<Polyline> result = new ArrayList<>();
        for (Object obj : array) {
            result.add(parseRailroad((JSONObject) obj));
        }
        return result;
    }

    private static Polyline parseRailroad(JSONObject obj) {
        double[] start = new double[2];
        double[] end = new double[2];
        JSONArray jsonStart = (JSONArray) obj.get("start");
        JSONArray jsonEnd = (JSONArray) obj.get("end");

        if ((jsonStart == null) || (jsonEnd == null)) {
            throw new AssertionError();
        }
        Iterator itStart = jsonStart.iterator();
        Iterator itEnd = jsonEnd.iterator();
        for (int i = 0; i < 2; i++) {
            start[i] = (Double) itStart.next();
            end[i] = (Double) itEnd.next();
        }
        Polyline result = new Polyline();
        result.addPoint(new GeoPoint(start[0], start[1]));
        result.addPoint(new GeoPoint(end[0], end[1]));
        return result;
    }

    static public ArrayList<AlpineHat> readMisc(@NonNull Context context, int resId)
            throws IOException, ParseException {

        InputStream is = context.getResources().openRawResource(resId);
        JSONParser parser = new JSONParser();

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        JSONArray array = (JSONArray) parser.parse(br);
        ArrayList<AlpineHat> result = new ArrayList<>();
        for (Object obj : array) {
            result.add(parseMisc((JSONObject) obj));
        }
        return result;
    }

    private static AlpineHat parseMisc(JSONObject obj) {
        double[] coordinates = new double[2];
        String[] names = new String[4];
        JSONArray jsonCoords = (JSONArray) obj.get("coordinates");
        JSONArray jsonNames = (JSONArray) obj.get("name");

        if ((jsonCoords == null) || (jsonNames == null)) {
            throw new AssertionError();
        }
        Iterator itCoords = jsonCoords.iterator();
        Iterator itNames = jsonNames.iterator();
        for (int i = 0; i < 2; i++) {
            coordinates[i] = (Double) itCoords.next();
            names[i] = (String) itNames.next();
        }
        return new AlpineHat(coordinates, names);
    }
}
