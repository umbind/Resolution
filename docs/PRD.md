# Product Requirements Document (PRD): Resolution

**Working Title:** Resolution (समाधान मंच)  
**Target Platform:** Android (Optimized for Android 8.0+ / API 26+; lightweight for low-end devices)  
**Primary Language:** Hindi (Default), English (Secondary)  
**Target Geography & Domain:** Uttar Pradesh, India (Education Focus: Infrastructure, Teacher Shortage, Mid-day Meals, Digital Access, Girls' Education)  
**Primary User Personas:** Students (14+), Parents, Teachers, Community Volunteers, Local NGOs  

---

## 1. Executive Summary & Problem Statement
In Uttar Pradesh, millions of students and schools face everyday educational challenges—ranging from non-functional toilets, delayed mid-day meals, broken benches, to teacher shortages. While formal government grievance portals (like Jansunwai 1076) exist, they are often seen as bureaucratic complaints rather than constructive community problem-solving platforms. 

**Resolution** is a constructive public platform where community members surface local educational challenges and collaboratively build, score, and execute practical solutions. Rather than merely venting or politicising grievances, users propose actionable solutions using a structured format, leveraged by Gemini AI assistance (Solution Coach, Duplicate Detection, and Best Practical Plans).

---

## 2. Core Principles
1. **Action-Oriented, Non-Political:** Strictly exclude political finger-pointing; focus exclusively on verifiable school and community solutions.
2. **Accessible to First-Time Smartphone Users:** Voice-first posting in Hindi; under-60-second time-to-first-post; large touch targets; zero jargon.
3. **Low-End Phone & Weak Network Resilient:** Sub-25MB APK; aggressive offline caching with Room; lazy-loading and compressed image storage; graceful offline queueing.
4. **Safety & DPDP Act 2023 Compliance:** Child protection first; zero public exposure of minors' contact details or school rosters; no defamatory naming of teachers or students; mandatory in-app reporting and blocking.
5. **Healthy, Non-Addictive Engagement:** Recognition rooted in real-world impact (worked solutions, verified badges, "Solution of the Week") without toxic streaks, dopamine hacking, or vanity metrics.

---

## 3. User Personas & Journeys
* **Ramesh (Parent, Rural Sitapur):** Wants clean drinking water in his daughter's primary school. Uses Hindi voice input to post an issue in 45 seconds. Reads solutions proposed by a local NGO.
* **Sunita (Primary School Teacher, Barabanki):** Needs teaching aids and library books for Grade 4. Posts a request; receives structured guidance on community book-donation drives.
* **Amit (College Student / Youth Volunteer, Lucknow):** Enjoys solving math doubts and organizing weekend tutoring; earns "Helpful Solution" and "Verified Volunteer" badges.
* **Seema (Local Education NGO Representative):** Identifies recurring block-level issues; publishes vetted solution blueprints with cost estimates; tracks real-world impact.

---

## 4. MVP Feature Specifications

### 4.1 Authentication & Role-Based Profiles
- **Auth Methods:** Firebase Phone Auth (OTP) & Google Sign-In.
- **Roles:** Student (Age verified / Parent consent check), Parent, Teacher, Volunteer, NGO Representative.
- **Profile Fields:** Name/Alias, District, Block, Role, Contributor Badges, Resolved Impact Count.
- **Privacy Controls:** Option to post with verified role alias (e.g., "Parent from Bakshi Ka Talab") to protect privacy while maintaining credibility.

### 4.2 Issue Creation (Voice-First & Low-Friction)
- **Fast 60-Second Flow:**
  1. Title (Text or Hindi Voice Transcription via Android SpeechRecognizer).
  2. Description (Text or Voice).
  3. Category: Infrastructure (भवन/शौचालय/बिजली), Teaching/Staff (शिक्षक/कक्षा), Mid-day Meal (मध्याह्न भोजन), Study Materials & Books (किताबें/उपकरण), Girls' Safety (बालिका सुरक्षा/सुलभता).
  4. Location: UP District (75 districts dropdown) & Block (तहसील/ब्लॉक).
  5. Optional Attachment: Compressed single photo (max 1080p, <500KB compressed locally).
- **Offline Drafts:** Auto-saved locally in Room DB.

### 4.3 Structured Solution Engine
Rather than casual comments, solutions follow a mandatory structured schema:
1. **What will be done (क्या किया जाएगा)**
2. **Who will do it (कौन करेगा: पंचायत, शिक्षक, स्वयंसेवक, एनजीओ)**
3. **Estimated Cost (अनुमानित खर्च: ₹0, <₹1,000, ₹1k-₹5k, >₹5k)**
4. **Estimated Time to Implement (समय सीमा: 1-3 दिन, 1-2 सप्ताह, 1 महीना+)**
5. **Key Risks & Precautions (जोखिम और सावधानियां)**
6. **Worked Example (कहाँ पहले सफल रहा)**

### 4.4 Community Evaluation & "Tried This, It Worked"
- **Feasibility, Cost, and Impact Ratings** (1 to 5 stars or simple High/Medium/Low chips).
- **Upvote / Support Button** (with optimistic local UI update).
- **"Tried this, it worked" Tag:** Requires before-and-after photo evidence or verification from 2 distinct community members.

### 4.5 AI Assistance (Powered by Server-Proxied Gemini)
- **Solution Coach:** Interactive pre-post critique: "आपका सुझाव अच्छा है, लेकिन खर्च का विवरण जोड़ें तो पंचायत जल्दी ध्यान देगी।"
- **Duplicate Issue Detector:** Alerts user if a similar issue in the same block/school already exists; prompts joining forces.
- **Issue Summary:** 3-bullet summary in simple Hindi for long posts.
- **Best Practical Plan (BPP):** Synthesizes top 2-3 voted solutions into a single step-by-step action plan.
- **Transparency:** All AI outputs clearly badged with "AI द्वारा संकलित (AI-Generated)", fully editable by author, and reportable.

### 4.6 Safety, Moderation & UGC Compliance (Google Play & DPDP)
- **Content Guidelines Acceptance:** Upfront acceptance modal before first post or solution.
- **In-App Reporting:** 1-tap report button on every issue and solution (Categories: Abusive language, Defamation/Naming individuals, False info, Irrelevant/Spam).
- **Blocking:** 1-tap block user from profile or issue card.
- **Anti-Defamation Guardrails:** AI and regex filter intercepting teacher/student personal names or phone numbers.
- **Minor Protection:** DPDP Act 2023 compliance—no behavioral tracking, strict parental consent flag for under-18, no public ranking of minors.

### 4.7 Shareability & Social Proof
- **WhatsApp Image Card Generator:** Renders a clean, high-contrast Hindi graphic summary card with issue title, district, best solution, and QR/short link for instant viral sharing on WhatsApp status and groups.
- **Impact Tab:** Displays verified resolved issues with before/after photos and community credit.

---

## 5. Non-Functional Requirements
- **APK Size:** Under 20 MB download size.
- **Cold Launch Time:** < 1.8 seconds on 2GB RAM Android Go devices.
- **Battery & Memory:** Zero background battery drain; image caching memory bound to 15% of heap.
- **Offline Support:** Full read capability for cached feeds and offline draft queueing with automatic sync when connection resumes.
