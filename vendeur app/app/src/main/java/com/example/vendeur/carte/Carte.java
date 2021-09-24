package com.example.vendeur.carte;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.PolygonOptions;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

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

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static android.content.Context.LOCATION_SERVICE;
import static com.mapbox.core.constants.Constants.PRECISION_6;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;
import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.sin;
//this is the fragment carte
/**
 * A simple {@link Fragment} subclass.
 */
public class Carte extends Fragment implements OnMapReadyCallback,MapboxMap.OnMapClickListener
        ,PermissionsListener{
    JSONArray jsonArray= null;
    private MapView mapView;
    private MapboxMap mapboxMap;
    private PermissionsManager permissionsManager;
    LocationComponent locationComponent;
    DirectionsRoute currenteRoute;
    View _view;
    private SessionManager sessionManager;
    private RequestQueue queue;
    private MyRequest request;
    PointMap pointMapCentre;
    String JSON_STRING;
    Vector<MarckerInformation> marckerDemandeVector;
    Vector<MarckerInformation> marckerReservationVector;
    Bitmap bitmap;
    RequestOptions option;
    private int idComande;
    private String comande;
    private MarckerDepotInfo depotInfo;
    private GetJSONDDOMANDE getJSONDemande;
    private GetJSONRESERVATION getJSONReservation;
    private MapboxDirections client;
    private Point des;
    private Point orgi;
    final Handler handler = new Handler();
    Timer timer = new Timer();
    private DirectionsRoute currentRoute;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Mapbox.getInstance(getContext(), getString(R.string.access_token));
        if(_view==null)
            _view=inflater.inflate(R.layout.fragment_carte, container, false);
        mapView =(MapView) _view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        queue = VolleySingleton.getInstance(getActivity()).getRequestQueue();
        request = new MyRequest(getActivity(), queue);
        sessionManager = new SessionManager(getActivity());
        marckerDemandeVector= new Vector<MarckerInformation>();
        marckerReservationVector=new Vector<MarckerInformation>();
        option = new RequestOptions().placeholder(R.drawable.white_shape).error(R.drawable.white_shape);
        pointMapCentre=new PointMap(0,0);
        depotInfo=new MarckerDepotInfo();
        getJSON(Config.URL+"centre.php?idVendeur="+sessionManager.getVendeur().getId_vendeur());
        fonctoin();
/// la botton de description des marchers   /////////////////////////////////////

        Button a= (Button) _view.findViewById(R.id.fullsrcin);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),FullScreen.class));
                getActivity().finish();
            }
        });


        Button b= _view.findViewById(R.id.bottonClic);
        b.setOnClickListener(new View.OnClickListener(){
            //the button sheep
            @Override
            public void onClick(View v) {
                Bottom_Sheet bo_St=new Bottom_Sheet();
                bo_St.show(getFragmentManager(),"   ");

            }
        });
        return _view;
    }

    public void fonctoin(){
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        if(PermissionsManager.areLocationPermissionsGranted(getContext())){
                            locationComponent=mapboxMap.getLocationComponent();
                            if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                            {
                                return;
                            }
                            locationComponent.activateLocationComponent(getContext(),style);
                            locationComponent.setLocationComponentEnabled(true);

                            locationComponent.setCameraMode(CameraMode.TRACKING);
                        }else{
                            //permissionsManager=new PermissionsManager(this);
                            enableLocationComponent(style);
                            //permissionsManager.requestLocationPermissions(getActivity());
                        }
                    }
                });
