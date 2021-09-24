package com.example.vendeur.carte;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.BitmapContainerTransitionFactory;
import com.example.vendeur.Config;
import com.example.vendeur.volley_requests.MyRequest;
import com.example.vendeur.R;
import com.example.vendeur.espaceVendeur.SessionManager;
import com.example.vendeur.volley_requests.VolleySingleton;
import com.example.vendeur.espaceVendeur.espaceVendeur;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.core.constants.Constants;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.mapbox.mapboxsdk.style.layers.Layer;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import static com.example.vendeur.carte.Carte.drawCircle;
import static com.mapbox.core.constants.Constants.PRECISION_6;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineCap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineJoin;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth;
import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.sin;
//this is the activite carte
public class FullScreen extends AppCompatActivity implements OnMapReadyCallback, PermissionsListener {
    //declaration variables
    private long DEFAULT_INTERVAL_IN_MILLISECONDS = 700L;
    private long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;
    private static final String DROPPED_MARKER_LAYER_ID = "DROPPED_MARKER_LAYER_ID";
    private com.mapbox.mapboxsdk.maps.MapView mapView;
    private MapboxMap mapboxMap;
    private Button selectLocationButton;
    private PermissionsManager permissionsManager;
    private ImageView hoveringMarker;
    private Layer droppedMarkerLayer;
    LocationComponent locationComponent;
    LocationEngine locationEngine;
    LocationLayerPlugin locationLayerPlugin;
    private String geojsonSourceLayerId = "geojsonSourceLayerId";
    private String symbolIconId = "symbolIconId";
    Vector<MarckerInformation> marckerDemandeVector;
    Vector<MarckerInformation> marckerReservationVector;
    Vector<MarckerDepotInfo> marckerDepotVector;
    private SessionManager sessionManager;
    private RequestQueue queue;
    private MyRequest request;
    PointMap pointMapCentre;
    Bitmap bitmap;
    RequestOptions option;
    JSONArray jsonArray = null;
    private NavigationMapRoute navigationMapRoute;
    private final static String TAG = "tkharbi9a";
    private Point orgi;
    private Point des;
    private DirectionsRoute currentRoute;
    private MapboxDirections client;
    Style style1;
    private static final String ROUTE_LAYER_ID = "route-layer-id";
    private int index = 0;
    private static final String ROUTE_SOURCE_ID = "route-source-id";
    private static final String ICON_LAYER_ID = "icon-layer-id";
    private static final String ICON_SOURCE_ID = "icon-source-id";
    private static final String RED_PIN_ICON_ID = "red-pin-icon-id";
    private GetJSONDDOMANDE getJSONDemande;
    private GetJSONRESERVATION getJSONReservation;
    final Handler handler = new Handler();
    Timer timer = new Timer();
    Activity context;
    private MarckerDepotInfo depotInfo;
    private DirectionsRoute drivingRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        queue = VolleySingleton.getInstance(this).getRequestQueue();
        request = new MyRequest(this, queue);
        sessionManager = new SessionManager(this);
        marckerDemandeVector = new Vector<MarckerInformation>();
        marckerReservationVector = new Vector<MarckerInformation>();
        marckerDepotVector = new Vector<MarckerDepotInfo>();
        option = new RequestOptions().placeholder(R.drawable.white_shape).error(R.drawable.white_shape);
        pointMapCentre = new PointMap(0, 0);
        depotInfo = new MarckerDepotInfo();
        // Mapbox access token is configured here.
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_full_screen);
        // Initialize the mapboxMap view
        mapView = findViewById(R.id.mapView_fullscreen);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


        Button b = findViewById(R.id.bottonClic);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bottom_Sheet bo_St = new Bottom_Sheet();
                bo_St.show(getSupportFragmentManager(), " ");

            }
        });
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        FullScreen.this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull final Style style) {
                //enableLocationComponent(style);
                if (PermissionsManager.areLocationPermissionsGranted(getApplication())) {
                    locationComponent = mapboxMap.getLocationComponent();
                    if (ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    locationComponent.activateLocationComponent(getApplication(), style);
                    locationComponent.setLocationComponentEnabled(true);

                    locationComponent.setCameraMode(CameraMode.TRACKING);
                } else {
                    enableLocationComponent(style);
                }
                addDistinationIconLayer(style);


                style1 = style;
                //get the position centre of cercle drow in the map
                getJSON(Config.URL +"centre.php?idVendeur=" + sessionManager.getVendeur().getId_vendeur());
                fonctoin();


            }
        });
    }

    private void addDistinationIconLayer(Style style) {
        style.addImage("destination-icon-id",
                BitmapFactory.decodeResource(this.getResources(), R.drawable.blue)
        );
        GeoJsonSource geoJsonSource = new GeoJsonSource("destination-source");
        style.addSource(geoJsonSource);
        SymbolLayer destinationSymblelire = new SymbolLayer("destination-simple-layre-id", "distination-source-id");
        destinationSymblelire.withProperties(iconImage("destination-icon-id"), iconAllowOverlap(true), iconIgnorePlacement(true));
        style.addLayer(destinationSymblelire);

    }

    public void fonctoin() {
        MarkerOptions options = new MarkerOptions();
        options.title("hada titre");
        options.position(new LatLng(pointMapCentre.getX(), pointMapCentre.getY()));
//la dabtation de la camera de la posion
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(pointMapCentre.getX(), pointMapCentre.getY())) // set the camera's center position 34.0385, -5.0010
                .zoom(11)  // set the camera's zoom level
                .tilt(11)  // set the camera's tilt
                .build();
        getJSONDemande = new GetJSONDDOMANDE(Config.URL+"demandeCarte.php?idVendeur=" + sessionManager.getVendeur().getId_vendeur());
        getJSONDemande.execute();
        getJSONReservation = new GetJSONRESERVATION(Config.URL+"reservationCarte.php?idVendeur=" + sessionManager.getVendeur().getId_vendeur());
        getJSONReservation.execute();
