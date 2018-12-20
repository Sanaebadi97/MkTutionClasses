package sanaebadi.info.teacherhandler.database.teacherNameFamily

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teacher_name_last_name")
class TeacherNameFamily(
    @ColumnInfo(name = "teacher_first_name") var teacher_first_name: String? = "",
    @ColumnInfo(name = "teacher_last_name") var teacher_last_name: String? = ""
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "teacher_id")
    var teacher_id: Int = 0
}