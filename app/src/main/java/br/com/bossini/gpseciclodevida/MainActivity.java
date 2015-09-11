package br.com.bossini.gpseciclodevida;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {

    //exibe as coordenadas (latitude e longitude atuais)
    private TextView latitudeTextView;
    private TextView longitudeTextView;
    //lida com o hardware de GPS. É um observável
    private LocationManager locationManager;
    //Observador que será notificado de atualizações envolvendo o locationManager
    private LocationListener locationListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //infla a view
        setContentView(R.layout.activity_main);
        //pede ao Android uma referência ao serviço de localização
        //isso ainda não inicializa o hardware de GPS
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //inicializa as views que exibirão as coordenadas
        latitudeTextView = (TextView) findViewById(R.id.latitudeTextView);
        longitudeTextView = (TextView) findViewById(R.id.longitudeTextView);
        //inicializa o observador usando essa classe interna anônima
        locationListener = new LocationListener() {

            //esse método é chamado quando uma nova localização é identificada
            @Override
            public void onLocationChanged(Location location) {
                //o objeto referenciado por location contém informações de interesse
                //aqui obtemos latitude e longitude e colocamos nos campos de texto
                latitudeTextView.setText(Double.toString(location.getLatitude()));
                longitudeTextView.setText(Double.toString(location.getLongitude()));
            }

            //como LocationListener é uma interface, somos obrigados a implementar todos os seus métodos
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        //liga o hardware de GPS, registrando o observador no observável
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //desliga o hardware de GPS temporariamente
        locationManager.removeUpdates(locationListener);
    }
}
