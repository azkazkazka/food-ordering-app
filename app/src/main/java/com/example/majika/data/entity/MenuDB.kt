package com.example.majika.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MenuDB (
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "currency") var currency: String?,
    @ColumnInfo(name = "price") var price: Int?,
    @ColumnInfo(name = "sold") var sold: Int?,
    @ColumnInfo(name = "type") var type: String?,
    @ColumnInfo(name = "quantity") var quantity: Int?,
)