# Product Requirements Document (PRD): पढ़ाई की चौपाल (Padhai Ki Chaupal)

**App Name:** पढ़ाई की चौपाल  
**Tagline (Hindi):** "सवाल पूछो, आइडिया बाँटो"  
**Tagline (English):** "Ask questions, share learning ideas"  
**Target Platform:** Android (Optimized for Android 8.0+ / API 26+; lightweight for low-end devices)  
**Primary Language:** Hindi (Default), English (Secondary)  
**Target Geography & Domain:** Uttar Pradesh, India (Learning Community: Study methods, teaching tricks, TLM, exam prep, learning challenges)  
**Primary User Personas:** Teachers, Parents, Students (14+), Education Volunteers, Shiksha Mitras  

---

## 1. Executive Summary & Problem Statement
In Uttar Pradesh and across India, teachers, parents, and students face daily challenges in *how to learn and study effectively*: How do you explain fractions to a 5th grader who struggles with numbers? How can parents help children with English homework when they don't speak English themselves? How can a teacher make a 40-student primary classroom interactive with zero budget?

**पढ़ाई की चौपाल** is a community knowledge-sharing platform dedicated entirely to **HOW TO LEARN AND STUDY**. It is **NOT** a grievance or complaint portal. Users do not report broken toilets or missing infrastructure. Instead, they ask questions about learning challenges and share practical, low-cost activities, study techniques, and teaching tricks. 

---

## 2. Core Principles
1. **Focus on Learning Solutions, Not Infrastructure Grievances:** School infrastructure complaints (broken walls, water pumps, teacher vacancies) and political accusations are strictly excluded and flagged. The platform is 100% focused on pedagogy, study methods, and learning activities.
2. **Accessible to Low-Resource Educators & Parents:** Voice-first posting in Hindi; under-60-second posting; large touch targets; simple Hindi without educational jargon.
3. **Structured "Activity Cards" (गतिविधि कार्ड):** Ideas are not vague opinions; they follow a concrete template: class/age, time needed, low-cost materials (TLM), step-by-step method, and learning outcome.
4. **Safety & Child Protection:** Strict adherence to DPDP Act 2023. Zero exposure of minors' contact details or identifiable photos. No named accusations against teachers, schools, or students.
5. **Constructive Recognition:** Badges for verified educators, community-voted "Tried it and it worked" (प्रमाणित: काम कर गया) counters, and WhatsApp-friendly sharing for teachers' groups.

---

## 3. User Personas & Journeys
* **Sunita Sharma (Primary School Teacher, Barabanki):** Wants a quick 5-minute math game for Grade 3 multiplication. Searches the app, finds a pebble-based counting activity, tries it in class, and marks "Tried it and it worked!"
* **Ramesh Chandra (Parent, Sitapur):** His son in Grade 6 gets anxious before exams. Ramesh posts a question: "बच्चा परीक्षा के नाम से घबराता है, क्या करें?". Gets structured routines from experienced teachers and an AI-generated weekly study routine.
* **Amit (Shiksha Mitra / Youth Volunteer, Varanasi):** Creates creative low-cost TLM (teaching-learning materials) using cardboard and bottle caps to teach English phonics. Shares the step-by-step activity card and earns the "Star Educator" badge.
* **Pooja (Grade 10 Student, Lucknow):** Asks for effective memory techniques to remember history dates without rote cramming; receives mnemonic tricks and a revision timetable.

---

## 4. MVP Feature Specifications

### 4.1 Post Architecture: Two Core Post Types
Every post belongs to one of two distinct types:
1. **सवाल या चुनौती (Question or Challenge):**
   - e.g., "कक्षा 5 के बच्चे को भिन्न (fractions) खेल-खेल में कैसे सिखाएं?", "पहाड़े याद करने में कठिनाई हो रही है।"
   - Contains: Title, Description, Category, Target Class/Age, Optional voice note or image.
2. **आइडिया या गतिविधि (Idea or Activity):**
   - Follows the structured **Activity Card** template (see 4.3).
   - Can be posted as a standalone idea in the feed or as a direct solution/reply to a question.

### 4.2 9 Core Categories
1. **पढ़ाई के तरीके व तकनीक (Study methods & memory techniques)**
2. **गणित और विज्ञान गतिविधियाँ (Maths and science activities)**
3. **पढ़ना और लिखना (Reading and writing fluency)**
4. **अंग्रेजी व भाषा सीखें (English and language learning)**
5. **परीक्षा की तैयारी व तनाव मुक्ति (Exam preparation and stress management)**
6. **कम खर्च की शिक्षण सामग्री / TLM (Low-cost activities & DIY toys)**
7. **कक्षा में पढ़ाने के तरीके (Classroom teaching methods & games)**
8. **करियर और हुनर (Careers, vocational skills & guidance)**
9. **घर पर बच्चों की पढ़ाई में मदद (Support for parents at home)**

### 4.3 Structured "Activity Card" (गतिविधि कार्ड) Schema
When posting an Idea/Activity or replying to a Question:
* **कक्षा / आयु वर्ग (Target Class/Age):** e.g., Balvatika / Class 1-3 / Class 4-5 / Class 6-8 / Class 9-10 / Class 11-12
* **समय (Time needed):** 5 मिनट / 15 मिनट / 30 मिनट
* **सामग्री (Materials needed - Low cost / TLM):** e.g., बोतल के ढक्कन, माचिस की तीलियां, कागज, कंकड़, ₹0 खर्च
* **चरणबद्ध तरीका (Step-by-step method):** Clear numbered steps
* **सीखने का प्रतिफल (Expected Learning Outcome):** What the learner will understand
* **काम कर गया (Tried it and it worked counter):** Community verification button
* **सुझाव / बदलाव (Adaptation notes):** How to modify for larger classes or younger siblings

### 4.4 AI Features (via Gemini Server-Side)
1. **Activity Coach (गतिविधि कोच):** Evaluates a draft idea before publishing, providing tips to make steps clearer, safer, and suitable for the selected age group.
2. **Duplicate & Similarity Clustering:** Finds similar questions already asked and points the user to existing answers.
3. **श्रेष्ठ अध्ययन योजना (Best Study Plan):** For any question with multiple responses, Gemini synthesizes the best community activities into a practical weekly routine (e.g., Day 1 to Day 6 daily 15-minute micro-habits).
4. **AI Safety Guardrails:** Strictly prohibits medical/psychological diagnoses, does not fabricate government schemes or research studies, and explicitly includes disclaimers when uncertain.

### 4.5 Exclusions & Moderation Rules
* **Excluded Content:** School infrastructure complaints (broken toilets, boundary walls, drinking water issues, handpumps, teacher vacancies), political debates, personal naming/accusations against teachers, staff, or students.
* **Automated & Community Moderation:** Pre-submit regex and keyword checks flag infrastructure/complaint words (शिकायत, शौचालय, हैंडपंप, वेतन, ट्रांसफर, नेता, भ्रष्ट) and gently redirect: "यह मंच पढ़ाई और गतिविधियों के लिए है। शिकायत दर्ज करने के लिए जनसुनवाई (1076) का उपयोग करें।"

---

## 5. Non-Functional Requirements
* **App Size:** < 25 MB APK.
* **Offline-First:** All feeds and drafts cached locally in Room DB. Auto-sync on reconnection.
* **Network Tolerance:** Performs smoothly on 2G/3G connections and high-latency edge networks in rural UP.
* **Bilingual UI:** Hindi default with high-contrast text and warm terracotta educational theme (`#C04E22`).