//   l'adaptation de la camera     /////////////////////////////////////////////////////
                // Set the camera's starting position
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(pointMapCentre.getX(),pointMapCentre.getY())) // set the camera's center position 34.0385, -5.0010
                        .zoom(11)  // set the camera's zoom level
                        .tilt(11)  // set the camera's tilt
                        .build();
                // Move the camera to that position
                mapboxMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//////////////////////////////////////////////////  l'affichafe de marcker    ////////////////////////////////////////////////////////////

                getJSONDemande = new GetJSONDDOMANDE(Config.URL+"demandeCarte.php?idVendeur="+sessionManager.getVendeur().getId_vendeur());
                getJSONDemande.execute();
                getJSONReservation = new GetJSONRESERVATION(Config.URL+"reservationCarte.php?idVendeur="+sessionManager.getVendeur().getId_vendeur());
                getJSONReservation.execute();
                mapboxMap.setInfoWindowAdapter(new MapboxMap.InfoWindowAdapter() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public View getInfoWindow(@NonNull Marker marker) {

                        View v = getLayoutInflater().inflate(R.layout.produits_notification_cadre, null);

                        TextView titulo = (TextView) v.findViewById(R.id.nomProduit);
                        TextView desc = (TextView) v.findViewById(R.id.quantitie);
                        TextView nomClient = (TextView) v.findViewById(R.id.nomClient);
                        TextView uniter = (TextView) v.findViewById(R.id.uni);
                        TextView tele = (TextView) v.findViewById(R.id.telephone);
                        ImageView img = (ImageView) v.findViewById(R.id.imageClient);

                        if(marker.getTitle().equals("demande")) {
                            for (int i=0;i<marckerDemandeVector.size();i++) {
                                if(marker.getSnippet().equals(""+marckerDemandeVector.get(i).idDemande)){
                                    titulo.setText(marckerDemandeVector.get(i).nomProduit);
                                    desc.setText(marckerDemandeVector.get(i).qunatitie+"");
                                    nomClient.setText(marckerDemandeVector.get(i).nomClient);
                                    tele.setText(marckerDemandeVector.get(i).numTeleClinet);
                                    uniter.setText("("+marckerDemandeVector.get(i).categorie+")");
                                    Glide.with(getContext()).load(marckerDemandeVector.get(i).imageclient).apply(option).into(img);
                                    des = Point.fromLngLat(marckerDemandeVector.get(i).pointMap.getX(), marckerDemandeVector.get(i).pointMap.getY());
                                    //orgi = Point.fromLngLat(pointMapCentre.getX(), pointMapCentre.getY());
                                    Criteria criteria = new Criteria();
                                    LocationManager locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
                                    List<String> providers = locationManager.getProviders(true);
                                    String provider = locationManager.getBestProvider(criteria, false);
                                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

                                    orgi = Point.fromLngLat(bestLocation.getLatitude(),bestLocation.getLongitude());
                                    getRoute(mapboxMap,orgi,des);
                                    i=marckerDemandeVector.size();
                                }
                            }

                        }
                        else {
                            if(marker.getTitle().equals("resrvation")) {
                                for (int i=0;i<marckerReservationVector.size();i++) {
                                    if(marker.getSnippet().equals(""+marckerReservationVector.get(i).idDemande)){
                                        titulo.setText(marckerReservationVector.get(i).nomProduit);
                                        desc.setText(marckerReservationVector.get(i).qunatitie+"");
                                        nomClient.setText(marckerReservationVector.get(i).nomClient);
                                        tele.setText(marckerReservationVector.get(i).numTeleClinet);
                                        uniter.setText("("+marckerReservationVector.get(i).categorie+")");
                                        Glide.with(getContext()).load(marckerReservationVector.get(i).imageclient).apply(option).into(img);
                                        des = Point.fromLngLat(marckerDemandeVector.get(i).pointMap.getX(), marckerDemandeVector.get(i).pointMap.getY());
                                        //orgi = Point.fromLngLat(pointMapCentre.getX(), pointMapCentre.getY());
                                        Criteria criteria = new Criteria();
                                        LocationManager locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
                                        List<String> providers = locationManager.getProviders(true);
                                        String provider = locationManager.getBestProvider(criteria, false);
                                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                                            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

                                        orgi = Point.fromLngLat(bestLocation.getLatitude(),bestLocation.getLongitude());
                                        getRoute(mapboxMap,orgi,des);
                                        i=marckerReservationVector.size();
                                    }
                                }

                            }
                            else{
                                nomClient.setText(depotInfo.getNomDepot());
                                tele.setVisibility(View.INVISIBLE);
                                uniter.setVisibility(View.INVISIBLE);
                                img.setImageResource(R.drawable.depot);
                                titulo.setText(depotInfo.getNumTeleDepot());
                                desc.setVisibility(View.INVISIBLE);
                                des = Point.fromLngLat(depotInfo.pointMap.getX(), depotInfo.pointMap.getY());
                                //orgi = Point.fromLngLat(pointMapCentre.getX(), pointMapCentre.getY());
                                Criteria criteria = new Criteria();
                                LocationManager locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
                                List<String> providers = locationManager.getProviders(true);
                                String provider = locationManager.getBestProvider(criteria, false);
                                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

                                orgi = Point.fromLngLat(bestLocation.getLatitude(),bestLocation.getLongitude());
                                getRoute(mapboxMap,orgi,des);
                                titulo.setTextColor(Color.BLUE);

                            }
                        }


                        v.setBackground(new ColorDrawable(Color.TRANSPARENT));
                        v.setPadding(10, 10, 10, 10);

                        return v;
                    }
                });

                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            public void run() {

                                mapboxMap.clear();
                                for (int i=0;i<marckerDemandeVector.size();i++) {
                                    MarkerOptions options1 = new MarkerOptions();
                                    options1.title("demande").snippet(""+marckerDemandeVector.get(i).idDemande);
                                    //le chois de la colore de l'icone que je maitre dans le map
                                    IconFactory iconFactory1 = IconFactory.getInstance(getContext());

                                    if (sessionManager.isPassed() && sessionManager.getTypeComande().equals("demande") && sessionManager.getIdComande()==marckerDemandeVector.get(i).getIdDemande()) {
                                        Icon icon1 = iconFactory1.fromResource(R.drawable.destination);
                                        options1.icon(icon1);//edit the defaultMarker position color mapbox in android java
                                        options1.position(new LatLng(marckerDemandeVector.get(i).getPointMap().getX(), marckerDemandeVector.get(i).getPointMap().getY()));//hna wa9ila kayna dak getposition TA Njarabha
                                    }else {
                                        Icon icon1 = iconFactory1.fromResource(R.drawable.verte);
                                        options1.icon(icon1);//edit the defaultMarker position color mapbox in android java
                                        options1.position(new LatLng(marckerDemandeVector.get(i).getPointMap().getX(), marckerDemandeVector.get(i).getPointMap().getY()));
                                    }
                                    mapboxMap.addMarker(options1);
                                }

                                for (int i=0;i<marckerReservationVector.size();i++) {
                                    MarkerOptions options1 = new MarkerOptions();
                                    options1.title("resrvation").snippet(""+marckerReservationVector.get(i).idDemande);
                                    BitmapContainerTransitionFactory bitmapContainerTransitionFactory;
                                    //le chois de la colore de l'icone que je maitre dans le map
                                    IconFactory iconFactory1 = IconFactory.getInstance(getContext());

                                    if (sessionManager.isPassed() && sessionManager.getTypeComande().equals("Reservation") && sessionManager.getIdComande()==marckerDemandeVector.get(i).getIdDemande()) {

                                        Icon icon1 = iconFactory1.fromResource(R.drawable.destination);
                                        options1.icon(icon1);//edit the defaultMarker position color mapbox in android java
                                        options1.position(new LatLng(marckerReservationVector.get(i).getPointMap().getX(), marckerReservationVector.get(i).getPointMap().getY()));//hna wa9ila kayna dak getposition TA Njarabha
                                    }else {
                                        Icon icon1 = iconFactory1.fromResource(R.drawable.reserve_c);
                                        options1.icon(icon1);//edit the defaultMarker position color mapbox in android java
                                        options1.position(new LatLng(marckerReservationVector.get(i).getPointMap().getX(), marckerReservationVector.get(i).getPointMap().getY()));
                                    }
                                    mapboxMap.addMarker(options1);
                                }

                                /////////////////////////////////////////////////////// depot \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

                                MarkerOptions optionsDepot= new MarkerOptions();
                                optionsDepot.title("gfjfhjjj").snippet("fyfffthf");
                                IconFactory iconFactory = IconFactory.getInstance(getContext());
                                Icon icon1 = iconFactory.fromResource(R.drawable.depot_cc);
                                optionsDepot.icon(icon1);//edit the defaultMarker position color mapbox in android java
                                optionsDepot.position(new LatLng(depotInfo.getPointMap().getX(),depotInfo.getPointMap().getY()));
                                mapboxMap.addMarker(optionsDepot);
                                //la cercele
                                MarkerOptions optionsCentre= new MarkerOptions();
                                IconFactory iconFactory1 = IconFactory.getInstance(getContext());
                                optionsCentre.position(new LatLng(pointMapCentre.getX(),pointMapCentre.getY()));
                                //mapboxMap.addMarker(optionsCentre);

                                drawCircle(mapboxMap,optionsCentre.getPosition(),R.color.mapbox_blue, 900);
                            }
                        });
                    }
                };
                timer.schedule(task, 0, 15000);



