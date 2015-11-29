package com.example.admin.pdmd_practica2_jaime;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView iv;
    private final int REQUEST_IMAGE_GET = 1,ROJO=2,AZUL=3,VERDE=4,INVERTH=5,INVERTV=6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = (ImageView) findViewById(R.id.imageView);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_GET && resultCode ==
                RESULT_OK) {
            //Bitmap thumbnail = data.getParcelableExtra("data");
            Uri uri = data.getData();
            iv.setImageURI(uri);
        }
    }

    /*****************************************************************************/
    public void seleccionar(View v){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_GET);
        }
    }

    public void invertirh(View v) {
        operaciones(INVERTH);
    }

    public void intertirv(View v) {
        operaciones(INVERTV);
    }

    public void escalagris(View v) {
        Bitmap bitmap = ((BitmapDrawable)iv.getDrawable( )).getBitmap();
        Bitmap bmpGris = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas lienzo = new Canvas(bmpGris);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter cmcf = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(cmcf);
        lienzo.drawBitmap(bitmap, 0, 0, paint);
        iv.setImageBitmap(bmpGris);
    }

    public void filtrorojo(View v) {
        operaciones(ROJO);
    }

    public void filtroazul(View v) {
        operaciones(AZUL);
    }

    public void filtroverde(View v) {
        operaciones(VERDE);
    }

    public void rotarizq(View v) {
        Bitmap bitmap = ((BitmapDrawable)iv.getDrawable( )).getBitmap();
        iv.setImageBitmap(rotar(bitmap, 90));
    }

    public void rotardch(View v) {
        Bitmap bitmap = ((BitmapDrawable)iv.getDrawable( )).getBitmap();
        iv.setImageBitmap(rotar(bitmap, -90));
    }

    /****************************************************************************/
    private void operaciones(int tipo) {
        Bitmap bitmap = ((BitmapDrawable) iv.getDrawable()).getBitmap();

        Bitmap bmp = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), bitmap.getConfig());
        int pixel, red, green, blue, alpha;
        for (int i = 0; i < bitmap.getWidth(); i++) {
            for (int j = 0; j < bitmap.getHeight(); j++) {
                pixel = bitmap.getPixel(i, j);
                red = Color.red(pixel);
                green = Color.green(pixel);
                blue = Color.blue(pixel);
                alpha = Color.alpha(pixel);
                if(tipo==ROJO)
                    red+=200;
                if(tipo==AZUL)
                    blue+=200;
                if(tipo==VERDE)
                    green+=200;
                bmp.setPixel(i, j, Color.argb(alpha, red, green, blue));
                if(tipo==INVERTH)
                    bmp.setPixel(bitmap.getWidth()-i-1, j, Color.argb(alpha, red, green, blue));
                if(tipo==INVERTV)
                    bmp.setPixel(i,bitmap.getHeight()-j-1, Color.argb(alpha, red, green, blue));
            }
        }
        iv.setImageBitmap(bmp);
    }
    public Bitmap rotar(Bitmap bmpOriginal,float angulo){
        Matrix matriz = new Matrix();
        matriz.postRotate(angulo);
        return Bitmap.createBitmap(bmpOriginal, 0, 0,
                bmpOriginal.getWidth(), bmpOriginal.getHeight(), matriz, true);
    }
}
