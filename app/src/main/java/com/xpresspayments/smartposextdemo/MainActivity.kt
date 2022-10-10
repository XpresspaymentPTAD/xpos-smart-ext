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

    private val reportLauncher =
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

        findViewById<Button>(R.id.pay_print).setOnClickListener {
            printSample()
        }
        findViewById<Button>(R.id.eod).setOnClickListener {
            report()
        }
    }

    private fun printSample() {
        val map = linkedMapOf<String, String>()
        map["Section 1"] = "M?1"
        map["Field 1"] = "one"
        map["Field 2"] = "two"
        map["Section 2"] = "M?2"
        map["Field 3"] = "three"
        map["Field 4"] = "four"
        map["Section 3"] = "M?3"
        map["Field 5"] = "Five"
        map["Field 6"] = "Six"
        XpressSmartPos.launchPrint(this, makePrintLauncher, null, map)
    }

    private fun report(){
        XpressSmartPos.launchReport(this, reportLauncher)
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