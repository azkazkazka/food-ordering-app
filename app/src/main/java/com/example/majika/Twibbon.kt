package com.example.majika

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.media.ImageReader
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


class Twibbon : Fragment() {
    private lateinit var textureView: TextureView
    private lateinit var cameraDevice: CameraDevice
    private lateinit var cameraButton: Button
    private lateinit var flipCamera: Button
    private lateinit var previewSession: CameraCaptureSession
    private lateinit var previewRequestBuilder: CaptureRequest.Builder
    private lateinit var previewRequest: CaptureRequest
    private lateinit var manager: CameraManager
    private lateinit var imageReader: ImageReader
    private var cameraId : String = "1"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_twibbon, container, false)
        textureView = view.findViewById(R.id.surfaceView)
        textureView.surfaceTextureListener = surfaceTextureListener
        cameraButton = view.findViewById(R.id.camera_button)
        flipCamera = view.findViewById(R.id.flip_camera)
        manager = requireActivity().getSystemService(Context.CAMERA_SERVICE) as CameraManager
        cameraButton.setOnClickListener {
            takePicture();
        }
        flipCamera.setOnClickListener {
            if (cameraId == "1") {
                cameraId = "0"
            } else {
                cameraId = "1"
            }
            cameraDevice.close()
            openCamera();
        }
        imageReader = ImageReader.newInstance(350, 450, ImageFormat.JPEG, 1)
        return view
    }

    private fun takePicture() {
        if (cameraButton.text == "Camera") {
            val singleReq = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
            singleReq.addTarget(imageReader.surface)
            previewSession.capture(singleReq.build(), null, null)
            previewSession.stopRepeating()
            flipCamera.visibility = View.GONE
            cameraButton.text = "Retake Photo?"
        } else {
            flipCamera.visibility = View.VISIBLE
            cameraButton.text = "Camera"
            createCameraPreviewSession()
        }
    }

    private val surfaceTextureListener = object : TextureView.SurfaceTextureListener {
        override fun onSurfaceTextureAvailable(
            surface: SurfaceTexture,
            width: Int,
            height: Int
        ) {
            openCamera()
        }

        override fun onSurfaceTextureSizeChanged(
            surface: SurfaceTexture,
            width: Int,
            height: Int
        ) {
            // ignore
            println("masuk sini surfacetexturechange??")
        }

        override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean = false

        override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
            // ignore
            println("masuk sini surfacetextureupdata??")
        }
    }

    private fun openCamera() {
        if (cameraId == null) {
            Toast.makeText(
                this.context,
                "No camera is available on this device",
                Toast.LENGTH_SHORT
            ).show()
            activity?.finish()
            return
        }
        try {
            if (ContextCompat.checkSelfPermission(this.requireActivity(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this.requireActivity(), arrayOf(android.Manifest.permission.CAMERA),
                    1
                )
            } else {
                manager.openCamera(cameraId, stateCallback, null)
            }
        } catch (e: CameraAccessException) {
            Toast.makeText(
                this.context,
                "Failed to access camera",
                Toast.LENGTH_SHORT
            ).show()
            activity?.finish()
        }
    }

    private val stateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice) {
            cameraDevice = camera
            createCameraPreviewSession()
        }

        override fun onDisconnected(camera: CameraDevice) {
            println("masuk sini ondisconnect??")
            cameraDevice.close()
        }

        override fun onError(camera: CameraDevice, error: Int) {
            cameraDevice.close()
            this@Twibbon.activity?.finish()
        }
    }

    private fun createCameraPreviewSession() {
        try {
            val surfaceTexture = textureView.surfaceTexture?.apply {
                setDefaultBufferSize(textureView.width, textureView.height)
            }
            val previewSurface = Surface(surfaceTexture)
            previewRequestBuilder =
                cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW).apply {
                    addTarget(previewSurface)
                }
            cameraDevice.createCaptureSession(
                listOf(previewSurface, imageReader.surface),
                object : CameraCaptureSession.StateCallback() {
                    override fun onConfigured(session: CameraCaptureSession) {
                        previewSession = session
                        updatePreview()
                    }

                    override fun onConfigureFailed(session: CameraCaptureSession) {
                        Toast.makeText(
                            requireActivity(),
                            "Failed to configure camera preview session",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                null
            )
        } catch (e: CameraAccessException) {
            Toast.makeText(
                this@Twibbon.context,
                "Failed to access camera",
                Toast.LENGTH_SHORT
            ).show()
            this.activity?.finish()
        }
    }

    private fun updatePreview() {
        previewRequest = previewRequestBuilder.build()
        previewSession.setRepeatingRequest(previewRequest, null, null)
    }
}
