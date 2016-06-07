package com.hikem.arks.arks;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.io.OutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.MediaActionSound;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.Toast;
import android.content.pm.ResolveInfo;
import android.content.ComponentName;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Video.Media;
import android.hardware.Camera.CameraInfo;
import android.view.Surface;
import android.app.AlertDialog;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.view.WindowManager.LayoutParams;

public class CameraActivity extends Activity implements SurfaceHolder.Callback {

    UserSessionManager session;
    Intent intent;
    int cameraId;
    private int displayOrientation;
    private int layoutOrientation;
    Camera mCamera;
    SurfaceView mPreview;
    Button btn_enviar;
    Button btn_cancelar;
    ImageButton ib_registrar;
    ImageButton ib_flash;
    String flash_status;
    CameraOrientationListener orientationListener;
    Bitmap bitmap;
    AlertDialog dialog;
    Button btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        session = new UserSessionManager(getApplicationContext());
        intent = getIntent();

        btn_enviar = (Button) findViewById(R.id.btn_enviar);
        btn_cancelar = (Button) findViewById(R.id.btn_cancelar);
        ib_registrar = (ImageButton) findViewById(R.id.ib_registrar);
        ib_flash = (ImageButton) findViewById(R.id.ib_flash);
        btn = (Button) findViewById(R.id.button);

