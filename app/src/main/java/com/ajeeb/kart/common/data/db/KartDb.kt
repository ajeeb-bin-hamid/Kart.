package com.ajeeb.kart.common.data.db


import androidx.room.Database
import androidx.room.RoomDatabase
import com.ajeeb.kart.common.data.model.CartTableItem
import com.ajeeb.kart.main.data.db.MainKartDbDao

@Database(entities = [CartTableItem::class], version = 1)
abstract class KartDb : RoomDatabase() {
    abstract val mainKartDbDao: MainKartDbDao
}