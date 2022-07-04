package com.xpresspayments.smartposextdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.xpresspayments.smartxext.XpressSmartPos
import com.xpresspayments.smartxext.api.XTransaction
import com.xpresspayments.smartxext.api.XTransactionRequest
import com.xpresspayments.smartxext.api.XTransactionResponse

class MainActivity : AppCompatActivity() {

    private val makePrintLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            //done printing
        }

    private val transactionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            printTransaction(it)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edAmount = findViewById<EditText>(R.id.ed_amount)
        findViewById<Button>(R.id.pay_btn).setOnClickListener {
            val amount = edAmount.text.toString().toLongOrNull()
            startTransaction(amount)
        }
    }

    private fun startTransaction(amount: Long?) {
        amount?.let {
            val request = XTransactionRequest(amount) //amount in kobo
            XpressSmartPos.startTransaction(this, request, transactionLauncher, "FUELMETRICS")
        }
    }

    private fun printTransaction(it: ActivityResult): XTransaction? {
        val result = XTransactionResponse(it).transaction
        if (result != null) {
            XpressSmartPos.launchPrint(this, makePrintLauncher, result, canPrintMerchant = true)
        }
        return result
    }
}