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

or you print dynamically, It is important to use LinkedMap to print in order as declared.
you can have single middle field. by declaring the value as either M?

```

private fun printSample() {
     val map = linkedMapOf<String, String>()
     map["Section 1"] = "M?"
     map["Field 1"] = "one"
     map["Field 2"] = "two"
     map["Section 2"] = "M?"
     map["Field 3"] = "three"
     map["Field 4"] = "four"
     map["Section 3"] = "M?"
     map["Field 5"] = "Five"
     map["Field 6"] = "Six"
  XpressSmartPos.launchPrint(this, makePrintLauncher, null, map)
}

```