package sanaebadi.info.teacherhandler.database.batchTime

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "batch_time")
class BatchTime(
    @ColumnInfo(name = "board_time_title")
    var title_time: String? = "",
    @ColumnInfo(name = "first_time")
    var first_time: String? = "",
    @ColumnInfo(name = "second_time")
    var second_time: String? = ""
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "time_id")
    var timeId: Int = 0

}