package comp4097.comp.hkbu.edu.hk.Infoday.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Event (
    @PrimaryKey
    val id: Int,

    val title: String,
    val deptId: String,
    var bookmarked:Boolean = false //This value may change in run-time
) {}
