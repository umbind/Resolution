# Project Backlog & Milestones: Resolution Android

Milestones are mapped to Approval Gates (G0 to G5). Each task is sized for a single autonomous session with clear Acceptance Criteria (AC) and mandatory Tests.

---

## Milestone G0: Project Inception & Planning (Current)
- [x] **TASK-001: Requirements & Seed Analysis**
  - *AC:* Create `docs/SEED.md`, `rules/project.md`, `workflows/*.md`.
  - *Tests:* File presence and schema validation.
- [x] **TASK-002: In-Depth Research & PRD**
  - *AC:* Author `docs/PRD.md`, `docs/ARCHITECTURE.md`, `docs/UX_BENCHMARK.md`, `docs/DESIGN.md`, `docs/ENGAGEMENT.md`, `docs/USABILITY_TEST.md`, `docs/RISKS.md`, `docs/AI_EVAL.md`.
  - *Tests:* Peer-review against bootstrap instructions.
- [ ] **TASK-003: Project Skeleton, Gradle & CI Setup**
  - *AC:* Initialize Git, `.gitignore`, Gradle build files (`build.gradle.kts`, `app/build.gradle.kts`, `settings.gradle.kts`), GitHub Actions workflow `.github/workflows/android.yml`, `lint.xml`.
  - *Tests:* Local `./gradlew test` / CI pipeline syntax check.
- [ ] **TASK-004: Gate G0 Review Report**
  - *AC:* Publish Gate G0 review, no-code validation recommendation, 3 user questions.

---

## Milestone G1: App Foundation, Design System, Onboarding & Auth
- [ ] **TASK-101: Design System & Theming (Material 3)**
  - *AC:* Warm Indian educational palette (`Warm Amber`, `Deep Indigo`, `Terracotta`), Devanagari typography scale (Noto Sans Devanagari), light & dark themes, button/card components with 48dp touch targets.
  - *Tests:* Compose preview render tests, contrast ratio unit tests (> 4.5:1).
- [ ] **TASK-102: Localisation Infrastructure**
  - *AC:* Zero hardcoded strings; Hindi (`res/values/strings.xml`) as default locale, English (`res/values-en/strings.xml`) secondary. Language selector supporting runtime switch.
  - *Tests:* Automated linter verifying no string literals in `@Composable`.
- [ ] **TASK-103: Onboarding & Guest Browsing Flow**
  - *AC:* Max 3 skippable onboarding cards explaining community problem solving; guest access allowed to view feed without forced login.
  - *Tests:* UI navigation test from splash -> onboarding -> home feed.
- [ ] **TASK-104: Authentication (Phone OTP & Google)**
  - *AC:* Firebase Auth integration with clean phone number input, OTP verification, Google Sign-In, and user role selection modal (Student, Parent, Teacher, Volunteer, NGO).
  - *Tests:* `AuthViewModelTest` mocking Firebase Auth results; role persistence in Room/Firestore.
- [ ] **TASK-105: Bottom Navigation & Screen Scaffolding**
  - *AC:* 4 main destinations: Feed (समस्याएं), Post (नया मुद्दा), Impact (सफलता), Profile (प्रोफाइल); Edge-to-edge layout, predictive back support.
  - *Tests:* Integration test for tab switching and backstack preservation.

---

## Milestone G2: Core Issue & Solution Flows (Offline-First)
- [ ] **TASK-201: Issue Feed with Offline Caching**
  - *AC:* LazyColumn with skeleton loading, pull-to-refresh, district & category filter chips, Room database caching with `Flow<PagingData<Issue>>`.
  - *Tests:* `IssueRepositoryTest` with offline cache fallback when network fails.
- [ ] **TASK-202: Voice-First Issue Creation**
  - *AC:* 1-tap microphone button invoking Android SpeechRecognizer in Hindi; real-time transcription display; district/block selector for 75 UP districts; compressed photo attachment (<500KB).
  - *Tests:* Post creation unit test, image compression utility test, draft auto-save test.
- [ ] **TASK-203: Issue Detail Screen**
  - *AC:* Clear layout showing author role, school context, status chip, support button with optimistic update, and solution list.
  - *Tests:* Upvote toggle test, optimistic state rollback on error.
- [ ] **TASK-204: Structured Solution Creation**
  - *AC:* Step-by-step or card-guided form: What, Who, Cost tier, Time tier, Risks, Worked Example.
  - *Tests:* Form validation test preventing incomplete or empty solutions.
- [ ] **TASK-205: Community Scoring & "Tried and Worked"**
  - *AC:* Rating chips (Feasibility, Cost, Impact), "Tried this, it worked" verification workflow.
  - *Tests:* Aggregate scoring computation unit test.

---

## Milestone G3: AI Features (Server-Proxied Gemini)
- [ ] **TASK-301: Solution Coach (Pre-Post Critique)**
  - *AC:* Non-blocking review button giving 2-sentence polite Hindi critique on feasibility and missing details before publishing.
  - *Tests:* Evaluation against 30 test cases in `docs/AI_EVAL.md` (> 85% helpfulness score).
- [ ] **TASK-302: Duplicate Issue Detection**
  - *AC:* Real-time check against local/server issues in the same block; surfaces top match with "क्या यह वही समस्या है?".
  - *Tests:* Benchmark test on duplicate case pairs.
- [ ] **TASK-303: 3-Bullet Issue Summary & Best Practical Plan (BPP)**
  - *AC:* Synthesizes top solutions into 3 actionable steps; clearly badged with "AI द्वारा संकलित"; editable.
  - *Tests:* Prompt regression tests in `docs/AI_EVAL.md`.

---

## Milestone G4: Safety, Moderation, WhatsApp Cards & Polish
- [ ] **TASK-401: UGC Content Reporting & User Blocking**
  - *AC:* 1-tap report on every card (Abuse, Defamation, False info, Spam); instant local hide; profile user blocking mechanism.
  - *Tests:* Google Play UGC compliance verification test suite.
- [ ] **TASK-402: Minor Safety & DPDP Act 2023 Guardrails**
  - *AC:* Anonymized public aliases, zero public phone numbers or photos of minors, parental consent confirmation for under-18.
  - *Tests:* Privacy filter unit test preventing phone numbers/emails in text.
- [ ] **TASK-403: WhatsApp Image Card Generator**
  - *AC:* Client-side Canvas/Bitmap renderer generating high-contrast Hindi summary card with title, district, best solution, and link.
  - *Tests:* Image rendering unit test; Intent sharing test.
- [ ] **TASK-404: Accessibility & Performance Audit**
  - *AC:* Full TalkBack Hindi content descriptions, 48dp touch targets, font scaling up to 200%, cold launch < 1.8s on low-end profile.
  - *Tests:* Accessibility scanner tests, macrobenchmark startup test.

---

## Milestone G5: Production Hardening, Release Prep & Play Store
- [ ] **TASK-501: Firebase Security Rules & Storage Rules Deployment**
  - *AC:* Emulator test suite for Firestore/Storage security rules passing with 100% authorization checks.
  - *Tests:* Firebase Rules Unit Tests using `@firebase/rules-unit-testing`.
- [ ] **TASK-502: ProGuard/R8 Optimization & Release Signing**
  - *AC:* Minification enabled, APK size verified under 20MB, release keystore setup guide.
  - *Tests:* Release build execution and APK size measurement.
- [ ] **TASK-503: Release Documentation & Play Store Checklist**
  - *AC:* `docs/RELEASE.md`, Bilingual Store listings, Data Safety declaration, Privacy Policy.
  - *Tests:* Verification against Google Play submission checklist.
