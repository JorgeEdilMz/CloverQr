package com.clover.cloverqr;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Objects;

public class PostActivity extends AppCompatActivity {

    Uri imageUri;
    String myUri = "";
    StorageTask<UploadTask.TaskSnapshot> uploadTask;
    StorageReference storageReference;

    private static final int PICK_IMAGE_REQUEST = 1;

    ImageView close, image_Added;
    TextView post;
    EditText descripcion;
    RadioGroup radioGroupCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        image_Added = findViewById(R.id.image_added);
        post = findViewById(R.id.post);
        descripcion = findViewById(R.id.descripcion);
        radioGroupCategories = findViewById(R.id.radio_group_categories);

        storageReference = FirebaseStorage.getInstance().getReference("Plantas");

        post.setOnClickListener(view -> {
            String category = getCategoryFromRadioGroup();
            if (category != null) {
                // Llama al método uploadImage() para guardar el post en la base de datos
                uploadImage();
            } else {
                Toast.makeText(PostActivity.this, "Selecciona una categoría", Toast.LENGTH_SHORT).show();
            }
        });

        image_Added.setOnClickListener(view -> openImagePicker());
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            // Configura la imagen seleccionada en tu ImageView o en cualquier otro lugar necesario.
            // image_Added.setImageURI(imageUri);
        }
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
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            myUri = uri.toString();

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");

                            String postId = reference.push().getKey();

                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("postid", postId);
                            hashMap.put("postimage", myUri);
                            hashMap.put("description", descripcion.getText().toString());
                            hashMap.put("publisher", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
                            hashMap.put("category", category);

                            reference.child(postId).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    progressDialog.dismiss();
                                    startActivity(new Intent(PostActivity.this, AdminFragment.class));
                                    finish();
                                }
                            });
                        }
                    });
                } else {
                    Toast.makeText(PostActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(e -> Toast.makeText(PostActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(PostActivity.this, "Imagen no Seleccionada", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para obtener la categoría seleccionada del RadioGroup
    private String getCategoryFromRadioGroup() {
        int selectedId = radioGroupCategories.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton radioButton = findViewById(selectedId);
            return radioButton.getText().toString();
        } else {
            return null;
        }
    }
}
