package s.hfad.com.myapplicationpaveltest.model_assets;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;


public class LoadingLocation {

    private Context mContext;
    private GoogleApiClient mClient;
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
        client = LocationServices.getFusedLocationProviderClient(mContext);
        if (ActivityCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(mContext, " Нет доступа к API Google", Toast.LENGTH_LONG).show();
            return;
        }

        client.getLastLocation().addOnSuccessListener(location -> {
            location.getLatitude();
            location.getLongitude();
            mLocation = location;
        });
    }
}


