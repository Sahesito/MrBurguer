package com.sahe.mrburguer.domain

import android.content.Context
import android.widget.Toast
import com.sahe.mrburguer.db.TinyDB

class ManagmentFavorite(val context: Context) {

    private val tinyDB = TinyDB(context)

    fun toggleFavorite(item: FoodModel): Boolean {
        val listFavorite = getListFavorite()
        val existAlready = listFavorite.any { it.Title == item.Title }

        return if (existAlready) {
            listFavorite.removeAll { it.Title == item.Title }
            tinyDB.putListObject("FavoriteList", listFavorite)
            item.BestFood = false
            Toast.makeText(context, "Removed from Favorites", Toast.LENGTH_SHORT).show()
            false
        } else {
            item.BestFood = true
            listFavorite.add(item)
            tinyDB.putListObject("FavoriteList", listFavorite)
            Toast.makeText(context, "Added to Favorites", Toast.LENGTH_SHORT).show()
            true
        }
    }

    fun getListFavorite(): ArrayList<FoodModel> {
        return tinyDB.getListObject("FavoriteList") ?: arrayListOf()
    }

    fun isFavorite(item: FoodModel): Boolean {
        val listFavorite = getListFavorite()
        return listFavorite.any { it.Title == item.Title }
    }
}