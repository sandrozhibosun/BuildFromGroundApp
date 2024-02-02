package com.example.buildfromgroundapp.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.buildfromgroundapp.feature.news.data.datasource.local.ArticlesDao
import com.example.buildfromgroundapp.feature.news.data.model.entity.ArticleEntity

@Database(entities = [ArticleEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getArticlesDao(): ArticlesDao
}
/**
 * @Embedded: Use when you want to flatten an object into the parent entity's table and always retrieve it with the parent entity.
 * @TypeConverter: Use for custom data types or when a complex data structure can be represented as a primitive type in the database.
 * @Relation: Use to maintain one-to-many or many-to-many relationships where related entities are stored in separate tables and can be queried independently.
 */

/** example of relation with Foreign key. eg, users who have list of books
 * Entity:
 *
 * @Entity(tableName = "users")
 * data class UserEntity(
 *     @PrimaryKey(autoGenerate = true) val userId: Int,
 *     val name: String
 * )
 *
 * @Entity(
 *     tableName = "library_books",
 *     foreignKeys = [
 *         ForeignKey(
 *             entity = UserEntity::class,
 *             parentColumns = arrayOf("userId"),
 *             childColumns = arrayOf("userOwnerId"),
 *             onDelete = ForeignKey.CASCADE
 *             onUpdate = ForeignKey.CASCADE
 *         )
 *     ]
 * )
 * data class LibraryBookEntity(
 *     @PrimaryKey(autoGenerate = true) val bookId: Int,
 *     val title: String,
 *     val userOwnerId: Int // Foreign key pointing to User
 * )
 *
 * DAO:
 *
 * @Dao
 * interface UserDAO {
 *     @Insert
 *     suspend fun insertUser(user: UserEntity)
 *
 *     @Transaction
 *     @Query("SELECT * FROM users WHERE userId = :userId")
 *     suspend fun getUserWithLibraryBooks(userId: Int): List<UserWithLibraryBooks>
 * }
 *
 * @Dao
 * interface LibraryBookDAO {
 *     @Insert
 *     suspend fun insertLibraryBook(libraryBook: LibraryBookEntity)
 * }
 *
 * Domain class and relationship(for UI):
 *  data class UserWithLibraryBooks(
 *     @Embedded val user: User,
 *     @Relation(
 *         parentColumn = "userId",
 *         entityColumn = "userOwnerId"
 *     )
 *     val libraryBooks: List<LibraryBook>
 * )
 *
 * @ Repo:
 * class UserRepository(private val userDAO: UserDAO, private val libraryBookDAO: LibraryBookDAO) {
 *
 *     suspend fun addUserWithBooks(user: UserEntity, books: List<LibraryBookEntity>) {
 *         val userId = userDAO.insertUser(user)
 *         books.forEach { book ->
 *             libraryBookDAO.insertLibraryBook(book.copy(userOwnerId = userId.toInt()))
 *         }
 *     }
 *
 *     suspend fun getUserBooks(userId: Int): UserWithLibraryBooks {
 *         return userDAO.getUserWithLibraryBooks(userId).first()
 *     }
 * }
 */
