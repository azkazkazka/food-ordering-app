package com.example.majika.model

class LocationModel(
    private var name: String,
    private var popular_food: String,
    private var address: String,
    private var contact_person: String,
    private var phone_number: String,
    private var longitude: Double,
    private var latitude: Double,
) {
    var get_name: String = name
        get() = field

    var get_popular_food: String = popular_food
        get() = field

    var get_address: String = address
        get() = field

    var get_contact_person: String = contact_person
        get() = field

    var get_phone_number: String = phone_number
        get() = field

    var get_longitude: Double = longitude
        get() = field

    var get_latitude: Double = latitude
        get() = field
}
