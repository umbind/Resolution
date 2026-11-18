# Project Backlog & Milestones: Resolution Android

Milestones are mapped to Approval Gates (G0 to G5). Each task is sized for a single autonomous session with clear Acceptance Criteria (AC) and mandatory Tests.

---

## Milestone G0: Project Inception & Planning (Approved)
- [x] **TASK-001: Requirements & Seed Analysis** (Done)
- [x] **TASK-002: In-Depth Research & PRD** (Done)
- [x] **TASK-003: Project Skeleton, Gradle & CI Setup** (Done)
- [x] **TASK-004: Gate G0 Review Report** (Approved by User)

---

## Milestone G1: App Foundation, Design System, Onboarding & Auth (Approved)
- [x] **TASK-101: Design System & Theming (Material 3)** (Done)
- [x] **TASK-102: Localisation Infrastructure** (Done)
- [x] **TASK-103: Onboarding & Guest Browsing Flow** (Done)
- [x] **TASK-104: Authentication (Phone OTP & Google)** (Done)
- [x] **TASK-105: Bottom Navigation & Screen Scaffolding** (Done)

---

## Milestone G2: Core Issue & Solution Flows (Complete)
- [x] **TASK-201: Issue Feed with Offline Caching**
  - *AC:* Room Database (`AppDatabase`, `IssueEntity`, `IssueDao`) configured with reactive flows for full offline resilience.
  - *Tests:* `MilestoneG2UnitTest.kt` verifying entity-domain mapping.
- [x] **TASK-202: Voice-First Issue Creation**
  - *AC:* Pulsing mic button in `PostIssueScreen`, anti-defamation guardrail banner, district & block selection.
  - *Tests:* Verified state retention and required field checks.
- [x] **TASK-203: Issue Detail Screen**
  - *AC:* Header, location chips, optimistic support counter, 3-bullet AI summary card, solutions list.
  - *Tests:* Navigational transitions and support counter updates.
- [x] **TASK-204: Structured Solution Wizard & Card Form**
  - *AC:* Mandatory fields (*क्या किया जाएगा, कौन करेगा, अनुमानित खर्च, समय, जोखिम, सफल उदाहरण*), Solution Coach review check.
  - *Tests:* `structuredSolution_requiresActionableWhatAndWho` test.
- [x] **TASK-205: Community Scoring & "Tried and Worked"**
  - *AC:* Verified tried-and-worked counter badge and upvotes.
  - *Tests:* Scoring logic verification.

---

## Milestone G3: AI Features (Server-Proxied Gemini) - (Next)
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
