package com.example.majika

class MenuModel(
    private var nama: String,
    private var desc: String,
//    private var x_coord: Float,
//    private var y_coord: Float,
) {
    var cabang_restoran: String = nama
        get() = field

    var deskripsi: String = desc
        get() = field

//    var koordinatX: Float = x_coord
//        get() = field
//
//    var koordinatY: Float = y_coord
//        get() = field
}