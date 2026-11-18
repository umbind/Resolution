package com.resolution.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.resolution.app.data.local.entity.IssueEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IssueDao {
    @Query("SELECT * FROM issues ORDER BY createdAt DESC")
    fun getAllIssues(): Flow<List<IssueEntity>>

    @Query("SELECT * FROM issues WHERE category = :category ORDER BY createdAt DESC")
    fun getIssuesByCategory(category: String): Flow<List<IssueEntity>>

    @Query("SELECT * FROM issues WHERE districtId = :districtId ORDER BY createdAt DESC")
    fun getIssuesByDistrict(districtId: String): Flow<List<IssueEntity>>

    @Query("SELECT * FROM issues WHERE issueId = :issueId LIMIT 1")
    suspend fun getIssueById(issueId: String): IssueEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIssues(issues: List<IssueEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIssue(issue: IssueEntity)

    @Query("DELETE FROM issues")
    suspend fun clearAll()
}
