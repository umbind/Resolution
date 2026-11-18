# Technical Architecture: पढ़ाई की चौपाल (Padhai Ki Chaupal)

## 1. High-Level System Architecture

```
[Android App (Jetpack Compose + MVVM)]
      │
      ├── UI Layer: Jetpack Compose + Material 3 + Devanagari Typography
      ├── Domain Layer: UseCases, Business Logic, Validation, AI Transformers
      └── Data Layer:
            ├── Local Cache: Room Database + DataStore Preferences (Offline First)
            └── Remote:
                  ├── Firebase Auth (Phone OTP & Google Sign-In)
                  ├── Cloud Firestore (Realtime database with offline persistence)
                  ├── Firebase Cloud Storage (Compressed educational activity photos)
                  └── Firebase Cloud Functions / AI Logic Proxy
                            └── Google Gemini API (Strictly server-side)
```

---

## 2. Tech Stack & Library Versions (Pinned)

| Layer / Concern | Technology | Pinned Version | Rationale |
|---|---|---|---|
| **Language** | Kotlin | `2.0.21` | Null-safety, coroutines, Compose compiler plugin |
| **Android Build** | Android Gradle Plugin (AGP) | `8.9.0` | Stable builds, R8 optimizations |
| **UI Toolkit** | Jetpack Compose BOM | `2024.10.01` | Declarative UI, state hoisting, memory-efficient lists |
| **Design System** | Material 3 Compose | Integrated in BOM | Modern accessible design tokens, warm terracotta theme |
| **Dependency Injection** | Dagger Hilt | `2.55` | Android DI, ViewModel scoping, clean unit testing |
| **Architecture** | AndroidX ViewModel & Lifecycle | `2.8.7` | UI state retention, CoroutineScope integration |
| **Local Persistence** | AndroidX Room | `2.6.1` | SQLite abstraction, offline caching, Flow queries |
| **Image Loading** | Coil Compose | `3.0.4` | Lightweight, coroutines-native, disk & memory caching |
| **Backend & Cloud** | Firebase Android BoM | `33.7.0` | Pinned compatible versions for Auth, Firestore, Storage |
| **Speech-to-Text** | Android SpeechRecognizer | Built-in Platform API | Zero APK overhead, native Hindi (`hi-IN`) voice transcription |
| **AI Processing** | Firebase Cloud Functions / GenAI | Server-Side Proxy | Zero client API key leakage, strict prompt sanitisation |

---

## 3. Data Model & Firestore Schema

### 3.1 Collections

#### `users/{userId}`
```typescript
interface UserProfile {
  userId: string;              // Firebase Auth UID
  phoneNumber?: string;        // Private - never exposed in public queries
  displayName: string;         // Public alias (e.g. "सुनीता जी (शिक्षिका)")
  role: 'teacher' | 'parent' | 'student' | 'volunteer' | 'shiksha_mitra';
  districtId: string;          // e.g. "barabanki", "sitapur"
  blockId: string;             // e.g. "fatehpur"
  isExpertVerified: boolean;   // Verified Teacher / Educator badge
  triedCountGiven: number;     // Number of activities marked as tried
  ideasSharedCount: number;    // Number of learning activities shared
  badges: string[];            // ["master_educator", "math_magician", "active_parent"]
  createdAt: Timestamp;
}
```

#### `posts/{postId}`
```typescript
type PostType = 'QUESTION' | 'ACTIVITY';

interface LearningPost {
  postId: string;
  authorId: string;            // User UID
  authorRole: string;          // Role snapshot
  authorName: string;          // Display name
  postType: PostType;          // 'QUESTION' or 'ACTIVITY'
  title: string;               // e.g. "कक्षा 5 के बच्चों को भिन्न कैसे समझाएं?"
  description: string;         // Full background or overview
  category: string;            // One of the 9 learning categories
  targetClass: string;         // e.g. "Class 1-3", "Class 4-5", "Class 6-8"
  imageUrl?: string;           // Optional photo of TLM or worksheet
  
  // Embedded Activity Card (only populated if postType === 'ACTIVITY')
  activityCard?: ActivityCard;

  // Social & Verification Signals
  upvotes: number;
  triedCount: number;          // "काम कर गया" verification count
  repliesCount: number;        // Number of solutions/replies
  bestStudyPlan?: BestStudyPlan; // AI-generated synthesis if question has top replies

  status: 'ACTIVE' | 'FLAGGED_EXCLUDED' | 'HIDDEN';
  createdAt: Timestamp;
  updatedAt: Timestamp;
}
```

#### `activity_cards` / Subcollection `posts/{postId}/replies/{replyId}`
```typescript
interface ActivityCard {
  cardId: string;
  postId: string;              // Parent post ID (if reply) or self ID (if standalone)
  targetClass: string;         // e.g. "कक्षा 3 से 5"
  timeMinutes: number;         // e.g. 10
  materialsNeeded: string[];   // e.g. ["माचिस की तीलियां", "कागज"] (TLM)
  steps: string[];             // Step 1, Step 2, Step 3
  learningOutcome: string;     // सीखने का प्रतिफल
  adaptations?: string;        // बदलाव के सुझाव
  upvotes: number;
  triedCount: number;          // "प्रमाणित: काम कर गया"
}
```

#### `BestStudyPlan` (Synthesized by AI)
```typescript
interface BestStudyPlan {
  title: string;               // e.g. "7 दिवसीय भिन्न (Fraction) अध्ययन योजना"
  summary: string;             // 2-sentence pedagogical synthesis
  dailySteps: DailyRoutineStep[];
  synthesizedFromReplies: string[]; // reply IDs
  disclaimer: string;          // "यह योजना साथी शिक्षकों के अनुभवों पर आधारित है।"
}

interface DailyRoutineStep {
  day: number;                 // Day 1 to Day 7
  title: string;               // e.g. "कंकड़ों से आधा-चौथाई की समझ"
  timeMinutes: number;         // 10-15 mins
  activitySummary: string;
}
```

---

## 4. AI Architecture & Guardrails

```
User Action: "Generate Best Study Plan" or "Coach My Activity"
      │
      ▼
Client calls Firebase Cloud Function: /synthesizeStudyPlan or /coachActivity
      │
      ├── Input Sanitisation & Moderator Filter (Reject infrastructure complaints, politics)
      │
      ├── Strict System Prompt:
      │     - Focus exclusively on pedagogical clarity, low-cost TLM, and positive reinforcement
      │     - NO medical or psychological advice (e.g. diagnosing ADHD or dyslexia)
      │     - NO fabricated research, false government schemes, or fake statistics
      │     - Clear admission of uncertainty when recommendations are inconclusive
      │
      └── Gemini 1.5 Flash / 2.0 Flash response -> Structured JSON parsed -> saved to Firestore
```

---

## 5. Offline-First Caching Strategy (Room DB)
* **Local Post Entity:** `PostEntity` stores posts, activity cards, and sync status (`isSynced`).
* **Offline Queue:** Drafted questions and activity posts are stored locally in Room. `WorkManager` retries posting once the device reconnects to network.
