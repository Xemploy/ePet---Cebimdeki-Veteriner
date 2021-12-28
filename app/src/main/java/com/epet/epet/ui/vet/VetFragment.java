package com.epet.epet.ui.vet;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.epet.epet.MainActivity;
import com.epet.epet.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import me.ibrahimsn.lib.SmoothBottomBar;

public class VetFragment extends Fragment  {

    private final static int REQUEST_lOCATION=90;
    private GoogleMap mMap;
    private VetViewModel vetViewModel;
    private int _page = 2;
    private int gps_check;
    public static VetFragment newInstance() {
        return new VetFragment();
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        gps_check = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

        if (gps_check != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},100);
        }else {

        }


        SmoothBottomBar bottomBar = getActivity().findViewById(R.id.bottomBar);
        bottomBar.setActiveItem(_page);
        MainActivity.lastPage = _page;

        vetViewModel =
                ViewModelProviders.of(this).get(VetViewModel.class);
        View root = inflater.inflate(R.layout.fragment_vets, container, false);
        vetViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {


            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.vets_map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                mMap = googleMap;
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                LatLng konum = new LatLng(38.391373, 27.173044);
                LatLng konum2 = new LatLng(38.390385, 27.156456);
                LatLng konum3 = new LatLng(38.388724, 27.160421);
                LatLng konum4 = new LatLng(38.391657, 27.151481);
                mMap.addMarker(new MarkerOptions().position(konum).title("İzmir Veteriner Polikliniği").icon(BitmapDescriptorFactory.fromResource(R.drawable.memberlocate)));
                mMap.addMarker(new MarkerOptions().position(konum2).title("My-Vet Veteriner Kliniği").icon(BitmapDescriptorFactory.fromResource(R.drawable.memberlocate)));
                mMap.addMarker(new MarkerOptions().position(konum3).title("Venüs Veteriner Polikliniği").icon(BitmapDescriptorFactory.fromResource(R.drawable.nonlocate)));
                mMap.addMarker(new MarkerOptions().position(konum4).title("Şirinyer Veteriner Kliniği").icon(BitmapDescriptorFactory.fromResource(R.drawable.nonlocate)));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(konum));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(konum, 15),1,null);
            }
        });



        return root;
    }

}