package com.example.majika

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.majika.viewmodel.PaymentViewModel

class Payment : AppCompatActivity() {
    private lateinit var codeScanner: CodeScanner
    private val viewModel by lazy { PaymentViewModel(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.payment_page)

        setupPermissions()
        codeScanner()

        findViewById<Button>(R.id.button2).setOnClickListener(View.OnClickListener {
            onBackPressed()
        })
    }

    private fun codeScanner() {
        codeScanner = CodeScanner(this, findViewById(R.id.scanner_view))

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.SINGLE
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                runOnUiThread {
                    Log.d("Main", "Scan result: ${it.text}")
                    val duration = 5000
                    val dur = 2000
                    var payment = ""
                    var notYetSuccess = true
                    var count = 1
                    viewModel.apply {
                        postPayment(it.text)
                        paymentStatus.observe(this@Payment) { paymentStatus ->
                            payment = paymentStatus
                            if (payment == "SUCCESS") {
                                if (notYetSuccess && count == 1) {
                                    println("Payment Status: $paymentStatus")
                                    codeScanner.releaseResources()
                                    codeScanner.stopPreview()
                                    findViewById<TextView>(R.id.text_view).setText("Payment Success.\nEnjoy!")
//                                    Handler().postDelayed({
                                    val intent = Intent(this@Payment, PaymentSuccess::class.java)
                                    startActivity(intent)
//                                    }, duration.toLong())
                                }
                                notYetSuccess = false
                                count++
                            } else {
                                findViewById<TextView>(R.id.text_view).setText("Payment Failed.\nPlease Retry in a Few Second")
                                Handler().postDelayed({
                                    codeScanner.startPreview()
                                }, dur.toLong())
                            }
                        }
                    }
                }
            }

            findViewById<View>(R.id.scanner_view).setOnClickListener {
                codeScanner.startPreview()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            this, arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_REQ
        )
    }

    override fun onRequestPermissionsResult (
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_REQ -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        this,
                        "You need the camera permission to use this app",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    companion object {
        private const val CAMERA_REQ = 101
    }

    override fun onBackPressed() {
        val fm: FragmentManager = supportFragmentManager
        if (fm.backStackEntryCount > 0) {
            fm.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}