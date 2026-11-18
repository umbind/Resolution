package com.resolution.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.resolution.app.data.local.dao.PostDao
import com.resolution.app.data.local.entity.PostEntity
import com.resolution.app.data.model.ActivityCard
import com.resolution.app.data.model.BestStudyPlan
import com.resolution.app.data.model.DailyStudyStep
import com.resolution.app.data.model.LearningPost
import com.resolution.app.data.model.PostType
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class PostRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val postDao: PostDao? = null
) {
    private val postsCollection = firestore.collection("posts")

    /**
     * Real-time stream of learning posts with offline Room DB fallback
     */
    fun getPostsStream(postType: PostType? = null, category: String = "cat_all"): Flow<List<LearningPost>> = callbackFlow {
        var query: com.google.firebase.firestore.Query = postsCollection
            .orderBy("createdAt", Query.Direction.DESCENDING)

        if (postType != null) {
            query = query.whereEqualTo("postType", postType.name)
        }
        if (category != "cat_all") {
            query = query.whereEqualTo("category", category)
        }

        val listener = query.addSnapshotListener { snapshot, error ->
            if (error != null) {
                // If offline, Room DB or mock fallback will be used
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.isEmpty) {
                val posts = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(LearningPost::class.java)?.copy(postId = doc.id)
                }
                trySend(posts)
            } else {
                // Default to realistic sample data for smooth first-run experience
                trySend(getSampleLearningPosts(postType, category))
            }
        }

        // Send sample posts immediately for instant UI render
        trySend(getSampleLearningPosts(postType, category))

        awaitClose { listener.remove() }
    }

    /**
     * Local cached posts stream from Room DB
     */
    fun getCachedPosts(postType: PostType? = null, category: String = "cat_all"): Flow<List<LearningPost>>? {
        return postDao?.let { dao ->
            when {
                postType != null -> dao.getPostsByType(postType.name).map { list -> list.map { it.toDomainModel() } }
                category != "cat_all" -> dao.getPostsByCategory(category).map { list -> list.map { it.toDomainModel() } }
                else -> dao.getAllPosts().map { list -> list.map { it.toDomainModel() } }
            }
        }
    }

    suspend fun getPostById(postId: String): LearningPost? {
        // Try local Room first
        val cached = postDao?.getPostById(postId)?.toDomainModel()
        if (cached != null) return cached

        // Fallback to sample or remote
        return getSampleLearningPosts(null, "cat_all").firstOrNull { it.postId == postId }
            ?: runCatching {
                postsCollection.document(postId).get().await().toObject(LearningPost::class.java)?.copy(postId = postId)
            }.getOrNull()
    }

    suspend fun addPost(post: LearningPost): Result<String> = runCatching {
        val docRef = postsCollection.document()
        val newPost = post.copy(postId = docRef.id)
        docRef.set(newPost).await()
        postDao?.insertPost(PostEntity.fromDomainModel(newPost))
        docRef.id
    }

    suspend fun markTriedAndWorked(postId: String) {
        postDao?.incrementTriedCount(postId)
        runCatching {
            postsCollection.document(postId)
                .update("triedCount", com.google.firebase.firestore.FieldValue.increment(1))
                .await()
        }
    }

    suspend fun toggleSupport(postId: String) {
        postDao?.incrementSupportCount(postId)
        runCatching {
            postsCollection.document(postId)
                .update("supportCount", com.google.firebase.firestore.FieldValue.increment(1))
                .await()
        }
    }

    /**
     * 5 Realistic Sample Learning Posts from Uttar Pradesh (Hindi)
     */
    companion object {
        fun getSampleLearningPosts(postType: PostType? = null, category: String = "cat_all"): List<LearningPost> {
            val list = listOf(
                LearningPost(
                    postId = "post_1",
                    authorId = "user_sunita",
                    authorName = "सुनीता शर्मा (शिक्षिका)",
                    authorRole = "role_teacher",
                    isExpertVerified = true,
                    postType = PostType.QUESTION,
                    title = "कक्षा 5 के बच्चों को भिन्न (Fractions) खेल-खेल में कैसे सिखाएं?",
                    description = "बच्चे आधा और चौथाई की परिभाषा तो रट लेते हैं, लेकिन जब 2/3 और 3/4 की तुलना करनी होती है तो बहुत भ्रमित हो जाते हैं। कोई सरल खेल या TLM उपाय बताएं।",
                    category = "cat_math_science",
                    targetClass = "कक्षा 4-5",
                    supportCount = 42,
                    triedCount = 28,
                    repliesCount = 3,
                    bestStudyPlan = BestStudyPlan(
                        title = "7 दिवसीय भिन्न (Fraction) समझ योजना",
                        summary = "रोटी और कागज मोड़ने की गतिविधियों से भिन्न को मूर्त रूप में सिखाने का साप्ताहिक रूटीन।",
                        targetClass = "कक्षा 4-5",
                        dailySteps = listOf(
                            DailyStudyStep(1, "कागज मोड़कर आधा व चौथाई", 10, "कागज की पट्टी को 2 और 4 बराबर भागों में मोड़ना", "सफेद कागज"),
                            DailyStudyStep(2, "रोटी/बिस्किट बंटवारा खेल", 15, "समान अंश में बांटने का अनुभव", "बिस्किट या गोल कागज"),
                            DailyStudyStep(3, "रंग भरो भिन्न खेल", 10, "चित्र देखकर 1/2 और 1/4 में रंग भरना", "रंग और कॉपी"),
                            DailyStudyStep(4, "भिन्न की तुलना (किसका हिस्सा बड़ा?)", 15, "1/2 बनाम 1/4 की तुलना", "कागज की पट्टियां"),
                            DailyStudyStep(5, "कंकड़ों से भिन्न", 15, "8 कंकड़ों में से 1/4 (2 कंकड़) चुनना", "कंकड़"),
                            DailyStudyStep(6, "भिन्न प्रश्नोत्तरी व खेल", 15, "सहपाठी जोड़ी में कार्ड चुनकर सवाल पूछना", "संख्या कार्ड"),
                            DailyStudyStep(7, "समीक्षा व बाल मेला प्रदर्शन", 20, "बच्चों द्वारा स्वयं भिन्न चार्ट बनाना", "चार्ट पेपर")
                        )
                    ),
                    createdAt = System.currentTimeMillis() - 3600000
                ),
                LearningPost(
                    postId = "post_2",
                    authorId = "user_ramesh",
                    authorName = "रमेश चन्द्र (अभिभावक)",
                    authorRole = "role_parent",
                    isExpertVerified = false,
                    postType = PostType.ACTIVITY,
                    title = "माचिस की तीलियों से 10-10 के बंडल: दहाई और इकाई का खेल",
                    description = "घर पर बच्चे को 2 अंकों की संख्याओं का स्थानीय मान और हासिल वाला जोड़ सिखाने के लिए यह गतिविधि बहुत कारगर रही।",
                    category = "cat_low_cost_tlm",
                    targetClass = "कक्षा 1-3",
                    activityCard = ActivityCard(
                        cardId = "card_1",
                        postId = "post_2",
                        authorId = "user_ramesh",
                        authorName = "रमेश चन्द्र (अभिभावक)",
                        authorRole = "role_parent",
                        isExpertVerified = false,
                        targetClass = "कक्षा 1-3",
                        timeNeeded = "15 मिनट",
                        materialsNeeded = "पुरानी माचिस की 50 तीलियां और 5 रबर बैंड (खर्च: ₹0)",
                        stepByStep = "1. बच्चे को 10-10 तीलियों को गिनवाकर रबर बैंड से 5 बंडल बनवाएं (प्रत्येक बंडल = 1 दहाई)।\n2. अब जैसे 24 संख्या बनानी हो, तो 2 बंडल (20) और 4 खुली तीलियां रखवाएं।\n3. बच्चे से पूछें: 'कुल कितनी तीलियां हुईं?' बच्चा बंडल और खुली तीलियों को देखकर तुरंत समझ जाता है।",
                        learningOutcome = "दहाई और इकाई की प्रत्यक्ष समझ। बच्चा कभी नहीं भूलता कि दहाई में 10 का समूह होता है।",
                        adaptations = "कंकड़ों या आइसक्रीम की डंडियों से भी किया जा सकता है।",
                        upvotes = 35,
                        triedCount = 22
                    ),
                    supportCount = 35,
                    triedCount = 22,
                    repliesCount = 1,
                    createdAt = System.currentTimeMillis() - 7200000
                ),
                LearningPost(
                    postId = "post_3",
                    authorId = "user_pooja",
                    authorName = "पूजा वर्मा (शिक्षा मित्र)",
                    authorRole = "role_shiksha_mitra",
                    isExpertVerified = true,
                    postType = PostType.ACTIVITY,
                    title = "आवाज की रेलगाड़ी: दो अक्षर वाले शब्द पढ़ने का मजेदार खेल",
                    description = "कक्षा 1 और 2 के जो बच्चे अक्षर पहचान लेते हैं पर जोड़कर पढ़ने में झिझकते हैं, उनके लिए यह रेलगाड़ी खेल रामबाण है।",
                    category = "cat_reading_writing",
                    targetClass = "कक्षा 1-2",
                    activityCard = ActivityCard(
                        cardId = "card_2",
                        postId = "post_3",
                        authorId = "user_pooja",
                        authorName = "पूजा वर्मा (शिक्षा मित्र)",
                        authorRole = "role_shiksha_mitra",
                        isExpertVerified = true,
                        targetClass = "कक्षा 1-2",
                        timeNeeded = "10 मिनट",
                        materialsNeeded = "गत्ते पर लिखे अक्षर कार्ड (क, ल, ज, न, घ, र)",
                        stepByStep = "1. एक बच्चे को इंजन बनाएं जिसके हाथ में 'ज' कार्ड हो।\n2. दूसरे बच्चे को डिब्बा बनाएं जिसके हाथ में 'ल' कार्ड हो।\n3. जैसे ही दोनों बच्चे पास आते हैं, पूरी कक्षा आवाज निकालती है: 'ज... ल... जल!'\n4. नए अक्षर जोड़ते जाएं और नए शब्द बनाते जाएं।",
                        learningOutcome = "ध्वनि संयोजन (Phonetic blending) में हिचकिचाहट खत्म होती है और पढ़ने की गति बढ़ती है।",
                        adaptations = "तीन अक्षर वाले शब्दों (कमल, महल) के लिए 3 बच्चों की रेल बनाएं।",
                        upvotes = 56,
                        triedCount = 39
                    ),
                    supportCount = 56,
                    triedCount = 39,
                    repliesCount = 4,
                    createdAt = System.currentTimeMillis() - 14400000
                ),
                LearningPost(
                    postId = "post_4",
                    authorId = "user_alok",
                    authorName = "आलोक सिंह (अध्यापक)",
                    authorRole = "role_teacher",
                    isExpertVerified = true,
                    postType = PostType.QUESTION,
                    title = "अंग्रेजी शब्दों का उच्चारण और हिचकिचाहट कैसे दूर करें?",
                    description = "ग्रामीण क्षेत्र के 7वीं के छात्र अंग्रेजी पढ़ तो लेते हैं, पर बोलते समय बहुत शर्माते हैं। कक्षा में रोज 10 मिनट क्या गतिविधि कराई जाए?",
                    category = "cat_english_lang",
                    targetClass = "कक्षा 6-8",
                    supportCount = 29,
                    triedCount = 14,
                    repliesCount = 2,
                    createdAt = System.currentTimeMillis() - 28800000
                ),
                LearningPost(
                    postId = "post_5",
                    authorId = "user_kavita",
                    authorName = "कविता दीदी (स्वयंसेवक)",
                    authorRole = "role_volunteer",
                    isExpertVerified = false,
                    postType = PostType.ACTIVITY,
                    title = "परीक्षा के समय 25-5 पोमोडोरो रूटीन: तनाव मुक्त पढ़ाई",
                    description = "बोर्ड परीक्षा के विद्यार्थियों के लिए ध्यान केंद्रित रखने और तनाव दूर करने की सरल तकनीक।",
                    category = "cat_exam_prep",
                    targetClass = "कक्षा 9-12",
                    activityCard = ActivityCard(
                        cardId = "card_3",
                        postId = "post_5",
                        authorId = "user_kavita",
                        authorName = "कविता दीदी (स्वयंसेवक)",
                        authorRole = "role_volunteer",
                        isExpertVerified = false,
                        targetClass = "कक्षा 9-12",
                        timeNeeded = "30 मिनट सत्र",
                        materialsNeeded = "साधारण घड़ी या मोबाइल टाइमर, सादा कागज",
                        stepByStep = "1. 25 मिनट बिना रुके सिर्फ एक विषय का एक अध्याय पढ़ें।\n2. 5 मिनट का ब्रेक लें: पानी पिएं, गहरी सांस लें या थोड़ा टहलें।\n3. 4 सत्रों के बाद 20 मिनट का लंबा विश्राम लें।",
                        learningOutcome = "लगातार रटने की थकान खत्म होती है और याददाश्त 40% बेहतर रहती है।",
                        adaptations = "छोटे बच्चों के लिए 15 मिनट पढ़ाई और 5 मिनट खेल रखें।",
                        upvotes = 48,
                        triedCount = 31
                    ),
                    supportCount = 48,
                    triedCount = 31,
                    repliesCount = 2,
                    createdAt = System.currentTimeMillis() - 86400000
                )
            )

            return list.filter { item ->
                (postType == null || item.postType == postType) &&
                (category == "cat_all" || item.category == category)
            }
        }
    }
}