///////////////////////////////////  l'appele a la methode pour la creation de circele  //////////////////////////////////////////

            }

        });

    }
    ///////////////////////////////////////
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
                Toast.makeText(getContext(), "la longeur de chemain est :"+currentRoute.distance()+"\nla duree est :"+ TimeUnit.SECONDS.toMinutes(currentRoute.duration().longValue())+" minutes", Toast.LENGTH_SHORT).show();//+"\nla duree est :"+TimeUnit.SECONDS.toMinutes(currentRoute.duration().longValue())+" minutes"

                Feature directionsRouteFeature = Feature.fromGeometry(LineString.fromPolyline(currentRoute.geometry(), PRECISION_6));
            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                Timber.e("Error: " + throwable.getMessage());
                Toast.makeText(getContext(), "Error: " + throwable.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    ////////////////////////////////////// la methothe de sroing the new marckers\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    private void droeing(){
        for (int i=0;i<marckerDemandeVector.size();i++) {
            MarkerOptions options1 = new MarkerOptions();
            options1.title("demande").snippet(""+marckerDemandeVector.get(i).idDemande);
            //le chois de la colore de l'icone que je maitre dans le map
            IconFactory iconFactory1 = IconFactory.getInstance(getContext());
            Icon icon1 = iconFactory1.fromResource(R.drawable.demande_c);
            options1.icon(icon1);//edit the defaultMarker position color mapbox in android java
            options1.position(new LatLng(marckerDemandeVector.get(i).getPointMap().getX(), marckerDemandeVector.get(i).getPointMap().getY()));
            mapboxMap.addMarker(options1);
        }

        for (int i=0;i<marckerReservationVector.size();i++) {
            MarkerOptions options1 = new MarkerOptions();
            options1.title("resrvation").snippet(""+marckerReservationVector.get(i).idDemande);
            BitmapContainerTransitionFactory bitmapContainerTransitionFactory;
            //le chois de la colore de l'icone que je maitre dans le map
            IconFactory iconFactory1 = IconFactory.getInstance(getContext());
            Icon icon1 = iconFactory1.fromResource(R.drawable.reserve_c);
            options1.icon(icon1);//edit the defaultMarker position color mapbox in android java
            options1.position(new LatLng(marckerReservationVector.get(i).getPointMap().getX(), marckerReservationVector.get(i).getPointMap().getY()));
            mapboxMap.addMarker(options1);
        }
    }

    ////////////////////////////////////////////   la partier de la creation de circle   /////////////////////////////////////////////////////////////
    public static void drawCircle(MapboxMap map, LatLng position, int color, double radiusMeters) {
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(color);
        polylineOptions.width(0.5f); // change the line width here
        polylineOptions.addAll(getCirclePoints(position, radiusMeters));
        map.addPolyline(polylineOptions);
        map.addPolygon(new PolygonOptions()
                .addAll(getCirclePoints(position, radiusMeters))

                .strokeColor(Color.GRAY)
                .fillColor(0x0F000FCF));//Color.parseColor("#2271cce7")
        //.alpha(84)
        //.strokeColor(Color.parseColor("#2271cce7"))
        //.setAlpha(20)//parseColor("#3bb122")
    }

    //drown the cercle in the map
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode,permissions,grantResults);

    }
    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    @Override
    public void onPermissionResult(boolean granted) {
        if(granted){
            enableLocationComponent(mapboxMap.getStyle());
        }
        else{
            Toast.makeText(getContext(),"Permation granted",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        return false;
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapboxMap=mapboxMap;
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                enableLocationComponent(style);
            }
        });

    }

    private void enableLocationComponent(@NonNull Style styleloaded) {
        if(PermissionsManager.areLocationPermissionsGranted(getContext())){
            locationComponent=mapboxMap.getLocationComponent();
            if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_DENIED)
            {
                return;
            }
            locationComponent.activateLocationComponent(getContext(),styleloaded);
            locationComponent.setLocationComponentEnabled(true);
            locationComponent.setCameraMode(CameraMode.TRACKING);
        }else{
            permissionsManager=new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(getActivity());
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();


    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        ViewPager mViewPager=(ViewPager) getActivity().findViewById(R.id.view_pager);
        mViewPager.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        if (getJSONDemande!=null)
            getJSONDemande.cancel(true);
        if (getJSONReservation!=null)
            getJSONReservation.cancel(true);
        super.onPause();
        sessionManager.setComande(0,"type");
        mapView.onPause();
    }

    @Override
    public void onStop() {
        if (getJSONDemande!=null)
            getJSONDemande.cancel(true);
        if (getJSONReservation!=null)
            getJSONReservation.cancel(true);
        super.onStop();
        mapView.onStop();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        if (getJSONDemande!=null)
            getJSONDemande.cancel(true);
        if (getJSONReservation!=null)
            getJSONReservation.cancel(true);
        super.onDestroy();
        mapView.onDestroy();
    }

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

    //get the all demande clients
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
                    Log.i("TAG", "here : "+i);
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    String nomProduit=jsonObject.getString("nomProduit").trim();
                    String nomClient=jsonObject.getString("nomClient").trim();
                    String telephone=jsonObject.getString("telephone").trim();
                    String categorier=jsonObject.getString("categorier").trim();
                    int  idDemande=jsonObject.getInt("idDemande");
                    String imageClient=jsonObject.getString("imageClient").trim();
                    double position_x=jsonObject.getDouble("centre_position_x");
                    double position_y=jsonObject.getDouble("centre_position_y");
                    int quantite=jsonObject.getInt("quantite");
                    PointMap p=new PointMap(position_x,position_y);
                    temp.add(new MarckerInformation(p,nomProduit,nomClient,telephone,quantite,idDemande,imageClient,categorier));
                }
                marckerDemandeVector=temp;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
    //get the all reservation clients
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
                    String categorier=jsonObject.getString("categorier").trim();
                    String imageClient=jsonObject.getString("imageClient").trim();
                    int  idreservation=jsonObject.getInt("idReservation");
                    double position_x=jsonObject.getDouble("centre_position_x");
                    Log.i("TAG", "verifypFirst: "+position_x);
                    double position_y=jsonObject.getDouble("centre_position_y");
                    int quantite=jsonObject.getInt("quantite");
                    PointMap p=new PointMap(position_x,position_y);
                    temp.add(new MarckerInformation(p,nomProduit,nomClient,telephone,quantite,idreservation,imageClient,categorier));
                }
                marckerReservationVector=temp;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

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

}
