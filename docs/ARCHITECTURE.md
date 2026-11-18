# Technical Architecture: Resolution Android

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
                  ├── Firebase Cloud Storage (Compressed issue images)
                  └── Firebase Cloud Functions / AI Logic Proxy
                            └── Google Gemini 1.5/2.0 API (Strictly server-side)
```

---

## 2. Tech Stack & Library Versions (Pinned)

| Layer / Concern | Technology | Pinned Version | Rationale |
|---|---|---|---|
| **Language** | Kotlin | `2.4.21` / `2.0.21` | Null-safety, modern coroutines, latest Compose compiler plugin |
| **Android Build** | Android Gradle Plugin (AGP) | `8.9.0` (LTS) | Stable builds, R8 optimizations, Gradle 8.x compatibility |
| **UI Toolkit** | Jetpack Compose BOM | `2026.09.00` | Declarative UI, state hoisting, memory-efficient lists |
| **Design System** | Material 3 Compose | Integrated in BOM | Modern accessible design tokens, dynamic theming support |
| **Dependency Injection** | Dagger Hilt | `2.55` | Standard Android DI, ViewModel scoping, clean unit testing |
| **Architecture** | AndroidX ViewModel & Lifecycle | `2.8.7` | UI state retention, CoroutineScope integration |
| **Local Persistence** | AndroidX Room | `2.7.0` | SQLite abstraction, offline caching, reactive Flow queries |
| **Image Loading** | Coil Compose | `3.1.0` | Lightweight, coroutines-native, disk & memory caching |
| **Backend & Cloud** | Firebase Android BoM | `35.2.0` | Pinned compatible versions for Auth, Firestore, Storage, Messaging |
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
  email?: string;              // Private
  displayName: string;         // Public alias (e.g. "Ramesh K.")
  role: 'student' | 'parent' | 'teacher' | 'volunteer' | 'ngo';
  districtId: string;          // e.g. "lucknow", "sitapur"
  blockId: string;             // e.g. "bakshi_ka_talab"
  isExpertVerified: boolean;   // Manual verification badge
  isMinor: boolean;            // Under 18 flag (DPDP compliance)
  reputationScore: number;     // Quality-based score
  badges: string[];            // ["helpful_solver", "verified_educator"]
  createdAt: Timestamp;
}
```

#### `issues/{issueId}`
```typescript
interface Issue {
  issueId: string;
  authorId: string;            // User UID
  authorRole: string;          // Role snapshot
  authorAlias: string;         // Anonymized display name
  title: string;               // e.g. "प्राथमिक विद्यालय में पीने का पानी नहीं है"
  description: string;
  category: 'infrastructure' | 'teaching' | 'mid_day_meal' | 'materials' | 'safety' | 'other';
  districtId: string;          // e.g. "sitapur"
  blockId: string;             // e.g. "mahmoodabad"
  schoolNameOrRef?: string;    // Optional reference without defamatory individual names
  photoUrl?: string;           // Compressed WebP in Cloud Storage
  status: 'open' | 'solutions_added' | 'in_progress' | 'resolved';
  supportCount: number;        // Upvotes
  solutionCount: number;
  aiSummaryHindi?: string;     // Cached Gemini 3-bullet summary
  bestPracticalPlanId?: string;// Reference to synthesized plan
  createdAt: Timestamp;
  updatedAt: Timestamp;
  isFlagged: boolean;
}
```

#### `solutions/{solutionId}`
```typescript
interface Solution {
  solutionId: string;
  issueId: string;             // Parent issue
  authorId: string;
  authorAlias: string;
  authorRole: string;
  isExpertVerifiedAuthor: boolean;
  
  // Mandatory Structured Template
  whatWillBeDone: string;      // Actionable steps
  whoWillDoIt: string;         // e.g. "ग्राम पंचायत + स्कूल प्रबंधन समिति (SMC)"
  estimatedCost: 'zero' | 'under_1k' | '1k_to_5k' | 'above_5k';
  estimatedCostNumber?: number;// Optional exact amount (₹)
  estimatedTime: 'days' | 'weeks' | 'one_month' | 'multiple_months';
  keyRisks: string;            // Potential roadblocks
  workedExample?: string;      // "ऐसा ही मॉडल गोंडा के ब्लॉक A में सफल हुआ था"
  
  upvoteCount: number;
  triedAndWorkedCount: number; // Community verification
  isAiGenerated: boolean;      // True if generated or synthesized
  aiCoachFeedback?: string;    // Optional advice received prior to post
  createdAt: Timestamp;
}
```

