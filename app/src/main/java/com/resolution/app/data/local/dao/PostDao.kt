package com.resolution.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.resolution.app.data.local.entity.PostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Query("SELECT * FROM posts ORDER BY createdAt DESC")
    fun getAllPosts(): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts WHERE postType = :postType ORDER BY createdAt DESC")
    fun getPostsByType(postType: String): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts WHERE category = :category ORDER BY createdAt DESC")
    fun getPostsByCategory(category: String): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts WHERE postId = :postId LIMIT 1")
    suspend fun getPostById(postId: String): PostEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<PostEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: PostEntity)

    @Query("UPDATE posts SET triedCount = triedCount + 1 WHERE postId = :postId")
    suspend fun incrementTriedCount(postId: String)

    @Query("UPDATE posts SET supportCount = supportCount + 1 WHERE postId = :postId")
    suspend fun incrementSupportCount(postId: String)

    @Query("DELETE FROM posts")
    suspend fun clearAll()
}
