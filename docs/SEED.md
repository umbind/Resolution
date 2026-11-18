# Product Seed Brief: Resolution

App name: working title "Resolution" (can be renamed later).

Android app where people post public issues and others suggest solutions. First focus: EDUCATION in Uttar Pradesh, India. Politics is excluded. Hindi is the primary language, English second. Users have low-end phones and weak internet. The architecture must allow other domains (farming, health) and other states later without rebuilding.

## MVP Features:
1. **Login & Profile**: Phone OTP or Google authentication; simple profile with user role (Student, Parent, Teacher, Volunteer, NGO).
2. **Post an Issue**: Title, description, category, district and block (UP), optional photo, voice input in Hindi.
3. **Browse & Discovery**: Browse, search, filter issues by district/block/status; offline-friendly caching.
4. **Structured Solutions**: Add a solution using a structured template:
   - What will be done
   - Who does it
   - Estimated cost
   - Estimated time
   - Risks
   - Similar example that worked elsewhere
5. **Community Scoring**: Upvotes/support plus ratings for feasibility, cost, and impact, and a "tried this, it worked" verification tag.
6. **AI Features (via Gemini Server/Firebase path)**:
   - Solution Coach: critiques a draft before posting
   - Duplicate Issue Detection
   - Issue Summary
   - "Best Practical Plan": merges the strongest solutions of an issue into actionable steps
   - Clear "AI-generated" labeling, editable and reportable
7. **Safety & Moderation**:
   - Report content and basic moderation queue
   - Strict guidelines for posts that name teachers, schools, or students
   - No personal data in public posts
   - Extra protections and care for users under 18
8. **Verified-Expert Badge**: Manual approval workflow for the MVP.
9. **World-Class UI/UX & Healthy Engagement**:
   - Warm, trustworthy, modern aesthetic adapted for low-end Android devices
   - Fast first-use experience (voice-first posting, first post under 60 seconds)
   - WhatsApp-friendly sharing of issues and solutions as image cards (Hindi)
   - "Impact" section celebrating resolved issues with verified real outcomes
   - Quality-based recognition (contributor levels, badges, "Solution of the week")
   - Non-spammy, respectful notification system (quiet hours, daily caps)

## Technology Defaults:
- Language & Framework: Kotlin, Jetpack Compose, Material 3
- Architecture: MVVM with Clean Architecture (UI, Domain, Data layers)
- Dependency Injection: Hilt
- Backend: Firebase (Auth, Firestore, Cloud Storage, Cloud Messaging)
- AI: Google Gemini accessed via server-side / Firebase Cloud Functions or Firebase Vertex AI / AI Logic (no raw API key in client APK)
- Pinned versions, offline caching via Room, edge-to-edge layout, Devanagari typography support.