#### `evaluations/{evalId}`
```typescript
interface Evaluation {
  evalId: string;
  solutionId: string;
  issueId: string;
  userId: string;
  feasibilityScore: number;    // 1-5
  costEffectivenessScore: number; // 1-5
  impactScore: number;         // 1-5
  markedAsWorked: boolean;
  workedProofUrl?: string;     // Photo proof of resolution
  createdAt: Timestamp;
}
```

#### `reports/{reportId}`
```typescript
interface UGCReport {
  reportId: string;
  targetType: 'issue' | 'solution' | 'user';
  targetId: string;
  reporterUserId: string;
  reason: 'abusive' | 'personal_defamation' | 'spam' | 'misleading' | 'safety';
  details?: string;
  status: 'pending' | 'reviewed' | 'actioned' | 'dismissed';
  createdAt: Timestamp;
}
```

---

## 4. Firestore Security Rules

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    
    function isAuthenticated() {
      return request.auth != null;
    }
    
    function isOwner(userId) {
      return isAuthenticated() && request.auth.uid == userId;
    }

    // User profiles
    match /users/{userId} {
      allow read: if isAuthenticated();
      allow create: if isOwner(userId);
      allow update: if isOwner(userId) && 
        (!request.resource.data.diff(resource.data).affectedKeys().hasAny(['isExpertVerified', 'reputationScore']));
      allow delete: if false; // Soft deletes only
    }

    // Issues
    match /issues/{issueId} {
      allow read: if true; // Public read for browsing without login
      allow create: if isAuthenticated() && request.resource.data.authorId == request.auth.uid;
      allow update: if isAuthenticated() && (
        resource.data.authorId == request.auth.uid || 
        request.resource.data.diff(resource.data).affectedKeys().hasOnly(['supportCount', 'solutionCount', 'status'])
      );
      allow delete: if isAuthenticated() && resource.data.authorId == request.auth.uid;
    }

    // Solutions
    match /solutions/{solutionId} {
      allow read: if true;
      allow create: if isAuthenticated() && request.resource.data.authorId == request.auth.uid;
      allow update: if isAuthenticated() && (
        resource.data.authorId == request.auth.uid ||
        request.resource.data.diff(resource.data).affectedKeys().hasOnly(['upvoteCount', 'triedAndWorkedCount'])
      );
      allow delete: if isAuthenticated() && resource.data.authorId == request.auth.uid;
    }

    // Evaluations
    match /evaluations/{evalId} {
      allow read: if true;
      allow create, update: if isAuthenticated() && request.resource.data.userId == request.auth.uid;
    }

    // UGC Reports
    match /reports/{reportId} {
      allow create: if isAuthenticated() && request.resource.data.reporterUserId == request.auth.uid;
      allow read, update, delete: if false; // Accessible only by admin/moderation backend
    }
  }
}
```

---

## 5. AI Design & Prompt Architecture

All Gemini interactions are executed through a secure backend cloud function or Vertex AI proxy (`/api/ai/...`). No API keys exist inside client code.

### 5.1 AI Features & Hindi Prompts
1. **Solution Coach (`solution_coach`):**
   * *Role:* Senior community mentor for Uttar Pradesh government schools.
   * *Input:* Draft title, description, who, cost, time.
   * *Output:* Constructive critique in 2-3 sentences of simple, polite Hindi.
   * *Example:* "आपका सुझाव उपयोगी है। लेकिन 'कौन करेगा' में सीधे स्कूल प्रबंधन समिति (SMC) और ग्राम प्रधान का नाम जोड़ने से क्रियान्वयन जल्दी संभव होगा।"
2. **Duplicate Detection (`duplicate_detection`):**
   * *Role:* Content matching engine. Compares incoming post embeddings against active issues in the same block.
   * *Output:* Similarity score (0.0 to 1.0) and matches.
3. **Best Practical Plan Synthesizer (`best_practical_plan`):**
   * *Role:* Pragmatic educational planner.
   * *Input:* Issue description + top 3 voted solutions.
   * *Output:* Markdown plan with Step 1 (तत्काल कार्रवाई), Step 2 (संसाधन जुटाना), Step 3 (स्थायी समाधान), clearly labeled as "AI द्वारा संकलित सुझाव".

### 5.2 Safety & Guardrails
- **Refusal to defame:** If a user post contains accusations against a specific named teacher or student, the coach prompts: "कृपया किसी व्यक्ति का नाम न लिखें, केवल समस्या और स्कूल का उल्लेख करें ताकि गरिमा बनी रहे।"
- **No legal/medical/political advice:** Intercepts political rallies, allegations of corruption with specific names, and redirects to official Jansunwai 1076 helpline.
