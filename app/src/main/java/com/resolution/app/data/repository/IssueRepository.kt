package com.resolution.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.resolution.app.data.local.dao.IssueDao
import com.resolution.app.data.local.entity.IssueEntity
import com.resolution.app.data.model.Issue
import com.resolution.app.data.model.Solution
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class IssueRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val issueDao: IssueDao? = null
) {
    private val issuesCollection = firestore.collection("issues")
    private val solutionsCollection = firestore.collection("solutions")

    /**
     * Real-time stream of issues from Firestore with Room local cache fallback
     */
    fun getIssuesStream(category: String = "all"): Flow<List<Issue>> = callbackFlow {
        val query = if (category == "all") {
            issuesCollection.orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
        } else {
            issuesCollection
                .whereEqualTo("category", category)
                .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
        }

        val listener = query.addSnapshotListener { snapshot, error ->
            if (error != null) {
                // If offline or network error, let caller rely on Room DB
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val issues = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(Issue::class.java)?.copy(issueId = doc.id)
                }
                trySend(issues)
            }
        }

        awaitClose { listener.remove() }
    }

    /**
     * Local cached issues stream from Room DB
     */
    fun getCachedIssues(category: String = "all"): Flow<List<Issue>>? {
        return issueDao?.let { dao ->
            if (category == "all") {
                dao.getAllIssues().map { list -> list.map { it.toDomainModel() } }
            } else {
                dao.getIssuesByCategory(category).map { list -> list.map { it.toDomainModel() } }
            }
        }
    }

    /**
     * Create a new issue in Firestore and cache locally
     */
    suspend fun createIssue(issue: Issue): Result<String> {
        return try {
            val docRef = issuesCollection.add(issue).await()
            val createdIssue = issue.copy(issueId = docRef.id)
            issueDao?.insertIssue(IssueEntity.fromDomainModel(createdIssue))
            Result.success(docRef.id)
        } catch (e: Exception) {
            // Cache locally if offline
            issueDao?.insertIssue(IssueEntity.fromDomainModel(issue))
            Result.failure(e)
        }
    }

    /**
     * Toggle community support count
     */
    suspend fun toggleSupport(issueId: String, increment: Boolean): Result<Unit> {
        return try {
            val delta = if (increment) 1L else -1L
            issuesCollection.document(issueId).update(
                "supportCount",
                com.google.firebase.firestore.FieldValue.increment(delta)
            ).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Fetch solutions for a specific issue
     */
    fun getSolutionsStream(issueId: String): Flow<List<Solution>> = callbackFlow {
        val query = solutionsCollection
            .whereEqualTo("issueId", issueId)
            .orderBy("upvoteCount", com.google.firebase.firestore.Query.Direction.DESCENDING)

        val listener = query.addSnapshotListener { snapshot, error ->
            if (error == null && snapshot != null) {
                val solutions = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(Solution::class.java)?.copy(solutionId = doc.id)
                }
                trySend(solutions)
            }
        }

        awaitClose { listener.remove() }
    }

    /**
     * Submit structured solution
     */
    suspend fun submitSolution(solution: Solution): Result<String> {
        return try {
            val docRef = solutionsCollection.add(solution).await()
            // Increment parent issue solution count
            issuesCollection.document(solution.issueId).update(
                "solutionCount",
                com.google.firebase.firestore.FieldValue.increment(1L),
                "status",
                "solutions_added"
            ).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
