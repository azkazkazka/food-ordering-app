package com.example.majika

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.hardware.camera2.CameraCaptureSession.CaptureCallback
import android.media.Image
import android.media.ImageReader
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.util.Size
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.OutputStream
import java.nio.ByteBuffer


class Twibbon : Fragment() {
    private lateinit var textureView: TextureView
    private lateinit var cameraDevice: CameraDevice
    private lateinit var cameraButton: Button
    private lateinit var previewSession: CameraCaptureSession
    private lateinit var previewRequestBuilder: CaptureRequest.Builder
    private lateinit var previewRequest: CaptureRequest

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
        }

        override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean = false

        override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
            // ignore
        }
    }

    private val captureCallbackListener = object : CameraCaptureSession.CaptureCallback() {
        override fun onCaptureCompleted(
            session: CameraCaptureSession,
            request: CaptureRequest,
            result: TotalCaptureResult
        ) {
            super.onCaptureCompleted(session, request, result)
        }
    }

    private val stateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice) {
            cameraDevice = camera
            createCameraPreviewSession()
        }

        override fun onDisconnected(camera: CameraDevice) {
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
                listOf(previewSurface),
                object : CameraCaptureSession.StateCallback() {
                    override fun onConfigured(session: CameraCaptureSession) {
                        previewSession = session
                        updatePreview()
                    }

                    override fun onConfigureFailed(session: CameraCaptureSession) {
                        Toast.makeText(
                            this@Twibbon.context,
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

    private fun openCamera() {
        val manager = requireActivity().getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraId = manager.cameraIdList.firstOrNull()
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

    private fun updatePreview() {
        previewRequest = previewRequestBuilder.build()
        previewSession.setRepeatingRequest(previewRequest, null, null)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_twibbon, container, false)
        textureView = view.findViewById(R.id.surfaceView)
        textureView.surfaceTextureListener = surfaceTextureListener
        cameraButton = view.findViewById(R.id.camera_button)
        cameraButton.setOnClickListener { View.OnClickListener { v ->
            takePicture();
        } }
        return view
    }

    override fun onPause() {
        super.onPause()
        cameraDevice.close()
    }

    private val onImageAvailableListener = object : ImageReader.OnImageAvailableListener {
        override fun onImageAvailable(reader: ImageReader) {
        // Get the captured image as a byte buffer
        val image: Image = reader.acquireLatestImage()
        val buffer: ByteBuffer = image.planes[0].buffer
        val bytes = ByteArray(buffer.capacity())
        buffer.get(bytes)

        // Save the image to file
        val file = File(requireContext().externalMediaDirs.first(),
        "image-${System.currentTimeMillis()}.jpg")
        FileOutputStream(file).use { it.write(bytes) }

        // Display a toast message to indicate the image capture is complete`
        Toast.makeText(requireContext(), "Image saved: ${file.absolutePath}", Toast.LENGTH_LONG).show()
        }
    }

    private fun takePicture() {
        val imageDimension = Size(1920, 1080)
        val imageReader = ImageReader.newInstance(imageDimension.width, imageDimension.height,
            ImageFormat.JPEG, 1)
        val outputSurfaces = ArrayList<Surface>(2)
        outputSurfaces.add(imageReader.surface)
        val surfaceTexture = textureView.surfaceTexture?.apply {
            setDefaultBufferSize(textureView.width, textureView.height)
        }
        outputSurfaces.add(Surface(surfaceTexture))
        val captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
        captureBuilder.addTarget(imageReader.surface)
        captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO)
        val rotation = requireActivity().windowManager.defaultDisplay.rotation
        captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, 1)
        previewSession.stopRepeating()
        previewSession.abortCaptures()
        previewSession.capture(captureBuilder.build(), captureCallbackListener, null)
//    // Get the camera manager
//
//    // Set up the image reader
//    val imageReader = ImageReader.newInstance(
//        640,
//        640,
//        ImageFormat.JPEG,
//        1
//    )
//    imageReader.setOnImageAvailableListener(onImageAvailableListener, null)
//
//    if (ContextCompat.checkSelfPermission(this.requireActivity(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//        ActivityCompat.requestPermissions(
//            this.requireActivity(), arrayOf(android.Manifest.permission.CAMERA),
//            1
//        )
//    } else {
//        println("LESGOW")
//        val manager = requireActivity().getSystemService(Context.CAMERA_SERVICE) as CameraManager
//        val cameraId = manager.cameraIdList.firstOrNull()
//        if (cameraId != null) {
//            manager.openCamera(cameraId, object : CameraDevice.StateCallback() {
//                override fun onOpened(camera: CameraDevice) {
//                    cameraDevice = camera
//
//                    val surfaceTexture = textureView.surfaceTexture?.apply {
//                        setDefaultBufferSize(textureView.width, textureView.height)
//                    }
//                    val previewSurface = Surface(surfaceTexture)
//
//                    // Set up the capture session
//                    val surfaces = listOf(previewSurface, imageReader.surface)
//                    camera.createCaptureSession(surfaces, object : CameraCaptureSession.StateCallback() {
//                        override fun onConfigured(session: CameraCaptureSession) {
//                            val captureSession = session
//
//                            // Set up the capture request
//                            val previewRequestBuilder = camera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
//                            previewRequestBuilder.addTarget(previewSurface)
//                            previewRequestBuilder.addTarget(imageReader.surface)
//
//                            // Start the capture session
//                            session.setRepeatingRequest(previewRequestBuilder.build(), null, null)
//                        }
//
//                        override fun onConfigureFailed(session: CameraCaptureSession) {
//                            Toast.makeText(requireContext(), "Failed to configure capture session", Toast.LENGTH_LONG).show()
//                        }
//                    }, null)
//                }
//
//                override fun onDisconnected(camera: CameraDevice) {
//                    cameraDevice?.close()
//                }
//
//                override fun onError(camera: CameraDevice, error: Int) {
//                    cameraDevice?.close()
//                    Toast.makeText(requireContext(), "Camera error: $error", Toast.LENGTH_LONG).show()
//                }
//            }, null)
//        }
//    }
    }
}
