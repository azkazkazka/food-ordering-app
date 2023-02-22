package com.example.majika.data.dao

import androidx.room.*
import com.example.majika.data.entity.MenuDB

@Dao
interface MenuDBDao {
    @Query("SELECT * FROM MenuDB")
    fun getAll(): List<MenuDB>

    @Query("SELECT * FROM MenuDB WHERE id IN (:menuIds)")
    fun loadAllByIds(menuIds: IntArray): List<MenuDB>

    @Query("SELECT quantity FROM MenuDB WHERE name = :name AND description = :desc AND price = :price AND type = :type")
    fun getQuantity(name: String, desc: String, price: Int, type: String): Int

    @Insert
    fun insert(menu: MenuDB)

    @Query("UPDATE MenuDB SET quantity = :quantity WHERE name = :name")
    fun update(name: String, quantity: Int)

    @Query("DELETE FROM MenuDB")
    fun delete()
}