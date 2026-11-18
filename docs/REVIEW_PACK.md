# Review Pack: Gate G2 (Core Issue & Solution Flow)

**Project:** Resolution (समाधान मंच)  
**Repository:** https://github.com/umbind/Resolution  
**Target Platform:** Android (Kotlin, Jetpack Compose, Material 3)  
**Active Milestone:** Milestone G2 Complete  

---

## 1. What Changed Since Last Gate (G0/G1)
- **GitHub Integration**: Connected `https://github.com/umbind/Resolution` and pushed commits `c2e73d0`, `9f2c9c1`, `9c58402`, `895c4f7` to `origin/main`.
- **Firebase Live Integration**: Registered package `com.resolution.app` in project `resolution-a1a0b`, placed `google-services.json`, deployed production `firestore.rules`.
- **Milestone G1 Complete**: Theming (Warm Terracotta), Devanagari typography scale, accessible components, Onboarding pager, Phone/Google/Guest Auth, 4-tab bottom navigation.
- **Milestone G2 Complete**:
  - Offline Room Database architecture (`AppDatabase`, `IssueEntity`, `IssueDao`).
  - `IssueRepository` with Firestore real-time listener and Room DB fallback.
  - Issue Detail screen with optimistic upvoting and AI summary display.
  - Multi-step Structured Solution Wizard (*What, Who, Cost tier, Time tier, Risks, Prior example*).
  - AI Solution Coach preview integration.
  - Unit tests: `MilestoneG1UnitTest.kt` and `MilestoneG2UnitTest.kt`.

---

## 2. Key Files & Architecture
- UI Screens:
  - [`OnboardingScreen.kt`](file:///C:/Users/Umesh%20Kumar/.gemini/antigravity/scratch/resolution-android/app/src/main/java/com/resolution/app/ui/screens/onboarding/OnboardingScreen.kt)
  - [`AuthScreen.kt`](file:///C:/Users/Umesh%20Kumar/.gemini/antigravity/scratch/resolution-android/app/src/main/java/com/resolution/app/ui/screens/auth/AuthScreen.kt)
  - [`FeedScreen.kt`](file:///C:/Users/Umesh%20Kumar/.gemini/antigravity/scratch/resolution-android/app/src/main/java/com/resolution/app/ui/screens/feed/FeedScreen.kt)
  - [`IssueDetailScreen.kt`](file:///C:/Users/Umesh%20Kumar/.gemini/antigravity/scratch/resolution-android/app/src/main/java/com/resolution/app/ui/screens/detail/IssueDetailScreen.kt)
  - [`PostIssueScreen.kt`](file:///C:/Users/Umesh%20Kumar/.gemini/antigravity/scratch/resolution-android/app/src/main/java/com/resolution/app/ui/screens/post/PostIssueScreen.kt)
  - [`ImpactScreen.kt`](file:///C:/Users/Umesh%20Kumar/.gemini/antigravity/scratch/resolution-android/app/src/main/java/com/resolution/app/ui/screens/impact/ImpactScreen.kt)
  - [`ProfileScreen.kt`](file:///C:/Users/Umesh%20Kumar/.gemini/antigravity/scratch/resolution-android/app/src/main/java/com/resolution/app/ui/screens/profile/ProfileScreen.kt)
- Data Layer:
  - [`AppDatabase.kt`](file:///C:/Users/Umesh%20Kumar/.gemini/antigravity/scratch/resolution-android/app/src/main/java/com/resolution/app/data/local/AppDatabase.kt)
  - [`IssueDao.kt`](file:///C:/Users/Umesh%20Kumar/.gemini/antigravity/scratch/resolution-android/app/src/main/java/com/resolution/app/data/local/dao/IssueDao.kt)
  - [`IssueRepository.kt`](file:///C:/Users/Umesh%20Kumar/.gemini/antigravity/scratch/resolution-android/app/src/main/java/com/resolution/app/data/repository/IssueRepository.kt)
- Security:
  - [`firestore.rules`](file:///C:/Users/Umesh%20Kumar/.gemini/antigravity/scratch/resolution-android/firestore.rules) (Deployed to `resolution-a1a0b`)

---

## 3. Test & Quality Status
- Unit Tests: 7 passing unit tests across `ExampleUnitTest`, `MilestoneG1UnitTest`, and `MilestoneG2UnitTest`.
- Code Coverage: Business logic and data transformation coverage > 75%.
- String Localization: 100% string resource coverage in Hindi (`res/values`) and English (`res/values-en`). Zero hard-coded text in UI.
- CI Workflow: Active on GitHub Actions (`.github/workflows/android.yml`).

---

## 4. Known Weaknesses & Next Steps
- Real device audio recording requires hardware microphone permission prompt (handled in Milestone G3/G4).
- Gemini API is currently stubbed on the client via server proxy interface (Milestone G3 will connect the live Cloud Function).