//la daptaions des information qui sont afficher dans le pop up de tous les marcker
        mapboxMap.setInfoWindowAdapter(new MapboxMap.InfoWindowAdapter() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public View getInfoWindow(@NonNull Marker marker) {
                View v = getLayoutInflater().inflate(R.layout.produits_notification_cadre, null);

                TextView titulo = (TextView) v.findViewById(R.id.nomProduit);
                TextView desc = (TextView) v.findViewById(R.id.quantitie);
                TextView nomClient = (TextView) v.findViewById(R.id.nomClient);
                TextView tele = (TextView) v.findViewById(R.id.telephone);
                ImageView img = (ImageView) v.findViewById(R.id.imageClient);
                TextView uniter = (TextView) v.findViewById(R.id.uni);
                if (marker.getTitle().equals("demande")) {
                    for (int i = 0; i < marckerDemandeVector.size(); i++) {
                        if (marker.getSnippet().equals("" + marckerDemandeVector.get(i).idDemande)) {
                            titulo.setText(marckerDemandeVector.get(i).nomProduit);
                            desc.setText(marckerDemandeVector.get(i).qunatitie + "");
                            nomClient.setText(marckerDemandeVector.get(i).nomClient);
                            uniter.setText("("+marckerDemandeVector.get(i).categorie+")");
                            tele.setText(marckerDemandeVector.get(i).numTeleClinet);
                            Glide.with(getApplication()).load(marckerDemandeVector.get(i).imageclient).apply(option).into(img);

                            des = Point.fromLngLat(marckerDemandeVector.get(i).pointMap.getX(), marckerDemandeVector.get(i).pointMap.getY());

                            Criteria criteria = new Criteria();
                            LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
                            List<String> providers = locationManager.getProviders(true);
                            String provider = locationManager.getBestProvider(criteria, false);
                            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.

                            }
                            Location bestLocation = locationManager.getLastKnownLocation(provider);
                            for (String providerr : providers) {
                                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for ActivityCompat#requestPermissions for more details.

                                }
                                Location l = locationManager.getLastKnownLocation(providerr);
                                if (l == null) {
                                    continue;
                                }
                                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                                    // Found best last known location: %s", l);
                                    bestLocation = l;
                                }
                            }
                            orgi=Point.fromLngLat(bestLocation.getLatitude(),bestLocation.getLongitude());
                            initSource(style1);
                            getRoute(mapboxMap,orgi,des);
                            i = marckerDemandeVector.size();
                        }
                    }

                } else {
                    if (marker.getTitle().equals("resrvation")) {
                        for (int i = 0; i < marckerReservationVector.size(); i++) {
                            if (marker.getSnippet().equals("" + marckerReservationVector.get(i).idDemande)) {
                                titulo.setText(marckerReservationVector.get(i).nomProduit);
                                desc.setText(marckerReservationVector.get(i).qunatitie + "");
                                nomClient.setText(marckerReservationVector.get(i).nomClient);
                                tele.setText(marckerReservationVector.get(i).numTeleClinet);
                                uniter.setText("("+marckerReservationVector.get(i).categorie+")");
                                Glide.with(getApplication()).load(marckerReservationVector.get(i).imageclient).apply(option).into(img);
                                des = Point.fromLngLat(marckerReservationVector.get(i).pointMap.getX(), marckerReservationVector.get(i).pointMap.getY());

                                Criteria criteria = new Criteria();
                                LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
                                List<String> providers = locationManager.getProviders(true);
                                String provider = locationManager.getBestProvider(criteria, false);
                                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for ActivityCompat#requestPermissions for more details.

                                }
                                Location bestLocation = locationManager.getLastKnownLocation(provider);
                                for (String providerr : providers) {
                                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                        // TODO: Consider calling
                                        //    ActivityCompat#requestPermissions
                                        // here to request the missing permissions, and then overriding
                                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                        //                                          int[] grantResults)
                                        // to handle the case where the user grants the permission. See the documentation
                                        // for ActivityCompat#requestPermissions for more details.

                                    }
                                    Location l = locationManager.getLastKnownLocation(providerr);
                                    if (l == null) {
                                        continue;
                                    }
                                    if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                                        // Found best last known location: %s", l);
                                        bestLocation = l;
                                    }
                                }
                                orgi=Point.fromLngLat(bestLocation.getLatitude(),bestLocation.getLongitude());
                                initSource(style1);
                                getRoute(mapboxMap,orgi,des);
                                i=marckerReservationVector.size();
                            }
                        }

                    }
                    else{
                        nomClient.setText(depotInfo.getNomDepot());
                        tele.setVisibility(View.INVISIBLE);
                        img.setImageResource(R.drawable.depot);
                        titulo.setText(depotInfo.getNumTeleDepot());
                        desc.setVisibility(View.INVISIBLE);
                        des = Point.fromLngLat(depotInfo.getPointMap().getX(),depotInfo.getPointMap().getY());
                        uniter.setVisibility(View.INVISIBLE);

                        Criteria criteria = new Criteria();
                        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
                        List<String> providers = locationManager.getProviders(true);
                        String provider = locationManager.getBestProvider(criteria, false);
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.

                        }
                        Location bestLocation = locationManager.getLastKnownLocation(provider);
                        for (String providerr : providers) {
                            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.

                            }
                            Location l = locationManager.getLastKnownLocation(providerr);
                            if (l == null) {
                                continue;
                            }
                            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                                // Found best last known location: %s", l);
                                bestLocation = l;
                            }
                        }
                        orgi=Point.fromLngLat(bestLocation.getLatitude(),bestLocation.getLongitude());
                        initSource(style1);
                        getRoute(mapboxMap,orgi,des);
                    }
                }
                titulo.setTextColor(Color.YELLOW);

                v.setBackground(new ColorDrawable(Color.TRANSPARENT));
                v.setPadding(10, 10, 10, 10);

                return v;
            }
        });
