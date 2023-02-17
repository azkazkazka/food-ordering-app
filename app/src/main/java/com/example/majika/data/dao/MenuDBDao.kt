package com.example.majika.data.dao

import androidx.room.*
import com.example.majika.data.entity.MenuDB

@Dao
interface MenuDBDao {
    @Query("SELECT * FROM menu")
    fun getAll(): List<MenuDB>

    @Query("SELECT * FROM menu WHERE id IN (:menuIds)")
    fun loadAllByIds(menuIds: IntArray): List<MenuDB>

    @Query("SELECT * FROM menu WHERE name = :name")
    fun findByName(name: String): MenuDB

    @Insert
    fun insertAll(vararg menu: MenuDB)

    @Query("UPDATE menu SET quantity = :quantity WHERE name = :name")
    fun update(name: String, quantity: Int)

    @Delete
    fun delete(menu: MenuDB)
}