# Needs From User (Action Items)

These items require user interaction, external accounts, or credentials. As per project rules, the engineering agent continues building autonomously around these items.

---

## 1. GitHub Repository Creation & CI Sync
To enable automated CI (Lint, Unit Tests, Security Rules) on every push:
1. Open your browser and navigate to [https://github.com/new](https://github.com/new).
2. Set the repository name to `resolution-android`.
3. Leave it Public or Private (your choice), do not initialize with README (we have our own).
4. Run the following commands in your terminal inside `C:\Users\Umesh Kumar\.gemini\antigravity\scratch\resolution-android`:
   ```bash
   git remote add origin https://github.com/<your-username>/resolution-android.git
   git branch -M main
   git push -u origin main
   ```

---

## 2. Firebase Android App Registration (`resolution-a1a0b`)
To connect the live Firebase backend to the Android app:
1. Go to the [Firebase Console](https://console.firebase.google.com/project/resolution-a1a0b/overview).
2. Click **+ Add app** and select the **Android** icon.
3. Enter the Android package name: `com.resolution.app`.
4. App nickname: `Resolution Android`.
5. Enter your debug SHA-1 fingerprint (optional for now, needed for Google Sign-in & Phone Auth).
6. Click **Register app** and download `google-services.json`.
7. Move the downloaded `google-services.json` file into:
   `C:\Users\Umesh Kumar\.gemini\antigravity\scratch\resolution-android\app\google-services.json`

---

## 3. Local Android SDK & Java JDK Environment
To run `./gradlew` builds and launch emulators locally:
1. Ensure **Android Studio** (Koala or Ladybug) is installed.
2. Verify that **Android SDK Platform 35** and **Android SDK Build-Tools** are installed via SDK Manager.
3. Add `ANDROID_HOME` to your Windows Environment Variables (typically `C:\Users\Umesh Kumar\AppData\Local\Android\Sdk`).
4. Ensure JDK 17 or JDK 21 is installed and `JAVA_HOME` is set.
