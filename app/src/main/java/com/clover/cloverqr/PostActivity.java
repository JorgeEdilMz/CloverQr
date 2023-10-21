package com.clover.cloverqr;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;
import java.util.Objects;

public class PostActivity extends AppCompatActivity {

    Uri imageUri;
    String myUri = "";
    StorageTask<UploadTask.TaskSnapshot> uploadTask;
    StorageReference storageReference;

    ImageView close, image_Added;
    TextView post;
    EditText nombre, descripcion;
    RadioGroup radioGroupCategories;

    private boolean isCropping = false;
    private boolean isImageSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        close = findViewById(R.id.close_button);
        image_Added = findViewById(R.id.image_added);
        post = findViewById(R.id.post);
        descripcion = findViewById(R.id.descripcion);
        nombre = findViewById(R.id.nombre);
        radioGroupCategories = findViewById(R.id.radio_group_categories);

        storageReference = FirebaseStorage.getInstance().getReference("Plantas");

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PostActivity.this, MainActivity.class));
                finish();
            }
        });

        post.setOnClickListener(view -> {
            String category = getCategoryFromRadioGroup();
            if (category != null) {
                // Llama al método uploadImage() para guardar el post en la base de datos
                uploadImage();
            } else {
                Toast.makeText(PostActivity.this, "Selecciona una categoría", Toast.LENGTH_SHORT).show();
            }
            if (imageUri != null) {

            } else {
                Toast.makeText(PostActivity.this, "Imagen no Seleccionada", Toast.LENGTH_SHORT).show();
            }

        });

        image_Added.setOnClickListener(view -> openImagePicker());

        CropImage.activity()
                .setAspectRatio(1, 1)
                .start(PostActivity.this);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Publicando");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Obtener la categoría seleccionada
        String category = getCategoryFromRadioGroup();

        if (imageUri != null) {
            StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    throw Objects.requireNonNull(task.getException());
                }
                return fileReference.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    myUri = downloadUri.toString();

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Plantas");

                    String postId = reference.push().getKey();

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("postid", postId);
                    hashMap.put("postimage", myUri);
                    hashMap.put("description", descripcion.getText().toString());
                    hashMap.put("nombre", nombre.getText().toString());
                    hashMap.put("publisher", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
                    hashMap.put("category", category);

                    reference.child(postId).setValue(hashMap);

                    progressDialog.dismiss();

                    startActivity(new Intent(PostActivity.this, PostPlantaActivity.class));
                    finish();
                } else {
                    Toast.makeText(PostActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(e -> Toast.makeText(PostActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(PostActivity.this, "Imagen no Seleccionada", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para obtener la categoría seleccionada del RadioGroup
    @Nullable
    private String getCategoryFromRadioGroup() {
        int selectedId = radioGroupCategories.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton radioButton = findViewById(selectedId);
            return radioButton.getText().toString();
        } else {
            return null;
        }
    }


    private void openImagePicker() {
        CropImage.activity()
                .setAspectRatio(1, 1)
                .start(PostActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                image_Added.setImageURI(imageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, "Error Recortando la Imagen: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
