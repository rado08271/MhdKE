package sk.rafig.mhdke.util

object SmsSpecs {

    var serviceProviderNumber: String = "1166"
    const val serviceProviderSmsCondition: String = " "
    var length: Long = 3600
    var price: String = "1,10"

    fun setNewLocation(city: String): Boolean {
        when {
            city.equals("Bratislava") -> {
                serviceProviderNumber = "1100"
                length = 4200
                price = "1,30"
                return true
            }

            city.equals("Žilina") -> {
                serviceProviderNumber = "1155"
                length = 4200
                price = "1,00"
                return true
            }

            city.equals("Banská Bystrica") -> {
                serviceProviderNumber = "1133"
                length = 2700
                price = "0,79"
                return true
            }

            city.equals("Prešov") -> {
                serviceProviderNumber = "1144"
                length = 1800
                price = "0,70"
                return true
            }

            city.equals("Trnava") -> {
                serviceProviderNumber = "1122"
                length = 3600
                price = "0,70"
                return true
            }

            city.equals("Nitra") -> {
                serviceProviderNumber = "1177"
                length = 3600
                price = "0,90"
                return true
            }

            else -> {
                serviceProviderNumber = "1166"
                length = 3600
                price = "1.10"
                return false
            }
        }
    }
}