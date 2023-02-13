package com.example.majika.model

class MenuModel (
    private var name: String,
    private var description: String,
    private var currency: String,
    private var price: Int,
    private var sold: Int,
    private var type: String,
    ) {
    var get_name: String = name
        get() = field

    var get_description: String = description
        get() = field

    var get_currency: String = currency
        get() = field

    var get_price: Int = price
        get() = field

    var get_sold: Int = sold
        get() = field

    var get_type: String = type
        get() = field

}