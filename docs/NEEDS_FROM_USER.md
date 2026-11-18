# Needs From User (Action Items)

These items require user interaction, external accounts, or credentials. As per project rules, the engineering agent continues building autonomously around these items.

---

## 1. GitHub Repository Creation & CI Sync (COMPLETED)
- [x] Repository connected: `https://github.com/umbind/Resolution`
- [x] Codebase pushed to `origin/main` (commit `9f2c9c1`).
- [x] GitHub Actions workflow `.github/workflows/android.yml` is active.

---

## 2. Firebase Android App Registration (COMPLETED)
- [x] Android app `com.resolution.app` registered in project `resolution-a1a0b`.
- [x] `google-services.json` fetched programmatically and placed in `app/google-services.json`.
- [x] Firestore `(default)` database verified and `firestore.rules` deployed live.

---

## 3. Local Android SDK & Java JDK Environment (Optional for local running)
To run `./gradlew` builds and launch emulators locally on your machine:
1. Ensure **Android Studio** (Koala or Ladybug) is installed.
2. Verify that **Android SDK Platform 35** and **Android SDK Build-Tools** are installed via SDK Manager.
3. Add `ANDROID_HOME` to your Windows Environment Variables (typically `C:\Users\Umesh Kumar\AppData\Local\Android\Sdk`).
4. Ensure JDK 17 or JDK 21 is installed and `JAVA_HOME` is set.
