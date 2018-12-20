package sanaebadi.info.teacherhandler.database.query

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "querys")
class Querys(
    @ColumnInfo(name = "query_message") var query_message: String? = "",
    @ColumnInfo(name = "query_stu_name") var query_stu_name: String? = ""
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "query_id")
    var query_id: Int = 0

}