        mPreview = (SurfaceView)findViewById(R.id.preview);
        mPreview.getHolder().addCallback(this);
        mPreview.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        orientationListener = new CameraOrientationListener(this);
    }

    public void onBackPressed() {
        Camera.Parameters params = mCamera.getParameters();
        session.SaveFlash(params.getFlashMode());
        Intent i = new Intent(getApplicationContext(), MainActivity2.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        orientationListener.enable();

        try {
            mCamera = Camera.open(cameraId);

            if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                mCamera.setDisplayOrientation(90);
            } else {
                mCamera.setDisplayOrientation(0);
            }

            Carrega_Flash();
        } catch (Exception exception) {
            Toast.makeText(getBaseContext(), "Erro ao abrir a camera.", Toast.LENGTH_LONG).show();
            return;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        orientationListener.disable();
        mCamera.stopPreview();
        mCamera.release();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Camera.Parameters params = mCamera.getParameters();
        List<Camera.Size> sizes = params.getSupportedPreviewSizes();
        Camera.Size selected = sizes.get(0);
        params.setPreviewSize(selected.width,selected.height);
        mCamera.setParameters(params);

        mCamera.startPreview();

        PegaResolucoes();
    }

    private List<Camera.Size> PegaResolucoes() {

        Camera.Parameters params = mCamera.getParameters();
        List<Camera.Size> sizes = params.getSupportedPictureSizes();
        Camera.Size selected = sizes.get(9);
        params.setPictureSize(selected.width, selected.height);
        mCamera.setParameters(params);

        return sizes;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(mPreview.getHolder());
            determineDisplayOrientation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i("PREVIEW", "surfaceDestroyed");
    }

    private void Carrega_Flash() {
        if(this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {

            HashMap<String, String> user = session.getUserDetails();
            flash_status = user.get(UserSessionManager.KEY_FLASH);
            Camera.Parameters params = mCamera.getParameters();

            if (flash_status.equals("off")) {
                params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                ib_flash.setImageResource(R.drawable.flash_off);
            } else if (flash_status.equals("on")) {
                params.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                ib_flash.setImageResource(R.drawable.flash_on);
            } else {
                params.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                ib_flash.setImageResource(R.drawable.flash_a);
            }

            mCamera.setParameters(params);
            ib_flash.setVisibility(View.VISIBLE);
        } else {
            ib_flash.setVisibility(View.INVISIBLE);
        }
    }

    public void Flash_Click(View view){
        if(this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            Camera.Parameters params = mCamera.getParameters();

            if (params.getFlashMode().equals("off")) {
                params.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                ib_flash.setImageResource(R.drawable.flash_on);
            } else if (params.getFlashMode().equals("on")) {
                params.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                ib_flash.setImageResource(R.drawable.flash_a);
            } else {
                params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                ib_flash.setImageResource(R.drawable.flash_off);
            }

            mCamera.setParameters(params);
            ib_flash.setVisibility(View.VISIBLE);
        } else {
            ib_flash.setVisibility(View.INVISIBLE);
        }
    }

    public void Registrar_Click(View view) {

        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        new MediaActionSound().play(MediaActionSound.FOCUS_COMPLETE);
                        new MediaActionSound().play(MediaActionSound.SHUTTER_CLICK);
                    }

                    orientationListener.rememberOrientation();

                    mCamera.takePicture(null, null, capturedIt);
                }
            }
        });
    }

    private Camera.PictureCallback capturedIt = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

            if (data == null) {
                Toast.makeText(getApplicationContext(), "Erro", Toast.LENGTH_SHORT).show();
            } else {
                btn_enviar.setVisibility(View.VISIBLE);
                btn_cancelar.setVisibility(View.VISIBLE);
                ib_registrar.setVisibility(View.INVISIBLE);
                ib_flash.setVisibility(View.INVISIBLE);

                int rotation = (displayOrientation + orientationListener.getRememberedOrientation() + layoutOrientation) % 360;
                if(rotation != 0) {
                    Bitmap oldBitmap = bitmap;

                    Matrix matrix = new Matrix();
                    matrix.postRotate(rotation);

                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);

                    oldBitmap.recycle();
                }

                mCamera.stopPreview();
            }
        }
    };

    public static Bitmap decodeSampledBitmapFromResource(byte[] data, int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, options);

        options.inSampleSize = calculaInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
    }

    public static int calculaInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        } else if (height < reqHeight || width < reqWidth) {
            final int doubleHeight = height * 2;
            final int doubleWidth = width * 2;

            while ((doubleHeight * inSampleSize) < reqHeight
                    && (doubleWidth * inSampleSize) < reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public void Enviar_Click(View v) {
        if (intent != null) {
            if (getNetworkStatus()) {

                File folder = new File(Environment.getExternalStorageDirectory() + "/Arks");
                if (!folder.exists()) {
                    folder.mkdir();
                }

                String arquivo = Environment.getExternalStorageDirectory() + "/Arks/" + System.currentTimeMillis() + ".jpg";
                File pictureFile = new File(arquivo);

                try {
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                } catch (Exception ex) {

                }

                File file = new File(arquivo);
                Uri imageUri = Uri.fromFile(file);

                intent = new Intent("INICIAR_CAMERA_POST");
                intent.putExtra(intent.EXTRA_STREAM, imageUri);

                int index = arquivo.lastIndexOf("/");
                String nome = arquivo.substring(index + 1);
                InsereFila(nome, "Pendente");

                startService(createExplicitFromImplicitIntent(this, intent));
                Intent i = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(i);
                Camera.Parameters params = mCamera.getParameters();
                session.SaveFlash(params.getFlashMode());
                finish();
            } else {
                Toast.makeText(getBaseContext(), "Sem conexão de internet.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void Cancelar_Click(View v) {
        btn_enviar.setVisibility(View.INVISIBLE);
        btn_cancelar.setVisibility(View.INVISIBLE);
        ib_registrar.setVisibility(View.VISIBLE);
        ib_flash.setVisibility(View.VISIBLE);
        mCamera.startPreview();
    }

    private boolean getNetworkStatus() {

        boolean status;

        ConnectivityManager con = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (con.getNetworkInfo(0).isConnected()) {
            status = true;
        } else if (con.getNetworkInfo(1).isConnected()) {
            status = true;
        } else {
            status = false;
        }

        return status;
    }

    private long InsereFila(String nome, String status) {
        long id = 0;

        SQLiteDatabase db = openOrCreateDatabase("arks.db", Context.MODE_PRIVATE, null);

        ContentValues ctv = new ContentValues();
        ctv.put("nome", nome);
        ctv.put("status", status);
        ctv.put("data", getDateTime());

        Log.d("PostService", ctv.toString());

        id = db.insert("fila", "_id", ctv);

        db.close();

        return id;
    }

    private String getDateTime() {

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }

        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        Intent explicitIntent = new Intent(implicitIntent);

        explicitIntent.setComponent(component);

        return explicitIntent;
    }

    public void determineDisplayOrientation() {
        CameraInfo cameraInfo = new CameraInfo();
        Camera.getCameraInfo(cameraId, cameraInfo);

        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        int degrees  = 0;

        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;

            case Surface.ROTATION_90:
                degrees = 90;
                break;

            case Surface.ROTATION_180:
                degrees = 180;
                break;

            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int displayOrientation;

        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            displayOrientation = (cameraInfo.orientation + degrees) % 360;
            displayOrientation = (360 - displayOrientation) % 360;
        } else {
            displayOrientation = (cameraInfo.orientation - degrees + 360) % 360;
        }

        this.displayOrientation = displayOrientation;
        this.layoutOrientation  = degrees;

        mCamera.setDisplayOrientation(displayOrientation);
    }

    public void Resolucao_Click(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CameraActivity.this);
        builder.setTitle("Resoluções:");
        ListView list = new ListView(CameraActivity.this);
        list.setAdapter(new CustomCameraList(CameraActivity.this, PegaResolucoes()));

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                Toast.makeText(CameraActivity.this, "Clicked at Position" + position, Toast.LENGTH_SHORT).show();
            }
        });

        builder.setView(list);
        dialog = builder.create();
        dialog.show();
    }

}
