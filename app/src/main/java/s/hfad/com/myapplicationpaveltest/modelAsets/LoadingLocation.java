package s.hfad.com.myapplicationpaveltest.modelAsets;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LoadingLocation {

    private Context mContext;
    private GoogleApiClient mClient;
    private LocationCallback locationCallback;
    private FusedLocationProviderClient client;
    private Location mLocation;


    public LoadingLocation(Context context) {
        mContext = context;
        apiClient();
        loadingRequest();
    }

    public Location getLocation() {
        return mLocation;
    }

    private void apiClient(){
        mClient= new GoogleApiClient.Builder(mContext)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {

                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .addOnConnectionFailedListener(connectionResult -> {

                })
                .build();
    }


    private void loadingRequest() {
        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setNumUpdates(1);
        request.setInterval(0);
        setLocationCallback();

        client = LocationServices.getFusedLocationProviderClient(mContext);

        if (ActivityCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        client.getLastLocation().addOnSuccessListener((Activity) mContext, location -> {
            Log.e("Location", String.valueOf(location));
            location.getLatitude();
            location.getLongitude();
            setLocation(location);
        });
        //client.requestLocationUpdates(request,locationCallback, Looper.myLooper());

    }

    private void setLocation(Location location){
        mLocation = location;
    }

    private void setLocationCallback(){
        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Log.e("Location", String.valueOf(locationResult.getLocations()));

            }
        };
    }

}
















