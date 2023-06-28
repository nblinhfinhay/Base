package com.nblinh.base.presentation

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.ImageView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.ImageAndVideo
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.nblinh.base.R
import com.nblinh.base.base.BaseActivity
import timber.log.Timber
import java.io.File
import java.util.Date

class CameraActivity : BaseActivity() {

    private val cameraRequestCode = 1
    private val galleryRequestCode = 2
    private var imageURI: Uri? = null

    private val resultLauncherCamera =
        registerForActivityResult(ActivityResultContracts.TakePicture()) {
            imageURI?.let {
                findViewById<ImageView>(R.id.ivImage).setImageURI(it)
            }
        }
    private val resultLauncherGallery =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { result ->
            result?.let {
                findViewById<ImageView>(R.id.ivImage).setImageURI(it)
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        findViewById<View>(R.id.btnCamera).setOnClickListener {
            requestCameraPermission()
        }

        findViewById<View>(R.id.btnGallery).setOnClickListener {
            requestGalleryPermission()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            cameraRequestCode -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else if (permissions.isNotEmpty()) {
                val showRationale = shouldShowRequestPermissionRationale(permissions[0])
                if (!showRationale) {
                    AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Yêu cầu truy cập Camera")
                        .setMessage("Hãy cho phép quyền truy cập camera để sử dụng tính năng này, đi tới Cài đặt Quyền?")
                        .setPositiveButton("Đồng ý") { dialog, which ->
                            dialog.dismiss()
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            val uri = Uri.fromParts("package", packageName, null)
                            intent.data = uri
                            startActivity(intent)
                        }.setNegativeButton("Huỷ", null).show()
                } else {
                    AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Yêu cầu truy cập Camera")
                        .setMessage("Hãy cho phép quyền truy cập camera để sử dụng tính năng này")
                        .setPositiveButton("Đồng ý") { dialog, which ->
                            dialog.dismiss()
                            requestCameraPermission()
                        }.setNegativeButton("Huỷ", null).show()
                }

            }

            galleryRequestCode -> {
                grantResults.forEach {
                    Timber.d("GrandRS $it")
                }
                permissions.forEach {
                    Timber.d("GrandRS Per $it")
                }
                if (grantResults.isNotEmpty()) {
                    val permissionsNotGranted = arrayListOf<String>()
                    grantResults.forEachIndexed { index, it ->
                        if (it != PackageManager.PERMISSION_GRANTED) {
                            permissionsNotGranted.add(permissions[index])
                        }
                    }
                    if (permissionsNotGranted.size == 0) {
                        openGallery()
                    } else {
                        val showRationale =
                            shouldShowRequestPermissionRationale(permissionsNotGranted.first())
                        if (!showRationale) {
                            AlertDialog.Builder(this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Yêu cầu truy cập Thư viện")
                                .setMessage("Hãy cho phép quyền truy cập thư viện để sử dụng tính năng này, đi tới Cài đặt Quyền?")
                                .setPositiveButton("Đồng ý") { dialog, which ->
                                    dialog.dismiss()
                                    val intent =
                                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                    val uri = Uri.fromParts("package", packageName, null)
                                    intent.data = uri
                                    startActivity(intent)
                                }.setNegativeButton("Huỷ", null).show()
                        } else {
                            AlertDialog.Builder(this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Yêu cầu truy cập Thư viện")
                                .setMessage("Hãy cho phép quyền truy cập thư viện để sử dụng tính năng này")
                                .setPositiveButton("Đồng ý") { dialog, which ->
                                    dialog.dismiss()
                                    requestGalleryPermission()
                                }.setNegativeButton("Huỷ", null).show()
                        }
                    }
                }
            }
        }
    }

    private fun requestCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermissions(arrayOf(Manifest.permission.CAMERA))) {
                openCamera()
            } else {
                requestPermission(arrayOf(Manifest.permission.CAMERA), cameraRequestCode)
            }
        } else {
            openCamera()
        }
    }

    private fun requestGalleryPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val storagePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arrayOf(
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_AUDIO,
                    Manifest.permission.READ_MEDIA_VIDEO
                )
            } else {
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            }
            if (checkPermissions(storagePermission)) {
                openGallery()
            } else {
                requestPermission(storagePermission, galleryRequestCode)
            }
        } else {
            openGallery()
        }
    }

    private fun checkPermissions(permissions: Array<String>): Boolean {
        permissions.forEach { permission ->
            if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    private fun requestPermission(permissions: Array<String>, requestCode: Int) {
        ActivityCompat.requestPermissions(this, permissions, requestCode)
    }

    private fun openCamera() {
        imageURI = createImageURI(Date().time.toString())
        resultLauncherCamera.launch(imageURI)
    }

    private fun createImageURI(fileName: String): Uri? {
        val image = File(applicationContext.filesDir, fileName)
        return FileProvider.getUriForFile(applicationContext, "com.nblinh.base", image)

    }

    private fun openGallery() {
        val request: PickVisualMediaRequest = PickVisualMediaRequest.Builder()
            .setMediaType(ImageAndVideo)
            .build()
        resultLauncherGallery.launch(request)
    }
}