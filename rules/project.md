# Project Rules: Resolution Android

### Autonomy
- Do not ask questions that you can answer by reasonable judgment. Decide, and record the decision in docs/DECISIONS.md with a one-line reason.
- If something needs the user (secrets, console clicks, product, money, or legal decisions), add it to docs/NEEDS_FROM_USER.md with exact steps, skip to another task, and continue. Do not stop for it.
- Stop only at approval gates (G0 to G5) or when every remaining task is blocked.

### Quality
- Work in small tasks: plan briefly, implement, write tests, run the build and tests, fix until green. After 3 failed attempts on one problem, log it in docs/BLOCKERS.md with the exact error and move on.
- Never claim that something works unless you ran it and saw the result. Report real command output.
- Commit to git after every task with a clear message. Keep docs/STATUS.md updated (done, in progress, next, blockers).

### Testing standards
- Every feature needs unit tests, and every user journey in the backlog needs at least one UI or integration test.
- Tests must be meaningful: each test must be able to fail if the feature breaks. Never write tests that assert only that code runs, never weaken or delete a failing test to make the build pass, and never mock away the behaviour that is being tested. If a test must change, explain why in the commit message.
- Test Firestore and Storage security rules with the Firebase emulator suite, including cases where an unauthorised user must be denied.
- Keep code coverage at or above 70 percent for business logic (view models, use cases, repositories). Report the real number at each gate.
- Lint and static analysis (Android lint plus ktlint or detekt) must pass with no errors. Fix warnings in code you touch.

### Continuous integration
- Create a GitHub Actions workflow that, on every push and pull request, builds the app, runs lint, unit tests, and security-rule tests, and fails if any step fails. Add Dependabot or an equivalent dependency-update and vulnerability check.
- Add the exact steps for the user to create the GitHub repository and push to it in docs/NEEDS_FROM_USER.md.

### AI quality
- Maintain docs/AI_EVAL.md and a runnable evaluation script with at least 30 realistic education cases from Uttar Pradesh written in Hindi, with some in English and some mixed. Cover: school infrastructure, teacher shortage, dropouts, exam stress, digital access, fees, mid-day meal quality, girls' education, and skill training. Include tricky cases: duplicate issues, vague posts, posts that name a teacher or student, abusive or political posts that must be flagged, and solutions that are unrealistic or unsafe.
- For each case, define what a good AI output must contain and what it must never do (invent statistics, invent government schemes, give medical or legal advice, reveal personal data). Run the evaluation whenever AI prompts or models change and at every gate, and report pass rates by feature. Log the prompts used in docs/AI_PROMPTS.md.
- If the AI is not confident or lacks facts, the app must say so instead of guessing.

### Design and engagement
The app must feel attractive, trustworthy, and effortless for first-time smartphone users in Uttar Pradesh, while staying fast on low-end phones. Create docs/DESIGN.md (design tokens, components, screen patterns) and docs/ENGAGEMENT.md (engagement plan and metrics) before building screens, and apply them to every screen.

#### World-class UX benchmark
The target is a UI and UX that feels as polished, clear, and effortless as the best apps in the world, adapted to our users. Learn principles and patterns from great apps, but never copy another app's layout pixel for pixel, brand assets, icons, illustrations, or text. The final design must be original.

1. Research first. Before designing any screen, search the web and study 8 to 10 best-in-class apps across these groups: community and social feeds, learning apps with strong habit and onboarding design, mass-market Indian apps used by first-time smartphone users (messaging, payments, food or travel apps), and recent award-winning apps from Google Play and design awards. Also read current Material 3 and Android design guidance. For each app note what it does well in onboarding, navigation, feed, forms, empty states, feedback, motion, trust, and accessibility. Write docs/UX_BENCHMARK.md with the patterns to adopt, the patterns to avoid, and the reason for each.
2. Principles to apply everywhere: one clear primary action per screen; familiar conventions (bottom navigation with at most 5 destinations, pull to refresh, a prominent post action); immediate feedback for every tap; forgiveness (undo, drafts saved automatically, confirmations only for destructive actions); progressive disclosure; recognition over recall; consistent components; trust cues (verification badges, source labels, clear "AI-generated" labels); and moments of delight at success points such as posting an issue or seeing a solution marked as worked.
3. Interaction polish: optimistic updates for votes and ratings, skeleton loading, smooth shared-element or fade transitions, subtle haptic feedback, edge-to-edge layout, predictive back gesture support, and press states on every tappable element. Before adding an animation library, check its effect on APK size and low-end performance.
4. Design specs before code. For each key screen (splash, language choice, onboarding, home feed, issue detail, post issue with voice, add solution using the structured template as a guided step-by-step or card flow, Best Practical Plan view, Impact, profile, notifications, settings) write a short spec in docs/DESIGN.md covering layout, states (empty, loading, error, offline), components, interactions, accessibility, and which benchmark pattern inspired it. Show the screenshots of the key screens at Gate G1 and wait for approval before building the remaining screens.
5. Heuristic review. Review every key user flow against Nielsen's 10 usability heuristics and record the findings and fixes.
6. UX rubric. Score the app from 0 to 10 in each of these categories, with evidence (screenshots, measured numbers): first impression and visual identity; clarity and navigation; speed to first value; ease of posting and form completion; motion and feedback; empty, loading, and error states; Hindi typography and localisation; accessibility; performance on a low-end phone; trust and emotional delight. The target is no category below 8 by Gate G4. Your own scores are a draft: report them honestly, list the weakest category first, and never inflate them. The user and an independent reviewer decide the final scores.
7. Usability testing. Write docs/USABILITY_TEST.md: a 5-person test script for real target users (for example a student, a parent, and a teacher) with specific tasks, success criteria, timing targets, and questions. Ask the user to run it at Gate G2 and G4 and to bring back the findings, then turn the findings into backlog tasks.

