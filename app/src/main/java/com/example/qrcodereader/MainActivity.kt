package com.example.qrcodereader

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.qrcodereader.databinding.ActivityMainBinding
import com.google.zxing.integration.android.IntentIntegrator


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnQRCode.setOnClickListener{setUpQRCode()}

    }

    private fun setUpQRCode(){
        IntentIntegrator(this)
            .setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            .setTorchEnabled(false)
            .setBeepEnabled(true)
            .setPrompt("Scan QR Code")
            .initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null) {
            if (result.contents !== null) {
                binding.textViewQR.text = result.contents

                if (binding.textViewQR != null) {
                    binding.textViewQR.setOnClickListener {
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(result.contents))
                        startActivity(browserIntent)
                    }
                } else {
                    Toast.makeText(
                        this, "Um erro inesperado aconteceu =/", Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                Toast.makeText(this, "Um erro inesperado aconteceu =/", Toast.LENGTH_SHORT).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}