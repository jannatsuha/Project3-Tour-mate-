package com.group.avengers.tourmate.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.group.avengers.tourmate.Classes.CameraContainer;
import com.group.avengers.tourmate.R;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class TakeAphotoFragment extends Fragment {

    private ImageView showImage;
    private EditText edtImageName;
    private Button btnCameraOpen,btnUploadPhoto;
    private String photoPath=null;
    private File fileName;
    private File photoFilePath =null;

    private String firebase_directory_path="tusu_usa";

    private StorageReference storageReference;
    private DatabaseReference reference;
    private ProgressDialog dialog;


    public interface TakeaPhotoInterface{
        void gotoimage(CameraContainer cameraContainer);
    }

    public TakeAphotoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_take_aphoto, container, false);


        showImage=view.findViewById(R.id.imageViewcameraDisplay);
        edtImageName=view.findViewById(R.id.edtimageName);
        btnCameraOpen=view.findViewById(R.id.btnChooseOpenCmera);
        btnUploadPhoto=view.findViewById(R.id.btnUploadImage);

        String imagename=getArguments().getString("imageName");
        String imageUrl=getArguments().getString("imageUrl");
        String currentDateAndTime=getArguments().getString("imageDateTime");
        String id=getArguments().getString("id");


        storageReference= FirebaseStorage.getInstance().getReference();

        reference= FirebaseDatabase.getInstance().getReference("eventlist").child(id).child("images");
        dialog=new ProgressDialog(getContext());




        btnCameraOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getActivity().getPackageManager())!=null){


                    try {
                        fileName=createImageFile();
                        photoFilePath =createImageFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (photoFilePath !=null){
                        photoPath= photoFilePath.getAbsolutePath();
                        Uri photoURI= FileProvider.getUriForFile(getActivity(),
                                "com.example.android.fileprovider", photoFilePath);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                        startActivityForResult(intent,111);
                    }


                }
            }
        });

        btnUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (photoFilePath!=null){
                    dialog.setTitle("Image Is Uploding....");
                    dialog.show();
                    final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

                    DateFormat dateTime=new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
                    final String currentDateTime=dateTime.format(Calendar.getInstance().getTime());

                    StorageReference storageReference2=storageReference.child(user.getUid()).child(firebase_directory_path+photoFilePath);

                    storageReference2.putFile(Uri.fromFile(photoFilePath)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String imageName=edtImageName.getText().toString().trim();
                            dialog.dismiss();
                            CameraContainer imageUploadInfo=new CameraContainer(imageName,taskSnapshot.getDownloadUrl().toString(),currentDateTime);
                            String imageUploadId=reference.push().getKey();
                            reference.child(user.getUid()).child(imageUploadId).setValue(imageUploadInfo);
                            Toast.makeText(getActivity(), "Image Upload Succesfull", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            Toast.makeText(getActivity(), "Upload Faield", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            dialog.setTitle("Image is Uploding");
                        }
                    });

                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==111&&resultCode==RESULT_OK){
//            Bundle bundle=data.getExtras();
//            Bitmap bitmap= (Bitmap) bundle.get("data");
//            showImage.setImageBitmap(bitmap);
//
            setPic();
            imageName();
        }
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = showImage.getWidth();
        int targetH = showImage.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, bmOptions);
        showImage.setImageBitmap(bitmap);
    }

    private File createImageFile() throws IOException{
        String timeStrub=new SimpleDateFormat("yyyyMMdd_hhmm").format(new Date());
        String imageName="CAMERA_"+timeStrub+"_";
        File privateDirectory=getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        // File privateDirectory=Environment.getExternalStorageDirectory();
        File imageFile=File.createTempFile(imageName,".jpg",privateDirectory);
        return imageFile;
    }
    private void  imageName(){
        edtImageName.setText(fileName.getName());
    }


}
