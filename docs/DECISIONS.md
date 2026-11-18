# Architecture & Product Decision Log: Resolution

| Decision ID | Decision | One-Line Reason |
|---|---|---|
| **DEC-001** | Use Kotlin + Jetpack Compose with Material 3 | Modern declarative UI standard for Android with native support for dynamic themes and Devanagari typography. |
| **DEC-002** | Select Hindi as default locale (`res/values`) with English secondary (`res/values-en`) | Primary target users are rural and semi-urban residents of Uttar Pradesh, India. |
| **DEC-003** | Use Room Database for aggressive offline caching | Intermittent 2G/3G connectivity requires complete offline feed viewing and draft protection. |
| **DEC-004** | Route Gemini AI calls strictly through server-side Cloud Functions / Vertex AI proxy | Prevents client APK decompilation from exposing raw API keys and enforces server-side prompt guardrails. |
| **DEC-005** | Implement Android SpeechRecognizer (`hi-IN`) for voice-first posting | Built-in platform speech-to-text adds zero bytes to APK size while enabling under-60-second posting for first-time smartphone users. |
| **DEC-006** | Exclude political content and personal names of teachers/students | Complies with Indian defamation laws, prevents harassment, and maintains a constructive solution focus. |
| **DEC-007** | Avoid heavy external animation libraries (Lottie/Rive) | Keeps APK download size under 20MB and avoids frame drops on low-end 2GB RAM Android Go devices. |
| **DEC-008** | Enforce DPDP Act 2023 compliance with minor protections | India's data law classifies individuals under 18 as children with strict prohibitions on profiling, tracking, and non-consensual data exposure. |
| **DEC-009** | Adopt structured solution schema (What, Who, Cost, Time, Risks, Example) | Prevents social media ranting and forces actionable community problem-solving. |
| **DEC-010** | WhatsApp Image Card generation via client Canvas/Bitmap | WhatsApp is the universal distribution channel in India; image cards ensure viral grassroots reach. |
| **DEC-011** | Pivot product to "पढ़ाई की चौपाल" (Learning methods & activities community) | Pivot from infrastructure grievances to pedagogical community knowledge sharing. Exclude complaints; focus on Question/Challenge and Idea/Activity cards, with AI Best Study Plan synthesis. |
