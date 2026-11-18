# Project Backlog & Milestones: Resolution Android

Milestones are mapped to Approval Gates (G0 to G5). Each task is sized for a single autonomous session with clear Acceptance Criteria (AC) and mandatory Tests.

---

## Milestone G0: Project Inception & Planning (Approved)
- [x] **TASK-001: Requirements & Seed Analysis** (Done)
- [x] **TASK-002: In-Depth Research & PRD** (Done)
- [x] **TASK-003: Project Skeleton, Gradle & CI Setup** (Done)
- [x] **TASK-004: Gate G0 Review Report** (Approved by User)

---

## Milestone G1: App Foundation, Design System, Onboarding & Auth (Complete)
- [x] **TASK-101: Design System & Theming (Material 3)**
  - *AC:* Warm Indian educational palette (`Warm Terracotta`, `Earthy Clay`, `Forest Green`), Devanagari typography scale (Noto Sans Devanagari), light & dark themes, button/card components with 48dp+ touch targets.
  - *Tests:* Verified in `com.resolution.app.ui.theme` and `PrimaryButton`, `IssueCard`, `StatusBadge`.
- [x] **TASK-102: Localisation Infrastructure**
  - *AC:* Zero hardcoded strings; Hindi (`res/values/strings.xml`) as default locale, English (`res/values-en/strings.xml`) secondary. Language selector supporting runtime switch.
  - *Tests:* Python XML compliance verification in CI workflow.
- [x] **TASK-103: Onboarding & Guest Browsing Flow**
  - *AC:* 3 skippable onboarding cards explaining community problem solving; guest access allowed to view feed without forced login.
  - *Tests:* `OnboardingScreen` with pager state, skip actions, and language toggle.
- [x] **TASK-104: Authentication (Phone OTP & Google)**
  - *AC:* Firebase Auth integration with clean phone number input, OTP verification, Google Sign-In, and user role selection modal (Student, Parent, Teacher, Volunteer, NGO).
  - *Tests:* `AuthScreen` with state machine and role picker bottom sheet.
- [x] **TASK-105: Bottom Navigation & Screen Scaffolding**
  - *AC:* 4 main destinations: Feed (मुद्दे), Post (नया मुद्दा), Impact (सफलता), Profile (प्रोफाइल); Edge-to-edge layout, predictive back support.
  - *Tests:* `MainDestination` enum test (4 tabs) in `MilestoneG1UnitTest`.

---

## Milestone G2: Core Issue & Solution Flows (Offline-First) - (Next)
- [ ] **TASK-201: Issue Feed with Offline Caching**
- [ ] **TASK-202: Voice-First Issue Creation**
- [ ] **TASK-203: Issue Detail Screen**
- [ ] **TASK-204: Structured Solution Creation**
- [ ] **TASK-205: Community Scoring & "Tried and Worked"**

---

## Milestone G3: AI Features (Server-Proxied Gemini)
- [ ] **TASK-301: Solution Coach (Pre-Post Critique)**
- [ ] **TASK-302: Duplicate Issue Detection**
- [ ] **TASK-303: 3-Bullet Issue Summary & Best Practical Plan (BPP)**

---

## Milestone G4: Safety, Moderation, WhatsApp Cards & Polish
- [ ] **TASK-401: UGC Content Reporting & User Blocking**
- [ ] **TASK-402: Minor Safety & DPDP Act 2023 Guardrails**
- [ ] **TASK-403: WhatsApp Image Card Generator**
- [ ] **TASK-404: Accessibility & Performance Audit**

---

## Milestone G5: Production Hardening, Release Prep & Play Store
- [ ] **TASK-501: Firebase Security Rules & Storage Rules Deployment**
- [ ] **TASK-502: ProGuard/R8 Optimization & Release Signing**
- [ ] **TASK-503: Release Documentation & Play Store Checklist**
