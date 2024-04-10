package com.example.taxi_backend.services;



import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.Distance;
import com.google.maps.model.TravelMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PricingService {
    @Value("${google.maps.api.key}")
    private String apiKey;
    private GeoApiContext geoApiContext;

    public PricingService(){
        this.geoApiContext = new GeoApiContext.Builder()
                .apiKey("AIzaSyCI1dVonXJS7Vj5zrfng6YihG8IBk4z4oU").build();
    }

    public double calculatePrice(String startAddress, String endAddress) {
        try {
            DirectionsResult result = DirectionsApi.newRequest(geoApiContext)
                    .origin(startAddress)
                    .destination(endAddress)
                    .mode(TravelMode.DRIVING)
                    .await();

            Distance distance = result.routes[0].legs[0].distance;
            return distance.inMeters;
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return 0;
    }
}