Visual identity:
- Use Material 3 as the base but give the app its own identity: a warm, hopeful colour palette suited to education and community (not a default template look), one consistent accent colour, rounded cards, generous spacing, and clear visual hierarchy. Support light and dark themes, with sufficient colour contrast.
- Use a Devanagari-friendly typeface with good readability at small sizes (for example Noto Sans Devanagari or Hind), tested for Hindi conjuncts and long words. Check that Hindi text never clips or overflows.
- Use simple, familiar icons with text labels. Do not rely on icons alone.
- Create an app icon, splash screen, and a small brand kit (name, tagline in Hindi and English, colours) and record them in docs/DESIGN.md.
- Use real-feeling empty states (friendly illustration or message with a clear next action), loading skeletons instead of blank screens, and kind, plain-language error messages in Hindi.
- Use subtle, fast animations only where they help (transitions, vote and success feedback). Respect the system "reduce animations" setting and never let animation slow the app on low-end devices.

First-use experience:
- Onboarding of at most 3 short screens, skippable, with a choice of Hindi or English on the first screen. Let people browse issues before forcing login. Ask for login only when posting, voting, or commenting.
- Voice-first posting: a prominent microphone option so a person can speak an issue in Hindi and review the transcribed text before posting. Target: a new user can post a first issue in under 60 seconds.
- Large touch targets, one primary action per screen, a short post form with progressive detail (title and description first, optional fields later), and a visible progress state for slow networks.

Social proof and shareability:
- Home feed with clean issue cards: photo or category illustration, short title, location chip, status chip (open, solutions added, in progress, resolved), and counts of solutions and supporters.
- One-tap sharing of an issue or the Best Practical Plan as a clean image card with a short link, designed for WhatsApp, in Hindi.
- An "Impact" tab showing resolved issues with before-and-after notes and the solution that worked, plus simple community numbers for the user's district and block. Only show verified or clearly sourced outcomes. Never fake or inflate numbers.

Healthy engagement (no dark patterns):
- Reward quality, not volume: contributor levels, badges (for example "Helpful solution", "Tried and worked", "Expert verified"), a weekly "Solution of the week", and local recognition by district or block. Do not use streak pressure, guilt-based messages, fake urgency, or public shaming.
- Notifications must be useful and limited: replies to my issue, a solution added to my issue, status changes, and a weekly digest. Provide clear notification settings, a default cap on daily notifications, and quiet hours.
- Because users may be minors, no manipulative mechanics, no public ranking of minors, and no features that encourage excessive use.

Performance and polish:
- Keep the app fast on low-end phones: cold start and scrolling must feel smooth, images must be compressed and lazy loaded, and the APK must stay small. Test on a low-end emulator profile and report the results at each gate.
- Accessibility: support large font sizes, screen readers (content descriptions in Hindi), and colour contrast, and never rely on colour alone to convey status.

Measuring attractiveness (privacy-respecting, with consent):
- Add basic analytics events with user consent and no personal data: onboarding completion, time to first post, first-week return rate, share rate, solution-to-issue ratio, and notification opt-out rate. Define targets in docs/ENGAGEMENT.md, report them at gates once real users exist, and suggest improvements.

### Engineering
- No hard-coded strings: use string resources with Hindi as the default locale and English as the second locale.
- No secrets in code or git. Use local.properties or the secret mechanism of the backend, and keep .gitignore correct.
- Accessibility basics, small APK size, offline caching, and graceful handling of slow networks.
- Never delete files outside the workspace, never force-push, never change git history.
- Privacy and child safety come first: collect the minimum data, and never expose phone numbers or exact student identities publicly.

### Gates (stop, show the user, wait)
- G0: plan approved.
- G1: app skeleton, design system (theme, typography, components, splash, icon), onboarding, login, and navigation running on the emulator with CI green.
- G2: issue and solution flow complete.
- G3: AI features working and AI evaluation reported.
- G4: moderation, safety, Hindi and English polish, and all tests green.
- G5: beta-ready signed build with Play Store checklist.

At each gate: run the app and all tests, and produce:
- a walkthrough with screenshots in Hindi and English, light and dark mode,
- a short design review against docs/DESIGN.md and the UX rubric in docs/UX_BENCHMARK.md, with scores (what looks good, what still looks plain, and the 3 most valuable visual or usability improvements),
- a short test report (test counts, coverage, lint result, CI status),
- a list of what the user should try on a real phone (maximum 7 checks),
- your top 3 risks,
- at most 3 questions for the user,
- docs/REVIEW_PACK.md: a compact, self-contained summary (what changed since the last gate, key files, architecture or data-model changes, security rules diff, test and coverage numbers, AI evaluation results, known weaknesses) that the user can paste to an independent reviewer.

Then stop and wait.
