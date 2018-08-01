package co.zaven.digitalimageprocessing.activities;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.zaven.digitalimageprocessing.R;
import co.zaven.digitalimageprocessing.helpers.ActivityHelper;
import co.zaven.digitalimageprocessing.helpers.BitmapHelper;

public class Canny extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.image_view)
    ImageView imageView;

    @Bind(R.id.deteksi_canny)
    ImageView deteksiCanny;

    Uri path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proses_canny);
        ButterKnife.bind(this);   //menampilkan ke layout
        ActivityHelper.setupToolbar(this, toolbar);

        path = getIntent().getExtras().getParcelable(KEY_BITMAP);
        try {
            detectEdges(BitmapHelper.readBitmapFromPath(this, path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void detectEdges(Bitmap bitmap) throws IOException {


        Mat rgba = new Mat();
        Utils.bitmapToMat(bitmap, rgba);


        Mat edges = new Mat(rgba.size(), CvType.CV_8UC1);
        Imgproc.cvtColor(rgba, edges, Imgproc.COLOR_RGB2GRAY, 4);
        Imgproc.Canny(edges, edges, 80, 100);

        // Don't do that at home or work it's for visualization purpose.
        BitmapHelper.showBitmap(this, bitmap, imageView);           //tampil ke layout (testing)
        Bitmap resultBitmap = Bitmap.createBitmap(edges.cols(), edges.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(edges, resultBitmap);
        BitmapHelper.showBitmap(this, resultBitmap, deteksiCanny);  //tampil ke layout (canny);

        String filePath = Environment.getExternalStorageDirectory().toString() + "/drawable";
        String fileName = "bugen.jpg";
        GLCMFeatureExtraction glcmfe = new GLCMFeatureExtraction(new File(filePath,fileName),15);
        glcmfe.extract();

        TextView panggilData=(TextView) findViewById(R.id.panggil_data);
        panggilData.setText("Contrast :" +glcmfe.getContrast());
        TextView panggilData2=(TextView) findViewById(R.id.panggil_data2);
        panggilData2.setText("Homogenity :" +glcmfe.getHomogenity());
        TextView panggilData3=(TextView) findViewById(R.id.panggil_data3);
        panggilData3.setText("Entropy :" +glcmfe.getEntropy());
        TextView panggilData4=(TextView) findViewById(R.id.panggil_data4);
        panggilData4.setText("Energy :" +glcmfe.getEnergy());



    }

//        public void cannyGLCM(String[] args){
//        try {
//            GLCMFeatureExtraction glcmfe = new GLCMFeatureExtraction(new File(String.valueOf(path)),15);
//            glcmfe.extract();
//            //System.out.println("mbuh");
//
//            TextView panggilData=(TextView) findViewById(R.id.panggil_data);
////        panggilData.setText("Contrast :" +glcmfe.getContrast());
//            panggilData.setText("Contrast :");
//            TextView panggilData2=(TextView) findViewById(R.id.panggil_data2);
////        panggilData2.setText("Homogenity :" +glcmfe.getHomogenity());
//            panggilData2.setText("Homogenity :");
//            TextView panggilData3=(TextView) findViewById(R.id.panggil_data3);
////        panggilData3.setText("Entropy :" +glcmfe.getEntropy());
//            panggilData3.setText("Entropy :" );
//            TextView panggilData4=(TextView) findViewById(R.id.panggil_data4);
////        panggilData4.setText("Energy :" +glcmfe.getEnergy());
//            panggilData4.setText("Energy :");
//        }catch (IOException ex) {
//            Logger.getLogger(Canny.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }

//    public static void cannyGLCM(String[] args){
//        try {
//            GLCMFeatureExtraction glcmfe = new GLCMFeatureExtraction(new File("resultBitmap"),15);
//            glcmfe.extract();
//            //System.out.println("mbuh");
//
//            TextView panggilData=(TextView) findViewById(R.id.panggil_data);
//            panggilData.setText("Contrast :" +glcmfe.getContrast());
//            TextView panggilData2=(TextView) findViewById(R.id.panggil_data2);
//            panggilData2.setText("Homogenity :" +glcmfe.getHomogenity());
//            TextView panggilData3=(TextView) findViewById(R.id.panggil_data3);
//            panggilData3.setText("Entropy :" +glcmfe.getEntropy());
//            TextView panggilData4=(TextView) findViewById(R.id.panggil_data4);
//            panggilData4.setText("Energy :" +glcmfe.getEnergy());
//
//
////            System.out.println("Contrast: "+glcmfe.getContrast());
////            System.out.println("Homogenity: "+glcmfe.getHomogenity());
////            System.out.println("Entropy: "+glcmfe.getEntropy());
////            System.out.println("Energy: "+glcmfe.getEnergy());
////            System.out.println("Dissimilarity: "+glcmfe.getDissimilarity());
//        }catch (IOException ex) {
//            Logger.getLogger(Canny.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }

}

//        ImageView myimage = (ImageView) findViewById(R.id.return21);
//        myimage.setImageBitmap(resultBitmap);
//GLCMFeatureExtraction glcmfe = new GLCMFeatureExtraction(new File("resultBitmap"),15);
//        GLCMFeatureExtraction glcmfe = new GLCMFeatureExtraction(new File("resultBitmap"),15);
//        glcmfe.extract();

//        ImageView myimage = (ImageView) findViewById(R.id.return21);
//        myimage.setImageBitmap(resultBitmap);

//        TextView panggilData=(TextView) findViewById(R.id.panggil_data);
////        panggilData.setText("Contrast :" +glcmfe.getContrast());
//        panggilData.setText("Contrast :");
//        TextView panggilData2=(TextView) findViewById(R.id.panggil_data2);
////        panggilData2.setText("Homogenity :" +glcmfe.getHomogenity());
//        panggilData2.setText("Homogenity :");
//        TextView panggilData3=(TextView) findViewById(R.id.panggil_data3);
////        panggilData3.setText("Entropy :" +glcmfe.getEntropy());
//        panggilData3.setText("Entropy :" );
//        TextView panggilData4=(TextView) findViewById(R.id.panggil_data4);
////        panggilData4.setText("Energy :" +glcmfe.getEnergy());
//        panggilData4.setText("Energy :");





//        ImageView canny2=(ImageView) findViewById(R.id.deteksi_canny2);
//        canny2.setImageBitmap(resultBitmap);


//        String x = String.valueOf(detectEdgesImageView);
//        Toast.makeText(getApplicationContext(), x, Toast.LENGTH_SHORT).show();

//        TextView panggilData2=(TextView) findViewById(R.id.panggil_data2);
//        panggilData2.setText(" Baris "+ resultBitmap);


//System.out.println("hem hem :"+resultBitmap);

//            File fileDir=getApplicationContext().getFilesDir();
//            File imageFile =new File(fileDir,resultBitmap+".jpg");
//            System.out.println("CONVER BITMAP: "+imageFile);
//            OutputStream os;
//            try {
//                os = new FileOutputStream(imageFile);
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
//
//                os.flush();
//                System.out.println("CONVER BITMAP 2: "+os);
//                os.close();
//            } catch (Exception e) {
//                Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
//            }