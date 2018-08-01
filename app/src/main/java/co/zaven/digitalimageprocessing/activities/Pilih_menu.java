package co.zaven.digitalimageprocessing.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.zaven.digitalimageprocessing.R;
import co.zaven.digitalimageprocessing.helpers.ActivityHelper;

public class Pilih_menu extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private Bundle bundle;

    ImageView inputGambar;

    Button get;
    private static final int PICK_IMAGE=100;

    @Override   //mengganti gambar yang dideteksi
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proses_pilih_menu);
//        ButterKnife.bind(this);
//        ActivityHelper.setupToolbar(this, toolbar);
//
//        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
//                getResources().getResourcePackageName(R.drawable.daun12) + '/' +
//                getResources().getResourceTypeName(R.drawable.daun12) + '/' +
//                getResources().getResourceEntryName(R.drawable.daun12) );
//
//        bundle = new Bundle();
//        bundle.putParcelable(KEY_BITMAP, uri);
        //---------------------------------------------------------------------

        get = (Button)findViewById(R.id.pilih_gambar);
        //aksi mengambil image dari media (galeri)
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse(
                        "content://media/internal/images/media"
                ));
                //dilanjutkan ke PICK_IMAGE
                startActivityForResult(intent,PICK_IMAGE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK && requestCode==PICK_IMAGE){

            ButterKnife.bind(this);
            ActivityHelper.setupToolbar(this, toolbar);

            Uri uri = data.getData();
            String x = getPath(uri);    //string x dari method getPath
            //menampilakan pesan lokasi image berada
            // contoh : /storage/galery/
            Toast.makeText(getApplicationContext(), x, Toast.LENGTH_SHORT).show();

            inputGambar = (ImageView)findViewById(R.id.input_gambar);
            inputGambar.setImageURI(uri);



            bundle = new Bundle();      //mengirim (passing) data image dari method ini ke @OnClick
            bundle.putParcelable(KEY_BITMAP, uri);
        }
    }

    // Get lokasi gambar itu disimpan
    public String getPath(Uri uri){
        if (uri==null)return null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri,projection, null, null, null);
        if (cursor!=null){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }

    @OnClick({R.id.histogram, R.id.id_canny})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.histogram:
                ActivityHelper.startActivity(this, Histogram.class, bundle);// Histogram
                break;
            case R.id.id_canny:
                ActivityHelper.startActivity(this, Canny.class, bundle); // Deteksi Tepi canny
                break;
        }
    }

}
