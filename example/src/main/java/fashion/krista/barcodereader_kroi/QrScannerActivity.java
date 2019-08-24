package fashion.krista.barcodereader_kroi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import fashion.krista.barcode.BarcodeReader;
import fashion.krista.barcodereader_kroi.Retrofit.NetworkService;
import fashion.krista.barcodereader_kroi.model.Item;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QrScannerActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {
    private static final String TAG = QrScannerActivity.class.getSimpleName();

    private BarcodeReader barcodeReader;

    int fromSklad;
    int sklad_id;
    int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scanner);

        // getting barcode instance
        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_fragment);
        getExtras();

        /***
         * Providing beep sound. The sound file has to be placed in
         * `assets` folder
         */
        // barcodeReader.setBeepSoundFile("shutter.mp3");

        /**
         * Pausing / resuming barcode reader. This will be useful when you want to
         * do some foreground user interaction while leaving the barcode
         * reader in background
         * */
        // barcodeReader.pauseScanning();
        // barcodeReader.resumeScanning();
    }

    private void getExtras() {
        fromSklad = getIntent().getIntExtra("from_sklad_id",0);
        sklad_id = getIntent().getIntExtra("sklad_id",0);
        mode = getIntent().getIntExtra("mode",0);
    }

    @Override
    public void onScanned(final Barcode barcode) {
        Log.e(TAG, "onScanned: " + barcode.displayValue);
        barcodeReader.playBeep();

        sendPost(barcode.displayValue);

    }

    private void sendPost(final String displayValue) {
        final Intent intent = new Intent(QrScannerActivity.this, CountActivity.class);

        NetworkService.getInstance()
                .getApi()
                .getModelWithId(displayValue,fromSklad)
                .enqueue(new Callback<List<Item>>() {
                    @Override
                    public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                        Log.e(TAG, "onResponse: " + response.raw());
                        if (response.isSuccessful()) {
                            List<Item> item = response.body();
                            if (item.size() > 0) {
                                if ((item != null ? item.get(0).getQty() : 0) > 0) {
                                    intent.putExtra("qty", item.get(0).getQty());
                                    intent.putExtra("sklad_id", sklad_id);
                                    intent.putExtra("mode", 0);
                                    intent.putExtra("from_sklad_id", fromSklad);
                                    intent.putExtra("model_id", item.get(0).getModelSeriesId());
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(),getResources().getString( R.string.wrong_id), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(),getResources().getString( R.string.wrong_id), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Item>> call, Throwable t) {

                    }
                });
    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {
        Log.e(TAG, "onScannedMultiple: " + barcodes.size());

        String codes = "";
        for (Barcode barcode : barcodes) {
            codes += barcode.displayValue + ", ";
        }

        final String finalCodes = codes;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Barcodes: " + finalCodes, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {

    }

    @Override
    public void onCameraPermissionDenied() {
        Toast.makeText(getApplicationContext(), "Camera permission denied!", Toast.LENGTH_LONG).show();
        finish();
    }
}