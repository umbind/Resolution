# Project Backlog & Milestones: पढ़ाई की चौपाल (Padhai Ki Chaupal)

Milestones are mapped to Approval Gates (G0 to G5). Each task has clear Acceptance Criteria (AC) and mandatory Tests.

---

## Milestone G0: Inception, Pivot & Planning (Approved)
- [x] **TASK-001: Pivot Directive & Seed Analysis** (Done - Focused on learning methods & activities)
- [x] **TASK-002: Pivot PRD, Architecture & Content Model** (Done - Padhai Ki Chaupal)
- [x] **TASK-003: Project Skeleton, KSP, Hilt & Firebase Setup** (Done)
- [x] **TASK-004: Gate G0 Review & Pivot Approval** (Approved by User)

---

## Milestone G1: App Foundation, Theming & Navigation (Approved)
- [x] **TASK-101: Warm Terracotta Design System (Material 3)** (Done)
- [x] **TASK-102: Bilingual Localization (Hindi Default / English Secondary)** (Done)
- [x] **TASK-103: Guest Browsing & Phone/Google Authentication** (Done)
- [x] **TASK-104: Main Navigation Scaffolding (चौपाल / आइडिया बनाएं / असर / प्रोफाइल)** (Done)

---

## Milestone G2: Learning Posts, Activity Cards & Community Knowledge (In Progress)
- [x] **TASK-201: Learning Posts Feed with Offline Caching**
  - *AC:* Two post tabs ("सवाल या चुनौती" / "आइडिया या गतिविधि") with 9 learning categories. Room DB offline cache (`PostEntity`, `PostDao`, `AppDatabase`).
  - *Tests:* `testFeed_filtersByPostTypeAndCategory`.
- [x] **TASK-202: Post Creation Flow (Question vs Activity Wizard)**
  - *AC:* Post type selector, voice input for title & description, anti-complaint moderation banner, class/age group picker.
  - *Tests:* `testCreateQuestion_requiresTitleAndCategory`.
- [x] **TASK-203: Structured Activity Card Wizard**
  - *AC:* Class/age group, time needed, low-cost TLM materials, step-by-step method, learning outcome, adaptation notes.
  - *Tests:* `testActivityCard_validationRules`.
- [x] **TASK-204: Post Detail & Activity Replies**
  - *AC:* Question view with community activity replies, "Tried it and it worked" (काम कर गया) counter, upvotes, and expert badges.
  - *Tests:* `testTriedCountIncrement`.
- [x] **TASK-205: AI Best Study Plan (श्रेष्ठ अध्ययन योजना)**
  - *AC:* Card displaying synthesized daily study routine from top community replies.
  - *Tests:* `testBestStudyPlan_structureAndDisclaimer`.

---

## Milestone G3: AI Features (Activity Coach, Duplicate Detection & Study Routine)
- [ ] **TASK-301: Activity Coach (गतिविधि कोच)**
  - Pre-post AI critique ensuring activities are age-appropriate, clear, and safe.
- [ ] **TASK-302: Duplicate Question Detection & Clustering**
  - Matches new questions against existing repository of answered questions.
- [ ] **TASK-303: Best Study Plan Generator Cloud Function**
  - Synthesizes top ideas into 7-day micro-habit routine.

---

## Milestone G4: Safety, Moderation & Sharing
- [ ] **TASK-401: Pre-Submit Anti-Complaint & Anti-Defamation Moderation Filter**
  - Blocks infrastructure complaints (toilets, handpumps, teacher shortage, politics) and redirects to Jansunwai.
- [ ] **TASK-402: WhatsApp Shareable Activity Card Image Generator**
  - Generates lightweight branded JPEG/PNG cards for sharing in teachers' WhatsApp groups.
- [ ] **TASK-403: DPDP Compliance & Report/Block System**
  - Anonymous authoring options and community moderation flag workflow.

---

## Milestone G5: Launch Readiness & Gate G5 Sign-off
- [ ] **TASK-501: End-to-End Testing on Low-End Device Profile**
- [ ] **TASK-502: Security Audit & Firestore Rules Deployment**
- [ ] **TASK-503: Release APK Build (<25MB) & Gate G5 Demo**
