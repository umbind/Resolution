# Design System & Screen Specifications: Resolution

## 1. Brand Kit & Visual Identity
* **Working App Name:** Resolution (समाधान मंच)
* **Tagline (Hindi):** समस्या आपकी, समाधान हम सबका।
* **Tagline (English):** Local educational challenges, community solutions.
* **Symbol / App Icon:** A stylized community circle forming an open book and a rising sun in warm amber and terracotta tones.

### Color Tokens (Material 3 Expressive Indian Palette)
```kotlin
// Light Theme Tokens
val Primary = Color(0xFFC04E22)          // Warm Terracotta (Action & Energy)
val OnPrimary = Color(0xFFFFFFFF)
val PrimaryContainer = Color(0xFFFFDBCF)
val OnPrimaryContainer = Color(0xFF3B0B00)

val Secondary = Color(0xFF77574B)        // Earthy Clay
val OnSecondary = Color(0xFFFFFFFF)
val SecondaryContainer = Color(0xFFFFDBCE)

val Tertiary = Color(0xFF28654D)         // Forest Green (Growth, Success, "Worked" tag)
val OnTertiary = Color(0xFFFFFFFF)
val TertiaryContainer = Color(0xFFAEF1CE)

val Background = Color(0xFFFCF8F7)       // Warm Off-White (Reduces eye fatigue)
val Surface = Color(0xFFFFFFFF)
val SurfaceVariant = Color(0xFFF5DED7)   // Soft tinted surface for cards
val Outline = Color(0xFF85736E)

// Dark Theme Tokens
val PrimaryDark = Color(0xFFFFB59C)
val BackgroundDark = Color(0xFF1A110F)
val SurfaceDark = Color(0xFF231917)
val SurfaceVariantDark = Color(0xFF53433E)
```

### Typography Scale (Devanagari First)
Using **Noto Sans Devanagari** (bundled locally in APK) with fallback to system fonts.
* **Display Large:** 32sp, Line height 40sp, Bold (Screen titles)
* **Headline Medium:** 22sp, Line height 28sp, SemiBold (Issue card titles)
* **Title Medium:** 16sp, Line height 22sp, Medium (Section headers)
* **Body Large:** 15sp, Line height 22sp, Regular (Description, body text)
* **Body Medium:** 13sp, Line height 18sp, Regular (Metadata, dates, location chips)
* **Label Large:** 14sp, Line height 20sp, SemiBold (Button text, 48dp touch target)

---

## 2. Key Screen Specifications

### 2.1 Language Selection & Splash Screen
* **Layout:** Centered logo with warm pulse animation; immediate dual buttons: "हिंदी में जारी रखें" (Primary elevated button) and "Continue in English" (Outlined button).
* **Benchmark Inspiration:** CoWIN & PhonePe onboarding.
* **Accessibility:** Large 56dp tall buttons with distinct sound/haptic feedback.

### 2.2 Onboarding (3 Cards, Skippable)
* **Card 1:** "समस्या साझा करें" (Speak or type local school challenges in under 60 seconds).
* **Card 2:** "समाधान पाएं" (Get structured plans from teachers, parents, and community volunteers).
* **Card 3:** "बदलाव लाएं" (Celebrate verified impact and share progress on WhatsApp).
* **Action:** Prominent "शुरू करें" (Get Started) button + "छोड़ें" (Skip) in top right.

### 2.3 Home Feed (समस्याएं)
* **Top App Bar:** App logo, District filter chip (e.g. "सीतापुर"), Notification bell.
* **Category Chips:** All (सभी), Infrastructure (भवन), Staff/Teachers (शिक्षक), Meals (भोजन), Books (किताबें).
* **Feed Cards:**
  - Status Chip (खुला / Open, 3 समाधान / 3 Solutions, हल हुआ / Resolved).
  - Clean Title (2 lines max, ellipsis).
  - Location Chip (Block & District).
  - Support counter (समर्थन) with 1-tap optimistic toggle.
  - Solution counter button navigating directly to solutions tab.
  - 1-tap WhatsApp share icon.
* **FAB:** Prominent Floating Action Button with microphone icon and label "मुद्दा दर्ज करें" (Post Issue).

### 2.4 Voice-First Issue Post Screen
* **Voice Flow:**
  - Large pulsing central microphone button with prompt: "बोलकर बताएं (Speak here)".
  - Real-time speech-to-text waveform.
  - Automatic transfer into Title and Description text fields.
* **Progressive Disclosure:**
  - Step 1: Title & Description (Required, auto-filled by voice).
  - Step 2: Category & District/Block selection (Pre-filled from user profile).
  - Step 3 (Optional): Photo attachment & School reference.
* **Safety Banner:** "कृपया किसी शिक्षक या छात्र का व्यक्तिगत नाम न लिखें।"

### 2.5 Issue Detail & Structured Solution Flow
* **Header:** Full issue description, author role badge (e.g. "अभिभावक / Parent"), photo viewer.
* **AI Summary Accordion:** 3-bullet quick summary in Hindi ("AI द्वारा संकलित").
* **Solutions List:** Sorted by community score & expert badges.
* **Solution Creation Card:**
  - Guided step wizard: (1) क्या किया जाएगा, (2) कौन करेगा, (3) अनुमानित खर्च, (4) समय, (5) जोखिम.
  - AI Solution Coach button: "सुझाव जांचें (Check with AI Coach)".

### 2.6 Best Practical Plan (BPP) View
* **Header:** "श्रेष्ठ व्यावहारिक योजना (Best Practical Plan)" badge.
* **Content:** Synthesized roadmap combining the most feasible solutions into Step 1, Step 2, and Step 3.
* **Action:** "व्हाट्सएप पर साझा करें" (Share to WhatsApp) generating a branded image card.

### 2.7 Impact Section (सफलता की कहानियां)
* **Feed of Resolved Issues:** Before and after photos, total days to resolve, community champions credited.
* **District Impact Barometer:** Total issues resolved in the user's district this month.

### 2.8 Profile & Verification
* **User Identity:** Display alias, role, district/block, earned badges.
* **Activity:** "मेरे मुद्दे (My Issues)", "मेरे समाधान (My Solutions)", "सहेजे गए (Saved)".
* **Expert Badge Request:** Simple form to submit credentials (teacher ID, NGO registration) for manual review.