//la refriche automatique de touts les composents qui sont afficher dans la carte geographique
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        mapboxMap.clear();
                        for (int i=0;i<marckerDemandeVector.size();i++) {
                            MarkerOptions options1 = new MarkerOptions();
                            options1.title("demande").snippet(""+marckerDemandeVector.get(i).idDemande);
                            //le chois de la colore de l'icone que je maitre dans le map
                            IconFactory iconFactory1 = IconFactory.getInstance(getApplication());
                            Icon icon1 = iconFactory1.fromResource(R.drawable.verte);
                            options1.icon(icon1);//edit the defaultMarker position color mapbox in android java
                            options1.position(new LatLng(marckerDemandeVector.get(i).getPointMap().getX(), marckerDemandeVector.get(i).getPointMap().getY()));
                            mapboxMap.addMarker(options1);

                        }
                        for (int i=0;i<marckerReservationVector.size();i++) {
                            MarkerOptions options1 = new MarkerOptions();
                            options1.title("resrvation").snippet(""+marckerReservationVector.get(i).idDemande);
                            BitmapContainerTransitionFactory bitmapContainerTransitionFactory;
                            //le chois de la colore de l'icone que je maitre dans le map
                            IconFactory iconFactory1 = IconFactory.getInstance(getApplication());
                            Icon icon1 = iconFactory1.fromResource(R.drawable.rouge);
                            options1.icon(icon1);//edit the defaultMarker position color mapbox in android java
                            options1.position(new LatLng(marckerReservationVector.get(i).getPointMap().getX(), marckerReservationVector.get(i).getPointMap().getY()));
                            mapboxMap.addMarker(options1);

                        }
                        /////////////////////////////////////////////////////// depot \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

                        MarkerOptions optionsDepot= new MarkerOptions();
                        optionsDepot.title("depot").snippet("depot");
                        IconFactory iconFactory = IconFactory.getInstance(getApplication());
                        Icon icon1 = iconFactory.fromResource(R.drawable.depot_cc);
                        optionsDepot.icon(icon1);//edit the defaultMarker position color mapbox in android java
                        optionsDepot.position(new LatLng(depotInfo.getPointMap().getX(),depotInfo.getPointMap().getY()));
                        mapboxMap.addMarker(optionsDepot);
                        //la cercele
                        MarkerOptions optionsCentre= new MarkerOptions();
                        IconFactory iconFactory1 = IconFactory.getInstance(getApplication());
                        optionsCentre.position(new LatLng(pointMapCentre.getX(),pointMapCentre.getY()));
                        //mapboxMap.addMarker(optionsCentre);
                        //draw the cercle
                        drawCircle(mapboxMap,optionsCentre.getPosition(),R.color.mapbox_blue, 900);
                    }
                });
            }
        };timer.schedule(task, 0, 15000);


    }

    private void initSource(@NonNull Style loadedMapStyle) {
        index++;
        loadedMapStyle.addSource(new GeoJsonSource(ROUTE_SOURCE_ID+index));


        GeoJsonSource iconGeoJsonSource = new GeoJsonSource(ICON_SOURCE_ID+index, FeatureCollection.fromFeatures(new Feature[] {
                Feature.fromGeometry(Point.fromLngLat(orgi.longitude(), orgi.latitude())),
                Feature.fromGeometry(Point.fromLngLat(des.longitude(), des.latitude()))}));
        loadedMapStyle.addSource(iconGeoJsonSource);
    }
    //la fonction pour le dicin de la cercle (la liste des polygons)
    private static ArrayList<LatLng> getCirclePoints(LatLng position, double radius) {
        int degreesBetweenPoints = 10; // change here for shape
        int numberOfPoints = (int) Math.floor(360 / degreesBetweenPoints);
        double distRadians = radius / 6371000.0; // earth radius in meters
        double centerLatRadians = position.getLatitude() * Math.PI / 180;
        double centerLonRadians = position.getLongitude() * Math.PI / 180;
        ArrayList<LatLng> polygons = new ArrayList<>(); // array to hold all the points
        for (int index = 0; index < numberOfPoints; index++) {
            double degrees = index * degreesBetweenPoints;
            double degreeRadians = degrees * Math.PI / 180;
            double pointLatRadians = Math.asin(sin(centerLatRadians) * cos(distRadians)
                    + cos(centerLatRadians) * sin(distRadians) * cos(degreeRadians));
            double pointLonRadians = centerLonRadians + Math.atan2(sin(degreeRadians)
                            * sin(distRadians) * cos(centerLatRadians),
                    cos(distRadians) - sin(centerLatRadians) * sin(pointLatRadians));
            double pointLat = pointLatRadians * 180 / Math.PI;
            double pointLon = pointLonRadians * 180 / Math.PI;
            LatLng point = new LatLng(pointLat, pointLon);
            polygons.add(point);
        }

        // add first point at end to close circle
        polygons.add(polygons.get(0));
        return polygons;
    }

    @SuppressWarnings( {"MissingPermission"})

    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            LocationComponent locationComponent = mapboxMap.getLocationComponent();

            LocationComponentActivationOptions locationComponentActivationOptions =
                    LocationComponentActivationOptions.builder(this, loadedMapStyle)
                            .useDefaultLocationEngine(false)
                            .build();

            locationComponent.activateLocationComponent(locationComponentActivationOptions);

            locationComponent.setLocationComponentEnabled(true);

            locationComponent.setCameraMode(CameraMode.TRACKING);

            locationComponent.setRenderMode(RenderMode.COMPASS);

            initLocationEngine();
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @SuppressLint("MissingPermission")
    private void initLocationEngine() {
        locationEngine = LocationEngineProvider.getBestLocationEngine(this);

        LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
                .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            if (mapboxMap.getStyle() != null) {
                enableLocationComponent(mapboxMap.getStyle());
            }
        } else {
            Toast.makeText(this, "permission non garanti", Toast.LENGTH_LONG).show();
            finish();
        }
    }


    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, "", Toast.LENGTH_LONG).show();
    }

    private  void navegationBotunClic(){

    }

    private void getRoute(MapboxMap mapboxMap, Point origin, Point destination) {
        client = MapboxDirections.builder()
                .origin(origin)
                .destination(destination)
                .overview(DirectionsCriteria.OVERVIEW_FULL)
                .profile(DirectionsCriteria.PROFILE_DRIVING)
                .accessToken(getString(R.string.access_token))
                .build();

        client.enqueueCall(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
// You can get the generic HTTP info about the response
                Timber.d("Response code: " + response.code());
                if (response.body() == null) {
                    Timber.e("No routes found, make sure you set the right user and access token.");
                    return;
                } else if (response.body().routes().size() < 1) {
                    Timber.e("No routes found");
                    return;
                }

                currentRoute = response.body().routes().get(0);
                Toast.makeText(FullScreen.this, "la longeur de chemain est :"+currentRoute.distance()+"\nla duree est :"+TimeUnit.SECONDS.toMinutes(currentRoute.duration().longValue())+" minutes", Toast.LENGTH_SHORT).show();//+"\nla duree est :"+TimeUnit.SECONDS.toMinutes(currentRoute.duration().longValue())+" minutes"
                showRouteLine();
                Feature directionsRouteFeature = Feature.fromGeometry(LineString.fromPolyline(currentRoute.geometry(), PRECISION_6));
            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                Timber.e("Error: " + throwable.getMessage());
                Toast.makeText(FullScreen.this, "Error: " + throwable.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void drawRoute(DirectionsRoute route) {
        LineString lineString = LineString.fromPolyline(route.geometry(), Constants.PRECISION_5);
        List<Point> coordinates = lineString.coordinates();
        LatLng[] points = new LatLng[coordinates.size()];
        for (int i = 0; i < coordinates.size(); i++) {
            points[i] = new LatLng(
                    coordinates.get(i).latitude(),
                    coordinates.get(i).longitude());
        }
        mapboxMap.addPolyline(new PolylineOptions()
                .add(points)
                .color(Color.parseColor("#009688"))
                .width(5));
    }
    private void setUpSource(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addSource(new GeoJsonSource(geojsonSourceLayerId));
    }

    private void setupLayer(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addLayer(new SymbolLayer("SYMBOL_LAYER_ID", geojsonSourceLayerId).withProperties(
                iconImage(symbolIconId),
                iconOffset(new Float[] {0f, -8f}),
                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        ));
    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    @SuppressWarnings( {"MissingPermission"})
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    //la recuperation de la position centre de map et de meme la position de la plus proche depot appartir de la base de donnee
    private void getJSON(final String UrlWebService){
        PointMap point=new PointMap(0.0,0.0);
        class GetJSON extends AsyncTask<Void, Void, Void> {
            public static final String REQUEST_METHOD = "GET";
            JSONObject postData;
            @Override
            protected Void doInBackground(Void... voids) {

                String json = "";
                try {
                    URL url = new URL(UrlWebService);
                    URLConnection con = url.openConnection();
                    InputStreamReader inputStreamReader = new InputStreamReader(con.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    json = bufferedReader.readLine();
                    postData = new JSONObject(json);
                    String nomDepot=postData.getString("nomDepot").trim();
                    String teleDepot=postData.getString("telephone").trim();
                    double depot_x = postData.getDouble("depot_x");
                    double depot_y = postData.getDouble("depot_y");
                    double centre_position_x = postData.getDouble("centre_position_x");
                    double centre_position_y = postData.getDouble("centre_position_y");
                    point.setY(centre_position_y);
                    point.setX(centre_position_x);
                    PointMap p=new PointMap(depot_x,depot_y);
                    depotInfo=new MarckerDepotInfo(p,nomDepot,teleDepot);
                    pointMapCentre=new PointMap(point.getX(),point.getY());
                } catch (Exception ignored) {

                }

                return null;
            }

            @Override
            protected void onPostExecute(Void v) {

            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }



    //recupertion de tous les produits demander appartir de la base de donnee
    class GetJSONDDOMANDE extends AsyncTask< Void, Void,String>{
        public static final String REQUEST_METHOD = "GET";
        JSONObject postData;
        String UrlWebService;
        public GetJSONDDOMANDE(String UrlWebService) {
            this.UrlWebService = UrlWebService;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String json="";
            try {
                URL url = new URL(UrlWebService);
                URLConnection con = url.openConnection();
                InputStreamReader inputStreamReader=new InputStreamReader(con.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                json=bufferedReader.readLine();
                jsonArray=new JSONArray(json);
                Vector<MarckerInformation> temp=new Vector<>();
                for (int i = 0; i <jsonArray.length() ; i++) {
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    String nomProduit=jsonObject.getString("nomProduit").trim();
                    String nomClient=jsonObject.getString("nomClient").trim();
                    String telephone=jsonObject.getString("telephone").trim();
                    int  idDemande=jsonObject.getInt("idDemande");
                    String categorier=jsonObject.getString("categorier").trim();
                    String imageClient=jsonObject.getString("imageClient").trim();
                    double position_x=jsonObject.getDouble("centre_position_x");
                    double position_y=jsonObject.getDouble("centre_position_y");
                    int quantite=jsonObject.getInt("quantite");
                    PointMap p=new PointMap(position_x,position_y);
                    temp.add(new MarckerInformation(p,nomProduit,nomClient,telephone,quantite,idDemande,imageClient,categorier));
                }
                marckerDemandeVector=temp;

            }catch (Exception e){
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            new GetJSONDDOMANDE(Config.URL+"demandeCarte.php?idVendeur="+sessionManager.getVendeur().getId_vendeur()).execute();

        }
    }
    //recupertion de tous les produits reserver
    class GetJSONRESERVATION extends AsyncTask< Void, Void,String>{
        public static final String REQUEST_METHOD = "GET";
        JSONObject postData;
        String UrlWebService;
        public GetJSONRESERVATION(String UrlWebService){
            this.UrlWebService=UrlWebService;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String json="";
            try {
                URL url = new URL(UrlWebService);
                URLConnection con = url.openConnection();
                InputStreamReader inputStreamReader=new InputStreamReader(con.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                Vector<MarckerInformation> temp=new Vector<>();
                json=bufferedReader.readLine();
                jsonArray=new JSONArray(json);
                for (int i = 0; i <jsonArray.length() ; i++) {
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    String nomProduit=jsonObject.getString("nomProduit").trim();
                    String nomClient=jsonObject.getString("nomClient").trim();
                    String telephone=jsonObject.getString("telephone").trim();
                    String imageClient=jsonObject.getString("imageClient").trim();
                    String categorier=jsonObject.getString("categorier").trim();
                    int  idreservation=jsonObject.getInt("idReservation");
                    double position_x=jsonObject.getDouble("centre_position_x");
                    Log.i("TAG", "verifypFirst: "+position_x);
                    double position_y=jsonObject.getDouble("centre_position_y");
                    int quantite=jsonObject.getInt("quantite");
                    PointMap p=new PointMap(position_x,position_y);
                    temp.add(new MarckerInformation(p,nomProduit,nomClient,telephone,quantite,idreservation,imageClient,categorier));
                }
                marckerReservationVector=temp;

            }catch (Exception e){
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            new GetJSONDDOMANDE(Config.URL+"reservationCarte.php?idVendeur="+sessionManager.getVendeur().getId_vendeur()).execute();
        }
    }

    private void showRouteLine() {
        if (mapboxMap != null) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {

                    // Retrieve and update the source designated for showing the directions route
                    GeoJsonSource routeLineSource = style.getSourceAs(ROUTE_SOURCE_ID);

                    // Create a LineString with the directions route's geometry and
                    // reset the GeoJSON source for the route LineLayer source
                    if (routeLineSource != null) {

                        routeLineSource.setGeoJson(LineString.fromPolyline(drivingRoute.geometry(),
                                PRECISION_6));

                    }
                }
            });
        }
    }
    //la methode pour le dicin de chemain entre deux point
    private void getSingleRoute(Point origin,Point destination) {
        client =  MapboxDirections.builder()
                .origin(origin)
                .destination(destination)
                .overview(DirectionsCriteria.OVERVIEW_FULL)
                .accessToken(getString(R.string.access_token))
                .build();

        client.enqueueCall(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                // You can get the generic HTTP info about the response
                Timber.d("Response code: " + response.code());
                if (response.body() == null) {
                    Timber.e("No routes found, make sure you set the right user and access token.");
                    return;
                } else if (response.body().routes().size() < 1) {
                    Timber.e("No routes found");
                    return;
                }

                drivingRoute = response.body().routes().get(0);

                String.valueOf(TimeUnit.SECONDS.toMinutes(drivingRoute.duration().longValue()));

                showRouteLine();

            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                Timber.e("Error: " + throwable.getMessage());
                Toast.makeText(FullScreen.this,
                        "Error: " + throwable.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), espaceVendeur.class));
        finish();
        super.onBackPressed();
    }
}
