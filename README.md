## XpostSmart Extension Android

This is a demo for easy integration of [Xpresspayments](https://https://www.xpresspayments.com.com) Xsmart POS application with your our supported terminals.

## Get Started

###### Step 1

Install the xsmart POS application on your terminal and prep the application successfully.

###### Step 2

Add the .aar dependency to your app build.gradle and build.

```
 dependencies {
     implementation files('libs/smartxExt-debug.aar')
 }

```

## Sample Function Request

To start the transaction, you'll need to pass information such as amount, serviceType, etc.

This repository contain a kotlin sample.

```

private val transactionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
        val transactionResponse = XTransactionResponse(activityResult).transaction
            printTransaction(transactionResponse)
        }

val request = XTransactionRequest(amount) //amount in kobo
XpressSmartPos.startTransaction(this, request, transactionLauncher, "FUELMETRICS")

```

## Print transaction response

To make use of our print function, it is required that you have the GSON dependencies configured in your build.gradle file

```
implementation 'com.google.code.gson:gson:2.8.6'


//In your fragment or activity class
private val makePrintLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            //done printing
        }

private fun printTransaction(response: XTransaction) {
        XpressSmartPos.launchPrint(this, makePrintLauncher, response, canPrintMerchant = true)
    }

```
